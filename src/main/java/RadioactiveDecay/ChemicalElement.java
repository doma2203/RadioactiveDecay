package RadioactiveDecay;

/**
 * Typ wyliczeniowy okreslajacy rodzaj pierwiastka- jego nazwe i liczbe atomowa
 */
public enum ChemicalElement {
    Wegiel(14),
    Uran(235),
    Fosfor(32),
    Pluton(239),
    Kobalt(60),
    Wodor(3),
    Potas(40),
    Rad(226),
    Miedz(64),
    Wapn(45),
    Jod(131),
    Polon(210);
    final int code;

    /**
     * Konstruktor pozwalajacy na skojarzenie nazwy pierwiastka z jego liczba atomowa
     * @param code wybrana liczba atomowa
     */
    ChemicalElement(int code)
    {
        this.code = code;
    }
}
