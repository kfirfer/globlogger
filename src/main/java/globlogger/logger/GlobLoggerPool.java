package globlogger.logger;

import java.util.HashMap;

public abstract class GlobLoggerPool {

	public static final String INFORMATION = "Information";
	public static final String ERROR = "Error";
	public static final String WARNING = "Warning";

	private final static HashMap<String, GlobLogger> hash = new HashMap<>();
	private final static Object lock = new Object();

	public static void removeLogger(String id) {
		synchronized (lock) {
			if (id != null)
				hash.remove(id);
		}
	}

	public static GlobLogger getLogger(String id) {
		synchronized (lock) {
			if (!hash.containsKey(id))
				hash.put(id, new GlobLogger());
			return hash.get(id);
		}
	}

	public static void clean() {
		synchronized (lock) {
			hash.clear();
		}
	}

}
