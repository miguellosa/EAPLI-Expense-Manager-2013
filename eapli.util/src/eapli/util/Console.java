/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * utility class for reading different data types fro the Console.
 *
 * based on code from Nuno Silva
 *
 * @author Paulo Gandra Sousa
 *
 */
public final class Console {

	private Console() {
	}

	static public String readLine(String prompt) {
		try {
			System.out.println(prompt);
			InputStreamReader converter = new InputStreamReader(System.in);
			BufferedReader in = new BufferedReader(converter);

			return in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static public int readInteger(String prompt) {
		do {
			try {
				String strInt = readLine(prompt);
				int valor = Integer.parseInt(strInt);

				return valor;
			} catch (NumberFormatException ex) {
				Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
															  null, ex);
			}
		} while (true);
	}

	static public boolean readBoolean(String prompt) {
		do {
			try {
				String strBool = readLine(prompt).toLowerCase();
				if (strBool.equals("s") || strBool.equals("y")) {
					return true;
				} else if (strBool.equals("n")) {
					return false;
				}
			} catch (NumberFormatException ex) {
				Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
															  null, ex);
			}
		} while (true);
	}

	static public int readOption(int low, int high, int exit) {
		int option;
		do {
			option = Console.readInteger("Introduza opção: ");
			if (option == exit) {
				break;
			}
		} while (option < low || option > high);
		return option;
	}

	static public Date readDate(String prompt) {
		do {
			try {
				String strDate = readLine(prompt);
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				Date date = df.parse(strDate);

				return date;
			} catch (ParseException ex) {
				Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
															  null, ex);
			}
		} while (true);
	}

	static public Calendar readCalendar(String prompt) {
		do {
			try {
				String strDate = readLine(prompt);
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				Calendar date = DateTime.dateToCalendar(df.parse(strDate));

				return date;
			} catch (ParseException ex) {
				Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
															  null, ex);
			}
		} while (true);
	}

	public static double readDouble(String prompt) {
		do {
			try {
				String input = readLine(prompt);
				double valor = Double.parseDouble(input);

				return valor;
			} catch (NumberFormatException ex) {
				Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
															  null, ex);
			}
		} while (true);
	}

	public static void waitForKey(String prompt) {
		System.out.println(prompt);
		try {
			System.in.read();
		} catch (IOException ex) {
			Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null,
														  ex);
		}
	}
}
