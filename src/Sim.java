import myUtil.ExpDistribution;
import myUtil.LinkedQueue;
import myUtil.NormalDistribution;
import myUtil.Queue;

import java.util.ArrayList;

/**
 * Ian Leyden
 * Ryan Mirman
 * <p/>
 * The source is on github at https://github.com/Rsgm/hairy-octo-wallhack
 * <p/>
 * Copyright under the MIT license
 * http://opensource.org/licenses/MIT
 */
public class Sim {
    private Queue<Car> arrivalQueue = new LinkedQueue<Car>(); // holds the cars waiting for a booth
    private Queue<Car> futureCarQueue = new LinkedQueue<Car>(); // holds the pool of cars yet to arrive
    private ArrayList<LinkedQueue<Car>> booths = new ArrayList<LinkedQueue<Car>>(); // I think we were allowed to use built in arraylists for this
    private Stats stats; // hold the simulation statistics

    /**
     * Initializes the simulation with a car pool of every car that will arrive within the three hour restriction.
     *
     * @param mtime pseudo-random number generator(prng) mean time
     * @param vtime prng variance time
     * @param flow flow rate of cars
     * @param numBooths number of booths to use
     * @param stats object to hold and calculate the statistics
     */
    public Sim(double mtime, double vtime, double flow, int numBooths, Stats stats) {
        this.stats = stats; // set the stats object

        for (int i = 0; i < numBooths; i++) { // create a queue for each booth
            this.booths.add(new LinkedQueue<Car>()); // add the queue to the list
        }

        // create the prng objects
        ExpDistribution nextCarArrive = new ExpDistribution(1 / flow);
        NormalDistribution service = new NormalDistribution(mtime, vtime);

        // keeps track of the summation of arrival times t know when to stop
        double totalArrivalTime = 0;

        // fill the
        do {
            Car car = new Car(nextCarArrive, service);

            // set ez pass if necessary
            if (car.getProcessTimeLength() < mtime) {
                car.setProcessTimeLength(1.0);
                this.stats.incrementEzTotal(); // add to the total of ez passes
            } else {
                this.stats.nonEzProcessTime(car.getProcessTimeLength());
            }

            futureCarQueue.offer(car);
            this.stats.incrementTotalCars();

            car.addCurrentTime(totalArrivalTime); // could be done after each car arrives in the simulation, but this doesn't add any extra time
            totalArrivalTime += car.getArrivalTimeLength(); // increment th time to the this cars arrival time
        } while (totalArrivalTime < 10800);
    }

    /**
     * The method in which the program is invoked.
     * @param args simulation parameters
     */
    public static void main(String[] args) {
        double mTime = Double.parseDouble(args[0]);
        double vTime = Double.parseDouble(args[1]);
        double flow = Double.parseDouble(args[2]);
        int numBooths = Integer.parseInt(args[3]);

        Stats stats = new Stats();
        Sim sim = new Sim(mTime, vTime, flow, numBooths, stats);
        sim.run();

        System.out.println("Simulation -- 3 hours (Booth No:" + numBooths + ") (Without EZ-Pass: m = " + mTime + ", v = " + vTime + ")");
        System.out.println("Flow: " + flow + " cars/Sec");
        System.out.println("Total cars: " + stats.totalCars + " EZ-Pass: " + stats.ezPassTotal); // this came out to -3 once, we suspect time travel, but my past self says otherwise
        System.out.println("Average pass through window without EZ-Pass: " + stats.nonEzTotal() + " Secs"); //^this comment got me 200+ karma on /r/programmerhumor the problem is fixed now
        System.out.println("Max number of cars waiting on the road: " + stats.maxCarsWaiting);
        System.out.println("Average total waiting time: " + stats.averageTime());
    }

    /**
     * Runs the simulation.
     */
    private void run() {
        while (!futureCarQueue.empty()) {
            Queue<Car> nextCarQueue = nextCar(); // get the next car to be processed, whether processing or arriving
            double globalTime;

            // if the car is arriving, add it to a booth, or the arrival overflow queue
            if (nextCarQueue.peek().getStage() == Car.Stages.ARRIVE) { //checks if car is at arrival
                Car car = nextCarQueue.poll();
                Queue<Car> min = booths.get(0); //the car with the lowest time goes first

                // find the shortest booth
                for (LinkedQueue<Car> booth : booths) {
                    if (booth.size() < min.size()) { //car finds the shortest queue for a booth
                        min = booth;
                    }
                }

                globalTime = car.time(); //time is added
                car.setStage(Car.Stages.PROCESS); //stage is changed

                if (min.size() < 20) {
                    min.offer(car);
                    if (min.size() == 1) {
                        car.addCurrentTime(globalTime); // start processing the car by calculating the end process time
                    }
                } else { // if the minimum is 20 or more, then add the car to the arrival queue
                    arrivalQueue.offer(car);
                    stats.waiting(arrivalQueue.size());
                }

            } else { // if the car is being processed, remove it from the booth and start processing the next car in the booth
                Car car = nextCarQueue.poll();
                globalTime = car.time();
                if (arrivalQueue.size() != 0) { // since there is now room, let a car waiting in
                    nextCarQueue.offer(arrivalQueue.poll());
                }

                stats.incrementTotalTime(car.calculateTime()); // add the time taken to process to the stats object
                car = nextCarQueue.peek();
                if (car != null) {
                    car.addCurrentTime(globalTime); // add the current time to the car to get the processing finish time
                }
            }
        }
    }

    /**
     * Find the car with the next even in time, that is, the next car to arrive or be processed.
     * @return the queue that contains this car, either a booth or the future car queue
     */
    public Queue<Car> nextCar() {
        Queue<Car> min = futureCarQueue;
        for (LinkedQueue<Car> booth : booths) {
            if (!booth.empty()) {
                double i = booth.peek().time();
                //checks if any of the booths has a car with a time that comes before that of the futureCarQueue or current minimum time
                if (i < min.peek().time()) {
                    min = booth;
                }
            }
        }
        return min;
    }
}
