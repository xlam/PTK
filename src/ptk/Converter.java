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
    
    private String csvFileName;
    private String delimeter = ";";
    private int    linesCount = 0;
    private int    errorLinesCount = 0;
    private int    polygonsCount = 0;
    private Gerber gerber = Gerber.getInstance();
    
    public Converter () {}
    
    public Converter(String csvFileName) {
        setCsvFileName(csvFileName);
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }
    
    public void printStats() {
        System.out.println("\nLines processed: " + linesCount);
        System.out.println("Polygons: " + polygonsCount);
        System.out.println("Error lines: " + errorLinesCount);
    }
   
    public boolean lineHasErrors(String line) {
        if (3 != countDelimeters(line)) {
            errorLinesCount++;
            return true;
        }
        return false;
    }
    
    public int countDelimeters(String str) {
        int count = 0;
        for (char c: str.toCharArray()) if (c == delimeter.charAt(0)) count++;
        return count;
    }

    
    public boolean convert() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(csvFileName));
            FileWriter w = new FileWriter(new File(csvFileName + ".gbr"));
            linesCount = 0;
            polygonsCount = 0;
            w.write(gerber.getHeader());
            while (r.ready()) {
                linesCount++;
                String line = r.readLine();
                if (lineHasErrors(line)) {
                    System.err.println("ERROR: Skipping error line " + linesCount + ": \"" + line + "\"");
                    continue;
                };
                String[] data = line.split(delimeter, 4);
                String result = "";
                result += data[2].isEmpty() ? "" : "X" + gerber.formatNumber(data[2]);
                result += data[3].isEmpty() ? "" : "Y" + gerber.formatNumber(data[3]);
                if (!data[0].isEmpty()) {
                    polygonsCount++;
                    result = "G36*\n" + result + "D02*\n";
                    if (polygonsCount > 1) result = "G37*\n" + result;
                } else {
                    result += "D01*\n";
                }
                w.write(result);
            }
            w.write("G37*\n");
            r.close();
            w.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
}
