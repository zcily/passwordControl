package com.szp.passwordControl;

/*
 * @hide
 */
interface IPasswordControl {
	/*
	 * @hide
	 */
	boolean checkPassWord(int type, String password);
	
	/*
	 * @hide
	 */
	boolean setPassWord(int type, String password);

	/*
	 * @hide
	 */
	boolean isLocked(int type);
}