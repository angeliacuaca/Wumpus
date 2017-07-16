import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author angeliacuaca
 * 
 *         Game Class This class contains logic and computation of the game, as
 *         well as user input.
 *
 */
public class Game {

	// By default
	private int width; // board width
	private int height; // board height
	private int maxGolds; // gold from 1 to maxGolds
	private int numPits; // number of pits
	private int numWumpus; // number of Wumpus
	private boolean revealMode; // reveal or hidden switch

	// board and item position
	private GameItem[][] revealBoard;
	private GameItem[][] hiddenBoard;

	// Game Item
	private final GameItem ground = new ClearGround('.');
	private final GameItem gold = new Gold('g');
	private final GameItem pit = new Pit('p');
	private final GameItem wumpus = new Wumpus('W');

	// direction where player move
	private final static int MOVE_LEFT = 1;
	private final static int MOVE_RIGHT = 2;
	private final static int MOVE_UP = 3;
	private final static int MOVE_DOWN = 4;

	// player character and positions
	private final char player = '*';
	private int pX;
	private int pY;
	private int score = 0;

	// game messages
	private final String gameOver = "\nxxxxxxxxxxxxxxxxxxxxx GAMEOVER xxxxxxxxxxxxxxxxxxxxxxxx";
	Scanner reader = new Scanner(System.in);
	Random random = new Random();

	/**
	 * Constructor of Game class
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
	public Game(int width, int height, int maxGolds, int numPits,
			int numWumpus, boolean revealMode) {
		this.width = width;
		this.height = height;
		this.maxGolds = maxGolds;
		this.numPits = numPits;
		this.numWumpus = numWumpus;
		this.revealMode = revealMode;

		revealBoard = new GameItem[width][height];
		hiddenBoard = new GameItem[width][height];
	}

	// Fill the board with random game items
	private void setBoard() {

		// To fill either hidden or revealed board with '.'
		for (GameItem[] row : revealBoard)
			Arrays.fill(row, ground);

		for (GameItem[] row : hiddenBoard)
			Arrays.fill(row, ground);

		// place Wumpus
		for (int i = 0; i < numWumpus; i++)
			placeItem(wumpus);

		// place Pits
		for (int i = 0; i < numPits; i++)
			placeItem(pit);

		// place Golds up to 3 pieces (Random)
		int numGolds = 0;
		while (numGolds == 0)
			numGolds = random.nextInt(maxGolds + 1);
		for (int i = 0; i < numGolds; i++)
			placeItem(gold);

		// generate random coordinate for player
		do {
			pX = random.nextInt(width);
			pY = random.nextInt(height);
		} while (revealBoard[pX][pY] != ground);

	}

	/**
	 * Place game items on board
	 * 
	 * @param item
	 *            GameItem to place
	 */
	private void placeItem(GameItem item) {

		// generate random on x y coordinate
		int randX, randY;
		do {
			randX = random.nextInt(width);
			randY = random.nextInt(height);
		} while (revealBoard[randX][randY] != ground); // if occupied

		revealBoard[randX][randY] = item; // assign item on position
	}

	// print dash
	private void printDash() {
		int k = 1;

		while (k <= 20) {
			System.out.print('-');
			k++;
		}
		System.out.println();
	}

	/**
	 * Display the game items and player
	 * 
	 * @param reveal
	 *            if true, show item. if false, show only ground and player
	 */
	private void display(boolean reveal) {

		if (reveal == true) {
			System.out.println("Reveal Mode");
			printDash();
			for (int i = 0; i < revealBoard.length; i++) {

				for (int j = 0; j < revealBoard[i].length; j++) {

					// if it's player position
					if (i == pX && j == pY)
						System.out.print("[" + player + "]");
					else
						System.out.print("[" + revealBoard[i][j].display()
								+ "]");
				}

				System.out.println();
			}
		}

		// if player choose Hidden mode
		else {
			System.out.println("Hidden Mode");
			printDash();
			for (int i = 0; i < hiddenBoard.length; i++) {

				for (int j = 0; j < hiddenBoard[i].length; j++) {

					// if it's player position
					if (i == pX && j == pY)
						System.out.print("[" + player + "]");
					else
						System.out.print("[" + hiddenBoard[i][j].display()
								+ "]");
				}

				System.out.println();
			}
		}

		printDash();
	}

	/**
	 * Preventing grid from out of bound
	 * 
	 * @param dir
	 *            direction to consider
	 * @param bound
	 *            maximum bound of the board
	 * @return
	 */
	private int bound(int dir, int bound) {
		if (dir == -1)
			return bound - 1;
		else if (dir == bound)
			return 0;
		else
			return dir;
	}

	// display message what is nearby (immediate surrounding)
	private void senseNearby() {

		// surrounding grid Left,Right,Up,Down
		int senseL = bound(pY - 1, width);
		int senseR = bound(pY + 1, width);
		int senseU = bound(pX - 1, height);
		int senseD = bound(pX + 1, height);

		// assign gameItem surrounding player
		GameItem[] sensed = { revealBoard[pX][senseL], revealBoard[pX][senseR],
				revealBoard[senseU][pY], revealBoard[senseD][pY] };

		// if game item other than clear ground detected
		for (int i = 0; i < sensed.length; i++) {
			if (sensed[i] != ground)
				System.out.println(sensed[i].warning());
		}

	}

	// print menu and obtain user input
	private void menu() {
		System.out
				.println("\n*********************** WUMPUS ***********************");
		System.out.println("1. Move player left");
		System.out.println("2. Move player right");
		System.out.println("3. Move player up");
		System.out.println("4. Move player down");
		System.out.println("5. Toggle Visibility");
		System.out.println();
	}

	/**
	 * process user input
	 * 
	 * @param direction
	 *            user input
	 */
	public void movePlayer(int direction) {
		GameItem death;

		switch (direction) {
		case MOVE_LEFT:
			pY = bound(pY - 1, width);
			break;
		case MOVE_RIGHT:
			pY = bound(pY + 1, width);
			break;
		case MOVE_UP:
			pX = bound(pX - 1, height);
			break;
		case MOVE_DOWN:
			pX = bound(pX + 1, height);
			break;
		case 5:
			if (revealMode == true)
				revealMode = false;
			else
				revealMode = true;
			break;
		}

		if (revealBoard[pX][pY] != ground) {

			// if player found gold, game still running
			if (revealBoard[pX][pY] == gold) {
				System.out.println(gold.encounter());
				score = score + 1;
				revealBoard[pX][pY] = ground;
				System.out.println("Score: " + score);
			}
			// else other options lead to game over
			else {
				death = revealBoard[pX][pY];
				System.out.println(death.encounter());
				System.out.println(gameOver);
				System.exit(1);
			}
		}
	}

	// Run the game
	public void runGame() {
		int input = 0; // user input

		setBoard(); // fill board with game Items

		do {
			movePlayer(input); // move the player
			display(revealMode); // display/update the board
			senseNearby(); // sense nearby game item
			menu(); // print menu

			try {
				input = reader.nextInt();
			} catch (InputMismatchException nfe) {
				System.out
						.println("ERROR: Please input only number, try again");
			}

		} while (input > 0 && input < 6);
	}

}
