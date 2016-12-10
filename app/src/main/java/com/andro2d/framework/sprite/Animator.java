package com.andro2d.framework.sprite;

import android.graphics.Rect;

import java.util.HashMap;

public final class Animator {
	
	private SpriteSheet sheet;
	private HashMap<String, Animation> animations = new HashMap<>();
	private Animation idle;
	private String idleAnimation;
	
	public Animator(SpriteSheet sheet) {
		this.sheet = sheet;
	}
	
	public HashMap<String, Animation> getAnimations(){
		return this.animations;
	}
	
	public String getIdleAnimation() { return this.idleAnimation; }
	
	public void setIdleAnimation(String animationName) {
		if(animations.containsKey(animationName)) {
			this.idleAnimation = animationName;
			if(idle == animations.get(animationName)) {
				if(isEndOfAnimation()) sheet.reset(); 
			} else {
				idle = animations.get(animationName);
				sheet.reset();
			}
		}
	}
	
	public Animation getAnimation(String name) {
		if(animations.containsKey(name)) {
			return animations.get(name);
		}

		return null;
	}
	
	public Rect update() {
		if(animations.size() > 0 && idle != null) {
			Rect tmp = idle.play(this);

			if(tmp == null) {
				idle = idle.transition;
				tmp = idle.play(this);
				sheet.reset();
			}

			return tmp;
		}

        return null;
	}
	
	public void addAnimation(String animationName, Animation animation) {
		this.animations.put(animationName, animation);
	}
	
	public Rect playAnimation(String animationName, int framerate, boolean loop) {
		return sheet.playForward(animations.get(animationName).getRow(), framerate, loop);
	}
	
	public Rect playAnimation(int row, int col, int framerate, boolean loop) {
		return sheet.playForward(row, col, framerate,  loop);
	}
	
	public boolean isEndOfAnimation() { return sheet.isEndOfAnimation(); }
}
