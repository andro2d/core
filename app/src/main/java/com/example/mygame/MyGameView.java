package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.andro2d.framework.core.ImageResource;
import com.andro2d.framework.core.Node;
import com.andro2d.framework.graphics.scene.SimpleView;
import com.andro2d.framework.sprite.AnimatedSprite;
import com.andro2d.framework.sprite.Animation;

public class MyGameView extends SimpleView {

    Node myNode;
    AnimatedSprite sp;

    public MyGameView(Context context) {
        super(context);

        myNode = new Node(this);

        sp = new AnimatedSprite(500, 500, 4, 15,
                new ImageResource("sprite", "player", this), this);
        sp.getAnimator().addAnimation("row1", new Animation(0, 6, 5, true));
        sp.getAnimator().addAnimation("row2", new Animation(1, 15, 2, true));
        sp.getAnimator().setIdleAnimation("row1");
        sp.flipHorizontal = true;
        myNode.addChild(sp);
        addRenderable(myNode);
    }

    @Override
    public void preRender(Canvas canvas, Paint paint) {
        super.preRender(canvas, paint);
        canvas.drawColor(Color.GRAY);
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
    }

    @Override
    public void update(float delta) {
        sp.location.x += (100 / delta);
        sp.location.y += (100 / delta);
    }

}
