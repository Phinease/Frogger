package environnment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;
import util.Logo;


public class Bonus {
    private Game game;
    private Case pos;
    private boolean use = true;

    Bonus(Game game, int y){
        this.game = game;
        this.pos = new Case(game.randomGen.nextInt(game.width), y);
    }

    void addToGraphics() {
        if(this.use){
            this.game.getGraphic().add(new Element(this.pos.x , this.pos.y - this.game.score, Logo.star));
        }
    }

    boolean coversCase(Case pos) {
        return pos.x == this.pos.x && pos.y == this.pos.y && use;
    }

    void used(){
        this.use = false;
    }
}