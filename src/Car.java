import myUtil.ExpDistribution;
import myUtil.NormalDistribution;

/**
 * Ian Leyden
 * Ryan Mirman
 * <p/>
 * The source is on github at https://github.com/Rsgm/hairy-octo-wallhack
 * <p/>
 * Copyright under the MIT license
 * http://opensource.org/licenses/MIT
 */
public class Car {
    Stages stage; // state of the car
    double arrivalTime; // time it takes to arrive after the last car
    double processTime; // time it takes to process                                     p
    double arrivalTimeLength; // the exact time at which the car will have arrived
    double processTimeLength; // the time at which the car will have finished processing

    /**
     * Initializes a car with the state as arriving. This will also set the car's times using the given prng.
     *
     * @param nextCarArrive prng object
     * @param service       prng object
     */
    public Car(ExpDistribution nextCarArrive, NormalDistribution service) {
        stage = Stages.ARRIVE;
        arrivalTimeLength = nextCarArrive.next();
        processTimeLength = service.sample();
    }

    /**
     * Gives the time until the current stage finishes.
     *
     * @return the finish time of the current stage
     */
    public double time() {
        if (stage == Stages.ARRIVE) {
            return arrivalTime;
        } else {
            return processTime;
        }
    }

    /**
     * Adds the current time to the current stage of the car.
     *
     * @param currentTime the current time of the simulation
     */
    public void addCurrentTime(double currentTime) {
        if (stage == Stages.ARRIVE) {
            arrivalTime = currentTime + arrivalTimeLength;
        } else {
            processTime = currentTime + processTimeLength;
        }
    }

    /**
     * Calculate the time it took from arrival until it has finished processing.
     *
     * @return the time the car took to go through the full cycle of events
     */
    public double calculateTime() {
        return processTime - arrivalTime;
    }

    /**
     * The type of the car's state it is currently in.
     */
    public enum Stages {
        ARRIVE, PROCESS
    }

    // getters and setters below
    // -------------------------

    public Stages getStage() {
        return stage;
    }

    public void setStage(Stages stage) {
        this.stage = stage;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getProcessTime() {
        return processTime;
    }

    public void setProcessTime(double processTime) {
        this.processTime = processTime;
    }

    public double getArrivalTimeLength() {
        return arrivalTimeLength;
    }

    public void setArrivalTimeLength(double arrivalTimeLength) {
        this.arrivalTimeLength = arrivalTimeLength;
    }

    public double getProcessTimeLength() {
        return processTimeLength;
    }

    public void setProcessTimeLength(double processTimeLength) {
        this.processTimeLength = processTimeLength;
    }
}
