package gui;

import java.awt.Container;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class FileChooser extends JFileChooser {

	private static final long serialVersionUID = -5521322319707823796L;
	
	private Container parent;
	
	public FileChooser(Container parent) {
		super();
		this.parent = parent;
	}
	
	public File select() throws IOException {
		setCurrentDirectory(new File("."));
		setDialogTitle("Sélectionnez le dossier contenant vos images");
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		setAcceptAllFileFilterUsed(false);
		
		if (showOpenDialog(parent) != JFileChooser.APPROVE_OPTION)
			throw new IOException("No directory selected");
		
		File tmpDir = getSelectedFile();
		if (!tmpDir.exists() || !tmpDir.isDirectory())
			throw new IOException(tmpDir + " does not exist or is not a directory");
		return tmpDir;
	}

}
