package test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileToByteArray {

	public static void main(String[] args) {
		new FileToByteArray();
	}

	public FileToByteArray() {
		fileToByteArray(".gitignore");
	}

	public byte[] fileToByteArray(String pathname) {
		InputStream file = null;
		byte[] data = null;
		try {
			byte[] buffer = new byte[1024];
			file = new FileInputStream(pathname);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			int r = file.read(buffer);
			while (r > -1) {
				bout.write(buffer, 0, r);
				r = file.read(buffer);
			}
			data = bout.toByteArray();
			file.close();
		} catch (final FileNotFoundException e) {
			System.err.println(e);
		} catch (final IOException e) {
			System.err.println(e);
		}
		System.out.printf("Done\n");
		return data;
	}

}
