package globlogger.database;

import globlogger.logger.Handler;
import globlogger.logger.Logger;
import globlogger.logger.Message;

public class DatabaseHandler implements Handler {

	private Logger database;

	public DatabaseHandler(Logger dbLog) {
		if (dbLog == null)
			throw new NullPointerException("Need provide db");
		this.database = dbLog;
	}

	@Override
	public void handle(Message message) {
		database.output(message);
	}
}
