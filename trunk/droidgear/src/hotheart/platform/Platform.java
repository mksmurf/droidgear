package hotheart.platform;

import hotheart.DroidGear.R;

import java.io.*;

import JavaGear.Engine;
import JavaGear.Ports;
import JavaGear.SN76489;
import JavaGear.Vdp;
import JavaGear.Z80;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.KeyEvent;

/*
    This file is part of JavaGear.
    
    Copyright (c) 2002-2008 Chris White
    All rights reserved. 
    
    Redistribution and use of this code or any derivative works are permitted
    provided that the following conditions are met: 
    
    * Redistributions may not be sold, nor may they be used in a commercial
    product or activity. 
    
    * Redistributions that are modified from the original source must include the
    complete source code, including the source code for all components used by a
    binary built from the modified sources. However, as a special exception, the
    source code distributed need not include anything that is normally distributed
    (in either source or binary form) with the major components (compiler, kernel,
    and so on) of the operating system on which the executable runs, unless that
    component itself accompanies the executable.
    
    * Redistributions must reproduce the above copyright notice, this list of
    conditions and the following disclaimer in the documentation and/or other
    materials provided with the distribution. 
    
    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
    AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
    IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
    ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
    LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
    CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
    SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
    INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
    CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
    ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
    POSSIBILITY OF SUCH DAMAGE.
*/

public class Platform
{   
    /** Debug output (console) */
    public final static boolean DEBUG = false;
    
    /** J2ME Version */
    public final static int ID = 1;
    
    // --------------------------------------------------------------------------------------------
    // Default Emulator Settings
    // --------------------------------------------------------------------------------------------
        
    /** Default frameskip value */
    public final static int DEFAULT_FRAMESKIP = 4;
    
    /** Turn throttling on by default? */
    public final static boolean DEFAULT_THROTTLE = false;
    
    /** Default minimum sleep value */
    public final static int DEFAULT_MIN_SLEEP = 0;
    
    /** Turn scale on by default? */
    public final static boolean DEFAULT_SCALE = false;
    
    /** Call Thread.yield() */
    public final static boolean THREAD_YIELD = false;
    
    /** Thread Priority */
    public final static int THREAD_PRIORITY = Thread.MAX_PRIORITY;
    
    // --------------------------------------------------------------------------------------------
    // Sound Output
    // --------------------------------------------------------------------------------------------
    
    /** Does platform support sound? */
    public final static boolean SUPPORTS_SOUND = false;
    
    /** Sample Rate */
    public final static int SAMPLE_RATE = 8000;
    
    /** Sound class (JSR-135 Only) */
    //private WavePlayer sound;

    // --------------------------------------------------------------------------------------------
    // Default Key Mappings
    // --------------------------------------------------------------------------------------------
    
    public static int 
        K_MENU_UP_MAP = KeyEvent.KEYCODE_DPAD_UP,
        K_MENU_DOWN_MAP =KeyEvent.KEYCODE_DPAD_DOWN,
        K_MENU_SELECT_MAP = KeyEvent.KEYCODE_ENTER,
    
        K_UP_MAP = KeyEvent.KEYCODE_W,
        K_DOWN_MAP = KeyEvent.KEYCODE_S,
        K_LEFT_MAP = KeyEvent.KEYCODE_A,
        K_RIGHT_MAP = KeyEvent.KEYCODE_D,
    
        K_UL_MAP = 0,
        K_UR_MAP = 0,
        K_DL_MAP = 0,
        K_DR_MAP = 0,
        
        K_FIRE1_MAP = KeyEvent.KEYCODE_K,
        K_FIRE2_MAP = KeyEvent.KEYCODE_L,
        K_START_MAP = KeyEvent.KEYCODE_SPACE,
        
        K_MENU_MAP = KeyEvent.KEYCODE_0;
    
    // --------------------------------------------------------------------------------------------
    // Panels and rendering
    // --------------------------------------------------------------------------------------------
    private Gfx gfx = new Gfx();
    
    public static int screen_width, screen_height;
    
    // --------------------------------------------------------------------------------------------
    
    Context context;
    Monitor mon;
    
    public Platform(Context cnt, Monitor m)
    {
    	context = cnt;
    	mon = m;
    }
    
    public final void exit()
    {
        Engine.engine.saveSettings();
        Engine.engine.saveSRAM();
    }
    
    // --------------------------------------------------------------------------------------------
    // Platform Specific Calls
    // --------------------------------------------------------------------------------------------
    
