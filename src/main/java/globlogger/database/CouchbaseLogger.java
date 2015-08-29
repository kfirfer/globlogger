package globlogger.database;

import java.util.Map;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

import globlogger.logger.Logger;
import globlogger.logger.Message;
import globlogger.logger.Params;

public final class CouchbaseLogger extends Logger {

	private String bucketName;
	private Cluster cluster;
	private Params params;
	private int expire;
	private static int counter = 0;

	public CouchbaseLogger(CouchbaseCluster cluster, String bucketName) {
		this.cluster = cluster;
		this.bucketName = bucketName;
		params = new Params();
	}

	@Override
	public void output(Message msg) {
		if (bucketName == null)
			throw new NullPointerException("Need provide bucket name");

		Map<String, String> map = getHash(msg, params);
		JsonObject content = JsonObject.empty();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = (String) entry.getValue();
			content.put(key, value);
		}

		JsonDocument doc = null;
		if (expire == 0)
			doc = JsonDocument.create("id" + counter++, content);
		else
			doc = JsonDocument.create("id2", expire, content);
		Bucket bucket = cluster.openBucket(bucketName);
		bucket.upsert(doc);
		bucket.close();

	}

	// for user
	public CouchbaseLogger setBucket(String bucketName) {
		this.bucketName = bucketName;
		return this;
	}

	public CouchbaseLogger setCluster(CouchbaseCluster cluster) {
		this.cluster = cluster;
		return this;

	}

	public CouchbaseLogger setExpire(int expire) {
		this.expire = expire;
		return this;
	}

	public CouchbaseLogger date(String string) {
		params.setDate(string);
		return this;
	}

	public CouchbaseLogger className(String string) {
		params.setClassName(string);
		return this;
	}

	public CouchbaseLogger message(String string) {
		params.setMessage(string);
		return this;
	}

	public CouchbaseLogger thread(String string) {
		params.setThread(string);
		return this;
	}

	public CouchbaseLogger method(String string) {
		params.setMethod(string);
		return this;
	}

	public CouchbaseLogger level(String string) {
		params.setLevel(string);
		return this;
	}

	public CouchbaseLogger line(String string) {
		params.setLineNumber(string);
		return this;
	}

	public CouchbaseLogger file(String string) {
		params.setFileName(string);
		return this;
	}

	public CouchbaseLogger exception(String string) {
		params.setException(string);
		return this;
	}

}
