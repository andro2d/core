package com.andro2d.framework.core;

import android.view.MotionEvent;

import com.andro2d.framework.graphics.scene.SimpleView;
import com.andro2d.framework.input.Touchable;
import com.andro2d.framework.sprite.Entity;

public abstract class SimpleScript implements Renderable, Touchable {

	public Renderable renderable;
	public Entity entity;
	public SimpleView sview;
	public boolean deleteFlag;
	
	public SimpleScript(Renderable renderable) {
		this.renderable = renderable;
		this.deleteFlag = false;
		if(this.renderable instanceof Entity){
			this.entity = (Entity) this.renderable;
			this.sview = this.entity.sview;
		}
	}

	@Override
	public void onTouch(MotionEvent event) {}
}
