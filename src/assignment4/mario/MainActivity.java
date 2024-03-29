package assignment4.mario;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private static boolean flag;
	static int level;
	Button level1, level2, level3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		goToMenu();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == level1.getId())
			level = 1;
		else if (v.getId() == level2.getId())
			level = 2;
		else if (v.getId() == level3.getId())
			level = 3;
		MarioView mv = new MarioView(getBaseContext());
		setContentView(mv);
		flag = false;
	}
	
	@Override
	public void onBackPressed() {
		if (flag)
			this.finish();		
		setContentView(R.layout.activity_main);
		goToMenu();
	}	
	
	public void goToMenu() { // Referred from example		
		level1 = (Button)findViewById(R.id.level1);
		level1.setOnClickListener(this);
		level2 = (Button)findViewById(R.id.level2);
		level2.setOnClickListener(this);
		level3 = (Button)findViewById(R.id.level3);
		level3.setOnClickListener(this);
		flag = true;
	}


}
