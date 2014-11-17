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

    public Converter () {}
    
    public Converter(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public void convert() throws IOException {
        readCsvFileIntoArrayList();
        convertCsvDataToPolygons();
        writeGerber();
        printStats();
    }
    
    public void readCsvFileIntoArrayList() throws IOException {
        csvData.clear();
        BufferedReader r = new BufferedReader(new FileReader(csvFileName));
        while (r.ready()) {
            csvData.add(r.readLine());
            linesCount++;
        }
        r.close();
    }
    
    public void convertCsvDataToPolygons() {
        polygons.clear();
        Polygon p = new Polygon();
        for (String line: csvData) {
            if (lineHasErrors(line)) continue;
            String[] data = line.split(delimeter, 4);
            if (!data[0].isEmpty()) {
                p.removeLast();             // последняя точка совпадает с первой
                p = new Polygon();
                polygons.add(p);
            }
            p.addPoint(new Point(data[2], data[3]));
        }
        p.removeLast();
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
        w.write(Gerber.getHeader());
        for (Polygon p: polygons)
            w.write(p.toGerber());
        w.write("M02*");
        w.close();
    }
    
    public void printStats() {
        System.out.println("\nLines processed: " + linesCount);
        System.out.println("Polygons: " + polygons.size());
        System.out.println("Error lines: " + errorLinesCount);
    }
   
    public boolean lineHasErrors(String line) {
        if (3 != countDelimeters(line)) {
            System.err.println("ERROR: Wrong format at line " + linesCount + ": \"" + line + "\"");
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

    
    public boolean convertClassic() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(csvFileName));
            FileWriter w = new FileWriter(new File(csvFileName + ".gbr"));
            linesCount = 0;
            while (r.ready()) {
                linesCount++;
                String line = r.readLine();
                if (lineHasErrors(line)) continue;
                String[] data = line.split(delimeter, 4);
                String result = "";
                result += data[2].isEmpty() ? "" : "X" + Gerber.formatNumber(data[2]);
                result += data[3].isEmpty() ? "" : "Y" + Gerber.formatNumber(data[3]);
                if (!data[0].isEmpty()) {
                    result = "G37*\nG36*\n" + result + "D02*\n";
                } else {
                    result += "D01*\n";
                }
                w.write(result);
            }
            r.close();
            w.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
}
