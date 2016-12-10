package com.andro2d.studio.util;


import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;

import com.andro2d.framework.core.GameResource;
import com.andro2d.framework.core.ImageResource;
import com.andro2d.framework.graphics.core.SimpleFont;
import com.andro2d.framework.graphics.core.SimpleText;
import com.andro2d.framework.graphics.scene.SimpleView;
import com.andro2d.framework.math.Vector2D;
import com.andro2d.framework.sprite.AnimatedSprite;
import com.andro2d.framework.sprite.Animation;
import com.andro2d.framework.sprite.Entity;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Map.Entry;
import java.util.Vector;

public class EngineParser {
    //Parser to XML
    public static void parseEntity(XMLEngine xml, Element parent, Entity e){
        Element eEntity = xml.appendChild(parent, "entity");
        xml.addAttr(eEntity, "name", e.name);
        xml.addAttr(eEntity, "width", e.width+"");
        xml.addAttr(eEntity, "height", e.height+"");
        xml.addAttr(eEntity, "centerRotation", e.centerRotation +"");
        xml.addAttr(eEntity, "render", e.render+"");
        parseVector2D("location", xml, eEntity, e.location);
        //parseSimpleScripts(xml, eEntity, e.getScripts());
    }
//    public static void parseSimpleScripts(XMLEngine xml, Element parent, HashMap<String, SimpleScript> maps){
//    	Element eScripts = xml.appendChild(parent, "scripts");
//    	for(Entry<String, SimpleScript> en : maps.entrySet()){
//    		Element eScript = xml.appendChild(eScripts, "script");
//    		xml.addAttr(eScript, "class", en.getValue().getClass().getName());
//    		xml.addAttr(eScript, "name", en.getKey());
//    	}
//    }
    public static void parseAnimatedSprite(XMLEngine xml, Element parent, AnimatedSprite as, String path, String resname){
    	Element eas = xml.appendChild(parent, "animatedsprite");
    	parseEntity(xml, eas, as);
    	Element espritesheet = xml.appendChild(eas, "spritesheet");
    	xml.addAttr(espritesheet, "path", path);
    	xml.addAttr(espritesheet, "resname", resname);
    	xml.addAttr(espritesheet, "row", as.getRow() + "");
    	xml.addAttr(espritesheet, "col", as.getCol() + "");
    	Element eanimator = xml.appendChild(eas, "animator");
    	xml.addAttr(eanimator, "idle", as.getAnimator().getIdleAnimation());
    	for(Entry<String, Animation> entry : as.getAnimator().getAnimations().entrySet()){
    		Element eanimation = xml.appendChild(eanimator, "animation");
    		xml.addAttr(eanimation, "name", entry.getKey());
    		xml.addAttr(eanimation, "row", entry.getValue().getRow() + "");
    		xml.addAttr(eanimation, "col", entry.getValue().getCol() + "");
    		xml.addAttr(eanimation, "framerate", entry.getValue().getFrameRate() + "");
    		xml.addAttr(eanimation, "loop", entry.getValue().isLooping() + "");
    	}
    }
//    public static void parseSimpleText(XMLEngine xml, Element parent, SimpleText st){
//        Element eSimpleText = xml.appendChild(parent, "simpletext");
//        xml.addAttr(eSimpleText, "text", st.getText());
//        //SimpleText is Entity, so parse it too
//        parseEntity(xml, eSimpleText, st);
//        parseColor(xml, eSimpleText, st.color);
//        parseFont(xml, eSimpleText, st.getFont());
//    }
    public static void parseColor(XMLEngine xml, Element parent, int color){
        Element ecol = xml.appendChild(parent, "color");
        xml.addAttr(ecol, "red", Color.red(color)+"");
        xml.addAttr(ecol, "green", Color.green(color)+"");
        xml.addAttr(ecol, "blue", Color.blue(color)+"");
    }
    public static void parseVector2D(String id, XMLEngine xml, Element parent, Vector2D v2d){
        Element ev2d = xml.appendChild(parent, "vector2d");
        xml.addAttr(ev2d, "id", id);
        xml.addAttr(ev2d, "x", ""+v2d.x);
        xml.addAttr(ev2d, "y", ""+v2d.y);
    }
//    public static void parseFont(XMLEngine xml, Element parent, Font font){
//    	Element efont = xml.appendChild(parent, "font");
//    	xml.addAttr(efont, "name", font.getName());
//    	xml.addAttr(efont, "style", font.getStyle()+"");
//    	xml.addAttr(efont, "size", font.getSize()+"");
//    }
    
