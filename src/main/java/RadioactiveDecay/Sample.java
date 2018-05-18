package RadioactiveDecay;

/**
 * Klasa do tworzenia probek (zbioru atomow) do eksperymentu.
 */
public class Sample
{
    private Atom[] atoms;       //tablica atomów

    /**
     * Tworzy probke (zbior atomow) do eksperymentu.
     * @param size ile atomow w probce
     * @param element rodzaj pierwiastka
     * @param parameterName nazwa parametru ktory podajemy (halfLife, meanLifeTime lub decayConstant)
     * @param parameterValue wartosc powyzszego parametru (halfLife podajemy w sekundach)
     * @see Parameter
     * @see ChemicalElement
     */
    public Sample(int size, ChemicalElement element, String parameterName, Float parameterValue)
    {
        atoms = new Atom[size];

        //create atoms
        for (int i =0; i< size; i++)
        {
            atoms[i] = new Atom(element, parameterName, parameterValue);
        }
    }

//    Sample(int size, Atom atom)
//    {
//        atoms = new Atom[size];
//
//        //create atoms
//        for (int i =0; i< size; i++)
//        {
//            atoms[i] = new Atom(atom);
//        }
//    }

    /**
     *
     * @return ilosc atomow w probce
     */
    public int size()
    {
        return atoms.length;
    }

    /**
     * zwraca wartosci parametrow (czas połowicznego rozpadu, stała rozpadu lub średni czas życia cząstki)
     * @param parameterName nazwa parametru ktorego wartosc chcemy otrzymac (halfLife, meanLifeTime lub decayConstant)
     * @return wartosc powyzszego parametru (halfLife zwracane jest w sekundach)
     */
    public float get(String parameterName)
    {
        return atoms[0].parameter.get(parameterName);
    }

    //

    /**
     *
     * @param index indeks atomu w kolekcji
     * @return stan danego atomu - czy już się rozpadł
     */
    public boolean isUndergone(int index)
    {
        return atoms[index].isUndergone();
    }

    /**
     * rozpad atomu - zmienia stanu atomu o danym indeksie- nadaje polu undergone wartosc true
     */
    void undergo(int index)
    {
        atoms[index].undergo();
    }
}
