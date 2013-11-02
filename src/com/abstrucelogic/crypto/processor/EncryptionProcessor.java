package com.abstrucelogic.crypto.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import com.abstrucelogic.crypto.constants.CryptoProcessStatus;

/**
 * 
 * This is used to perform the actual encryption procedure.
 * 
 * @author abslog
 *
 */
public class EncryptionProcessor {
	
	private CryptoProcessStatusListener mProcessListener;
	
	public void setProgressListener(CryptoProcessStatusListener listener) {
		this.mProcessListener = listener;
	}
	
	/**
	 * This method encrypts a file and places the encrypted file in the given file path.
	 *
	 * @param inputFilePath - path of the file to encrypt
	 * @param outputFilePath - path of the final encrypted file.
	 * @param cipher - the cipher object which holds the encryption method details.
	 */
	public void encryptFile(String inputFilePath, String outputFilePath, Cipher cipher) {
		FileInputStream inStream = null;
		BufferedInputStream buffInStream = null;
		FileOutputStream outStream = null;
		BufferedOutputStream buffOutStream = null;
		CipherOutputStream cipherOutStream = null;
		try {
			//input file
			File inputFile = new File(inputFilePath);	
			inStream = new FileInputStream(inputFile);
			buffInStream = new BufferedInputStream(inStream);				
			
			//output file
			File outputFile = new File(outputFilePath);
			if(outputFile.exists()) {
				//break the process and ask the user to rename the file before attempting encryption
			} else {
				outputFile.createNewFile();
			}
			outStream = new FileOutputStream(outputFile);	
			buffOutStream = new BufferedOutputStream(outStream);
		
			//encryption
			cipherOutStream = new CipherOutputStream(buffOutStream, cipher);	
			byte[] buff = new byte[10240];
			int bytesAvailable = 0;
			int totalBytesRead = 0;
			int numOfBytesRead = 0;
			bytesAvailable = buffInStream.available();
			while(true) {
				numOfBytesRead = buffInStream.read(buff, 0, buff.length);
				if(numOfBytesRead != -1) {
					cipherOutStream.write(buff, 0, numOfBytesRead);
					cipherOutStream.flush();
					totalBytesRead = totalBytesRead + numOfBytesRead;
					if(mProcessListener != null) {
						mProcessListener.processStatusUpdate(CryptoProcessStatus.INPROGRESS, ((float)totalBytesRead/(float)bytesAvailable) * 100);
					}
				} else {
					if(mProcessListener != null) {
						mProcessListener.processStatusUpdate(CryptoProcessStatus.COMPLETE, 100);
					}
					break;
				}
			}
		} catch(Exception ex) {
			if(mProcessListener != null) {
				mProcessListener.processStatusUpdate(CryptoProcessStatus.ERROR, 0);
			}
			ex.printStackTrace();
		} finally {
			try {
				buffInStream.close();
				inStream.close();
				cipherOutStream.close();
				buffOutStream.close();
				outStream.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}	
		}
	}
}
