package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import dao.ContrattoDAO;
import entity.Atleta;
import entity.Club;
import entity.Contratto;
import entity.Sponsor;

public class ContrattoDAOPostgresImpl implements ContrattoDAO {
	private Connection connection;
	private Controller controller;
	private Statement statement;
	private PreparedStatement insertContrattoPS;
	private PreparedStatement deleteContrattoPS;
	private PreparedStatement updateContrattoPS;
	
	public ContrattoDAOPostgresImpl(Connection connection, Controller controller) throws SQLException {
		this.connection = connection;
		this.controller = controller;
		insertContrattoPS = connection.prepareStatement("INSERT INTO Contratto (datainizio, datafine, retribuzione, percentualeprocuratore, atleta, club, sponsor) VALUES (?, ?, ?, ?, ?, ?, ?)");
		deleteContrattoPS = connection.prepareStatement("DELETE FROM Contratto WHERE datainizio = ? AND datafine = ? AND retribuzione = ? AND percentualeprocuratore = ? AND atleta = ? AND club = ? AND sponsor = ?");
		updateContrattoPS = connection.prepareStatement("UPDATE Contratto SET datainizio = ?, datafine = ?, retribuzione = ?, percentualeprocuratore = ?, atleta = ?, club = ?, sponsor = ? WHERE datainizio = ? AND datafine = ? AND retribuzione = ? AND percentualeprocuratore = ? AND atleta = ? AND club = ? AND sponsor = ?");
	}
	
	@Override
	public List<Contratto> getAllContratti(String nomeColonna, String scelta) {
		List<Contratto> listaContratti = new ArrayList<Contratto>();
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet;
			if(!nomeColonna.equals("Atleta") && !nomeColonna.equals("Club") && !nomeColonna.equals("Sponsor")) nomeColonna = nomeColonna.concat(" DESC");
			if(scelta.equals("Club"))
				resultSet = this.statement.executeQuery("SELECT * FROM Contratto WHERE club is not null ORDER BY " + nomeColonna);
			else
				resultSet = this.statement.executeQuery("SELECT * FROM Contratto WHERE sponsor is not null ORDER BY " + nomeColonna);
			while(resultSet.next()) {
				LocalDate dataInizio = resultSet.getDate("dataInizio").toLocalDate();
				LocalDate dataFine = resultSet.getDate("dataFine").toLocalDate();
				double retribuzione = resultSet.getDouble("retribuzione");
				int percentualeProcuratore = resultSet.getInt("percentualeProcuratore");
				double guadagnoProcuratore = resultSet.getDouble("guadagnoProcuratore");
				Atleta atleta = controller.cercaAtleta(resultSet.getString("atleta"));
				Contratto contratto;
				if (scelta.equals("Club")) {
					Club club = controller.cercaClub(resultSet.getString("club"));
					contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, guadagnoProcuratore, atleta, club);
				}
				else {
					Sponsor sponsor = controller.cercaSponsor(resultSet.getString("sponsor"));
					contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, guadagnoProcuratore, atleta, sponsor);
				}
				listaContratti.add(contratto);
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return listaContratti;
	}
	
	@Override
	public void insertContratto(Contratto contratto) {
		try {
			insertContrattoPS.setObject(1, contratto.getDataInizio());
			insertContrattoPS.setObject(2, contratto.getDataFine());
			insertContrattoPS.setDouble(3, contratto.getRetribuzione());
			insertContrattoPS.setInt(4, contratto.getPercentualeProcuratore());
			insertContrattoPS.setString(5, contratto.getAtleta().getCodiceFiscale());
			if(contratto.getClub()!=null) {
				insertContrattoPS.setString(6, contratto.getClub().getNome());
				insertContrattoPS.setString(7, null);
			}
			else {
				insertContrattoPS.setString(6, contratto.getSponsor().getNome());
				insertContrattoPS.setString(7, null);
			}
			insertContrattoPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void deleteContratto(Contratto contratto) {
		try {
			deleteContrattoPS.setObject(1, contratto.getDataInizio());
			deleteContrattoPS.setObject(2, contratto.getDataFine());
			deleteContrattoPS.setDouble(3, contratto.getRetribuzione());
			deleteContrattoPS.setInt(4, contratto.getPercentualeProcuratore());
			deleteContrattoPS.setString(5, contratto.getAtleta().getCodiceFiscale());
			if(contratto.getClub()!=null) {
				deleteContrattoPS.setString(6, contratto.getClub().getNome());
				deleteContrattoPS.setString(7, null);
			}
			else {
				deleteContrattoPS.setString(6, contratto.getSponsor().getNome());
				deleteContrattoPS.setString(7, null);
			}
			deleteContrattoPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void updateContratto(Contratto nuovoContratto, Contratto vecchioContratto) {
		try {
			updateContrattoPS.setObject(1, nuovoContratto.getDataInizio());
			updateContrattoPS.setObject(2, nuovoContratto.getDataFine());
			updateContrattoPS.setDouble(3, nuovoContratto.getRetribuzione());
			updateContrattoPS.setInt(4, nuovoContratto.getPercentualeProcuratore());
			updateContrattoPS.setString(5, nuovoContratto.getAtleta().getCodiceFiscale());
			if(nuovoContratto.getClub()!=null) {
				updateContrattoPS.setString(6, nuovoContratto.getClub().getNome());
				updateContrattoPS.setString(7, null);
			}
			else {
				updateContrattoPS.setString(6, nuovoContratto.getSponsor().getNome());
				updateContrattoPS.setString(7, null);
			}
			updateContrattoPS.setObject(8, vecchioContratto.getDataInizio());
			updateContrattoPS.setObject(9, vecchioContratto.getDataFine());
			updateContrattoPS.setDouble(10, vecchioContratto.getRetribuzione());
			updateContrattoPS.setInt(11, vecchioContratto.getPercentualeProcuratore());
			updateContrattoPS.setString(12, vecchioContratto.getAtleta().getCodiceFiscale());
			if(vecchioContratto.getClub()!=null) {
				updateContrattoPS.setString(13, vecchioContratto.getClub().getNome());
				updateContrattoPS.setString(14, null);
			}
			else {
				updateContrattoPS.setString(13, vecchioContratto.getSponsor().getNome());
				updateContrattoPS.setString(14, null);
			}
			
			updateContrattoPS.executeUpdate();
		}
		catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
        }
	}
}
