package atm;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONObject;

public class CurrencyExchange {
public void currency()
{
	System.out.println("Choose currency");
	Scanner scannerObject = new Scanner(System.in);
	int userInput = scannerObject.nextInt();
	try {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://v6.exchangerate-api.com/v6/96e28daf4121ddb86cad4c65/latest/PLN")).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject myObject = new JSONObject(response.body());
		float toEuro = myObject.getJSONObject("conversion_rates").getFloat("EUR");
		float toDolar = myObject.getJSONObject("conversion_rates").getFloat("USD");
		float toPound =myObject.getJSONObject("conversion_rates").getFloat("GBP");
		System.out.println(toEuro);
		System.out.println(toDolar);
		System.out.println(toPound);
	} catch (IOException | InterruptedException e) {
		e.printStackTrace();
	}
	
}
}
