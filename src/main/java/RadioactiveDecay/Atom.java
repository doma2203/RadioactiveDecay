package RadioactiveDecay;

import java.util.*;

 enum ChemicalElement {
    Uran(235),
    Rad(222),
    Polon(111); //TODO: Dodac pierwiastki!

    final int code;

    ChemicalElement(int code) {
        this.code = code;
    }
}


class Parameter extends HashMap<String, Float> {
    private Boolean isCorrectKey(String key, String pattern) {
        return (key.compareToIgnoreCase(pattern) == 0);
    }

    @Override
    public Float put(String key, Float value) {
        if (this.size() < 1 || isCorrectKey(key, "halflife") || isCorrectKey(key, "meanlifetime") || isCorrectKey(key, "decayconstant")) {
            return super.put(key, value);
        } else
            throw new IllegalArgumentException("Niepoprawny argument!");
    }
}

public class Atom implements IAtom {

    ChemicalElement Info;
    Parameter Parameter = new Parameter();
    Boolean Undergone = false;

    public Atom(ChemicalElement no, String parameter_name, Float parameter_value) {
        Info = no;
        this.Parameter.put(parameter_name, parameter_value);
    }

    public int getAtomicNumber() {
        return Info.code;
    }

    public String getName() {
        return Info.name();
    }

    public String getParameterName() {
        return Parameter.keySet().toArray()[0].toString();
    }

    public Float getParameterValue() {
        return Parameter.get(getParameterName());

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
