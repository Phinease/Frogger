package environnment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;
import util.Logo;


class Piege {
    private Game game;
    private Case pos;
    private int danger = 0;

    Piege(Game game, int y){
        this.game = game;
        this.pos = new Case(game.randomGen.nextInt(game.width), y);
    }

    boolean coversCase(Case pos) {
        // System.out.println("Danger: " + danger);
        return pos.x == this.pos.x && pos.y == this.pos.y;
    }

    boolean danger(){
        return this.danger > 1;
    }

    void add_danger(){
        this.danger += 1;
    }

    void addToGraphics() {
        Logo logo = (danger>=1)?Logo.trap2:Logo.trap1;
        this.game.getGraphic().add(new Element(this.pos.x , this.pos.y - this.game.score, logo));
    }
}
