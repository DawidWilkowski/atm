package atm;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {



	float balance = 1000;


	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Database database = new Database();
		Connection connect = DriverManager.getConnection(database.getURL(), database.getUSERNAME(), database.getPASSWORD());

		Statement statement = 	connect.createStatement();
		Scanner scannerObject = new Scanner(System.in);
		String userName = scannerObject.nextLine();
		String SQL = "SELECT * FROM account WHERE name =" + userName;

		ResultSet resultSet = statement.executeQuery(SQL);
		if(resultSet !=null){
		System.out.println("Hello" + resultSet);
		}
		while (resultSet.next()) {
			System.out.println(resultSet.getString("name") + " " + resultSet.getInt("amount"));
		}
		

		
		Main main = new Main();
		main.initializePage();
		while (true) {
			main.inputFromUser();
		}

	}


	private void initializePage() {
		System.out.println("International Bank of Poland");
		System.out.println("How can we help You?");
		System.out.println("1 - Balance");
		System.out.println("2 - Deposit");
		System.out.println("3 - Withdraw");
	}

	private void inputFromUser() {
		Scanner scannerObject = new Scanner(System.in);
		int userInput = scannerObject.nextInt();
		switch (userInput) {
		case 1: {
			System.out.println("\f");
			System.out.println("1 - Balance");
			System.out.println("You have:" + balance + "zl \n");

			initializePage();
			break;
		}
		case 2: {
			float depositAmount = 0;
			System.out.println("Enter amount:");
			float userDepositAmount = scannerObject.nextFloat();
			balance = balance + userDepositAmount;
			System.out.println("Successfully added " + userDepositAmount + "zl to Your account");
			System.out.println("You have:" + balance + "zl \n");

			String SQLCREATE = "UPDATE account SET amount = ";
			//Statement createStatement = connect.prepareStatement(SQLCREATE);

			initializePage();
			break;
		}
		case 3: {
			float withdrawalAmount = 0;
			System.out.println("Enter amount:");
			float userWithdrawalAmount = scannerObject.nextFloat();
			if (userWithdrawalAmount <= balance) {
				balance = balance - userWithdrawalAmount;
				System.out.println("Balance after withdrawal: " + balance);

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
