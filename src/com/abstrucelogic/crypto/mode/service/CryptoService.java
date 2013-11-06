package com.abstrucelogic.crypto.mode.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

import com.abstrucelogic.crypto.CryptoScheduler;
import com.abstrucelogic.crypto.mode.CryptoHandler;

public class CryptoService extends Service {

	public static String EXTRA_IN_PATH = "inpath";
	private IBinder curBinder;
	private ServiceHandler curServiceHandler;
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "Service onStart", Toast.LENGTH_SHORT).show();
		Message msg = this.curServiceHandler.obtainMessage(); 
		msg.arg1 = startId;
		msg.obj = intent.getStringExtra(EXTRA_IN_PATH);
		this.curServiceHandler.sendMessage(msg);	
		return START_STICKY;
	}

	public void onCreate() {
		HandlerThread encThread = new HandlerThread("EncDecThread", Process.THREAD_PRIORITY_BACKGROUND);
		encThread.start();
		Looper encThreadLooper = encThread.getLooper();
		this.curServiceHandler = new ServiceHandler(encThreadLooper);
		//think about showing a notification for the service
	}
	
	public IBinder onBind(Intent intent) {
		if(this.curBinder == null) {
			this.curBinder = new CryptoServiceBinder();
		}
		return this.curBinder;	
	}

	public void onDestroy() {
		Toast.makeText(this, "onDestroy service", Toast.LENGTH_SHORT).show();
	}
	
	private class ServiceHandler extends Handler {
		
		public ServiceHandler(Looper l) {
			super(l);
		}

		public void handleMessage(Message msg) {
			String inPath = (String) msg.obj;
			CryptoHandler handler = CryptoScheduler.getInstance().getScheduledTask(inPath);
			handler.exec();
			CryptoService.this.stopSelf(msg.arg1);
		}
	}

	public class CryptoServiceBinder extends Binder {
		public CryptoService getService() {
			return CryptoService.this;
		}
	}		

}
