package com.ailhanli.moneytransfer.util;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Utilities class for validation of input
 */
public class ValidationUtil {
	
    public static boolean isAccountNumberValid(String value) {
    	return !isNullOrEmpty(value) && isNumber(value);
    }
    
    public static boolean isNullOrEmpty(String value) {
    	return value==null || value.trim().equals("");
    }
    
    @SuppressWarnings("deprecation")
	public static boolean isNumber(String value) {
    	return NumberUtils.isNumber(value);
    }
}