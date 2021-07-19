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
import entity.Contratto;
import exception.CodiceFiscaleNonValidoException;
import exception.DuplicatoException;
import exception.LunghezzaCodiceFiscaleNonValidaException;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class ContrattoFrame extends JFrame {

	private Controller controller;
	private JPanel contentPane;
	private JTable table;
	private JTextField atletaTF;
	private JTextField retribuzioneTF;
	private JComboBox<String> ordinaComboBox;
	private JTextField percentualeProcuratoreTF;
	JComboBox<Integer> annoInizioComboBox;
	JComboBox<Integer> meseInizioComboBox;
	JComboBox<Integer> giornoInizioComboBox;
	JComboBox<Integer> annoFineComboBox;
	JComboBox<Integer> meseFineComboBox;
	JComboBox<Integer> giornoFineComboBox;
	JComboBox<String> club_sponsorComboBox;
	private final ButtonGroup club_sponsorButtonGroup = new ButtonGroup();

	public ContrattoFrame(Controller controller) {
		setResizable(false);
		setTitle("ContrattoFrame");
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel contrattiLabel = new JLabel("Contratti");
		contrattiLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		contrattiLabel.setBounds(272, 20, 73, 14);
		contentPane.add(contrattiLabel);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContrattoFrame.this.controller.apriHomeFrame(ContrattoFrame.this);
			}
		});
		indietroButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		indietroButton.setBounds(515, 373, 93, 19);
		contentPane.add(indietroButton);
		
		//Codice di scrollPane scritto a mano per evitare problemi
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 617, 131);
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
				atletaTF.setText((String) model.getValueAt(table.getSelectedRow(), 0));
				club_sponsorComboBox.setSelectedItem(String.valueOf(model.getValueAt(table.getSelectedRow(), 1)));
				annoInizioComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 2)).getYear());
				meseInizioComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 2)).getMonthValue());
				giornoInizioComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 2)).getDayOfMonth());
				annoFineComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 3)).getYear());
				meseFineComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 3)).getMonthValue());
				giornoFineComboBox.setSelectedItem((Integer) ((LocalDate) model.getValueAt(table.getSelectedRow(), 3)).getDayOfMonth());
				retribuzioneTF.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 4)));
				percentualeProcuratoreTF.setText(String.valueOf(model.getValueAt(table.getSelectedRow(), 5)));
			}
		});
		
		scrollPane.setViewportView(table);
		
		JLabel atletaLabel = new JLabel("Atleta");
		atletaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		atletaLabel.setBounds(10, 199, 140, 19);
		contentPane.add(atletaLabel);
		
		JLabel retribuzioneLabel = new JLabel("Retribuzione");
		retribuzioneLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		retribuzioneLabel.setBounds(10, 315, 140, 19);
		contentPane.add(retribuzioneLabel);
		
		atletaTF = new JTextField();
		atletaTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		atletaTF.setBounds(160, 199, 150, 19);
		contentPane.add(atletaTF);
		atletaTF.setColumns(10);
		
		retribuzioneTF = new JTextField();
		retribuzioneTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		retribuzioneTF.setBounds(160, 314, 150, 20);
		contentPane.add(retribuzioneTF);
		retribuzioneTF.setColumns(10);
		
		JButton inserisciButton = new JButton("Inserisci");
		
		//GESTIONE DELL'INSERIMENTO DELLE RIGHE
		inserisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(atletaTF.getText().length()>0 && retribuzioneTF.getText().length()>0 && percentualeProcuratoreTF.getText().length()>0 && annoInizioComboBox.getSelectedIndex()!=-1 && meseInizioComboBox.getSelectedIndex()!=-1 && giornoInizioComboBox.getSelectedIndex()!=-1) {
					Contratto contratto;
					String codiceFiscale = atletaTF.getText();
					String club = (String) club_sponsorComboBox.getSelectedItem();
					LocalDate dataInizio = LocalDate.of((int) annoInizioComboBox.getSelectedItem(), (int) meseInizioComboBox.getSelectedItem(), (int) giornoInizioComboBox.getSelectedItem());
					LocalDate dataFine = LocalDate.of((int) annoFineComboBox.getSelectedItem(), (int) meseFineComboBox.getSelectedItem(), (int) giornoFineComboBox.getSelectedItem());
					double retribuzione = Double.valueOf(retribuzioneTF.getText());
					int percentualeProcuratore = Integer.valueOf(percentualeProcuratoreTF.getText());
					double guadagnoProcuratore = 0; //Prova
					
					try {
						if(codiceFiscale.length()!=16) throw new LunghezzaCodiceFiscaleNonValidaException();
						if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
						for(int i = 0; i<table.getRowCount(); i++)
							if(codiceFiscale.equals(model.getValueAt(i, 0))) throw new DuplicatoException();
						//contratto = new Contratto(codiceFiscale, );
						//controller.inserisci(contratto, nazionale, presenzeNazionale, procuratore);
						//ricaricaContratti();
					}
					catch (LunghezzaCodiceFiscaleNonValidaException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "Il codice fiscale deve contenere esattamente 16 caratteri", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (CodiceFiscaleNonValidoException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "Il codice fiscale non è scritto in una forma valida", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (DuplicatoException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "L'contratto " +codiceFiscale+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
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
				String codiceFiscale = atletaTF.getText();
				String nome = retribuzioneTF.getText();
				String cognome = percentualeProcuratoreTF.getText();
				LocalDate dataNascita = LocalDate.of((int) annoInizioComboBox.getSelectedItem(), (int) meseInizioComboBox.getSelectedItem(), (int) giornoInizioComboBox.getSelectedItem());
				String nazionale = (String) club_sponsorComboBox.getSelectedItem();
				int presenzeNazionale;
				//String procuratore = (String) procuratoreComboBox.getSelectedItem();
				//Contratto contratto = new Contratto(codiceFiscale, nome, cognome, dataNascita);
				//controller.rimuovi(contratto, nazionale, presenzeNazionale, procuratore);
				//ricaricaContratti();
			}
		});
		rimuoviButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rimuoviButton.setBounds(331, 226, 93, 19);
		contentPane.add(rimuoviButton);
		
		//GESTIONE DELLA MODIFICA DELLE RIGHE
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1 && atletaTF.getText().length()>0 && retribuzioneTF.getText().length()>0 && percentualeProcuratoreTF.getText().length()>0 && annoInizioComboBox.getSelectedIndex()!=-1 && meseInizioComboBox.getSelectedIndex()!=-1 && giornoInizioComboBox.getSelectedIndex()!=-1) {
					Contratto contratto;
					String codiceFiscale = atletaTF.getText();
					String nome = retribuzioneTF.getText();
					String cognome = percentualeProcuratoreTF.getText();
					LocalDate dataNascita = LocalDate.of((int) annoInizioComboBox.getSelectedItem(), (int) meseInizioComboBox.getSelectedItem(), (int) giornoInizioComboBox.getSelectedItem());
					String nazionale = (String) club_sponsorComboBox.getSelectedItem();
					int presenzeNazionale = 0;
					//String procuratore = (String) procuratoreComboBox.getSelectedItem();
					
					try {
						if(codiceFiscale.length()!=16) throw new LunghezzaCodiceFiscaleNonValidaException();
						if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
						for(int i = 0; i<table.getRowCount(); i++)
							if(i!=table.getSelectedRow() && codiceFiscale.equals(model.getValueAt(i, 0))) throw new DuplicatoException();
						//contratto = new Contratto(codiceFiscale, nome, cognome, dataNascita);
						String vecchioCodiceFiscale = (String) model.getValueAt(table.getSelectedRow(), 0);
						//controller.modifica(contratto, nazionale, presenzeNazionale, procuratore, vecchioCodiceFiscale);
						//ricaricaContratti();
					}
					catch (LunghezzaCodiceFiscaleNonValidaException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "Il codice fiscale deve contenere esattamente 16 caratteri", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (CodiceFiscaleNonValidoException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "Il codice fiscale non è scritto in una forma valida", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (DuplicatoException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "L'contratto " +codiceFiscale+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
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
		ordinaComboBox.addItem("Atleta");
		ordinaComboBox.addItem("Club");
		ordinaComboBox.addItem("DataInizio");
		ordinaComboBox.addItem("DataFine");;
		ordinaComboBox.addItem("Retribuzione");
		ordinaComboBox.addItem("PercentualeProcuratore");
		ordinaComboBox.addItem("GuadagnoProcuratore");
		contentPane.add(ordinaComboBox);
		
		percentualeProcuratoreTF = new JTextField();
		percentualeProcuratoreTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		percentualeProcuratoreTF.setColumns(10);
		percentualeProcuratoreTF.setBounds(160, 344, 150, 20);
		contentPane.add(percentualeProcuratoreTF);
		
		JLabel percentualeProcuratoreLabel = new JLabel("Percentuale procuratore");
		percentualeProcuratoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		percentualeProcuratoreLabel.setBounds(10, 344, 140, 19);
		contentPane.add(percentualeProcuratoreLabel);
		
		JLabel dataInizioLabel = new JLabel("Data inizio");
		dataInizioLabel.setVerticalAlignment(SwingConstants.TOP);
		dataInizioLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		dataInizioLabel.setBounds(10, 255, 140, 19);
		contentPane.add(dataInizioLabel);
		
		annoInizioComboBox = new JComboBox<Integer>();
		annoInizioComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		annoInizioComboBox.setBounds(160, 255, 59, 19);
		for(int i=1950; i<2099; i++) annoInizioComboBox.addItem(i);
		contentPane.add(annoInizioComboBox);
		
		meseInizioComboBox = new JComboBox<Integer>();
		meseInizioComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		meseInizioComboBox.setBounds(224, 255, 41, 19);
		for(int i=1; i<13; i++) meseInizioComboBox.addItem(i);
		contentPane.add(meseInizioComboBox);
		
		giornoInizioComboBox = new JComboBox<Integer>();
		giornoInizioComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		giornoInizioComboBox.setBounds(269, 255, 41, 19);
		for(int i=1; i<32; i++) giornoInizioComboBox.addItem(i);
		contentPane.add(giornoInizioComboBox);
		
		JLabel club_sponsorLabel = new JLabel("Club");
		club_sponsorLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		club_sponsorLabel.setBounds(10, 226, 140, 19);
		contentPane.add(club_sponsorLabel);
		
		club_sponsorComboBox = new JComboBox<String>();
		club_sponsorComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
		club_sponsorComboBox.setBounds(160, 228, 150, 19);
		contentPane.add(club_sponsorComboBox);
		
		JLabel dataFineLabel = new JLabel("Data fine");
		dataFineLabel.setVerticalAlignment(SwingConstants.TOP);
		dataFineLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		dataFineLabel.setBounds(10, 284, 140, 19);
		contentPane.add(dataFineLabel);
		
		annoFineComboBox = new JComboBox<Integer>();
		annoFineComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		annoFineComboBox.setBounds(160, 285, 59, 19);
		for(int i=1951; i<2100; i++) annoFineComboBox.addItem(i);
		contentPane.add(annoFineComboBox);
		
		meseFineComboBox = new JComboBox<Integer>();
		meseFineComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		meseFineComboBox.setBounds(224, 285, 41, 19);
		for(int i=1; i<13; i++) meseFineComboBox.addItem(i);
		contentPane.add(meseFineComboBox);
		
		giornoFineComboBox = new JComboBox<Integer>();
		giornoFineComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		giornoFineComboBox.setBounds(269, 285, 41, 19);
		for(int i=1; i<32; i++) giornoFineComboBox.addItem(i);
		contentPane.add(giornoFineComboBox);
		
		//GESTIONE ANNO FINE
		annoFineComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestisciDate(annoFineComboBox, meseFineComboBox, giornoFineComboBox);
			}
		});
		
		//GESTIONE MESE FINE
		meseFineComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestisciDate(annoFineComboBox, meseFineComboBox, giornoFineComboBox);
			}
		});
		
		JRadioButton clubRadioButton = new JRadioButton("Club");
		clubRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!club_sponsorLabel.getText().equals("Club")) {
					club_sponsorLabel.setText("Club");
					club_sponsorComboBox.removeAllItems();
					ordinaComboBox.setEnabled(false);
					ordinaComboBox.removeAllItems();
					ordinaComboBox.addItem("Atleta");
					ordinaComboBox.addItem("Club");
					ordinaComboBox.addItem("DataInizio");
					ordinaComboBox.addItem("DataFine");;
					ordinaComboBox.addItem("Retribuzione");
					ordinaComboBox.addItem("PercentualeProcuratore");
					ordinaComboBox.addItem("GuadagnoProcuratore");
					ricaricaContratti("Club");
					ordinaComboBox.setEnabled(true);
				}
			}
		});
		club_sponsorButtonGroup.add(clubRadioButton);
		clubRadioButton.setSelected(true);
		clubRadioButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		clubRadioButton.setBounds(430, 200, 103, 21);
		contentPane.add(clubRadioButton);
		
		JRadioButton sponsorRadioButton = new JRadioButton("Sponsor");
		sponsorRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!club_sponsorLabel.getText().equals("Sponsor")) {
					club_sponsorLabel.setText("Sponsor");
					club_sponsorComboBox.removeAllItems();
					ordinaComboBox.setEnabled(false);
					ordinaComboBox.removeAllItems();
					ordinaComboBox.addItem("Atleta");
					ordinaComboBox.addItem("Sponsor");
					ordinaComboBox.addItem("DataInizio");
					ordinaComboBox.addItem("DataFine");;
					ordinaComboBox.addItem("Retribuzione");
					ordinaComboBox.addItem("PercentualeProcuratore");
					ordinaComboBox.addItem("GuadagnoProcuratore");
					ricaricaContratti("Sponsor");
					ordinaComboBox.setEnabled(true);
				}
			}
		});
		club_sponsorButtonGroup.add(sponsorRadioButton);
		sponsorRadioButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		sponsorRadioButton.setBounds(430, 225, 103, 21);
		contentPane.add(sponsorRadioButton);
		
		ordinaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ordinaComboBox.isEnabled()) 
					ricaricaContratti(club_sponsorLabel.getText());
			}
		});
		
		//GESTIONE ANNO INIZIO
		annoInizioComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestisciDate(annoInizioComboBox, meseInizioComboBox, giornoInizioComboBox);
			}
		});
		
		//GESTIONE MESE INIZIO
		meseInizioComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestisciDate(annoInizioComboBox, meseInizioComboBox, giornoInizioComboBox);
			}
		});
		
	}
	
	public void setContratti(List<Contratto> listaContratti, List<String> listaNomiClub, List<String> listaNomiSponsor) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.addColumn("Atleta");
		model.addColumn("Club");
		model.addColumn("Data inizio");
		model.addColumn("Data fine");
		model.addColumn("Retribuzione");
		model.addColumn("Percentuale procuratore");
		model.addColumn("Guadagno procuratore");
		
		for(int i=0; i<listaContratti.size(); i++) {
			if(listaContratti.get(i).getClub()!=null) {
				model.addRow(new Object[] {
						listaContratti.get(i).getAtleta().getCodiceFiscale(),
						listaContratti.get(i).getClub().getNome(), 
						listaContratti.get(i).getDataInizio(),
						listaContratti.get(i).getDataFine(),
						listaContratti.get(i).getRetribuzione(),
						listaContratti.get(i).getPercentualeProcuratore(),
						listaContratti.get(i).getGuadagnoProcuratore()
						});
			}
			else if(listaContratti.get(i).getSponsor()!=null) {
				model.addRow(new Object[] {
						listaContratti.get(i).getAtleta().getCodiceFiscale(),
						listaContratti.get(i).getSponsor().getNome(), 
						listaContratti.get(i).getDataInizio(),
						listaContratti.get(i).getDataFine(),
						listaContratti.get(i).getRetribuzione(),
						listaContratti.get(i).getPercentualeProcuratore(),
						listaContratti.get(i).getGuadagnoProcuratore()
						});
			}
		}
		
		if(listaNomiClub!=null)
		    for(int j=0; j<listaNomiClub.size(); j++) club_sponsorComboBox.addItem(listaNomiClub.get(j));
		if(listaNomiSponsor!=null)
		   for(int j=0; j<listaNomiSponsor.size(); j++) club_sponsorComboBox.addItem(listaNomiSponsor.get(j));
	}
	
	public void ricaricaContratti(String scelta) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		table.setEnabled(false);
		model.setRowCount(0);
		model.setColumnCount(0);
		controller.setContrattiInOrdine((String) ordinaComboBox.getSelectedItem(), scelta);
		table.getColumnModel().getColumn(1).setHeaderValue(scelta);
		table.setEnabled(true);
	}
	
	public void gestisciDate(JComboBox<Integer> annoComboBox, JComboBox<Integer> meseComboBox, JComboBox<Integer> giornoComboBox) {
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
