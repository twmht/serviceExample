package com.example.serviceexample;

import com.example.serviceexample.Services.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class Main extends Activity{
    /** Called when the activity is first created. */
	String TAG="Main";
	private Services mService = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void startService(View view){
        startService(new Intent(this,Services.class));
    }
    public void stopService(View view){
        stopService(new Intent(this,Services.class));
    }
    public void bindService(View view){
    	Intent intent = new Intent(Main.this,Services.class);
    	this.bindService(intent, mServConn, BIND_ABOVE_CLIENT);	
    }
    public void unbindService(View view){
          mService = null;
          this.unbindService(mServConn);
    }
    private ServiceConnection mServConn = new ServiceConnection() {
    	  // bind Service過程中,系統會呼叫執行以下程式碼
    	  @Override
    	  public void onServiceConnected(ComponentName name, IBinder service) {

    		  Log.i(TAG,"onServiceConnected");
    	   mService = ((LocalBinder) service).getService();
    	  }

    	  @Override
    	  public void onServiceDisconnected(ComponentName name) {
              mService = null;
    	  }

    	 };
    public void callBindMethod(View view){
    	mService.doSomeThing();
    }
      public void callIntentService(View view){
		Log.d(TAG,"callIntentService");
        startService(new Intent(this,HelloIntentService.class));
    }
    
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mService!=null){
                this.unbindService(mServConn);
        }
    } 
    public void startPlayer(View v) {
        Intent i=new Intent(this, PlayerService.class);
        
        i.putExtra(PlayerService.EXTRA_PLAYLIST, "main");
        i.putExtra(PlayerService.EXTRA_SHUFFLE, true);
        
        startService(i);
      }
      
      public void stopPlayer(View v) {
        stopService(new Intent(this, PlayerService.class));
      }
}
