/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ptk;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Polygon {
    
    public final static String GRB_G36  = "G36*\n";
    public final static String GRB_G37  = "G37*\n";
    public final static String GRB_D01  = "D01*\n";
    public final static String GRB_D02  = "D02*\n";
    
    private ArrayList<Point> points;
    
    public Polygon() {
        points = new ArrayList();
    }
    
    public Polygon addPoint(Point p) {
        points.add(p);
        return this;
    }
    
    public int countPoints() {
        return points.size();
    }
    
    public void removeLast() {
        if (points.size() > 0) points.remove(points.size()-1);
    }
    
    public String toGerber() {
        String result = GRB_G36;
        Point[] p = points.toArray(new Point[points.size()]);
        for (int i = 0; i < p.length; i++) {
            if (i > 0) {
                if (!(p[i].getX()).equals(p[i-1].getX())) result += p[i].getX();
                if (!(p[i].getY()).equals(p[i-1].getY())) result += p[i].getY();
                result += GRB_D01;
            } else {
                result += p[i] + GRB_D02;
            }
        }

        if (!(p[p.length-1].getX()).equals(p[0].getX())) result += p[0].getX();
        if (!(p[p.length-1].getY()).equals(p[0].getY())) result += p[0].getY();
        result += GRB_D01;
        result += GRB_G37;
        
        return result;
    }
}
