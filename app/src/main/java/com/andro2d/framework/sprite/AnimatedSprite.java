package com.andro2d.framework.sprite;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.andro2d.framework.core.ImageResource;
import com.andro2d.framework.graphics.scene.SimpleView;

public class AnimatedSprite extends Entity {
	private Animator animator;
    private int row, col;

	public AnimatedSprite(int width, int height, int row, int col, ImageResource irsc,
			SimpleView sview) {
		super(width, height, irsc.getImage(), false, sview);
        this.row = row;
        this.col = col;
        SpriteSheet sheet = new SpriteSheet(irsc.getImage(), row, col);
		this.animator = new Animator(sheet);
	}
	
	public Animator getAnimator() { return this.animator; }

	public int getRow() { return this.row; }

	public int getCol() { return this.col; }

	@Override
	public void update(float delta) {
		super.update(delta);
		src = this.animator.update();
	}

	@Override
	public void render(Canvas canvas, Paint paint) {
        super.render(canvas, paint);
	}
}
