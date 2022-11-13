package atm;


import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

	public static void main(String[] args)
			throws SQLException, ClassNotFoundException, IOException, InterruptedException {
		initializePage();
		Logger logger = LogManager.getLogger(Main.class);
		Scanner scannerObject = new Scanner(System.in);
		int userInput = scannerObject.nextInt();

		switch (userInput) {
		case 1:
			CurrencyExchange currencyExchange = new CurrencyExchange();
			currencyExchange.currency();
			break;
		case 2:
			AccountMenager accountMenager = new AccountMenager();
			accountMenager.menageAccount();
			break;

		default:
			logger.error("Please use proper input");
			break;
		}

	}

	private static void initializePage() {
		System.out.println("International Bank of Poland");
		System.out.println("How can we help You?");
		System.out.println("1 - Wymiana walut");
		System.out.println("2 - Logowanie");

	}
}