package com.ailhanli.moneytransfer.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

/**
 * Utilities class for validation of input
 */
@Component
public class ValidationUtil {
	
    public  boolean isAccountNumberValid(String value) {
    	return !isNullOrEmpty(value) && isNumber(value);
    }
    
    public  boolean isNullOrEmpty(String value) {
    	return value==null || value.trim().equals("");
    }
    
    @SuppressWarnings("deprecation")
	public  boolean isNumber(String value) {
    	return NumberUtils.isNumber(value);
    }
}