package test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ReadZipFile {

	private String filePath = "c:\\Downloads\\TestOutput3\\1\\vapp4854441224679650619.zip";
	private final static String METADATA_FILE_NAME = "artifacts.xml";

	public static void main(String[] args) {
		new ReadZipFile();
	}

	public ReadZipFile() {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unchecked")
		final Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile
				.entries();
		while (entries.hasMoreElements()) {
			final ZipEntry entry = entries.nextElement();
			if (entry.getName().contains(METADATA_FILE_NAME)) {
				// found it!
				System.out.printf("Metadata file found: %s", entry.getName());
			}
		}
	}

}
