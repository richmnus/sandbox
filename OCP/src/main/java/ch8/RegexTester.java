package ch8;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTester {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out
					.println("Usage: java RegexTester \"<regex>\" \"<stringdata>\"");
			System.exit(1);
		}
		Pattern p = Pattern.compile(args[0]);
		Matcher m = p.matcher(args[1]);
		System.out.println("\nSource: " + args[1]);
		System.out
				.println(" Index: 0123456789012345678901234567890123456789\n");
		System.out.println("Pattern: " + m.pattern());
		while (m.find()) {
			System.out.println(m.start() + " " + m.group());
		}

	}

}