    public final void platformFunction(Engine e, int function)
    {
        /*switch (function)
        {                
            case Engine.PLATFORM_AUDIO_START:
                // Only initialise sound class if it exists in this java version
                if (SUPPORTS_SOUND)
                {
                    int value = 0;
                    try
                    {
                        Class.forName ("javax.microedition.media.protocol.DataSource");
                        value = 1;
                        sound = new WavePlayer();
                    }
                    catch (Exception ex)
                    {
                        sound = null;
                    }
                }
                break;
                
            case Engine.PLATFORM_AUDIO_STOP:
                //if (sound != null) sound.audioStop();
                break;      
        }*/
    }
    
    // --------------------------------------------------------------------------------------------
    // Sound Output
    // --------------------------------------------------------------------------------------------
    
    public final void audioOutput(byte[] buffer)
    {
        //if (sound != null)
        //    sound.audioOutput(buffer, 0, buffer.length);
    }
    
    // --------------------------------------------------------------------------------------------
    // Screen Rendering
    // --------------------------------------------------------------------------------------------
    
    public final void doRepaint()
    {
    	mon.postInvalidate();
        //repaint();
        //serviceRepaints();
    }

//    
//    public void paint(Graphics g) 
//    {   
//        switch (Engine.state)
//        {
//            case Engine.S_EMULATE:
//                g.drawRGB(
//                        Engine.render, 
//                        Engine.renderOffset, 
//                        Engine.scaledWidth, 
//                        Engine.renderX, Engine.renderY,
//                        Engine.renderWidth, Engine.renderHeight, false);
//                
//                if (Setup.DEBUG_TIMING)
//                {
//                    int off = 16;
//                    int y = screen_height - off;
//                    
//                    g.setColor(0);
//                    g.fillRect(0, y, screen_width, off);
//                    
//                    g.setColor(0xFFFFFF);
//                    g.drawString("Z80: "+Engine.z80Time+"  Draw: "+Engine.drawTime, 0, y, Graphics.TOP | Graphics.LEFT);
//                }
//                break;
//                
//            case Engine.S_MENU:
//                gfx.g = g;
//                Engine.engine.paintUI(gfx);
//                break;
//                
//            case Engine.S_CLS:
//                gfx.g = g;
//                Engine.cls(gfx, 0);
//                break;
//        }   
//    }
//    
    // --------------------------------------------------------------------------------------------
    // Input
    // --------------------------------------------------------------------------------------------
    
    protected final void keyPressed(int key) 
    {
        Engine.keyCode = key;
        Engine.keyPress(Engine.getKeyMap(key));
    }
    
    protected final void keyReleased(int key)
    {
        Engine.keyRelease(Engine.getKeyMap(key));
    }
    
    public final String getKeyNm(int key)
    {
       return String.format("%c", new KeyEvent(0, key).getDisplayLabel());
    }
    
    public final void pointerPressed(int x, int y)
    {
    }
    
    public void pointerReleased(int x, int y)
    {

    }
        
    // --------------------------------------------------------------------------------------------
    // File Loading Routines
    // --------------------------------------------------------------------------------------------
    
    public final InputStream getResourceAsStream(String s)
    {
    	try {
			return new FileInputStream(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    // not yet implemented...
    public final InputStream getResourceOutsideJar(String s)
    {
        return null;      
    }
    
    public final int getResourceSize(String s)
    {
        try {
        	InputStream is = getResourceAsStream(s);
			return is.available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
    }
    
    public final byte[][] getZip(Engine engine, String s)
    {
        return null;
    }
    
    public final static void initEngine(Engine e)
    {
        e.vdp = new Vdp(Engine.display);
        if (SUPPORTS_SOUND)
            e.psg = new SN76489();
        e.ports = new Ports(e.vdp, e.psg);
        e.z80 = new Z80(e.ports);            
    }
    
    // --------------------------------------------------------------------------------------------
    // Settings
    // --------------------------------------------------------------------------------------------
    public Bundle StateClass;
    public final void save(String name, byte[] data)
    {
    	StateClass.putByteArray(name, data);
    }
    
    public final byte[] load(String name)
    {
    	return StateClass.getByteArray(name);
//        RecordStore rs = RecordStore.openRecordStore(name, true); // creates if necessary
//        
//        byte[] inBytes = null;
//        
//        // Check RMS exists
//        if (rs.getNumRecords() > 0)
//            inBytes = rs.getRecord(1);
//                    
//        rs.closeRecordStore(); 
//        
//        return inBytes;      
    }     
}