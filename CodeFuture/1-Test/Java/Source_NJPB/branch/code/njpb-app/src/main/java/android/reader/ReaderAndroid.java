package android.reader;

import java.io.File;
import java.io.IOException;

//import android.util.Log;

public class ReaderAndroid {
	// JNI 
	private native static int open(String path, int baudrate);

	public native void close(int handle);

	// For ISO14443 Type-A
	public native int getCardSN(int handle, int address, byte mode, byte cmd, byte[] hald_flag, byte[] SN);

	public native int MFRead(int handle, int address, byte mode, byte blk_add, byte num_blk, byte[] PWS, byte[] buffer);

	public native int MFWrite(int handle, int address, byte mode, byte blk_add, byte num_blk, byte[] PWS, byte[] buffer);

	public native int MFGetVersion(int handle, int address, byte[] buffer, byte[] retlen);

	//public native int MFRstAnt(int handle, int address, byte[] buffer, byte[] retlen);
	// native int MFCloseAnt(int handle, int address, byte[] buffer, byte[] retlen);
	/*
	*///
	public native int CPU_RATS(int handle, int address, byte[] param, byte[] buff, byte[] retlen);

	public native int CPU_APDU(int handle, int address, byte[] param, byte paramlen, byte[] buff, byte[] retlen);

	public native int CPU_RST_Ant(int handle, int address, byte[] buff, byte[] retlen);

	// For 15693 Card
	public native int Inventory_15693(int handle, int address, byte Flags, byte AFI, byte masklen, byte[] mask, byte[] retlen, byte[] buff);

	public native int ReadBlk_15693(int handle, int address, byte Flags, byte StartBlk, byte Blks, byte[] UID, byte[] retlen, byte[] buff);

	public native int WriteBlk_15693(int handle, int address, byte Flags, byte StartBlk, byte Blks, byte[] UID, byte[] WriteDat, byte[] buff);

	public native int LockBlk_15693(int handle, int address, byte Flags, byte numBlk, byte[] UID, byte[] buff);

	// For TypeB
	public native int getCardUID(int handle, int address, byte[] retlen, byte[] uid);

	static {
		System.loadLibrary("ReaderAndroid");
		//Log.i("ReaderAndroid", "Android LIB install");
	}

	private static final String TAG = "ReaderAndroid";
	private int mFd;

	public ReaderAndroid(File device, int baudrate) throws SecurityException, IOException {
				mFd = open(device.getAbsolutePath(), baudrate);

		if (mFd == -1) {
			//Log.e(TAG, "native open returns null");
			throw new IOException();
		}

	}

	// Getters and setters
	public int getHandle() {
		return mFd;
	}
}