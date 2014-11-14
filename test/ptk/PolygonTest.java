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
    
    private Polygon     p;
    private String      expected;
    
    public PolygonTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Gerber.setNumberFormat(4, 3);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        prepareTestData();
    }

    private void prepareTestData() {
        p = new Polygon();
        Point[] points = {
            new Point("5",  "5"),
            new Point("5",  "10"),
            new Point("10", "10"),
            new Point("10", "5")
        };
        addPointsTo(p, points);
        expected = ""
                + "G36*\n"
                + "X5000Y5000D02*\n"
                + "Y10000D01*\n"
                + "X10000D01*\n"
                + "Y5000D01*\n"
                + "X5000D01*\n"
                + "G37*\n";
    }
    
    @After
    public void tearDown() {
    }
    
    private void addPointsTo(Polygon p, Point[] points) {
        for (Point point: points)
            p.addPoint(point);
    }
    
    @Test
    public void testPolygonGerberStringGenerated() {
        assertEquals(4, p.countPoints());
        assertEquals(expected, p.toGerber());
    }
    
    @Test
    public void testPolygonPointsCounted() {
        p.addPoint(new Point("0", "0"));
        assertEquals(5, p.countPoints());
    }
    
    @Test
    public void testPolygonLastPointRemoved() {
        p.removeLast();
        assertEquals(3, p.countPoints());
    }
}
