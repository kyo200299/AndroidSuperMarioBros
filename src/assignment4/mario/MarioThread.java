package assignment4.mario;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MarioThread extends Thread {
		
	final Board board;
	final SurfaceHolder sh;
	boolean gamestillrunning = true;
	
	public MarioThread(SurfaceHolder sh, Board board) {
		this.board = board;
		this.sh = sh;
	}
	
	@Override
	public void run() {			
		while(gamestillrunning) {
			
    		board.updateWorld();

			Canvas c=sh.lockCanvas();
			
			if (c!=null) {
				board.draw(c);
				sh.unlockCanvasAndPost(c);
			}	
		}		
	}
	
	// Terminating the game
	public synchronized void stopGame() {
		gamestillrunning = false;
	}	
}
