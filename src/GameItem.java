/**
 * 
 * @author angeliacuaca
 *
 *         Abstract Class of game items on the board
 */
public abstract class GameItem {
	private char item;

	/**
	 * GameItem can be Gold(g), Pits(p), Wumpus(W), ClearGround(.)
	 * 
	 * @param item
	 *            character represent game items
	 */
	public GameItem(char item) {
		this.item = item;
	}

	// display the item as char
	public char display() {
		return this.item;
	}

	public abstract String warning(); // print if player anywhere nearby

	public abstract String encounter(); // print if player land on same grid
}
