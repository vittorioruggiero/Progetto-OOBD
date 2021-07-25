package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
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
import entity.Club;
import entity.Contratto;
import entity.Sponsor;
import exception.DurataContrattoInsufficienteException;
import exception.DateIncoerentiException;
import exception.IntervalloDateOccupatoException;
import exception.PercentualeProcuratoreNonValidaException;
import exception.RetribuzioneNonValidaException;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class ContrattoFrame extends JFrame {

	private Controller controller;
	private JPanel contentPane;
	private JTable table;
	private JTextField retribuzioneTF;
	private JComboBox<String> ordinaComboBox;
	private JTextField percentualeProcuratoreTF;
	JComboBox<Integer> annoInizioComboBox;
	JComboBox<Integer> meseInizioComboBox;
	JComboBox<Integer> giornoInizioComboBox;
	JComboBox<Integer> annoFineComboBox;
	JComboBox<Integer> meseFineComboBox;
	JComboBox<Integer> giornoFineComboBox;
	JComboBox<String> atletaComboBox;
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
				atletaComboBox.setSelectedItem((String) model.getValueAt(table.getSelectedRow(), 0));
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
		
		JLabel club_sponsorLabel = new JLabel("Club");
		club_sponsorLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		club_sponsorLabel.setBounds(10, 226, 140, 19);
		contentPane.add(club_sponsorLabel);
		
		club_sponsorComboBox = new JComboBox<String>();
		club_sponsorComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
		club_sponsorComboBox.setBounds(160, 228, 150, 19);
		contentPane.add(club_sponsorComboBox);
		
		JLabel retribuzioneLabel = new JLabel("Retribuzione");
		retribuzioneLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		retribuzioneLabel.setBounds(10, 315, 140, 19);
		contentPane.add(retribuzioneLabel);
		
		retribuzioneTF = new JTextField();
		retribuzioneTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		retribuzioneTF.setBounds(160, 314, 150, 20);
		contentPane.add(retribuzioneTF);
		retribuzioneTF.setColumns(10);
		
		JButton inserisciButton = new JButton("Inserisci");
		
		//GESTIONE DELL'INSERIMENTO DELLE RIGHE
		inserisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(atletaComboBox.getSelectedItem()!=null && retribuzioneTF.getText().length()>0 && club_sponsorComboBox.getSelectedItem()!=null
						&& annoInizioComboBox.getSelectedIndex()!=-1 && meseInizioComboBox.getSelectedIndex()!=-1 && giornoInizioComboBox.getSelectedIndex()!=-1
						&& annoFineComboBox.getSelectedIndex()!=-1 && meseFineComboBox.getSelectedIndex()!=-1 && giornoFineComboBox.getSelectedIndex()!=-1) {
					Contratto contratto;
					String atleta = (String) atletaComboBox.getSelectedItem();
					String club_sponsor = (String) club_sponsorComboBox.getSelectedItem();
					LocalDate dataInizio = LocalDate.of((int) annoInizioComboBox.getSelectedItem(), (int) meseInizioComboBox.getSelectedItem(), (int) giornoInizioComboBox.getSelectedItem());
					LocalDate dataFine = LocalDate.of((int) annoFineComboBox.getSelectedItem(), (int) meseFineComboBox.getSelectedItem(), (int) giornoFineComboBox.getSelectedItem());
					double retribuzione = Double.valueOf(retribuzioneTF.getText());
					int percentualeProcuratore = 0;
					if(percentualeProcuratoreTF.getText().length()>0) percentualeProcuratore = Integer.valueOf(percentualeProcuratoreTF.getText());
					String scelta = club_sponsorLabel.getText();
					
					try {
						if(dataInizio.isAfter(dataFine)) throw new DateIncoerentiException();
						if(ChronoUnit.YEARS.between(dataInizio, dataFine)<1) throw new DurataContrattoInsufficienteException();
						if (scelta.equals("Club"))
							for (int i = 0; i < table.getRowCount(); i++)
								if (atleta.equals(model.getValueAt(i, 0))
										&& !(
												(dataInizio.isBefore((LocalDate) model.getValueAt(i, 2)) && dataFine.isBefore((LocalDate) model.getValueAt(i, 2)))
										     || (dataInizio.isAfter((LocalDate) model.getValueAt(i, 3)) && dataFine.isAfter((LocalDate) model.getValueAt(i, 3)))
										     )
										)
									throw new IntervalloDateOccupatoException();
						if(retribuzione<=0) throw new RetribuzioneNonValidaException();
						if(percentualeProcuratoreTF.getText().length()>0 && (percentualeProcuratore<=0 || percentualeProcuratore>=100)) throw new PercentualeProcuratoreNonValidaException();
						if(scelta.equals("Club")) contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, new Atleta(atleta, null, null, null), new Club(club_sponsor, null));
						else contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, new Atleta(atleta, null, null, null), new Sponsor(club_sponsor, null));
						controller.inserisci(contratto);
						ricaricaContratti(scelta);
					}
					catch (DateIncoerentiException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "La data di inizio deve essere precedente alla data di fine", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (DurataContrattoInsufficienteException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "La data finale deve essere distante almeno un anno da quella iniziale", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (IntervalloDateOccupatoException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "Un atleta può avere un solo contratto con club in un intervallo di date", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (RetribuzioneNonValidaException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "Il valore della retribuzione deve essere maggiore di 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (PercentualeProcuratoreNonValidaException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "La percentuale del procuratore deve essere maggiore di 0 e minore di 100", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
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
				if (atletaComboBox.getSelectedItem()!=null && retribuzioneTF.getText().length()>0 && club_sponsorComboBox.getSelectedItem()!=null
						&& annoInizioComboBox.getSelectedIndex()!=-1 && meseInizioComboBox.getSelectedIndex()!=-1 && giornoInizioComboBox.getSelectedIndex()!=-1
						&& annoFineComboBox.getSelectedIndex()!=-1 && meseFineComboBox.getSelectedIndex()!=-1 && giornoFineComboBox.getSelectedIndex()!=-1) {
					String atleta = (String) atletaComboBox.getSelectedItem();
					String club_sponsor = (String) club_sponsorComboBox.getSelectedItem();
					LocalDate dataInizio = LocalDate.of((int) annoInizioComboBox.getSelectedItem(), (int) meseInizioComboBox.getSelectedItem(), (int) giornoInizioComboBox.getSelectedItem());
					LocalDate dataFine = LocalDate.of((int) annoFineComboBox.getSelectedItem(), (int) meseFineComboBox.getSelectedItem(), (int) giornoFineComboBox.getSelectedItem());
					double retribuzione = Double.valueOf(retribuzioneTF.getText());
					int percentualeProcuratore = 0;
					if(percentualeProcuratoreTF.getText().length()>0) percentualeProcuratore = Integer.valueOf(percentualeProcuratoreTF.getText());
					Contratto contratto;
					String scelta = club_sponsorLabel.getText();
					if(scelta.equals("Club")) contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, new Atleta(atleta, null, null, null), new Club(club_sponsor, null));
					else contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, new Atleta(atleta, null, null, null), new Sponsor(club_sponsor, null));
					controller.rimuovi(contratto);
					ricaricaContratti(scelta);
				}
			}
		});
		rimuoviButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rimuoviButton.setBounds(331, 226, 93, 19);
		contentPane.add(rimuoviButton);
		
		//GESTIONE DELLA MODIFICA DELLE RIGHE
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1 && atletaComboBox.getSelectedItem()!=null && retribuzioneTF.getText().length()>0 && club_sponsorComboBox.getSelectedItem()!=null
						&& annoInizioComboBox.getSelectedIndex()!=-1 && meseInizioComboBox.getSelectedIndex()!=-1 && giornoInizioComboBox.getSelectedIndex()!=-1
						&& annoFineComboBox.getSelectedIndex()!=-1 && meseFineComboBox.getSelectedIndex()!=-1 && giornoFineComboBox.getSelectedIndex()!=-1) {
					Contratto nuovoContratto;
					Contratto vecchioContratto;
					String atleta = (String) atletaComboBox.getSelectedItem();
					String club_sponsor = (String) club_sponsorComboBox.getSelectedItem();
					LocalDate dataInizio = LocalDate.of((int) annoInizioComboBox.getSelectedItem(), (int) meseInizioComboBox.getSelectedItem(), (int) giornoInizioComboBox.getSelectedItem());
					LocalDate dataFine = LocalDate.of((int) annoFineComboBox.getSelectedItem(), (int) meseFineComboBox.getSelectedItem(), (int) giornoFineComboBox.getSelectedItem());
					double retribuzione = Double.valueOf(retribuzioneTF.getText());
					int percentualeProcuratore = 0;
					if(percentualeProcuratoreTF.getText().length()>0) percentualeProcuratore = Integer.valueOf(percentualeProcuratoreTF.getText());
					String vecchioAtleta = (String) model.getValueAt(table.getSelectedRow(), 0);
					String vecchioClub_Sponsor = (String) model.getValueAt(table.getSelectedRow(), 1);
					LocalDate vecchiaDataInizio = (LocalDate) model.getValueAt(table.getSelectedRow(), 2);
					LocalDate vecchiaDataFine = (LocalDate) model.getValueAt(table.getSelectedRow(), 3);
					double vecchiaRetribuzione = (double) model.getValueAt(table.getSelectedRow(), 4);
					int vecchiaPercentualeProcuratore = 0;
					if(!model.getValueAt(table.getSelectedRow(), 5).equals("")) vecchiaPercentualeProcuratore= (int) model.getValueAt(table.getSelectedRow(), 5);
					String scelta = club_sponsorLabel.getText();
					
					try {
						if(dataInizio.isAfter(dataFine)) throw new DateIncoerentiException();
						if(ChronoUnit.YEARS.between(dataInizio, dataFine)<1) throw new DurataContrattoInsufficienteException();
						if (scelta.equals("Club"))
							for (int i = 0; i < table.getRowCount(); i++)
								if (i!=table.getSelectedRow() && atleta.equals(model.getValueAt(i, 0))
										&& !(
												(dataInizio.isBefore((LocalDate) model.getValueAt(i, 2)) && dataFine.isBefore((LocalDate) model.getValueAt(i, 2)))
										     || (dataInizio.isAfter((LocalDate) model.getValueAt(i, 3)) && dataFine.isAfter((LocalDate) model.getValueAt(i, 3)))
										     )
										)
									throw new IntervalloDateOccupatoException();
						if(retribuzione<=0) throw new RetribuzioneNonValidaException();
						if(percentualeProcuratoreTF.getText().length()>0 && (percentualeProcuratore<=0 || percentualeProcuratore>=100)) throw new PercentualeProcuratoreNonValidaException();
						if(scelta.equals("Club")) {
							nuovoContratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, new Atleta(atleta, null, null, null), new Club(club_sponsor, null));
							vecchioContratto = new Contratto(vecchiaDataInizio, vecchiaDataFine, vecchiaRetribuzione, vecchiaPercentualeProcuratore, new Atleta(vecchioAtleta, null, null, null), new Club(vecchioClub_Sponsor, null));
						}
						else {
							nuovoContratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, new Atleta(atleta, null, null, null), new Sponsor(club_sponsor, null));
							vecchioContratto = new Contratto(vecchiaDataInizio, vecchiaDataFine, vecchiaRetribuzione, vecchiaPercentualeProcuratore, new Atleta(vecchioAtleta, null, null, null), new Sponsor(vecchioClub_Sponsor, null));
						}
						controller.modifica(nuovoContratto, vecchioContratto);
						ricaricaContratti(scelta);
					}
					catch (DateIncoerentiException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "La data di inizio deve essere precedente alla data di fine", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (DurataContrattoInsufficienteException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "La data finale deve essere distante almeno un anno da quella iniziale", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (IntervalloDateOccupatoException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "Un atleta può avere un solo contratto con club in un intervallo di date", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (RetribuzioneNonValidaException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "Il valore della retribuzione deve essere maggiore di 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
					}
					catch (PercentualeProcuratoreNonValidaException exception) {
						JOptionPane.showMessageDialog(ContrattoFrame.this, "La percentuale del procuratore deve essere maggiore di 0 e minore di 100", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
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
					ricaricaContratti("Prendi Club");
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
					ricaricaContratti("Prendi Sponsor");
					ordinaComboBox.setEnabled(true);
				}
			}
		});
		club_sponsorButtonGroup.add(sponsorRadioButton);
		sponsorRadioButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		sponsorRadioButton.setBounds(430, 225, 103, 21);
		contentPane.add(sponsorRadioButton);
		
		atletaComboBox = new JComboBox<String>();
		atletaComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
		atletaComboBox.setBounds(160, 200, 150, 19);
		contentPane.add(atletaComboBox);
		
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
	
	public void setContratti(List<Contratto> listaContratti, List<String> listaCodiciFiscaliAtleti, List<String> listaNomiClub, List<String> listaNomiSponsor) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.addColumn("Atleta");
		model.addColumn("Club");
		model.addColumn("Data inizio");
		model.addColumn("Data fine");
		model.addColumn("Retribuzione");
		model.addColumn("Percentuale procuratore");
		model.addColumn("Guadagno procuratore");
		
		for(int i=0; i<listaContratti.size(); i++) {
			String atleta = listaContratti.get(i).getAtleta().getCodiceFiscale();
			String club_sponsor;
			if(listaContratti.get(i).getClub()!=null) club_sponsor = listaContratti.get(i).getClub().getNome();
			else club_sponsor = listaContratti.get(i).getSponsor().getNome();
			LocalDate dataInizio = listaContratti.get(i).getDataInizio();
			LocalDate dataFine = listaContratti.get(i).getDataFine();
			double retribuzione = listaContratti.get(i).getRetribuzione();
			
			if(listaContratti.get(i).getAtleta().getProcuratore()!=null)
				model.addRow(new Object[] {atleta,
						club_sponsor,
						dataInizio,
						dataFine,
						retribuzione,
						listaContratti.get(i).getPercentualeProcuratore(),
						listaContratti.get(i).getGuadagnoProcuratore()
						});
			else
				model.addRow(new Object[] {atleta,
						club_sponsor,
						dataInizio,
						dataFine,
						retribuzione,
						"",
						""
						});
			
//			if(listaContratti.get(i).getClub()!=null) {
//				model.addRow(new Object[] {
//						listaContratti.get(i).getAtleta().getCodiceFiscale(),
//						listaContratti.get(i).getClub().getNome(), 
//						listaContratti.get(i).getDataInizio(),
//						listaContratti.get(i).getDataFine(),
//						listaContratti.get(i).getRetribuzione(),
//						listaContratti.get(i).getPercentualeProcuratore(),
//						listaContratti.get(i).getGuadagnoProcuratore()
//						});
//			}
//			else if(listaContratti.get(i).getSponsor()!=null) {
//				model.addRow(new Object[] {
//						listaContratti.get(i).getAtleta().getCodiceFiscale(),
//						listaContratti.get(i).getSponsor().getNome(), 
//						listaContratti.get(i).getDataInizio(),
//						listaContratti.get(i).getDataFine(),
//						listaContratti.get(i).getRetribuzione(),
//						listaContratti.get(i).getPercentualeProcuratore(),
//						listaContratti.get(i).getGuadagnoProcuratore()
//						});
//			}
		}
		
		if(listaCodiciFiscaliAtleti!=null)
			for(int j=0; j<listaCodiciFiscaliAtleti.size(); j++) atletaComboBox.addItem(listaCodiciFiscaliAtleti.get(j));
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
