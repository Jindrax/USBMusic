package Data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class Biblioteca implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 330484211429418667L;
	private File path = null;
	List<Cancion> canciones = null;
	/**
	 * @param path
	 */
	public Biblioteca(File path) {
		super();
		this.path = path;
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
	
	private void recorrer(File folder, List<File> files){
		for(File next: folder.listFiles()){
			if(!next.isDirectory()){
				if (next.getName().endsWith(".mp3")) {
					files.add(next);
				}
			}else{
				recorrer(next,files);
			}
		}
	}
	
	public List<Cancion> cargar(){
		canciones = new ArrayList<Cancion>();
		List<File> files = new ArrayList<File>();
		recorrer(this.path, files);
		int i=0;
		for(File next: files){
			try {
				AudioFile aF = AudioFileIO.read(next);
				Tag tag = aF.getTag();
				AudioHeader aH = aF.getAudioHeader();
				Cancion cancion = null;
				if (tag!=null) {
					String titulo = tag.getFirst(FieldKey.TITLE);
					String artista = tag.getFirst(FieldKey.ARTIST);
					String genero = tag.getFirst(FieldKey.GENRE);					
					if (!titulo.equals("")) {
						cancion = new Cancion(i,next, titulo, artista, genero, aH.getTrackLength());
						i++;
					} else {
						cancion = new Cancion(i,next, next.getName().substring(0, next.getName().length() - 4), artista, genero, aH.getTrackLength());
						i++;
					}
				}else{
					cancion = new Cancion(i,next, next.getName().substring(0, next.getName().length() - 4), "", "", aH.getTrackLength());
					i++;
				}
				canciones.add(cancion);
			} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
					| InvalidAudioFrameException e) {
				e.printStackTrace();
			}
		}
		return this.canciones;
	}
	public Cancion buscarCancion(String name){
		for(Cancion next: canciones){
			if(next.getName().equals(name)||next.getPath().getName().equals(name)){
				return next;
			}
		}
		return null;
	}
	public Cancion buscarCancion(int id){
		for(Cancion next: canciones){
			if(next.getId()==id){
				return next;
			}
		}
		return null;
	}
	public List<Cancion> renombrar(){
		canciones = new ArrayList<Cancion>();
		List<File> files = new ArrayList<File>();
		recorrer(this.path, files);
		int i=0;
		@SuppressWarnings("unused")
		File file = new File("prueba.txt");
		for(File next: files){
			try {
				AudioFile aF = AudioFileIO.read(next);
				Tag tag = aF.getTag();
				AudioHeader aH = aF.getAudioHeader();
				Cancion cancion = null;
				if (tag!=null) {
					String titulo = tag.getFirst(FieldKey.TITLE);
					String artista = tag.getFirst(FieldKey.ARTIST);
					String genero = tag.getFirst(FieldKey.GENRE);					
					if (!titulo.equals("")) {
						cancion = new Cancion(i,next, titulo, artista, genero, aH.getTrackLength());
						i++;
					} else {
						cancion = new Cancion(i,next, next.getName().substring(0, next.getName().length() - 4), artista, genero, aH.getTrackLength());
						i++;
					}
				}else{
					cancion = new Cancion(i,next, next.getName().substring(0, next.getName().length() - 4), "", "", aH.getTrackLength());
					i++;
				}
				canciones.add(cancion);
			} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
					| InvalidAudioFrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.canciones;
	}
}
