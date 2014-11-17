/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptk;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Admin
 */
public class ConverterTest {
    
    private Converter c;
    
    public ConverterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        c = new Converter();
    }
    
    @After
    public void tearDown() {
        c = null;
    }
    
    @Test
    public void testConverterDelimetersCounted() {
        assertEquals(3, c.countDelimeters("А;1;25000;50000"));
        assertEquals(2, c.countDelimeters("А;1;25000"));
        assertEquals(1, c.countDelimeters(";"));
        assertEquals(0, c.countDelimeters(""));
    }
}
