package hotheart.DroidGear;

public class GameDefinition {
	public static final int GAME_GEAR = 1;
	public static final int MASTER_SYSTEM = 2;
	public static final int UNSUPPORTED = 0;
	
	public GameDefinition(String name, int type, String fname)
	{
		Caption = name;
		GameType = type;
		FileName = fname;
	}
	
	public GameDefinition(String fileName)
	{
        String[] strs = fileName.split("\\.");
        String exp = strs[strs.length - 1].toLowerCase();
        
        String name = strs[strs.length - 2];
        strs = name.split("/");
        name = strs[strs.length - 1];
        
        int type = GameDefinition.UNSUPPORTED;
        if (exp.equals("sms"))
        	type = GameDefinition.MASTER_SYSTEM;
        if (exp.equals("gg"))
        	type = GameDefinition.GAME_GEAR;
        
        this.FileName = fileName;
        this.GameType = type;
        this.Caption = name;
	}
	
	public String Caption;
	public int GameType;
	public String FileName;
}
