package com.example.battleship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AyudaComienza extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ayuda);		
	}
	
	public void pantallaInicial(View view){
		Intent mIntent=new Intent(AyudaComienza.this,Inicial.class);
    	startActivity(mIntent);
	}
	
	public void masAyuda(View view){
		Intent mInten=new Intent(AyudaComienza.this,SegundaAyuda.class);
		startActivity(mInten);
	}
}
