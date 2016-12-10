package com.andro2d.framework.util;

import com.andro2d.framework.graphics.scene.SimpleView;
import com.andro2d.framework.math.Vector2D;
import com.andro2d.framework.sprite.Entity;

public final class Adaptiver {
	
	private SimpleView sview;
	
	public Adaptiver(SimpleView sview){
		this.sview = sview;
	}
	
	public float adaptWidth(float percentage){
		return sview.getWidth()*percentage;
	}
	
	public float adaptHeight(float percentage){
		return sview.getHeight()*percentage;
	}
		
	public Vector2D center(Entity e){
		Vector2D v = new Vector2D();
		v.x = (sview.getWidth()-e.width)/2;
		v.y = (sview.getHeight()-e.height)/2;
		return v;
	}
	
	public Vector2D center(int w, int h){
		Vector2D v = new Vector2D();
		v.x = (sview.getWidth()-w)/2;
		v.y = (sview.getHeight()-h)/2;
		return v;
	}
	
	public static Vector2D center(Entity e1, Entity e2){
		Vector2D v = new Vector2D();
		v.x = e1.location.x + (e1.width - e2.width)/2;
		v.y = e1.location.y + (e1.height - e2.height)/2;
		return v;
	}
}
