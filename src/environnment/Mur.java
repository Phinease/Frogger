package environnment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;
import util.Logo;


class Mur {
    private Game game;
    private Case pos;
    private int length;

    Mur(Game game, int y){
        this.game = game;
        this.pos = new Case(game.randomGen.nextInt(game.width), y);
        this.length = game.randomGen.nextInt(5) + 3;
    }

    void addToGraphics() {
        for(int i = 0; i < this.length; ++i) {
            this.game.getGraphic().add(new Element(this.pos.x + i, this.pos.y - this.game.score, Logo.wall));
        }
    }

    boolean coversCase(Case pos) {
        if (pos.y != this.pos.y) {
            return false;
        } else {
            return pos.x >= this.pos.x && pos.x < this.pos.x + this.length;
        }
    }
}
