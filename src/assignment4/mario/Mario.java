package assignment4.mario;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Mario {
	
	static int xMario, yMario;
	static final int vx = 18, vy = 24, jumpHeight = 4*Board.tileDimension;
	static boolean rise;
	static int i = 0;
	
	public Mario() {
		xMario = 2*Board.tileDimension;
		yMario = 8*Board.tileDimension; //Ground level
		Board.marioTransform = 1;
		Board.dx = 0;
	}
	
	public static void goRight() {		
		xMario = xMario + vx;		
	}

	public static void goLeft() {		
		xMario = xMario - vx;
	}
	
	public boolean jump(int count) {
		if (vy*count <= jumpHeight) {	// Jump up
			rise = true;
			yMario = yMario - vy;
		} else {
			rise = false;
			return false;
		}
		
		return true;		
	}
	
	public void gravity() {
			yMario = yMario + vy;			
	}

	public void draw(boolean move, boolean jump, int marioTransform, Rect mdst,
			int tileDimension, int touchx, int touchy, Canvas c, MarioView mv) {		
		i++;	if (i == 100)	i = 0;	//Reset counter for animation
		
		// Transformation Type	: 0=Small 1=Big	2=Golden(Invincible)
		switch(marioTransform) {
		// Dead
		case 0:
			mdst.set(xMario, yMario, xMario + tileDimension, yMario + tileDimension);
			c.drawBitmap(mv.mariodead, null, mdst, null);
			break;
		// Regular small Mario
		case 1:
			mdst.set(xMario, yMario, xMario + tileDimension, yMario + tileDimension);
			if (touchx < (Board.xScreen/2)) {
				if (move && !jump) {
					if (i%2 == 0)
						c.drawBitmap(mv.marioRL1, null, mdst, null);
					else if (i%2 == 1)
						c.drawBitmap(mv.marioRL2, null, mdst, null);
				} else if (jump)
					c.drawBitmap(mv.marioJL, null, mdst, null);
				else
					c.drawBitmap(mv.marioSL, null, mdst, null);	
			}
				
			if (touchx >= (Board.xScreen/2)) {
				if (move && !jump) {
					if (i%2 == 0)
						c.drawBitmap(mv.marioRR1, null, mdst, null);
					else if (i%2 == 1)
						c.drawBitmap(mv.marioRR2, null, mdst, null);
				} else if (jump)
					c.drawBitmap(mv.marioJR, null, mdst, null);
				else
					c.drawBitmap(mv.marioSR, null, mdst, null);
			}
			break;
		// Big Mario after eating mushroom
		case 2:
			mdst.set(xMario, yMario - tileDimension, xMario + tileDimension, yMario + tileDimension);
			if (touchx < (Board.xScreen/2)) {
				if (move && !jump) {
					if (i%2 == 0)
						c.drawBitmap(mv.bigmarioRL1, null, mdst, null);
					else if (i%2 == 1)
						c.drawBitmap(mv.bigmarioRL2, null, mdst, null);
				} else if (jump)
					c.drawBitmap(mv.bigmarioJL, null, mdst, null);
				else
					c.drawBitmap(mv.bigmarioSL, null, mdst, null);	
			}
				
			if (touchx >= (Board.xScreen/2)) {
				if (move && !jump) {
					if (i%2 == 0)
						c.drawBitmap(mv.bigmarioRR1, null, mdst, null);
					else if (i%2 == 1)
						c.drawBitmap(mv.bigmarioRR2, null, mdst, null);
				} else if (jump)
					c.drawBitmap(mv.bigmarioJR, null, mdst, null);
				else
					c.drawBitmap(mv.bigmarioSR, null, mdst, null);
			}
			break;
		// Golden Super Mario after eating star
		case 3:
			mdst.set(xMario, yMario - tileDimension, xMario + tileDimension, yMario + tileDimension);
			if (touchx < (Board.xScreen/2)) {
				if (move && !jump) {
					if (i%2 == 0)
						c.drawBitmap(mv.goldmarioRL1, null, mdst, null);
					else if (i%2 == 1)
						c.drawBitmap(mv.goldmarioRL2, null, mdst, null);
				} else if (jump)
					c.drawBitmap(mv.goldmarioJL, null, mdst, null);
				else
					c.drawBitmap(mv.goldmarioSL, null, mdst, null);	
			}
				
			if (touchx >= (Board.xScreen/2)) {
				if (move && !jump) {
					if (i%2 == 0)
						c.drawBitmap(mv.goldmarioRR1, null, mdst, null);
					else if (i%2 == 1)
						c.drawBitmap(mv.goldmarioRR2, null, mdst, null);
				} else if (jump)
					c.drawBitmap(mv.goldmarioJR, null, mdst, null);
				else
					c.drawBitmap(mv.goldmarioSR, null, mdst, null);
			}
			break;
		}
		
	}
	
}
