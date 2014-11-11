/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptk;

import java.util.LinkedHashSet;

/**
 *
 * @author Admin
 */
public class Polygon {
    
    private LinkedHashSet<Point> points;
    
    public Polygon() {
        points = new LinkedHashSet();
    }
    
    public Polygon addPoint(Point p) {
        points.add(p);
        return this;
    }
    
    public int countPoints() {
        return points.size();
    }
    
    public String toGerber() {
        String result = "G36*\n";
        Point[] p = points.toArray(new Point[points.size()]);
        for (int i = 0; i < p.length; i++) {
            if (i > 0) {
                if (!(p[i].getX()).equals(p[i-1].getX())) result += "X" + p[i].getXGerber();
                if (!(p[i].getY()).equals(p[i-1].getY())) result += "Y" + p[i].getYGerber();
                result += "D01*\n";
            } else {
                result += p[i] + "D02*\n";
            }
        }
        if (!(p[p.length-1].getX()).equals(p[0].getX())) result += "X" + p[0].getXGerber();
        if (!(p[p.length-1].getY()).equals(p[0].getY())) result += "Y" + p[0].getYGerber();
        result += "D01*\n";
        result += "G37*\n";
        return result;
    }
}
