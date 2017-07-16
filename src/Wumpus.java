/**
 * 
 * @author angeliacuaca
 *
 *         Wumpus Class extended from abstract class GameItem
 */
public class Wumpus extends GameItem {

	public Wumpus(char item) {
		super(item);
	}

	@Override
	public String warning() {
		return "!!! Vile Smell !!!";
	}

	@Override
	public String encounter() {
		return "You have been eaten by Wumpus...";
	}

}
