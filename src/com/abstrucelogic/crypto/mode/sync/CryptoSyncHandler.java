package com.abstrucelogic.crypto.mode.sync;

import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.mode.AbstractCryptoHandler;

public class CryptoSyncHandler extends AbstractCryptoHandler {
	
	public CryptoSyncHandler(CryptoConf conf) {
		super(conf);
	}
	
	@Override
	public void scheduledForExec() {
		this.exec();
	}
	
}
