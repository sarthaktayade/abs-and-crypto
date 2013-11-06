/**
 * 
 */
package com.abstrucelogic.crypto.notificatoin;

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
	
	public CryptoNotification(Context context) {
		this.mContext = context;
	}
	
	public void showNotification() {
		NotificationManager notMan = (NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		this.mBuilder = new NotificationCompat.Builder(this.mContext);
		this.mBuilder.setContentTitle("Test");
		this.mBuilder.setContentText("Test");
		this.mBuilder.setSmallIcon(R.drawable.loc);
		this.mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Please be patient!"));
        PendingIntent contentIntent = PendingIntent.getActivity(this.mContext, 0, new Intent(), 0);
        this.mBuilder.setContentIntent(contentIntent);
		notMan.notify(0, this.mBuilder.build());
	}
	
	public void updateNotification(float progress) {
		NotificationManager notMan = (NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		this.mBuilder.setProgress(MAX_PROGRESS, (int) progress, false);
		notMan.notify(0, this.mBuilder.build());
	}	
}
