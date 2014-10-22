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
     * Main constructor
     * 
     * @param args Command line args
     * @throws IOException
     */
    public Converter(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Укажите имя файла координат");
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
          
        String line, out;
        String X,Y, Name;
        int colon1, colon2;
        int count = 0;
                
        while (fr.ready()) {
            out = "";
            line = fr.readLine();

            // TODO: если в строке нет разделителей возможен сбой!
            Name = line.substring(0, line.indexOf(delim));
            colon2 = line.lastIndexOf(delim);
            colon1 = line.lastIndexOf(delim, colon2-1);
            X = line.substring(colon1+1, colon2);
            Y = line.substring(colon2+1);
        
            if (!X.isEmpty()) out = "X"+X+zeros;
            if (!Y.isEmpty()) out = out + "Y"+Y+zeros;
            
            if (!Name.isEmpty()) {
                if (count > 0) fw.write("G37*\n");
                fw.write("G36*\n");
                count++;
                out = out + "D02*\n";
            } else if (!out.isEmpty()) {
                out = out + "D01*\n";
            }
            
            fw.write(out);
            
            System.out.println(out.trim() + "\t" + line + " - [" + colon1 + ";" + colon2 + "] - [X" + X + ":Y" + Y + "]");
        }
        
        if (count > 0) {
            fw.write("G37*\n");
        }

        fw.write("M02*");
        
        fr.close();
        fw.close();
    }
    
}
