package environnment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;
import util.Direction;
import util.Logo;


class Wood {
    private Game game;
    private Case pos;
    private boolean dir; // True - right | False - left
    private int length;

    Wood(Game game,int y){
        this.game = game;
        this.length = game.randomGen.nextInt(3) + 3;
        this.dir = game.randomGen.nextBoolean();
        this.pos = new Case(game.randomGen.nextInt(game.width-10)+5, y);
        // System.out.println("WOOD HERE " + pos.y +"  "+ pos.x);
    }

    void move(boolean b) {
        if (b) {
            if(this.pos.x == 0 || this.pos.x == game.width-this.length){
                this.dir = !this.dir;
            }
            this.pos = new Case(this.pos.x + (this.dir ? 1 : -1), this.pos.y);
        }
        this.addToGraphics();
    }

    private void addToGraphics() {
        for(int i = 0; i < this.length; ++i) {
            this.game.getGraphic().add(new Element(this.pos.x + i, this.pos.y - this.game.score, Logo.wood));
        }
    }

    Direction get_LtR(){
        if (this.dir){
            return Direction.right;
        }else{
            return Direction.left;
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
