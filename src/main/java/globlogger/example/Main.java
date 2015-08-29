package globlogger.example;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.java.CouchbaseCluster;

import globlogger.database.CouchbaseLogger;
import globlogger.database.DatabaseHandler;
import globlogger.email.EmailHandler;
import globlogger.email.EmailLogger;
import globlogger.logger.GlobLoggerPool;
import globlogger.logger.Handler;
import globlogger.rest.PostHandler;
import globlogger.rest.PostLogger;

public class Main {

	public static void main(String[] args) {

		
		// complete 3 examples, you can comment out two to check one by one
		POSTExample();
		DataBaseExample();
		EmailExample();

		// clean loggers (itws will esrase all logs, for resources purpose)
		GlobLoggerPool.clean();
	}

	// POST
	static void POSTExample() {
		PostLogger post = new PostLogger("http://localhost/rest/sendexception/kfirdb");
		// naming the fields as you like , if you not mention names they wont apper in logs
		post.date("date").method("method")
							.exception("exception")
							.file("file")
							.level("level")
							.line("line")
							.thread("thread")
							.message("message")
							.className("class");
		
		Handler postHandler = new PostHandler(post);
		
		// while you take a logger you provide uniqe id,
		// and when you call it back you get the same logger
		
		GlobLoggerPool.getLogger("log1").addHandler(postHandler);

		// send LOG
		GlobLoggerPool.getLogger("log1")
		.publish(new NullPointerException(), "This is gonna do alot of problems!","WARNING");
	}

	// DATABASE
	static void DataBaseExample() {

		// send cluster client and name of the bucket
		CouchbaseLogger db = new CouchbaseLogger(CouchbaseCluster.create(), "logger");
		
		// naming the fields as you like , if you not mention names they wont apper in logs
		db.date("date").method("method")
						.exception("exception")
						.file("file")
						.level("level")
						.line("line")
						.thread("thread")
						.message("message")
						.className("class")
						.setExpire((int) TimeUnit.MINUTES.toSeconds(1));
		// create handler
		Handler dbHandler = new DatabaseHandler(db);

		// add handler
		GlobLoggerPool.getLogger("log1").addHandler(dbHandler);

		// send LOG
		GlobLoggerPool.getLogger("log1").publish(new NullPointerException(), "This is gonna do alot of problems!",
				"WARNING");
	}

	// EMAIL
	static void EmailExample() {
		// Email from (mail address and password)
		EmailLogger email = new EmailLogger("kfirfer@gmail.com", "xxx");
		// send to(you can add multiple emails)
		email.sendTo("kfirfer@hotmail.com");
		/*
		 * Change provider: (Default is "smtp.gmail.com", and port is 465)
		 * email.setProvider("smtp.live.com");
		 * email.setProviderPort(25);
		 */
		
		// naming the fields as you like , if you not mention names they wont apper in logs
		email.date("date").method("method")
							.exception("exception")
							.file("file")
							.level("level")
							.line("line")
							.thread("thread")
							.message("message")
							.className("class");
		
		// create handler
		Handler emailHandler = new EmailHandler(email);
		
		// add handler
		GlobLoggerPool.getLogger("log1").addHandler(emailHandler);

		// send LOG
		GlobLoggerPool.getLogger("log1").publish(new NullPointerException(), "This is gonna do alot of problems!",
				"WARNING");

	}

}
