package assignment4.mario;

import android.graphics.Rect;

public class Cannon {
	static final int vx = 25, shootRange = 1600;
	int xCannonball = 0, yCannonball = 0;
	boolean LR = false, dead = false;
	Rect cndst = new Rect();	// Cannon
	Rect cnbdst = new Rect();	// Cannon Ball
	
	public void move(Rect mdst, int xTileIni) {
		// Cannon
		if ((cndst.exactCenterX() > mdst.exactCenterX()) &&
			(cndst.exactCenterX()-mdst.exactCenterX()) <= Board.xScreen) {
			LR = false;			
		} else if ((cndst.exactCenterX() < mdst.exactCenterX()) &&
				((cndst.exactCenterX()-mdst.exactCenterX()) >= -Board.xScreen)) {
			LR = true; 
		} else
			xCannonball = 0;
		
		// Cannon Ball
		if (dead) {
			xCannonball = 0;
			dead = false;
		} else if (!dead) {
			if ((cnbdst.exactCenterX() > xTileIni+shootRange) ||
					(cnbdst.exactCenterX() < xTileIni-shootRange)) {
				xCannonball = 0;
			} else if (LR)
				xCannonball = xCannonball + vx;
			else if (!LR)
				xCannonball = xCannonball - vx;				
		}
		

			
	}
}
