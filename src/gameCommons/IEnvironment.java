package gameCommons;

public interface IEnvironment {

	boolean isSafe(Case c, boolean river);
	void update();
	void addLane();
	void updatePieges(Case pos);
	boolean get_slide(Case pos);
	boolean get_block(Case pos);
	boolean get_bonus(Case pos);

}
