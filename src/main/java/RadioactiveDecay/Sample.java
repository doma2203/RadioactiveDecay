package RadioactiveDecay;

public class Sample
{
    private Atom[] atoms;       //tablica atomów

    public Sample(int size, ChemicalElement no, String parameter_name, Float parameter_value)
    {
        atoms = new Atom[size];

        //create atoms
        for (int i =0; i< size; i++)
        {
            atoms[i] = new Atom(no, parameter_name, parameter_value);
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

    public int size()
    {
        return atoms.length;
    }

    //zwraca wartości parametrów (czas połowicznego rozpadu, stała rozpadu lub średni czas życia cząstki)
    public float get(String parameterName)
    {
        return atoms[0].parameter.get(parameterName);
    }

    //stan atomu o danym indeksie w tablicy - czy już się rozpadł
    public boolean isUndergone(int index)
    {
        return atoms[index].isUndergone();
    }

    //rozpad atomu o danym indeksie w tablicy - zmiana jego stanu na undergone
    void undergo(int index)
    {
        atoms[index].undergo();
    }
}
