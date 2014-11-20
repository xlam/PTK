package ptk;

/**
 *
 * @author Sergey
 */
public enum Code {
    
    D01("D01*"), D02("D02*"),
    G36("G36*"), G37("G37*"),
    LPD("%LPD*%"), LPC("%LPC*%"),
    M02("M02*");
    
    private final String tocken;
    
    Code(String tocken) {
        this.tocken = tocken;
    }
    
    @Override
    public String toString() {
        return tocken;
    }
    
    public String nl() {
        return toString() + "\n";
    }
}
