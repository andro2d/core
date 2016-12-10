package com.andro2d.framework.graphics.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.andro2d.framework.graphics.scene.SimpleView;
import com.andro2d.framework.math.Vector2D;
import com.andro2d.framework.sprite.Entity;

public final class SimpleText extends Entity {
	
	private static final long serialVersionUID = 1L;
	public int color;
	private SimpleFont font;
	private String text;
	
	public SimpleText(String text, int color, Vector2D location, SimpleFont font, SimpleView sview) {
		super(100, 100, null, false, sview);
		this.text = text;
		this.color = color;
		this.location = location;
		this.font = font;
	}
	
	public SimpleFont getSimpleFont(){
		return this.font;
	}
	
	public String getText(){
		return this.text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public SimpleText(String text, SimpleView sview){
		this(text, new SimpleFont(Typeface.create("Arial Black", Typeface.NORMAL), 12, Typeface.NORMAL), sview);
	}
	
	public SimpleText(String text, SimpleFont font, SimpleView sview){
		this(text, Color.WHITE, new Vector2D(), font, sview);
	}
	
	public void setFontStyle(int style){
		this.font.style = style;
	}
	
	public void setFontFamily(String resourceName){
		font.typeface = Typeface.createFromAsset(sview.getActivity().getAssets(), resourceName);
	}
	
	public void setFontSize(int size){
		this.font.size = size;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		if(!render) {
			return;
		}

		super.render(canvas, paint);
		paint.setTextSize(font.size);
		paint.setColor(color);
		paint.setTypeface(font.typeface);
		canvas.drawText(text, location.x, location.y+height/1.5f, paint);
	}
}
