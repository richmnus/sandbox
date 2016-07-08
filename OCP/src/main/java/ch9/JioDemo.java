package ch9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JioDemo {

	public static void main(String[] args) {
		File file = new File("testfile.txt");
		BufferedWriter bw = null;
		try {
			boolean success = file.createNewFile();
			FileWriter out = new FileWriter(file);
			bw = new BufferedWriter(out);
			bw.write("Hello world");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (FileReader in = new FileReader(file);
				BufferedReader br = new BufferedReader(in);) {
			String instr;
			while ((instr = br.readLine()) != null) {
				System.out.println(instr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
