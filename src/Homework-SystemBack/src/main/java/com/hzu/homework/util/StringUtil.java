package com.hzu.homework.util;

public class StringUtil {
	
	public static boolean isNumber(String str)
	  {
	    for (int i = 0; i < str.length(); i++) {
	      if (!Character.isDigit(str.charAt(i))) {
	        return false;
	      }
	    }
	    return true;
	  }
	
	 public static String intToString(int num) {
		    String result = "0";
		    try {
		      result = Integer.toString(num);
		    }
		    catch (Exception e) {
		    }
		    return result;
	 }
	 public static boolean isEmpty(String str)
	  {
	    return (str == null) || (str.trim().equals("")) || (str.trim().equals("null"));
	  }
}
