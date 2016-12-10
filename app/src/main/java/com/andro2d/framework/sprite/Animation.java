package com.andro2d.framework.sprite;

import android.graphics.Rect;

public class Animation {
	private int row, col, framerate;
	private boolean loop;
	protected Animation transition;
	
	public Animation(int row, int col, int framerate, boolean loop){
		this.row = row;
		this.col = col;
		this.loop = loop;
		this.framerate = framerate;
	}
	
	public int getRow() { return this.row; }

    public int getCol() { return this.col; }

    public int getFrameRate() { return this.framerate; }

    public boolean isLooping() { return this.loop; }
	
	public Rect play(Animator animator) {
		if(!loop && animator.isEndOfAnimation() && transition != null) {
			return null;
		}
		return animator.playAnimation(row, col, framerate, loop);
	}
}
