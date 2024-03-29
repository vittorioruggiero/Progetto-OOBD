package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import entity.Procuratore;
import exception.CodiceFiscaleNonValidoException;
import exception.DuplicatoException;
import javax.swing.SwingConstants;

public class ProcuratoreFrame extends JFrame {

	private Controller controller;
	private JPanel contentPane;
	private JTable table;
	private JTextField codiceFiscaleTF;
	private JTextField nomeTF;
	private JComboBox<String> ordinaComboBox;
	private JTextField cognomeTF;
	JComboBox<Integer> annoComboBox;
	JComboBox<Integer> meseComboBox;
	JComboBox<Integer> giornoComboBox;

	public ProcuratoreFrame(Controller controller) {
		setResizable(false);
		setTitle("ProcuratoreFrame");
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel procuratoriLabel = new JLabel("Procuratori");
		procuratoriLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		procuratoriLabel.setBounds(232, 21, 84, 13);
		contentPane.add(procuratoriLabel);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProcuratoreFrame.this.controller.apriHomeFrame(ProcuratoreFrame.this);
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
				codiceFiscaleTF.setText((String) model.getValueAt(table.getSelectedRow(), 0));
				nomeTF.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 1)));
				cognomeTF.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 2)));
				annoComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 3)).getYear());
				meseComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 3)).getMonthValue());
				giornoComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 3)).getDayOfMonth());
			}
		});
		
		scrollPane.setViewportView(table);
		
		JLabel codiceFiscaleLabel = new JLabel("Codice fiscale");
		codiceFiscaleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		codiceFiscaleLabel.setBounds(10, 199, 105, 19);
		contentPane.add(codiceFiscaleLabel);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeLabel.setBounds(10, 226, 105, 19);
		contentPane.add(nomeLabel);
		
		codiceFiscaleTF = new JTextField();
		codiceFiscaleTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		codiceFiscaleTF.setBounds(125, 199, 150, 19);
		contentPane.add(codiceFiscaleTF);
		codiceFiscaleTF.setColumns(10);
		
		nomeTF = new JTextField();
		nomeTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeTF.setBounds(125, 225, 150, 20);
		contentPane.add(nomeTF);
		nomeTF.setColumns(10);
		
		JButton inserisciButton = new JButton("Inserisci");
		
		//GESTIONE DELL'INSERIMENTO DELLE RIGHE
		inserisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(codiceFiscaleTF.getText().length()>0 && nomeTF.getText().length()>0 && cognomeTF.getText().length()>0
						&& annoComboBox.getSelectedIndex()!=-1 && meseComboBox.getSelectedIndex()!=-1 && giornoComboBox.getSelectedIndex()!=-1) {
					controller.inserisciProcuratore();
					ricaricaProcuratori();
				}
			}
		});
		
		inserisciButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		inserisciButton.setBounds(297, 199, 93, 19);
		contentPane.add(inserisciButton);
		
		//GESTIONE DELLA RIMOZIONE DELLE RIGHE
		JButton rimuoviButton = new JButton("Rimuovi");
		rimuoviButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(codiceFiscaleTF.getText().length()>0 && nomeTF.getText().length()>0 && cognomeTF.getText().length()>0
						&& annoComboBox.getSelectedIndex()!=-1 && meseComboBox.getSelectedIndex()!=-1 && giornoComboBox.getSelectedIndex()!=-1) {
					controller.rimuoviProcuratore();
					ricaricaProcuratori();
				}
			}
		});
		rimuoviButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rimuoviButton.setBounds(297, 226, 93, 19);
		contentPane.add(rimuoviButton);
		
		//GESTIONE DELLA MODIFICA DELLE RIGHE
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1 && codiceFiscaleTF.getText().length()>0 && nomeTF.getText().length()>0 && cognomeTF.getText().length()>0
						&& annoComboBox.getSelectedIndex()!=-1 && meseComboBox.getSelectedIndex()!=-1 && giornoComboBox.getSelectedIndex()!=-1) {
					controller.modificaProcuratore();
					ricaricaProcuratori();
				}
			}
		});
		modificaButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		modificaButton.setBounds(297, 255, 93, 19);
		contentPane.add(modificaButton);
		
		JLabel ordinaLabel = new JLabel("Ordina per");
		ordinaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		ordinaLabel.setBounds(297, 284, 93, 19);
		contentPane.add(ordinaLabel);
		
		ordinaComboBox = new JComboBox<String>();
		ordinaComboBox.setFont(new Font("SansSerif", Font.PLAIN, 10));
		ordinaComboBox.setBounds(297, 305, 93, 19);
		ordinaComboBox.addItem("CodiceFiscale");
		ordinaComboBox.addItem("Nome");
		ordinaComboBox.addItem("Cognome");
		ordinaComboBox.addItem("DataNascita");;
		contentPane.add(ordinaComboBox);
		
		cognomeTF = new JTextField();
		cognomeTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cognomeTF.setColumns(10);
		cognomeTF.setBounds(125, 255, 150, 20);
		contentPane.add(cognomeTF);
		
		JLabel cognomeLabel = new JLabel("Cognome");
		cognomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cognomeLabel.setBounds(10, 255, 105, 19);
		contentPane.add(cognomeLabel);
		
		JLabel dataNascitaLabel = new JLabel("Data di nascita");
		dataNascitaLabel.setVerticalAlignment(SwingConstants.TOP);
		dataNascitaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		dataNascitaLabel.setBounds(10, 284, 105, 19);
		contentPane.add(dataNascitaLabel);
		
		annoComboBox = new JComboBox<Integer>();
		annoComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
		annoComboBox.setBounds(125, 286, 59, 19);
		for(int i=1950; i<2022; i++) annoComboBox.addItem(i);
		contentPane.add(annoComboBox);
		
		meseComboBox = new JComboBox<Integer>();
		meseComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
		meseComboBox.setBounds(189, 286, 41, 19);
		for(int i=1; i<13; i++) meseComboBox.addItem(i);
		contentPane.add(meseComboBox);
		
		giornoComboBox = new JComboBox<Integer>();
		giornoComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
		giornoComboBox.setBounds(234, 286, 41, 19);
		for(int i=1; i<32; i++) giornoComboBox.addItem(i);
		contentPane.add(giornoComboBox);
		
		JButton procuratoreMaxGuadagniButton = new JButton("<html>Max<br>guadagni</html>");
		procuratoreMaxGuadagniButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriProcuratoreMaxGuadagniFrame();
			}
		});
		procuratoreMaxGuadagniButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		procuratoreMaxGuadagniButton.setBounds(434, 200, 93, 45);
		contentPane.add(procuratoreMaxGuadagniButton);
		
		ordinaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ricaricaProcuratori();
			}
		});
		
		//GESTIONE ANNO
		annoComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(annoComboBox.isEnabled()) {
					giornoComboBox.removeAllItems();
					int anno = annoComboBox.getItemAt(annoComboBox.getSelectedIndex());
					int mese = meseComboBox.getItemAt(meseComboBox.getSelectedIndex());
					if(mese==2) //febbraio
						if(((anno%100)!=0 && (anno%4)==0) || ((anno%100)==0 && (anno%400)==0)) //anno bisestile
							for(int i=1; i<30; i++) giornoComboBox.addItem(i);
						else for(int i=1; i<29; i++) giornoComboBox.addItem(i);
					else if(mese==4 || mese==6 || mese==9 || mese==11) //aprile, giugno, settembre e novembre
						for(int i=1; i<31; i++) giornoComboBox.addItem(i);
					else for(int i=1; i<32; i++) giornoComboBox.addItem(i);
				}
			}
		});
		
		//GESTIONE MESE
		meseComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					giornoComboBox.removeAllItems();
					int anno = annoComboBox.getItemAt(annoComboBox.getSelectedIndex());
					int mese = meseComboBox.getItemAt(meseComboBox.getSelectedIndex());
					if(mese==2) //febbraio
						if(((anno%100)!=0 && (anno%4)==0) || ((anno%100)==0 && (anno%400)==0)) //anno bisestile
							for(int i=1; i<30; i++) giornoComboBox.addItem(i);
						else for(int i=1; i<29; i++) giornoComboBox.addItem(i);
					else if(mese==4 || mese==6 || mese==9 || mese==11) //aprile, giugno, settembre e novembre
						for(int i=1; i<31; i++) giornoComboBox.addItem(i);
					else for(int i=1; i<32; i++) giornoComboBox.addItem(i);
			    }
		});
		
	}
	
	public void setProcuratori(List<Procuratore> listaProcuratori) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.addColumn("Codice fiscale");
		model.addColumn("Nome");
		model.addColumn("Cognome");
		model.addColumn("Data di nascita");
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		
		for(int i=0; i<listaProcuratori.size(); i++) model.addRow(new Object[] {
				listaProcuratori.get(i).getCodiceFiscale(),
				listaProcuratori.get(i).getNome(),
				listaProcuratori.get(i).getCognome(),
				listaProcuratori.get(i).getDataNascita()
				});
	}
	
	public Procuratore getProcuratoreFromFields() throws CodiceFiscaleNonValidoException {
		String codiceFiscale = codiceFiscaleTF.getText();
		String nome = nomeTF.getText();
		String cognome = cognomeTF.getText();
		LocalDate dataNascita = LocalDate.of((int) annoComboBox.getSelectedItem(), (int) meseComboBox.getSelectedItem(), (int) giornoComboBox.getSelectedItem());
		return new Procuratore(codiceFiscale, nome, cognome, dataNascita);
	}
	
	public Procuratore getProcuratoreFromSelectedRow() throws CodiceFiscaleNonValidoException {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String codiceFiscale = (String) model.getValueAt(table.getSelectedRow(), 0);
		String nome = (String) model.getValueAt(table.getSelectedRow(), 1);
		String cognome = (String) model.getValueAt(table.getSelectedRow(), 2);
		LocalDate dataNascita = (LocalDate) model.getValueAt(table.getSelectedRow(), 3);
		return new Procuratore(codiceFiscale, nome, cognome, dataNascita);
	}
	
	public void controllaDuplicato() throws DuplicatoException {
		for(int i = 0; i<table.getRowCount(); i++)
			if(codiceFiscaleTF.getText().equals(table.getModel().getValueAt(i, 0))) throw new DuplicatoException();
	}
	
	public void controllaDuplicatoModifica() throws DuplicatoException {
		for(int i = 0; i<table.getRowCount(); i++)
			if(i!=table.getSelectedRow() && codiceFiscaleTF.getText().equals(table.getModel().getValueAt(i, 0))) throw new DuplicatoException();
	}
	
	public void setFields() {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		codiceFiscaleTF.setText((String) model.getValueAt(table.getSelectedRow(), 0));
		nomeTF.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 1)));
		cognomeTF.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 2)));
		annoComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 3)).getYear());
		meseComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 3)).getMonthValue());
		giornoComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 3)).getDayOfMonth());
	}
	
	public void ricaricaProcuratori() {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		table.setEnabled(false);
		model.setRowCount(0);
		model.setColumnCount(0);
		controller.setProcuratoriInOrdine((String) ordinaComboBox.getSelectedItem());
		table.setEnabled(true);
	}
}
