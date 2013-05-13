package modules;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.image.ColorModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.Vector;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import mainframe.GraphicsModule;
import mainframe.MainFrame;
import ide.Configuration;
import ide.ConflictException;
import ide.Module;
import ide.MsgRcvr;
import ide.RussianPost;

public class Compiler extends GraphicsModule{
	private JTextPane console;
	private boolean closed;
	private final int SHOW;
	private DocumentListener dl = null;
	private Process p;
	
	public Compiler(MainFrame mf, RussianPost rp, Configuration conf) {
		super(mf, rp, conf);
		JPanel p = mainframe.addGraphicsModule(this, MainFrame.BOTTOM, "Console");
		p.setLayout(new BorderLayout(0,0));
		closed = false;
		console = new JTextPane();
		console.setSize(p.getSize());
		console.setFocusable(true);
		p.add(new JScrollPane(console));
		int show = -1;
		try {
			show = mainframe.addMenu("Window/Console", this, null);
		} catch (ConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SHOW = show;
		new Builder();
		new Launcher();
	}
	
	@Override
	public boolean onTabClose(Component where) {
		try{
			p.exitValue();
			closed = true;
			return false;
		}catch(Exception ex){
			p.destroy();
			return true;
		}
	}
	
	@Override
	public void menuClick(int index) {
		if(index == SHOW){
			JPanel p = mainframe.addGraphicsModule(this, MainFrame.BOTTOM, "Console");
			p.setLayout(new BorderLayout(0,0));
			p.add(new JScrollPane(console));
		}
	}
	
	private void appendText(String s, Color c){
		//System.out.println(s);
		StyledDocument doc = console.getStyledDocument();
		SimpleAttributeSet col = new SimpleAttributeSet();
		StyleConstants.setForeground(col, c);
		synchronized(doc){
			try {
				doc.removeDocumentListener(dl);
				doc.insertString(doc.getLength(), s, col);
				doc.addDocumentListener(dl);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		console.setCaretPosition(doc.getLength());
		console.repaint();
	}
	
	private class Builder implements MsgRcvr{

		public Builder() {
			Compiler.this.russianpost.addIncPostBox("Compile", this);
		}
		
		@Override
		public void handle(Object msg) {
			if(msg instanceof File){
				console.setText("");
				File f = (File)msg;
				File dir = f.getParentFile();
				String[] cmdarray = new String[2];
				cmdarray[0] = "javac";
				cmdarray[1] = f.getAbsolutePath();
				try {
					//System.out.println("try to start");
					p = Runtime.getRuntime().exec(cmdarray, null, dir);
					//System.out.println(p.toString());
					new StreamReader(p.getInputStream(), StreamReader.STD);
					new StreamReader(p.getErrorStream(), StreamReader.ERR);
					int res = p.waitFor();
					if(res == 0){
						Compiler.this.appendText("Compilation successful.\n", Color.black);
					}else{
						Compiler.this.appendText("Compilation failed with exitcode "+res+".\n", Color.red);
					}
					//System.out.println("end");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private class Launcher implements MsgRcvr{
		
		public Launcher() {
			Compiler.this.russianpost.addIncPostBox("Run", this);
		}
		
		@Override
		public void handle(Object msg) {
			if(msg instanceof File){
				console.setText("");
				console.grabFocus();
				File f = (File)msg;
				File dir = f.getParentFile();
				String[] cmdarray = new String[2];
				Vector<String> v = new Vector<String>();
				cmdarray[0] = "java";
				cmdarray[1] = f.getName().substring(0, f.getName().lastIndexOf('.'));
				try {
					//System.out.println("try to start");
					p = Runtime.getRuntime().exec(cmdarray, null, dir);
					//System.out.println(p.toString());
					new StreamWriter(p.getOutputStream());
					new StreamReader(p.getInputStream(), StreamReader.STD);
					new StreamReader(p.getErrorStream(), StreamReader.ERR);
					/*int res = p.waitFor();
					if(res == 0){
						Compiler.this.appendText("Execution successful.\n", Color.black);
					}else{
						Compiler.this.appendText("Execution failed with exitcode "+res+".\n", Color.red);
					}*/
					//System.out.println("end");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class StreamReader implements Runnable{

		private BufferedReader br;
		public static final int STD = 0;
		public static final int ERR = 1;
		private Color color;
		private Thread t;
		
		public StreamReader(InputStream is, int flag) {
			//System.out.println("begin");
			br = new BufferedReader(new InputStreamReader(is));
			if(flag == ERR){
				color = Color.red;
			}else{
				color = Color.black;
			}
			//System.out.println("start");
			t = new Thread(this);
			t.start();
		}
		
		@Override
		public void run() {
			//System.out.println("start2");
			String s;
			try {
				for(;(s = br.readLine())!=null;){
					Compiler.this.appendText(s+"\n", color);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(e.toString());
				System.err.println("It seems, subprocess was killed");
			}
		}
	}
	
	private class StreamWriter implements DocumentListener{
		private OutputStream os;
		
		public StreamWriter(OutputStream os) {
			this.os = os;
			Compiler.this.console.getDocument().addDocumentListener(this);
			Compiler.this.dl = this;
		}
		
		@Override
		public void changedUpdate(DocumentEvent arg0) {
			
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			try {
				String s = Compiler.this.console.getDocument().getText(arg0.getOffset(), arg0.getLength());
				os.write(s.charAt(0));
				os.flush();	
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				Compiler.this.console.getDocument().removeDocumentListener(this);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			
		}
	}
}
