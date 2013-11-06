package com.abstrucelogic.crypto.mode.async;

import android.content.Context;

import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.mode.AbstractCryptoHandler;

public class CryptoAsyncHandler extends AbstractCryptoHandler {
	
	private CryptoAsyncTask mCurAsyncTask;
	
	public CryptoAsyncHandler(CryptoConf conf, Context context) {
		super(conf, context);
		this.mCurAsyncTask = new CryptoAsyncTask(this);
	}

	@Override
	public void scheduledForExec() {
		this.mCurAsyncTask.execute((Void) null);
	}

}
