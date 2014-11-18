/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    
    @Test
    public void testCsvFileCorrectlyConverted() throws IOException {
        
        String[] expected = {
            "G36*",
            "X5000Y5000D02*",
            "Y10000D01*",
            "X10000D01*",
            "Y5000D01*",
            "X5000D01*",
            "G37*",
            "G36*",
            "X15000Y5000D02*",
            "Y10000D01*",
            "X20000D01*",
            "Y5000D01*",
            "X15000D01*",
            "G37*",
            "G36*",
            "X25000Y5000D02*",
            "Y10000D01*",
            "X30000D01*",
            "Y5000D01*",
            "X25000D01*",
            "G37*",
        };

        Gerber gerber = Gerber.getInstance();
        gerber.setNumberFormat(4, 3);
        c.setCsvFileName("table.csv");
        c.convert();
        BufferedReader r = new BufferedReader(new FileReader("table.csv.gbr"));
        while(r.ready()) {
            String line = r.readLine();
            if (line.startsWith("%FS"))
                assertEquals("%FSLAX43Y43*%", line);
            if (line.equals("%LPD*%")) break; // конец заголовка
        }
        int index = 0;
        while(r.ready())
            assertEquals(expected[index++], r.readLine());
        r.close();
    }
}
