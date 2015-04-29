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
    private double globalTime = 0;
    private double mtime = 0;
    private double vtime = 0;
    private double flow = 0;
    private int numBooths = 0;
    private Queue<Car> arrivalQueue = new LinkedQueue<Car>();
    private Queue<Car> futureCarQueue = new LinkedQueue<Car>();
    private ArrayList<LinkedQueue<Car>> booths; // I think we were allowed to use built in arraylists for this
    Stats stats;

    public Sim(double mtime, double vtime, double flow, int numBooths, Stats stats) {
        this.mtime = mtime;
        this.vtime = vtime;
        this.flow = flow;
        this.stats = stats;
        this.booths = new ArrayList<LinkedQueue<Car>>(numBooths);

        for (int i = 0; i < numBooths; i++) {
            this.booths.add(new LinkedQueue<Car>());
        }

        ExpDistribution nextCarArrive = new ExpDistribution(1 / flow);
        NormalDistribution service = new NormalDistribution(mtime, vtime);

        double totalArrivalTime = 0;

        do {
            Car car = new Car(nextCarArrive, service);
            if (car.getProcessTimeLength() < mtime) {
                car.setProcessTimeLength(1.0);
                this.stats.ezTotal();
            } else {
                this.stats.nEzProcess(car.getProcessTimeLength());
            }
            futureCarQueue.offer(car);
            this.stats.totalCars();
            car.addCurrentTime(totalArrivalTime);
            totalArrivalTime += car.getArrivalTimeLength();
        } while (totalArrivalTime < 10800);
    }

    public static void main(String[] args) {
        int numBooths = 2;
        double mTime = 3;
        double vTime = 6;
        double flow = .5;

        Stats stats = new Stats();
        Sim sim = new Sim(mTime, vTime, flow, numBooths, stats);
        sim.run();

        System.out.println(
                "Simulation -- 3 hours (Booth No:" + numBooths + ") (Without EZ-Pass: m = " + mTime + ", v = " + vTime +
                ")");
        System.out.println("Flow: " + flow + " cars/Sec");
        System.out.println("Total cars: " + stats.total + " EZ-Pass: " +
                           stats.ezPass); // this came out to -3 once, we suspect time travel, but my past self says otherwise
        System.out.println("Average pass through window without EZ-Pass: " + stats.nonEzTotal() + " Secs"); //^this comment got me 200+ karma
        System.out.println("Max number of cars waiting on the road: " + stats.maxWaiting);                  //on /r/programmerhumor
        System.out.println("Average total waiting time: " + stats.averageTime());                               //the problem is fixed now
    }

    private void run() {
        while (!futureCarQueue.empty()) {
            Queue<Car> process = nextCar();
            if (process.peek().getStage() == Car.Stages.ARRIVE) { //checks if car is at arrival
                Car car = process.poll();
                Queue<Car> min = booths.get(0); //the car with the lowest time goes first
                for (LinkedQueue<Car> booth : booths) {
                    if (booth.size() < min.size()) { //car finds the shortest queue for a booth
                        min = booth;
                    }
                }
                globalTime = car.time(); //time is added
                car.setStage(Car.Stages.PROCESS); //stage is changed
                globalTime = car.time();
                car.setStage(Car.Stages.PROCESS);
                if (min.size() < 20) {
                    min.offer(car);
                    if (min.size() == 1) {
                        car.addCurrentTime(globalTime);
                    }
                } else {
                    arrivalQueue.offer(car);
                    stats.waiting(arrivalQueue.size());
                }
            } else {
                Car car = process.poll();
                globalTime = car.time();
                if (arrivalQueue.size() != 0) {
                    process.offer(arrivalQueue.poll());
                }
                stats.totalTime(car.calculateTime());
                car = process.peek();
                if (car != null) {
                    car.addCurrentTime(globalTime);
                }
            }
        }
    }

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
