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

    private FileWriter fw;
    private BufferedReader fr;

    private ArrayList<Polygon> polygons = new ArrayList();
    
    /**
     * CSV delimeter
     */
    private String delimeter = ";";
    
    /**
     * Total lines processed
     */
    private int linesCount = 0;
    
    /**
     * Error lines encountered
     */
    private int errorLinesCount = 0;

    /**
     * Empty constructor for creating test instances
     * 
     */
    public Converter () {}
    
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
        createStreams(args[0]); // args[0] = input file name
        Point.setZeros("000"); // формат чисел - X43Y43. Используется только целая часть.
    }

    public void convert() throws IOException{
        readCSV();
        writeGerber();
        printStats();
    }
    
    /**
     * Первый полигон будет создан только тогда, когда встретится
     * строка в которой присутствует имя полигона. Поэтому надо следить
     * за корректностью CSV файла.
     * 
     * @throws IOException 
     */
    public void readCSV() throws IOException {
        String[] data;
        String line;
        Polygon p = new Polygon();
        while (fr.ready()) {
            line = fr.readLine();
            linesCount++;
            if (hasErrors(line)) continue;
            data = line.split(delimeter, 4);
            if (!data[0].isEmpty()) {
                //System.out.println("Adding polygon " + (polygons.size()+1) + ": " + data[0]);
                p.removeLast();
                p = new Polygon();
                polygons.add(p);
            }
            p.addPoint(new Point(data[2], data[3]));
        }
        fr.close();
    }
    
    public void writeGerber() throws IOException {
        writeHeader();
        for (Polygon p: polygons) {
            fw.write(p.toGerber());
        }
        fw.write("M02*");
        fw.close();
    }
    
    public void printStats() {
        System.out.println("\nLines processed: " + linesCount);
        System.out.println("Polygons: " + polygons.size());
        System.out.println("Error lines: " + errorLinesCount);
    }
    
    private void writeHeader() throws IOException {
        fw.write(
            "G04 PTK 0.3.1*\n" +
            "%TF.FileFunction,Copper,L1,Top,Signal*%\n" +
            "%MOMM*%\n" +
            "%FSLAX43Y43*%\n" +
            "G75*\n" +
            "G01*\n" +
            "%LPD*%\n"
        );
    }

    private void createStreams(String filename) throws IOException {
        File coordsFile = new File(filename);
        this.fr = new BufferedReader(new FileReader(coordsFile));
        this.fw = new FileWriter(coordsFile.getName() + ".gbr");
    }
   
    /**
     * Checks if line has errors and therefore can not be processed
     * @param line string line to check for errors
     * @return true if line has errors
     */
    public boolean hasErrors(String line) {
        if (3 != countDelimeters(line)) {
                System.err.println("ERROR: Wrong format at line " + linesCount + ": \"" + line + "\"");
                errorLinesCount++;
                return true;
        }
        return false;
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
        String[] arr = str.split(delimeter, 4);
        return arr;
    }

}
