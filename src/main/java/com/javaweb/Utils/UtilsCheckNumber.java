package com.javaweb.Utils;

public class UtilsCheckNumber {
	public static boolean isNumber(String data) {
		try {
			Long number = Long.parseLong(data);
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
}
