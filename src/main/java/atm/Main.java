package atm;


import java.sql.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Main main = new Main();
        Statement statement = main.databaseConnect();
        Scanner scannerObject = new Scanner(System.in);
		System.out.println("Enter Login: ");
        String login = scannerObject.nextLine();
		System.out.println("Enter Password: ");
		String password = scannerObject.nextLine();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM account WHERE login = '" + login + "' AND password = '" + password + "'");
        if (resultSet.next() == true) {
            System.out.println("Hello " + resultSet.getString("login"));
            main.initializePage();
            while (true) {
                main.inputFromUser(resultSet);
            }
        } else {
            throw new RuntimeException("Wrong username or password");
        }
    }

    private Statement databaseConnect() throws SQLException {
        Database database = new Database();
        Connection connect = DriverManager.getConnection(database.getURL(), database.getUSERNAME(), database.getPASSWORD());
        Statement statement = connect.createStatement();
        return statement;
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
                Statement statement = databaseConnect();
                System.out.println("1 - Balance");
                ResultSet rs = statement.executeQuery("SELECT * FROM account WHERE login = '" + resultSet.getString("login") + "'");
                rs.next();
                System.out.println("You have:" + rs.getFloat("amount") + "zl \n");
                initializePage();
                break;
            }
            case 2: {
                System.out.println("Enter amount:");
                int userDepositAmount = scannerObject.nextInt();
                Statement statement = databaseConnect();
                ResultSet result = statement.executeQuery("SELECT * FROM account WHERE login = '" + resultSet.getString("login") + "'");
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
                Statement statement = databaseConnect();
                ResultSet result = statement.executeQuery("SELECT * FROM account WHERE login = '" + resultSet.getString("login") + "'");
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
            default:
                throw new IllegalArgumentException("Unexpected value: " + userInput);
        }

    }


}
