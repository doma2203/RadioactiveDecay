package RadioactiveDecay;

import java.util.*;


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

    int AtomicNumber;
    String DisplayedName;
    Parameter Parameter = new Parameter();
    Boolean Undergone = false;

    public Atom(int no, String name, String parameter_name, Float parameter_value) {
        this.AtomicNumber = no;
        this.DisplayedName = name;
        this.Parameter.put(parameter_name, parameter_value);
    }

    public int getAtomicNumber() {

        return this.AtomicNumber;
    }

    public String getDisplayedName() {
        return DisplayedName;
    }

    public String getParameter() {
        return Parameter.keySet().toString(); //TODO: Delete brackets in string!
    }

    public void Undergone() {
        Undergone = true;
    }

    public Boolean getState() {
        return Undergone;
    }
}
