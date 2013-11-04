package com.abstrucelogic.crypto.mode;

import com.abstrucelogic.crypto.processor.CryptoProcessStatusListener;

public interface CryptoHandler extends CryptoProcessStatusListener {
	
	public void scheduledForExec();
	public void exec();
	
}
