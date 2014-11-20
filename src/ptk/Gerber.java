/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptk;

/**
 *
 * @author Admin
 */
public class Gerber {

    private static Gerber instance = null;
    
    private int leadDigits  = 4;
    private int trailDigits = 4;
    private boolean isLayersFile = false;
    
    private final String[] zeros = {"", "0", "00", "000", "0000", "00000", "000000", "0000000"};
    
    private Gerber() {}
    
    public static Gerber getInstance() {
        if (null == instance)
            instance = new Gerber();
        return instance;
    }
    
    public void setNumberFormat(int leadDigits, int trailDigits) {
        this.leadDigits  = leadDigits;
        this.trailDigits = trailDigits;
    }
    
    public String getNumberFormatString() {
        return "X" + leadDigits + trailDigits + "Y" + leadDigits + trailDigits;
    }
    
    public boolean isLayersFile() {
        return isLayersFile;
    }

    public void setIsLayersFile(boolean isLayersFile) {
        this.isLayersFile = isLayersFile;
    }
    
    public String getTrailZeros() {
        return zeros[trailDigits];
    }

    public String formatNumber(String number) {
        return number + getTrailZeros();
    }
    
    public String getHeader() {
        String header = "" +
        "G04 PTK " + PTK.VERSION + "*\n" +
        "%TF.FileFunction,Copper,L1,Top,Signal*%\n" +
        "%MOMM*%\n" +
        "%FSLA" + getNumberFormatString() + "*%\n" +
        "G75*\n" +
        "G01*\n" +
        "%LPD*%\n";
        return header;
    }
}
