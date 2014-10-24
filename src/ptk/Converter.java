package ptk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Sergey
 */
public class Converter {

    private FileWriter fw;
    private BufferedReader fr;
    
    /**
     * CSV delimeter
     */
    private String delim = ";";
    
    /**
     * Decimal zeros. We use only integer values.
     */
    private String zeros = "000";
    
    /**
     * Polygons count
     */
    private int polygonsCount = 0;
    
    /**
     * Main constructor
     * 
     * @param args Command line args
     * @throws IOException
     */
    public Converter(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Provide filename");
            System.exit(0);
        }
        String filename = args[0];
        File coordsFile = new File(filename);
        this.fr = new BufferedReader(new FileReader(coordsFile));
        this.fw = new FileWriter(coordsFile.getName() + ".gbr"); 
    
        fw.write(
            "G04 PTK 0.2a*\n" +
            "%TF.FileFunction,Copper,L1,Top,Signal*%\n" +
            "%MOMM*%\n" +
            "%FSLAX53Y53*%\n" +
            "G75*\n" +
            "G01*\n" +
            "%LPD*%\n"
        );
    }
   
    /**
     * Empty constructor for creating test instances
     * 
     */
    public Converter () {}
    
    /**
     * Converts coordinates data into Gerber RS-274X and
     * writes it to gerber output file
     * 
     * @throws IOException
     */
    public void convert() throws IOException {
          
        String line, out;
        int linesCount = 0;
        int errorLinesCount = 0;
                
        polygonsCount = 0;
        while (fr.ready()) {
            line = fr.readLine();
            linesCount++;

            if (3 != countDelimeters(line)) {
                System.err.println("ERROR: Wrong format at line " + linesCount + ": \"" + line + "\"");
                errorLinesCount++;
                continue;
            }
            
            out = createTockens(parseLine(line));
            fw.write(out);
            System.out.println(out.trim() + "\t\tline: " + line);
        }
        
        if (polygonsCount > 0) fw.write("G37*\n");
        fw.write("M02*");
        
        fr.close();
        fw.close();
        
        System.out.println("\nLines processed: " + linesCount);
        System.out.println("Polygons: " + polygonsCount);
        System.out.println("Error lines: " + errorLinesCount);
    }

    /**
     * Counts delimeters in CSV separated line
     * @param str String CSV line
     * @return int Number of delimeters in line
     */
    public int countDelimeters(String str) {
        int count = 0;
        for (char c: str.toCharArray()) if (c == delim.charAt(0)) count++;
        return count;
    }

    /**
     * Parses line and splits it by delimiter into array
     * @param str String CSV text line
     * @return array Array of strings:
     *  [0] - polygon name
     *  [1] - point number
     *  [2] - X coordinate
     *  [3] - Y coordinate
     */
    public String[] parseLine(String str) {
        String[] arr = str.split(delim, 4);
        return arr;
    }

    /**
     * Creates Gerber tockens from array data
     * @param arr array Array containing Gerber data
     * @return String String of Gerber tockens
     */
    public String createTockens(String[] arr) {
        String X = "", Y = "", result = "";
        if (!arr[2].isEmpty()) X = "X" + arr[2] + zeros;
        if (!arr[3].isEmpty()) Y = "Y" + arr[3] + zeros;
        if (!arr[0].isEmpty()) {
            if (polygonsCount++ > 0) result = "G37*\n";
            result += "G36*\n"+X+Y+"D02*\n";
        }
        else {
            result = X+Y+"D01*\n";
        }
        return result;
    }

    
}
