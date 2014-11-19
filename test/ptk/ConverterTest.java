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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Admin
 */
public class ConverterTest {
    
    private Converter c;
    
    public ConverterTest() {
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
    public void testConverterCsvFileCorrectlyConverted() throws IOException {
        
        String[] expected = {
            "G36*",             // 0
            "X5000Y5000D02*",   // 1
            "Y10000D01*",       // 2
            "X10000D01*",       // 3
            "Y5000D01*",        // 4
            "X5000D01*",        // 5
            "G37*",             // 6
            "G36*",             // 7
            "X15000Y5000D02*",  // 8
            "Y10000D01*",       // 9
            "X20000D01*",       // 10
            "Y5000D01*",        // 11
            "X15000D01*",       // 12
            "G37*",             // 13
            "G36*",             // 14
            "X25000Y5000D02*",  // 15
            "Y10000D01*",       // 16
            "X30000D01*",       // 17
            "Y5000D01*",        // 18
            "X25000D01*",       // 19
            "G37*",             // 20
            "M02*",             // 21 (22 total)
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
            if (line.equals("%LPD*%"))
                break; // конец заголовка
        }
        int linesCount = expected.length;
        for (int index = 0; index < linesCount; index++) {
            if (!r.ready())
                fail("Unexdected end of file (Expecting \"" + expected[index] + "\" at index " + index + ")");
            assertEquals(expected[index], r.readLine());
        }
    }
    
    @Test
    public void testConverterCsvFileWithLayersCorrectlyConverted() throws IOException {
        
        String[] expected = {
            "G36*",             // 0
            "X5000Y2000D02*",   // 1
            "Y15000D01*",       // 2
            "X35000D01*",       // 3
            "Y2000D01*",        // 4
            "X5000D01*",        // 5
            "G37*",             // 6
            "%LPC*%",           // 7
            "G36*",             // 8
            "X15000Y5000D02*",  // 9
            "Y10000D01*",       // 10
            "X20000D01*",       // 11
            "Y5000D01*",        // 12
            "X15000D01*",       // 13
            "G37*",             // 14
            "G36*",             // 15
            "X25000Y5000D02*",  // 16
            "Y10000D01*",       // 17
            "X30000D01*",       // 18
            "Y5000D01*",        // 19
            "X25000D01*",       // 20
            "G37*",             // 21
            "M02*",             // 22 (23 total)
        };

        Gerber gerber = Gerber.getInstance();
        gerber.setNumberFormat(4, 3);
        gerber.setIsLayersFile(true);
        c.setCsvFileName("tableLayers.csv");
        c.convert();
        BufferedReader r = new BufferedReader(new FileReader("tableLayers.csv.gbr"));
        while(r.ready()) {
            String line = r.readLine();
            if (line.startsWith("%FS"))
                assertEquals("%FSLAX43Y43*%", line);
            if (line.equals("%LPD*%"))
                break; // конец заголовка
        }
        int linesCount = expected.length;
        for (int index = 0; index < linesCount; index++) {
            if (!r.ready())
                fail("Unexdected end of file (Expecting \"" + expected[index] + "\" at index " + index + ")");
            assertEquals(expected[index], r.readLine());
        }
    }
}
