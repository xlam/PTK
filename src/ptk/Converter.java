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
     * Flag indicating that we currently in polygon mode
     */
    private boolean polygonFlag = false;
    
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
            "G04 PTK 0.1a*\n" +
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
          
        String[] split;
        String line, out;
        String X,Y;
        int linesCount = 0;
        int errorLinesCount = 0;
                
        polygonsCount = 0;
        while (fr.ready()) {
            out = "";
            line = fr.readLine();
            linesCount++;

            if (3 != countDelimeters(line)) {
                System.err.println("ERROR: Wrong format at line " + linesCount + ": \"" + line + "\"");
                errorLinesCount++;
                continue;
            }
            
            split = parseLine(line);
            
            X = split[2];
            Y = split[3];
            
            if (!X.isEmpty()) out += "X" + X + zeros;
            if (!Y.isEmpty()) out += "Y" + Y + zeros;
            
            if (!split[0].isEmpty()) {
                if (polygonsCount > 0) fw.write("G37*\n");
                fw.write("G36*\n");
                polygonsCount++;
                out += "D02*\n";
            } else if (!out.isEmpty()) {
                out += "D01*\n";
            }
            
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

    public int countDelimeters(String str) {
        int count = 0;
        for (char c: str.toCharArray()) if (c == delim.charAt(0)) count++;
        return count;
    }

    public String[] parseLine(String str) {
        String[] arr = str.split(delim, 4);
        return arr;
    }

    public String CreateTockens(String[] arr) {
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
