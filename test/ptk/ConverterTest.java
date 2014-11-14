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
    
    private ArrayList<String> csvData = new ArrayList<>();
    
    private String expected;
    
    public ConverterTest() {
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
        c = new Converter();
        
        csvData.add("A;1;5;5");
        csvData.add(";2;;10");
        csvData.add(";3;10;");
        csvData.add(";4;;5");
        csvData.add(";5;5;");
        csvData.add("Б;1;15;5");
        csvData.add(";2;;10");
        csvData.add(";3;20;");
        csvData.add(";4;;5");
        csvData.add(";5;15;");
        csvData.add("A;1;25;5");
        csvData.add(";2;;10");
        csvData.add(";3;30;");
        csvData.add(";4;;5");
        csvData.add(";5;25;");
        
        c.setCsvData(csvData);
        
        expected = 
              "G36*\n"
            + "X5000Y5000D02*\n"
            + "Y10000D01*\n"
            + "X10000D01*\n"
            + "Y5000D01*\n"
            + "X5000D01*\n"
            + "G37*\n"
            + "G36*\n"
            + "X15000Y5000D02*\n"
            + "Y10000D01*\n"
            + "X20000D01*\n"
            + "Y5000D01*\n"
            + "X15000D01*\n"
            + "G37*\n"
            + "G36*\n"
            + "X25000Y5000D02*\n"
            + "Y10000D01*\n"
            + "X30000D01*\n"
            + "Y5000D01*\n"
            + "X25000D01*\n"
            + "G37*\n";
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

    @Test
    public void testConverterConvertedThreePolygons() {
        c.convertCsvDataToPolygons();
        assertEquals(3, c.getPolygonsCount());
    }
    
    @Test
    public void testConverterCsvDataConvertedToGerber() {
        c.convertCsvDataToPolygons();
        assertEquals(expected, c.toGerber());
    }
}
