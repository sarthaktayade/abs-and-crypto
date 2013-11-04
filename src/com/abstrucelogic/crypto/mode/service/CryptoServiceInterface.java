/**
 * 
 */
package com.abstrucelogic.crypto.mode.service;

import com.abstrucelogic.crypto.mode.service.CryptoService.CryptoServiceBinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

/**
 * @author abslog
 *
 */
public class CryptoServiceInterface {

	private static CryptoServiceInterface instance;
	
	private Context mContext;
	
	private CryptoServiceInterface(Context context) {
		this.mContext = context;
		Intent serviceIntent = new Intent(this.mContext, CryptoService.class);
		context.bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
	}
	
	public static CryptoServiceInterface getIntstance(Context context) {
		if(CryptoServiceInterface.instance == null) {
			CryptoServiceInterface.instance = new CryptoServiceInterface(context);
		}
		return CryptoServiceInterface.instance;
	}
	
	public void scheduleInService(String inPath) {
		startCryptoProcess(inPath);
	}
	
	private void startCryptoProcess(String inPath) {
		Intent serviceIntent = new Intent(this.mContext, CryptoService.class);
		serviceIntent.putExtra(CryptoService.EXTRA_IN_PATH, inPath);
		this.mContext.startService(serviceIntent);
	}
	
	private ServiceConnection serviceConn = new ServiceConnection()	{
		public void onServiceConnected(ComponentName className, IBinder binder) {		
			Toast.makeText(CryptoServiceInterface.this.mContext, "Service connected", Toast.LENGTH_SHORT).show();
			CryptoServiceBinder curBinder = (CryptoServiceBinder) binder;
		}
		
		public void onServiceDisconnected(ComponentName className) {
			Toast.makeText(CryptoServiceInterface.this.mContext, "Service disconnected", Toast.LENGTH_SHORT).show();
		}
	};
	
}
