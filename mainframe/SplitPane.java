package mainframe;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.plaf.metal.MetalSplitPaneUI;

public class SplitPane extends JSplitPane implements ChangeListener, PropertyChangeListener {
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	private int mainPane;
	private JTabbedPane tp;
	private double resize;
	//private JComponent main;
	
	public SplitPane(JComponent left, JComponent right, int oriention, int main, double resize) {
		super(oriention, true, main == LEFT ? left : null, main == RIGHT ? right : null);
		this.tp = (JTabbedPane)(main == LEFT ? right : left);
		//this.main = (main == LEFT ? left : right);
		//setOneTouchExpandable(true);
		mainPane = main;
		this.resize = resize;
		setDividerSize(5);
		setDividerLocation(resize);
		setResizeWeight(resize);
		tp.addChangeListener(this);
		//addPropertyChangeListener("dividerLocation", this);
		/*JButton b = ((JButton) ((MetalSplitPaneUI)getUI()).getDivider().getComponent(1));
		b.doClick();
		
		System.out.println(b.toString());*/
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(tp.getTabCount() > 0){
			if(mainPane == LEFT){
				setRightComponent(tp);
			}else{
				setLeftComponent(tp);
			}
			setDividerLocation(resize);
			setResizeWeight(resize);
			addPropertyChangeListener("dividerLocation", this);
		}else{
			removePropertyChangeListener("dividerLocation", this);
			if(mainPane == LEFT){
				setRightComponent(null);
			}else{
				setLeftComponent(null);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		//System.out.println("change "+arg0.getPropertyName()+" from "+arg0.getOldValue()+" to "+arg0.getNewValue());
		resize = ((Integer)arg0.getNewValue())/((double)this.getMaximumDividerLocation());
		//setDividerLocation(resize);
		setResizeWeight(resize);
		//System.out.println("new "+resize +" by "+this.getMaximumDividerLocation() +" and "+arg0.getNewValue());
	}
}
