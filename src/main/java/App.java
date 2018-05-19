import RadioactiveDecay.Atom;
import RadioactiveDecay.ChemicalElement;
import RadioactiveDecay.Experiment;
import RadioactiveDecay.Sample;

import static java.lang.Thread.sleep;

/***
 *Przykladowe uzycie klasy.
 */

public class App {
    public static void main(String[] args) {
        Atom atom = new Atom(ChemicalElement.Uran, "halflife", 32.0f);
        Sample sample = new Sample(22, new Atom(ChemicalElement.Uran, "halflife", 32.0f));
        Experiment ex = new Experiment(
                10, sample);
        ex.start();

        while (ex.getTime() < ex.getDuration()) {
            try {
                sleep(300L);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.printf("time:\t%d\nremaining particles:\t%d\nsurvive probability:\t%f\nradiological activity:\t%f\n", ex.getTime() / 1000, ex.getRemainingParticles(), ex.getSurviveProbability(), ex.getRadiologicalActivity());

            for (int i = 0; i < sample.size(); i++)
                System.out.print(ex.isUndergone(i) + " ");
            System.out.println("\n");
        }
    }
}