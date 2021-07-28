package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import entity.Atleta;
import entity.Nazionale;
import entity.Procuratore;
import exception.CodiceFiscaleNonValidoException;
import exception.DuplicatoException;
import exception.LunghezzaCodiceFiscaleNonValidaException;
import exception.PresenzeNazionaleMancantiException;

public class AtletaFrame extends JFrame {

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
	JComboBox<String> nazionaleComboBox;
	private JTextField presenzeNazionaleTF;
	JComboBox<String> procuratoreComboBox;

	public AtletaFrame(Controller controller) {
		setResizable(false);
		setTitle("AtletaFrame");
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1030, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel atletiLabel = new JLabel("Atleti");
		atletiLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		atletiLabel.setBounds(472, 20, 50, 14);
		contentPane.add(atletiLabel);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AtletaFrame.this.controller.apriHomeFrame(AtletaFrame.this);
			}
		});
		indietroButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		indietroButton.setBounds(912, 373, 93, 19);
		contentPane.add(indietroButton);
		
		//Codice di scrollPane scritto a mano per evitare problemi
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 995, 131);
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
				nazionaleComboBox.setSelectedItem(String.valueOf(model.getValueAt(table.getSelectedRow(), 4)));
				presenzeNazionaleTF.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 5)));
				procuratoreComboBox.setSelectedItem(String.valueOf(model.getValueAt(table.getSelectedRow(), 6)));
			}
		});
		
		scrollPane.setViewportView(table);
		
		JLabel codiceFiscaleLabel = new JLabel("Codice fiscale");
		codiceFiscaleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		codiceFiscaleLabel.setBounds(10, 199, 140, 19);
		contentPane.add(codiceFiscaleLabel);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeLabel.setBounds(10, 226, 140, 19);
		contentPane.add(nomeLabel);
		
		codiceFiscaleTF = new JTextField();
		codiceFiscaleTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		codiceFiscaleTF.setBounds(160, 199, 150, 19);
		contentPane.add(codiceFiscaleTF);
		codiceFiscaleTF.setColumns(10);
		
		nomeTF = new JTextField();
		nomeTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeTF.setBounds(160, 225, 150, 20);
		contentPane.add(nomeTF);
		nomeTF.setColumns(10);
		
		JButton inserisciButton = new JButton("Inserisci");
		
		//GESTIONE DELL'INSERIMENTO DELLE RIGHE
		inserisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(codiceFiscaleTF.getText().length()>0 && nomeTF.getText().length()>0 && cognomeTF.getText().length()>0 && annoComboBox.getSelectedIndex()!=-1 && meseComboBox.getSelectedIndex()!=-1 && giornoComboBox.getSelectedIndex()!=-1) {
					Atleta atleta;
					String codiceFiscale = codiceFiscaleTF.getText();
					String nome = nomeTF.getText();
					String cognome = cognomeTF.getText();
					LocalDate dataNascita = LocalDate.of((int) annoComboBox.getSelectedItem(), (int) meseComboBox.getSelectedItem(), (int) giornoComboBox.getSelectedItem());
					String nazionale = (String) nazionaleComboBox.getSelectedItem();
					int presenzeNazionale = 0;
					String procuratore = (String) procuratoreComboBox.getSelectedItem();
					
					try {
						if(codiceFiscale.length()!=16) throw new LunghezzaCodiceFiscaleNonValidaException();
						if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
						for(int i = 0; i<table.getRowCount(); i++)
							if(codiceFiscale.equals(model.getValueAt(i, 0))) throw new DuplicatoException();
						if(nazionale.length()>0 && presenzeNazionaleTF.getText().length()==0) throw new PresenzeNazionaleMancantiException();
						if(nazionale.length()>0 && presenzeNazionaleTF.getText().length()>0) presenzeNazionale = Integer.valueOf(presenzeNazionaleTF.getText()); 
						atleta = new Atleta(codiceFiscale, nome, cognome, dataNascita, presenzeNazionale, new Procuratore(procuratore, null, null, null), new Nazionale(nazionale, 0));
						controller.inserisci(atleta);
						ricaricaAtleti();
					}
					catch (LunghezzaCodiceFiscaleNonValidaException exception) {
						JOptionPane.showMessageDialog(AtletaFrame.this, "Il codice fiscale deve contenere esattamente 16 caratteri", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (CodiceFiscaleNonValidoException exception) {
						JOptionPane.showMessageDialog(AtletaFrame.this, "Il codice fiscale non è scritto in una forma valida", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (DuplicatoException exception) {
						JOptionPane.showMessageDialog(AtletaFrame.this, "L'atleta " +codiceFiscale+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (PresenzeNazionaleMancantiException exception) {
						JOptionPane.showMessageDialog(AtletaFrame.this, "Specificare il numero di presenze nella nazionale scelta", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		inserisciButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		inserisciButton.setBounds(331, 199, 93, 19);
		contentPane.add(inserisciButton);
		
		//GESTIONE DELLA RIMOZIONE DELLE RIGHE
		JButton rimuoviButton = new JButton("Rimuovi");
		rimuoviButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codiceFiscale = codiceFiscaleTF.getText();
				String nome = nomeTF.getText();
				String cognome = cognomeTF.getText();
				LocalDate dataNascita = LocalDate.of((int) annoComboBox.getSelectedItem(), (int) meseComboBox.getSelectedItem(), (int) giornoComboBox.getSelectedItem());
				String nazionale = (String) nazionaleComboBox.getSelectedItem();
				int presenzeNazionale;
				if(nazionale.length()>0 && presenzeNazionaleTF.getText().length()>0) presenzeNazionale = Integer.valueOf(presenzeNazionaleTF.getText());
				else presenzeNazionale = 0;
				String procuratore = (String) procuratoreComboBox.getSelectedItem();
				Atleta atleta = new Atleta(codiceFiscale, nome, cognome, dataNascita, presenzeNazionale, new Procuratore(procuratore, null, null, null), new Nazionale(nazionale, 0));
				controller.rimuovi(atleta);
				ricaricaAtleti();
			}
		});
		rimuoviButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rimuoviButton.setBounds(331, 226, 93, 19);
		contentPane.add(rimuoviButton);
		
		//GESTIONE DELLA MODIFICA DELLE RIGHE
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1 && codiceFiscaleTF.getText().length()>0 && nomeTF.getText().length()>0 && cognomeTF.getText().length()>0 && annoComboBox.getSelectedIndex()!=-1 && meseComboBox.getSelectedIndex()!=-1 && giornoComboBox.getSelectedIndex()!=-1) {
					Atleta atleta;
					String codiceFiscale = codiceFiscaleTF.getText();
					String nome = nomeTF.getText();
					String cognome = cognomeTF.getText();
					LocalDate dataNascita = LocalDate.of((int) annoComboBox.getSelectedItem(), (int) meseComboBox.getSelectedItem(), (int) giornoComboBox.getSelectedItem());
					String nazionale = (String) nazionaleComboBox.getSelectedItem();
					int presenzeNazionale = 0;
					String procuratore = (String) procuratoreComboBox.getSelectedItem();
					
					try {
						if(codiceFiscale.length()!=16) throw new LunghezzaCodiceFiscaleNonValidaException();
						if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
						for(int i = 0; i<table.getRowCount(); i++)
							if(i!=table.getSelectedRow() && codiceFiscale.equals(model.getValueAt(i, 0))) throw new DuplicatoException();
						if(nazionale.length()>0 && presenzeNazionaleTF.getText().length()==0) throw new PresenzeNazionaleMancantiException();
						if(nazionale.length()>0 && presenzeNazionaleTF.getText().length()>0) presenzeNazionale = Integer.valueOf(presenzeNazionaleTF.getText()); 
						atleta = new Atleta(codiceFiscale, nome, cognome, dataNascita, presenzeNazionale, new Procuratore(procuratore, null, null, null), new Nazionale(nazionale, 0));
						String vecchioCodiceFiscale = (String) model.getValueAt(table.getSelectedRow(), 0);
						controller.modifica(atleta, vecchioCodiceFiscale);
						ricaricaAtleti();
					}
					catch (LunghezzaCodiceFiscaleNonValidaException exception) {
						JOptionPane.showMessageDialog(AtletaFrame.this, "Il codice fiscale deve contenere esattamente 16 caratteri", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (CodiceFiscaleNonValidoException exception) {
						JOptionPane.showMessageDialog(AtletaFrame.this, "Il codice fiscale non è scritto in una forma valida", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (DuplicatoException exception) {
						JOptionPane.showMessageDialog(AtletaFrame.this, "L'atleta " +codiceFiscale+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (PresenzeNazionaleMancantiException exception) {
						JOptionPane.showMessageDialog(AtletaFrame.this, "Specificare il numero di presenze nella nazionale scelta", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		modificaButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		modificaButton.setBounds(331, 255, 93, 19);
		contentPane.add(modificaButton);
		
		JLabel ordinaLabel = new JLabel("Ordina per");
		ordinaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		ordinaLabel.setBounds(331, 284, 93, 19);
		contentPane.add(ordinaLabel);
		
		ordinaComboBox = new JComboBox<String>();
		ordinaComboBox.setFont(new Font("SansSerif", Font.PLAIN, 10));
		ordinaComboBox.setBounds(331, 305, 93, 19);
		ordinaComboBox.addItem("CodiceFiscale");
		ordinaComboBox.addItem("Nome");
		ordinaComboBox.addItem("Cognome");
		ordinaComboBox.addItem("DataNascita");;
		ordinaComboBox.addItem("Nazionale");
		ordinaComboBox.addItem("PresenzeNazionale");
		ordinaComboBox.addItem("Procuratore");
		contentPane.add(ordinaComboBox);
		
		cognomeTF = new JTextField();
		cognomeTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cognomeTF.setColumns(10);
		cognomeTF.setBounds(160, 254, 150, 20);
		contentPane.add(cognomeTF);
		
		JLabel cognomeLabel = new JLabel("Cognome");
		cognomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cognomeLabel.setBounds(10, 255, 140, 19);
		contentPane.add(cognomeLabel);
		
		JLabel dataNascitaLabel = new JLabel("Data di nascita");
		dataNascitaLabel.setVerticalAlignment(SwingConstants.TOP);
		dataNascitaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		dataNascitaLabel.setBounds(10, 286, 140, 19);
		contentPane.add(dataNascitaLabel);
		
		annoComboBox = new JComboBox<Integer>();
		annoComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
		annoComboBox.setBounds(160, 286, 59, 19);
		for(int i=1950; i<2022; i++) annoComboBox.addItem(i);
		contentPane.add(annoComboBox);
		
		meseComboBox = new JComboBox<Integer>();
		meseComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
		meseComboBox.setBounds(224, 286, 41, 19);
		for(int i=1; i<13; i++) meseComboBox.addItem(i);
		contentPane.add(meseComboBox);
		
		giornoComboBox = new JComboBox<Integer>();
		giornoComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
		giornoComboBox.setBounds(269, 286, 41, 19);
		for(int i=1; i<32; i++) giornoComboBox.addItem(i);
		contentPane.add(giornoComboBox);
		
		presenzeNazionaleTF = new JTextField();
		presenzeNazionaleTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		presenzeNazionaleTF.setColumns(10);
		presenzeNazionaleTF.setBounds(160, 344, 150, 19);
		contentPane.add(presenzeNazionaleTF);
		
		JLabel nazionaleLabel = new JLabel("Nazionale");
		nazionaleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nazionaleLabel.setBounds(10, 315, 140, 19);
		contentPane.add(nazionaleLabel);
		
		JLabel presenzeNazionaleLabel = new JLabel("Presenze in nazionale");
		presenzeNazionaleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		presenzeNazionaleLabel.setBounds(10, 344, 140, 19);
		contentPane.add(presenzeNazionaleLabel);
		
		JLabel procuratoreLabel = new JLabel("Procuratore");
		procuratoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		procuratoreLabel.setBounds(10, 373, 140, 19);
		contentPane.add(procuratoreLabel);
		
		nazionaleComboBox = new JComboBox<String>();
		nazionaleComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nazionaleComboBox.getSelectedItem().equals("")) {
					presenzeNazionaleTF.setText("");
					presenzeNazionaleTF.setEnabled(false);
				}
				else presenzeNazionaleTF.setEnabled(true);
			}
		});
		nazionaleComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nazionaleComboBox.setBounds(160, 315, 150, 19);
		nazionaleComboBox.addItem("");
		nazionaleComboBox.setSelectedItem("");
		contentPane.add(nazionaleComboBox);
		
		procuratoreComboBox = new JComboBox<String>();
		procuratoreComboBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
		procuratoreComboBox.setBounds(160, 373, 150, 19);
		procuratoreComboBox.addItem("");
		contentPane.add(procuratoreComboBox);
		
		JButton sorgentiIntroitoButton = new JButton("<html>Sorgenti<br>di introito</html>");
		sorgentiIntroitoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriSorgentiIntroitoFrame();
			}
		});
		sorgentiIntroitoButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		sorgentiIntroitoButton.setBounds(912, 200, 93, 45);
		contentPane.add(sorgentiIntroitoButton);
		
		ordinaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ricaricaAtleti();
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
	
	public void setAtleti(List<Atleta> listaAtleti, List<String> listaNomiNazionali, List<String> listaCodiciFiscaliProcuratori) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.addColumn("Codice fiscale");
		model.addColumn("Nome");
		model.addColumn("Cognome");
		model.addColumn("Data di nascita");
		model.addColumn("Nazionale");
		model.addColumn("Presenze in nazionale");
		model.addColumn("Procuratore");
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		
		for(int i=0; i<listaAtleti.size(); i++) {
			if(listaAtleti.get(i).getNazionale()!=null && listaAtleti.get(i).getProcuratore()!=null) {
				model.addRow(new Object[] {
						listaAtleti.get(i).getCodiceFiscale(),
						listaAtleti.get(i).getNome(), 
						listaAtleti.get(i).getCognome(),
						listaAtleti.get(i).getDataNascita(),
						listaAtleti.get(i).getNazionale().getNome(),
						listaAtleti.get(i).getPresenzeNazionale(),
						listaAtleti.get(i).getProcuratore().getCodiceFiscale()
						});
			}
			else if(listaAtleti.get(i).getNazionale()!=null && listaAtleti.get(i).getProcuratore()==null) {
				model.addRow(new Object[] {
						listaAtleti.get(i).getCodiceFiscale(),
						listaAtleti.get(i).getNome(), 
						listaAtleti.get(i).getCognome(),
						listaAtleti.get(i).getDataNascita(),
						listaAtleti.get(i).getNazionale().getNome(),
						listaAtleti.get(i).getPresenzeNazionale(),
						""
						});
			}
			else if(listaAtleti.get(i).getNazionale()==null && listaAtleti.get(i).getProcuratore()!=null) {
				model.addRow(new Object[] {
						listaAtleti.get(i).getCodiceFiscale(),
						listaAtleti.get(i).getNome(), 
						listaAtleti.get(i).getCognome(),
						listaAtleti.get(i).getDataNascita(),
						"",
						"",
						listaAtleti.get(i).getProcuratore().getCodiceFiscale()
						});
			}
			else if(listaAtleti.get(i).getNazionale()==null && listaAtleti.get(i).getProcuratore()==null) {
				model.addRow(new Object[] {
						listaAtleti.get(i).getCodiceFiscale(),
						listaAtleti.get(i).getNome(), 
						listaAtleti.get(i).getCognome(),
						listaAtleti.get(i).getDataNascita(),
						"",
						"",
						""
						});
			}
		}
		
		if(listaNomiNazionali!=null)
		    for(int j=0; j<listaNomiNazionali.size(); j++) nazionaleComboBox.addItem(listaNomiNazionali.get(j));
		if(listaCodiciFiscaliProcuratori!=null)
		   for(int j=0; j<listaCodiciFiscaliProcuratori.size(); j++) procuratoreComboBox.addItem(listaCodiciFiscaliProcuratori.get(j));
	}
	
	public void ricaricaAtleti() {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		table.setEnabled(false);
		model.setRowCount(0);
		model.setColumnCount(0);
		controller.setAtletiInOrdine((String) ordinaComboBox.getSelectedItem());
		table.setEnabled(true);
	}
}
