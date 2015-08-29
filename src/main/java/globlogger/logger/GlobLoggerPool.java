package globlogger.logger;

import java.util.HashMap;

public abstract class GlobLoggerPool {

	public static final String INFORMATION = "Information";
	public static final String ERROR = "Error";
	public static final String WARNING = "Warning";

	private final static HashMap<String, GlobLogger> hash = new HashMap<>();
	private final static Object lock = new Object();

	public synchronized static void removeLogger(String id) {
		if (id != null)
			hash.remove(id);
		
	}

	public synchronized static GlobLogger getLogger(String id) {

		if (!hash.containsKey(id))
			hash.put(id, new GlobLogger());
		return hash.get(id);

	}

	public synchronized static void clean() {
		hash.clear();
	}

}
