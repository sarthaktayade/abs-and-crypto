/**
 * 
 */
package com.abstrucelogic.crypto.notificatoin;

import java.io.File;

import com.abstrucelogic.crypto.R;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * @author abslog
 */
public class CryptoNotification {

	private static int MAX_PROGRESS = 100;
	
	private Context mContext;
	private NotificationCompat.Builder mBuilder;
	private int mUniqId;
	private String mFileName;
	
	public CryptoNotification(Context context, String filePath) {
		this.mContext = context;
		this.init(filePath);
	}
	
	public void showNotification() {
		NotificationManager notMan = (NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		this.mBuilder = new NotificationCompat.Builder(this.mContext);
		this.mBuilder.setContentTitle(this.mFileName);
		//this.mBuilder.setContentText("Test");
		this.mBuilder.setSmallIcon(R.drawable.loc);
		this.mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(this.mContext.getResources().getString(R.string.not_bigtext)));
        PendingIntent contentIntent = PendingIntent.getActivity(this.mContext, 0, new Intent(), 0);
        this.mBuilder.setContentIntent(contentIntent);
        this.mBuilder.setOngoing(true);
		notMan.notify(this.mUniqId, this.mBuilder.build());
	}
	
	public void updateNotification(float progress) {
		NotificationManager notMan = (NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		this.mBuilder.setProgress(MAX_PROGRESS, (int) progress, false);
		notMan.notify(this.mUniqId, this.mBuilder.build());
	}
	
	public void setCompleteNotification() {
		NotificationManager notMan = (NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		this.mBuilder.setProgress(0, 0, false);
		this.mBuilder.setContentText(this.mContext.getResources().getString(R.string.not_compl));
		this.mBuilder.setOngoing(false);
		notMan.notify(this.mUniqId, this.mBuilder.build());
	}
	
	public void setErrorNotification() {
		NotificationManager notMan = (NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		this.mBuilder.setProgress(0, 0, false);
		this.mBuilder.setContentText(this.mContext.getResources().getString(R.string.not_err));
		this.mBuilder.setOngoing(false);
		notMan.notify(this.mUniqId, this.mBuilder.build());
	}
	
	private void init(String filePath) {
		this.mUniqId = this.generateIdFromPath(filePath);
		File tempFile = new File(filePath);
		this.mFileName = tempFile.getName();
	}
	
	private int generateIdFromPath(String path) {
		return path.hashCode();
	}
}
