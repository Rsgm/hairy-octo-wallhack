/**
 * Ian Leyden
 * Ryan Mirman
 * <p/>
 * The source is on github at https://github.com/Rsgm/hairy-octo-wallhack
 * <p/>
 * Copyright under the MIT license
 * http://opensource.org/licenses/MIT
 */
public class Stats {
    int totalCars; // holds the total number of cars to pass through
    int ezPassTotal; // number of ez pass users
    int maxCarsWaiting; // mst amount of cars waiting
    double totalTime; // total time spent by all cars
    double nonEzTime; // total time spent by all non-ez pass users

    /**
     * Increment the total amount of cars by one.
     */
    public void incrementTotalCars() {
        totalCars++;
    }

    /**
     * Increment the total amount of ez pass users by one.
     */
    public void incrementEzTotal() {
        ezPassTotal++;
    }

    /**
     * Calculates the average time it takes for non-ez pass users to finish.
     *
     * @return the average time
     */
    public double nonEzTotal() {
        return nonEzTime / (double) (totalCars - ezPassTotal);
    }

    /**
     * Add to the sum of non ez process times1
     *
     * @param time amount of time a non-ez pass user took
     */
    public void nonEzProcessTime(double time) {
        nonEzTime += time;
    }

    /**
     * Sets the amount of most cars waiting if there is more than the last.
     *
     * @param waiting number of cars in each queue
     */
    public void waiting(int waiting) {
        if (waiting > maxCarsWaiting) {
            maxCarsWaiting = waiting;
        }
    }

    /**
     * Increment the total time by the given amount.
     *
     * @param time amount to increment by
     */
    public void incrementTotalTime(double time) {
        totalTime += time;
    }

    /**
     * Calculates the average time spent by a car.
     *
     * @return the average time
     */
    public double averageTime() {
        return totalTime / totalCars;
    }
}
