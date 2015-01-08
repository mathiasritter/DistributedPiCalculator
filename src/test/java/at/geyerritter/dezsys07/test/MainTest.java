package at.geyerritter.dezsys07.test;

import at.geyerritter.dezsys07.Main;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testen der Kommandoverarbeitung des Calculators
 *
 * @author sgeyer
 * @author mritter
 * @version 1.0
 */
public class MainTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testParseArgs1() {
        String[] args = {"bla"};
        assertFalse(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs2() {
        String[] args = {""};
        assertFalse(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs3() {
        String[] args = {"-h", "127.0.0.1"};
        assertFalse(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs4() {
        String[] args = {"-p", "5434"};
        assertFalse(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs5() {
        String[] args = {"-t", "Bla"};
        assertFalse(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs6() {
        String[] args = {"-t", "Balancer"};
        assertTrue(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs7() {
        String[] args = {"-t", "Server"};
        assertTrue(Main.parseArgs(args));
    }

    @Test
     public void testParseArgs8() {
        String[] args = {"-t", "Client"};
        assertTrue(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs9() {
        String[] args = {"-t", "Client", "-h", "127.0.0.1"};
        assertTrue(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs10() {
        String[] args = {"-t", "Client", "-h", "127.0.0"};
        assertFalse(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs11() {
        String[] args = {"-t", "Client", "-p", "-5"};
        assertFalse(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs12() {
        String[] args = {"-t", "Client", "-p", "435345"};
        assertFalse(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs13() {
        String[] args = {"-t", "Client", "-p", "4534"};
        assertTrue(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs14() {
        String[] args = {"-t", "Client", "-h", "127.0.0.1", "-p", "4534"};
        assertTrue(Main.parseArgs(args));
    }

    @Test
    public void testParseArgs15() {
        String[] args = {"-t", "Client", "-h", "bla", "-p", "4534"};
        assertFalse(Main.parseArgs(args));
    }
    @Test
    public void testParseArgs16() {
        String[] args = {"-t", "Client", "-h", "127.0.0.1", "-p", "sdf"};
        assertFalse(Main.parseArgs(args));
    }
}