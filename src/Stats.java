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
    double total;
    double ezPass;
    double maxWaiting;
    double totalTime;
    double nonEzPass;
    double nonEzTime;

    public void totalCars() {
        total++;
    }

    public void ezTotal() {
        ezPass++;
    }

    public double nonEzTotal() {
        nonEzPass = total - ezPass;
        return nonEzTime / nonEzPass;
    }

    /**
     * Add to the sum of non ez process times1
     *
     * @param time
     */
    public void nEzProcess(double time) {
        nonEzTime += time;
    }

    public void waiting(int waiting) {
        if (waiting > maxWaiting) {
            maxWaiting = waiting;
        }
    }

    public void totalTime(double time) {
        totalTime += time;
    }

    public double averageTime() {
        return totalTime / total;
    }
}
