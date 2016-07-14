package com.szp.passwordControl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.widget.Toast;

public class PasswordControlService extends Service {
    private final int SZP_PASSWORD_DOWNLOAD_TYPE = Settings.System.SZP_PASSWORD_DOWNLOAD_TYPE; 
    private final int SZP_PASSWORD_ADMINISTRATOR_TYPE = Settings.System.SZP_PASSWORD_ADMINISTRATOR_TYPE; 
    private final int SZP_PASSWORD_SUPER_TYPE = Settings.System.SZP_PASSWORD_SUPER_TYPE; 
    private final int SZP_PASSWORD_CALL_ALL_TYPE = Settings.System.SZP_PASSWORD_CALL_ALL_TYPE;
	
    private final int LENTH_PASSWORD = 6;
    
	public class PasswordControlImpl extends IPasswordControl.Stub {

		@Override
		public boolean checkPassWord(int type, String needCheckPassword)
				throws RemoteException {
			// TODO Auto-generated method stub
			if(!isValidType(type) || needCheckPassword == null)
				return false;
			
			String passWord = Settings.System.getString(getContentResolver(), getSettingStringFromType(type));
			
			if(passWord != null && passWord.length() == LENTH_PASSWORD) {
				return checkPassWordIsRight(passWord, needCheckPassword);
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
			
			Settings.System.putString(getContentResolver(), getSettingStringFromType(type), newPassword);
			
			return false;
		}
		
		private boolean isValidType(int type) {
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
		
		private String getSettingStringFromType(int type) {
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
		
		private boolean checkPassWordIsRight(String origPassword,  String needCheckPassword) {
			boolean result = true;
			
			if(origPassword == null || needCheckPassword == null)
				return false;
			
			if(origPassword.length() != needCheckPassword.length())
				return false;
			
			for(int i = origPassword.length(); i > 0; --i) {
				if(origPassword.charAt(i - 1) != needCheckPassword.charAt(i - 1)) {
					result = false;
					break;
				}
			}
			
			return result;
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
					Toast.makeText(getApplicationContext(), "The Password only support digit and alphabet",
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
}
