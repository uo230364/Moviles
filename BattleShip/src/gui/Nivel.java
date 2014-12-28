package gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class Nivel extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nivel);
	}
	
	public void pararMusica(View view){
		Inicial.paraReproduceMusica(view);
	}
}
