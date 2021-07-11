package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.ClubDAO;
import entity.Club;

public class ClubDAOPostgresImpl implements ClubDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement insertClubPS;
	private PreparedStatement deleteClubPS;
	private PreparedStatement updateClubPS;
	
	public ClubDAOPostgresImpl(Connection connection) throws SQLException {
		this.connection = connection;
		insertClubPS = connection.prepareStatement("INSERT INTO Club VALUES (?, ?)");
		deleteClubPS = connection.prepareStatement("DELETE FROM Club WHERE nome = ? AND citta = ?");
		updateClubPS = connection.prepareStatement("UPDATE Club SET nome = ?, citta = ? WHERE nome = ?");
	}
	
	@Override
	public List<Club> getAllClub(String nomeColonna) {
		List<Club> listaClub = new ArrayList<Club>();
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Club ORDER BY " + nomeColonna);
			
			while(resultSet.next()) {
				String nome = resultSet.getString("nome");
				String citta = resultSet.getString("citta");
				Club club = new Club(nome, citta);
				listaClub.add(club);
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return listaClub;
	}
	
	@Override
	public Club getClub(String nomeCercato) {
		Club club = null;
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Club WHERE club = '" + nomeCercato +"'");
			
			if(resultSet.next()) {
				String nome = resultSet.getString("nome");
				String citta = resultSet.getString("citta");
				club = new Club(nome, citta);
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return club;
	}
	
	@Override
	public void insertClub(Club club) {
		try {
			insertClubPS.setString(1, club.getNome());
			insertClubPS.setString(2, club.getCitta());
			insertClubPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void deleteClub(Club club) {
		try {
			deleteClubPS.setString(1, club.getNome());
			deleteClubPS.setString(2, club.getCitta());
			deleteClubPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void updateClub(Club nuovoClub, String vecchioNome) {
		try {
			updateClubPS.setString(1, nuovoClub.getNome());
			updateClubPS.setString(2, nuovoClub.getCitta());
			updateClubPS.setString(3, vecchioNome);
			updateClubPS.executeUpdate();
		}
		catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
        }
	}
}
