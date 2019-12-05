package environnment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;
import util.Logo;

import java.util.ArrayList;

public class Rue {
	private Game game;
	private int y;
	private int speed;
	private ArrayList<Car> cars = new ArrayList<>();
	private ArrayList<Piege> pieges = new ArrayList<>();
	private ArrayList<Ice> ices = new ArrayList<>();
	private ArrayList<Mur> murs = new ArrayList<>();
	private ArrayList<Bonus> bonus = new ArrayList<>();
	private boolean dir; // True - right | False - left
	private double densityMove;
	private double densityOther;
	private int count;

	public Rue(Game game, int y, double densityMove) {
		this.game = game;
		this.y = y;
		this.speed = game.randomGen.nextInt(game.minSpeed) + 1;
		this.dir = game.randomGen.nextBoolean();
		this.densityMove = densityMove;
		this.densityOther = 0.3D;
		this.mayAddPiege();
		this.mayAddIce();
		this.mayAddMur();
		this.mayAddBonus();
		// System.out.println("POS RUE: " + this.y);

		for(int i = 0; i < game.width; ++i) {
			this.moveCars(true);
			this.mayAddCar();
		}
	}

	public Rue(Game game, int y) {
		this(game, y, game.defaultMove);
	}

	public void update() {
		addToGraphics();
		++this.count;
		if (this.count <= this.speed) {
			this.moveCars(false);
		} else {
			this.moveCars(true);
			this.mayAddCar();
			this.count = 0;
		}
		show();
	}

	private void addToGraphics() {
		for (int i = 0; i < this.game.width; ++i) {
			this.game.getGraphic().add(new Element(i, this.y - this.game.score, Logo.ground));
		}
	}

	private void show(){
		for (Piege piege : this.pieges) {
			piege.addToGraphics();
		}
		for (Ice ice : this.ices) {
			ice.addToGraphics();
		}
		for (Mur mur : this.murs) {
			mur.addToGraphics();
		}
		for (Bonus bonu : this.bonus) {
			bonu.addToGraphics();
		}
	}

	private void moveCars(boolean b) {
		for (Car car : this.cars) {
			car.move(b);
		}
		this.removeOldCars();
	}

	private void removeOldCars() {
		this.cars.removeIf(c -> !c.appearsInBounds());
	}

	private void mayAddCar() {
		if (this.isSafe(this.getFirstCase()) && this.isSafe(this.getBeforeFirstCase()) && this.game.randomGen.nextDouble() < this.densityMove) {
			this.cars.add(new Car(this.game, this.getBeforeFirstCase(), this.dir));
		}
	}

	private void mayAddPiege(){
		if(this.game.randomGen.nextDouble() < this.densityOther){
			this.pieges.add(new Piege(this.game,this.y));
		}
	}

	private void mayAddIce(){
		if(this.game.randomGen.nextDouble() < this.densityOther){
			this.ices.add(new Ice(this.game,this.y));
		}
	}

	private void mayAddMur(){
		if(this.game.randomGen.nextDouble() < this.densityOther *0.5){
			this.murs.add(new Mur(this.game,this.y));
		}
	}

	private void mayAddBonus(){
		if(this.game.randomGen.nextDouble() < this.densityOther *0.5){
			this.bonus.add(new Bonus(this.game,this.y));
		}
	}

	public boolean isSafe(Case pos) {
		for (Car car : this.cars) {
			if (car.coversCase(pos)) {
				return false;
			}
		}
		for (Piege piege : this.pieges){
			if(piege.coversCase(pos) && piege.danger()) {
				return false;
			}
		}
		// System.out.println("Road: " + this.y);
		return true;
	}

	public void updatePieges(Case pos){
		for (Piege piege : this.pieges) {
			if(piege.coversCase(pos)){
				piege.add_danger();
			}
		}
	}

	public boolean get_slide(Case pos){
		for (Ice ice : this.ices) {
			if (ice.coversCase(pos)){
				return true;
			}
		}
		return false;
	}

	public boolean get_bonus(Case pos){
		for (Bonus bonu : this.bonus) {
			if(bonu.coversCase(pos)){
				bonu.used();
				return true;
			}
		}
		return false;
	}

	public boolean get_block(Case pos){
		for (Mur mur : this.murs) {
			if(mur.coversCase(pos)) {
				return true;
			}
		}
		return false;
	}

	private Case getFirstCase() {
		return this.dir ? new Case(0, this.y) : new Case(this.game.width - 1, this.y);
	}

	private Case getBeforeFirstCase() {
		return this.dir ? new Case(-1, this.y) : new Case(this.game.width, this.y);
	}
}