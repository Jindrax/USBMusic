package Data;

import java.io.File;

public class Cancion {
	
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
	
}
