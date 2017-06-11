package security;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ExceptionLogger {

	private static Logger logger;
	private static Handler handler;
	private static final String FILEPATH = "Files/errorlog.log";

	public ExceptionLogger() throws Exception {
		logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0]
				.getClassName());
		
		handler = new FileHandler(FILEPATH, 1024 * 1024 * 1024, 1, false);
		handler.setEncoding("UTF-8");
		logger.addHandler(handler);
		setLoggerPermissions();
	}

	private void setLoggerPermissions() {
		File f = new File(FILEPATH);
		f.setWritable(true);
		f.setReadable(true);
	}
	
	public static void writeSevereError(Exception e) {
		logger.log(Level.SEVERE, e.toString(), e);
	}
}