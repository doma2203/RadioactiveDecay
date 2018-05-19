package RadioactiveDecay;

import java.util.Date;
import java.util.Random;

/**
 * klasa do tworzenia eksperymentu
 */
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


    /**
     *
     * @param duration czas trwania eksperymentu w sekundach
     * @param size wielkosc probki
     * @param element rodzaj pierwiastka
     * @param parameterName nazwa parametru ktory podajemy (halfLife, meanLifeTime lub decayConstant)
     * @param parameterValue wartosc powyzszego parametru (halfLife podajemy w sekundach)
     */
    public Experiment(long duration, int size, ChemicalElement element, String parameterName, Float parameterValue)
    {
        this.sample = new Sample(size, element, parameterName, parameterValue);
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

    /**
     * Wylicza ile czastek nie uległo rozkladowi.
     * @param t czas od startu eksperymentu
     * @return ile czastek nie uleglo rozpadowi
     */
    public int remainingParticles(double t)
    {
        int howMany = N;                                    //w homMany obliczam ile ma sie rozpaść
        N = (int) (N0 * Math.pow( 0.5, t/1000/halfLife ));  // t/1000 bo zmieniamy na sekundy
        howMany -= N;
        undergo(howMany);                                   //rozpad cząstek w próbce
        return N;
    }

    /**
     * Wylicza aktualne prawdopodobieństwo przeżycia cząstki.
     * @param t czas od startu eksperymentu
     * @return aktualne prawdopodobieństwo przeżycia cząstki
     */
    public double surviveProbability(double t)
    {
        P = Math.pow( Math.E, -decayConstant*t/1000);
        return P;
    }

    /**
     * Wylicza aktualna aktywnosc promieniotworcza probki.
     * @param t czas od startu eksperymentu
     * @return aktualna aktywnosc promieniotworcza probki
     */
    public double radiologicalActivity(double t)
    {
        A = decayConstant * N0 *  Math.pow( Math.E, -decayConstant*t/1000);
        return A;
    }

    /**
     * W czasie trwania eksperyentu nastepuje ciagle wyliczanie parametrow:
     * aktualna aktywnosc promieniotworcza probki,
     * aktualne prawdopodobieństwo przeżycia cząstki,
     * ile czastek nie uleglo rozpadowi.
     */
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

    /**
     *
     * @return ilosc czastek ktora nie ulegla rozpadowi
     */
    public int getRemainingParticles()
    {
        return N;
    }

    /**
     *
     * @return aktualne prawdopodobieństwo przeżycia cząstki
     */
    public double getSurviveProbability()
    {
        return P;
    }

    /**
     *
     * @return aktualna aktywnosc promieniotworcza probki
     */
    public double getRadiologicalActivity()
    {
        return A;
    }

    /**
     *
     * @return czas od poczatku eksperymentu w milisekundach
     */
    public long getTime()
    {
        return t;
    }

    /**
     *
     * @return czas trwania eksperymentu w milisekundach
     */
    public long getDuration()
    {
        return duration;
    }

    /**
     * zwraca stan atomu o danym indeksie (czy juz ulegl rozpadowi)
     * @param index indeks atomu w probce
     * @return stan atomu czy juz ulegl rozpadowi)
     */
    public boolean isUndergone(int index)
    {
        return sample.isUndergone(index);
    }

    /**
     * rozpad czasteczek w probce
     * @param howMany ile czastek ma sie rozpasc
     */
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
