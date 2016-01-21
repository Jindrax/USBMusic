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
	private int id = 0;
	/**
	 * @param path
	 * @param nombre
	 * @param canciones
	 * @param lenght
	 */
	public Carpeta(int id, File path) {
		super();
		this.id = id;
		this.path = path;
		this.name = path.getName();
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	public void addCancion(int id, File path){
		Cancion cancion = new Cancion(id, path);
		canciones.add(cancion);
	}
	public Cancion buscarCancion(int id){
		for(Cancion next: canciones){
			if(next.getId()==id){
				return next;
			}
		}
		return null;
	}
	public void removeCancion(int id){
		Cancion cancion = buscarCancion(id);
		cancion.getPath().delete();
		canciones.remove(cancion);
	}
	
}
