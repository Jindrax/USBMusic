package Presentacion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Data.Biblioteca;
import Data.Cancion;
import jaco.mp3.player.MP3Player;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.Component;

public class Window {

	private JFrame frmUsbmusic;
	private JComboBox<String> comboDisks;
	private JTextField searchBox;
	private JTable searchTable;
	private JTable folderTable;
	private JTable songTable;
	private Biblioteca biblio;
	private MP3Player player = new MP3Player();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Window window = new Window();
					window.frmUsbmusic.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUsbmusic = new JFrame();
		frmUsbmusic.setTitle("USBMusic");
		frmUsbmusic.setResizable(false);
		frmUsbmusic.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {				
				cargarDiscos();
				inicializarDB();
			}
		});
		
		frmUsbmusic.setBounds(0, 0, 1366, 721);
		frmUsbmusic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUsbmusic.getContentPane().setLayout(null);
		
		comboDisks = new JComboBox<String>();
		comboDisks.setFont(new Font("Arial", Font.PLAIN, 20));
		comboDisks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarFolderTable();
			}
		});
		comboDisks.setBounds(742, 14, 608, 30);
		frmUsbmusic.getContentPane().add(comboDisks);
		
		searchBox = new JTextField();
		searchBox.setFont(new Font("Arial", Font.PLAIN, 20));
		searchBox.setHorizontalAlignment(SwingConstants.RIGHT);
		searchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String busqueda = searchBox.getText().trim().toLowerCase();
				if(busqueda.equals("")){
					cargarSearchTable(biblio.getCanciones());
				}else{
					cargarSearchTable(busqueda(busqueda));
				}
			}
		});
		searchBox.setBounds(190, 11, 392, 36);
		frmUsbmusic.getContentPane().add(searchBox);
		searchBox.setColumns(10);
		
		JLabel lblSearch = new JLabel("Buscar:");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSearch.setAlignmentY(Component.TOP_ALIGNMENT);
		lblSearch.setFont(new Font("Arial", Font.PLAIN, 20));
		lblSearch.setLabelFor(searchBox);
		lblSearch.setBounds(113, 11, 67, 36);
		frmUsbmusic.getContentPane().add(lblSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 710, 605);
		frmUsbmusic.getContentPane().add(scrollPane);
		
		searchTable = new JTable();
		searchTable.setFont(new Font("Arial", Font.PLAIN, 20));
		searchTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Titulo", "Artista", "Genero"
			}
		));
		searchTable.getColumnModel().getColumn(1).setPreferredWidth(20);
		searchTable.getColumnModel().getColumn(1).setPreferredWidth(307);
		searchTable.getColumnModel().getColumn(2).setPreferredWidth(154);
		searchTable.getColumnModel().getColumn(3).setPreferredWidth(132);
		searchTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
								
			}
		});
		searchTable.setRowHeight(30);
		scrollPane.setViewportView(searchTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(742, 55, 608, 184);
		frmUsbmusic.getContentPane().add(scrollPane_1);
		
		folderTable = new JTable();
		folderTable.setFont(new Font("Arial", Font.PLAIN, 20));
		folderTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Carpeta"
			}
		));
		folderTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				folderSelected();
			}
		});
		folderTable.setRowHeight(30);
		scrollPane_1.setViewportView(folderTable);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(742, 280, 608, 380);
		frmUsbmusic.getContentPane().add(scrollPane_2);
		
		songTable = new JTable();
		songTable.setFont(new Font("Arial", Font.PLAIN, 20));
		songTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Archivo"
			}
		));
		songTable.setRowHeight(30);
		scrollPane_2.setViewportView(songTable);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nuevaCarpeta = JOptionPane.showInputDialog("Nombre de la nueva carpeta: ");
				String folderS = (String)(comboDisks.getSelectedItem());
				File folder = new File(folderS+nuevaCarpeta+"\\");
				folder.mkdir();
				cargarFolderTable();
			}
		});
		button.setIcon(new ImageIcon(Window.class.getResource("/Recursos/1453260062_Plus.png")));
		button.setBounds(742, 241, 36, 36);
		frmUsbmusic.getContentPane().add(button);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Copier().execute();
			}
		});
		button_1.setIcon(new ImageIcon(Window.class.getResource("/Recursos/1453260916_arrow-right.png")));
		button_1.setBounds(786, 241, 36, 36);
		frmUsbmusic.getContentPane().add(button_1);
		
		JButton btnPlay = new JButton("");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cancion cancion = biblio.buscarCancion(Integer.parseInt((String) searchTable.getValueAt(searchTable.getSelectedRow(), 0)));
				player.stop();
				player = new MP3Player();
				player.addToPlayList(cancion.getPath());
				player.play();
			}
		});
		btnPlay.setIcon(new ImageIcon(Window.class.getResource("/Recursos/player6.png")));
		btnPlay.setBounds(638, 11, 36, 36);
		btnPlay.setOpaque(false);
		btnPlay.setContentAreaFilled(false);
		btnPlay.setBorderPainted(false);
		frmUsbmusic.getContentPane().add(btnPlay);
		
		JButton btnPause = new JButton("");
		btnPause.setBorderPainted(false);
		btnPause.setBorder(null);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player.pause();
			}
		});
		btnPause.setIcon(new ImageIcon(Window.class.getResource("/Recursos/rounded57.png")));
		btnPause.setBounds(592, 11, 36, 36);
		btnPause.setOpaque(false);
		btnPause.setContentAreaFilled(false);
		btnPause.setBorderPainted(false);
		frmUsbmusic.getContentPane().add(btnPause);
		
		JButton btnStop = new JButton("");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player.stop();
			}
		});
		btnStop.setIcon(new ImageIcon(Window.class.getResource("/Recursos/multimedia option161.png")));
		btnStop.setBounds(684, 11, 36, 36);
		btnStop.setOpaque(false);
		btnStop.setContentAreaFilled(false);
		btnStop.setBorderPainted(false);
		frmUsbmusic.getContentPane().add(btnStop);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(730, 11, 2, 649);
		frmUsbmusic.getContentPane().add(separator);
		
		JButton btnRename = new JButton("");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnRename.setIcon(new ImageIcon(Window.class.getResource("/Recursos/favorite5.png")));
		btnRename.setOpaque(false);
		btnRename.setContentAreaFilled(false);
		btnRename.setBorderPainted(false);
		btnRename.setBorder(null);
		btnRename.setBounds(10, 11, 36, 36);
		frmUsbmusic.getContentPane().add(btnRename);
		
		JMenuBar menuBar = new JMenuBar();
		frmUsbmusic.setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmConfiguracion = new JMenuItem("Configuracion");
		mnArchivo.add(mntmConfiguracion);
	}
	protected void copySong() {
		Cancion cancion = biblio.buscarCancion(Integer.parseInt((String) searchTable.getValueAt(searchTable.getSelectedRow(), 0)));
		File copia = new File(comboDisks.getSelectedItem().toString()+(String)folderTable.getValueAt(folderTable.getSelectedRow(), 0)+"\\"+cancion.getPath().getName());
		try {
			Files.copy(cancion.getPath().toPath(), copia.toPath(),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	protected void folderSelected() {
		if (folderTable.getSelectedRow()!=-1) {
			String folderS = (String) comboDisks.getSelectedItem();
			folderS = folderS + folderTable.getValueAt(folderTable.getSelectedRow(), 0) + "\\";
			File folder = new File(folderS);
			File[] files = folder.listFiles();
			DefaultTableModel model = (DefaultTableModel) songTable.getModel();
			model.setRowCount(0);
			if (files != null) {
				for (File next : files) {
					Vector<String> row = new Vector<String>();
					row.add(next.getName());
					model.addRow(row);
				}
			} 
		}
	}

	protected void cargarFolderTable() {
		DefaultTableModel modelSong=(DefaultTableModel) songTable.getModel();
		modelSong.setRowCount(0);
		File disk = new File((String)(comboDisks.getSelectedItem()));
		File[] files = disk.listFiles();
		DefaultTableModel model = (DefaultTableModel) folderTable.getModel();
		model.setRowCount(0);
		for(File next: files){
			if(next.isDirectory()){
				Vector<String> row = new Vector<String>();
				row.add(next.getName());
				model.addRow(row);
			}
		}
	}

	protected void cargarDiscos() {
		//File[] disks = File.listRoots();
		List<File> driveLetter = new ArrayList<File>();
		FileSystemView fsv = FileSystemView.getFileSystemView();

		File[] f = File.listRoots();
		for (int i = 0; i < f.length; i++) {
			File drive = f[i];
			String type = fsv.getSystemTypeDescription(f[i]);
			if (type.equals("Unidad de USB") ) {
				driveLetter.add(drive);
			}						
		}
		if (driveLetter.size()>0) {
			comboDisks.removeAllItems();
			for (File next : driveLetter) {
				comboDisks.addItem(next.getAbsolutePath());
			} 
		}else{
			JOptionPane.showMessageDialog(null, "Ninguna USB detectada.");
		}
	}	
	
	private void cargarSearchTable(List<Cancion> canciones){
		DefaultTableModel model = (DefaultTableModel) searchTable.getModel();
		model.setRowCount(0);
		for(Cancion next: canciones){
			Vector<String> row = new Vector<String>();
			row.add(String.valueOf(next.getId()));
			row.add(next.getName());
			row.add(next.getAuthor());
			row.add(next.getGenre());
			model.addRow(row);
		}
	}
	
	protected void inicializarDB(){
		biblio = new Biblioteca(new File("Archivos\\"));
		List<Cancion> canciones = biblio.cargar();
		cargarSearchTable(canciones);
	}
	
	protected List<Cancion> busqueda(String busqueda){
		List<Cancion> compendio = new ArrayList<Cancion>();
		for(Cancion next: biblio.getCanciones()){
			if(next.getName().toLowerCase().contains(busqueda)||next.getAuthor().toLowerCase().contains(busqueda)||next.getGenre().toLowerCase().contains(busqueda)){
				compendio.add(next);
			}
		}
		return compendio;
	}
		
	public JComboBox<String> getComboDisks() {
		return comboDisks;
	}
	public JTable getSearchTable() {
		return searchTable;
	}
	public JTable getFolderTable() {
		return folderTable;
	}
	public JTable getSongTable() {
		return songTable;
	}
	class Copier extends SwingWorker<String, Object> {
        @Override
       public String doInBackground() {
           copySong();
           return "Finalizado";
       }

        @Override
       protected void done() {
        	folderSelected();
       }
   }
}
