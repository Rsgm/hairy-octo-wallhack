/**
 * Created by Ian_Leyden on 4/25/2015.
 */
public class Car {
    Stages stage;
    double arrivalTime;
    double processTime;

    public Car(ExpDistribution nextCarArrive, NormalDistribution service) {
        stage = Stages.ARRIVE;
        arrivalTime = nextCarArrive.next();
        processTime = service.sample();
    }

    public enum Stages{
        ARRIVE, PROCESS;
    }
}
