/**
 * 
 * @author angeliacuaca
 *
 *         Instantiate Game Class and run the game.
 * 
 *         Wumpus text-based game. Player searching for golds and avoiding pits
 *         and Wumpus.
 * 
 */

public class World {

	public static void main(String[] args) {
		/**
		 * Parameter for new Game
		 * 
		 * @param width
		 *            width of the board
		 * @param height
		 *            height of the board
		 * @param maxGolds
		 *            maximum gold the player can obtain
		 * @param numPits
		 *            number of pits in board
		 * @param numWumpus
		 *            number of Wumpus in board
		 * @param revealMode
		 *            hidden/reveal switch
		 */

		Game myGame = new Game(4, 4, 3, 3, 1, true);
		myGame.runGame();
	}

}