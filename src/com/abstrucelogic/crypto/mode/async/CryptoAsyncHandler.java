package com.abstrucelogic.crypto.mode.async;

import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.mode.AbstractCryptoHandler;

public class CryptoAsyncHandler extends AbstractCryptoHandler {
	
	public CryptoAsyncHandler(CryptoConf conf) {
		super(conf);
	}

	@Override
	public void scheduledForExec() {
		this.exec();
	}
	
	public void exec() {
		new CryptoAsyncTask(this).execute((Void) null);
	}

}
