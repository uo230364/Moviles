package gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class AyudaComienza extends Activity {

	private final static int PRIMERA_AYUDA = R.layout.activity_ayuda;
	private final static int SEGUNDA_AYUDA = R.layout.activity_segunda_ayuda;
	private final static int TERCERA_AYUDA = R.layout.activity_tercera_ayuda;
	private final static int CUARTA_AYUDA = R.layout.activity_ultima_ayuda;
	private static int currentLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ayuda);
	}

	public void atrasAyuda(View view) {
		switch (currentLayout) {
		case PRIMERA_AYUDA:
			finish();
			break;
		case SEGUNDA_AYUDA:
			setContentView(R.layout.activity_ayuda);
			break;
		case TERCERA_AYUDA:
			setContentView(R.layout.activity_segunda_ayuda);
			break;
		case CUARTA_AYUDA:
			setContentView(R.layout.activity_tercera_ayuda);
			break;
		}
	}

	public void masAyuda(View view) {
		// Intent mInten=new Intent(AyudaComienza.this,SegundaAyuda.class);
		// startActivity(mInten);
		// finish();
		switch (currentLayout) {
		case PRIMERA_AYUDA:
			setContentView(R.layout.activity_segunda_ayuda);
			break;
		case SEGUNDA_AYUDA:
			setContentView(R.layout.activity_tercera_ayuda);
			break;
		case TERCERA_AYUDA:
			setContentView(R.layout.activity_ultima_ayuda);
			break;
		case CUARTA_AYUDA:
			setContentView(R.layout.activity_ayuda);
			break;
		}

	}

	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}

	@Override
	public void setContentView(int layoutResID) {
		currentLayout = layoutResID;
		super.setContentView(layoutResID);
	}

}
