package uk.co.martinus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MessageIds {

	private static final String idFilename = "GHMessageIdentifiers.properties";
	private static final String nlsFilename = "GHMessages.properties";
	private static final String outFilename = "differences.txt";
	private Map<String,String> msgIds;
	
	public MessageIds() {
		msgIds = new HashMap<String,String>();
		parseIdFile(idFilename);
		parseCompareNlsFile(nlsFilename, outFilename);
	}

	private void parseCompareNlsFile(String inFilename, String outFilename) {
		BufferedReader in;
		BufferedWriter out;
		try {
			in = new BufferedReader(new FileReader(inFilename));
			out = new BufferedWriter(new FileWriter(outFilename));
			String line;
			int lineNumber = 0;
			String key;
			String value;
			while ((line=in.readLine())!=null) {
				lineNumber++;
				if (line.startsWith("#")) {
					continue;
				}
				if (line.isEmpty()) {
					continue;
				}
				if (line.matches("\\s")) {
					continue;
				}
				String[] keyValuePairs = line.split("=");
				if (keyValuePairs.length<2) {
					System.out.printf("Found line in %s:%d with either a key or value but not both\n",inFilename,lineNumber);
				}
				key = keyValuePairs[0];
				if (keyValuePairs.length==1) {
					value = "";
				}
				else {
					value = keyValuePairs[1];
				}
				if (!msgIds.containsKey(key)) {
					System.out.printf("%s=%s\n", key,value);
					out.write(String.format("%s=%s\n", key,value));
				}
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseIdFile(String filename) {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(filename));
			String line;
			int lineNumber = 0;
			while ((line=in.readLine())!=null) {
				lineNumber++;
				if (line.startsWith("#")) {
					continue;
				}
				if (line.isEmpty()) {
					continue;
				}
				if (line.matches("\\s")) {
					continue;
				}
				String[] keyValuePairs = line.split("=");
				if (keyValuePairs.length<2) {
					System.out.printf("Found line in %s:%d with either a key or value but not both\n",filename,lineNumber);
				}
				String key = keyValuePairs[0];
				String value = keyValuePairs[1];
				msgIds.put(key, value);
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void parseNlsFile(String filename) {
		
	}
	
	public static void main(String[] args) {
		new MessageIds();
	}

}
