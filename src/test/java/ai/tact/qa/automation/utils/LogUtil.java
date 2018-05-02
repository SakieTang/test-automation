package ai.tact.qa.automation.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.*;

public class LogUtil {

    private static Calendar now = Calendar.getInstance();
    private static final int year = now.get(Calendar.YEAR);
    private static final int month = now.get(Calendar.MONTH) + 1;

    private static final String LOG_FILE_SUFFIX = ".log";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
    private static Logger logger = Logger.getLogger("Tact");

    private static FileHandler fileHandler = getFileHandler();
    private static LogFormatter logFormatter = new LogFormatter();

    private synchronized static String getLogFilePath() {
        StringBuffer logFilePath = new StringBuffer();
        logFilePath.append("target");
        logFilePath.append(File.separatorChar);
        logFilePath.append("log");

        System.out.println("dir is  " + logFilePath.toString());

        File dir = new File(logFilePath.toString());
        if (!dir.exists())
        {
            dir.mkdir();
        }

        logFilePath.append(File.separatorChar);
        logFilePath.append(sdf.format(new Date()));
        logFilePath.append(LOG_FILE_SUFFIX);

        return logFilePath.toString();
    }

    private static FileHandler getFileHandler() {
        FileHandler fileHandler = null;
        boolean APPEND_MODE = true;
        try {
            fileHandler = new FileHandler(getLogFilePath(), APPEND_MODE);
            return fileHandler;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized static Logger setLoggerHandler() {
        return setLoggerHandler(Level.ALL);
    }

    // severe > warning > info > config > fine > finer > fineset
    public synchronized static Logger setLoggerHandler (Level level) {

        try {
            fileHandler.setFormatter(logFormatter);

            logger.addHandler(fileHandler);
            logger.setLevel(level);
        }
        catch (SecurityException e) {
            logger.severe(populateExceptionStackTrace(e));
        }
        return logger;
    }

    private synchronized static String populateExceptionStackTrace(Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(e.toString()).append("\n");
        for (StackTraceElement element : e.getStackTrace()) {
            stringBuilder.append("\tat ").append(element).append("\n");
        }
        return stringBuilder.toString();
    }
}
