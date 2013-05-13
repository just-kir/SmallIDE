package test;

import ide.Configuration;
import ide.Module;
import ide.MsgRcvr;
import ide.RussianPost;
import mainframe.MainFrame;
import modules.TextEditor;
import modules.TextEditor.TextFile;
import modules.PopupListener;
import modules.PopupMenu;

import javax.swing.event.*;
import javax.swing.text.*;



public class TextTest extends Module implements MsgRcvr,DocumentListener, PopupListener{
	private Document doc;
	private TextFile tf;
	private int pos;
	
	public TextTest(MainFrame mf, RussianPost rp, Configuration conf){
		super(mf, rp, conf);
		System.out.println("FUUUUuCK!");
		rp.subscribe("ActiveDocument", this);
				
	}


	public void handle(Object msg){
		if(doc != null) doc.removeDocumentListener(this);
		if(msg != null){
			tf = (TextFile)msg;
			doc = ((TextFile)msg).getDocument();
			pos = ((TextFile)msg).getCurrentPosition();
			System.err.println("fuck yeah");
			System.out.println(doc);
			System.out.println(pos);
		}else
			System.err.println("so strange - null msg");
		//System.out.println(doc);
		if(doc != null) doc.addDocumentListener(this);
	}

	public void changedUpdate(DocumentEvent e){
	}
	
	public void insertUpdate(DocumentEvent e){
		
		System.out.println("up!");
		pos = tf.getCurrentPosition();
		doc.removeDocumentListener(this);
	/*	Thread t = new Thread(new Runnable(){
			public void run(){
				try {
					TextTest.this.doc.insertString(TextTest.this.pos+1, "blaaaaa", null);
					doc.addDocumentListener(TextTest.this);
					String s = "blaaaaa";
					pos =pos +  s.length();
					doc.createPosition(pos);
				} catch(BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		});
		t.start();*/
		Event("bla-bla");
		System.out.println(tf.getCurrentPosition());
		
	}
	
	public void Event(String s) {
		PopupMenu p = PopupMenu.getInstance(mainframe.getFrame());
		//p.setSkip(2);
		p.clear();
		p.add("fuck");
		p.add("let fuck right now %)");
		p.add("anton pupsik");
		p.setPopupListener(this);
		russianpost.send("ShowPopupMenu", p);
	}
	
	
	public void removeUpdate(DocumentEvent e){
		
	}
	
	public void accept(String s) {
		System.out.println("user agreed with "+s);
		
		// try to do some inserts
		try {
			doc.insertString(1, "blaaaaaaaa", null);
		} catch(BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void hide() {
		System.out.println("hide this fuckkinn menu");
	}
//Document doc = TextFile.getDocument();

}
