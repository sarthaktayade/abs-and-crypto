package com.abstrucelogic.crypto.mode.sync;

import android.content.Context;

import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.mode.AbstractCryptoHandler;

public class CryptoSyncHandler extends AbstractCryptoHandler {
	
	public CryptoSyncHandler(CryptoConf conf, Context context) {
		super(conf, context);
	}
	
	@Override
	public void scheduledForExec() {
		this.exec();
	}
	
}
