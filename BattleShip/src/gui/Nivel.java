package gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class Nivel extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nivel);
		
		if (!Inicial.obtenerReproductor().isPlaying())
			findViewById(R.id.btMusicaNivel).setBackgroundResource(R.drawable.botonmusicaquitado);
	}
	
	public void pararMusica(View view){
		Inicial.paraReproduceMusica(view);
	}
	
	public void colocaBarcos(View view){
		int nivel=view.getId();
		Intent mIntent=new Intent();
		switch(nivel){
			case R.id.btFacil:
				mIntent=new Intent(Nivel.this,JuegoFacil.class);
				break;
			case R.id.btMedio:
				mIntent=new Intent(Nivel.this,JuegoMedio.class);
				break;
			case R.id.btDificil:
				mIntent=new Intent(Nivel.this,JuegoDificil.class);
				break;
		}
		startActivity(mIntent);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}
}
