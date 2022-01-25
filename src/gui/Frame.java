package gui;

import java.awt.Component;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {

	private static final long serialVersionUID = 7446192599263749847L;
	
	public static final int width = 300;
	public static final int height = 170;

	public Frame() {
		super("ImagesRenamer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setContentPane(new JPanel());
		setSize(width, height);
		
		DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		int scWidth = dm.getWidth();
		int scHeight = dm.getHeight();
		
		setLocation(scWidth/2 - width/2, scHeight/2 - height/2);
	}
	
	public void clear() {
		for (Component c : getComponents())
			remove(c);
	}

}
