package RadioactiveDecay;

/**
 * Typ wyliczeniowy okreslajacy rodzaj pierwiastka- jego nazwe i liczbe atomowa
 */
public enum ChemicalElement {
    Uran(235),
    Rad(222),
    Polon(111); //TODO: Dodac pierwiastki!

    //liczba atomowa
    final int code;

    /**
     *
     * @param code liczba atomowa
     */
    ChemicalElement(int code)
    {
        this.code = code;
    }
}
