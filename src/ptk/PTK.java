/**
 * Преобразователь таблиц координат (ПТК)
 * @author Sergey
 */

package ptk;

public class PTK {
    
    public static final String VERSION = "0.4.0";
    
    public static void main(String[] args) {
        System.out.println("PTK v" + VERSION + ".\n");
        if (args.length == 0) {
            System.out.println("Provide filename");
            System.exit(0);
        }
        Gerber g = Gerber.getInstance();
        g.setNumberFormat(4, 3); // формат чисел - X43Y43. Все числа целые.
        Converter c = new Converter(args[0]);
        System.out.println("Converting " + args[0] + "...");
        c.convert();
        System.out.println("Converting finished.\n");
        c.printStats();
    }
}
