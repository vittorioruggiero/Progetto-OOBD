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
import exception.DateIncoerentiException;
import exception.DurataContrattoInsufficienteException;
import exception.IncoerenzaAssociazioneProcuratoreException;
import exception.PercentualeProcuratoreNonValidaException;
import exception.RetribuzioneNonValidaException;

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
		updateContrattoPS = connection.prepareStatement("UPDATE Contratto SET datainizio = ?, datafine = ?, retribuzione = ?, percentualeprocuratore = ?, atleta = ?, club = ?, sponsor = ? WHERE datainizio = ? AND datafine = ? AND retribuzione = ? AND percentualeprocuratore = ? AND atleta = ? AND club = ? AND sponsor = ?");
	}
	
	@Override
	public List<Contratto> getAllContratti(String nomeColonna, String scelta) throws RetribuzioneNonValidaException, PercentualeProcuratoreNonValidaException, DurataContrattoInsufficienteException, DateIncoerentiException {
		List<Contratto> listaContratti = new ArrayList<Contratto>();
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet;
			if(!nomeColonna.equals("Atleta") && !nomeColonna.equals("Club") && !nomeColonna.equals("Sponsor")) nomeColonna = nomeColonna.concat(" DESC");
			if(scelta.equals("Club"))
				resultSet = this.statement.executeQuery("SELECT * FROM Contratto WHERE club is not null ORDER BY " + nomeColonna + " NULLS LAST");
			else
				resultSet = this.statement.executeQuery("SELECT * FROM Contratto WHERE sponsor is not null ORDER BY " + nomeColonna + " NULLS LAST");
			while(resultSet.next()) {
				LocalDate dataInizio = resultSet.getDate("dataInizio").toLocalDate();
				LocalDate dataFine = resultSet.getDate("dataFine").toLocalDate();
				double retribuzione = resultSet.getDouble("retribuzione");
				int percentualeProcuratore = resultSet.getInt("percentualeProcuratore");
				Atleta atleta = controller.cercaAtleta(resultSet.getString("atleta"));
				Contratto contratto;
				if (scelta.equals("Club")) {
					Club club = controller.cercaClub(resultSet.getString("club"));
					if(percentualeProcuratore!=0) contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, atleta, club);
					else contratto = new Contratto(dataInizio, dataFine, retribuzione, atleta, club);
				}
				else {
					Sponsor sponsor = controller.cercaSponsor(resultSet.getString("sponsor"));
					if(percentualeProcuratore!=0) contratto = new Contratto(dataInizio, dataFine, retribuzione, percentualeProcuratore, atleta, sponsor);
					else contratto = new Contratto(dataInizio, dataFine, retribuzione, atleta, sponsor);
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
	public void insertContratto(Contratto contratto) throws IncoerenzaAssociazioneProcuratoreException {
		try {
			insertContrattoPS.setObject(1, contratto.getDataInizio());
			insertContrattoPS.setObject(2, contratto.getDataFine());
			insertContrattoPS.setDouble(3, contratto.getRetribuzione());
			if(contratto.getPercentualeProcuratore()!=0) insertContrattoPS.setInt(4, contratto.getPercentualeProcuratore());
			else insertContrattoPS.setNull(4, java.sql.Types.INTEGER);
			insertContrattoPS.setString(5, contratto.getAtleta().getCodiceFiscale());
			if(contratto.getClub()!=null) {
				insertContrattoPS.setString(6, contratto.getClub().getNome());
				insertContrattoPS.setString(7, null);
			}
			else {
				insertContrattoPS.setString(6, null);
				insertContrattoPS.setString(7, contratto.getSponsor().getNome());
			}
			insertContrattoPS.executeUpdate();
		}
			catch (SQLException exception) {
				if(exception.getMessage().startsWith("ERRORE: Incoerenza associazione procuratore")) throw new IncoerenzaAssociazioneProcuratoreException();
				else System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void deleteContratto(Contratto contratto) {
		String scelta;
		if(contratto.getClub()!=null) scelta = "Club";
		else scelta = "Sponsor";
		String deleteString = "DELETE FROM Contratto WHERE datainizio = ? AND datafine = ? AND retribuzione = ? AND atleta = ? AND " + scelta + " = ? AND percentualeprocuratore = ?";
		if(contratto.getPercentualeProcuratore()==0) deleteString = deleteString.replace("percentualeprocuratore = ?", "percentualeprocuratore is null");
		try {
			deleteContrattoPS = connection.prepareStatement(deleteString);
			deleteContrattoPS.setObject(1, contratto.getDataInizio());
			deleteContrattoPS.setObject(2, contratto.getDataFine());
			deleteContrattoPS.setDouble(3, contratto.getRetribuzione());
			deleteContrattoPS.setString(4, contratto.getAtleta().getCodiceFiscale());
			if(scelta.equals("Club")) deleteContrattoPS.setString(5, contratto.getClub().getNome());
			else deleteContrattoPS.setString(5, contratto.getSponsor().getNome());
			if(contratto.getPercentualeProcuratore()!=0) deleteContrattoPS.setInt(6, contratto.getPercentualeProcuratore());
			deleteContrattoPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void updateContratto(Contratto nuovoContratto, Contratto vecchioContratto) throws IncoerenzaAssociazioneProcuratoreException {
		String scelta;
		if(nuovoContratto.getClub()!=null) scelta = "Club";
		else scelta = "Sponsor";
		String updateString = "UPDATE Contratto SET datainizio = ?, datafine = ?, retribuzione = ?, percentualeprocuratore = ?, atleta = ?, " + scelta + " = ? WHERE datainizio = ? AND datafine = ? AND retribuzione = ? AND atleta = ? AND " + scelta + " = ? AND percentualeprocuratore = ?";
		if(vecchioContratto.getPercentualeProcuratore()==0) updateString = updateString.replace("AND percentualeprocuratore = ?", "AND percentualeprocuratore is null");
		try {
			updateContrattoPS = connection.prepareStatement(updateString);
			updateContrattoPS.setObject(1, nuovoContratto.getDataInizio());
			updateContrattoPS.setObject(2, nuovoContratto.getDataFine());
			updateContrattoPS.setDouble(3, nuovoContratto.getRetribuzione());
			if(nuovoContratto.getPercentualeProcuratore()==0) updateContrattoPS.setNull(4, java.sql.Types.INTEGER);
			else updateContrattoPS.setInt(4, nuovoContratto.getPercentualeProcuratore());
			updateContrattoPS.setString(5, nuovoContratto.getAtleta().getCodiceFiscale());
			if(nuovoContratto.getClub()!=null) updateContrattoPS.setString(6, nuovoContratto.getClub().getNome());
			else updateContrattoPS.setString(6, nuovoContratto.getSponsor().getNome());
			updateContrattoPS.setObject(7, vecchioContratto.getDataInizio());
			updateContrattoPS.setObject(8, vecchioContratto.getDataFine());
			updateContrattoPS.setDouble(9, vecchioContratto.getRetribuzione());
			updateContrattoPS.setString(10, vecchioContratto.getAtleta().getCodiceFiscale());
			if(vecchioContratto.getClub()!=null) updateContrattoPS.setString(11, vecchioContratto.getClub().getNome());
			else updateContrattoPS.setString(11, vecchioContratto.getSponsor().getNome());
			if(vecchioContratto.getPercentualeProcuratore()!=0) updateContrattoPS.setInt(12, vecchioContratto.getPercentualeProcuratore());
			updateContrattoPS.executeUpdate();
		}
		catch (SQLException exception) {
			if(exception.getMessage().startsWith("ERRORE: Incoerenza associazione procuratore")) throw new IncoerenzaAssociazioneProcuratoreException();
			else System.out.println("SQLException: " + exception.getMessage());
        }
	}
}
