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
    Stages stage;
    double arrivalTime;
    double processTime;
    double arrivalTimeLength;
    double processTimeLength;

    public Car(ExpDistribution nextCarArrive, NormalDistribution service) {
        stage = Stages.ARRIVE;
        arrivalTimeLength = nextCarArrive.next();
        processTimeLength = service.sample();
    }

    public double getTime() {
        if (stage == Stages.ARRIVE) {
            return arrivalTime;
        } else {
            return processTime;
        }
    }

    public void addCurrentTime(double currentTime) {
        if (stage == Stages.ARRIVE) {
            arrivalTime = currentTime + arrivalTimeLength;
        } else {
            processTime = currentTime + processTimeLength;
        }
    }

    public double calculateTime() {
        return processTime - arrivalTime;
    }

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

    public enum Stages {
        ARRIVE, PROCESS;
    }
}
