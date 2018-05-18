package RadioactiveDecay;

public enum ChemicalElement {
    Uran(235),
    Rad(222),
    Polon(111); //TODO: Dodac pierwiastki!

    //liczba atomowa
    final int code;

    ChemicalElement(int code)
    {
        this.code = code;
    }
}
