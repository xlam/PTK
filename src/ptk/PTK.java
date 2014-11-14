/**
 * Преобразователь таблиц координат (ПТК)
 * @author Sergey
 */

package ptk;

import java.io.IOException;

public class PTK {
    
    public static final String VERSION = "0.3.5-dev";
    
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Provide filename");
            System.exit(0);
        }
        Gerber.setNumberFormat(4, 3); // формат чисел - X43Y43. Все числа целые.
        Converter c = new Converter(args[0]);
        c.convert();
    }
}
