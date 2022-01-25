package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class StatusDisplayer extends JPanel {
	
	private static final long serialVersionUID = 5240020968380665298L;
	
	private JLabel title = null;
	private JProgressBar bar = null;
	
	public StatusDisplayer(String dfltMsg) {
		setLayout(new GridLayout(0, 1));
		
		title = new JLabel(dfltMsg);
		title.setVisible(true);
		add(title);
		
		setVisible(true);
	}
	
	public void initProgressBar(int max, int width) {
		bar = new JProgressBar(0, max);
		bar.setSize(width, 20);
		bar.setString(0 + " / " + bar.getMaximum());
		bar.setStringPainted(true);
		add(bar);
		bar.setVisible(true);
	}
	
	public void forwardBar(int step) {
		bar.setValue(bar.getValue() + step);
		bar.setString(bar.getValue() + " / " + bar.getMaximum());
	}
	
	public void setTitle(String msg) {
		title.setText(msg);
	}
}
