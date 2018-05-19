package RadioactiveDecay;

/**
 * Interfejs atomu opisujacy pierwiastek chemiczny kazdego atomu.
 */

public interface IAtom {
    ChemicalElement Info = null;

    /**
     * Zwraca liczbę atomową atomu
     *
     * @return zwraca liczbe atomowa atomu pierwiastka
     */
    int getAtomicNumber();

    /** Zwraca nazwe pierwiastka atomu
     * @return zwraca nazwe systematyczna pierwiastka
     */
    String getName();
}
