/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptk;

import java.io.IOException;

/**
 * Преобразователь таблиц координат (ПТК)
 * @author Sergey
 */
public class PTK {
    public static void main(String[] args) throws IOException {
        Converter c = new Converter(args);
        //c.convert();
        c.readCSV();
        c.writeGerber();
        c.printStats();
    }
}
