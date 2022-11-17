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

/**
 * This class contains methods that are responsible
 * for managing user account (Check balance, deposit, withdraw).
 */
public class AccountManager {

    public void menageAccount() throws SQLException {

        Logger logger = LogManager.getLogger(AccountManager.class);
        Statement statement = databaseConnect();
        getUserFromDatabase(statement);
    }

	private Statement databaseConnect() throws SQLException {
        Properties prop = new Properties();
		try (InputStream input = new FileInputStream("application.properties")) {
			prop.load(input);
			String URL = prop.getProperty("URL");
			String USERNAME = prop.getProperty("USERNAME");
			String PASSWORD = prop.getProperty("PASSWORD");
			Connection connect = DriverManager.getConnection(
					stringFormat(URL),
					stringFormat(USERNAME),
					stringFormat(PASSWORD));
			return connect.createStatement();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

    /**
     * @param stringToFormat application.properties field
     * @return string without quotation marks at start and end
     */

	private String stringFormat(String stringToFormat) {
		return stringToFormat.substring(1, stringToFormat.length() - 1);
	}

    private void getUserFromDatabase(Statement statement) throws SQLException {
        Scanner scannerObject = new Scanner(System.in);
        System.out.println("Enter Login: ");
        String login = scannerObject.nextLine();
        System.out.println("Enter Password: ");
        String password = scannerObject.nextLine();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM account WHERE login = '"
                + login + "' AND password = '" + password + "'");
        if (resultSet.next()) {
            System.out.println("Hello " + resultSet.getString("login"));
            initializePage();
            while (inputFromUser(resultSet));
			System.exit(1);
        } else {
			System.out.println("Wrong username or password");
            menageAccount();
        }
    }
    private void initializePage() {
        System.out.println("1 - Balance");
        System.out.println("2 - Deposit");
        System.out.println("3 - Withdraw");
    }

    /**
     * Accepts user input when true is returned.
     * @param resultSet user data from database
     * @return true to continue listening for user input
     * or false to quit
     * @throws SQLException
     */
    private boolean inputFromUser(ResultSet resultSet) throws SQLException {
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
                        .executeQuery("SELECT * FROM account WHERE login = '" +
                                resultSet.getString("login") + "'");
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
                        .executeQuery("SELECT * FROM account WHERE login = '" +
                                resultSet.getString("login") + "'");
                result.next();
                if (userWithdrawalAmount <= result.getInt("amount")) {
                    int amountToSubtract = result.getInt("amount") - userWithdrawalAmount;
                    statement.executeUpdate("UPDATE account SET amount = " + amountToSubtract);
                    System.out.println("Balance after withdrawal: " + amountToSubtract);
                } else {
                    System.out.println("Not enough funds");
                }
                initializePage();
                break;
            }
			case 4:
			{
				System.out.println("Ending Session...");
				return false;

			}
            default:
                System.out.println("Unexpected value : " + userInput);
        }

		return true;
	}
}
