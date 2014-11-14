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
public class GerberTest {
    
    public GerberTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
    public void testGerberSetAndGetNumberFormat() {
        Gerber.setNumberFormat(4, 3);
        assertEquals("X43Y43", Gerber.getNumberFormatString());
    }
    
    @Test
    public void testGerberGetTrailZeros() {
        Gerber.setNumberFormat(4, 4);
        assertEquals("0000", Gerber.getTrailZeros());
    }
    
    @Test
    public void testGerberNumberFormatted() {
        Gerber.setNumberFormat(4, 2);
        assertEquals("500", Gerber.formatNumber("5"));
    }
    
}
