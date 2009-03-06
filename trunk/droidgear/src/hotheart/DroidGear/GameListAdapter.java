package hotheart.DroidGear;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GameListAdapter extends BaseAdapter {

    /** Remember our context so we can use it when constructing views. */
    private Context mContext;
    public List<GameDefinition> infos = new ArrayList<GameDefinition>();
    Drawable gameGearIcon, masterSystemIcon, unsupportedIcon;
    
    public GameListAdapter(Context context) {
         mContext = context;

         gameGearIcon = mContext.getResources().getDrawable(R.drawable.gamegear);
         masterSystemIcon = mContext.getResources().getDrawable(R.drawable.mastersystem);
         unsupportedIcon = mContext.getResources().getDrawable(R.drawable.unsupported);
    }

    public int getCount() { return infos.size(); }

    public Object getItem(int position) { return infos.get(position); }

    public boolean areAllItemsSelectable() { return false; }

    public boolean isSelectable(int position) {
            return true;
    }

    /** Use the array index as a unique id. */
    public long getItemId(int position) {
         return position;
    }

    /** @param convertView The old view to overwrite, if one is passed
     * @returns a IconifiedTextView that holds wraps around an IconifiedText */
    public View getView(int position, View convertView, ViewGroup parent) {
    	View res = null;
    	
    	 if (convertView == null) {
    		 LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
    	     res = inflater.inflate(R.layout.game, null);
         } else { // Reuse/Overwrite the View passed
        	 res =  convertView;
         }
    	 
    	 GameDefinition data = (GameDefinition)getItem(position);
    	 
    	 TextView caption =  (TextView) res.findViewById(R.id.caption);
    	 caption.setText(data.Caption);
    	 
    	 TextView gameType = (TextView) res.findViewById(R.id.gameType);
    	 ImageView consoleIcon = (ImageView) res.findViewById(R.id.consoleIcon);

    	 switch (data.GameType)
    	 {
    	 	case GameDefinition.MASTER_SYSTEM:
    	 		consoleIcon.setImageDrawable(masterSystemIcon);
    	 		gameType.setText("Sega Master System");
    	 		break;
    	 	case GameDefinition.GAME_GEAR:
    	 		consoleIcon.setImageDrawable(gameGearIcon);
    	 		gameType.setText("Sega Game Gear");
    	 		break;
    	 	default:
    	 		consoleIcon.setImageDrawable(unsupportedIcon);
    	 	    gameType.setText("Unsupported");
    	 		break;
    	 }
         return res;
    }
}
