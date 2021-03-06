package gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.battleship.R;

public class Inicial extends Activity {
	
	private static MediaPlayer mediaPlayer;
	private static SharedPreferences preferences;
	private static Button musica;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        
	    mediaPlayer = MediaPlayer.create(this, R.raw.bensound);
	    mediaPlayer.setLooping(true);
	    mediaPlayer.setVolume(100, 100);
	    if(preferences.getBoolean("reproductor", false));
	    	mediaPlayer.start();
	    musica=(Button)findViewById(R.id.btMusica);
    }
    
    public void cargaAyuda (View view){
    	Intent mIntent=new Intent(Inicial.this,AyudaComienza.class);
    	startActivity(mIntent);  	
    }
    
    public void seleccionModo(View view){
    	Intent mIntent=new Intent(Inicial.this,Modo.class);
    	startActivity(mIntent);
    }
    
    public static void paraReproduceMusica (View view){
    	
    	Editor editor = preferences.edit();
    	
    	if(mediaPlayer.isPlaying()){
    		mediaPlayer.pause();
    		editor.putBoolean("reproductor", false);
    		view.setBackgroundResource(R.drawable.botonmusicaquitado);
    		musica.setBackgroundResource(R.drawable.botonmusicaquitado);
    		
    	} else {
    		mediaPlayer.start();
    		editor.putBoolean("reproductor", true);
    		view.setBackgroundResource(R.drawable.botonmusica);
    		musica.setBackgroundResource(R.drawable.botonmusica);
    	}
    	editor.commit();
    	
    }

	@Override
    public void onBackPressed() {
		mostrarDialogoSalir("�Estas seguro de que quieres salir?");
//    	super.onBackPressed();
    }
    
    public static void pararReproductor(){
    	mediaPlayer.stop();
    }
    
    public static MediaPlayer obtenerReproductor(){
    	return mediaPlayer;
    }
    public void salirDeAplicacion(View view){
    	mostrarDialogoSalir("�Estas seguro de que quieres salir?");
    }
    public void cargaInfo(View view){
    	Intent mIntent=new Intent(Inicial.this,InfoApplication.class);
    	startActivity(mIntent);  
    	
    }
    
    private void mostrarDialogoSalir(String mensajeAMostrar) {
		new AlertDialog.Builder(this)
				.setTitle("Salir")
				.setMessage(mensajeAMostrar)
				.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						pararReproductor();
						finish();
					}
				})
				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								//Vacio para que solo se cierre el dialogo sin hacer nada mas
							}
						}).show();
	}
}
