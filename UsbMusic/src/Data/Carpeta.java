package Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Carpeta {

	private File path = null;
	private String name = "";
	private List<Cancion> canciones = null;
	private int lenght = 0;
	private long size = 0;
	/**
	 * @param path
	 * @param nombre
	 * @param canciones
	 * @param lenght
	 */
	public Carpeta(String nombre) {
		super();
		this.name = nombre;
		this.canciones = new ArrayList<Cancion>();
	}
	
	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int calcularDuracion(){
		for(Cancion next: canciones){
			this.lenght += next.getLenght();
		}
		return this.lenght;
	}
	public long calcularSize(){
		for(Cancion next: canciones){
			this.size+=next.getSize();
		}
		return this.size;
	}
	public void addCancion(Cancion cancion){
		canciones.add(cancion);
	}
	public void removeCancion(String name){
		for(Cancion next: canciones){
			if(next.getName().equals(name)){
				canciones.remove(next);
			}
		}
	}
}
