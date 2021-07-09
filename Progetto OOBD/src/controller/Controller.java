package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;

import daoImpl.AtletaDAOPostgresImpl;
import daoImpl.ClubDAOPostgresImpl;
import daoImpl.NazionaleDAOPostgresImpl;
import daoImpl.ProcuratoreDAOPostgresImpl;
import daoImpl.SponsorDAOPostgresImpl;
import dbConfig.DBConnection;
import gui.HomeFrame;
import gui.NazionaleFrame;
import entity.Atleta;
import entity.Club;
import entity.Nazionale;
import entity.Procuratore;
import entity.Sponsor;

import java.util.List;

public class Controller {
	private HomeFrame homeFrame;
	private NazionaleFrame nazionaleFrame;
	private List<Nazionale> listaNazionali;
	private List<Procuratore> listaProcuratori;
	private List<Atleta> listaAtleti;
	private List<Club> listaClub;
	private List<Sponsor> listaSponsor;
	private NazionaleDAOPostgresImpl nazionaleDAO;
	private ProcuratoreDAOPostgresImpl procuratoreDAO;
	private AtletaDAOPostgresImpl atletaDAO;
	private ClubDAOPostgresImpl clubDAO;
	private SponsorDAOPostgresImpl sponsorDAO;
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
	
	public void setNazionaliInOrdine(String nomeColonna) {
		listaNazionali = nazionaleDAO.getAllNazionali(nomeColonna);
		nazionaleFrame.setNazionali(listaNazionali);
	}
	
//	public void setProcuratoriInOrdine(String codiceFiscale) {
//		listaProcuratori = procuratoreDAO.getAllProcuratori(codiceFiscale);
//		procuratoreFrame.setProcuratori(listaProcuratori);
//	}
	
//	public void setAtletiInOrdine(String codiceFiscale) {
//		listaAtleti = atletaDAO.getAllAtleti(codiceFiscale);
//		atletaFrame.setAtleti(listaAtleti);
//	}
	
//	public void setClubInOrdine(String nome) {
//		listaClub = clubDAO.getAllClub(nome);
//		clubFrame.setClub(listaClub);
//	}
	
//	public void setSponsorInOrdine(String nome) {
//		listaSponsor = sponsorDAO.getAllsponsor(nome);
//		sponsorFrame.setSponsor(listaSponsor);
//	}
	
	public Procuratore cercaProcuratore(String codiceFiscaleCercato) {
		return procuratoreDAO.getProcuratore(codiceFiscaleCercato);
	}
	
	public Nazionale cercaNazionale(String nome) {
		return nazionaleDAO.getNazionale(nome);
	}
}
