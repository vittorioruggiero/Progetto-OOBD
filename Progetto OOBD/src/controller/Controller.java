package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;

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
import entity.Atleta;
import entity.Club;
import entity.Contratto;
import entity.Nazionale;
import entity.Procuratore;
import entity.Sponsor;

import java.util.List;

public class Controller {
	private HomeFrame homeFrame;
	private NazionaleFrame nazionaleFrame;
	private ProcuratoreFrame procuratoreFrame;
	private AtletaFrame atletaFrame;
	private List<Nazionale> listaNazionali;
	private List<Procuratore> listaProcuratori;
	private List<Atleta> listaAtleti;
	private List<Club> listaClub;
	private List<Sponsor> listaSponsor;
	private List<Contratto> listaContratti;
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
		}
		catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
        }
		this.homeFrame = new HomeFrame(this);
		this.homeFrame.setVisible(true);
	}
	
	public void apriHomeFrame(JFrame frame) {
		frame.setVisible(false);
		homeFrame.setVisible(true);
	}
	
	public void apriNazionaleFrame() {
		try {
			nazionaleDAO = new NazionaleDAOPostgresImpl(connection);
			listaNazionali = nazionaleDAO.getAllNazionali("nome");
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		nazionaleFrame = new NazionaleFrame(this);
		nazionaleFrame.setNazionali(listaNazionali);
		homeFrame.setVisible(false);
		nazionaleFrame.setVisible(true);
	}
	
	public void apriProcuratoreFrame() {
		try {
			procuratoreDAO = new ProcuratoreDAOPostgresImpl(connection);
			listaProcuratori = procuratoreDAO.getAllProcuratori("codiceFiscale");
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		procuratoreFrame = new ProcuratoreFrame(this);
		procuratoreFrame.setProcuratori(listaProcuratori);
		homeFrame.setVisible(false);
		procuratoreFrame.setVisible(true);
	}
	
	public void apriAtletaFrame() {
		try {
			atletaDAO = new AtletaDAOPostgresImpl(connection, this);
			listaAtleti = atletaDAO.getAllAtleti("codiceFiscale");
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		atletaFrame = new AtletaFrame(this);
		atletaFrame.setAtleti(listaAtleti);
		homeFrame.setVisible(false);
		atletaFrame.setVisible(true);
	}
	
	public void inserisci(Nazionale nazionale) {
		nazionaleDAO.insertNazionale(nazionale);
	}
	
	public void inserisci(Procuratore procuratore) {
		procuratoreDAO.insertProcuratore(procuratore);
	}
	
	public void inserisci(Atleta atleta, String nazionale, int presenzeNazionale, String procuratore) {
		atletaDAO.insertAtleta(atleta, nazionale, presenzeNazionale, procuratore);
	}
	
	public void inserisci(Club club) {
		clubDAO.insertClub(club);
	}
	
	public void inserisci(Sponsor sponsor) {
		sponsorDAO.insertSponsor(sponsor);
	}
	
	public void inserisci(Contratto contratto) {
		contrattoDAO.insertContratto(contratto);
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
	
	public void modifica(Contratto contratto, Contratto vecchioContrtato) {
		contrattoDAO.updateContratto(contratto, vecchioContrtato);
	}
	
	public void setNazionaliInOrdine(String nomeColonna) {
		listaNazionali = nazionaleDAO.getAllNazionali(nomeColonna);
		nazionaleFrame.setNazionali(listaNazionali);
	}
	
	public void setProcuratoriInOrdine(String nomeColonna) {
		listaProcuratori = procuratoreDAO.getAllProcuratori(nomeColonna);
		procuratoreFrame.setProcuratori(listaProcuratori);
	}
	
	public void setAtletiInOrdine(String nomeColonna) {
		listaAtleti = atletaDAO.getAllAtleti(nomeColonna);
		atletaFrame.setAtleti(listaAtleti);
	}
	
//	public void setClubInOrdine(String nomeColonna) {
//		listaClub = clubDAO.getAllClub(nomeColonna);
//		clubFrame.setClub(listaClub);
//	}
	
//	public void setSponsorInOrdine(String nomeColonna) {
//		listaSponsor = sponsorDAO.getAllsponsor(nomeColonna);
//		sponsorFrame.setSponsor(listaSponsor);
//	}
	
//	public void setContrattiInOrdine(String nomeColonna, String scelta) {
//		listaContratti = contrattoDAO.getAllContratti(nomeColonna, scelta);
//		contrattoFrame.setCotratti(listaContratti);
//	}
	
	public Procuratore cercaProcuratore(String codiceFiscaleCercato) throws SQLException {
		return new ProcuratoreDAOPostgresImpl(connection).getProcuratore(codiceFiscaleCercato);
	}
	
	public Atleta cercaAtleta(String codiceFiscaleCercato) throws SQLException {
		return new AtletaDAOPostgresImpl(connection, this).getAtleta(codiceFiscaleCercato);
	}
	
	public Nazionale cercaNazionale(String nomeCercato) throws SQLException {
		return new NazionaleDAOPostgresImpl(connection).getNazionale(nomeCercato);
	}
	
	public Club cercaClub(String nomeCercato) throws SQLException {
		return new ClubDAOPostgresImpl(connection).getClub(nomeCercato);
	}
	
	public Sponsor cercaSponsor(String nomeCercato) throws SQLException {
		return new SponsorDAOPostgresImpl(connection).getSponsor(nomeCercato);
	}
}
