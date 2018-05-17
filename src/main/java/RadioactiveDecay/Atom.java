package RadioactiveDecay;

public class Atom implements IAtom {

    int AtomicNumber;
    String DisplayedName;
    Parameter parameter = new Parameter();
    Boolean Undergone = false;

    public Atom(int no, String name, String parameter_name, Float parameter_value) {
        this.AtomicNumber = no;
        this.DisplayedName = name;
        this.parameter.put(parameter_name, parameter_value);
    }

//    public Atom(Atom atom) {
//        try
//        {
//            this = atom.clone();
//        }
//        catch(CloneNotSupportedException e )
//        {
//
//        }
//    }

    public int getAtomicNumber()
    {
        return this.AtomicNumber;
    }

    public String getDisplayedName()
    {
        return DisplayedName;
    }

    public String getParameter()
    {
        return parameter.keySet().toString(); //TODO: Delete brackets in string!
    }

    public float get(String parameterName)
    {
        return parameter.get(parameterName);
    }

    public void Undergone()
    {
        Undergone = true;
    }

    public Boolean getState()
    {
        return Undergone;
    }
}
