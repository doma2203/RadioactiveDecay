package RadioactiveDecay;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Atom atom = new Atom(32, "Oxygen", "halflife", (float)32);
        Sample sample = new Sample(22, 32, "Oxygen", "halfLife", (float)32);
        Experiment ex = new Experiment(
                4, 22, 32, "Oxygen", "halfLife", (float)2);
        ex.start();
    }
}