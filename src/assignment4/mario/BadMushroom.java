package assignment4.mario;

import android.graphics.Rect;

public class BadMushroom {
	static final int vx = 12, vy = 0;
	int xBMushroom = 0, yBmushroom = 0;
	boolean LR = false, dead = false;
	Rect bmdst = new Rect();	// Bad Mushroom
	
	public void move() {
		if (!dead) {
			if (LR) {
				xBMushroom = xBMushroom + vx;
				if (xBMushroom >= 2*Board.tileDimension)
					LR = false;
			} else if (!LR){
				xBMushroom = xBMushroom - vx;
				if (xBMushroom <= -2*Board.tileDimension)
					LR = true;
			}
		}
	}
}
