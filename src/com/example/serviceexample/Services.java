package com.example.serviceexample;

import javax.net.ssl.SSLException;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

public class Services extends Service{
	String TAG="TestService";
	public class LocalBinder extends Binder{
		Services getService(){
			return Services.this;
		}
	}
	private LocalBinder myBinder=new LocalBinder();
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG,"onBind");
		return myBinder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG,"onStartCommand"+Thread.currentThread());
	
		new Thread(new Runnable(){
			public void run() {
				// TODO Auto-generated method stub
				while(true)
				{
					try {
						Thread.sleep(50000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}


	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG,"onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG,"onDestroy");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG,"onUnbind");
		return super.onUnbind(intent);
	}
	@Override
	public void onRebind(Intent intent) {
		// A client is binding to the service with bindService(),
		// after onUnbind() has already been called
		Log.d(TAG,"onRebind");
	}

	public void doSomeThing(){
	}
}
