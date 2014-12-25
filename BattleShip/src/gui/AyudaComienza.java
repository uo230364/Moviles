package gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class AyudaComienza extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ayuda);
	}
	
	public void pantallaInicial(View view){
    	finish();
	}
	
	public void masAyuda(View view){
		Intent mInten=new Intent(AyudaComienza.this,SegundaAyuda.class);
		startActivity(mInten);
		finish();
	}
	
    @Override
    public void onBackPressed() {
    	finish();
    	super.onBackPressed();
    }
}
