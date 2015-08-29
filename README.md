# Logger-System-Report
Logger for send Logs to email,database(couchbase for now),and http post

Download jar: <a href="http://globnet.co.il/kfirfer/BugSystemReport-0.1.jar">BugSystemReport.01</a>

Full user guide: "src/main/java/example.Main.java"
## Simple example of sending log to email
	EmailLogger email = new EmailLogger("kfirfer@gmail.com", "password");
	email.sendTo("yukuzuna@live.com");
			// naming the fields as you like , if you don't mention names, they won't appear in logs
			email.date("date").method("method")
								.exception("exception")
								.file("file")
								.level("level")
								.line("line")
								.thread("thread")
								.message("message")
								.className("class");
	Handler emailHandler = new EmailHandler(email);
	GlobLoggerPool.getLogger("log1").addHandler(emailHandler);
	GlobLoggerPool.getLogger("log1").publish(new NullPointerException(), "This is gonna do alot of problems!",
					"WARNING");


