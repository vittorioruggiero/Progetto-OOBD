package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;

import daoImpl.NazionaleDAOPostgresImpl;
import daoImpl.ProcuratoreDAOPostgresImpl;
import dbConfig.DBConnection;
import gui.HomeFrame;
import gui.NazionaleFrame;
import entity.Nazionale;
import entity.Procuratore;

import java.util.List;

public class Controller {
	private HomeFrame homeFrame;
	private NazionaleFrame nazionaleFrame;
	private List<Nazionale> listaNazionali;
	private NazionaleDAOPostgresImpl nazionaleDAO;
	private ProcuratoreDAOPostgresImpl procuratoreDAO;
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
	
	public void rimuovi(Nazionale nazionale) {
		nazionaleDAO.deleteNazionale(nazionale);
	}
	
	public void rimuovi(Procuratore procuratore) {
		procuratoreDAO.deleteProcuratore(procuratore);
	}
	
	public void modifica(Nazionale nazionale, String vecchioNome) {
		nazionaleDAO.updateNazionale(nazionale, vecchioNome);
	}
	
	public void modifica(Procuratore procuratore, String vecchioCodiceFiscale) {
		procuratoreDAO.updateProcuratore(procuratore, vecchioCodiceFiscale);
	}
	
	public void setInOrdine(String nomeColonna) {
		listaNazionali = nazionaleDAO.getAllNazionali(nomeColonna);
		nazionaleFrame.setNazionali(listaNazionali);
	}
}
