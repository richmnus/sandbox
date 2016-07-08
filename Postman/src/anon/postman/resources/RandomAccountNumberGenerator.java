package anon.postman.resources;

import java.util.Random;

public class RandomAccountNumberGenerator {

	/**
	 * ? = put random digit in here between 0-9 $ = put alphabetic character in
	 * here between A-Z
	 */
	private String spec;

	public RandomAccountNumberGenerator(String spec) {
		this.spec = spec;
	}

	public String getRandomAccountNumber(Random r) {
		StringBuilder builder = new StringBuilder();
		char[] chars = spec.toCharArray();
		for (char chr : chars) {
			switch (chr) {
			case '?':
				builder.append(rndDigit(r));
				break;
			case '$':
				builder.append(rndChar(r));
				break;
			default:
				builder.append(chr);
				break;
			}
		}
		return builder.toString();
	}

	private String rndDigit(Random r) {
		int digit = r.nextInt(10);
		return Integer.toString(digit);
	}

	private String rndChar(Random r) {
		int rndChr = 'A' + r.nextInt(26);
		return Character.toString((char) rndChr);
	}

}
