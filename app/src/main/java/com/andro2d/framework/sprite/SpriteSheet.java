package com.andro2d.framework.sprite;

import android.graphics.Bitmap;
import android.graphics.Rect;

public final class SpriteSheet {
	
	private Bitmap image;
	private int col, width, height, frames;
	private int count;
	private boolean endOfAnimation = false;

	public SpriteSheet(Bitmap image, int row, int col) {
		this.image = image;
		this.col = col;
		this.width = this.image.getWidth() / col;
		this.height = this.image.getHeight() / row;
	}

	public boolean isEndOfAnimation() { return this.endOfAnimation; }

    public void reset() {
        frames = count = 0;
        endOfAnimation = false;
    }
	
	public Rect getImage(int row, int col) {
		int srcX = col * width;
		int srcY = row * height;
		return new Rect(srcX, srcY, srcX+width, srcY+height);
	}
	
	public Rect playForward(int row, int col, int framerate, boolean loop) {
		frames++;

		if(frames == framerate) {
			frames = 0;
			count++;

			if(count == col) {
				if(!loop) {
					count = col - 1;
				} else {
					count = 0;
				}

				endOfAnimation = true;
			}
		}

		if(count == col && !loop) { count = col - 1; }

		return getImage(row, count);
	}

	public Rect playForward(int row, int framerate, boolean loop) {
		return playForward(row, this.col, framerate, loop);
	}
}
