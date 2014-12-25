package gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class SegundaAyuda extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_segunda_ayuda);
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
