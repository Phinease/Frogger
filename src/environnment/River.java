package environnment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;
import util.Logo;


public class River {
    private Game game;
    private int y;
    private int speed;
    private int timer;
    private Wood wood;
    
    public River(Game game, int y){
        this.game = game;
        this.y = y;
        this.speed = game.randomGen.nextInt(game.minSpeed) + 1;
        this.wood = new Wood(game, y);
        // System.out.println("POS RIVER: " + this.y);
    }

    public void update() {
        show();
        ++this.timer;
        if (this.timer <= this.speed) {
            this.wood.move(false);
        } else {
            this.wood.move(true);
            if(this.game.get_frog_position() == this.y){
                this.game.move_frog(this.wood.get_LtR());
            }
            this.timer = 0;
        }

    }

    public boolean isSafe(Case pos) {
        // return true;
        return wood.coversCase(pos);
    }

    private void show(){
        for(int i = 0; i < this.game.width; ++i) {
            this.game.getGraphic().add(new Element(i, this.y - this.game.score, Logo.water));
        }
    }
}

