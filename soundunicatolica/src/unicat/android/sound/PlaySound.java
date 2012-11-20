package unicat.android.sound;

import android.os.Bundle;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.app.Activity;
import android.view.Menu;

public class PlaySound extends Activity implements OnTouchListener {
	private SoundPool soundPool;
	private int soundID;
	boolean loaded = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        View view = findViewById(R.id.textView1);
        view.setOnTouchListener(this);
     // Establece el hardware y los controles de reproduccion
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
        @Override
        public void onLoadComplete(SoundPool soundPool, int sampleId,
        int status) {
        loaded = true;
        }
        });
        soundID = soundPool.load(this, R.raw.sound1, 1);
        
        setContentView(R.layout.main);
    }
    
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
    // obtiene las caracteristicas de audio del usuario
    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    float actualVolume = (float) audioManager
    .getStreamVolume(AudioManager.STREAM_MUSIC);
    float maxVolume = (float) audioManager
    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    float volume = actualVolume / maxVolume;
    // Esta disponible el sonido cargado? Receurde que la carga es asincronica
    if (loaded) {
    soundPool.play(soundID, volume, volume, 1, 0, 1f);
    Log.e("Test", "sonido OK");
    }
    }
    return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
