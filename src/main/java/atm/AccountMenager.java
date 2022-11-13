package atm;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class AccountMenager {
	
	Properties prop = new Properties();
	
	public void menageAccount() throws SQLException {
		
		Logger logger = LogManager.getLogger(AccountMenager.class);
	        Statement statement = databaseConnect();
		try (Scanner scannerObject = new Scanner(System.in)) {
			System.out.println("Enter Login: ");
			String login = scannerObject.nextLine();
			System.out.println("Enter Password: ");
			String password = scannerObject.nextLine();

			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM account WHERE login = '" +
			login + "' AND password = '" + password + "'");
			if (resultSet.next() == true) {
				System.out.println("Hello " + resultSet.getString("login"));
				initializePage();
				while (true) {
					inputFromUser(resultSet);
				}
			} else {
				
				   logger.error("Wrong username or password");
				   menageAccount();
			}
		}
	}
	

private Statement databaseConnect() throws SQLException {
	try (InputStream input = new FileInputStream("application.properties")) {
		prop.load(input);
		String URL = prop.getProperty("URL");
		String USERNAME = prop.getProperty("USERNAME");
		String PASSWORD = prop.getProperty("PASSWORD");

		Connection connect = DriverManager.getConnection(
				stringFormat(URL),
				stringFormat(USERNAME),
				stringFormat(PASSWORD));
		Statement statement = connect.createStatement();
		return statement;
	} catch (IOException ex) {
		ex.printStackTrace();
	}
	return null;

}

private String stringFormat(String stringToFormat) {
	return stringToFormat.substring(1, stringToFormat.length() - 1);
}

private void initializePage() {
	System.out.println("1 - Balance");
	System.out.println("2 - Deposit");
	System.out.println("3 - Withdraw");
}

private void inputFromUser(ResultSet resultSet) throws SQLException {
	Scanner scannerObject = new Scanner(System.in);
	Statement statement = databaseConnect();
	int userInput = scannerObject.nextInt();
	switch (userInput) {
	case 1: {

		System.out.println("1 - Balance");
		ResultSet rs = statement
				.executeQuery("SELECT * FROM account WHERE login = '" +
						resultSet.getString("login") + "'");
		rs.next();
		System.out.println("You have:" + rs.getFloat("amount") + "zl \n");
		initializePage();
		break;
	}
	case 2: {
		System.out.println("Enter amount:");
		int userDepositAmount = scannerObject.nextInt();
		ResultSet result = statement
				.executeQuery("SELECT * FROM account WHERE login = '" + resultSet.getString("login") + "'");
		result.next();
		int amountToAdd = result.getInt("amount") + userDepositAmount;
		statement.executeUpdate("UPDATE account SET amount = " + amountToAdd);
		System.out.println("Successfully added " + userDepositAmount + "zl to Your account");
		System.out.println("You have:" + amountToAdd + "zl \n");
		initializePage();
		break;
	}
	case 3: {
		System.out.println("Enter amount:");
		int userWithdrawalAmount = scannerObject.nextInt();
		ResultSet result = statement
				.executeQuery("SELECT * FROM account WHERE login = '" + resultSet.getString("login") + "'");
		result.next();
		if (userWithdrawalAmount <= result.getInt("amount")) {
			int amountToSubstract = result.getInt("amount") - userWithdrawalAmount;
			statement.executeUpdate("UPDATE account SET amount = " + amountToSubstract);
			System.out.println("Balance after withdrawal: " + amountToSubstract);
		} else {
			System.out.println("Not enough funds");
		}
		initializePage();
		break;
	}
	case 4: {

//        	// Setting URL
//        	String url_str = "https://v6.exchangerate-api.com/v6/YOUR-API-KEY/latest/USD";
//
//        	// Making Request
//        	URL url = new URL(url_str);
//        	HttpURLConnection request = (HttpURLConnection) url.openConnection();
//        	request.connect();
//
//        	// Convert to JSON
//        	JsonParser jp = new JsonParser();
//        	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
//        	JsonObject jsonobj = root.getAsJsonObject();
//
//        	// Accessing object
//        	String req_result = jsonobj.get("result").getAsString();
	}
	default:
		throw new IllegalArgumentException("Unexpected value: " + userInput);
	}

}
}
