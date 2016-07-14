package com.szp.passwordControl;

import android.R.integer;
import android.content.Context;
import android.provider.Settings;

public class PasswordLockedControl {
	public static final String PASSWORD_DOWNLOAD_WRONG_NUMBER = "password_download_wrong_number";
	public static final String PASSWORD_ADMINISTRATOR_WRONG_NUMBER = "password_administrator_wrong_number";
	public static final String PASSWORD_SUPER_WRONG_NUMBER = "password_super_wrong_number";
	public static final String PASSWORD_CALL_ALL_WRONG_NUMBER = "password_call_all_wrong_number";
	
	public static void setPasswordWrong(Context context, int type) {
		if(!PasswordControlService.isValidType(type))
			return;
		
		int getWrongNumber = SharePreferenceUtils.get(context, getShareKeyStringFromType(type), 0);
	}
	
	public static int getPasswordWrongNumber(int type) {
		if(!PasswordControlService.isValidType(type))
			return -1;
		
		return 0;
	}
	
	public static void clearPasswordWrongNumber(int type) {
		if(!PasswordControlService.isValidType(type))
			return;
		
	}
	
	private static String getShareKeyStringFromType(int type) {
		String result = "";
		if(type == PasswordControlService.SZP_PASSWORD_DOWNLOAD_TYPE) {
			result = PASSWORD_DOWNLOAD_WRONG_NUMBER;
		} else if (type == PasswordControlService.SZP_PASSWORD_ADMINISTRATOR_TYPE) {
			result = PASSWORD_ADMINISTRATOR_WRONG_NUMBER;
		} else if (type == PasswordControlService.SZP_PASSWORD_SUPER_TYPE) {
			result = PASSWORD_SUPER_WRONG_NUMBER;
		} else if (type == PasswordControlService.SZP_PASSWORD_CALL_ALL_TYPE) {
			result = PASSWORD_CALL_ALL_WRONG_NUMBER;
		}

		return result;
	}
}
