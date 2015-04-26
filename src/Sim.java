import java.security.PrivateKey;
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
    private static double mtime = 0;
    private static double vtime = 0;
    private static double flow = 0;
    private static int numBooths = 0;
    private Queue<Car> arrivalQueue = new LinkedQueue<Car>();
    private Queue<Car> futureCarQueue = new LinkedQueue<Car>();
    private ArrayList<LinkedQueue<Car>> booths;

    public Sim(double mtime, double vtime, double flow, int numBooths) {
        this.mtime = mtime;
        this.vtime = vtime;
        this.flow = flow;
        this.booths = new ArrayList<LinkedQueue<Car>>(numBooths);
        for (int i = 0; i < numBooths; i++) {
            this.booths.add(new LinkedQueue<Car>(20));
        }

        ExpDistribution nextCarArrive = new ExpDistribution(1 / flow);
        NormalDistribution service = new NormalDistribution(mtime, vtime);

        double totalArrivalTime = 0;

        do {
            Car car = new Car(nextCarArrive, service);
            car.addCurrentTime(totalArrivalTime);
            futureCarQueue.offer(car);
            totalArrivalTime += car.arrivalTime;
        } while (totalArrivalTime < 10800);
    }

    public static void main(String[] args) {
        new Sim(3,6,.5,2).run();
        System.out.println("Simulation -- 3 hours (Booth No:" + numBooths + ") (Without EZ-Pass: m = " + mtime + ", v = " + vtime + ")");
        System.out.println("Flow: " + flow + " cars/sec");
        System.out.println("Total cars: " );
        System.out.println("Pass window without EZ-Pass: ");
        System.out.println("Max number of cars waiting on the road: ");
        System.out.println("Average waiting time: ");

    }

    private void run() {
        while (!futureCarQueue.empty()) {
            Queue<Car> process = nextCar();
            if(process.peek().getStage() == Car.Stages.ARRIVE){
                Car car = process.poll();
                Queue<Car> min = booths.get(0);
                for(LinkedQueue<Car> booth : booths){
                    if (booth.size() < min.size()){
                        min = booth;
                    }
                }
                if(min.size() < 20){
                    min.offer(car);
                }else{
                    arrivalQueue.offer(car);
                }
                globalTime = car.getTime();
                car.setStage(Car.Stages.PROCESS);
            }else {
                Car car = process.poll();
                globalTime = car.getTime();
                if (arrivalQueue.size() != 0){
                    process.offer(arrivalQueue.poll());
                }
                car = process.peek();
                car.addCurrentTime(globalTime);
            }
        }
    }

    public Queue<Car> nextCar() {
        Queue<Car> min = futureCarQueue;
        for (LinkedQueue<Car> booth : booths) {
            double i = booth.peek().getTime();
            if (i < min.peek().getTime() ) {
                min = booth;
            }
        }
        return min;
    }
}
