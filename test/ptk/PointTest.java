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
public class PointTest {
    
    public PointTest() {
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
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testPointPointsCreatedAndChecked() {
        Point p = new Point("123", "456");
        assertEquals("X123000Y456000", p.toGerber());
        p = new Point("", "456");
        assertEquals("Y456000", p.toGerber());
        p = new Point("123", "");
        assertEquals("X123000", p.toGerber());
        p = new Point("", "");
        assertEquals("", p.toGerber());
        p = new Point();
        assertEquals("", p.toGerber());
    }
    
    @Test
    public void testPointEqualityChecked() {
        Point p1 = new Point("5", "5");
        Point p2 = new Point("5", "5");
        Point p3 = new Point("5", "10");
        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));
    }
}
