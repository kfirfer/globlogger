package globlogger.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GlobLogger {

	private List<Handler> handlers = new ArrayList<>();
	public final Object lock = new Object();

	public void publish() {
		Message msg = new Message();
		submit(msg);
	}

	public void publish(String message) {
		Message msg = new Message();
		msg.setMessage(message);
		submit(msg);
	}

	public void publish(String message, String level) {
		Message msg = new Message();
		msg.setMessage(message);
		msg.setLevel(level);
		submit(msg);
	}

	public void publish(Throwable e) {
		Message msg = new Message();
		msg.setE(e);
		submit(msg);
	}

	public void publish(Throwable e, String message) {
		Message msg = new Message();
		msg.setMessage(message);
		msg.setE(e);
		submit(msg);
	}

	public void publish(Throwable e, String message, String level) {
		Message msg = new Message();
		msg.setMessage(message);
		msg.setLevel(level);
		msg.setE(e);
		submit(msg);
	}

	private void submit(Message msg) {
		new Thread(() -> {
			synchronized (lock) {
				for (Handler handler : handlers) {
					handler.handle(msg);
				}
			}
		}).start();
	}

	public boolean addHandler(Handler handler) {
		return handlers.add(handler);
	}

	public boolean removeHandler(Handler handler) {
		return handlers.remove(handler);
	}

	public Handler removeHandler(int index) {
		return handlers.remove(index);
	}

	public void ignore(Class<IOException> class1) {

	}

}
