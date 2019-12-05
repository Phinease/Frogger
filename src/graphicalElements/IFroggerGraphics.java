package graphicalElements;

import gameCommons.IFrog;

public interface IFroggerGraphics {

    void add(Element e);
    void clear();
    void repaint();
    void setFrog(IFrog frog);
    void endGameScreen(String message);
    boolean get_move();

}
