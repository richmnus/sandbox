package ch9;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Nio2Demo {

	public static void main(String[] args) {
		Path path = Paths.get("testfile.txt");
		try {
			BufferedReader reader = Files.newBufferedReader(path,
					StandardCharsets.UTF_8);
			String input = null;
			while ((input = reader.readLine()) != null) {
				System.out.println(input);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
