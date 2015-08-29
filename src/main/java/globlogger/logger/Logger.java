package globlogger.logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Logger {

	public abstract void output(Message msg);

	public Map<String, String> getHash(Message msg, Params params) {
		Map<String, String> map = new HashMap<>();

		String className = null;
		String methodName = null;
		String fileName = null;
		String lineNumber = null;

		if (msg.getE() != null) {
			className = msg.getE().getStackTrace()[0].getClassName();
			methodName = msg.getE().getStackTrace()[0].getMethodName();
			lineNumber = ((Integer) msg.getE().getStackTrace()[0].getLineNumber()).toString();
			fileName = msg.getE().getStackTrace()[0].getFileName();
		}

		if (params.getDate() != null)
			map.put(params.getDate(), new Date().toString());

		if (params.getLevel() != null && msg.getLevel() != null)
			map.put(params.getLevel(), msg.getLevel());

		if (params.getMessage() != null && msg.getMessage() != null)
			map.put(params.getMessage(), msg.getMessage());

		if (params.getException() != null && msg.getE() != null)
			map.put(params.getException(), msg.getE().getClass().getName());

		if (params.getMethod() != null && methodName != null)
			map.put(params.getMethod(), methodName);

		if (params.getClassName() != null && className != null)
			map.put(params.getClassName(), className);

		if (params.getLineNumber() != null && lineNumber != null)
			map.put(params.getLineNumber().toString(), lineNumber);

		if (params.getFileName() != null && fileName != null)
			map.put(params.getFileName(), fileName);

		return map;

	}
}
