package assignment4.mario;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Tile {
	private final int dimension, col, row; //Referred from example "Board"
	private int xTileIni, yTileIni, xTileFin, yTileFin;
	private int leftEdge, rightEdge, topEdge, bottomEdge;
	private int i = 0, pop;
	Turtle turtle;
	Cannon cannon;
	BadMushroom bmushroom;
	Block block;
	Rect ground;
	
	public Tile(int row, int col, int tileDimension) {
		this.row = row;
		this.col = col;
		this.dimension = tileDimension;
	}
	
	private int getRelX() {
		return col * dimension;		
	}
	
	private int getRelY() {
		return row * dimension;
	}

	public void draw(Canvas c, int[][] placement, MarioView mv, int dx) {
		i++;	if (i == 100)	i = 0;	//Reset counter for animation		
		xTileIni = getRelX() - dx;			// Initial X Position
		yTileIni = getRelY();				// Initial Y Position 
		xTileFin = xTileIni + dimension;	// Final X Position
		yTileFin = yTileIni + dimension;	// Final Y Position

		switch(placement[row][col]) {
		case 1: // Ground
			ground.set(xTileIni, yTileIni, xTileFin, yTileFin);
			break;
		case 2: // Breakable block
			block.brkdst.set(xTileIni, yTileIni, xTileFin, yTileFin);
			c.drawBitmap(mv.breakable, null, block.brkdst, null);
			break;
		case 3: // Unbreakable block
			// Item index: 0=Mushroom	(Big Mario)
			//			   1=Star 		(Golden Mario)
			if (block.itmdst != null && block.pop && !block.used) {
				block.itmdst.set(xTileIni, yTileIni+block.yItem, xTileFin, yTileFin+block.yItem);
				if (pop == 0)
					c.drawBitmap(mv.greenMushroom, null, block.itmdst, null);
				else if (pop == 1)
					c.drawBitmap(mv.mushroom, null, block.itmdst, null);
				else if (pop == 2) {
					if (i%4 == 0)
						c.drawBitmap(mv.star1, null, block.itmdst, null);
					else if (i%4 == 1)
						c.drawBitmap(mv.star2, null, block.itmdst, null);
					else if (i%4 == 2)
						c.drawBitmap(mv.star3, null, block.itmdst, null);
					else if (i%4 == 3)
						c.drawBitmap(mv.star4, null, block.itmdst, null);
				}		
			}
			
			if (block.unbrkdst == null)
				block.unbrkdst = new Rect();
			block.unbrkdst.set(xTileIni, yTileIni, xTileFin, yTileFin);
			c.drawBitmap(mv.unbreakable, null, block.unbrkdst, null);
			break;
		case 4: // Question block
			if (block.quesdst != null) {
				block.quesdst.set(xTileIni, yTileIni, xTileFin, yTileFin);
				c.drawBitmap(mv.question, null, block.quesdst, null);
			}
			break;
		case 5: // Turtle enemy
			turtle.tdst.set(xTileIni+turtle.xTurtle, yTileIni+turtle.yTurtle,
					xTileFin+turtle.xTurtle, yTileFin+turtle.yTurtle+dimension);
			if(turtle.dead) {
				turtle.tdst.set(xTileIni+turtle.xTurtle, yTileIni+dimension+turtle.yTurtle,
						xTileFin+turtle.xTurtle, yTileFin+turtle.yTurtle+dimension);
				c.drawBitmap(mv.enemyTurtleD, null, turtle.tdst, null);
			}				
			else if (turtle.LR) {
				if (i%2 == 0)
					c.drawBitmap(mv.enemyTurtleWR1, null, turtle.tdst, null);
				else if (i%2 == 1)
					c.drawBitmap(mv.enemyTurtleWR2, null, turtle.tdst, null);
			} else if (!turtle.LR) {
				if (i%2 == 0)
					c.drawBitmap(mv.enemyTurtleWL1, null, turtle.tdst, null);					
				else if (i%2 == 1)
					c.drawBitmap(mv.enemyTurtleWL2, null, turtle.tdst, null);				
			}				
			break;
		case 6: // Mushroom enemy
			bmushroom.bmdst.set(xTileIni+bmushroom.xBMushroom, yTileIni+bmushroom.yBmushroom,
					xTileFin+bmushroom.xBMushroom, yTileFin+bmushroom.yBmushroom);
			if(bmushroom.dead)
				c.drawBitmap(mv.enemyMushroomD, null, bmushroom.bmdst, null);
			else if (i%2 == 0)
				c.drawBitmap(mv.enemyMushroomW1, null, bmushroom.bmdst, null);
			else if (i%2 == 1)
				c.drawBitmap(mv.enemyMushroomW2, null, bmushroom.bmdst, null);
			break;
		case 7: // Cannon enemy
			cannon.cndst.set(xTileIni, yTileIni, xTileFin, yTileFin);
			cannon.cnbdst.set(xTileIni+cannon.xCannonball, yTileIni,
					xTileFin+cannon.xCannonball, yTileFin);
			if (cannon.LR) {
				c.drawBitmap(mv.enemyCannonballR, null, cannon.cnbdst, null);
			} else if (!cannon.LR) {
				c.drawBitmap(mv.enemyCannonballL, null, cannon.cnbdst, null);
			}
			c.drawBitmap(mv.enemyCannon, null, cannon.cndst, null);
			break;
		case 8: // Items
		
			break;
		}		
	}
	
	public int checkCollision(int collisionType, int[][] placement, Rect mdst, int dx) {
		xTileIni 	= getRelX() - dx;		// Initial X Position
		yTileIni 	= getRelY();			// Initial Y Position 
		xTileFin 	= xTileIni + dimension;	// Final X Position
		yTileFin	= yTileIni + dimension;	// Final Y Position
		leftEdge	= xTileIni - dimension + 1;	// Left edge
		rightEdge	= xTileIni + dimension;	// Right edge
		topEdge		= yTileIni - dimension;	// Top edge		
		bottomEdge	= yTileIni + dimension;	// Bottom edge

		// Collision Type of Blocks	: 1=left 2=right 3=top 4=bottom
		// Collision Type of Enemies: 5=top  6=left=right=bottom
		switch(placement[row][col]) {
		case 1: // Ground
			if (Mario.yMario + dimension >= yTileIni) {
				Mario.yMario = yTileIni - dimension;
				collisionType = 3;
			}			
			break;
			
		case 2: // Breakable block
			if ((Mario.yMario+dimension == yTileIni) && (Mario.xMario >= leftEdge) && (Mario.xMario <= rightEdge))
				collisionType = 3;
			else if ((Mario.yMario == yTileFin) && (Mario.xMario >= leftEdge) && (Mario.xMario <= rightEdge)
					&& Mario.rise) {
				if (Board.marioTransform > 1) {
					placement[row][col] = 0;
					block.brkdst = null;
				}
				if (Board.marioTransform > 1) {
					if (Mario.yMario-dimension<= yTileFin)
						collisionType = 4;
				} else
					collisionType = 4;
			}else if ((Mario.xMario+dimension == xTileIni) && (Mario.yMario >= topEdge) && (Mario.yMario <= bottomEdge))
				collisionType = 1;
			else if ((Mario.xMario == xTileFin) && (Mario.yMario >= topEdge) && (Mario.yMario <= bottomEdge))
				collisionType = 2;
			else
				collisionType = 0;			
			break;
			
		case 3: // Unbreakable block
			if ((Mario.yMario+dimension == yTileIni) && (Mario.xMario >= leftEdge) && (Mario.xMario <= rightEdge))
				collisionType = 3;
			else if ((Mario.yMario == yTileFin) && (Mario.xMario >= leftEdge) && (Mario.xMario <= rightEdge)
					&& Mario.rise)
				if (Board.marioTransform > 1) {
					if (Mario.yMario-dimension <= yTileFin)
						collisionType = 4;
				} else
					collisionType = 4;
			else if ((Mario.xMario+dimension == xTileIni) && (Mario.yMario >= topEdge) && (Mario.yMario <= bottomEdge))
				collisionType = 1;
			else if ((Mario.xMario == xTileFin) && (Mario.yMario >= topEdge) && (Mario.yMario <= bottomEdge))
				collisionType = 2;
			else
				collisionType = 0;
			
			if (Rect.intersects(block.itmdst, mdst) && !block.used && block.stop) {			
				if (pop == 0) {
					Board.lifeCount++;
					Board.score += 4000;
				}
				if (pop == 1) {
					Board.score += 1200;
					if (Board.marioTransform < 2)
						Board.marioTransform = 2;
				}
				if (pop == 2) {
					Board.score += 1500;
					if (Board.marioTransform < 3)
						Board.marioTransform = 3;								
				}
				block.used = true;
			}
			
			break;
			
		case 4: // Question block
			if ((Mario.yMario+dimension == yTileIni) && (Mario.xMario >= leftEdge) && (Mario.xMario <= rightEdge))
				collisionType = 3;
			else if ((Mario.yMario == yTileFin) && (Mario.xMario >= leftEdge) && (Mario.xMario <= rightEdge)
					&& Mario.rise) {
				if (!block.pop)
					pop = block.popItem();
				//System.out.println("Item poped: " + pop);
				placement[row][col] = 3;
				block.quesdst = null;
				
				if (Board.marioTransform > 1) {
					if (Mario.yMario-dimension <= yTileFin)
						collisionType = 4;
				} else
					collisionType = 4;				
			} else if ((Mario.xMario+dimension == xTileIni) && (Mario.yMario >= topEdge) && (Mario.yMario <= bottomEdge))
				collisionType = 1;
			else if ((Mario.xMario == xTileFin) && (Mario.yMario >= topEdge) && (Mario.yMario <= bottomEdge))
				collisionType = 2;
			else
				collisionType = 0;			
			break;
			
		case 5: // Turtle enemy
			if (Rect.intersects(turtle.tdst, mdst) && !turtle.dead
					&& Mario.yMario+36 < turtle.tdst.exactCenterY()) {
				turtle.dead = true;
				collisionType = 5;
			} else if (Rect.intersects(turtle.tdst, mdst) && !turtle.dead)
				collisionType = 6;
			else
				collisionType = 0;			
			break;
			
		case 6: // Mushroom enemy
			if (Rect.intersects(bmushroom.bmdst, mdst) && !bmushroom.dead
					&& Mario.yMario < bmushroom.bmdst.exactCenterY()) {
				bmushroom.dead = true;
				collisionType = 5;
			} else if (Rect.intersects(bmushroom.bmdst, mdst) && !bmushroom.dead)
				collisionType = 6;
			else
				collisionType = 0;			
			break;
			
		case 7: // Cannon enemy
			if ((Mario.yMario+dimension == yTileIni) && (Mario.xMario >= leftEdge) && (Mario.xMario <= rightEdge))
				collisionType = 3;
			else if ((Mario.xMario+dimension == xTileIni) && (Mario.yMario >= topEdge) && (Mario.yMario <= bottomEdge)
					&& !Mario.rise) {
				Mario.yMario = yTileIni;
				collisionType = 1;
			} else if ((Mario.xMario == xTileFin) && (Mario.yMario >= topEdge) && (Mario.yMario <= bottomEdge)
					&& !Mario.rise) {
				Mario.yMario = yTileIni;
				collisionType = 2;
			} else
				collisionType = 0;
			
			if (Rect.intersects(cannon.cnbdst, mdst) && !cannon.dead
					&& Mario.yMario+36 < cannon.cnbdst.exactCenterY()) {
				collisionType = 5;
				cannon.dead = true;
			} else if (Rect.intersects(cannon.cnbdst, mdst)
					&& !cannon.dead) {
				if (Board.marioTransform != 3)
					collisionType = 6;
				cannon.dead = true;
			}
			break;
		}

		return collisionType;
	}
	
	public void moveObjects(int[][] placement, Rect mdst, int dx) {		
		xTileIni = getRelX() - dx;			// Initial X Position
		yTileIni = getRelY();				// Initial Y Position 
		
		switch(placement[row][col]) {
		case 1: // Ground
			if (ground == null)
				ground = new Rect();
			break;
		case 2: // Breakable block
			if (block == null)
				block = new Block();
			break;
		case 3: // Unbreakable block
			if (block == null)
				block = new Block();
			if (block.itmdst != null && !block.stop)
				block.moveItem();				
			break;
		case 4: // Question block
			if (block == null)
				block = new Block();
			break;
		case 5: // Turtle enemy
			if (turtle == null)
				turtle = new Turtle();
			turtle.move();
			break;
		case 6: // Mushroom enemy
			if (bmushroom == null)
				bmushroom = new BadMushroom();
			bmushroom.move();
			break;
		case 7: // Cannon enemy
			if (cannon == null)
				cannon = new Cannon();
			cannon.move(mdst, xTileIni);
			break;
		}
	}
	
}
