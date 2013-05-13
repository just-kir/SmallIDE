package modules;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import mainframe.MainFrame;
import modules.TextEditor.TextFile;
import ide.Configuration;
import ide.Module;
import ide.MsgRcvr;
import ide.RussianPost;

public class Highlighter extends Module implements MsgRcvr,DocumentListener{
	private StyledDocument doc;
	private Hashtable<String, SimpleAttributeSet> styles;
	private Hashtable<String, String> keywords;
	
	public Highlighter(MainFrame mf, RussianPost rp, Configuration conf) {
		super(mf, rp, conf);
		styles = new Hashtable<String, SimpleAttributeSet>();
		keywords = new Hashtable<String, String>();
		this.parseFile("colors");
		rp.subscribe("ActiveDocument", this);
		new OpenDocument();
	}
	
	@Override
	public void handle(Object msg) {
		if(msg != null){
			if(doc != null) doc.removeDocumentListener(this);
			doc = (StyledDocument)(((TextFile)msg).getDocument());
			doc.addDocumentListener(this);
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		repaint(); // TODO Lightweight highlighting
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		repaint(); // TODO Lightweight highlighting
	}
	
	private void repaint(){
		try {
			String text = doc.getText(0, doc.getLength());
			String[] words = text.split("\\W");
			int start = 0;
			int end = 0;
			for (int i = 0; i < words.length; i++) {
				if(!words[i].equals("")){
					String s = ask(words[i]);
					SimpleAttributeSet sas = null;
					if(s != null)
						sas = styles.get(s);
					if(sas!=null && s != null){
						start = text.indexOf(words[i], end);
						end = start+words[i].length();
						doc.setCharacterAttributes(start, words[i].length(), sas, false);
					}
					//System.out.println(words[i]+"/"+s+"/"+c+"/"+start+"/"+end);
				}
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseFile(String filename){
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String s;
			while((s = br.readLine())!=null){
				if(s.startsWith("syn keyword")){
					s = s.substring("syn keyword ".length());
					String[] ss = s.split(" ");
					keywords.put(ss[1], ss[0]);
				}else if(s.startsWith("hi")){
					s = s.substring("hi ".length());
					String[] ss = s.split(" ");
					SimpleAttributeSet sas = styles.get(ss[0]);
					if(sas == null){
						sas = new SimpleAttributeSet();
						styles.put(ss[0],sas);
					}
					for (int i = 1; i < ss.length; i++) {
						String[] sss = ss[i].split("=");
						if(sss[0].equals("gui")){
							if(sss[1].equals("bold")){
								StyleConstants.setBold(sas, true);
							}
						}else if(sss[0].equals("guifg")){
							int r = Integer.parseInt(sss[1].substring(1, 3), 16);
							int g = Integer.parseInt(sss[1].substring(3, 5), 16);
							int b = Integer.parseInt(sss[1].substring(5, 7), 16);
							System.out.println(r+" "+g+" "+b);
							Color c = new Color(r, g, b);
							StyleConstants.setForeground(sas, c);
							
						}else if(sss[0].equals("guibg")){
							int r = Integer.parseInt(sss[1].substring(1, 3), 16);
							int g = Integer.parseInt(sss[1].substring(3, 5), 16);
							int b = Integer.parseInt(sss[1].substring(5, 7), 16);
							System.out.println(r+" "+g+" "+b);
							Color c = new Color(r, g, b);
							StyleConstants.setBackground(sas, c);
							
						}
					}
					
				}else{
					System.err.println("incorrect syntax: "+s);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setForeground(sas, new Color(127, 0, 85));
		StyleConstants.setBold(sas, true);
		styles.put("keywords", sas);
		keywords.put("public", "keywords");
		keywords.put("static", "keywords");
		keywords.put("int", "keywords");
		keywords.put("void", "keywords");
		keywords.put("class", "keywords");
		keywords.put("throws", "keywords");
		keywords.put("import", "keywords");
		keywords.put("new", "keywords");
		keywords.put("try", "keywords");
		keywords.put("catch", "keywords");
	}
	
	private String ask(String word){
		
		return keywords.get(word);
	}
	
	private Vector<SpecialHighlighting> searchSpecial(String text){
		Vector<SpecialHighlighting> res = new Vector<SpecialHighlighting>(); 
		return res;
	}
	
	private class SpecialHighlighting{
		private int start;
		private int end;
		private String type;
		
		public SpecialHighlighting(int start, int end, String type) {
			this.end = end;
			this.start = start;
			this.type = type;
		}
		
		public int getEnd() {
			return end;
		}
		
		public int getStart() {
			return start;
		}
		
		public String getType() {
			return type;
		}
	}
	
	private class OpenDocument implements MsgRcvr{
		public OpenDocument() {
			Highlighter.this.russianpost.subscribe("OpenDocument", this);
		}

		@Override
		public void handle(Object msg) {
			Highlighter.this.handle(msg);
			Highlighter.this.repaint();
		}
	}
	
}
