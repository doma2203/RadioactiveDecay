package RadioactiveDecay;

import java.util.Date;
import java.util.Random;

public class Experiment extends Thread
{
    private Sample sample;
    private Date t0;
    private long duration;
    private int N0;
    private int N;
    private double P;
    private double A;
    private float decayConstant;
    private float halfLife;

    Experiment(long duration, int size, ChemicalElement no, String parameter_name, Float parameter_value)
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

    public int remainingParticles(double t)
    {
        // t/1000 bo zmieniamy na sekundy
        //w homMany obliczam ile ma sie rozpaść
        int howMany = N;
        N = (int) (N0 * Math.pow( 0.5, t/1000/halfLife ));
        howMany -= N;
        Undergo(howMany);
        return N;
    }

    public double surviveProbability(double t)
    {
        P = Math.pow( Math.E, -decayConstant*t/1000);
        return P;
    }

    public double radiologicalActivity(double t)
    {
        A = decayConstant * N0 *  Math.pow( Math.E, -decayConstant*t/1000);
        return A;
    }

    @Override
    public void run()
    {
        double t = (new Date()).getTime() - t0.getTime();
        while ( t <= duration)
        {
            try {
                sleep( (long)(halfLife*1000/4) );
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println("time: " + t/1000);
            System.out.println("remaining particles: " + remainingParticles(t));
            System.out.println("survive probability: " + surviveProbability(t));
            System.out.println("radiological activity: " + radiologicalActivity(t));
            for(int i =0; i<sample.size(); i++)
            {
                System.out.print(sample.getState(i)+" ");
            }
            System.out.println();
            System.out.println();
            t = (new Date()).getTime() - t0.getTime();
        }
    }

    public int getN()
    {
        return N;
    }

    public double getP()
    {
        return P;
    }

    public double getA()
    {
        return A;
    }

    //rozpad czasteczek w probce
    private void Undergo(int howMany)
    {
        int index;
        for (int i = 0; i < howMany; i++)
        {
            //przez losowanie indeksu szukam atomu ktory jeszcze sie nie rozpadl
            do {
                index = (new Random()).nextInt(sample.size());
            } while(sample.getState(index) == true);

            //gdy znajdę nastepuje rozpad czasteczki
            sample.Undergo(index);
        }
    }
}
