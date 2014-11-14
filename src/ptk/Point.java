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
    
    private String raw_x;   // CSV
    private String raw_y;
    private String x;       // Gerber formatted
    private String y;
    
    public Point() {
        update("", "");
    };
    
    public Point(String x, String y) {
        update(x, y);
    }
    
    private void update(String x, String y) {
        raw_x = x;
        raw_y = y;
        this.x = raw_x.isEmpty() ? "" : "X" + Gerber.formatNumber(raw_x);
        this.y = raw_y.isEmpty() ? "" : "Y" + Gerber.formatNumber(raw_y);
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
    
    public String toGerber() {
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
