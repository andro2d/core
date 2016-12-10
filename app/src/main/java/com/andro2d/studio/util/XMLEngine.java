/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andro2d.studio.util;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author prk
 */
public class XMLEngine {
    private DocumentBuilderFactory docfactory;
    private DocumentBuilder docbuilder;
    private Document doc;

    public XMLEngine() {
    	docfactory = DocumentBuilderFactory.newInstance();
    	try {
            docbuilder = docfactory.newDocumentBuilder();
            doc = docbuilder.newDocument();
        } catch (Exception ex) {
            Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public XMLEngine(File path){
    	 docfactory = DocumentBuilderFactory.newInstance();
         try {
             docbuilder = docfactory.newDocumentBuilder();
             if(path.equals("")){
                 doc = docbuilder.newDocument();
             }else{
                 doc = docbuilder.parse(path);
                 doc.getDocumentElement().normalize();
             }
         } catch (Exception ex) {
             Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public XMLEngine(InputStream path) {
        docfactory = DocumentBuilderFactory.newInstance();
        try {
            docbuilder = docfactory.newDocumentBuilder();
            if(path.equals("")){
                doc = docbuilder.newDocument();
            }else{
                doc = docbuilder.parse(path);
                doc.getDocumentElement().normalize();
            }
        } catch (Exception ex) {
            Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public NodeList getElements(String name){
        return doc.getElementsByTagName(name);
    }
    
    public NodeList getElements(Element element, String name){
        return element.getElementsByTagName(name);
    }
    
    public Element getElement(Element element, String name){
        NodeList nodes = getElements(element, name);
        return (Element)nodes.item(0);
    }
    
    public Element createElement(Element parent, String ElementName){
        Element e = doc.createElement(ElementName);
        parent.appendChild(e);
        return e;
    }
    
    public Element appendRoot(String name){
        Element e = doc.createElement(name);
        doc.appendChild(e);
        return e;
    }
    
    public void addAttr(Element e, String attribute, String value){
        Attr a = doc.createAttribute(attribute);
        a.setValue(value);
        e.setAttributeNode(a);
    }
    
    public Element appendChild(Element parent, String childName){
        return appendChild(parent, childName, "");
    }
    
    public Element appendChild(Element parent, String childName, String value){
        Element child =  createElement(parent, childName);
        if(value.equals("")){
            child.appendChild(doc.createTextNode(value));
        }
        return child;
    }
    
    public void save(String filepath){
        TransformerFactory trans = TransformerFactory.newInstance();
        trans.setAttribute("indent-number", 3);
        try {
            Transformer transformer = trans.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
            transformer.transform(source, result);
        } catch (Exception ex) {
            Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}