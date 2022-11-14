package atm;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONObject;

public class CurrencyExchange {
public void currency() throws IOException, InterruptedException {

	Scanner scannerObject = new Scanner(System.in);
	String currency;
	boolean properValue = false;
	while (!properValue){
		System.out.println("Choose currency:");
		System.out.println("1 - EUR");
		System.out.println("2 - USD");
		System.out.println("3 - GBP");
		int userInput = scannerObject.nextInt();
		System.out.println("Choose amount:");
		float userAmount = scannerObject.nextFloat();
	switch (userInput){
		case 1:
			currency = "EUR";
			properValue = true;
			convertCurrency(currency, userAmount);
			break;

		case 2:
			currency = "USD";
			properValue = true;
			convertCurrency(currency, userAmount);
			break;
		case 3:
			currency = "GBP";
			properValue = true;
			convertCurrency(currency, userAmount);
			break;
		default:

			break;
	}
	}

}

	private void convertCurrency(String currency,float userAmount) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://v6.exchangerate-api.com/v6/96e28daf4121ddb86cad4c65/latest/"+currency)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		JSONObject myObject = new JSONObject(response.body());
		float toEuro = myObject.getJSONObject("conversion_rates").getFloat("EUR");
		float toDollar = myObject.getJSONObject("conversion_rates").getFloat("USD");
		float toPound =myObject.getJSONObject("conversion_rates").getFloat("GBP");
		System.out.println("Euro: " + toEuro * userAmount);
		System.out.println("Dollars: " + toDollar * userAmount);
		System.out.println("Pounds: " + toPound * userAmount);
	}

}

