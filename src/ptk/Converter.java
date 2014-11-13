package ptk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Sergey
 */
public class Converter {
    
    private String csvFileName;
    private ArrayList<String> csvData   = new ArrayList<>();
    private ArrayList<Polygon> polygons = new ArrayList<>();
    private String delimeter            = ";";
    private int linesCount              = 0;
    private int errorLinesCount         = 0;

    private String gerberHeader =
        "G04 PTK " + PTK.VERSION + "*\n" +
        "%TF.FileFunction,Copper,L1,Top,Signal*%\n" +
        "%MOMM*%\n" +
        "%FSLAX43Y43*%\n" +
        "G75*\n" +
        "G01*\n" +
        "%LPD*%\n";
    

    public Converter () {}
    
    public Converter(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public void convert() throws IOException {
        readCsvIntoArrayList();
        convertCsvDataToPolygons();
        writeGerber();
        printStats();
    }
    
    public void readCsvIntoArrayList() throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(csvFileName));
        csvData.clear();
        while (r.ready()) {
            csvData.add(r.readLine());
            linesCount++;
        }
        r.close();
    }
    
    public void convertCsvDataToPolygons() {
        polygons.clear();
        Polygon p = new Polygon();
        for (String line: csvData)
            if (hasNoErrors(line)) p = addPointFromLineTo(p, line);
        p.removeLast();
    }
    
    private Polygon addPointFromLineTo(Polygon p, String line) {
        String[] data = line.split(delimeter, 4);
        if (!data[0].isEmpty()) {
            p.removeLast();             // последняя точка совпадает с первой
            p = new Polygon();
            polygons.add(p);
        }
        p.addPoint(new Point(data[2], data[3]));
        return p;
    }
    
    public void setCsvData(ArrayList<String> csvData) {
        this.csvData = csvData;
    }
    
    public int getPolygonsCount() {
        return polygons.size();
    }
    
    public String toGerber() {
        String result = "";
        for (Polygon p: polygons)
            result += p.toGerber();
        return result;
    }
    
    public void writeGerber() throws IOException {
        File gerberFile = new File(csvFileName + ".gbr");
        FileWriter w = new FileWriter(gerberFile);
        w.write(gerberHeader);
        for (Polygon p: polygons) {
            w.write(p.toGerber());
        }
        w.write("M02*");
        w.close();
    }
    
    public void printStats() {
        System.out.println("\nLines processed: " + linesCount);
        System.out.println("Polygons: " + polygons.size());
        System.out.println("Error lines: " + errorLinesCount);
    }
   
    /**
     * Checks if line has no errors and therefore can be processed
     * @param line string line to check for errors
     * @return true if line has no errors
     */
    public boolean hasNoErrors(String line) {
        if (3 != countDelimeters(line)) {
                System.err.println("ERROR: Wrong format at line " + linesCount + ": \"" + line + "\"");
                errorLinesCount++;
                return false;
        }
        return true;
    }
    
    /**
     * Counts delimeters in CSV separated line
     * @param str String CSV line
     * @return int Number of delimeters in line
     */
    public int countDelimeters(String str) {
        int count = 0;
        for (char c: str.toCharArray()) if (c == delimeter.charAt(0)) count++;
        return count;
    }

}
