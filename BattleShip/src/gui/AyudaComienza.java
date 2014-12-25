package gui;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.battleship.R;

public class AyudaComienza extends Activity {
	
	MediaPlayer mediaPlayer = new MediaPlayer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ayuda);
		/*Bundle extras=getIntent().getExtras();
		mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.bensound);
		mediaPlayer.seekTo(extras.getInt("posActual"));
		//mediaPlayer.start();*/
	}
	
	public void pantallaInicial(View view){
    	Intent mIntent=new Intent(this,Inicial.class);
    	startActivity(mIntent);
	}
	
	public void masAyuda(View view){
		Intent mInten=new Intent(AyudaComienza.this,SegundaAyuda.class);
		mInten.putExtra("posActual", mediaPlayer.getCurrentPosition());
		startActivity(mInten);
		finish();
	}
	
	@Override
    public void onDestroy(){
    	super.onDestroy();
    	mediaPlayer.pause();
    }
    
    @Override
    public void onBackPressed() {
    	mediaPlayer.stop();
    	super.onBackPressed();
    }
}
