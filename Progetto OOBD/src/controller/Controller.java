package controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;

import daoImpl.nazionaleDAOPostgresImpl;
import dbConfig.DBConnection;
import gui.HomeFrame;
import gui.NazionaleFrame;
import entity.Nazionale;

import java.util.ArrayList;
import java.util.List;

public class Controller {
	private HomeFrame homeFrame;
	private NazionaleFrame nazionaleFrame;
	private List<Nazionale> listaNazionali;
	private nazionaleDAOPostgresImpl nazionaleDAO;
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
			nazionaleDAO = new nazionaleDAOPostgresImpl(connection);
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
	
	public void inserisciNazionale(Nazionale nazionale) {
		nazionaleDAO.insertNazionale(nazionale);
	}
	
	public void rimuoviNazionali(Nazionale nazionale) {
		nazionaleDAO.deleteNazionale(nazionale);
	}
	
	public void modificaNazionale(Nazionale nazionale, String vecchioNome) {
		nazionaleDAO.updateNazionale(nazionale, vecchioNome);
	}
	
	public void setNazionaliOrdinate(String nomeColonna) {
		listaNazionali = nazionaleDAO.getAllNazionali(nomeColonna);
		nazionaleFrame.setNazionali(listaNazionali);
	}
}
