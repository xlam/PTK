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
        setCsvFilename(csvFileName);
    }

    public void setCsvFilename(String csvFileName) {
        this.csvFileName = csvFileName;
    }
    
    public void printStats() {
        System.out.println("Lines processed: " + linesCount);
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
            FileWriter w = new FileWriter(new File(constructGerberFilename(csvFileName)));
            linesCount = 0;
            polygonsCount = 0;
            w.write(gerber.getHeader());
            while (r.ready()) {
                linesCount++;
                w.write(constructGerberString(r.readLine()));
            }
            w.write("G37*\n");
            w.write("M02*\n");
            r.close();
            w.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    private String constructGerberString(String line) {
        if (lineHasErrors(line)) {
            System.err.println("ERROR: Skipping error line " + linesCount + ": \"" + line + "\"");
            return "";
        }
        String[] data = line.split(delimeter, 4);
        String result = "";
        result += data[2].isEmpty() ? "" : "X" + gerber.formatNumber(data[2]);
        result += data[3].isEmpty() ? "" : "Y" + gerber.formatNumber(data[3]);
        if (!data[0].isEmpty()) {
            polygonsCount++;
            result = "G36*\n" + result + "D02*\n";
            if (polygonsCount == 2 && gerber.isLayersFile()) result = "%LPC*%\n" + result;
            if (polygonsCount > 1) result = "G37*\n" + result;
        } else
            result += "D01*\n";
        return result;
    }
    
    public String getGerberFilename() {
        if (csvFileName.isEmpty())
            return "";
        return constructGerberFilename(csvFileName);
    }
    
    public String constructGerberFilename(String filename) {
        String ext = ".gbr";
        int index = filename.lastIndexOf('.');
        if (index < 0)
            return filename + ext;
        return filename.substring(0, index) + ext;
    }
}
