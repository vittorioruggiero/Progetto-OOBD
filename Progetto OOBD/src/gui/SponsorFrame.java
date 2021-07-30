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
import entity.Sponsor;
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

public class SponsorFrame extends JFrame {
	private Controller controller;
	private JPanel contentPane;
	private JTable table;
	private JTextField nomeTF;
	private JTextField statoTF;
	private JComboBox<String> ordinaComboBox;

	public SponsorFrame(Controller controller) {
		setResizable(false);
		setTitle("SponsorFrame");
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel sponsorLabel = new JLabel("Sponsor");
		sponsorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		sponsorLabel.setBounds(232, 21, 73, 13);
		contentPane.add(sponsorLabel);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SponsorFrame.this.controller.apriHomeFrame(SponsorFrame.this);
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
				nomeTF.setText((String) model.getValueAt(table.getSelectedRow(), 0));
				statoTF.setText((String) model.getValueAt(table.getSelectedRow(), 1));
			}
		});
		
		scrollPane.setViewportView(table);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeLabel.setBounds(10, 199, 93, 19);
		contentPane.add(nomeLabel);
		
		JLabel statoLabel = new JLabel("Stato");
		statoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		statoLabel.setBounds(10, 226, 93, 19);
		contentPane.add(statoLabel);
		
		nomeTF = new JTextField();
		nomeTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeTF.setBounds(113, 199, 96, 19);
		contentPane.add(nomeTF);
		nomeTF.setColumns(10);
		
		statoTF = new JTextField();
		statoTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		statoTF.setBounds(113, 226, 96, 20);
		contentPane.add(statoTF);
		statoTF.setColumns(10);
		
		JButton inserisciButton = new JButton("Inserisci");
		
		//GESTIONE DELL'INSERIMENTO DELLE RIGHE
		inserisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nomeTF.getText().length()>0 && statoTF.getText().length()>0) {
					Sponsor sponsor;
					String nome = nomeTF.getText();
					String stato = statoTF.getText();
					
					try {
						for(int i = 0; i<table.getRowCount(); i++)
							if(nome.equals(model.getValueAt(i, 0))) throw new DuplicatoException();
						sponsor = new Sponsor(nome, stato);
						controller.inserisci(sponsor);
						ricaricaSponsor();
					}
					catch (DuplicatoException exception) {
						JOptionPane.showMessageDialog(SponsorFrame.this, "Lo sponsor " +nome+ " � gi� presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
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
				String nome = nomeTF.getText();
				String stato = statoTF.getText();
				Sponsor sponsor = new Sponsor(nome, stato);
				controller.rimuovi(sponsor);
				ricaricaSponsor();
			}
		});
		rimuoviButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rimuoviButton.setBounds(243, 226, 93, 19);
		contentPane.add(rimuoviButton);
		
		//GESTIONE DELLA MODIFICA DELLE RIGHE
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1 && nomeTF.getText().length()>0 && statoTF.getText().length()>0) {
					Sponsor sponsor;
					String nome = nomeTF.getText();
					String stato = statoTF.getText();
					try {
						for(int i = 0; i<table.getRowCount(); i++) 
							if(i!=table.getSelectedRow() && nome.equals(model.getValueAt(i, 0))) throw new DuplicatoException();
						//if(stato<=0) throw new GettoneNonValidoException();
						sponsor = new Sponsor(nome, stato);
						String vecchioNome = (String) model.getValueAt(table.getSelectedRow(), 0);
						controller.modifica(sponsor, vecchioNome);
						ricaricaSponsor();
					}
					catch (DuplicatoException exception) {
						JOptionPane.showMessageDialog(SponsorFrame.this, "Lo sponsor " +nome+ " � gi� presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
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
		ordinaComboBox.addItem("Stato");
		contentPane.add(ordinaComboBox);
		ordinaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ricaricaSponsor();
			}
		});
		
	}
	
	public void setSponsor(List<Sponsor> listaSponsor) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.addColumn("Nome");
		model.addColumn("Stato");
		for(int i=0; i<listaSponsor.size(); i++) model.addRow(new Object[] {listaSponsor.get(i).getNome(), listaSponsor.get(i).getStato()});
	}
	
	public void ricaricaSponsor() {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		table.setEnabled(false);
		model.setRowCount(0);
		model.setColumnCount(0);
		controller.setSponsorInOrdine((String) ordinaComboBox.getSelectedItem());
		table.setEnabled(true);
	}
}
