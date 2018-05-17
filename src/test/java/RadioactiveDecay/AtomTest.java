package RadioactiveDecay;

import org.junit.Test;
import static org.junit.Assert.*;
public class AtomTest {

Atom atom = new Atom(ChemicalElement.Uran, "HalfLife",  2.23f);

    @Test
    public void getAtomicNumber() { assertEquals(235, atom.getAtomicNumber()); }

    @Test
    public void getName() { assertEquals("Uran", atom.getName()); }

    @Test
    public void getParameterName() { assertEquals("HalfLife", atom.getParameterName()); }

    @Test
    public void getParameterValue() { assertEquals(Float.valueOf(2.23f),atom.getParameterValue());}

    @Test
    public void Undergone() {
        assertEquals(false,atom.getState());
        atom.Undergone();
        assertEquals(true, atom.getState());
    }
}