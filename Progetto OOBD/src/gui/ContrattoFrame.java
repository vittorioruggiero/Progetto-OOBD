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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import entity.Contratto;
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
		setBounds(100, 100, 1030, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel contrattiLabel = new JLabel("Contratti");
		contrattiLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		contrattiLabel.setBounds(464, 20, 73, 14);
		contentPane.add(contrattiLabel);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContrattoFrame.this.controller.apriHomeFrame(ContrattoFrame.this);
			}
		});
		indietroButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		indietroButton.setBounds(911, 373, 93, 19);
		contentPane.add(indietroButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 994, 131);
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
		club_sponsorComboBox.setBounds(180, 228, 181, 19);
		contentPane.add(club_sponsorComboBox);
		
		JLabel retribuzioneLabel = new JLabel("Retribuzione");
		retribuzioneLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		retribuzioneLabel.setBounds(10, 315, 140, 19);
		contentPane.add(retribuzioneLabel);
		
		retribuzioneTF = new JTextField();
		retribuzioneTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		retribuzioneTF.setBounds(180, 314, 181, 20);
		contentPane.add(retribuzioneTF);
		retribuzioneTF.setColumns(10);
		
		JButton inserisciButton = new JButton("Inserisci");
		
		//GESTIONE DELL'INSERIMENTO DELLE RIGHE
		inserisciButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(atletaComboBox.getSelectedItem()!=null && retribuzioneTF.getText().length()>0 && club_sponsorComboBox.getSelectedItem()!=null
						&& annoInizioComboBox.getSelectedIndex()!=-1 && meseInizioComboBox.getSelectedIndex()!=-1 && giornoInizioComboBox.getSelectedIndex()!=-1
						&& annoFineComboBox.getSelectedIndex()!=-1 && meseFineComboBox.getSelectedIndex()!=-1 && giornoFineComboBox.getSelectedIndex()!=-1) {
					controller.inserisciContratto();
					ricaricaContratti(club_sponsorLabel.getText());
				}
			}
		});
		
		inserisciButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		inserisciButton.setBounds(371, 199, 93, 19);
		contentPane.add(inserisciButton);
		
		//GESTIONE DELLA RIMOZIONE DELLE RIGHE
		JButton rimuoviButton = new JButton("Rimuovi");
		rimuoviButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (atletaComboBox.getSelectedItem()!=null && retribuzioneTF.getText().length()>0 && club_sponsorComboBox.getSelectedItem()!=null
						&& annoInizioComboBox.getSelectedIndex()!=-1 && meseInizioComboBox.getSelectedIndex()!=-1 && giornoInizioComboBox.getSelectedIndex()!=-1
						&& annoFineComboBox.getSelectedIndex()!=-1 && meseFineComboBox.getSelectedIndex()!=-1 && giornoFineComboBox.getSelectedIndex()!=-1) {
					controller.rimuoviContratto();
					ricaricaContratti(club_sponsorLabel.getText());
				}
			}
		});
		rimuoviButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rimuoviButton.setBounds(371, 226, 93, 19);
		contentPane.add(rimuoviButton);
		
		//GESTIONE DELLA MODIFICA DELLE RIGHE
		JButton modificaButton = new JButton("Modifica");
		modificaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!=-1 && atletaComboBox.getSelectedItem()!=null && retribuzioneTF.getText().length()>0 && club_sponsorComboBox.getSelectedItem()!=null
						&& annoInizioComboBox.getSelectedIndex()!=-1 && meseInizioComboBox.getSelectedIndex()!=-1 && giornoInizioComboBox.getSelectedIndex()!=-1
						&& annoFineComboBox.getSelectedIndex()!=-1 && meseFineComboBox.getSelectedIndex()!=-1 && giornoFineComboBox.getSelectedIndex()!=-1) {
					controller.modificaContratto();
					ricaricaContratti(club_sponsorLabel.getText());
				}
			}
		});
		modificaButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		modificaButton.setBounds(371, 255, 93, 19);
		contentPane.add(modificaButton);
		
		JLabel ordinaLabel = new JLabel("Ordina per");
		ordinaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		ordinaLabel.setBounds(371, 284, 93, 19);
		contentPane.add(ordinaLabel);
		
		ordinaComboBox = new JComboBox<String>();
		ordinaComboBox.setFont(new Font("SansSerif", Font.PLAIN, 10));
		ordinaComboBox.setBounds(371, 305, 112, 19);
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
		percentualeProcuratoreTF.setBounds(180, 344, 181, 20);
		contentPane.add(percentualeProcuratoreTF);
		
		JLabel percentualeProcuratoreLabel = new JLabel("Percentuale procuratore");
		percentualeProcuratoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		percentualeProcuratoreLabel.setBounds(10, 344, 160, 19);
		contentPane.add(percentualeProcuratoreLabel);
		
		JLabel dataInizioLabel = new JLabel("Data inizio");
		dataInizioLabel.setVerticalAlignment(SwingConstants.TOP);
		dataInizioLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		dataInizioLabel.setBounds(10, 255, 140, 19);
		contentPane.add(dataInizioLabel);
		
		annoInizioComboBox = new JComboBox<Integer>();
		annoInizioComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		annoInizioComboBox.setBounds(180, 255, 79, 19);
		for(int i=1950; i<2099; i++) annoInizioComboBox.addItem(i);
		contentPane.add(annoInizioComboBox);
		
		meseInizioComboBox = new JComboBox<Integer>();
		meseInizioComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		meseInizioComboBox.setBounds(263, 255, 47, 19);
		for(int i=1; i<13; i++) meseInizioComboBox.addItem(i);
		contentPane.add(meseInizioComboBox);
		
		giornoInizioComboBox = new JComboBox<Integer>();
		giornoInizioComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		giornoInizioComboBox.setBounds(314, 255, 47, 19);
		for(int i=1; i<32; i++) giornoInizioComboBox.addItem(i);
		contentPane.add(giornoInizioComboBox);
		
		JLabel dataFineLabel = new JLabel("Data fine");
		dataFineLabel.setVerticalAlignment(SwingConstants.TOP);
		dataFineLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		dataFineLabel.setBounds(10, 284, 140, 19);
		contentPane.add(dataFineLabel);
		
		annoFineComboBox = new JComboBox<Integer>();
		annoFineComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		annoFineComboBox.setBounds(180, 285, 79, 19);
		for(int i=1951; i<2100; i++) annoFineComboBox.addItem(i);
		contentPane.add(annoFineComboBox);
		
		meseFineComboBox = new JComboBox<Integer>();
		meseFineComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		meseFineComboBox.setBounds(263, 285, 47, 19);
		for(int i=1; i<13; i++) meseFineComboBox.addItem(i);
		contentPane.add(meseFineComboBox);
		
		giornoFineComboBox = new JComboBox<Integer>();
		giornoFineComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		giornoFineComboBox.setBounds(314, 285, 47, 19);
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
					radioButtonClicked("Club");
				}
			}
		});
		club_sponsorButtonGroup.add(clubRadioButton);
		clubRadioButton.setSelected(true);
		clubRadioButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		clubRadioButton.setBounds(470, 200, 103, 21);
		contentPane.add(clubRadioButton);
		
		JRadioButton sponsorRadioButton = new JRadioButton("Sponsor");
		sponsorRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!club_sponsorLabel.getText().equals("Sponsor")) {
					club_sponsorLabel.setText("Sponsor");
					radioButtonClicked("Sponsor");
				}
			}
		});
		club_sponsorButtonGroup.add(sponsorRadioButton);
		sponsorRadioButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		sponsorRadioButton.setBounds(470, 225, 103, 21);
		contentPane.add(sponsorRadioButton);
		
		atletaComboBox = new JComboBox<String>();
		atletaComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
		atletaComboBox.setBounds(180, 200, 181, 19);
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
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		
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
			
		}
		
		if(listaCodiciFiscaliAtleti!=null)
			for(int j=0; j<listaCodiciFiscaliAtleti.size(); j++) atletaComboBox.addItem(listaCodiciFiscaliAtleti.get(j));
		if(listaNomiClub!=null) {
			club_sponsorComboBox.removeAllItems();
		    for(int j=0; j<listaNomiClub.size(); j++) club_sponsorComboBox.addItem(listaNomiClub.get(j));
		}
		if(listaNomiSponsor!=null) {
			club_sponsorComboBox.removeAllItems();
			for(int j=0; j<listaNomiSponsor.size(); j++) club_sponsorComboBox.addItem(listaNomiSponsor.get(j));
		}
	}
	
	public Contratto getContrattoFromFields() throws RetribuzioneNonValidaException, PercentualeProcuratoreNonValidaException, DurataContrattoInsufficienteException, DateIncoerentiException {
		Contratto contratto;
		String atleta = (String) atletaComboBox.getSelectedItem();
		String club_sponsor = (String) club_sponsorComboBox.getSelectedItem();
		LocalDate dataInizio = LocalDate.of((int) annoInizioComboBox.getSelectedItem(), (int) meseInizioComboBox.getSelectedItem(), (int) giornoInizioComboBox.getSelectedItem());
		LocalDate dataFine = LocalDate.of((int) annoFineComboBox.getSelectedItem(), (int) meseFineComboBox.getSelectedItem(), (int) giornoFineComboBox.getSelectedItem());
		double retribuzione = Double.valueOf(retribuzioneTF.getText());
		int percentualeProcuratore = 0;
		if(percentualeProcuratoreTF.getText().length()>0) percentualeProcuratore = Integer.valueOf(percentualeProcuratoreTF.getText());
		String scelta = (String) table.getColumnModel().getColumn(1).getHeaderValue();
		if(scelta.equals("Club")) {
			if(percentualeProcuratoreTF.getText().length()>0) contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, controller.cercaAtleta(atleta), controller.cercaClub(club_sponsor));
			else contratto = new Contratto(dataInizio, dataFine, retribuzione, controller.cercaAtleta(atleta), controller.cercaClub(club_sponsor));
		}
		else {
			if(percentualeProcuratoreTF.getText().length()>0) contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, controller.cercaAtleta(atleta), controller.cercaSponsor(club_sponsor));
			else contratto = new Contratto(dataInizio, dataFine, retribuzione, controller.cercaAtleta(atleta), controller.cercaSponsor(club_sponsor));
		}
		return contratto;
	}
	
	public Contratto getContrattoFromSelectedRow() throws RetribuzioneNonValidaException, PercentualeProcuratoreNonValidaException, DurataContrattoInsufficienteException, DateIncoerentiException {
		Contratto contratto;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String atleta = (String) model.getValueAt(table.getSelectedRow(), 0);
		String club_sponsor = (String) model.getValueAt(table.getSelectedRow(), 1);
		LocalDate dataInizio = (LocalDate) model.getValueAt(table.getSelectedRow(), 2);
		LocalDate dataFine = (LocalDate) model.getValueAt(table.getSelectedRow(), 3);
		double retribuzione = (double) model.getValueAt(table.getSelectedRow(), 4);
		int percentualeProcuratore = 0;
		if(String.valueOf(model.getValueAt(table.getSelectedRow(), 5)).length()>0) percentualeProcuratore = (int) model.getValueAt(table.getSelectedRow(), 5);
		String scelta = (String) table.getColumnModel().getColumn(1).getHeaderValue();
		if(scelta.equals("Club")) {
			if(String.valueOf(model.getValueAt(table.getSelectedRow(), 5)).length()>0) contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, controller.cercaAtleta(atleta), controller.cercaClub(club_sponsor));
			else contratto = new Contratto(dataInizio, dataFine, retribuzione, controller.cercaAtleta(atleta), controller.cercaClub(club_sponsor));
		}
		else {
			if(String.valueOf(model.getValueAt(table.getSelectedRow(), 5)).length()>0) contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, controller.cercaAtleta(atleta), controller.cercaSponsor(club_sponsor));
			else contratto = new Contratto(dataInizio, dataFine, retribuzione, controller.cercaAtleta(atleta), controller.cercaSponsor(club_sponsor));
		}
		return contratto;
	}
	
	public void controllaIntervalloDate() throws IntervalloDateOccupatoException {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		String atleta = (String) atletaComboBox.getSelectedItem();
		LocalDate dataInizio = LocalDate.of((int) annoInizioComboBox.getSelectedItem(), (int) meseInizioComboBox.getSelectedItem(), (int) giornoInizioComboBox.getSelectedItem());
		LocalDate dataFine = LocalDate.of((int) annoFineComboBox.getSelectedItem(), (int) meseFineComboBox.getSelectedItem(), (int) giornoFineComboBox.getSelectedItem());
		String scelta = (String) table.getColumnModel().getColumn(1).getHeaderValue();
		
		if (scelta.equals("Club")) {
			for (int i = 0; i < table.getRowCount(); i++)
				if (atleta.equals(model.getValueAt(i, 0))
						&& !(
								(dataInizio.isBefore((LocalDate) model.getValueAt(i, 2)) && dataFine.isBefore((LocalDate) model.getValueAt(i, 2)))
						     || (dataInizio.isAfter((LocalDate) model.getValueAt(i, 3)) && dataFine.isAfter((LocalDate) model.getValueAt(i, 3)))
						     )
						)
					throw new IntervalloDateOccupatoException();
		}
		else {
			String sponsor = (String) club_sponsorComboBox.getSelectedItem();
			for (int i = 0; i < table.getRowCount(); i++)
				if (atleta.equals(model.getValueAt(i, 0)) && sponsor.equals(model.getValueAt(i, 1))
						&& !(
								(dataInizio.isBefore((LocalDate) model.getValueAt(i, 2)) && dataFine.isBefore((LocalDate) model.getValueAt(i, 2)))
						     || (dataInizio.isAfter((LocalDate) model.getValueAt(i, 3)) && dataFine.isAfter((LocalDate) model.getValueAt(i, 3)))
						     )
						)
					throw new IntervalloDateOccupatoException();
		}
	}
	
	public void controllaIntervalloDateModifica() throws IntervalloDateOccupatoException {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		String atleta = (String) atletaComboBox.getSelectedItem();
		LocalDate dataInizio = LocalDate.of((int) annoInizioComboBox.getSelectedItem(), (int) meseInizioComboBox.getSelectedItem(), (int) giornoInizioComboBox.getSelectedItem());
		LocalDate dataFine = LocalDate.of((int) annoFineComboBox.getSelectedItem(), (int) meseFineComboBox.getSelectedItem(), (int) giornoFineComboBox.getSelectedItem());
		String scelta = (String) table.getColumnModel().getColumn(1).getHeaderValue();
		
		if (scelta.equals("Club")) {
			for (int i = 0; i < table.getRowCount(); i++)
				if (i!=table.getSelectedRow() && atleta.equals(model.getValueAt(i, 0))
						&& !(
								(dataInizio.isBefore((LocalDate) model.getValueAt(i, 2)) && dataFine.isBefore((LocalDate) model.getValueAt(i, 2)))
						     || (dataInizio.isAfter((LocalDate) model.getValueAt(i, 3)) && dataFine.isAfter((LocalDate) model.getValueAt(i, 3)))
						     )
						)
					throw new IntervalloDateOccupatoException();
		}
		else {
			String sponsor = (String) club_sponsorComboBox.getSelectedItem();
			for (int i = 0; i < table.getRowCount(); i++)
				if (i!=table.getSelectedRow() && atleta.equals(model.getValueAt(i, 0)) && sponsor.equals(model.getValueAt(i, 1))
						&& !(
								(dataInizio.isBefore((LocalDate) model.getValueAt(i, 2)) && dataFine.isBefore((LocalDate) model.getValueAt(i, 2)))
						     || (dataInizio.isAfter((LocalDate) model.getValueAt(i, 3)) && dataFine.isAfter((LocalDate) model.getValueAt(i, 3)))
						     )
						)
					throw new IntervalloDateOccupatoException();
		}
	}
	
	public void setFields() {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
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
	
	public void ricaricaContratti(String scelta) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		table.setEnabled(false);
		model.setRowCount(0);
		model.setColumnCount(0);
		if(scelta.equals("RadioClub")) {
			controller.setContrattiInOrdine((String) ordinaComboBox.getSelectedItem(), "Prendi Club");
			scelta = "Club";
			}
		else if(scelta.equals("RadioSponsor")) {
			controller.setContrattiInOrdine((String) ordinaComboBox.getSelectedItem(), "Prendi Sponsor");
			scelta = "Sponsor";
			}
		else controller.setContrattiInOrdine((String) ordinaComboBox.getSelectedItem(), scelta);
		table.getColumnModel().getColumn(1).setHeaderValue(scelta);
		table.setEnabled(true);
	}
	
	public void radioButtonClicked(String bottonePremuto) {
		club_sponsorComboBox.removeAllItems();
		ordinaComboBox.setEnabled(false);
		ordinaComboBox.removeAllItems();
		ordinaComboBox.addItem("Atleta");
		ordinaComboBox.addItem(bottonePremuto);
		ordinaComboBox.addItem("DataInizio");
		ordinaComboBox.addItem("DataFine");;
		ordinaComboBox.addItem("Retribuzione");
		ordinaComboBox.addItem("PercentualeProcuratore");
		ordinaComboBox.addItem("GuadagnoProcuratore");
		if(bottonePremuto.equals("Club")) ricaricaContratti("RadioClub");
		else ricaricaContratti("RadioSponsor");
		ordinaComboBox.setEnabled(true);
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
