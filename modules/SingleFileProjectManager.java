package modules;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import mainframe.GraphicsModule;
import mainframe.MainFrame;
import modules.TextEditor.TextFile;
import ide.Configuration;
import ide.ConflictException;
import ide.Module;
import ide.MsgRcvr;
import ide.RussianPost;

public class SingleFileProjectManager extends GraphicsModule implements ListSelectionListener {
	private final int SHOW;
	private final int NEW;
	private final int NEW_MENU;
	private final int COMPILE;
	private final int COMPILE_MENU;
	private final int RUN;
	private final int RUN_MENU;
	private boolean closed;
	private JPanel panel;
	private JList<TextFile> list;
	
	public SingleFileProjectManager(MainFrame mf, RussianPost rp, Configuration conf) {
		super(mf, rp, conf);
		panel = mf.addGraphicsModule(this, MainFrame.LEFT, "Files");
		int show;
		int run_menu;
		int compile_menu;
		int new_menu;
		try {
			show = mf.addMenu("Window/File manager", this, null);
			run_menu = mf.addMenu("Run/Run", this, KeyStroke.getKeyStroke("ctrl F11"));
			compile_menu = mf.addMenu("Run/Compile", this, KeyStroke.getKeyStroke("F11"));
			new_menu = mf.addMenu("File/New", this, KeyStroke.getKeyStroke("ctrl N"));
		} catch (ConflictException e) {
			show = -1;
			run_menu = -1;
			compile_menu = -1;
			new_menu = -1;
			e.printStackTrace();
		}
		SHOW = show;
		RUN_MENU = run_menu;
		COMPILE_MENU = compile_menu;
		NEW_MENU = new_menu;
		NEW = mf.addButton(new ImageIcon("document-new.png"), this);
		COMPILE = mf.addButton(new ImageIcon("run-build-configure.png"), this);
		RUN = mf.addButton(new ImageIcon("run.png"), this);
		DefaultListModel<TextFile> dlm = new DefaultListModel<TextFile>();
		list = new JList<TextFile>(dlm);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.setLayout(new BorderLayout(0,0));
		panel.add(new JScrollPane(list), BorderLayout.CENTER);
		list.addListSelectionListener(this);
		new OnChange();
		new OnClose();
		new OnOpen();
	}
	
	@Override
	public void menuClick(int index) {
		if(index == SHOW && closed){
			panel = mainframe.addGraphicsModule(this, MainFrame.LEFT, "Files");
			panel.setLayout(new BorderLayout(0,0));
			panel.add(new JScrollPane(list), BorderLayout.CENTER);
		}
		if(index == RUN_MENU)
			buttonClick(RUN);
		if(index == COMPILE_MENU)
			buttonClick(COMPILE);
		if(index == NEW_MENU)
			buttonClick(NEW);
	}
	
	@Override
	public void buttonClick(int index) {
		if(index == NEW){
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("New file");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int res = fc.showDialog(mainframe.getFrame(), "Create");
			if (res == JFileChooser.APPROVE_OPTION){
				File f = fc.getSelectedFile();
				try {
					f.createNewFile();
					russianpost.send("OpenFile", f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(index == COMPILE){
			russianpost.send("Compile", list.getSelectedValue().getFile());
		}
		if(index == RUN){
			russianpost.send("Run", list.getSelectedValue().getFile());
		}
	}

	@Override
	public boolean onTabClose(Component where) {
		closed = true;
		return false;
	}
	
	private class OnClose implements MsgRcvr{
		private DefaultListModel<TextFile> dlm;
		
		public OnClose() {
			SingleFileProjectManager.this.russianpost.subscribe("CloseDocument", this);
			dlm = (DefaultListModel<TextFile>)SingleFileProjectManager.this.list.getModel();
		}

		@Override
		public void handle(Object msg) {
			dlm.removeElement(msg);
		}
		
	}
	
	private class OnOpen implements MsgRcvr{
		private DefaultListModel<TextFile> dlm;
		
		public OnOpen() {
			SingleFileProjectManager.this.russianpost.subscribe("OpenDocument", this);
			dlm = (DefaultListModel<TextFile>)SingleFileProjectManager.this.list.getModel();
		}

		@Override
		public void handle(Object msg) {
			dlm.addElement(((TextFile)msg));
			SingleFileProjectManager.this.list.removeListSelectionListener(SingleFileProjectManager.this);
			int z = dlm.indexOf(msg);
			SingleFileProjectManager.this.list.setSelectedIndex(z);
			SingleFileProjectManager.this.list.addListSelectionListener(SingleFileProjectManager.this);
			SingleFileProjectManager.this.panel.repaint();
		}
		
	}
	
	private class OnChange implements MsgRcvr{
		
		public OnChange() {
			SingleFileProjectManager.this.russianpost.subscribe("ActiveDocument", this);
		}

		@Override
		public void handle(Object msg) {
			SingleFileProjectManager.this.list.removeListSelectionListener(SingleFileProjectManager.this);
			int z = ((DefaultListModel<TextFile>)SingleFileProjectManager.this.list.getModel()).indexOf(msg);
			SingleFileProjectManager.this.list.setSelectedIndex(z);
			SingleFileProjectManager.this.list.addListSelectionListener(SingleFileProjectManager.this);
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		russianpost.send("SetDocument", list.getSelectedValue());
	}
}
