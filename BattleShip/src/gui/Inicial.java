package gui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class Inicial extends Activity {
	
	private static MediaPlayer mediaPlayer;

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
    
    public void seleccionNivel(View view){
    	Intent mIntent=new Intent(Inicial.this,Nivel.class);
    	startActivity(mIntent);
    }
    
    public static void paraReproduceMusica (View view){
    	
    	if(mediaPlayer.isPlaying()){
    		mediaPlayer.pause();
    	} else {
    		mediaPlayer.start();
    	}
    }

	@Override
    public void onBackPressed() {
    	pararReproductor();
    	finish();
    	super.onBackPressed();
    }
    
    public static void pararReproductor(){
    	mediaPlayer.stop();
    }
}
