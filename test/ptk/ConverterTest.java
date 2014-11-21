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
 * @author Sergey
 */
public class ConverterTest {
    
    private Converter converter;
    
    private Gerber gerber;
    
    public ConverterTest() {
    }
    
    @Before
    public void setUp() {
        gerber = Gerber.getInstance();
        gerber.setNumberFormat(4, 3);
        converter = new Converter("notexistingfile");
    }
    
    @After
    public void tearDown() {
        converter = null;
    }
    
    @Test
    public void testConverterDelimetersCounted() {
        assertEquals(3, converter.countDelimeters("А;1;25000;50000"));
        assertEquals(2, converter.countDelimeters("А;1;25000"));
        assertEquals(1, converter.countDelimeters(";"));
        assertEquals(0, converter.countDelimeters(""));
    }
    
    @Test
    public void testConverterGerberFileNameConstructed() {
        assertEquals("filename.gbr", converter.constructGerberFilename("filename.csv"));
        assertEquals("filename.gbr", converter.constructGerberFilename("filename.csvfile"));
        assertEquals("file.gbr", converter.constructGerberFilename("file.csv"));
        assertEquals("f.csv.gbr", converter.constructGerberFilename("f.csv.csv"));
        assertEquals("file.gbr", converter.constructGerberFilename("file"));
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

        gerber.setIsLayersFile(false);  // file with no layers
        converter.setCsvFilename("table.csv");
        converter.convert();
        assertTrue(verifyResult(converter.getGerberFilename(), expected));
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

        gerber.setIsLayersFile(true);
        converter.setCsvFilename("tableLayers.csv"); // file with layers
        converter.convert();
        assertTrue(verifyResult(converter.getGerberFilename(), expected));
    }
    
    private boolean verifyResult(String filename, String[] expected) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(filename));
        while(r.ready()) {
            String line = r.readLine();
            if (line.startsWith("%FS"))
                assertEquals("%FSLAX43Y43*%", line);
            if (line.equals(gerber.getEndOfHeaderMarker())) // end of header
                break;
        }
        int linesCount = expected.length;
        for (int index = 0; index < linesCount; index++) {
            if (!r.ready())
                fail("Unexdected end of file (Expecting \"" + expected[index] + "\" at index " + index + ")");
            assertEquals(expected[index], r.readLine());
        }
        r.close();
        return true;
    }
}
