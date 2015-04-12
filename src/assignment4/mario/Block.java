package assignment4.mario;

import java.util.Random;

import android.graphics.Rect;

public class Block {
	public int xItem = 0, yItem = 0, index, count;
	static final int vx = 0, vy = 12;
	boolean pop = false, used = false, stop = false;
	Rect brkdst = new Rect();	// Breakable Block
	Rect unbrkdst = new Rect();	// Unbreakbale Block
	Rect quesdst = new Rect();	// Question Block
	Rect itmdst = new Rect();	// Items
	Random r = new Random();
	
	// Item index: 0=Green Mushroom	(Life +1)
	//			   1=Mushroom 		(Big Mario)
	//			   2=Star			(Golden Mario)
	public int popItem() {
		pop = true;		
		
		index = r.nextInt(3);
		return index;
	}
	
	public void moveItem() {
		if (vy*count <= Board.tileDimension) {
			count++;
			yItem = yItem - vy;
			stop = false;
		} else
			stop = true;
	}

}
