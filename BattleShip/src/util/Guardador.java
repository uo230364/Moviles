package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import logica.modelo.Partida;
import android.content.Context;

public class Guardador {

	private final static String NOMBRE_PARTIDA = "partidaGuardada";

	public static void guardar(Context context, Partida partida) {
		FileOutputStream fos;
		try {
			fos = context.openFileOutput(NOMBRE_PARTIDA, Context.MODE_PRIVATE);
			ObjectOutputStream os;
			os = new ObjectOutputStream(fos);
			os.writeObject(partida);
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Partida cargar(Context context) {
		FileInputStream fis;
		try {
			fis = context.openFileInput(NOMBRE_PARTIDA);
			ObjectInputStream is = new ObjectInputStream(fis);
			Partida partidaClass = (Partida) is.readObject();
			is.close();
			return partidaClass;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
