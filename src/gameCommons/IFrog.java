package gameCommons;


public interface IFrog {

	Case getPosition();
	void move(util.Direction key);
	int get_score();
	void slide();
	boolean on_water();

}
