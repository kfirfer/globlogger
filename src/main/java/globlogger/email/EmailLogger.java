package globlogger.email;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import globlogger.logger.Logger;
import globlogger.logger.Params;
import globlogger.rest.PostLogger;

@SuppressWarnings("unused")
public class EmailLogger extends Logger {

	private String username;
	private String password;
	private String provider = "smtp.gmail.com";
	private String port = "465";
	private HashSet<String> sendTo = new HashSet<>();
	private Params params;

	public EmailLogger(String from, String password) {
		this.username = from;
		this.password = password;
		params = new Params();
	}

	@Override
	public void output(globlogger.logger.Message msg) {
		Map<String, String> map = getHash(msg, params);

		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", provider);
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", port);
		props.setProperty("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		try {
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			// -- Create a new message --
			Message e_msg = new MimeMessage(session);

			// -- Set the FROM and TO fields --
			e_msg.setFrom(new InternetAddress(username));
			for (String to : sendTo) {
				e_msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
			}
			e_msg.setSubject("BugSystemReport");
			// For HTML
			StringBuilder buildHtml = buildHTML(map);
			e_msg.setContent(buildHtml.toString(), "text/html");
			e_msg.setSentDate(new Date());
			Transport.send(e_msg);
			System.out.println("Message sent.");
		} catch (MessagingException e) {
			System.out.println("Erreur d'envoi, cause: " + e);
		}

	}

	private StringBuilder buildHTML(Map<String, String> map) {
		StringBuilder buildHtml = new StringBuilder();
		buildHtml.append("<h1> Bug Report System </h1><br><br>");

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = (String) entry.getValue();
			buildHtml.append("<h4><b>" + key + "</h4></b>" + value + "<br>");
		}

		return buildHtml;
	}
	// for user

	public void sendFrom(String from) {
		this.username = from;
	}

	public void changePassword(String password) {
		this.password = password;
	}

	public void remove(String string) {
		sendTo.remove(string);
	}

	public void sendTo(String... string) {
		sendTo.addAll(Arrays.asList(string));
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setProviderPort(int port) {
		this.port = ((Integer) port).toString();
	}

	public EmailLogger date(String string) {
		params.setDate(string);
		return this;
	}

	public EmailLogger message(String string) {
		params.setMessage(string);
		return this;
	}

	public EmailLogger thread(String string) {
		params.setThread(string);
		return this;
	}

	public EmailLogger method(String string) {
		params.setMethod(string);
		return this;
	}

	public EmailLogger level(String string) {
		params.setLevel(string);
		return this;
	}

	public EmailLogger line(String string) {
		params.setLineNumber(string);
		return this;
	}

	public EmailLogger file(String string) {
		params.setFileName(string);
		return this;
	}

	public EmailLogger exception(String string) {
		params.setException(string);
		return this;
	}

	public EmailLogger className(String string) {
		params.setClassName(string);
		return this;
	}

}
