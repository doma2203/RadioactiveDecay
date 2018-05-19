package RadioactiveDecay;
/**
 * Testy jednostkowe klasy Atom, w czasie oddania klasa przeszla wszystkie :)
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class AtomTest {

Atom atom = new Atom(ChemicalElement.Uran, "halfLife",  2.23f);

    @Test
    public void getAtomicNumber() { assertEquals(235, atom.getAtomicNumber()); }

    @Test
    public void getName() { assertEquals("Uran", atom.getName()); }

    @Test
    public void getParameterValue() { assertEquals(Float.valueOf(2.23f),atom.getParameterValue("halfLife"));}

    @Test
    public void Undergone() {
        assertEquals(false,atom.isUndergone());
        atom.undergo();
        assertEquals(true, atom.isUndergone());
    }
}