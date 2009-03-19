package hotheart.DroidGear;

import hotheart.platform.Monitor;
import hotheart.platform.Platform;
import JavaGear.Engine;
import JavaGear.Vdp;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class DroidGear extends Activity {
	
	public static final String ROM_FILE_NAME = "RomFileName";
	
    /** Called when the activity is first created. */
	Engine engine = null;
	GameDefinition game = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        
		Bundle b = this.getIntent().getExtras(); 
        String s = b.getString(ROM_FILE_NAME);
        game = new GameDefinition(s);
        
        if (game.GameType == GameDefinition.UNSUPPORTED)
        	{
        		new AlertDialog.Builder(this)
    			.setMessage("Unsupported file type!")
    			.setNeutralButton("Close", new DialogInterface.OnClickListener()
    			{
    				public void onClick(DialogInterface dialog, int whichButton)
    				{
    					finish();
    				}
    				})
    			.create()
    			.show();
        		return;
        	}
        
        this.setTitle(game.Caption);
        
        
		
        Monitor mon = new Monitor(this);
    
        if (engine == null)
        {
        	Engine engine = new Engine(this, mon);
        	Platform.initEngine(engine);
        	mon.SetEngine(engine, game);
        	
        	if (game.GameType == GameDefinition.GAME_GEAR)
        		engine.setGG();
        	else if (game.GameType == GameDefinition.MASTER_SYSTEM) 
        		engine.setSMS();
        	
        	try
        	{
        		
            	        	
        		
        		engine.initRom(game.FileName);
        	}
        	catch(Exception e)
        	{
        		new AlertDialog.Builder(this)
    			.setMessage("Error in file :\n" + game.FileName)
    			.setNeutralButton("Close", new DialogInterface.OnClickListener()
    			{
    				public void onClick(DialogInterface dialog, int whichButton)
    				{
    					finish();
    				}
    				})
    			.create()
    			.show();
        		return;
        	}
        	//engine.start();
        }
        
        setContentView(mon);
    }

	@Override
	protected void onResume() {
		super.onResume();
		Engine.running= true;
	}
    
	@Override
	protected void onPause() {
		super.onPause();
		Engine.running= false;
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Engine.running= false;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Engine.running= false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Engine.engine.StateClass = outState;
		Engine.engine.saveState();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle outState) {
		Engine.engine.StateClass = outState;
		Engine.engine.loadState();
	}
}