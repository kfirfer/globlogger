package globlogger.email;

import globlogger.logger.Handler;
import globlogger.logger.Logger;
import globlogger.logger.Message;

public class EmailHandler implements Handler {

	private Logger email;

	public EmailHandler(EmailLogger email2) {
		if (email2 == null)
			throw new NullPointerException("Need provide db");
		this.email = email2;
	}

	@Override
	public void handle(Message msg) {
		email.output(msg);

	}

}
