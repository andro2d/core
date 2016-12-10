package com.andro2d.framework.core;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.andro2d.framework.graphics.scene.SimpleView;
import com.andro2d.framework.sprite.Entity;

import java.util.Vector;

public class Node extends Entity {
    private Vector<Renderable> children;

    public Node(SimpleView sview) {
        super(0, 0, null, false, sview);

        this.children = new Vector<>();
    }

    public Vector<Renderable> getChildren() { return this.children; }

    public void addChild(Renderable renderable) {
        if (renderable instanceof Entity) {
            Entity entity = (Entity) renderable;
            entity.parent = this;
        }
        this.children.add(renderable);
    }

    public void removeChild(Renderable renderable) {
        ((Entity)renderable).deleteFlag = true;
    }

    public Entity getEntity(String name) {
        for(Renderable r : children) {
            if(r instanceof Entity) {
                if(((Entity) r).name.equals(name)) {
                    return (Entity)r;
                }
            }
        }
        return null;
    }

    @Override
    public void update(float fps) {
        synchronized (children) {
            for(Renderable r : children){
                if (r instanceof Entity) {
                    Entity entity = (Entity) r;

                    if (entity.deleteFlag) {
                        children.remove(r);
                        break;
                    }

                }

                r.update(fps);
            }
        }
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        synchronized (children) {
            for(Renderable r : children) {
                r.render(canvas, paint);
            }
        }
    }
}
