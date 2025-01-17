package ebayAutomation.endToendTests;

import java.io.IOException;
import java.net.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import com.jayway.jsonpath.JsonPath;

public class GetCallAEndToEnd {

	@Test	
	public void verifyAPIgetResponse() throws ClientProtocolException, IOException {
		// Send the GET request
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response =   client.execute(request);
       
		
     // Get the response body as a string
        String responseBody = EntityUtils.toString((response).getEntity());
        
        // Close the client
        client.close();
        

        // Parse the response JSON using JsonPath
        String[] bpiCurrencies = JsonPath.read(responseBody, "$.bpi.keySet()");
        String gbpDescription = JsonPath.read(responseBody, "$.bpi.GBP.description");
        
     // Verify there are 3 BPIs (USD, GBP, EUR)
        Assert.assertTrue("There are not exactly 3 BPIs", bpiCurrencies.length == 3);
        
        // Verify GBP description is "British Pound Sterling"
        Assert.assertEquals("GBP description does not match", "British Pound Sterling", gbpDescription);
        
        // Print the response to see the output (optional)
        System.out.println(response);
        
        System.out.println("Test passed successfully.");
	}

}
