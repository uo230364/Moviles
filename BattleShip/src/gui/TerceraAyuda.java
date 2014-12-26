package gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class TerceraAyuda extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tercera_ayuda);
	}
	
	public void segundaAyuda(View view){
		Intent mIntent=new Intent(this,SegundaAyuda.class);
		startActivity(mIntent);
		finish();
	}
	
	public void ultimaAyuda(View view){
		Intent mIntent=new Intent(this,UltimaAyuda.class);
		startActivity(mIntent);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		Intent mIntent=new Intent(this,SegundaAyuda.class);
		startActivity(mIntent);
		finish();
		super.onBackPressed();
	}
}
