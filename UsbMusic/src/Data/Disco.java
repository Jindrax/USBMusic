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
	public Disco(String name) {
		super();
		this.name = name;
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
	public void addCarpeta(Carpeta carpeta){
		carpetas.add(carpeta);
	}
	public void removeCarpeta(String name){
		for(Carpeta next: carpetas){
			if(next.getName().equals(name)){
				carpetas.remove(next);
			}
		}
	}
	public void localizar(File path){
		setPath(path);
		for(Carpeta next: carpetas){
			File nextPath = new File(path.getAbsolutePath()+next.getName()+"\\");
			next.setPath(nextPath);
		}
	}
	
}