    //XML to Object load
    public static Vector<AnimatedSprite> loadAnimatedSprites(XMLEngine xml, SimpleView sview, ClassLoader loader){
    	Vector<AnimatedSprite> v = new Vector<AnimatedSprite>();
    	NodeList list = xml.getElements("animatedsprite");
    	for(int i=0;i<list.getLength();i++){
    		Node n = list.item(i);
    		Element eanimsp = (Element) n;
    		Element eentity = xml.getElement(eanimsp, "entity");
    		Element espritesheet = xml.getElement(eanimsp, "spritesheet");
    		Element eanimator = xml.getElement(eanimsp, "animator");
    		GameResource.getInstance().addBitmap(espritesheet.getAttribute("resname"), espritesheet.getAttribute("path"), sview);
    		int row = Integer.parseInt(espritesheet.getAttribute("row"));
    		int col = Integer.parseInt(espritesheet.getAttribute("col"));
    		int w = Integer.parseInt(eentity.getAttribute("width"));
    		int h = Integer.parseInt(eentity.getAttribute("height"));
    		AnimatedSprite as = new AnimatedSprite(w, h, row, col, 
    				new ImageResource(espritesheet.getAttribute("resname"), espritesheet.getAttribute("path"), sview), sview);
    		loadEntity(xml, eanimsp, as, loader);
    		
    		//Animator
    		NodeList listanimations = xml.getElements(eanimator, "animation");
    		for(int j = 0 ; j < listanimations.getLength() ; j++){
    			Element eanimation = (Element)listanimations.item(j);
    			int arow = Integer.parseInt(eanimation.getAttribute("row"));
    			int acol = Integer.parseInt(eanimation.getAttribute("col"));
    			int framerate = Integer.parseInt(eanimation.getAttribute("framerate"));
    			boolean loop = Boolean.parseBoolean(eanimation.getAttribute("loop"));
    			Animation anim = new Animation(arow, acol, framerate, loop);
    			as.getAnimator().addAnimation(eanimation.getAttribute("name"), anim);
    		}
    		as.getAnimator().setIdleAnimation(eanimator.getAttribute("idle"));
    		v.add(as);
    	}
    	return v;
    }

    public static Vector<SimpleText> loadSimpleTexts(XMLEngine xml, SimpleView sview, ClassLoader loader){
    	Vector<SimpleText> v = new Vector<SimpleText>();
    	NodeList list = xml.getElements("simpletext");
    	for(int i=0;i<list.getLength();i++){
    		Node n = list.item(i);
    		Element e = (Element) n;
    		String text = e.getAttribute("text");
    		//Get color
    		int c = loadColor(xml, e);
    		SimpleFont sf = loadSimpleFont(xml, e, sview.getActivity().getAssets());
    		//Get entity
    		SimpleText s = new SimpleText(text, c, new Vector2D(), sf, sview);
    		loadEntity(xml, e, s, loader);
    		v.add(s);
    	}
    	return v;
    }
    public static SimpleFont loadSimpleFont(XMLEngine xml, Element parent, AssetManager assets){
    	Element efont = xml.getElement(parent, "font");
    	String name = efont.getAttribute("name");
    	int style = Integer.parseInt(efont.getAttribute("style"));
    	int size = Integer.parseInt(efont.getAttribute("size"));
    	SimpleFont sf = new SimpleFont(Typeface.create(name, Typeface.NORMAL), size, style);
    	return sf;
    }
    
    
    public static void loadEntity(XMLEngine xml, Element parent, Entity entity, ClassLoader loader){
    	Element eEntity = xml.getElement(parent, "entity");
    	entity.name = eEntity.getAttribute("name");
    	entity.location = loadVector2D(xml, eEntity);
    	entity.width = Integer.parseInt(eEntity.getAttribute("width"));
    	entity.height = Integer.parseInt(eEntity.getAttribute("height"));
    	entity.centerRotation = Float.parseFloat(eEntity.getAttribute("centerRotation"));
    	entity.render = Boolean.parseBoolean(eEntity.getAttribute("render"));
    	//entity.centerRotation.setRotation(Float.parseFloat(eEntity.getAttribute("centerRotation")));
    	
//    	Element eScripts = xml.getElement(eEntity, "scripts");
//    	if(eScripts.hasChildNodes()){
//	    	NodeList list = xml.getElements(eScripts, "script");
//	    	for(int i=0;i<list.getLength();i++){
//	    		Element eScript = (Element) list.item(i);
//	    		try {
//	    			Class c = loader.loadClass(eScript.getAttribute("class"));
//					//Class c = Class.forName(eScript.getAttribute("class"));
//					entity.addScript(eScript.getAttribute("name"), c);
//				} catch (Exception e) {
//					System.out.println("Error scr : "+e.toString());
//				}
//	    	}
//    	}
    }
    public static Vector2D loadVector2D(XMLEngine xml, Element parent){
    	Element ev2d = xml.getElement(parent, "vector2d");
    	float x = Float.parseFloat(ev2d.getAttribute("x"));
    	float y = Float.parseFloat(ev2d.getAttribute("y"));
    	return new Vector2D(x, y);
    }
    public static int loadColor(XMLEngine xml, Element parent){
    	Element ecolor = xml.getElement(parent, "color");
    	int r = Integer.parseInt(ecolor.getAttribute("red"));
    	int g = Integer.parseInt(ecolor.getAttribute("green"));
    	int b = Integer.parseInt(ecolor.getAttribute("blue"));
    	return Color.rgb(r, g, b);
    }
}
