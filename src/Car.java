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
    double totalTime;

    public Car(ExpDistribution nextCarArrive, NormalDistribution service) {
        stage = Stages.ARRIVE;
        arrivalTime = nextCarArrive.next();
        processTime = service.sample();
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
            arrivalTime += currentTime;
        } else {
            processTime += currentTime;
        }
    }

    public Stages getStage(){
        return stage;
    }

    public void setStage(Stages stage){
        this.stage = stage;
    }

   public double calculateTime(){
       return processTime - arrivalTime;
   }

    public enum Stages {
        ARRIVE, PROCESS;
    }
}
