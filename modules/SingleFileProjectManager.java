package modules;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	private boolean closed;
	private JPanel panel;
	private JList<TextFile> list;
	
	public SingleFileProjectManager(MainFrame mf, RussianPost rp, Configuration conf) {
		super(mf, rp, conf);
		panel = mf.addGraphicsModule(this, MainFrame.LEFT, "Files");
		int show;
		try {
			show = mf.addMenu("Window/Opened files manager", this);
		} catch (ConflictException e) {
			show = -1;
			e.printStackTrace();
		}
		SHOW = show;
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
