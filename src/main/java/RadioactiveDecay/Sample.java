package RadioactiveDecay;

public class Sample
{
    Atom[] sample;


    Sample(int size, int no, String name, String parameter_name, Float parameter_value)
    {
        sample = new Atom[size];

        //create atoms
        for (int i =0; i< size; i++)
        {
            sample[i] = new Atom(no, name, parameter_name, parameter_value);
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

    int size()
    {
        return sample.length;
    }

    public float get(String parameterName)
    {
        return sample[0].parameter.get(parameterName);
    }


}
