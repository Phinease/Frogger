package gameCommons;

import java.util.Random;
import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;
import util.Direction;
import util.Logo;


public class Game {

	public final Random randomGen = new Random();

	public int width;
	public int height;
	public int minSpeed;
	public final double defaultMove;
	public int score = 0;
	public int cycle = 0;
	public int maxScore = 0;
	public int scoreBonus = 0;
	public int stop = 0;

	private IEnvironment environment;
	private IFrog frog;
	private IFroggerGraphics graphic;


	public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultMove) {
		this.graphic = graphic;
		this.width = width;
		this.height = height;
		this.minSpeed = minSpeedInTimerLoop;
		this.defaultMove = defaultMove;
	}

	void setFrog(IFrog frog) {
		this.frog = frog;
	}

	public void move_frog(Direction d){
		this.frog.move(d);
	}

	public int get_frog_position(){
		return this.frog.getPosition().y;
	}

	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	public IFroggerGraphics getGraphic() {
		return graphic;
	}

	public boolean testLose(int i) {
		//POINT
		//return false;
		if(i == 0){
			if(!this.environment.isSafe(frog.getPosition(), frog.on_water())){
				this.graphic.endGameScreen("You Lose, your score: " + (this.maxScore + this.scoreBonus));
				return true;
			}
		}
		return false;
	}

	private void update_piege(Case pos){
		if(graphic.get_move()){
			environment.updatePieges(pos);
		}
	}

	public boolean get_slide(Case pos){
		return environment.get_slide(pos);
	}

	public void get_bonus(Case pos){
		if(environment.get_bonus(pos)){
			scoreBonus += 10;
		}
	}

	public boolean get_block(Case pos){
		return environment.get_block(pos);
	}

	void update() {
		graphic.clear();
		environment.update();
		graphic.add(new Element(frog.getPosition().x,1, Logo.frog));
		if(testLose(stop)){
			stop++;
		}
		update_piege(this.frog.getPosition());
	}

	public void addLane() {
		this.environment.addLane();
	}
}
