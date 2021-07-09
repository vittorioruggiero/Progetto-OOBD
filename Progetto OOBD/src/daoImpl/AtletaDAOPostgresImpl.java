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
		deleteAtletaPS = connection.prepareStatement("DELETE FROM Atleta WHERE codiceFiscale = ? AND nome = ? AND cognome = ? AND dataNascita = ? AND nazionale = ? AND presenzeNazionale = ? AND codicefiscaleprocuratore = ?");
		updateAtletaPS = connection.prepareStatement("UPDATE Atleta SET codiceFiscale = ?, nome = ?, cognome = ?, dataNascita = ?, nazionale = ?, presenzeNazionale = ?, codicefiscaleprocuratore = ? WHERE codiceFiscale = ?");
	}
	
	@Override
	public List<Atleta> getAllAtleti(String nomeColonna) {
		List<Atleta> listaAtleti = new ArrayList<Atleta>();
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Atleta ORDER BY " + nomeColonna);
			
			while(resultSet.next()) {
				Atleta atleta;
				String codiceFiscale = resultSet.getString("codiceFiscale");
				String nome = resultSet.getString("nome");
				String cognome = resultSet.getString("cognome");
				LocalDate dataNascita = resultSet.getDate("dataNascita").toLocalDate();
				Procuratore procuratore;
				if (resultSet.getString("codicefiscaleprocuratore")!=null)
					procuratore = controller.cercaProcuratore(resultSet.getString("codicefiscaleprocuratore"));
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
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Atleta WHERE codiceFiscale = " + codiceFiscaleCercato);
			
			if(resultSet.next()) {
				String codiceFiscale = resultSet.getString("codiceFiscale");
				String nome = resultSet.getString("nome");
				String cognome = resultSet.getString("cognome");
				LocalDate dataNascita = resultSet.getDate("dataNascita").toLocalDate();
				Procuratore procuratore;
				if (resultSet.getString("codicefiscaleprocuratore")!=null)
					procuratore = controller.cercaProcuratore(resultSet.getString("codicefiscaleprocuratore"));
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
	
	@Override
	public void insertAtleta(Atleta atleta) {
		try {
			insertAtletaPS.setString(1, atleta.getCodiceFiscale());
			insertAtletaPS.setString(2, atleta.getNome());
			insertAtletaPS.setString(3, atleta.getCognome());
			insertAtletaPS.setObject(4, atleta.getDataNascita());
			if (atleta.getNazionale()!=null) {
				insertAtletaPS.setString(5, atleta.getNazionale().getNome());
				insertAtletaPS.setInt(6, atleta.getPresenzeNazionale());
			}
			else {
				insertAtletaPS.setNull(5, java.sql.Types.VARCHAR);
				insertAtletaPS.setNull(6, java.sql.Types.INTEGER);
			}
			if (atleta.getProcuratore()!=null) 
				insertAtletaPS.setString(7, atleta.getProcuratore().getCodiceFiscale());
			else
				insertAtletaPS.setNull(7, java.sql.Types.CHAR);
			insertAtletaPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void deleteAtleta(Atleta atleta) {
		try {
			deleteAtletaPS.setString(1, atleta.getCodiceFiscale());
			deleteAtletaPS.setString(2, atleta.getNome());
			deleteAtletaPS.setString(3, atleta.getCognome());
			deleteAtletaPS.setObject(4, atleta.getDataNascita());
			if (atleta.getNazionale()!=null) {
				insertAtletaPS.setString(5, atleta.getNazionale().getNome());
				insertAtletaPS.setInt(6, atleta.getPresenzeNazionale());
			}
			else {
				insertAtletaPS.setNull(5, java.sql.Types.VARCHAR);
				insertAtletaPS.setNull(6, java.sql.Types.INTEGER);
			}
			if (atleta.getProcuratore()!=null) 
				insertAtletaPS.setString(7, atleta.getProcuratore().getCodiceFiscale());
			else
				insertAtletaPS.setNull(7, java.sql.Types.CHAR);
			deleteAtletaPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void updateAtleta(Atleta nuovoAtleta, String vecchioCodiceFiscale) {
		try {
			updateAtletaPS.setString(1, nuovoAtleta.getCodiceFiscale());
			updateAtletaPS.setString(2, nuovoAtleta.getNome());
			updateAtletaPS.setString(3, nuovoAtleta.getCognome());
			updateAtletaPS.setObject(4, nuovoAtleta.getDataNascita());
			if (nuovoAtleta.getNazionale()!=null) {
				insertAtletaPS.setString(5, nuovoAtleta.getNazionale().getNome());
				insertAtletaPS.setInt(6, nuovoAtleta.getPresenzeNazionale());
			}
			else {
				insertAtletaPS.setNull(5, java.sql.Types.VARCHAR);
				insertAtletaPS.setNull(6, java.sql.Types.INTEGER);
			}
			if (nuovoAtleta.getProcuratore()!=null) 
				insertAtletaPS.setString(7, nuovoAtleta.getProcuratore().getCodiceFiscale());
			else
				insertAtletaPS.setNull(7, java.sql.Types.CHAR);
			updateAtletaPS.setString(8, vecchioCodiceFiscale);
			updateAtletaPS.executeUpdate();
		}
		catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
        }
	}
}
