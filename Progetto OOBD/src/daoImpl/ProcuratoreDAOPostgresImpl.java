package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.ProcuratoreDAO;
import entity.Procuratore;

public class ProcuratoreDAOPostgresImpl implements ProcuratoreDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement insertProcuratorePS;
	private PreparedStatement deleteProcuratorePS;
	private PreparedStatement updateProcuratorePS;
	
	public ProcuratoreDAOPostgresImpl(Connection connection) throws SQLException {
		this.connection = connection;
		insertProcuratorePS = connection.prepareStatement("INSERT INTO Procuratore VALUES (?, ?, ?, ?)");
		deleteProcuratorePS = connection.prepareStatement("DELETE FROM Procuratore WHERE codiceFiscale = ? AND nome = ? AND cognome = ? AND dataNascita = ?");
		updateProcuratorePS = connection.prepareStatement("UPDATE Procuratore SET codiceFiscale = ?, nome = ?, cognome = ?, dataNascita = ? WHERE codiceFiscale = ?");
	}
	
	@Override
	public List<Procuratore> getAllProcuratori(String nomeColonna) {
		List<Procuratore> listaProcuratori = new ArrayList<Procuratore>();
		try {
			this.statement = this.connection.createStatement();
			if(nomeColonna.equals("DataNascita")) nomeColonna = nomeColonna.concat(" DESC");
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Procuratore ORDER BY " + nomeColonna);
			
			while(resultSet.next()) {
				String codiceFiscale = resultSet.getString("codiceFiscale");
				String nome = resultSet.getString("nome");
				String cognome = resultSet.getString("cognome");
				LocalDate dataNascita = resultSet.getDate("dataNascita").toLocalDate();
				Procuratore Procuratore = new Procuratore(codiceFiscale, nome, cognome, dataNascita);
				listaProcuratori.add(Procuratore);
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return listaProcuratori;
	}
	
	@Override
	public Procuratore getProcuratore(String codiceFiscaleCercato) {
		Procuratore procuratore = null;
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Procuratore WHERE codiceFiscale = '" + codiceFiscaleCercato +"'");
			if(resultSet.next()) {
				String codiceFiscale = resultSet.getString("codiceFiscale");
				String nome = resultSet.getString("nome");
				String cognome = resultSet.getString("cognome");
				LocalDate dataNascita = resultSet.getDate("dataNascita").toLocalDate();
				procuratore = new Procuratore(codiceFiscale, nome, cognome, dataNascita);
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return procuratore;
	}
	
	@Override
	public List<String> getCodiciFiscaliProcuratori() {
		List<String> listaCodiciFiscaliProcuratori = new ArrayList<String>();
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet = this.statement.executeQuery("SELECT codicefiscale FROM procuratore ORDER BY codicefiscale");
			
			while (resultSet.next()) listaCodiciFiscaliProcuratori.add(resultSet.getString("codicefiscale"));
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return listaCodiciFiscaliProcuratori;
	}
	
	@Override
	public void insertProcuratore(Procuratore procuratore) {
		try {
			insertProcuratorePS.setString(1, procuratore.getCodiceFiscale());
			insertProcuratorePS.setString(2, procuratore.getNome());
			insertProcuratorePS.setString(3, procuratore.getCognome());
			insertProcuratorePS.setObject(4, procuratore.getDataNascita());
			insertProcuratorePS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void deleteProcuratore(Procuratore procuratore) {
		try {
			deleteProcuratorePS.setString(1, procuratore.getCodiceFiscale());
			deleteProcuratorePS.setString(2, procuratore.getNome());
			deleteProcuratorePS.setString(3, procuratore.getCognome());
			deleteProcuratorePS.setObject(4, procuratore.getDataNascita());
			deleteProcuratorePS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void updateProcuratore(Procuratore nuovoProcuratore, String vecchioCodiceFiscale) {
		try {
			updateProcuratorePS.setString(1, nuovoProcuratore.getCodiceFiscale());
			updateProcuratorePS.setString(2, nuovoProcuratore.getNome());
			updateProcuratorePS.setString(3, nuovoProcuratore.getCognome());
			updateProcuratorePS.setObject(4, nuovoProcuratore.getDataNascita());
			updateProcuratorePS.setString(5, vecchioCodiceFiscale);
			updateProcuratorePS.executeUpdate();
		}
		catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
        }
	}
}
