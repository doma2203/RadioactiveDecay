package RadioactiveDecay;

public class Sample
{
    private Atom[] sample;

    Sample(int size, ChemicalElement no, String parameter_name, Float parameter_value)
    {
        sample = new Atom[size];

        //create atoms
        for (int i =0; i< size; i++)
        {
            sample[i] = new Atom(no, parameter_name, parameter_value);
        }
    }

//    Sample(int size, Atom atom)
//    {
//        sample = new Atom[size];
//
//        //create atoms
//        for (int i =0; i< size; i++)
//        {
//            sample[i] = new Atom(atom);
//        }
//    }

    public int size()
    {
        return sample.length;
    }

    public float get(String parameterName)
    {
        return sample[0].Parameter.get(parameterName);
    }

    public boolean getState(int index)
    {
        return sample[index].getState();
    }

    void Undergo(int index)
    {
        sample[index].Undergone();
    }
}
