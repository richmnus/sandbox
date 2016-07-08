package anon.postman;

import java.io.File;
import java.util.Random;

import anon.postman.resources.RandomAccountNumberGenerator;
import anon.postman.resources.RandomStringGenerator;

public class Postman {

	private static final String namesFile = "resources" + File.separatorChar
			+ "names.txt";
	private static final String surnamesFile = "resources" + File.separatorChar
			+ "surnames.txt";

	public static void main(String[] args) {
		Random r = new Random();
		RandomStringGenerator nameGen = new RandomStringGenerator(namesFile);
		RandomStringGenerator surnameGen = new RandomStringGenerator(
				surnamesFile);
		String name = nameGen.getRandomStringItem(r);
		String surname = surnameGen.getRandomStringItem(r);
		System.out.printf("Random name is %s %s\n", name, surname);

		RandomAccountNumberGenerator acGen = new RandomAccountNumberGenerator(
				"$$-2014-????-3345-????");
		String accountNo = acGen.getRandomAccountNumber(r);
		System.out.printf("Random account number %s\n", accountNo);
	}

}
