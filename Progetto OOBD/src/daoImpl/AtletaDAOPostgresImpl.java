package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Atleta;
import entity.Nazionale;
import entity.Procuratore;
import controller.Controller;
import dao.AtletaDAO;

public class AtletaDAOPostgresImpl implements AtletaDAO {
	private Connection connection;
	private Controller controller;
	private Statement statement;
	private PreparedStatement insertAtletaPS;
	private PreparedStatement deleteAtletaPS;
	private PreparedStatement updateAtletaPS;
	
	public AtletaDAOPostgresImpl(Connection connection, Controller controller) throws SQLException {
		this.connection = connection;
		this.controller = controller;
		insertAtletaPS = connection.prepareStatement("INSERT INTO Atleta VALUES (?, ?, ?, ?, ?, ?, ?)");
		updateAtletaPS = connection.prepareStatement("UPDATE Atleta SET codiceFiscale = ?, nome = ?, cognome = ?, dataNascita = ?, nazionale = ?, presenzeNazionale = ?, procuratore = ? WHERE codiceFiscale = ?");
	}
	
	@Override
	public List<Atleta> getAllAtleti(String nomeColonna) {
		List<Atleta> listaAtleti = new ArrayList<Atleta>();
		try {
			this.statement = this.connection.createStatement();
			if(nomeColonna.equals("DataNascita") || nomeColonna.equals("PresenzeNazionale")) nomeColonna = nomeColonna.concat(" DESC");
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Atleta ORDER BY " + nomeColonna + " NULLS LAST");
			
			while(resultSet.next()) {
				Atleta atleta;
				String codiceFiscale = resultSet.getString("codiceFiscale");
				String nome = resultSet.getString("nome");
				String cognome = resultSet.getString("cognome");
				LocalDate dataNascita = resultSet.getDate("dataNascita").toLocalDate();
				Procuratore procuratore;
				if (resultSet.getString("procuratore")!=null)
					procuratore = controller.cercaProcuratore(resultSet.getString("procuratore"));
				else
					procuratore = null;
				Nazionale nazionale;
				if (resultSet.getString("nazionale")!=null) {
					nazionale = controller.cercaNazionale(resultSet.getString("nazionale"));
					int presenzeNazionale = resultSet.getInt("presenzeNazionale");
					atleta = new Atleta(codiceFiscale, nome, cognome, dataNascita, presenzeNazionale, procuratore, nazionale);
				}
				else {
					nazionale = null;
					atleta = new Atleta(codiceFiscale, nome, cognome, dataNascita, procuratore);
				}
				listaAtleti.add(atleta);
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return listaAtleti;
	}
	
	@Override
	public Atleta getAtleta(String codiceFiscaleCercato) {
		Atleta atleta = null;
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Atleta WHERE codiceFiscale = '" + codiceFiscaleCercato + "'");
			
			if(resultSet.next()) {
				String codiceFiscale = resultSet.getString("codiceFiscale");
				String nome = resultSet.getString("nome");
				String cognome = resultSet.getString("cognome");
				LocalDate dataNascita = resultSet.getDate("dataNascita").toLocalDate();
				Procuratore procuratore;
				if (resultSet.getString("procuratore")!=null)
					procuratore = controller.cercaProcuratore(resultSet.getString("procuratore"));
				else
					procuratore = null;
				Nazionale nazionale;
				if (resultSet.getString("nazionale")!=null) {
					nazionale = controller.cercaNazionale(resultSet.getString("nazionale"));
					int presenzeNazionale = resultSet.getInt("presenzeNazionale");
					atleta = new Atleta(codiceFiscale, nome, cognome, dataNascita, presenzeNazionale, procuratore, nazionale);
				}
				else {
					nazionale = null;
					atleta = new Atleta(codiceFiscale, nome, cognome, dataNascita, procuratore);
				}
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return atleta;
	}
	
	public List<String> getCodiciFiscaliAtleti() {
		List<String> listaCodiciFiscaliAtleti = new ArrayList<String>();
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet = this.statement.executeQuery("SELECT codicefiscale FROM atleta ORDER BY codicefiscale");
			
			while (resultSet.next()) listaCodiciFiscaliAtleti.add(resultSet.getString("codicefiscale"));
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return listaCodiciFiscaliAtleti;
	}
	
	@Override
	public void insertAtleta(Atleta atleta, String nazionale, int presenzeNazionale, String procuratore) {
		try {
			insertAtletaPS.setString(1, atleta.getCodiceFiscale());
			insertAtletaPS.setString(2, atleta.getNome());
			insertAtletaPS.setString(3, atleta.getCognome());
			insertAtletaPS.setObject(4, atleta.getDataNascita());
			if (nazionale.length()>0) {
				insertAtletaPS.setInt(5, presenzeNazionale);
				insertAtletaPS.setString(6, nazionale);
			}
			else {
				insertAtletaPS.setNull(5, java.sql.Types.INTEGER);
				insertAtletaPS.setNull(6, java.sql.Types.VARCHAR);
			}
			if (procuratore.length()>0) 
				insertAtletaPS.setString(7, procuratore);
			else
				insertAtletaPS.setNull(7, java.sql.Types.CHAR);
			insertAtletaPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void deleteAtleta(Atleta atleta, String nazionale, int presenzeNazionale, String procuratore) {
		String deleteString = "DELETE FROM Atleta WHERE codiceFiscale = ? AND nome = ? AND cognome = ? AND dataNascita = ?";
		if(nazionale.length()>0) deleteString = deleteString.concat(" AND nazionale = ? AND presenzeNazionale = ?");
		else deleteString = deleteString.concat(" AND nazionale is null AND presenzeNazionale is null");
		if(procuratore.length()>0) deleteString = deleteString.concat(" AND procuratore = ?");
		else deleteString = deleteString.concat(" AND procuratore is null");
		try {
			deleteAtletaPS = connection.prepareStatement(deleteString);
			deleteAtletaPS.setString(1, atleta.getCodiceFiscale());
			deleteAtletaPS.setString(2, atleta.getNome());
			deleteAtletaPS.setString(3, atleta.getCognome());
			deleteAtletaPS.setObject(4, atleta.getDataNascita());
			if (nazionale.length()>0) {
				deleteAtletaPS.setString(5, nazionale);
				deleteAtletaPS.setInt(6, presenzeNazionale);
				if(procuratore.length()>0) deleteAtletaPS.setString(7, procuratore);
			}
			else if(procuratore.length()>0) deleteAtletaPS.setString(5, procuratore);
			deleteAtletaPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void updateAtleta(Atleta nuovoAtleta, String nazionale, int presenzeNazionale, String procuratore, String vecchioCodiceFiscale) {
		try {
			updateAtletaPS.setString(1, nuovoAtleta.getCodiceFiscale());
			updateAtletaPS.setString(2, nuovoAtleta.getNome());
			updateAtletaPS.setString(3, nuovoAtleta.getCognome());
			updateAtletaPS.setObject(4, nuovoAtleta.getDataNascita());
			if (nazionale.length()>0) {
				updateAtletaPS.setString(5, nazionale);
				updateAtletaPS.setInt(6, presenzeNazionale);
			}
			else {
				updateAtletaPS.setNull(5, java.sql.Types.VARCHAR);
				updateAtletaPS.setNull(6, java.sql.Types.INTEGER);
			}
			if (procuratore.length()>0) 
				updateAtletaPS.setString(7, procuratore);
			else
				updateAtletaPS.setNull(7, java.sql.Types.CHAR);
			updateAtletaPS.setString(8, vecchioCodiceFiscale);
			updateAtletaPS.executeUpdate();
		}
		catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
        }
	}
	
	@Override
	public ArrayList<ArrayList<Object>> getSorgentiIntroito(String nomeColonna) {
		ArrayList<ArrayList<Object>> listaSorgentiIntroito = new ArrayList<ArrayList<Object>>();
		try {
			this.statement = this.connection.createStatement();
			if(!nomeColonna.equals("CodiceFiscale")) nomeColonna = nomeColonna.concat(" DESC");
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM atleta_sorgenti_introito ORDER BY " + nomeColonna + " NULLS LAST");
			
			while(resultSet.next()) {
				String codiceFiscale = resultSet.getString("codiceFiscale");
				double guadagniDaNazionale = resultSet.getDouble("guadagnidanazionale");
				double guadagniDaClub = resultSet.getDouble("guadagnidaclub");
				double guadagniDaSponsor = resultSet.getDouble("guadagnidasponsor");
				ArrayList<Object> lista = new ArrayList<Object>();
				lista.add(codiceFiscale);
				lista.add(guadagniDaNazionale);
				lista.add(guadagniDaClub);
				lista.add(guadagniDaSponsor);
				listaSorgentiIntroito.add(lista);
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return listaSorgentiIntroito;
	}
}
