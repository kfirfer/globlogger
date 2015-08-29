package globlogger.rest;

import java.net.URI;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import globlogger.logger.Logger;
import globlogger.logger.Message;
import globlogger.logger.Params;

public class PostLogger extends Logger {

	private Params params;
	private URI uri;

	public PostLogger(String uri) {
		this.uri = UriBuilder.fromUri(uri).build();
		params = new Params();
	}

	public void output(Message msg) {

		Map<String, String> map = getHash(msg, params);

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget service = client.target(uri);
		Form form = new Form();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = (String) entry.getValue();
			form.param(key, value);
		}

		try {
			service.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
		} catch (Exception e) {
			System.out.println("Unable to get response from :" + this.uri);
		}

	}

	// for user
	public void setURL(String string) {
		this.uri = UriBuilder.fromUri(string).build();
	}

	public PostLogger date(String string) {
		params.setDate(string);
		return this;
	}

	public PostLogger message(String string) {
		params.setMessage(string);
		return this;
	}

	public PostLogger thread(String string) {
		params.setThread(string);
		return this;
	}

	public PostLogger method(String string) {
		params.setMethod(string);
		return this;
	}

	public PostLogger level(String string) {
		params.setLevel(string);
		return this;
	}

	public PostLogger line(String string) {
		params.setLineNumber(string);
		return this;
	}

	public PostLogger file(String string) {
		params.setFileName(string);
		return this;
	}

	public PostLogger exception(String string) {
		params.setException(string);
		return this;
	}

	public PostLogger className(String string) {
		params.setClassName(string);
		return this;
	}

}
