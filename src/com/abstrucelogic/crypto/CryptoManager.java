package com.abstrucelogic.crypto;

import android.content.Context;

import com.abstrucelogic.crypto.conf.CryptoConf;

/**
 * This class is the entry point for an application to use the library functionality.
 * Applications should not be required to access other classes from the library 
 * (Of-course they will need to create an instance CryptoConf when calling the process method present in this class).
 * 
 * @author abslog
 */
public class CryptoManager {

	private static CryptoManager instance;
	
	/**
	 * Private constructor for singleton implementation.
	 */
	private CryptoManager() {
	
	}

	/**
	 * Method to return the singleton instance of this class.
	 * @return Singleton instance of CryptoManager
	 */
	public static CryptoManager getInstance() {
		if(CryptoManager.instance == null) {
			CryptoManager.instance = new CryptoManager();	
		}
		return CryptoManager.instance;
	}

	/**
	 * Starts a new crypto process.
	 * 
	 * @param conf - Information about the crypto process.
	 * @param context - The application context.
	 */
	public void process(CryptoConf conf, Context context) {
		CryptoScheduler.getInstance().scheduleNewTask(conf, context); 
	}
	
}
