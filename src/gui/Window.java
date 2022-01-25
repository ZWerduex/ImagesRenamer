package gui;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import application.FileLogger;
import listening.Listener;

public class Window implements Listener {

	private FileLogger logger;
	
	private Frame f;
	private StatusDisplayer statusDisp;
	private FileChooser fileChooser;

	public Window(FileLogger logger) {
		this.logger = logger;
		this.logger.info("Building window ...");
		f = new Frame();

		statusDisp = new StatusDisplayer("En attente d'un dossier");
		f.add(statusDisp);

		f.setVisible(true);
		fileChooser = new FileChooser(f);
		this.logger.info("Window built");
	}
	
	public void setStatusTitle(String msg) {
		statusDisp.setTitle(msg);
	}
	
	public void initProgressBar(int max) {
		statusDisp.initProgressBar(max, f.width - 50);
	}
	
	public void forwardBar() {
	}

	@Override
	public void onNotification(Object source) {
		statusDisp.forwardBar((int) source);
	}
	
	public boolean confirmClosing() {
		boolean close = showConfirmDialog("Quitter", "Voulez-vous quitter le programme ?");
		if (close) close();
		return close;
	}

	public String showInputDialog(String title, String question, String defaultText) {
		logger.info("Showing input dialog titled " + title);
		return (String) JOptionPane.showInputDialog(
				f,
				question,
				title,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				null,
				defaultText
				);
	}
	
	public boolean showConfirmDialog(String title, String question) {
		logger.info("Showing confirm dialog titled " + title);
		Object[] options = {"Oui", "Non"};
		int n = JOptionPane.showOptionDialog(
				f,
				question,
				title,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		return (n == 0);
	}

	public File selectDirectory() {
		File selectedDirectory = null;
		try {
			logger.info("Started file chooser");
			selectedDirectory = fileChooser.select();
		} catch (IOException e) {
			logger.throwable("No directory selected", e);
			return null;
		}
		logger.info("Directory selected : " + selectedDirectory);
		return selectedDirectory;
	}

	public void close() {
		logger.info("Closing window ...");
		f.setVisible(false);
		f.clear();
		statusDisp = null;
		fileChooser = null;
		f.dispose();
		f = null;
	}
}
