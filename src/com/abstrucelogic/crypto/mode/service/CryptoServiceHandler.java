package com.abstrucelogic.crypto.mode.service;

import android.content.Context;

import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.mode.AbstractCryptoHandler;

public class CryptoServiceHandler extends AbstractCryptoHandler {
	
	private Context mCurContext;
	
	public CryptoServiceHandler(CryptoConf conf, Context context) {
		super(conf, context);
		this.mCurContext = context;
	}

	@Override
	public void scheduledForExec() {
		CryptoServiceInterface.getIntstance(this.mCurContext).scheduleInService(this.mCurCryptoConf.getInputFilePath());
	}
}
