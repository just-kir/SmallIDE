package mainframe;
import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.*;
 
public class ButtonTabComponent extends JPanel {
    private final JTabbedPane pane;
    private GraphicsModule module;
 
    public JTabbedPane getPane() {
		return pane;
	}
    
    public ButtonTabComponent(final JTabbedPane pane, GraphicsModule gm) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        module = gm;
        this.pane = pane;
        setOpaque(false);
         
        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return pane.getComponentAt(i).getName();
                }
                return null;
            }
        };
         
        add(label);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        JButton button = new TabButton();
        add(button);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }
 
    private class TabButton extends JButton implements ActionListener {
        public TabButton() {
            int size = 19;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            setUI(new MetalButtonUI());
            setContentAreaFilled(false);
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            addActionListener(this);
        }
 
        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            if (i != -1) {
            	boolean stop = ButtonTabComponent.this.module.onTabClose(ButtonTabComponent.this.getPane().getComponentAt(i));
                if(!stop) 
                	pane.remove(i);
            }
        }
 
        public void updateUI() {
        }
 
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }
 
    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }
 
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}