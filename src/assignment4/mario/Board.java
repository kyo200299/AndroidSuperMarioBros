package assignment4.mario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

//Referred from example "Board"
public class Board implements OnTouchListener {

	private MarioView mv;
	Mario mario;
	Level lv;
	final int MAX_X, MAX_Y;
	final static int MAX_TILE_X = 40, MAX_TILE_Y = 10;
	static int i, dx, marioTransform, level, lifeCount, score, tileDimension, xScreen, yScreen;
	private int xBoard, count, collisionType = 0, touchx = 1000, touchy = 1000;
	static boolean touch, move, jump, fall, goLeft, goRight, goJump, dead;
	private int placement[][];
	private Tile tile[][];
	Paint textPaint = new Paint();
	Paint gameover = new Paint();

	private static Rect dst = new Rect();		// General use
	private static Rect mdst=	new Rect();		// Mario

	public Board(MarioView mv) {		
		this.mv = mv;
		Board.level = MainActivity.level;
		tileDimension = mv.getHeight() / MAX_TILE_Y;		
		MAX_X = tileDimension * MAX_TILE_X;
		MAX_Y = tileDimension * MAX_TILE_Y;
		xScreen = mv.getWidth();
		yScreen = mv.getHeight();		
		//Display values for debugging
		System.out.println("Screen  Width  = " + mv.getWidth());
		System.out.println("Screen Height  = " + mv.getHeight());
		System.out.println("Max  Width  = " + MAX_X);
		System.out.println("Max Height  = " + MAX_Y);
		System.out.println("Tile Dimension = " + tileDimension);
		//
		placement = new int[MAX_TILE_Y][MAX_TILE_X];
		lv = new Level(level);		
		lv.levelBuild(placement);		
		tile = new Tile[MAX_TILE_Y][MAX_TILE_X];		
		for (int row = 0; row < MAX_TILE_Y; row++) {
			for (int col = 0; col < MAX_TILE_X; col++) {
				if (placement[row][col] != 0)
					tile[row][col] = new Tile( row, col, tileDimension);
				// System output for checking placement in each position
				//System.out.println("Postision("+row+", "+col+"): " + placement[row][col]);
			}
		}
		
		// Initialize Game Parameters //
		mario = new Mario();
		move = false;
		jump = false;
		fall = true;
		dead = false;
		xBoard = 0;
		count = 0;
		dx = 0;
		score = 0;
		lifeCount = 10;
		///////////////////////////////
		System.out.println("Game Board Built!");
	}
	
