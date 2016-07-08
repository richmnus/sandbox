package uk.co.martinus;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Client {

	private static final String endpoint1 = "http://127.0.0.1:8888/rest/library/hi";
	private static final String endpoint2 = "http://127.0.0.1:8888/rest/library/domain/env";

	// private static final String endpoint = "http://www.example.com";

	public static void main(String[] args) {
		Client client = new Client();
		client.postRequest();
	}

	private void sendRequests() {
		try {
			// GET requests
			HttpURLConnection conn = getConnection(endpoint1, "GET");
			conn.connect();
			printAndParse(conn, true);

			// conn = getConnection(endpoint + "?name=MarxBrothers", "GET");
			// conn.connect();
			// printAndParse(conn, false);

			conn = getConnection(endpoint2, "POST");
			conn.connect();
			printAndParse(conn, true);

		} catch (IOException e) {
			System.err.println(e);
		} catch (NullPointerException e) {
			System.err.println(e);
		}
	}

	private void postRequest() {
		try {
			HttpURLConnection conn = null;

			List<Integer> nums = new ArrayList<Integer>();
			for (int i = 1; i < 15; i++) {
				nums.add(i);
			}
			String payload = URLEncoder.encode("nums", "UTF-8") + "="
					+ URLEncoder.encode(nums.toString(), "UTF-8");

			conn = getConnection(endpoint2, "POST");
//			conn.setRequestProperty("accept", "text/xml");
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(payload);
			out.flush();

		} catch (IOException e) {
			System.err.println(e);
		} catch (NullPointerException e) {
			System.err.println(e);
		}
	}

	private HttpURLConnection getConnection(String urlStr, String verb) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(verb);
			conn.setDoInput(true);
			conn.setDoOutput(true);
		} catch (MalformedURLException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
		return conn;
	}

	private void printAndParse(HttpURLConnection conn, boolean parse) {
		try {
			String xml = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String next = null;
			while ((next = reader.readLine()) != null) {
				xml += next + "\n";
			}
			System.out.println("The raw XML:\n" + xml);
			if (parse) {
				SAXParser parser = SAXParserFactory.newInstance()
						.newSAXParser();
				parser.parse(new ByteArrayInputStream(xml.getBytes()),
						new SaxParserHandler());
			}
		} catch (IOException e) {
			System.err.println(e);
		} catch (ParserConfigurationException e) {
			System.err.println(e);
		} catch (SAXException e) {
			System.err.println(e);
		}
	}

	static class SaxParserHandler extends DefaultHandler {
		char[] buffer = new char[1024];
		int n = 0;

		public void startElement(String uri, String lname, String qname,
				Attributes attributes) {
			clearBuffer();
		}

		public void characters(char[] data, int start, int length) {
			System.arraycopy(data, start, buffer, 0, length);
			n += length;
		}

		public void endElement(String uri, String lname, String qname) {
			if (Character.isUpperCase(buffer[0])) {
				System.out.println(new String(buffer));
			}
			clearBuffer();
		}

		private void clearBuffer() {
			Arrays.fill(buffer, '\0');
			n = 0;
		}
	}

}
