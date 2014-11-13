/**
 * Преобразователь таблиц координат (ПТК)
 * @author Sergey
 */

package ptk;

import java.io.IOException;

public class PTK {
    
    public static final String VERSION = "0.3.4-dev";
    
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Provide filename");
            System.exit(0);
        }
        Point.setTrailingZeros("000"); // формат чисел - X43Y43. Используется только целая часть.
        Converter c = new Converter(args[0]);
        c.convert();
    }
}
