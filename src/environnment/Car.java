package environnment;

import gameCommons.Case;
import gameCommons.Game;
import graphicalElements.Element;
import util.Logo;


class Car {
	private Game game;
	private Case pos;
	private boolean dir; // True - right | False - left
	private int length;

	Car(Game game, Case frontPosition, boolean dir) {
		this.game = game;
		this.length = game.randomGen.nextInt(2)*2 + 2;
		this.dir = dir;
		this.pos = new Case(dir ? frontPosition.x - this.length : frontPosition.x, frontPosition.y);
	}

	void move(boolean b) {
		if (b) {
			this.pos = new Case(this.pos.x + (this.dir ? 1 : -1), this.pos.y);
		}
		this.addToGraphics();
	}

	private void addToGraphics() {
		for(int i = 0; i < this.length; ++i) {
			this.game.getGraphic().add(new Element(this.pos.x + i, this.pos.y - this.game.score, Logo.car));
		}
	}

	boolean appearsInBounds() {
		return this.pos.x + this.length > 0 || this.pos.x < this.game.width;
	}

	boolean coversCase(Case pos) {
		if (pos.y != this.pos.y) {
			return false;
		} else {
			return pos.x >= this.pos.x && pos.x < this.pos.x + this.length;
		}
	}
}
