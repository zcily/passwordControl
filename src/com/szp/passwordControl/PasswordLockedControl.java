package com.szp.passwordControl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class PasswordLockedControl {
	public static final String PASSWORD_DOWNLOAD_WRONG_COUNT = "password_download_wrong_count";
	public static final String PASSWORD_ADMINISTRATOR_WRONG_COUNT = "password_administrator_wrong_count";
	public static final String PASSWORD_SUPER_WRONG_COUNT = "password_super_wrong_count";
	public static final String PASSWORD_CALL_ALL_WRONG_COUNT = "password_call_all_wrong_count";
	
	public static final String PASSWORD_DOWNLOAD_WRONG_TIME = "password_download_wrong_time";
	public static final String PASSWORD_ADMINISTRATOR_WRONG_TIME = "password_administrator_wrong_time";
	public static final String PASSWORD_SUPER_WRONG_TIME = "password_super_wrong_time";
	public static final String PASSWORD_CALL_ALL_WRONG_TIME = "password_call_all_wrong_time";
	
	private static final int LIMIT_MAX_WRONG_COUNT = 5;
	private static final int NEXT_TIME_ALLOW_TO_ENTERY_PASSWORD = 10800; //3 * 60 * 60 (3 hours)
	private static final int DEFAULT_ERROR_COUNT = 0;
	private static final int DEFAULT_ERROR_TIME =  0;
	
	public static void setPasswordWrong(Context context, int type) {
		if(!PasswordControlService.isValidType(type))
			return;
		
		String typeKey = getShareKeyStringFromType(type);
		int getWrongCount = (Integer)SharePreferenceUtils.get(context, typeKey, Integer.valueOf(DEFAULT_ERROR_COUNT));
		
		if(++getWrongCount >= LIMIT_MAX_WRONG_COUNT) {
			getWrongCount = LIMIT_MAX_WRONG_COUNT;
			SharePreferenceUtils.put(context, getShareTimeKeyStringFromType(type), Long.valueOf(System.currentTimeMillis()));
		} 
		
		SharePreferenceUtils.put(context, typeKey, Integer.valueOf(getWrongCount));
	}
	
	public static int getPasswordWrongCount(Context context, int type) {
		if(!PasswordControlService.isValidType(type))
			return -1;
		
		String typeKey = getShareKeyStringFromType(type);
		
		return (Integer)SharePreferenceUtils.get(context, typeKey, Integer.valueOf(DEFAULT_ERROR_COUNT));
	}
	
	public static void clearPasswordWrongCount(Context context, int type) {
		if(!PasswordControlService.isValidType(type))
			return;
		String typeKey = getShareKeyStringFromType(type);
		String typeTimeKey = getShareTimeKeyStringFromType(type);
		
		SharePreferenceUtils.put(context, typeKey, Integer.valueOf(DEFAULT_ERROR_COUNT));
		SharePreferenceUtils.put(context, typeTimeKey, Long.valueOf(DEFAULT_ERROR_TIME));
	}
	
	public static boolean passwordIsLocked(Context context, int type) {
		if(!PasswordControlService.isValidType(type))
			return false;
		
		String typeKey = getShareKeyStringFromType(type);
		String typeTimeKey = getShareTimeKeyStringFromType(type);
		
		int wrongCount = (Integer)SharePreferenceUtils.get(context, typeKey, Integer.valueOf(DEFAULT_ERROR_COUNT));
		long wrongTime = (Long)SharePreferenceUtils.get(context, typeTimeKey, Long.valueOf(DEFAULT_ERROR_TIME));
		
		if(wrongCount >= LIMIT_MAX_WRONG_COUNT && isInLimitTime(context, type, wrongTime)) {
			showPasswordLocked(context);
			return true;
		}
		
		if(wrongCount >= LIMIT_MAX_WRONG_COUNT && !isInLimitTime(context, type, wrongTime)) {
			clearPasswordWrongCount(context,type);
		}
		
		return false;
	}
	
	private static String getShareKeyStringFromType(int type) {
		String result = "";
		if(type == PasswordControlService.SZP_PASSWORD_DOWNLOAD_TYPE) {
			result = PASSWORD_DOWNLOAD_WRONG_COUNT;
		} else if (type == PasswordControlService.SZP_PASSWORD_ADMINISTRATOR_TYPE) {
			result = PASSWORD_ADMINISTRATOR_WRONG_COUNT;
		} else if (type == PasswordControlService.SZP_PASSWORD_SUPER_TYPE) {
			result = PASSWORD_SUPER_WRONG_COUNT;
		} else if (type == PasswordControlService.SZP_PASSWORD_CALL_ALL_TYPE) {
			result = PASSWORD_CALL_ALL_WRONG_COUNT;
		}

		return result;
	}
	
	private static String getShareTimeKeyStringFromType(int type) {
		String result = "";
		if(type == PasswordControlService.SZP_PASSWORD_DOWNLOAD_TYPE) {
			result = PASSWORD_DOWNLOAD_WRONG_TIME;
		} else if (type == PasswordControlService.SZP_PASSWORD_ADMINISTRATOR_TYPE) {
			result = PASSWORD_ADMINISTRATOR_WRONG_TIME;
		} else if (type == PasswordControlService.SZP_PASSWORD_SUPER_TYPE) {
			result = PASSWORD_SUPER_WRONG_TIME;
		} else if (type == PasswordControlService.SZP_PASSWORD_CALL_ALL_TYPE) {
			result = PASSWORD_CALL_ALL_WRONG_TIME;
		}

		return result;
	}
	
	private static boolean isInLimitTime(Context context, int type, long wrongTime) {
		long currentTime = System.currentTimeMillis();
		
		if(wrongTime == 0) {
			return false;
		}
		
		//防止用户修改系统时间导致计算出错。 一旦修改系统时间 重新计算时间
		if(currentTime < wrongTime) {
			SharePreferenceUtils.put(context, getShareTimeKeyStringFromType(type), Long.valueOf(System.currentTimeMillis()));
			return true;
		}
		
		if((currentTime - wrongTime) / 1000 < NEXT_TIME_ALLOW_TO_ENTERY_PASSWORD)
			return true;
		
		return false;
	}
	
	private static void showPasswordLocked(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.PassWordLocked)
				.setPositiveButton(R.string.PassWordLockedDialogEnsure, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		
		builder.create().show();
	}
}
