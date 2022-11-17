package atm;


import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author Dawid Wilkowski
 * Simple program for simulating ATM machine.
 * Basic operations: Check Balance, Deposit, Withdraw.
 * Currency converter.
 */
public class Main {

	public static void main(String[] args)
			throws SQLException, IOException, InterruptedException {
		initializePage();
		Logger logger = LogManager.getLogger(Main.class);
		Scanner scannerObject = new Scanner(System.in);
		boolean session = true;
		while (session){
			int userInput = scannerObject.nextInt();
			switch (userInput) {
			case 1:
				CurrencyExchange currencyExchange = new CurrencyExchange();
				currencyExchange.currency();
				break;
			case 2:
				AccountManager accountManager = new AccountManager();
				accountManager.menageAccount();
				break;
			case 3:
				session = false;
				System.out.println("Ending Session...");
				break;

			default:

				System.out.println("Please use proper input");
				//logger.debug("Please use proper input");
				break;
		}}
	}
	private static void initializePage() {
		System.out.println("International Bank of Poland");
		System.out.println("How can we help You?");
		System.out.println("1 - Currency Exchange");
		System.out.println("2 - Login");
		System.out.println("3 - End Session");


	}
}