package ch8;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TestDate {

	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(date.toString());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_WEEK, 2);
		date = c.getTime();
		System.out.println(date.toString());
		
		Locale locDe = new Locale("de");
		Locale locDeCh = new Locale("de", "CH");
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locDe);
		System.out.println(df.format(date));
		df = DateFormat.getDateInstance(DateFormat.FULL, locDeCh);
		System.out.println(df.format(date));
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(locDeCh);
		System.out.println(nf.format(123.00));
		
	}

	
	
}
