package util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {

	private Context pContext;
	private SoundPool sndPool;
	private float rate=1.0f;
	private float leftVolume=0.5f;
	private float rightVolume=0.5f;
	
	public SoundManager(Context appContext){
		sndPool=new SoundPool(16,AudioManager.STREAM_MUSIC,100);
		pContext=appContext;
	}
	
	public int load(int idSonido){
		return sndPool.load(pContext,idSonido,1);
	}
	
	public void play(int sound){
		sndPool.play(sound, leftVolume, rightVolume, 1, 0, rate);
	}
}
