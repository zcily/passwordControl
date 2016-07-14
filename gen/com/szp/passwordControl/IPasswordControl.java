/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: Z:\\SZP_PasswordControl\\src\\com\\szp\\passwordControl\\IPasswordControl.aidl
 */
package com.szp.passwordControl;
/*
 * @hide
 */
public interface IPasswordControl extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.szp.passwordControl.IPasswordControl
{
private static final java.lang.String DESCRIPTOR = "com.szp.passwordControl.IPasswordControl";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.szp.passwordControl.IPasswordControl interface,
 * generating a proxy if needed.
 */
public static com.szp.passwordControl.IPasswordControl asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.szp.passwordControl.IPasswordControl))) {
return ((com.szp.passwordControl.IPasswordControl)iin);
}
return new com.szp.passwordControl.IPasswordControl.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_checkPassWord:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.checkPassWord(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setPassWord:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.setPassWord(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isLocked:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isLocked(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.szp.passwordControl.IPasswordControl
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/*
	 * @hide
	 */
@Override public boolean checkPassWord(int type, java.lang.String password) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
_data.writeString(password);
mRemote.transact(Stub.TRANSACTION_checkPassWord, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/*
	 * @hide
	 */
@Override public boolean setPassWord(int type, java.lang.String password) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
_data.writeString(password);
mRemote.transact(Stub.TRANSACTION_setPassWord, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/*
	 * @hide
	 */
@Override public boolean isLocked(int type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_isLocked, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_checkPassWord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setPassWord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_isLocked = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
/*
	 * @hide
	 */
public boolean checkPassWord(int type, java.lang.String password) throws android.os.RemoteException;
/*
	 * @hide
	 */
public boolean setPassWord(int type, java.lang.String password) throws android.os.RemoteException;
/*
	 * @hide
	 */
public boolean isLocked(int type) throws android.os.RemoteException;
}
