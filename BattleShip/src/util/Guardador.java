package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import logica.modelo.Partida;
import android.content.Context;
import android.widget.Toast;

public class Guardador {

	public static void guardar(Context context, Partida partida, String nombre) {
		FileOutputStream fos;
		ObjectOutputStream os;
		try {
			fos = context.openFileOutput(nombre, Context.MODE_PRIVATE);
			os = new ObjectOutputStream(fos);
			os.writeObject(partida);
			os.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Partida cargar(Context context, String nombre) {
		FileInputStream fis;
		Partida partidaClass = null;
		try {
			fis = context.openFileInput(nombre);
			ObjectInputStream is = new ObjectInputStream(fis);
			partidaClass = (Partida) is.readObject();
			is.close();	
			fis.close();
		} catch (Exception e) {
			Toast.makeText(context, "No hay ninguna partida guardada", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return partidaClass;
	}
}
