/**
 * 
 * @author angeliacuaca
 *
 *         Pit Class extended from abstract class GameItem
 */
public class Pit extends GameItem {
	public Pit(char item) {
		super(item);
	}

	@Override
	public String warning() {
		return "~~~ Breeze ~~~";
	}

	@Override
	public String encounter() {
		return "You have fallen to a pit...";
	}
}
