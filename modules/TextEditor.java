package modules;

import ide.Configuration;
import ide.ConflictException;
import ide.MsgRcvr;
import ide.OutBox;
import ide.RussianPost;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

import mainframe.GraphicsModule;
import mainframe.MainFrame;

public class TextEditor extends GraphicsModule{
	//private final int NEW;
	private final int OPEN;
	private final int SAVE;
	private final int OPEN_MENU;
	private final int SAVE_MENU;
	private Hashtable<JPanel, TextFile> documents;
	private JFileChooser fc;
	private TabChangeListener tabchangelistener;
	
	private class SetTab implements MsgRcvr{

		public SetTab() {
			TextEditor.this.russianpost.addIncPostBox("SetDocument", this);
		}
		
		@Override
		public void handle(Object msg) {
			Set<JPanel> set = TextEditor.this.documents.keySet();
			for (Iterator<JPanel> iterator = set.iterator(); iterator.hasNext();) {
				JPanel item = iterator.next();
				if(TextEditor.this.documents.get(item) == msg){
					TextEditor.this.mainframe.setActiveTab(item, MainFrame.TOP);
					break;
				}
			}
		}
		
	}
	
	public class TextFile{
		private File f;
		private Document doc;
		public TextFile(File f, Document doc) {
			this.doc = doc;
			this.f = f;
			try {
				FileInputStream is = new FileInputStream(f);
				byte[] str = new byte[is.available()];
				is.read(str);
				is.close();
				if(str.length > 0) 
					doc.insertString(0, new String(str), null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void save(){
			try {
				PrintStream ps = new PrintStream(f);
				String str = doc.getText(0, doc.getLength());
				ps.print(str);
				ps.close();
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public Document getDocument() {
			return doc;
		}
		
		public File getFile() {
			return f;
		}
		
		@Override
		public String toString() {
			return f.getName();
		}
	}
	
	private class TabChangeListener implements MsgRcvr{
		private OutBox outChange;
		private OutBox outOpen;
		private OutBox outClose;
		
		public TabChangeListener() {
			outChange = TextEditor.this.russianpost.addOutPostBox("ActiveDocument");
			outOpen = TextEditor.this.russianpost.addOutPostBox("OpenDocument");
			outClose = TextEditor.this.russianpost.addOutPostBox("CloseDocument");
			TextEditor.this.russianpost.subscribe("topChange", this);
		}
		
		@Override
		public void handle(Object msg) {
			outChange.putAndSend(msg == null ? null : TextEditor.this.documents.get(msg));
		}
		
		public void open(TextFile doc){
			outOpen.putAndSend(doc);
		}
		
		public void close(TextFile doc){
			outClose.putAndSend(doc);
		}
		
	}

	public TextEditor(MainFrame mf, RussianPost rp, Configuration conf) {
		super(mf, rp, conf);
		int zo = -1, zs = -1;
		try {
			zo = mf.addMenu("File/Open", this);
			zs = mf.addMenu("File/Save", this);
		} catch (ConflictException e) {
			e.printStackTrace();
		}
		OPEN_MENU = zo;
		SAVE_MENU = zs;
		OPEN = mf.addButton(new ImageIcon("document-open.png"),this);
		SAVE = mf.addButton(new ImageIcon("document-save.png"),this);
		documents = new Hashtable<JPanel, TextFile>();
		fc = new JFileChooser();
		tabchangelistener = new TabChangeListener();
		new SetTab();
		 
	}

	private JPanel getActiveTab(){
		return mainframe.getActiveTab(MainFrame.TOP);
	}
	
	@Override
	public boolean onTabClose(Component where) {
		JPanel p = (JPanel) where;
		int res = JOptionPane.showConfirmDialog(mainframe.getFrame(), "Do you want to save changes?", "Question", JOptionPane.YES_NO_CANCEL_OPTION);
		if(res == JOptionPane.YES_OPTION){
			TextFile tf = documents.get(p);
			tf.save();
			tabchangelistener.close(documents.get(p));
			documents.remove(p);
			return false;
		}
		if(res == JOptionPane.NO_OPTION){
			tabchangelistener.close(documents.get(p));
			documents.remove(p);
			return false;
		}
		return true;
	}
	
	@Override
	public void menuClick(int index) {
		if(index == OPEN_MENU){
			buttonClick(OPEN);
		}
		if(index == SAVE_MENU){
			save();
		}
	}
	
	@Override
	public void buttonClick(int index) {
		/*if(index == NEW){
			if(mainframe == null){
				System.out.println("FUCK");
				return;
			}
			JPanel p = mainframe.addGraphicsModule(this, MainFrame.TOP, "Untitled"+(count++));
			
		}*/
		if(index == OPEN){
			fc.setDialogTitle("Open file");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int res = fc.showOpenDialog(mainframe.getFrame());
			if (res == JFileChooser.APPROVE_OPTION){
				File f = fc.getSelectedFile();
				open(f);
			}
			
		}
		if(index == SAVE){
			save();
		}
	}
	
	private void open(File f){
		JPanel p = mainframe.addGraphicsModule(this, MainFrame.TOP, f.getName());
		p.setLayout(new BorderLayout(0,0)); 
		JTextPane tp = new JTextPane();
		TextFile tf = new TextFile(f, tp.getDocument());
		tp.setCaretPosition(0);
		//tp.setSize(p.getSize());
		JScrollPane sp = new JScrollPane(tp);
		//sp.setSize(p.getSize());
		p.add(sp,BorderLayout.CENTER);
		documents.put(p, tf);
		tabchangelistener.open(tf);
	}
	
	private void save(){
		JPanel p = getActiveTab();
		if(p == null) return;
		TextFile tf = documents.get(p);
		tf.save();
	}
}

