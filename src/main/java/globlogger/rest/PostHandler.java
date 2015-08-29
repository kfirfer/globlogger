package globlogger.rest;

import globlogger.logger.Handler;
import globlogger.logger.Message;

public class PostHandler implements Handler {

	protected PostLogger post;

	public PostHandler(PostLogger post) {
		this.post = post;
	}

	@Override
	public void handle(Message message) {
		post.output(message);
	}

}
