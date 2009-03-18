package hotheart.DroidGear;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainScreen extends Activity {
	
	 AlertDialog controlsDialog;
	 Intent gameIntent; 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        setContentView(R.layout.main);
	        
	        controlsDialog = new AlertDialog.Builder(this)
			.setMessage("Start - Space\n"+
					"Fire1 - K\n"+
					"Fire2 - L\n"+
					"Up - W\n"+
					"Left - A\n"+
					"Down - S\n"+
					"Right - D")
			.setTitle("DroidGear controls help")
			.setNeutralButton("Close", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int whichButton)
				{
				}
			})
			.create();
	        
	        gameIntent = new Intent();
	        gameIntent.setClass( this , GameListActivity.class);

	        //Setting button callbacks
	        Button bt = (Button) findViewById(R.id.exit);
	        bt.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View arg0) {
					finish();
				}
	        });
	        
	        bt = (Button) findViewById(R.id.controlHelp);
	        bt.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View arg0) {
					controlsDialog.show();
				}
	        });
	        
	        bt = (Button) findViewById(R.id.startButton);
	        bt.setOnClickListener(new OnClickListener()
	        {
				public void onClick(View arg0) {
		    		startActivity(gameIntent);
				}
	        });
	 }
}