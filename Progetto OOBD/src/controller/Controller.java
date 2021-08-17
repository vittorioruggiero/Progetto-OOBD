package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import daoImpl.AtletaDAOPostgresImpl;
import daoImpl.ClubDAOPostgresImpl;
import daoImpl.ContrattoDAOPostgresImpl;
import daoImpl.NazionaleDAOPostgresImpl;
import daoImpl.ProcuratoreDAOPostgresImpl;
import daoImpl.SponsorDAOPostgresImpl;
import dbConfig.DBConnection;
import gui.AtletaFrame;
import gui.HomeFrame;
import gui.NazionaleFrame;
import gui.ProcuratoreFrame;
import gui.ProcuratoreMaxGuadagniFrame;
import gui.SorgentiIntroitoFrame;
import gui.ClubFrame;
import gui.ContrattoFrame;
import gui.SponsorFrame;
import entity.Atleta;
import entity.Club;
import entity.Contratto;
import entity.Nazionale;
import entity.Procuratore;
import entity.Sponsor;
import exception.CodiceFiscaleNonValidoException;
import exception.CodiciFiscaliUgualiException;
import exception.DateIncoerentiException;
import exception.DuplicatoException;
import exception.DurataContrattoInsufficienteException;
import exception.GettoneNonValidoException;
import exception.IncoerenzaAssociazioneProcuratoreException;
import exception.IntervalloDateOccupatoException;
import exception.PercentualeProcuratoreNonValidaException;
import exception.PresenzeNazionaleNonValideException;
import exception.RetribuzioneNonValidaException;

import java.util.ArrayList;
import java.util.List;

public class Controller {
	private HomeFrame homeFrame;
	private NazionaleFrame nazionaleFrame;
	private ProcuratoreFrame procuratoreFrame;
	private AtletaFrame atletaFrame;
	private ClubFrame clubFrame;
	private SponsorFrame sponsorFrame;
	private ContrattoFrame contrattoFrame;
	private SorgentiIntroitoFrame sorgentiIntroitoFrame;
	private ProcuratoreMaxGuadagniFrame procuratoreMaxGuadagniFrame;
	private List<Nazionale> listaNazionali;
	private List<Procuratore> listaProcuratori;
	private List<Atleta> listaAtleti;
	private List<Club> listaClub;
	private List<Sponsor> listaSponsor;
	private List<Contratto> listaContratti;
	private ArrayList<ArrayList<Object>> listaSorgentiIntroito;
	private ArrayList<ArrayList<Object>> listaProcuratoriMaxGuadagni;
	private NazionaleDAOPostgresImpl nazionaleDAO;
	private ProcuratoreDAOPostgresImpl procuratoreDAO;
	private AtletaDAOPostgresImpl atletaDAO;
	private ClubDAOPostgresImpl clubDAO;
	private SponsorDAOPostgresImpl sponsorDAO;
	private ContrattoDAOPostgresImpl contrattoDAO;
	DBConnection dbconn;
	Connection connection;
	
	public static void main(String[] args) {
		Controller controller = new Controller();
	}
	
