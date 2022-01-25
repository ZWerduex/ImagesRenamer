package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import listening.AbstractListenable;

public class DirectoryReader extends AbstractListenable {
	
	private FileLogger logger;

	private File directory = null;
	private File[] content = null;
	private String prefix;
	private String prefixErrors;

	public DirectoryReader(FileLogger fLog, File dir) {
		logger = fLog;
		directory = dir;
		prefix = "";
		prefixErrors = "";
	}

	public boolean read() {
		logger.info("Directory received : " + directory);
		logger.info("Using prefix : " + prefix);
		logger.info("Reading content ...");
		if (directory == null) {
			logger.severe("Directory is not set");
			return false;
		}
		
		File[] files = directory.listFiles();
		logger.info("Directory readed, files pathes retrieved");
		
		logger.info("Removing sub-directories ...");
		ArrayList<File> cleanedFiles = new ArrayList<>();
		for (File f : files)
			if (!f.isDirectory())
				cleanedFiles.add(f);
			else
				logger.info("Removed " + f);
		content = new File[cleanedFiles.size()];
		for (int i = 0; i < cleanedFiles.size(); i ++)
			content[i] = cleanedFiles.get(i);
		
		logger.info("Sorting from oldest to newest ...");
		Arrays.sort(content, Comparator.comparingLong(File::lastModified));
		logger.info("Files updated and sorted");
		return true;
	}

	public boolean isPrefixValid(String prefix) {
		logger.info("Checking if " + prefix + " is a valid prefix ...");
		ArrayList<String> errors = new ArrayList<>();
		// Prefix contains characters
		if (prefix == null || prefix.isBlank())
			errors.add("Prefix is empty");
		// Prefix is 6 caharacter long
		if (prefix.length() != 6)
			errors.add("Prefix is not 6-character long");
		// Prefix is a digit
		for (char chr : prefix.toCharArray())
			if (!Character.isDigit(chr))
				errors.add("Prefix includes other characters than digit (found '" + chr + "')");
		// Prefix starts with 16
		if (!prefix.startsWith("16"))
			errors.add("Prefix does not start with '16'");
		
		for (String err : errors)
			logger.severe(err);
		if (!errors.isEmpty()) {
			prefixErrors = String.join("\n", errors);
			return false;
		}
		logger.info("Check passed");
		return true;
	}
	
	public boolean renameContent() {		
		boolean success = true;
		int cpt = 0;

		logger.info("Starting renaming files ...");
		for (int i = 0; i < content.length; i++) {
			cpt ++;
			
			File oldFile = content[i];
			String oldPath = oldFile.toString();
			logger.info("Found file at " + oldPath);
			
			String newPath =
					oldFile.getParent() + "\\" +					// Parent directory path
					prefix + "-" + String.format("%04d", cpt) 		// New filename
					+ oldPath.substring(oldPath.lastIndexOf(".")); 	// File extension
			
			File newFile = new File(newPath);
			logger.info("New full name is : " + newFile.toString());
			
			success = success && oldFile.renameTo(newFile);
			if (!success) {
				logger.severe("File " + oldFile + " couldn't be renamed, process aborted");
				logger.info("Files in directory : " + content.length);
				logger.info("Files successfully renamed : " + (cpt-1));
				return false;
			}
			notifyListeners(1);
		}
		
		//updateStatus(cpt + " sur " + content.length + " fichiers ont été renommés.");
		logger.info("Renaming process successfully terminated");
		logger.info("Files in directory : " + content.length);
		logger.info("Files renamed : " + cpt);
		return true;
	}
	
	public int size() {
		return content.length;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefixErrors() {
		return prefixErrors;
	}
}