	public void draw(Canvas c) {
		dst.set(xBoard - dx, 0, MAX_X - dx, MAX_Y);
		c.drawBitmap(mv.background, null, dst, null);
		dst.set(0, 0, tileDimension*4/5, tileDimension*4/5);
		c.drawBitmap(mv.mariodead, null, dst, null);
		textPaint.setColor(Color.YELLOW);		
		textPaint.setTextSize(50);
		c.drawText(" X "+ lifeCount, tileDimension, tileDimension*2/3, textPaint);
		c.drawText("Score : "+ score, 7*tileDimension, tileDimension*2/3, textPaint);
		c.drawText("Level : "+ level, 13*tileDimension, tileDimension*2/3, textPaint);
		
		// Drawing items and enemies
		for (int row = 0; row < MAX_TILE_Y; row++) {
			for (int col = 0; col < MAX_TILE_X; col++) {
				if (placement[row][col] != 0)
					tile[row][col].draw(c, placement, mv, dx);
			}
		}
		
		// Drawing Mario
		mario.draw(move, jump, marioTransform, mdst, tileDimension, touchx, touchy, c, mv);
		
		gameover.setColor(Color.RED);
		gameover.setTextSize(100);
		if (lifeCount == 0 && dead)
			c.drawText("GAME OVER !!", 5*tileDimension, 5*tileDimension, gameover);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {		
		switch(event.getAction()) {			    		    	
	    case MotionEvent.ACTION_MOVE:
	    case MotionEvent.ACTION_DOWN:
	    	synchronized (this) {
	    		touchx = (int)event.getX();
	    		touchy = (int)event.getY();	    		
	    		move = true;
	    	}	    	
	    	return true;
	    case MotionEvent.ACTION_UP:
	    	move = false;
	    	return true;		    	
		}		
		return false;
	}

	public void updateWorld() {
		// Check life and transformation
		synchronized (this) {
			checkLife();
		}
		// Movement of Mario
		if (move && !dead) {			
			if (touchx >= (xScreen/2) && touchy <= (yScreen*4/5)) {
				if (goRight) {
					if(Mario.xMario < xScreen*4/7)
						Mario.goRight();
					else if (dx < (MAX_X - xScreen - Mario.vx))
						dx = dx + Mario.vx;
					else if (Mario.xMario < (xScreen - tileDimension))
						Mario.goRight();
				}
			}
			if (touchx <  (xScreen/2) && touchy <= (yScreen*4/5)) {
				if (goLeft) {
					if(Mario.xMario > xScreen*3/7)
						Mario.goLeft();	
					else if (dx > 0)
						dx = dx - Mario.vx;
					else if (Mario.xMario > 0)
						Mario.goLeft();
				}
			}
	   				   		
			if ((touchx > xScreen/2) && (touchy > yScreen*4/5) && touch) {
	   			jump = true;
	   			touch = false;
			}
   		}
   		   		
   		if (jump && goJump) {   			
   			jump = mario.jump(count);  			
   			count++;
  		} else if (fall) {
   			mario.gravity();   			
   		} else if (!fall) {
   			touch = true;
   			count = 0;
   		}
		
		// Move items and enemies
		// Look for collisions
		// Collision Type of Blocks	: 1=left 2=right 3=top 4=bottom
		// Collision Type of Enemies: 5=left 6=right 7=top 8=bottom
   		collisionType = 0;
		for (int row = 0; row < MAX_TILE_Y; row++) {
			for (int col = 0; col < MAX_TILE_X; col++) {
				if (placement[row][col] != 0) {
					tile[row][col].moveObjects(placement, mdst, dx);					
						if (collisionType == 0)
							collisionType = tile[row][col].checkCollision(collisionType, placement, mdst, dx);				
				}
			}
		}
		
		// Debug System Output
		// Flag 'jump' for touch event
		// Flag 'goJump' for jump eligibility
		//System.out.println("Collision Type	: " + collisionType);
		//System.out.println("Flag 'goLeft'	: " + goLeft);
		//System.out.println("Flag 'goRight'	: " + goRight);		
		//System.out.println("Flag 'jump'		: "	 + jump);
		//System.out.println("Flag 'goJump'	: " + goJump);
		//System.out.println("Flag 'fall'		: " + fall);
	}

	private void checkLife() {
		// Check life and transformation
		// Transformation Type		: 0=Small 1=Big	2=Golden(Invincible)
		// Collision Type of Blocks	: 1=left 2=right 3=top 4=bottom
		// Collision Type of Enemies: 5=top  6=left=right=bottom
		switch(collisionType) {
		case 0: // While in the air
			goLeft = true;
			goRight = true;
			goJump = true;
			fall = true;
			break;
		case 1:
			goLeft = true;
			goRight = false;
			goJump = true;
			fall = true;
			break;
		case 2:
			goLeft = false;
			goRight = true;
			goJump = true;
			fall = true;
			break;
		case 3:
			goLeft = true;
			goRight = true;
			goJump = true;
			fall = false;
			break;
		case 4:
			goLeft = true;
			goRight = true;
			goJump = false;
			fall = true;
			count = 1000000;
			break;
		case 5:
			goLeft = true;
			goRight = true;
			jump = true;
			goJump = true;
			fall = false;			
			System.out.println(i);
			score += 450;
			break;
		case 6:
			goLeft = false;
			goRight = false;
			goJump = false;
			if (i%2 == 1) {
				if (marioTransform > 2)
					marioTransform = 2;
				else if (marioTransform > 1)
					marioTransform = 1;
				else if (marioTransform == 1) {				
					lifeCount--;
					if (lifeCount > 0)
						mario = new Mario();
					else {
						goJump = true;
						jump = true;
					}
				}
			}
			i++;
			System.out.println(i);
			break;
		}
		if (i == 100)
			i=0;
		if (lifeCount == 0) {
			marioTransform = 0;
			dead = true;
		}
	}

}
