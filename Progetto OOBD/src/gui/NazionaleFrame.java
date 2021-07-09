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
import entity.Nazionale;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NazionaleFrame extends JFrame {
	private Controller controller;
	private JPanel contentPane;
	private JTable table;
	private JTextField nomeTF;
	private JTextField valoreGettoneTF;
	private JComboBox<String> ordinaComboBox;

	public NazionaleFrame(Controller controller) {
		setResizable(false);
		setTitle("NazionaleFrame");
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nazionaliLabel = new JLabel("Nazionali");
		nazionaliLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		nazionaliLabel.setBounds(232, 21, 73, 13);
		contentPane.add(nazionaliLabel);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NazionaleFrame.this.controller.apriHomeFrame(NazionaleFrame.this);
			}
		});
		indietroButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		indietroButton.setBounds(434, 318, 93, 19);
		contentPane.add(indietroButton);
		
		//Codice di scrollPane scritto a mano per evitare problemi
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
				valoreGettoneTF.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 1)));
			}
		});
		
		scrollPane.setViewportView(table);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeLabel.setBounds(10, 199, 93, 19);
		contentPane.add(nomeLabel);
		
		JLabel valoreGettoneLabel = new JLabel("Valore gettone");
		valoreGettoneLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		valoreGettoneLabel.setBounds(10, 226, 93, 19);
		contentPane.add(valoreGettoneLabel);
		
		nomeTF = new JTextField();
		nomeTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeTF.setBounds(113, 199, 96, 19);
		contentPane.add(nomeTF);
		nomeTF.setColumns(10);
		
		valoreGettoneTF = new JTextField();
		valoreGettoneTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		valoreGettoneTF.setBounds(113, 226, 96, 20);
		contentPane.add(valoreGettoneTF);
		valoreGettoneTF.setColumns(10);
		
		JButton inserisciButton = new JButton("Inserisci");
		
		//GESTIONE DELL'INSERIMENTO DELLE RIGHE
		inserisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!nomeTF.getText().equals("") && !valoreGettoneTF.getText().equals("")) {
					String nome = nomeTF.getText();
					double valoreGettone = Double.parseDouble(valoreGettoneTF.getText());
					if(valoreGettone<=0) JOptionPane.showMessageDialog(NazionaleFrame.this, "Il valore del gettone deve essere maggiore di 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					else {
						for(int i = 0; i<table.getRowCount(); i++) {
							if(nome.equals(model.getValueAt(i, 0))) {
								JOptionPane.showMessageDialog(NazionaleFrame.this, "La nazionale " +nome+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						Nazionale nazionale = new Nazionale(nome, valoreGettone);
						controller.inserisci(nazionale);
						ricaricaNazionali();
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
				double valoreGettone = Double.parseDouble(valoreGettoneTF.getText());
				Nazionale nazionale = new Nazionale(nome, valoreGettone);
				controller.rimuovi(nazionale);
				ricaricaNazionali();
			}
		});
		rimuoviButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rimuoviButton.setBounds(243, 226, 93, 19);
		contentPane.add(rimuoviButton);
		
		//GESTIONE DELLA MODIFICA DELLE RIGHE
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1 && !nomeTF.getText().equals("") && !valoreGettoneTF.getText().equals("")) {
					String nome = nomeTF.getText();
					double valoreGettone = Double.parseDouble(valoreGettoneTF.getText());
					if(valoreGettone<=0) JOptionPane.showMessageDialog(NazionaleFrame.this, "Il valore del gettone deve essere maggiore di 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					else {
						for(int i = 0; i<table.getRowCount(); i++) {
							if(i!=table.getSelectedRow() && nome.equals(model.getValueAt(i, 0))) {
								JOptionPane.showMessageDialog(NazionaleFrame.this, "La nazionale " +nome+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
						Nazionale nazionale = new Nazionale(nome, valoreGettone);
						String vecchioNome = (String) model.getValueAt(table.getSelectedRow(), 0);
						controller.modifica(nazionale, vecchioNome);
						ricaricaNazionali();
					}
				}
			}
		});
		modificaButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		modificaButton.setBounds(243, 255, 93, 19);
		contentPane.add(modificaButton);
		
		JLabel ordinaLabel = new JLabel("Ordina per");
		ordinaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		ordinaLabel.setBounds(243, 284, 93, 19);
		contentPane.add(ordinaLabel);
		
		ordinaComboBox = new JComboBox<String>();
		ordinaComboBox.setBounds(243, 306, 93, 19);
		ordinaComboBox.addItem("Nome");
		ordinaComboBox.addItem("ValoreGettone");
		contentPane.add(ordinaComboBox);
		ordinaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ricaricaNazionali();
			}
		});
		
	}
	
	public void setNazionali(List<Nazionale> listaNazionali) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.addColumn("Nome");
		model.addColumn("Valore gettone");
		for(int i=0; i<listaNazionali.size(); i++) model.addRow(new Object[] {listaNazionali.get(i).getNome(), listaNazionali.get(i).getValoreGettone()});
	}
	
	public void ricaricaNazionali() {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		table.setEnabled(false);
		model.setRowCount(0);
		model.setColumnCount(0);
		controller.setNazionaliInOrdine((String) ordinaComboBox.getSelectedItem());
		table.setEnabled(true);
	}
}
