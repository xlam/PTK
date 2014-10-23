/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
public class ConverterTest {
    
    private static Converter c;
    
    public ConverterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        c = new Converter();
    }
    
    @AfterClass
    public static void tearDownClass() {
        c = null;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testCountDelimeters() {
        assertEquals(3, c.countDelimeters("А;1;25000;50000"));
        assertEquals(2, c.countDelimeters("А;1;25000"));
        assertEquals(1, c.countDelimeters(";"));
        assertEquals(0, c.countDelimeters(""));
    }
    
    @Test
    public void testParseLine() {
        String[] arr;
        arr = new String[] {"А", "1", "25000", "50000"};
        assertArrayEquals(arr, c.parseLine("А;1;25000;50000"));
        arr = new String[] {"", "1", "25000", "50000"};
        assertArrayEquals(arr, c.parseLine(";1;25000;50000"));
        arr = new String[] {"", "1", "", "50000"};
        assertArrayEquals(arr, c.parseLine(";1;;50000"));
        arr = new String[] {"", "1", "25000", ""};
        assertArrayEquals(arr, c.parseLine(";1;25000;"));
        arr = new String[] {"", "", "", ""};
        assertArrayEquals(arr, c.parseLine(";;;"));
    }
    
    @Test
    public void testCreateTockens() {
        String[] arr;
        // this must be the first test assert
        arr = new String[] {"А", "1", "5", "3"};
        assertEquals("G36*\nX5000Y3000D02*\n", c.CreateTockens(arr));
        // this must be the second test assert
        arr = new String[] {"А", "1", "5", "3"};
        assertEquals("G37*\nG36*\nX5000Y3000D02*\n", c.CreateTockens(arr));
        arr = new String[] {"", "1", "5", "3"};
        assertEquals("X5000Y3000D01*\n", c.CreateTockens(arr));
        arr = new String[] {"", "1", "5", ""};
        assertEquals("X5000D01*\n", c.CreateTockens(arr));
        arr = new String[] {"", "1", "", "3"};
        assertEquals("Y3000D01*\n", c.CreateTockens(arr));
        arr = new String[] {"", "1", "", ""};
        assertEquals("D01*\n", c.CreateTockens(arr));
    }
    

}
