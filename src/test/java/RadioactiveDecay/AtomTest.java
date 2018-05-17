package RadioactiveDecay;

import org.junit.Test;

import static org.junit.Assert.*;

public class AtomTest {

    Atom atom = new Atom(1, "Wodór", "HalfLife", 2.23f);

    @Test
    public void getAtomicNumber() {
        assertEquals(1, atom.getAtomicNumber());
    }

    @Test
    public void getDisplayedName() {
        assertEquals("Wodór", atom.getDisplayedName());
    }

    @Test
    public void getParameter() {
        assertEquals("HalfLife", atom.getParameter());
    }

    @Test
    public void Undergone() {
        atom.Undergone();
        assertEquals(true, atom.getState());
    }
}