/**
 * Ian Leyden
 * Ryan Mirman
 * <p/>
 * The source is on github at https://github.com/Rsgm/hairy-octo-wallhack
 * <p/>
 * Copyright undlller the MIT license
 * http://opensource.org/licenses/MIT
 */
public class Sim {
    static double[] mtimeValues = {3, 2.5};
    static double[] vtimeValues = {6, 4};
    static double[] flowValues = {0.1, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0};
    private final double mtimeValue;
    private final double vtimeValue;
    private final double flow;
    private Queue<Car> arrivalQueue;
    LinkedQueue[] booths;

    public Sim(double mtimeValue, double vtimeValue, double flow, int booths) {
        this.mtimeValue = mtimeValue;
        this.vtimeValue = vtimeValue;
        this.flow = flow;
        this.booths = new LinkedQueue[booths];
        for (int i = 0; i < booths; i++) {
            this.booths[i] = new LinkedQueue<Car>(20);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < mtimeValues.length; i++) {
            for (double d : flowValues) {
                new Sim(mtimeValues[i], vtimeValues[i], d, 2).run();
            }
        }
    }

    private boolean run() {
        while(!arrivalQueue.empty()){

        }
        return false;
    }
}
