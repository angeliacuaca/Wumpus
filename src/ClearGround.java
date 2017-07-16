/**
 * 
 * @author angeliacuaca
 *
 * ClearGround Class
 * extended from abstract class GameItem
 */
public class ClearGround extends GameItem{

	public ClearGround(char item) {
		super(item);
	}

	@Override
	public String warning() {
		return "";
	}

	@Override
	public String encounter() {
		return "";
	}

}
