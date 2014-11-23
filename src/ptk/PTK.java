/**
 * Преобразователь таблиц координат (ПТК)
 * @author Sergey
 */

package ptk;

public class PTK {
    
    public static final String VERSION = "0.4.4-dev";
    
    public static void main(String[] args) {
        System.out.println("PTK v" + VERSION + ".\n");
        // Need to add arguments handling stuff
        if (args.length == 0) {
            System.out.println("Provide filename");
            System.exit(0);
        }
        Gerber g = Gerber.getInstance();
        g.setNumberFormat(4, 3); // формат чисел - X43Y43. Используем только целые значения.
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
}
