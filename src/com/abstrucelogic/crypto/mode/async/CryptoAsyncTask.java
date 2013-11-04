package com.abstrucelogic.crypto.mode.async;

import android.os.AsyncTask;

public class CryptoAsyncTask extends AsyncTask<Void, Integer, Boolean> {

	private CryptoAsyncHandler mHandler;
	
	public CryptoAsyncTask(CryptoAsyncHandler curHandler){
		this.mHandler = curHandler;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		this.mHandler.exec();
		return null;
	}
	
}
