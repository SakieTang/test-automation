package ai.tact.qa.automation.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {

    //DateFormat
    private static final DateFormat df = new SimpleDateFormat("MM/dd hh:mm:ss.SSS");

    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        builder.append(df.format(new Date(record.getMillis()))).append(" - ");

        return builder.toString();
    }

    public String getHead (Handler h) {
        return super.getHead(h);
    }

    public String getTail (Handler h) {
        return super.getTail(h);
    }

}
