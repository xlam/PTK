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
public class Point {

    private static String zeros = "";
    
    private String raw_x;
    private String raw_y;
    private String x;
    private String y;
    
    public Point() {
        update("", "");
    };
    
    public Point(String x, String y) {
        update(x, y);
    }
    
    public static void setZeros(String zeros) {
        Point.zeros = zeros;
    }
    
    private void update(String x, String y) {
        raw_x = x;
        raw_y = y;
        this.x = raw_x.isEmpty() ? "" : "X" + raw_x + zeros;
        this.y = raw_y.isEmpty() ? "" : "Y" + raw_y + zeros;
    }
    
    public String getX() {
        return x;
    }
    
    public String getY() {
        return y;
    }

    public String getRawX() {
        return raw_x;
    }
    
    public String getRawY() {
        return raw_y;
    }
    
    @Override
    public String toString() {
        return x + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) return false;
        Point p = (Point)obj;
        if (x.equals(p.getX()) && y.equals(p.getY())) return true;
        return false;
    }

}
