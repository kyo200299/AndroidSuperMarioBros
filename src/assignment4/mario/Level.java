package assignment4.mario;

public class Level {
	
	private int level;
	
	public Level(int level) {
		this.level = level;
	}
	public void levelBuild(int[][] placement) {
		// 1=Ground 2=Breakable 3=Unbreakable 4=Question
		// 5=Turtle 6=Mushroom  7=Cannon
		switch(level) {
		case 1:
			for (int row = 0; row < Board.MAX_TILE_Y; row++) {
				for (int col = 0; col < Board.MAX_TILE_X; col++) {
					placement[9][col] = 1; // Ground
					if (col >= 18 && col <= 25)
						placement[5][col] = 2; // Breakable block
					if (col >= 12  && col <= 14)
						placement[5][col] = 3; // Unbreakable block
					if (col == 6 || col == 7)
						placement[5][col] = 4; // Question block
					if (col == 30 || col == 20 || col == 24)
						placement[7][col] = 5; // Turtle enemy
					if (col == 14)
						placement[8][col] = 6; // Mushroom Enemy
					if (col == 18)
						placement[8][col] = 7; // Cannon enemy
				}
			}
			break;
		case 2:
			for (int row = 0; row < Board.MAX_TILE_Y; row++) {
				for (int col = 0; col < Board.MAX_TILE_X; col++) {
					placement[9][col] = 1; // Ground
					if (col >= 8 && col%6 == 5)
						placement[5][col] = 2; // Breakable block
					if (col >= 10 && col%6 == 3)
						placement[5][col] = 3; // Unbreakable block
					if (col >= 10 && col%6 == 4)
						placement[5][col] = 4; // Question block
					if (col >= 10 && col%8 == 3)
						placement[7][col] = 5; // Turtle enemy
					if (col >= 10 && col%8 == 5)
						placement[8][col] = 6; // Mushroom Enemy
					if (col >= 10 && col%15 == 6)
						placement[8][col] = 7; // Cannon enemy
				}
			}
			break;
		case 3:
			for (int row = 0; row < Board.MAX_TILE_Y; row++) {
				for (int col = 0; col < Board.MAX_TILE_X; col++) {
					placement[9][col] = 1; // Ground
					if (col >= 10 && col <= 13)
						placement[5][col] = 2; // Breakable block
					if (col >= 8  && col <= 9)
						placement[5][col] = 3; // Unbreakable block
					if (col == 5)
						placement[5][col] = 4; // Question block
					if (col == 22 || col == 20)
						placement[7][col] = 5; // Turtle enemy
					if (col == 17)
						placement[8][col] = 6; // Mushroom Enemy
					if (col == 10 || col == 14 || col == 28)
						placement[8][col] = 7; // Cannon enemy
				}
			}
			break;			
		}
	}
}
