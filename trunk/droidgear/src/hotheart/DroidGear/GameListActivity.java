package hotheart.DroidGear;

import java.io.FileDescriptor;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class GameListActivity extends ListActivity {
    /** Called when the activity is first created. */
	GameListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (adapter == null)
        {
        	adapter = new GameListAdapter(this);
        }
        
        setListAdapter(adapter);
        
        this.setTitle("Select a game or add new...");
        
        this.getListView().setOnCreateContextMenuListener(new OnCreateContextMenuListener()
        {

			public void onCreateContextMenu(ContextMenu menu, View v,
					final ContextMenuInfo menuInfo) {
				menu.add(0,1,0,"Remove").setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {

					public boolean onMenuItemClick(MenuItem item) {
						adapter.infos.remove(((AdapterContextMenuInfo)menuInfo).position);
						SaveData();
						setListAdapter(adapter);
						return true;
					}} );
				
				menu.add(0,2,0,"Start").setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {

					public boolean onMenuItemClick(MenuItem item) {
						//Start Game
						GameDefinition game =  adapter.infos.get(((AdapterContextMenuInfo)menuInfo).position);
						StartGame(game);
						return true;
					}} );
			}
        
        });
        
        this.getListView().setOnItemClickListener(new OnItemClickListener()
        {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				GameDefinition game =  adapter.infos.get(arg2);
				StartGame(game);
			}
        	
        });
        
    }
    
    public static final String ROM_COUNT = "RomCount";
    public static final String GAME_FILE_NAME = "GameFileName";
    
    void StartGame(GameDefinition game)
    {
    	//DroidGear act = new DroidGear();
    	Intent intent = new Intent(); 
        intent.setClass(this, DroidGear.class);
        
        Bundle b = new Bundle(); 
        b.putString(DroidGear.ROM_FILE_NAME, game.FileName); 
        intent.putExtras(b); 
        
        startActivity(intent);
    }
    
    void SaveData()
    {
    	SharedPreferences settings = getSharedPreferences("DroidGear.cfg", 0);
        SharedPreferences.Editor editor = settings.edit();
        
        int count = adapter.getCount();
        editor.putInt(ROM_COUNT, count);
        
        for(int i = 0; i < count; i++)
        {
        	GameDefinition def = adapter.infos.get(i);
        	editor.putString(GAME_FILE_NAME+Integer.toString(i), def.FileName);
        }
        
        editor.commit();
    }
    void LoadData()
    {
    	adapter.infos.clear();
    	SharedPreferences settings = getSharedPreferences("DroidGear.cfg", 0);
    	int count = settings.getInt(ROM_COUNT, 0);
                
        //editor.putInt(ROM_COUNT, count);
        
        for(int i = 0; i < count; i++)
        {
        	adapter.infos.add(new GameDefinition(settings.getString(GAME_FILE_NAME+Integer.toString(i), "")));
        }
        
        setListAdapter(adapter);
    }
    
    //GUI Add ROM
    public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Add ROM").setIcon(R.drawable.openrom);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureID, MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			OnAdd();
			return true;
		}
		return true;
	}
	
	//Rom load
	void OnAdd()
    {
    	Intent intent = new Intent("org.openintents.action.PICK_FILE");
        intent.setData(Uri.parse("file:///sdcard/"));
        intent.putExtra("org.openintents.extra.TITLE", "Please select a file");
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        String filename = data.getData().getPath();
        adapter.infos.add(new GameDefinition(filename));
        
        SaveData();
        setListAdapter(adapter);
    }

    @Override
	protected void onResume() {
		super.onResume();
		LoadData();
	}
    
	@Override
	protected void onPause() {
		super.onPause();
		SaveData();
	}
}