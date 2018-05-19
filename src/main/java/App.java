import RadioactiveDecay.Atom;
import RadioactiveDecay.ChemicalElement;
import RadioactiveDecay.Experiment;
import RadioactiveDecay.Sample;

import static java.lang.Thread.sleep;

public class App 
{
    public static void main( String[] args )
    {
        Atom atom = new Atom(ChemicalElement.Uran, "halflife", (float) 32);
        Sample sample = new Sample(22, ChemicalElement.Uran, "halfLife", (float) 32);
        Experiment ex = new Experiment(
                4, 22, ChemicalElement.Uran, "halfLife", (float) 2);
        ex.start();

        while (ex.getTime() < ex.getDuration())
        {
            try
            {
                sleep((long) (300));
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println("time: " + ex.getTime() / 1000);
            System.out.println("remaining particles: " + ex.getRemainingParticles());
            System.out.println("survive probability: " + ex.getSurviveProbability());
            System.out.println("radiological activity: " + ex.getRadiologicalActivity());
            for (int i = 0; i < sample.size(); i++)
            {
                System.out.print(ex.isUndergone(i) + " ");
            }
            System.out.println("\n");
        }
    }
}