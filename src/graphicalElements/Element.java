package graphicalElements;

import gameCommons.Case;
import util.Logo;


public class Element extends Case {
    final Logo logo;

    public Element(int x, int y, Logo logo) {
        super(x, y);
        this.logo = logo;
    }
    
    public Element(Case c, Logo logo) {
        super(c.x, c.y);
        this.logo = logo;
    }
    
}
