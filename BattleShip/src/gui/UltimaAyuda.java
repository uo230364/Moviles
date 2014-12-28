package gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class UltimaAyuda extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ultima_ayuda);
	}
	
	public void terceraAyuda(View view){
		Intent mIntent=new Intent(this,TerceraAyuda.class);
		startActivity(mIntent);
		finish();
	}
	
	public void vuelveMenu(View view){
		finish();
	}
	
	@Override
	public void onBackPressed() {
		Intent mIntent=new Intent(this, TerceraAyuda.class);
		startActivity(mIntent);
		finish();
		super.onBackPressed();
	}
}
