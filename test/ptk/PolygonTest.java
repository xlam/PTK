/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptk;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class PolygonTest {
    
    public PolygonTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Point.setZeros("000");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testPolygon01() {
        Polygon p = new Polygon();
        p.addPoint(new Point("5", "5"));
        p.addPoint(new Point("5", "10"));
        p.addPoint(new Point("10", "10"));
        p.addPoint(new Point("10", "5"));
        assertEquals(4, p.countPoints());
        String expected = ""
                + "G36*\n"
                + "X5000Y5000D02*\n"
                + "Y10000D01*\n"
                + "X10000D01*\n"
                + "Y5000D01*\n"
                + "X5000D01*\n"
                + "G37*\n";
        assertEquals(expected, p.toGerber());
    }
    
    @Test
    public void testPolygon02() {
        Polygon p = new Polygon();
        p.addPoint(new Point("5", "5"));
        p.addPoint(new Point("5", "10"));
        p.addPoint(new Point("5", "10"));
        assertEquals(3, p.countPoints());
    }
}
