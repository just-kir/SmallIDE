package modules;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.text.LayeredHighlighter;
	

public class PopupMenu{
	private static PopupMenu instance = null;
	private JFrame frame;
	private JScrollPane panel;
	private boolean shown;
	private JComponent c;
	private List list;
	private Vector<String> v;
	private PopupListener popuplistener = null;
	
	private PopupMenu(JFrame fr) {
		frame = fr;
		v=new Vector<String>();
		v.add("test yopta");
		list = new List(v);  
		panel = new JScrollPane(list);
		
	}
	
	public static PopupMenu getInstance(JFrame fr){
		if(instance == null) instance = new PopupMenu(fr);
		return instance;
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public void clear(){
		v.clear();
	}
	
	public void add(String s){
		v.add(s);
	}
	
	public void remove(String s){
		v.remove(s);
	}
	
	public void show(JComponent c, int x, int y){
		if(!isShown()){
			this.c = c;
			c.addKeyListener(list);
			Point frp = frame.getLayeredPane().getLocationOnScreen();
			Point p = c.getLocationOnScreen();
			panel.setBounds(p.x+x-frp.x, p.y+y-frp.y, 300, 200);
			frame.getLayeredPane().add(panel,JLayeredPane.POPUP_LAYER);
			shown = true;
			list.setSelectedIndex(0);
			c.grabFocus();
		}else
			System.err.println("try to show when shown");
	}
	
	public void hide(){
		if(isShown()){
			shown = false;
			frame.repaint();
			frame.getLayeredPane().remove(panel);
			c.removeKeyListener(list);
			if(popuplistener!= null) popuplistener.hide();
		}else
			System.err.println("try to hide when not shown");
	}
	
	private class List extends JList<String> implements KeyListener,MouseListener{
		
		public List(Vector<String> v) {
			super(v);
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			addMouseListener(this);
		}
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_DOWN || arg0.getKeyCode() == KeyEvent.VK_UP){
				arg0.setSource(this);
				processKeyEvent(arg0);
				//c.dispatchEvent(arg0);
				arg0.consume();
			}
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER ){
				if(PopupMenu.this.popuplistener!=null)PopupMenu.this.popuplistener.accept(this.getSelectedValue());
				PopupMenu.this.hide();
				arg0.consume();
			}
			if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE ){
				PopupMenu.this.hide();
				arg0.consume();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2){
				if(PopupMenu.this.popuplistener!=null)PopupMenu.this.popuplistener.accept(this.getSelectedValue());
				PopupMenu.this.hide();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
}
