/**
 * Преобразователь таблиц координат (ПТК)
 * @author Sergey
 */

package ptk;

public class PTK {
    
    public static final String VERSION = "0.4.1-dev";
    
    public static void main(String[] args) {
        System.out.println("PTK v" + VERSION + ".\n");
        if (args.length == 0) {
            System.out.println("Provide filename");
            System.exit(0);
        }
        Gerber g = Gerber.getInstance();
        g.setNumberFormat(4, 3); // формат чисел - X43Y43. Все числа целые.
        if (args.length > 1 && args[1].equals("-l")) {
            g.setIsLayersFile(true);
            System.out.println("Layers enabled.\n");
        }
        Converter c = new Converter(args[0]);
        System.out.println("Converting [" + args[0] + "] -> [" + args[0] + ".gbr]...");
        c.convert();
        System.out.println("Converting finished.\n");
        c.printStats();
    }
}
