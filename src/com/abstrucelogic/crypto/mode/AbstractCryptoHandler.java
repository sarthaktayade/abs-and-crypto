package com.abstrucelogic.crypto.mode;

import javax.crypto.Cipher;

import android.content.Context;

import com.abstrucelogic.crypto.CryptoScheduler;
import com.abstrucelogic.crypto.conf.CryptoConf;
import com.abstrucelogic.crypto.constants.CryptoOperation;
import com.abstrucelogic.crypto.constants.CryptoProcessStatus;
import com.abstrucelogic.crypto.notificatoin.CryptoNotification;
import com.abstrucelogic.crypto.processor.DecryptionProcessor;
import com.abstrucelogic.crypto.processor.EncryptionProcessor;

/**
 * @author abslog
 *
 */
public abstract class AbstractCryptoHandler implements CryptoHandler {

	private CryptoNotification mCurNot;
	
	protected CryptoConf mCurCryptoConf;
	protected EncryptionProcessor mEncProcessor;
	protected DecryptionProcessor mDecProcessor;
	
	public AbstractCryptoHandler(CryptoConf conf, Context curContext) {
		this.mEncProcessor = new EncryptionProcessor();
		this.mEncProcessor.setProgressListener(this);
		this.mDecProcessor = new DecryptionProcessor();
		this.mDecProcessor.setProgressListener(this);
		this.mCurCryptoConf = conf;
		this.mCurNot = new CryptoNotification(curContext, conf.getInputFilePath());
	}
	
	@Override
	public void processStatusUpdate(CryptoProcessStatus status, float progressPer) {
		switch(status) {
			case COMPLETE :
				android.util.Log.e("CryptoHandler", "complete");
				this.mCurNot.setCompleteNotification();
				this.mCurCryptoConf.getListener().cryptoProcessComplete();
				CryptoScheduler.getInstance().requestRemoveFromSchedulingMap(this.mCurCryptoConf);
				break;
			case ERROR :
				android.util.Log.e("CryptoHandler", "error");
				this.mCurNot.setErrorNotification();
				this.mCurCryptoConf.getListener().cryptoProcessError();
				CryptoScheduler.getInstance().requestRemoveFromSchedulingMap(this.mCurCryptoConf);
				break;
			case INPROGRESS : 
				android.util.Log.e("CryptoHandler", "inprogress - " + progressPer);
				this.mCurNot.updateNotification(progressPer);
				this.mCurCryptoConf.getListener().cryptoInProgress(progressPer);
				break;
			case START:
				android.util.Log.e("CryptoHandler", "start");
				this.mCurNot.showNotification();
				this.mCurCryptoConf.getListener().cryptoProcessStarted();
				break;
		}
	}


	public abstract void scheduledForExec();

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
