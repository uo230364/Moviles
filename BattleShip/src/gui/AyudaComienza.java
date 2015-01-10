package gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class AyudaComienza extends Activity {

	private final static int PRIMERA_AYUDA = R.layout.activity_ayuda;
	private final static int SEGUNDA_AYUDA = R.layout.activity_segunda_ayuda;
	private final static int TERCERA_AYUDA = R.layout.activity_tercera_ayuda;
	private final static int CUARTA_AYUDA = R.layout.activity_cuarta_ayuda;
	private final static int QUINTA_AYUDA = R.layout.activity_quinta_ayuda;
	private final static int SEXTA_AYUDA = R.layout.activity_sexta_ayuda;
	private final static int SEPTIMA_AYUDA= R.layout.activity_septima_ayuda;
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
		case QUINTA_AYUDA:
			setContentView(R.layout.activity_cuarta_ayuda);
			break;
		case SEXTA_AYUDA:
			setContentView(R.layout.activity_quinta_ayuda);
			break;
		case SEPTIMA_AYUDA:
			setContentView(R.layout.activity_sexta_ayuda);
			break;
		}
	}
		
	

	public void masAyuda(View view) {
		switch (currentLayout) {
		case PRIMERA_AYUDA:
			setContentView(R.layout.activity_segunda_ayuda);
			break;
		case SEGUNDA_AYUDA:
			setContentView(R.layout.activity_tercera_ayuda);
			break;
		case TERCERA_AYUDA:
			setContentView(R.layout.activity_cuarta_ayuda);
			break;
		case CUARTA_AYUDA:
			setContentView(R.layout.activity_quinta_ayuda);
			break;
		case QUINTA_AYUDA:
			setContentView(R.layout.activity_sexta_ayuda);
			break;
		case SEXTA_AYUDA:
			setContentView(R.layout.activity_septima_ayuda);
			break;
		case SEPTIMA_AYUDA:
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
