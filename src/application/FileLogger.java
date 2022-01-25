package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogger {
	
	private static final String filePath = "ImagesRenamer.log";
	
	private Logger logger = null;
	
	public FileLogger() throws SecurityException, IOException {		
		logger = Logger.getLogger("ImagesRenamerLogger");
		FileHandler fh;
		fh = new FileHandler(filePath);
		fh.setFormatter(new SimpleFormatter() {
			private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s%n%4$s";

			@Override
			public synchronized String format(LogRecord lr) {
				String stackTrace = "";
				Throwable thrown = null;
				if ((thrown = lr.getThrown()) != null) {
					StringWriter out = new StringWriter();
					thrown.printStackTrace(new PrintWriter(out));
					stackTrace = out.toString() + "\n";
				}
					
				return String.format(format,
						new Date(lr.getMillis()),
						lr.getLevel().getLocalizedName(),
						lr.getMessage(),
						stackTrace
						);
			}
		});
		logger.addHandler(fh);
		logger.setUseParentHandlers(false);

		info("Logger successfully initialized");
	}
	
	public void info(String msg) {
		logger.info(msg);
	}

	public void warning(String msg) {
		logger.warning(msg);
	}

	public void severe(String msg) {
		logger.severe(msg);
	}

	public void throwable(String msg, Throwable thrown) {
		logger.log(Level.SEVERE, msg, thrown);
	}
}
