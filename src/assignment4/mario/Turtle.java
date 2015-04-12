package assignment4.mario;

import android.graphics.Rect;

public class Turtle {
	static final int vx = 8, vy = 0;
	int xTurtle = 0, yTurtle = 0;	
	boolean LR = false, dead = false;
	Rect tdst = new Rect();	// Turtle
	
	public void move() {
		if (!dead) {
			if (LR) {
				xTurtle = xTurtle + vx;
				if (xTurtle >= 4*Board.tileDimension)
					LR = false;
			} else if (!LR){
				xTurtle = xTurtle - vx;
				if (xTurtle <= -4*Board.tileDimension)
					LR = true;
			}
		}
	}
}
