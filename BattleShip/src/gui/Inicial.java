package gui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.battleship.R;

public class Inicial extends Activity {
	
	private MediaPlayer mediaPlayer;
	private int posActual=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        
        mediaPlayer = MediaPlayer.create(this, R.raw.bensound);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.seekTo(posActual);
       //mediaPlayer.start();
    }
    
    public void cargaAyuda (View view){
    	Intent mIntent=new Intent(Inicial.this,AyudaComienza.class);
    	posActual=mediaPlayer.getCurrentPosition();
    	mIntent.putExtra("posActual", posActual);
    	mediaPlayer.pause();
    	startActivity(mIntent);
    	
    }
    
    public void paraReproduceMusica (View view){
    	if(mediaPlayer.isPlaying())
    		mediaPlayer.pause();
    	else
    		mediaPlayer.start();
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	mediaPlayer.pause();
    }
    
    @Override
    public void onBackPressed() {
    	mediaPlayer.stop();
    	finish();
    	super.onBackPressed();
    }
    
   /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	boolean defaultAction = super.onKeyDown(keyCode, event);
    	if (keyCode == KeyEvent.KEYCODE_HOME){
    		Log.i("HOME","HOME");
    	}
    	  System.out.println(keyCode);
    	return defaultAction;
    }*/
}
