package RadioactiveDecay;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Atom atom = new Atom(ChemicalElement.Uran, "halflife", (float)32);
        Sample sample = new Sample( 22, ChemicalElement.Uran,"halfLife", (float)32);
        Experiment ex = new Experiment(
                4, 22, ChemicalElement.Uran, "halfLife", (float)2);
        ex.start();
    }
}