package Inf;

import environnment.Rue;
import environnment.River;
import gameCommons.Case;
import gameCommons.Game;
import gameCommons.IEnvironment;
import java.util.ArrayList;

public class EnvInf implements IEnvironment {
    private ArrayList<Rue> rues;
    private ArrayList<River> rivers;
    private Game game;

    public EnvInf(Game game) {
        this.game = game;
        this.rues = new ArrayList<>();
        this.rivers = new ArrayList<>();
        this.rues.add(new Rue(game, 0, 0));
        this.rues.add(new Rue(game, 1, 0));

        for(int i = 2; i < game.height; ++i) {
            if((i/10)%2 == 0){
                this.addRue(i);
            }else {
                this.addRiver(i);
            }
        }

    }

    private void addRue(int i) {
        this.rues.add(new Rue(this.game, i));
    }

    private void addRiver(int i) { this.rivers.add(new River(this.game, i)); }

    public boolean isSafe(Case c, boolean river) {
        // System.out.println(game.cycle);
        if(river){
            // System.out.println("River " + (c.y-(this.game.cycle/2+1)*10) + " Pos: " + c.y);
            return (this.rivers.get(c.y-(this.game.cycle/2+1)*10)).isSafe(c);
        }else{
            // System.out.println("Road: " + (c.y-(this.game.cycle/2)*10) + " Pos: " + c.y);
            return (this.rues.get(c.y-(this.game.cycle/2)*10)).isSafe(c);
        }
    }

    public void update() {
        for (Rue rue : this.rues) {
            rue.update();
        }
        for (River river : this.rivers) {
            river.update();
        }
    }

    public void addLane() {
        int idx = this.game.score+this.game.height-1;
        if((idx/10)%2==0){
            this.addRue(idx);
        }else {
            this.addRiver(idx);
        }

    }

    public void updatePieges(Case pos) {
        for (Rue rue: this.rues) {
            rue.updatePieges(pos);
        }
        // System.out.println("UPDATED");
    }

    public boolean get_slide(Case pos) {
        for (Rue rue: this.rues) {
            if(rue.get_slide(pos)){
                return true;
            }
        }
        return false;
    }

    public boolean get_bonus(Case pos) {
        for (Rue rue: this.rues) {
            if(rue.get_bonus(pos)){
                return true;
            }
        }
        return false;
    }

    public boolean get_block(Case pos) {
        for (Rue rue: this.rues) {
            if(rue.get_block(pos)){
                return true;
            }
        }
        return false;
    }
}
