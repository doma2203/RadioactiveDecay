package RadioactiveDecay;

/**
 * Klasa do tworzenia atomow o roznych parametrach
 */
public class Atom implements IAtom
{
    ChemicalElement info;
    Parameter parameter = new Parameter();
    Boolean undergone = false;

    /**
     * Tworzy atom pierwiastka okreslonego parametrem element
     * @param element rodzaj pierwiastka
     * @param parameterName nazwa parametru ktory podajemy (halfLife, meanLifeTime lub decayConstant)
     * @param parameterValue wartosc powyzszego parametru (halfLife podajemy w sekundach)
     * @see Parameter
     */
    public Atom(ChemicalElement element, String parameterName, Float parameterValue)
    {
        info = element;
        this.parameter.put(parameterName, parameterValue);
    }

    /**
     * Tworzy atom pierwiatka na podstawie innego atomu (konstruktor kopiujący)
     *
     * @param a atom pierwiastka
     */
    public Atom(Atom a) {
        info = a.info;
        this.parameter.put(a.parameter);
    }

    /**
     * zwraca liczba atomowa pierwiastka
     * @return liczba atomowa
     */
    public int getAtomicNumber()
    {
        return info.code;
    }

    /**
     * zwraca nazwe pierwiastka
     * @return nazwa pierwistka
     */
    public String getName()
    {
        return info.name();
    }

    /**
     * zwraca wartosc parametru (halfLife, meanLifeTime lub decayConstant)
     * @param parameterName nazwa parametru ktorego wartosc chcemy otrzymac (halfLife, meanLifeTime lub decayConstant)
     * @return wartosc parametru określonego argumentem parameterName
     * @see Parameter
     */
    public Float getParameterValue(String parameterName)
    {
        return parameter.get(parameterName);
    }

    /**
     * rozpad atomu - zmienia stanu atomu, nadaje polu undergone wartosc true
     */
    public void undergo()
    {
        undergone = true;
    }

    /**
     * zwraca informacje o stanie atomu (czy juz sie rozpadl)
     * @return wartosc pola undergone
     */
    public Boolean isUndergone()
    {
        return undergone;
    }
}
