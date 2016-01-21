package Persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Data.Biblioteca;

public class Archivador {

	public static void guardarBiblioteca(Biblioteca biblio){
		FileOutputStream archivo = null;
		ObjectOutputStream oOStream = null;
		try {
			File file = new File("biblioteca.dat");
			archivo = new FileOutputStream(file);
			oOStream = new ObjectOutputStream(archivo);
			oOStream.writeObject(biblio);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				oOStream.close();
				archivo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Biblioteca cargarBiblioteca(){
		File ruta = null;
		FileInputStream archivo = null;
		ObjectInputStream oIStream = null;
		Biblioteca cargado = null;
		try {
			ruta = new File("biblioteca.dat");			
			if (ruta.exists()) {
				try {
					archivo = new FileInputStream(ruta);
					oIStream = new ObjectInputStream(archivo);
					cargado = (Biblioteca) oIStream.readObject();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 
			}else{
				ruta.createNewFile();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cargado;
	}
	
	public static boolean biblioExists(){
		File biblio = new File("biblioteca.dat");
		return biblio.exists();
	}
	
}
