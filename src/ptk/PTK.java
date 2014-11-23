/**
 * Table of coordinates to Gerber converter.
 * @author Sergey
 */

package ptk;

public class PTK {
    
    private static final String VERSION_MAJOR = "0";
    private static final String VERSION_MINOR = "4";
    private static final String VERSION_BUILD = "4";
    private static final String VERSION_STAGE = "dev"; // STAGE or something else would be correct?
    
    public static final String VERSION = getVersion();
    
    public static void main(String[] args) {
        System.out.println("PTK v" + VERSION + ".\n");
        // Need to add arguments handling stuff
        if (args.length == 0) {
            System.out.println("Provide filename");
            System.exit(0);
        }
        Gerber g = Gerber.getInstance();
        g.setNumberFormat(4, 3); // We use X43Y43 number format and only integer values.
        if (args.length > 1 && args[1].equals("-l")) {
            g.setIsLayersFile(true);
            System.out.println("Layers enabled.\n");
        }
        Converter c = new Converter(args[0]);
        System.out.println("Converting [" + args[0] + "] -> [" + c.constructGerberFilename(args[0]) + "]...");
        c.convert();
        System.out.println("Converting finished.\n");
        c.printStats();
    }
    
    public static String getVersion() {
        return VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_BUILD + "-" + VERSION_STAGE;
    }
}
