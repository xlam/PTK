package ptk;

/**
 *
 * @author Sergey
 */
public class Gerber {

    private static Gerber instance = null;
    
    private int leadDigits  = 4;
    private int trailDigits = 4;
    private boolean isLayersFile = false;
    
    private final String fileExtention = ".gbr";
    private final String EOH = "G04 END OF HEADER*";
    private final String[] zeros = {"", "0", "00", "000", "0000", "00000", "000000", "0000000"};
    
    private Gerber() {}
    
    public static Gerber getInstance() {
        if (null == instance)
            instance = new Gerber();
        return instance;
    }
    
    public String getHeader() {
        String header = "" +
        "G04 PTK " + PTK.getVersion() + "*\n" +  // Gerber should not know about PTK!
        "%TF.FileFunction,Copper,L1,Top,Signal*%\n" +
        "%MOMM*%\n" +
        "%FSLA" + getNumberFormatString() + "*%\n" +
        Code.G75.nl() +
        Code.G01.nl() +
        Code.LPD.nl() +
        getEndOfHeaderMarker() + "\n";
        return header;
    }
    
    public String getEndOfHeaderMarker() {
        return EOH;
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
    
    public String getFileExtention() {
        return fileExtention;
    }
    
}
