/**
 * 
 * @author angeliacuaca
 *
 *         Gold Class extended from abstract class GameItem
 */
public class Gold extends GameItem {

	public Gold(char item) {
		super(item);
	}

	@Override
	public String warning() {
		return "*** Faint Glitter ***";
	}

	@Override
	public String encounter() {
		return "You found the Gold!...";
	}

}
