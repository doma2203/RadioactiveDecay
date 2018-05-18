package RadioactiveDecay;

public class Atom implements IAtom
{
    ChemicalElement info;                   //nazwa pierwiastka i liczba atomowa
    Parameter parameter = new Parameter();  //mapa na parametry: czas połowicznego rozpadu, stała rozpadu i średni czas życia cząstki
    Boolean undergone = false;              //stan atomu (czy już się rozpadł)

    public Atom(ChemicalElement no, String parameter_name, Float parameter_value)
    {
        info = no;
        this.parameter.put(parameter_name, parameter_value);
    }

    //liczba atomowa
    public int getAtomicNumber()
    {
        return info.code;
    }

    //nazwa pierwiastka
    public String getName()
    {
        return info.name();
    }

    //pobiera wartość: czas połowicznego rozpadu, stała rozpadu lub średni czas życia cząstki
    public Float getParameterValue(String parameterName)
    {
        return parameter.get(parameterName);
    }

    //rozpad - zmiana stanu atomu
    public void undergo()
    {
        undergone = true;
    }

    //stan atomu - czy już się rozpadł
    public Boolean isUndergone()
    {
        return undergone;
    }
}
