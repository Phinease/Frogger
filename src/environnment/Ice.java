package environnment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;
import util.Logo;


class Ice {
    private Game game;
    private Case pos;

    Ice(Game game, int y){
        this.game = game;
        this.pos = new Case(game.randomGen.nextInt(game.width), y);
    }

    void addToGraphics() {
        this.game.getGraphic().add(new Element(this.pos.x , this.pos.y - this.game.score, Logo.ice));
    }

    boolean coversCase(Case pos) {
        return pos.x == this.pos.x && pos.y == this.pos.y;
    }
}
