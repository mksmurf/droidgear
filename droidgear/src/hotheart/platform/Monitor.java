package hotheart.platform;

import hotheart.DroidGear.GameDefinition;

import java.util.Timer;

import JavaGear.Engine;
import JavaGear.Setup;
import JavaGear.Vdp;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class Monitor extends View {

	Engine e;
	GameDefinition g;

	public Monitor(Context context) {
		super(context);

		setFocusable(true);

		// For Touch and keyboard working
		// in one time
		setFocusableInTouchMode(true);
		requestFocus();
	}

	public void SetEngine(Engine eng, GameDefinition game) {
		e = eng;
		e.setupScreen(false);
		g = game;
	}

	// Keyboard update
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return false;
		
		Engine.keyCode = keyCode;
		Engine.keyPress(Engine.getKeyMap(keyCode));
		return true;
	}

	public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
		return false;
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return false;
		
		Engine.keyRelease(Engine.getKeyMap(keyCode));
		return true;
	}

	Canvas canv = null;

	@Override
	public void onDraw(Canvas canvas) {
		//Engine.engine.Execute();
		Gfx gfx = new Gfx();
		gfx.g = canvas;
		gfx.p = new Paint();
		try {
			switch (Engine.state) {
			case Engine.S_EMULATE:

				if (Engine.render != null) {
					
					Matrix matrix = new Matrix();
					
					int destWidth = Vdp.GG_WIDTH;
					int destHeight = Vdp.GG_HEIGHT;
					if (g.GameType == GameDefinition.MASTER_SYSTEM)
					{
						destWidth = Vdp.SMS_WIDTH;
						destHeight = Vdp.SMS_HEIGHT;
					}
				
					float factor = Math.min(((float)this.getWidth())/ destWidth,  ((float)this.getHeight())/destHeight);
					
					matrix.postTranslate(-Engine.scaledWidth*0.5f, -Engine.scaledHeight*0.5f);
					
					matrix.postScale(factor, factor);
					
					matrix.postTranslate(this.getWidth()*0.5f, this.getHeight()*0.5f);

					canvas.save();
					canvas.setMatrix(matrix);

					canvas.drawBitmap(Engine.render, 0, Engine.scaledWidth,
							Engine.renderX, Engine.renderY, Engine.scaledWidth,
							Engine.scaledHeight, false, null);
					canvas.restore();
					
					
					Paint p = new Paint();
					p.setColor(Color.LTGRAY);
					
					//Left
					canvas.drawRect(0, 0, 
							(canvas.getWidth() - factor*destWidth)*0.5f, 
							canvas.getHeight(), p);
					
					//Right
					canvas.drawRect(canvas.getWidth() - (canvas.getWidth() - factor*destWidth)*0.5f, 0, 
							canvas.getWidth(), 
							canvas.getHeight(), p);
					//Top
					canvas.drawRect(0, 0,
							canvas.getWidth(),
							(canvas.getHeight() - factor*destHeight)*0.5f, 
							p);
					
					//Bottom
					canvas.drawRect(0,
							canvas.getHeight() - (canvas.getHeight() - factor*destHeight)*0.5f,
							canvas.getWidth(),
							canvas.getHeight(), 
							p);
				}
				break;

			case Engine.S_MENU:
				Engine.engine.paintUI(gfx);
				break;

			case Engine.S_CLS:
				Engine.cls(gfx, 0);
				break;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		
		//invalidate();
	}
}