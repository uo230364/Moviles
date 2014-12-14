package gui;

import com.example.battleship.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class Inicial extends Activity {
	
	private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        
        mediaPlayer = MediaPlayer.create(this, R.raw.bensound);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();
    }
    
    public void cargaAyuda (View view){
    	Intent mIntent=new Intent(Inicial.this,AyudaComienza.class);
    	startActivity(mIntent);
    }
    
    public void paraReproduceMusica (View view){
    	if(mediaPlayer.isPlaying())
    		mediaPlayer.pause();
    	else
    		mediaPlayer.start();
    }
}
