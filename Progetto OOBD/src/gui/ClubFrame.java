package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import entity.Club;
import exception.DuplicatoException;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClubFrame extends JFrame {
	private Controller controller;
	private JPanel contentPane;
	private JTable table;
	private JTextField nomeTF;
	private JTextField cittaTF;
	private JComboBox<String> ordinaComboBox;

	public ClubFrame(Controller controller) {
		setResizable(false);
		setTitle("ClubFrame");
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel clubLabel = new JLabel("Club");
		clubLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		clubLabel.setBounds(232, 21, 73, 13);
		contentPane.add(clubLabel);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClubFrame.this.controller.apriHomeFrame(ClubFrame.this);
			}
		});
		indietroButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		indietroButton.setBounds(434, 318, 93, 19);
		contentPane.add(indietroButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 517, 131);
		this.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("SansSerif", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
				}
			)
					{
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
					}
			);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		//GESTIONE DEI VALORI DELLA RIGA CLICCATA
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setFields();
			}
		});
		
		scrollPane.setViewportView(table);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeLabel.setBounds(10, 199, 93, 19);
		contentPane.add(nomeLabel);
		
		JLabel cittaLabel = new JLabel("Città");
		cittaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cittaLabel.setBounds(10, 226, 93, 19);
		contentPane.add(cittaLabel);
		
		nomeTF = new JTextField();
		nomeTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeTF.setBounds(113, 199, 96, 19);
		contentPane.add(nomeTF);
		nomeTF.setColumns(10);
		
		cittaTF = new JTextField();
		cittaTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cittaTF.setBounds(113, 226, 96, 20);
		contentPane.add(cittaTF);
		cittaTF.setColumns(10);
		
		JButton inserisciButton = new JButton("Inserisci");
		
		//GESTIONE DELL'INSERIMENTO DELLE RIGHE
		inserisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nomeTF.getText().length()>0 && cittaTF.getText().length()>0) {
					controller.inserisciClub();
					ricaricaClub();
				}
			}
		});
		
		inserisciButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		inserisciButton.setBounds(243, 199, 93, 19);
		contentPane.add(inserisciButton);
		
		//GESTIONE DELLA RIMOZIONE DELLE RIGHE
		JButton rimuoviButton = new JButton("Rimuovi");
		rimuoviButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.rimuoviClub();
				ricaricaClub();
			}
		});
		rimuoviButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rimuoviButton.setBounds(243, 226, 93, 19);
		contentPane.add(rimuoviButton);
		
		//GESTIONE DELLA MODIFICA DELLE RIGHE
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1 && nomeTF.getText().length()>0 && cittaTF.getText().length()>0) {
					controller.modificaClub();
					ricaricaClub();
				}
			}
		});
		modificaButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		modificaButton.setBounds(243, 255, 93, 19);
		contentPane.add(modificaButton);
		
		JLabel ordinaLabel = new JLabel("Ordina per");
		ordinaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		ordinaLabel.setBounds(243, 284, 93, 19);
		contentPane.add(ordinaLabel);
		
		ordinaComboBox = new JComboBox<String>();
		ordinaComboBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
		ordinaComboBox.setBounds(243, 306, 93, 19);
		ordinaComboBox.addItem("Nome");
		ordinaComboBox.addItem("Citta");
		contentPane.add(ordinaComboBox);
		ordinaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ricaricaClub();
			}
		});
		
	}
	
	public void setClub(List<Club> listaClub) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.addColumn("Nome");
		model.addColumn("Città");
		for(int i=0; i<listaClub.size(); i++) model.addRow(new Object[] {listaClub.get(i).getNome(), listaClub.get(i).getCitta()});
	}
	
	public Club getClubFromFields() {
		String nome = nomeTF.getText();
		String citta = cittaTF.getText();
		return new Club(nome, citta);
	}
	
	public Club getClubFromSelectedRow() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String nome = (String) model.getValueAt(table.getSelectedRow(), 0);
		String citta = (String) model.getValueAt(table.getSelectedRow(), 1);
		return new Club(nome, citta);
	}
	
	public void controllaDuplicato() throws DuplicatoException {
		for(int i = 0; i<table.getRowCount(); i++)
			if(nomeTF.getText().equals(table.getModel().getValueAt(i, 0))) throw new DuplicatoException();
	}
	
	public void controllaDuplicatoModifica() throws DuplicatoException {
		for(int i = 0; i<table.getRowCount(); i++)
			if(i!=table.getSelectedRow() && nomeTF.getText().equals(table.getModel().getValueAt(i, 0))) throw new DuplicatoException();
	}
	
	public void setFields() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		nomeTF.setText((String) model.getValueAt(table.getSelectedRow(), 0));
		cittaTF.setText((String) model.getValueAt(table.getSelectedRow(), 1));
	}
	
	public void ricaricaClub() {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		table.setEnabled(false);
		model.setRowCount(0);
		model.setColumnCount(0);
		controller.setClubInOrdine((String) ordinaComboBox.getSelectedItem());
		table.setEnabled(true);
	}
}
