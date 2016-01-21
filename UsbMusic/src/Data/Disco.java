package Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Disco {

	private File path = null;
	private String name = "";
	private List<Carpeta> carpetas = null;
	private int length = 0;
	private long size = 0;
	/**
	 * @param path
	 * @param name
	 */
	
	public File getPath() {
		return path;
	}
	/**
	 * @param path
	 * @param name
	 */
	public Disco(File path) {
		super();
		this.path = path;
		this.name = path.getName();
		this.carpetas = new ArrayList<Carpeta>();
	}
	public void setPath(File path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int calcularDuracion(){
		for(Carpeta next: carpetas){
			this.length += next.calcularDuracion();
		}
		return this.length;
	}
	public long calcularSize(){
		for(Carpeta next: carpetas){
			this.size += next.calcularSize();
		}
		return this.size;
	}
	public Carpeta addCarpeta(int id, File path){
		Carpeta carpeta = new Carpeta(id, path);
		carpetas.add(carpeta);
		return carpeta;
	}
	public Carpeta buscarCarpeta(int id){
		for(Carpeta next: carpetas){
			if(next.getId()==id){
				return next;
			}
		}
		return null;
	}
	public void removeCarpeta(int id){
		Carpeta carpeta = buscarCarpeta(id);
		for(Cancion next: carpeta.getCanciones()){
			next.removeCancion();
		}
		carpeta.getPath().delete();
		carpetas.remove(carpeta);
	}
	public void localizar(File path){
		setPath(path);
		for(Carpeta next: carpetas){
			File nextPath = new File(path.getAbsolutePath()+next.getName()+"\\");
			next.setPath(nextPath);
		}
	}
	public List<Carpeta> getCarpetas() {
		return carpetas;
	}
	public void setCarpetas(List<Carpeta> carpetas) {
		this.carpetas = carpetas;
	}
		
}
