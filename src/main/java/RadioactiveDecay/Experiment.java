package RadioactiveDecay;

import java.util.Date;
import java.util.Random;

public class Experiment extends Thread
{
    private Sample sample;          //próbka
    private Date t0;                //czas rozpoczęcia
    private long t;                 //aktualny czas w milisekundach
    private long duration;          //czas trwania w milisekundach
    private int N0;                 //początkowa liczba atomów
    private int N;                  //aktualna liczba atomów, które nie uległy rozpadowi
    private double P;               //aktualne prawdopodobieństwo przeżycia cząstki
    private double A;               //aktualna aktywność promieniotwórcza próbki
    private float decayConstant;    //stała rozpadu
    private float halfLife;         //czas połowicznego rozpadu


    public Experiment(long duration, int size, ChemicalElement no, String parameter_name, Float parameter_value)
    {
        this.sample = new Sample(size, no, parameter_name, parameter_value);
        this.t0= new Date();
        this.duration = duration*1000; //bylo w sekundach, zmieniam na milisekundy
        this.N0 = size;
        this.N = size;
        this.halfLife = sample.get("halfLife");
        this.decayConstant = sample.get("decayConstant");
    }

//    Experiment(long duration, Sample sample)
//    {
//
//    }
//    Experiment(long duration, int size, Atom atom)
//    {
//
//    }

    //wylicza ile cząstek nie uległo rozkładowi
    public int remainingParticles(double t)
    {
        int howMany = N;                                    //w homMany obliczam ile ma sie rozpaść
        N = (int) (N0 * Math.pow( 0.5, t/1000/halfLife ));  // t/1000 bo zmieniamy na sekundy
        howMany -= N;
        undergo(howMany);                                   //rozpad cząstek w próbce
        return N;
    }

    //wylicza aktualne prawdopodobieństwo przeżycia cząstki
    public double surviveProbability(double t)
    {
        P = Math.pow( Math.E, -decayConstant*t/1000);
        return P;
    }

    //wylicza aktualną aktywność promieniotwórczą próbki
    public double radiologicalActivity(double t)
    {
        A = decayConstant * N0 *  Math.pow( Math.E, -decayConstant*t/1000);
        return A;
    }

    //ciągłe wyliczanie parametrów
    @Override
    public void run()
    {
        t = (new Date()).getTime() - t0.getTime();   //aktualny czas
        while ( t <= duration)
        {
            remainingParticles(t);
            surviveProbability(t);
            radiologicalActivity(t);
            t = (new Date()).getTime() - t0.getTime();
        }
    }

    public int getRemainingParticles()
    {
        return N;
    }

    public double getSurviveProbability()
    {
        return P;
    }

    public double getRadiologicalActivity()
    {
        return A;
    }

    public long getTime()
    {
        return t;
    }

    public long getDuration()
    {
        return duration;
    }

    public boolean isUndergone(int index)
    {
        return sample.isUndergone(index);
    }

    //rozpad czasteczek w probce
    private void undergo(int howMany)
    {
        int index;
        for (int i = 0; i < howMany; i++)
        {
            //przez losowanie indeksu szukam atomu ktory jeszcze sie nie rozpadl
            do {
                index = (new Random()).nextInt(sample.size());
            } while(sample.isUndergone(index) == true);

            //gdy znajdę nastepuje rozpad czasteczki
            sample.undergo(index);
        }
    }
}
