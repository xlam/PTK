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
    
    private Integer x;
    private Integer y;
    
    public Point() {
    };
    
    public Point(String x, String y) {
        setXY(x, y);
    }
    
    public static void setZeros(String zeros) {
        Point.zeros = zeros;
    }
    
    public Point setXY(String x, String y) {
        if (x.length() > 0 ) this.x = Integer.decode(x);
        if (y.length() > 0 ) this.y = Integer.decode(y);
        return this;
    }
    
    public Integer getX() {
        return x;
    }
    
    public Integer getY() {
        return y;
    }
    
    public String getXGerber() {
        return new String(x + zeros);
    }
    
    public String getYGerber() {
        return new String(y + zeros);
    }
    
    @Override
    public String toString() {
        String _x = "", _y = "";
        if (x != null) _x = "X"+x+zeros;
        if (y != null) _y = "Y"+y+zeros;
        return _x+_y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) return false;
        Point p = (Point)obj;
        if (x.equals(p.getX()) && y.equals(p.getY())) return true;
        return false;
    }

}
