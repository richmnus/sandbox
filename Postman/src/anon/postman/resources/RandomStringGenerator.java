package anon.postman.resources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomStringGenerator {

	private String filename;
	private List<String> strings;

	public RandomStringGenerator(String path) {
		strings = new ArrayList<String>();
		this.filename = path;
		loadFile();
	}

	private void loadFile() {
		try {
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fis, "UTF8");
			BufferedReader reader = new BufferedReader(isr);
			String line;
			while ((line = reader.readLine()) != null) {
				strings.add(line);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getRandomStringItem(Random r) {
		int randomIndex = r.nextInt(strings.size());
		return strings.get(randomIndex);
	}

}
