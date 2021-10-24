package rad.mining.ethermine.helper;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component @Slf4j public class JSONHelper {

	private final CloseableHttpClient httpClient;

	public JSONHelper() {
		httpClient = HttpClients.createDefault();
	}

	public static JSONObject getJsonObject(String jsonString) {
		return new JSONObject(jsonString);
	}

	public String getRequest(String url) {
		log.info("Getting info from {}.", url);
		String result = null;

		HttpGet request = new HttpGet(url);

		try (CloseableHttpResponse response = httpClient.execute(request)) {

			// Get HttpResponse Status

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// return it as a String
				result = EntityUtils.toString(entity);
			}

		} catch (ClientProtocolException e) {
			log.error("ClientProtocolException {}", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("IOException {}", e);
			e.printStackTrace();
		}
		log.trace("Got result {}: ", result);
		return result;
	}

	public JSONArray JsonFromFile(String file) {
		return null;
	}

}
