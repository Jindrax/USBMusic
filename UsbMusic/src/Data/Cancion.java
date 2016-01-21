package Data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class Cancion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8141944073647001007L;
	private int id = 0;
	private File path = null;
	private String name = "";
	private String author = "";
	private String genre = "";
	private int lenght = 0;
	private long size = 0;

	/**
	 * @param path
	 * @param name
	 * @param author
	 * @param genre
	 */
	public Cancion(int id, File path, String name, String author, String genre, int lenght) {
		super();
		this.id = id;
		this.path = path;
		this.name = name;
		this.author = author;
		this.genre = genre;
		this.lenght = lenght;
	}
	
	public Cancion(int id, File path){
		try {
			this.id = id;
			this.path = path;
			AudioFile aF = AudioFileIO.read(path);
			Tag tag = aF.getTag();
			AudioHeader aH = aF.getAudioHeader();
			if (tag!=null) {
				String titulo = tag.getFirst(FieldKey.TITLE);
				String artista = tag.getFirst(FieldKey.ARTIST);
				String genero = tag.getFirst(FieldKey.GENRE);					
				if (!titulo.equals("")) {
					this.name=titulo;
					this.author=artista;
					this.genre=genero;
					this.lenght=aH.getTrackLength();
				} else {
					this.name=path.getName().substring(0, path.getName().length() - 4);
					this.author=artista;
					this.genre=genero;
					this.lenght=aH.getTrackLength();
				}
			}else{
				this.name=path.getName().substring(0, path.getName().length() - 4);
				this.author="";
				this.genre="";
				this.lenght=aH.getTrackLength();
			}
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}

	public File getPath() {
		return path;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
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
	public void removeCancion(){
		this.path.delete();
	}
}
