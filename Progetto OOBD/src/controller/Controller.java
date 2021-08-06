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
import exception.GettoneNonValidoException;
import exception.IncoerenzaAssociazioneProcuratoreException;
import exception.PresenzeNazionaleNonValideException;

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
	
	public void inserisci(Nazionale nazionale) {
		nazionaleDAO.insertNazionale(nazionale);
	}
	
	public void inserisci(Procuratore procuratore) {
		procuratoreDAO.insertProcuratore(procuratore);
	}
	
	public void inserisci(Atleta atleta) {
		atletaDAO.insertAtleta(atleta);
	}
	
	public void inserisci(Club club) {
		clubDAO.insertClub(club);
	}
	
	public void inserisci(Sponsor sponsor) {
		sponsorDAO.insertSponsor(sponsor);
	}
	
	public void inserisci(Contratto contratto) {
		try {
			contrattoDAO.insertContratto(contratto);
		} catch (IncoerenzaAssociazioneProcuratoreException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La percentuale del procuratore deve essere presente se l'atleta è associato ad esso, non presente altrimenti", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void rimuovi(Nazionale nazionale) {
		nazionaleDAO.deleteNazionale(nazionale);
	}
	
	public void rimuovi(Procuratore procuratore) {
		procuratoreDAO.deleteProcuratore(procuratore);
	}
	
	public void rimuovi(Atleta atleta) {
		atletaDAO.deleteAtleta(atleta);
	}
	
	public void rimuovi(Club club) {
		clubDAO.deleteClub(club);
	}
	
	public void rimuovi(Sponsor sponsor) {
		sponsorDAO.deleteSponsor(sponsor);
	}
	
	public void rimuovi(Contratto contratto) {
		contrattoDAO.deleteContratto(contratto);
	}
	
	public void modifica(Nazionale nazionale, String vecchioNome) {
		nazionaleDAO.updateNazionale(nazionale, vecchioNome);
	}
	
	public void modifica(Procuratore procuratore, String vecchioCodiceFiscale) {
		procuratoreDAO.updateProcuratore(procuratore, vecchioCodiceFiscale);
	}
	
	public void modifica(Atleta atleta, String vecchioCodiceFiscale) {
		atletaDAO.updateAtleta(atleta, vecchioCodiceFiscale);
	}
	
	public void modifica(Club club, String vecchioNome) {
		clubDAO.updateClub(club, vecchioNome);
	}
	
	public void modifica(Sponsor sponsor, String vecchioNome) {
		sponsorDAO.updateSponsor(sponsor, vecchioNome);
	}
	
	public void modifica(Contratto contratto, Contratto vecchioContratto) {
		try {
			contrattoDAO.updateContratto(contratto, vecchioContratto);
		} catch (IncoerenzaAssociazioneProcuratoreException exception) {
			JOptionPane.showMessageDialog(contrattoFrame, "La percentuale del procuratore deve essere presente se l'atleta è associato ad esso, non presente altrimenti", "ATTENZIONE", JOptionPane.ERROR_MESSAGE);
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
