package com.andro2d.studio.util;

import com.andro2d.framework.core.Renderable;
import com.andro2d.framework.graphics.core.SimpleText;
import com.andro2d.framework.graphics.scene.SimpleView;
import com.andro2d.framework.sprite.AnimatedSprite;
import com.andro2d.framework.sprite.Entity;

import java.io.File;
import java.io.InputStream;
import java.util.Vector;

/**
 * Created by antony on 11/20/16.
 */

public class GameLoader {
    private SimpleView view;
    private Vector<Renderable> renderables;

    public GameLoader(SimpleView view, Vector<Renderable> renderables) {
        this.view = view;
        this.renderables = renderables;
    }

    public InputStream getXMLScene(String name) {
        return SimpleView.class.getResourceAsStream("/com/scenes/"+name+".xml");
    }

    public void loadXML(String name) {
        XMLEngine xml = new XMLEngine(getXMLScene(name));
        renderables.addAll(EngineParser.loadSimpleTexts(xml, this.view, getClass().getClassLoader()));
        renderables.addAll(EngineParser.loadAnimatedSprites(xml, this.view, getClass().getClassLoader()));
    }

    public <T extends Entity> T loadXMLObject(File f, ClassLoader loader) {
        Renderable r;
        XMLEngine xml = new XMLEngine(f);

        Vector<SimpleText> v1 = EngineParser.loadSimpleTexts(xml, this.view, loader);
        if(v1.size() > 0) {
            r = v1.get(0);
        } else {
            Vector<AnimatedSprite> v2 = EngineParser.loadAnimatedSprites(xml, this.view, loader);
            r = v2.get(0);
        }
        return (T) r;
    }

    public <T extends Entity> T loadXMLObject(String name) {
        return loadXMLObject(name, getClass().getClassLoader());
    }

    public <T extends Entity> T loadXMLObject(String name, ClassLoader loader) {
        Renderable r;
        XMLEngine xml = new XMLEngine(getXMLObject(name));

        Vector<SimpleText> v1 = EngineParser.loadSimpleTexts(xml, this.view, loader);
        if(v1.size() > 0){
            r = v1.get(0);
        }else{
            Vector<AnimatedSprite> v2 = EngineParser.loadAnimatedSprites(xml, this.view, loader);
            r = v2.get(0);
        }
        return (T) r;
    }

    public InputStream getXMLObject(String name) {
        return SimpleView.class.getResourceAsStream("/com/objects/"+name+".xml");
    }
}
