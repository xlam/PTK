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

    private static int leadDigits  = 4;
    private static int trailDigits = 4;
    
    private static final String[] zeros = {"", "0", "00", "000", "0000", "00000", "000000", "0000000"};
    
    public static void setNumberFormat(int leadDigits, int trailDigits) {
        Gerber.leadDigits  = leadDigits;
        Gerber.trailDigits = trailDigits;
    }
    
    public static String getNumberFormatString() {
        return "X" + leadDigits + trailDigits + "Y" + leadDigits + trailDigits;
    }
    
    public static String getTrailZeros() {
        return zeros[trailDigits];
    }

    public static String formatNumber(String number) {
        return number + getTrailZeros();
    }
}
