package gui;

import com.example.battleship.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class SegundaAyuda extends Activity {
	
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_segunda_ayuda);
		/*Bundle extras=getIntent().getExtras();
		mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.bensound);
		mediaPlayer.seekTo(extras.getInt("posActual"));
		mediaPlayer.start();*/
	}
	
	public void primeraAyuda(View view){
		Intent mIntent=new Intent(this, AyudaComienza.class);
		startActivity(mIntent);
		finish();
	}
	
	public void terceraAyuda (View view){
		Intent mIntent=new Intent(this, TerceraAyuda.class);
		startActivity(mIntent);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		Intent mIntent=new Intent(this, AyudaComienza.class);
		startActivity(mIntent);
		finish();
		super.onBackPressed();
	}
	
	}
