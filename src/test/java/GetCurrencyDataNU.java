import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class GetCurrencyDataNU {

	public static void main(String[] args) throws Exception{
		
		String url = "http://download.finance.yahoo.com/d/quotes.csv?s=USDTHB=X,USDEUR=X&f=sl1&e=.csv";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		
		// Create an HTTP GET request
		HttpGet httpget = new HttpGet(url);

		// Execute the request
		httpget.getRequestLine();
		HttpResponse response = null;
		response = httpclient.execute(httpget);
		
		HttpEntity entity = response.getEntity();
		// Print the response
		System.out.println("Response status: {}"+ response.getStatusLine());
		
		StringBuilder responseText = new StringBuilder();
		if (entity != null) {
			try {
				InputStream inputStream = entity.getContent();
				// Process the response
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					responseText.append(line).append("\n");
				}
				bufferedReader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources.
		httpclient.getConnectionManager().shutdown();
		
		System.out.println(responseText);
	}
}
