package com.tkis.qedbot;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ServerSideValidation {
	boolean error = false;
	String alphanumberic = "[a-zA-Z0-9]*";
	String alphanumbericSpace = "[a-z A-Z0-9]*";
	String alphabetic = "[a-zA-Z]*";
	String alphabeticSpace = "[a-z A-Z]*";
	String numberic = "[0-9]*";
	String emailid = ".+@.+\\.[a-z]+";
	String numbericwithhipen = "[0-9 -]*)";
	String date = "[0-9\\/]*";
	String imagename = ".+\\.[a-z]+";
	String salutation = "[a-zA-Z]+\\.";
	String Secrect_question = "[a-z A-Z0-9]+\\?";
	String hostName = "[a-z.0-9]*";
	String alphanumbericSpaceDot = "[A-Z0-9 .,a-z]*";

	public ServerSideValidation() {
	}

	public boolean isValidEmailId(String str) {
		boolean flag = false;
		if (str.matches(emailid)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public boolean isValidSalutation(String str) {
		boolean flag = false;
		if (str.matches(salutation)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public boolean isValidSecrectQuestion(String str) {
		boolean flag = false;
		if (str.matches(Secrect_question)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public boolean isValidDate(String str) {
		boolean flag = false;
		if (str.matches(date)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public boolean isValidImage(String str) {
		boolean flag = false;
		if (str.matches(imagename)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public String getRefindedString(String str) throws SQLException, Exception {
		str = str.trim();
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		return str;
	}

	public boolean isStringEmpty(String str) {
		boolean flag = false;
		str = str.trim();

		if ((str.length() < 1) || (str == "")) {
			flag = true;
		}
		return flag;
	}

	public boolean isAlphaString(String str) {
		boolean flag = false;
		if (str.matches(alphabetic)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public boolean isAlphaWithSpace(String str) {
		boolean flag = false;
		if (str.matches(alphabeticSpace)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public boolean isAlphNumberic(String str) {
		boolean flag = false;
		if (str.matches(alphanumberic)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public boolean isAlphNumbericWithSpace(String str) {
		boolean flag = false;
		if (str.matches(alphanumbericSpace)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public boolean isNumbericString(String str) {
		boolean flag = false;
		if (str.matches(numberic)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public boolean isNumbericWithHipen(String str) {
		boolean flag = false;
		if (str.matches(numbericwithhipen)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public int isValidPassword(String password) {
		int errorCount = 0;

		password = password.trim();
		System.out.println("Password is :" + password);
		if ((password.length() <= 7) || (password.length() > 15)) {
			System.out.println("Invalid length");
			errorCount = 1;
		}
		return errorCount;
	}

	public boolean checkValidURL(String refererURL, String currentURL) {
		boolean validURL = false;
		String partRefererURL = "";
		String partCurrentURL = "";

		try {
			partRefererURL = refererURL.substring(0, refererURL.lastIndexOf('/'));
			partCurrentURL = currentURL.substring(0, currentURL.lastIndexOf('/'));

			System.out.println("partRefererURL : " + partRefererURL);
			System.out.println("partCurrentURL : " + partCurrentURL);

			if ((partRefererURL.equals(partCurrentURL)) && (refererURL != null)) {
				validURL = true;
			} else {
				validURL = false;
			}

		} catch (Exception ex) {
			System.out.println("URL General Exception !!!");
			System.out.println("URL Exception Message : " + ex.getMessage());
		}
		System.out.println("Valid URL " + validURL);
		return validURL;
	}

	public String getTodayDate() {
		Date dt = new Date();
		Format formatter = new SimpleDateFormat("MMMyyyy");
		String curr_date = formatter.format(dt);
		return curr_date;
	}

	public boolean isCurrentFileFormat(String str, String type) throws SQLException, Exception {
		boolean flag = false;
		str = str.trim();
		type = "." + type;
		String subs = str.substring(str.lastIndexOf('.'), str.length()).toLowerCase();
		if (subs.matches(type)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isValidHostName(String str) {
		boolean flag = false;
		if (str.matches(hostName)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public boolean isAlphaNumbericSpaceDot(String str) {
		boolean flag = false;
		if (str.matches(alphanumbericSpaceDot)) {
			flag = true;
		} else
			flag = false;
		return flag;
	}

	public String getDynamicDate(int count) {
		Calendar today = Calendar.getInstance();
		today.add(5, count);
		Date dt = new Date(today.getTimeInMillis());
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String day = formatter.format(dt);
		return day;
	}

	public static void main(String[] args) {
		String date = "01/2009";

		ServerSideValidation s = new ServerSideValidation();
		System.out.println("Error code for Password :" + s.getDynamicDate(2));
	}
}
