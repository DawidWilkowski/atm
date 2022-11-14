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


		boolean properValue = false;
		while (!properValue){
			int userInput = scannerObject.nextInt();
			switch (userInput) {
			case 1:
				CurrencyExchange currencyExchange = new CurrencyExchange();
				properValue = true;
				currencyExchange.currency();
				break;
			case 2:
				AccountMenager accountMenager = new AccountMenager();
				properValue = true;
				accountMenager.menageAccount();
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

	}
}