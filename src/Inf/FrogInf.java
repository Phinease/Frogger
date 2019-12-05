package Inf;

import gameCommons.Case;
import gameCommons.Game;
import gameCommons.IFrog;
import graphicalElements.Element;
import util.Direction;
import util.Logo;


public class FrogInf implements IFrog {
    private Case position;
    private Direction direction;
    private Game game;

    public FrogInf(Game game) {
        this.position = new Case(game.width/2, 1);
        this.direction = Direction.up;
        this.game = game;
    }

    public Case getPosition() {
        return this.position;
    }

    public void move(Direction key) {
        this.direction = key;
        switch (key){
            case up:
                Case future1 = new Case(this.position.x, this.position.y + 1);
                if (this.game.get_block(future1)){
                    return;
                }
                this.position = future1;
                ++this.game.score;
                if (this.game.score > this.game.maxScore) {
                    this.game.maxScore = this.game.score;
                    this.game.addLane();
                }
                break;
            case down:
                Case future2 = new Case(this.position.x, this.position.y -1);
                if (this.game.get_block(future2)){
                    return;
                }
                if (this.position.y > 1){
                    this.position = new Case(this.position.x, this.position.y -1);
                    --this.game.score;
                }
                break;
            case right:
                Case future3 = new Case(this.position.x +1, this.position.y);
                if (this.game.get_block(future3)){
                    return;
                }
                if (this.position.x < this.game.width - 1){
                    this.position = new Case(this.position.x +1, this.position.y);
                }
                break;
            case left:
                Case future4 = new Case(this.position.x -1, this.position.y);
                if (this.game.get_block(future4)){
                    return;
                }
                if(this.position.x > 0){
                    this.position = new Case(this.position.x -1, this.position.y);
                }
                break;
        }
        this.game.cycle = (this.game.score+1)/10;
        this.game.get_bonus(position);
        this.game.getGraphic().add(new Element(this.position.x, 1, Logo.frog));
        // this.game.testWin(game.stop);
        this.game.testLose(game.stop);
        // System.out.println("Scores: " + (this.game.score+this.game.scoreBonus));
    }

    public int get_score(){
        return this.game.score+this.game.scoreBonus;
    }

    public boolean on_water(){
        return (this.position.y / 10) % 2 == 1;
    }
    public void slide(){
        if(this.game.get_slide(this.position)){
            // System.out.println("SLIDE: " +this.game.get_slide(this.position));
            int limit;
            switch (this.direction){
                case right:
                    limit = this.game.width-this.position.x-1;
                    for (int i = 0; i < this.game.randomGen.nextInt(limit)+1 ; i++) {
                        move(Direction.right);
                    }
                    break;
                case left:
                    limit = this.position.x-1;
                    for (int i = 0; i < this.game.randomGen.nextInt(limit)+1 ; i++) {
                        move(Direction.left);
                    }
                    break;
                case down:
                    limit = this.game.score-1;
                    for (int i = 0; i < this.game.randomGen.nextInt(limit)+1 ; i++) {
                        move(Direction.down);
                    }
                    break;
                case up:
                    limit = this.game.height-4;
                    for (int i = 0; i < this.game.randomGen.nextInt(limit)+1 ; i++) {
                        move(Direction.up);
                    }
                    break;
            }
        }
    }

}
