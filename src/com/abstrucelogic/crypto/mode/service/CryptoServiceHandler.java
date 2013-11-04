package com.abstrucelogic.crypto.mode.service;

import javax.crypto.Cipher;

import android.content.Context;

import com.abstrucelogic.crypto.CryptoHandler;
import com.abstrucelogic.crypto.CryptoScheduler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoOperation;
import com.abstrucelogic.crypto.constants.CryptoProcessStatus;
import com.abstrucelogic.crypto.processor.DecryptionProcessor;
import com.abstrucelogic.crypto.processor.EncryptionProcessor;

public class CryptoServiceHandler implements CryptoHandler {
	
	private CryptoConf mCurCryptoConf;
	private Context mCurContext;
	
	private EncryptionProcessor mEncProcessor;
	private DecryptionProcessor mDecProcessor;
	
	public CryptoServiceHandler(CryptoConf conf, Context context) {
		this.mCurCryptoConf = conf;
		this.mCurContext = context;
		this.mEncProcessor = new EncryptionProcessor();
		this.mEncProcessor.setProgressListener(this);
		this.mDecProcessor = new DecryptionProcessor();
		this.mDecProcessor.setProgressListener(this);
	}
	
	@Override
	public void processStatusUpdate(CryptoProcessStatus status, float progressPer) {
		switch(status) {
			case COMPLETE :
				this.mCurCryptoConf.getListener().cryptoProcessComplete();
				CryptoScheduler.getInstance().requestRemoveFromSchedulingMap(this.mCurCryptoConf);
				break;
			case ERROR :
				this.mCurCryptoConf.getListener().cryptoProcessError();
				CryptoScheduler.getInstance().requestRemoveFromSchedulingMap(this.mCurCryptoConf);
				break;
			case INPROGRESS : 
				this.mCurCryptoConf.getListener().cryptoInProgress(progressPer);
				break;
			case START:
				this.mCurCryptoConf.getListener().cryptoProcessStarted();
				break;
		}
	}

	@Override
	public void scheduledForExec() {
		CryptoServiceInterface.getIntstance(this.mCurContext).scheduleInService(this.mCurCryptoConf.getInputFilePath());
	}
	
	@Override
	public void exec() {
		CryptoOperation opp = this.mCurCryptoConf.getOperation();
		switch(opp) {
			case ENCRYPTION:
				this.encrypt(this.mCurCryptoConf.getInputFilePath(), this.mCurCryptoConf.getOutputFilePath(), this.mCurCryptoConf.getCipher());
				break;
			case DECRYPTION:
				this.decrypt(this.mCurCryptoConf.getInputFilePath(), this.mCurCryptoConf.getOutputFilePath(), this.mCurCryptoConf.getCipher());
				break;
		}
	}
	
	private void encrypt(String inputFilePath, String outputFilePath, Cipher cipher) {
		this.mEncProcessor.encryptFile(inputFilePath, outputFilePath, cipher);
	}
	
	private void decrypt(String inputFilePath, String outputFilePath, Cipher cipher) {
		this.mDecProcessor.decryptFile(inputFilePath, outputFilePath, cipher);
	}
}
