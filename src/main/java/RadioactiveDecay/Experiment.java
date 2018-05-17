package RadioactiveDecay;
import java.util.*;

public class Experiment extends Thread
{
    private Sample sample;
    private Date t0;
    private long duration;
    private int N0;
    private int N;
    private float P;
    private float A;

    Experiment(long duration, int size, int no, String name, String parameter_name, Float parameter_value)
    {
        this.sample = new Sample(size, no, name, parameter_name, parameter_value);
        this.t0= new Date();
        this.duration = duration*1000; //bylo w sekundach, zmieniam na milisekundy
        this.N0 = size;
    }

//    Experiment(long duration, Sample sample)
//    {
//
//    }
//    Experiment(long duration, int size, Atom atom)
//    {
//
//    }

    int remainingParticles()
    {
        float halfLife = sample.get("halfLife");
        float t = (new Date()).getTime() - t0.getTime();
        N = (int) (N0 * Math.pow( 0.5, (t/halfLife/1000) ));
        //System.out.println("pow: " + Math.pow( 0.5, (t/halfLife/1000)));
        return N;
    }

    float surviveProbability()
    {
        P = (float)N/N0;
        return P;
    }

    float radiologicalActivity()
    {
        A = sample.get("decayConstant") * N;
        return A;
    }

    @Override
    public void run()
    {
        float t = (new Date()).getTime() - t0.getTime();
        while ( t <= duration)
        {
            try {
                sleep(1000);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println("time: " + t/1000);
            System.out.println("remaining particles: " + remainingParticles());
            System.out.println("survive probability: " + surviveProbability());
            System.out.println("radiological activity: " + radiologicalActivity());
            System.out.println();
            t = (new Date()).getTime() - t0.getTime();

        }
    }

}