	public Controller() {
		try {
            this.dbconn = DBConnection.getInstance();
            this.connection = this.dbconn.getConnection();
            this.homeFrame = new HomeFrame(this);
    		this.homeFrame.setVisible(true);
		}
		catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
        }
	}
	
	public void apriHomeFrame(JFrame frame) {
		frame.setVisible(false);
		homeFrame.setVisible(true);
	}
	
	public void apriNazionaleFrame() {
		try {
			nazionaleDAO = new NazionaleDAOPostgresImpl(connection);
			listaNazionali = nazionaleDAO.getAllNazionali("nome");
		    nazionaleFrame = new NazionaleFrame(this);
		    nazionaleFrame.setNazionali(listaNazionali);
		    homeFrame.setVisible(false);
		    nazionaleFrame.setVisible(true);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		} catch (GettoneNonValidoException exception) {
			exception.printStackTrace();
		}
	}
	
	public void apriClubFrame() {
		try {
			clubDAO = new ClubDAOPostgresImpl(connection);
			listaClub = clubDAO.getAllClub("nome");
			clubFrame = new ClubFrame(this);
			clubFrame.setClub(listaClub);
			homeFrame.setVisible(false);
			clubFrame.setVisible(true);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
	}
	
	public void apriSponsorFrame() {
		try {
			sponsorDAO = new SponsorDAOPostgresImpl(connection);
			listaSponsor = sponsorDAO.getAllsponsor("nome");
			sponsorFrame = new SponsorFrame(this);
			sponsorFrame.setSponsor(listaSponsor);
			homeFrame.setVisible(false);
			sponsorFrame.setVisible(true);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
	}
	
	public void apriProcuratoreFrame() {
		try {
			procuratoreDAO = new ProcuratoreDAOPostgresImpl(connection);
			listaProcuratori = procuratoreDAO.getAllProcuratori("codiceFiscale");
			procuratoreFrame = new ProcuratoreFrame(this);
			procuratoreFrame.setProcuratori(listaProcuratori);
			homeFrame.setVisible(false);
			procuratoreFrame.setVisible(true);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		} 
		catch (CodiceFiscaleNonValidoException exception) {
			exception.printStackTrace();
		}
	}
	
	public void apriAtletaFrame() {
		List<String> listaNomiNazionali = new ArrayList<String>();
		List<String> listaCodiciFiscaliProcuratori = new ArrayList<String>();
		try {
			atletaDAO = new AtletaDAOPostgresImpl(connection, this);
			nazionaleDAO = new NazionaleDAOPostgresImpl(connection);
			procuratoreDAO = new ProcuratoreDAOPostgresImpl(connection);
			
			listaAtleti = atletaDAO.getAllAtleti("codiceFiscale");
			listaNomiNazionali = nazionaleDAO.getNomiNazionali();
			listaCodiciFiscaliProcuratori = procuratoreDAO.getCodiciFiscaliProcuratori();
			
			atletaFrame = new AtletaFrame(this);
			atletaFrame.setAtleti(listaAtleti, listaNomiNazionali, listaCodiciFiscaliProcuratori);
			homeFrame.setVisible(false);
			atletaFrame.setVisible(true);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		} catch (CodiceFiscaleNonValidoException e) {
			e.printStackTrace();
		} catch (PresenzeNazionaleNonValideException e) {
			e.printStackTrace();
		} catch (CodiciFiscaliUgualiException e) {
			e.printStackTrace();
		}
	}
	
	public void apriContrattoFrame() {
		List<String> listaNomiClub = new ArrayList<String>();
		List<String> listaCodiciFiscaliAtleti = new ArrayList<String>();
		try {
			contrattoDAO = new ContrattoDAOPostgresImpl(connection, this);
		    atletaDAO = new AtletaDAOPostgresImpl(connection, this);
			clubDAO = new ClubDAOPostgresImpl(connection);
			
			listaContratti = contrattoDAO.getAllContratti("Atleta", "Club");
			listaCodiciFiscaliAtleti = atletaDAO.getCodiciFiscaliAtleti();
			listaNomiClub = clubDAO.getNomiClub();
			
			contrattoFrame = new ContrattoFrame(this);
			contrattoFrame.setContratti(listaContratti, listaCodiciFiscaliAtleti, listaNomiClub, null);
			homeFrame.setVisible(false);
			contrattoFrame.setVisible(true);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		} catch (RetribuzioneNonValidaException e) {
			e.printStackTrace();
		} catch (PercentualeProcuratoreNonValidaException e) {
			e.printStackTrace();
		} catch (DurataContrattoInsufficienteException e) {
			e.printStackTrace();
		} catch (DateIncoerentiException e) {
			e.printStackTrace();
		}
	}
	
	public void apriSorgentiIntroitoFrame() {
		listaSorgentiIntroito = atletaDAO.getSorgentiIntroito("CodiceFiscale");
		sorgentiIntroitoFrame = new SorgentiIntroitoFrame(this);
		sorgentiIntroitoFrame.setSorgentiIntroito(listaSorgentiIntroito);
		atletaFrame.setVisible(false);
		sorgentiIntroitoFrame.setVisible(true);
	}
	
	public void apriProcuratoreMaxGuadagniFrame() {
		listaProcuratoriMaxGuadagni = procuratoreDAO.getProcuratoriMaxGuadagni("Procuratore");
		procuratoreMaxGuadagniFrame = new ProcuratoreMaxGuadagniFrame(this);
		procuratoreMaxGuadagniFrame.setProcuratoreMaxGuadagni(listaProcuratoriMaxGuadagni);
		procuratoreFrame.setVisible(false);
		procuratoreMaxGuadagniFrame.setVisible(true);
	}
	
	public void riapriAtletaFrame() {
		sorgentiIntroitoFrame.setVisible(false);
		atletaFrame.setVisible(true);
	}
	
	public void riapriProcuratoreFrame() {
		procuratoreMaxGuadagniFrame.setVisible(false);
		procuratoreFrame.setVisible(true);
	}
	
	public void inserisciNazionale() {
		Nazionale nazionale = null;
		try {
			nazionale = nazionaleFrame.getNazionaleFromFields();
			nazionaleFrame.controllaDuplicato();
			nazionaleDAO.insertNazionale(nazionale);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(nazionaleFrame, "Il valore del gettone deve essere un numero", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (GettoneNonValidoException exception) {
			JOptionPane.showMessageDialog(nazionaleFrame, "Il valore del gettone deve essere maggiore di 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DuplicatoException exception) {
			JOptionPane.showMessageDialog(nazionaleFrame, "La nazionale " +nazionale.getNome()+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void inserisciProcuratore() {
		Procuratore procuratore = null;
		try {
			procuratore = procuratoreFrame.getProcuratoreFromFields();
			procuratoreFrame.controllaDuplicato();
			procuratoreDAO.insertProcuratore(procuratore);
		}
		catch (CodiceFiscaleNonValidoException exception) {
			JOptionPane.showMessageDialog(procuratoreFrame, "Il codice fiscale non è valido", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DuplicatoException exception) {
			JOptionPane.showMessageDialog(procuratoreFrame, "Il procuratore " +procuratore.getCodiceFiscale()+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void inserisciAtleta() {
		Atleta atleta = null;
		try {
			atleta = atletaFrame.getAtletaFromFields();
			atletaFrame.controllaDuplicato();
			atletaDAO.insertAtleta(atleta);
		}
		catch (CodiceFiscaleNonValidoException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il codice fiscale non è valido", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DuplicatoException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "L'atleta " +atleta.getCodiceFiscale()+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (PresenzeNazionaleNonValideException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il numero di presenze in nazionale deve essere maggiore o uguale a 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il valore delle presenze in nazionale deve essere un numero intero", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (CodiciFiscaliUgualiException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il codice fiscale del procuratore deve essere diverso da quello dell'atleta", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void inserisciClub() {
		Club club = null;
		try {
			club = clubFrame.getClubFromFields();
			clubFrame.controllaDuplicato();
			clubDAO.insertClub(club);
		}
		catch (DuplicatoException exception) {
			JOptionPane.showMessageDialog(clubFrame, "Il club " + club.getNome() + " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void inserisciSponsor() {
		Sponsor sponsor = null;
		try {
			sponsor = sponsorFrame.getSponsorFromFields();
			sponsorFrame.controllaDuplicato();
			sponsorDAO.insertSponsor(sponsor);
		}
		catch (DuplicatoException exception) {
			JOptionPane.showMessageDialog(sponsorFrame, "Lo sponsor " +sponsor.getNome()+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void inserisciContratto() {
		Contratto contratto = null;
		try {
			contratto = contrattoFrame.getContrattoFromFields();
			contrattoFrame.controllaIntervalloDate();
			contrattoDAO.insertContratto(contratto);
		}
		catch (IncoerenzaAssociazioneProcuratoreException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La percentuale del procuratore deve essere presente se l'atleta è associato ad esso, non presente altrimenti", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DateIncoerentiException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La data di inizio deve essere precedente alla data di fine", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DurataContrattoInsufficienteException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La data finale deve essere distante almeno un anno da quella iniziale", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (IntervalloDateOccupatoException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "Un atleta può avere un solo contratto con club in un intervallo di date e un solo contratto con uno stesso sponsor in un intervallo di date", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "Il valore della retribuzione deve essere un numero.\nIl valore della percentuale del procuratore deve essere un numero intero", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (RetribuzioneNonValidaException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "Il valore della retribuzione deve essere maggiore di 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (PercentualeProcuratoreNonValidaException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La percentuale del procuratore deve essere maggiore di 0 e minore di 100", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void rimuoviNazionale() {
		try {
			Nazionale nazionale = nazionaleFrame.getNazionaleFromFields();
			nazionaleDAO.deleteNazionale(nazionale);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(nazionaleFrame, "Il valore del gettone deve essere un numero", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (GettoneNonValidoException exception) {
			JOptionPane.showMessageDialog(nazionaleFrame, "Il valore del gettone deve essere maggiore di 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void rimuoviProcuratore() {
		try {
			Procuratore procuratore = procuratoreFrame.getProcuratoreFromFields();
			procuratoreDAO.deleteProcuratore(procuratore);
		}
		catch (CodiceFiscaleNonValidoException exception) {
			JOptionPane.showMessageDialog(procuratoreFrame, "Il codice fiscale non è valido", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void rimuoviAtleta() {
		try {
			Atleta atleta = atletaFrame.getAtletaFromFields();
			atletaDAO.deleteAtleta(atleta);
		}
		catch (CodiceFiscaleNonValidoException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il codice fiscale non è valido", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il valore delle presenze in nazionale deve essere un numero intero", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (PresenzeNazionaleNonValideException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il numero di presenze in nazionale deve essere maggiore o uguale a 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (CodiciFiscaliUgualiException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il codice fiscale del procuratore deve essere diverso da quello dell'atleta", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void rimuoviClub() {
		Club club = clubFrame.getClubFromFields();
		clubDAO.deleteClub(club);
	}
	
	public void rimuoviSponsor() {
		Sponsor sponsor = sponsorFrame.getSponsorFromFields();
		sponsorDAO.deleteSponsor(sponsor);
	}
	
	public void rimuoviContratto() {
		try {
			Contratto contratto = contrattoFrame.getContrattoFromFields();
			contrattoDAO.deleteContratto(contratto);
		}
		catch (DateIncoerentiException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La data di inizio deve essere precedente alla data di fine", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DurataContrattoInsufficienteException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La data finale deve essere distante almeno un anno da quella iniziale", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "Il valore della retribuzione deve essere un numero.\nIl valore della percentuale del procuratore deve essere un numero intero", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (RetribuzioneNonValidaException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "Il valore della retribuzione deve essere maggiore di 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (PercentualeProcuratoreNonValidaException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La percentuale del procuratore deve essere maggiore di 0 e minore di 100", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void modificaNazionale() {
		Nazionale nazionale = null;
		try {
			nazionale = nazionaleFrame.getNazionaleFromFields();
			nazionaleFrame.controllaDuplicatoModifica();
			String vecchioNome = nazionaleFrame.getNazionaleFromSelectedRow().getNome();
			nazionaleDAO.updateNazionale(nazionale, vecchioNome);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(nazionaleFrame, "Il valore del gettone deve essere un numero", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (GettoneNonValidoException exception) {
			JOptionPane.showMessageDialog(nazionaleFrame, "Il valore del gettone deve essere maggiore di 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DuplicatoException exception) {
			JOptionPane.showMessageDialog(nazionaleFrame, "La nazionale " +nazionale.getNome()+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void modificaProcuratore() {
		Procuratore procuratore = null;
		try {
			procuratore = procuratoreFrame.getProcuratoreFromFields();
			procuratoreFrame.controllaDuplicatoModifica();
			String vecchioCodiceFiscale = procuratoreFrame.getProcuratoreFromSelectedRow().getCodiceFiscale();
			procuratoreDAO.updateProcuratore(procuratore, vecchioCodiceFiscale);
		}
		catch (CodiceFiscaleNonValidoException exception) {
			JOptionPane.showMessageDialog(procuratoreFrame, "Il codice fiscale non è valido", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DuplicatoException exception) {
			JOptionPane.showMessageDialog(procuratoreFrame, "Il procuratore " +procuratore.getCodiceFiscale()+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void modificaAtleta() {
		Atleta atleta = null;
		try {
			atleta = atletaFrame.getAtletaFromFields();
			atletaFrame.controllaDuplicatoModifica();
			String vecchioCodiceFiscale = atletaFrame.getAtletaFromSelectedRow().getCodiceFiscale();
			atletaDAO.updateAtleta(atleta, vecchioCodiceFiscale);
		}
		catch (CodiceFiscaleNonValidoException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il codice fiscale non è valido", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DuplicatoException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "L'atleta " +atleta.getCodiceFiscale()+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (PresenzeNazionaleNonValideException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il numero di presenze in nazionale deve essere maggiore o uguale a 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il valore delle presenze in nazionale deve essere un numero intero", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (CodiciFiscaliUgualiException exception) {
			JOptionPane.showMessageDialog(atletaFrame, "Il codice fiscale del procuratore deve essere diverso da quello dell'atleta", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void modificaClub() {
		Club club = null;
		try {
			club = clubFrame.getClubFromFields();
			clubFrame.controllaDuplicatoModifica();
			String vecchioNome = clubFrame.getClubFromSelectedRow().getNome();
			clubDAO.updateClub(club, vecchioNome);
		} catch (DuplicatoException exception) {
			JOptionPane.showMessageDialog(clubFrame, "Il club " + club.getNome() + " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void modificaSponsor() {
		Sponsor sponsor = null;
		try {
			sponsor = sponsorFrame.getSponsorFromFields();
			sponsorFrame.controllaDuplicatoModifica();
			String vecchioNome = sponsorFrame.getSponsorFromSelectedRow().getNome();
			sponsorDAO.updateSponsor(sponsor, vecchioNome);
		}
		catch (DuplicatoException exception) {
			JOptionPane.showMessageDialog(sponsorFrame, "Lo sponsor " +sponsor.getNome()+ " è già presente", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void modificaContratto() {
		Contratto contratto = null;
		try {
			contratto = contrattoFrame.getContrattoFromFields();
			contrattoFrame.controllaIntervalloDateModifica();
			Contratto vecchioContratto = contrattoFrame.getContrattoFromSelectedRow();
			contrattoDAO.updateContratto(contratto, vecchioContratto);
		}
		catch (IncoerenzaAssociazioneProcuratoreException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La percentuale del procuratore deve essere presente se l'atleta è associato ad esso, non presente altrimenti", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DateIncoerentiException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La data di inizio deve essere precedente alla data di fine", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (DurataContrattoInsufficienteException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La data finale deve essere distante almeno un anno da quella iniziale", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (IntervalloDateOccupatoException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "Un atleta può avere un solo contratto con club in un intervallo di date e un solo contratto con uno stesso sponsor in un intervallo di date", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "Il valore della retribuzione deve essere un numero.\nIl valore della percentuale del procuratore deve essere un numero intero", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (RetribuzioneNonValidaException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "Il valore della retribuzione deve essere maggiore di 0", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
		catch (PercentualeProcuratoreNonValidaException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La percentuale del procuratore deve essere maggiore di 0 e minore di 100", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setNazionaliInOrdine(String nomeColonna) {
		try {
			listaNazionali = nazionaleDAO.getAllNazionali(nomeColonna);
			nazionaleFrame.setNazionali(listaNazionali);
		} catch (GettoneNonValidoException exception) {
			exception.printStackTrace();
		}
	}
	
	public void setProcuratoriInOrdine(String nomeColonna) {
		try {
			listaProcuratori = procuratoreDAO.getAllProcuratori(nomeColonna);
			procuratoreFrame.setProcuratori(listaProcuratori);
		} 
		catch (CodiceFiscaleNonValidoException exception) {
			exception.printStackTrace();
		}
	}
	
	public void setAtletiInOrdine(String nomeColonna) {
		try {
			listaAtleti = atletaDAO.getAllAtleti(nomeColonna);
			atletaFrame.setAtleti(listaAtleti, null, null);
		} catch (CodiceFiscaleNonValidoException e) {
			e.printStackTrace();
		} catch (PresenzeNazionaleNonValideException e) {
			e.printStackTrace();
		} catch (CodiciFiscaliUgualiException e) {
			e.printStackTrace();
		}
	}
	
	public void setClubInOrdine(String nomeColonna) {
		listaClub = clubDAO.getAllClub(nomeColonna);
		clubFrame.setClub(listaClub);
	}
	
	public void setSponsorInOrdine(String nomeColonna) {
		listaSponsor = sponsorDAO.getAllsponsor(nomeColonna);
		sponsorFrame.setSponsor(listaSponsor);
	}
	
	public void setContrattiInOrdine(String nomeColonna, String scelta) {
		List<String> listaNomiClub = null;
		List<String> listaNomiSponsor = null;
		try {
			if(scelta.equals("Prendi Club")) { 
				listaNomiClub = new ClubDAOPostgresImpl(connection).getNomiClub();
				scelta = "Club";
				}
			else if(scelta.equals("Prendi Sponsor")) { 
				listaNomiSponsor = new SponsorDAOPostgresImpl(connection).getNomiSponsor();
				scelta = "Sponsor";
				}
			listaContratti = contrattoDAO.getAllContratti(nomeColonna, scelta);
			contrattoFrame.setContratti(listaContratti, null, listaNomiClub, listaNomiSponsor);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		} catch (RetribuzioneNonValidaException e) {
			e.printStackTrace();
		} catch (PercentualeProcuratoreNonValidaException e) {
			e.printStackTrace();
		} catch (DurataContrattoInsufficienteException e) {
			e.printStackTrace();
		} catch (DateIncoerentiException e) {
			e.printStackTrace();
		}
	}
	
	public void setSorgentiIntroitoInOrdine(String nomeColonna) {
		listaSorgentiIntroito = atletaDAO.getSorgentiIntroito(nomeColonna);
		sorgentiIntroitoFrame.setSorgentiIntroito(listaSorgentiIntroito);
	}
	
	public void setProcuratoriMaxGuadagniInOrdine(String nomeColonna) {
		listaProcuratoriMaxGuadagni = procuratoreDAO.getProcuratoriMaxGuadagni(nomeColonna);
		procuratoreMaxGuadagniFrame.setProcuratoreMaxGuadagni(listaProcuratoriMaxGuadagni);
	}
	
	public Procuratore cercaProcuratore(String codiceFiscaleCercato) {
		Procuratore procuratore = null;
		try {
			procuratore = new ProcuratoreDAOPostgresImpl(connection).getProcuratore(codiceFiscaleCercato);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		catch (CodiceFiscaleNonValidoException exception) {
			exception.printStackTrace();
		}
		return procuratore;
	}
	
	public Atleta cercaAtleta(String codiceFiscaleCercato) {
		Atleta atleta = null;
		try {
			atleta = new AtletaDAOPostgresImpl(connection, this).getAtleta(codiceFiscaleCercato);
		} catch (CodiceFiscaleNonValidoException e) {
			e.printStackTrace();
		} catch (PresenzeNazionaleNonValideException e) {
			e.printStackTrace();
		} catch (CodiciFiscaliUgualiException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		return atleta;
	}
	
	public Nazionale cercaNazionale(String nomeCercato) {
		Nazionale nazionale = null;
		try {
			nazionale = new NazionaleDAOPostgresImpl(connection).getNazionale(nomeCercato);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		catch (GettoneNonValidoException exception) {
			exception.printStackTrace();
		}
		return nazionale;
	}
	
	public Club cercaClub(String nomeCercato) {
		Club club = null;
		try {
		club = new ClubDAOPostgresImpl(connection).getClub(nomeCercato);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return club;
	}
	
	public Sponsor cercaSponsor(String nomeCercato) {
		Sponsor sponsor = null;
		try {
		sponsor = new SponsorDAOPostgresImpl(connection).getSponsor(nomeCercato);
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return sponsor;
	}
}
