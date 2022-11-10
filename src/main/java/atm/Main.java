package atm;


import java.sql.*;
import java.util.Scanner;

public class Main {


	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Database database = new Database();
		Connection connect = DriverManager.getConnection(database.getURL(), database.getUSERNAME(), database.getPASSWORD());

		Statement statement = 	connect.createStatement();
		Scanner scannerObject = new Scanner(System.in);
		String userName = scannerObject.nextLine();

		ResultSet resultSet = statement.executeQuery("SELECT * FROM account WHERE name = '" + userName + "'");

		if(resultSet.next() == true){
			System.out.println("Hello " + resultSet.getString("name"));
			Main main = new Main();
			main.initializePage();
			while (true) {
				main.inputFromUser(resultSet);
			}
		}
		else {
			throw new RuntimeException("There is no acccount named:" + userName );
		}

		

		


	}


	private void initializePage() {
		System.out.println("International Bank of Poland");
		System.out.println("How can we help You?");
		System.out.println("1 - Balance");
		System.out.println("2 - Deposit");
		System.out.println("3 - Withdraw");
	}

	private void inputFromUser(ResultSet resultSet) throws SQLException {
		Scanner scannerObject = new Scanner(System.in);
		int userInput = scannerObject.nextInt();
		switch (userInput) {
		case 1: {
			Database database = new Database();
			Connection connect = DriverManager.getConnection(database.getURL(), database.getUSERNAME(), database.getPASSWORD());
			System.out.println("\f");
			System.out.println("1 - Balance");
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM account WHERE name = '" + resultSet.getString("name") + "'" );
			rs.next();
			System.out.println("You have:" + rs.getFloat("amount") + "zl \n");

			initializePage();
			break;
		}
		case 2: {
			Database database = new Database();
			Connection connect = DriverManager.getConnection(database.getURL(), database.getUSERNAME(), database.getPASSWORD());

			System.out.println("Enter amount:");
			int userDepositAmount = scannerObject.nextInt();

			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM account WHERE name = '" + resultSet.getString("name") + "'" );
			result.next();
			int amountToAdd = result.getInt("amount") + userDepositAmount;
			statement.executeUpdate("UPDATE account SET amount = " + amountToAdd );

			ResultSet rs = statement.executeQuery("SELECT * FROM account WHERE name = '" + resultSet.getString("name") + "'" );
			rs.next();
			System.out.println("Successfully added " + userDepositAmount + "zl to Your account");
			System.out.println("You have:" + rs.getInt("amount") + "zl \n");

			initializePage();
			break;
		}
		case 3: {
			Database database = new Database();
			Connection connect = DriverManager.getConnection(database.getURL(), database.getUSERNAME(), database.getPASSWORD());

			System.out.println("Enter amount:");
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM account WHERE name = '" + resultSet.getString("name") + "'" );
			result.next();
			int userWithdrawalAmount = scannerObject.nextInt();
			if (userWithdrawalAmount <= result.getInt("amount")) {


				int amountToSubstract = result.getInt("amount") - userWithdrawalAmount;
				statement.executeUpdate("UPDATE account SET amount = " + amountToSubstract );


				ResultSet rs = statement.executeQuery("SELECT * FROM account WHERE name = '" + resultSet.getString("name") + "'" );
				rs.next();
				System.out.println("Balance after withdrawal: " + rs.getInt("amount"));
			} else {
				System.out.println("Not enough funds");
			}

			initializePage();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + userInput);
		}

	}
	
	
}
