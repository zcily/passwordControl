package com.szp.passwordControl;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

public class PasswordControlService extends Service {
    public static final int SZP_PASSWORD_DOWNLOAD_TYPE = Settings.System.SZP_PASSWORD_DOWNLOAD_TYPE; 
    public static final int SZP_PASSWORD_ADMINISTRATOR_TYPE = Settings.System.SZP_PASSWORD_ADMINISTRATOR_TYPE; 
    public static final int SZP_PASSWORD_SUPER_TYPE = Settings.System.SZP_PASSWORD_SUPER_TYPE; 
    public static final int SZP_PASSWORD_CALL_ALL_TYPE = Settings.System.SZP_PASSWORD_CALL_ALL_TYPE;
	
    private final int LENTH_PASSWORD = 6;
    
	public class PasswordControlImpl extends IPasswordControl.Stub {

		@Override
		public boolean checkPassWord(int type, String needCheckPassword)
				throws RemoteException {
			// TODO Auto-generated method stub
			if(!isValidType(type) || needCheckPassword == null)
				return false;
			
			String passWord = Settings.System.getString(getContentResolver(), getSettingStringFromType(type));
			
			//use super password can entry all control manager
			String superPassWord = Settings.System.getString(getContentResolver(),  
					getSettingStringFromType(SZP_PASSWORD_SUPER_TYPE));
			
			if(superPassWord != null && superPassWord.equals(needCheckPassword)) {
				return true;
			}
			
			if(passWord != null && passWord.length() == LENTH_PASSWORD) {
				return checkPassWordIsRight(type, passWord, needCheckPassword);
			}
			
			return false; 
		}

		@Override
		public boolean setPassWord(int type, String newPassword)
				throws RemoteException {
			// TODO Auto-generated method stub
			if(!isValidType(type) || newPassword == null)
				return false;
			
			if(!checkNewPasswordIsValid(newPassword)) 
				return false;
			
			if(isLocked(type)) 
				return false;
			
			Settings.System.putString(getContentResolver(), getSettingStringFromType(type), newPassword);
			
			return true;
		}
		
		@Override
		public boolean isLocked(int type) throws RemoteException {
			// TODO Auto-generated method stub
			return PasswordLockedControl.passwordIsLocked(getApplicationContext(), type);
		}
		
		private boolean checkPassWordIsRight(int type, String origPassword,  String needCheckPassword) {
			boolean result = true;
			
			if(origPassword == null || needCheckPassword == null)
				return false;
			
			if(origPassword.length() != needCheckPassword.length()) {
				PasswordLockedControl.setPasswordWrong(getApplicationContext(), type);
				return false;
			}
			
			for(int i = origPassword.length(); i > 0; --i) {
				if(origPassword.charAt(i - 1) != needCheckPassword.charAt(i - 1)) {
					PasswordLockedControl.setPasswordWrong(getApplicationContext(), type);
					return false;
				}
			}
			
			if(PasswordLockedControl.getPasswordWrongCount(getApplicationContext(), type) > 0) {
				PasswordLockedControl.clearPasswordWrongCount(getApplicationContext(), type);
			}
			
			return true;
		}
		
		//the password is only number and alphabet
		private boolean checkNewPasswordIsValid(String newPassWord) {
			boolean result = true;
			
			if(newPassWord == null)
				return false;
			
			if(newPassWord.length() != LENTH_PASSWORD) 
				return false;
			
			for(int i = 0; i < newPassWord.length();  ++i) {
				char value = newPassWord.charAt(i);
				if(!Character.isAlphabetic(value) && !Character.isDigit(value))  {
					result = false;
					Toast.makeText(getApplicationContext(), getApplication().getResources().getString(R.string.PassWordOnlySupportDigitAndAlphat),
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
			return result;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		return new PasswordControlImpl();
	}
	
	public static boolean isValidType(int type) {
		boolean result = false;
		
		switch (type) {
		case SZP_PASSWORD_DOWNLOAD_TYPE:
		case SZP_PASSWORD_ADMINISTRATOR_TYPE:
		case SZP_PASSWORD_SUPER_TYPE:
		case SZP_PASSWORD_CALL_ALL_TYPE:
			result = true;
			break;
		default:
			break;
		}
		return result;
	}
	
	public static String getSettingStringFromType(int type) {
		if(type == SZP_PASSWORD_DOWNLOAD_TYPE) {
			return Settings.System.SZP_DOWNLOAD_PASSWORD;
		} else if (type == SZP_PASSWORD_ADMINISTRATOR_TYPE) {
			return Settings.System.SZP_ADMIN_PASSWORD; 
		} else if (type == SZP_PASSWORD_SUPER_TYPE) {
			return Settings.System.SZP_SUPER_PASSWORD;
		} else if (type == SZP_PASSWORD_CALL_ALL_TYPE) {
			return Settings.System.SZP_ALL_CALL_PASSWORD;
		}
		
		// it can not come here.
		return null;
	}
}
