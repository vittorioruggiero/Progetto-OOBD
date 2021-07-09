package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.SponsorDAO;
import entity.Sponsor;

public class SponsorDAOPostgresImpl implements SponsorDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement insertSponsorPS;
	private PreparedStatement deleteSponsorPS;
	private PreparedStatement updateSponsorPS;
	
	public SponsorDAOPostgresImpl(Connection connection) throws SQLException {
		this.connection = connection;
		insertSponsorPS = connection.prepareStatement("INSERT INTO Sponsor VALUES (?)");
		deleteSponsorPS = connection.prepareStatement("DELETE FROM Sponsor WHERE nome = ?");
		updateSponsorPS = connection.prepareStatement("UPDATE Sponsor SET nome = ? WHERE nome = ?");
	}
	
	@Override
	public List<Sponsor> getAllsponsor(String nomeColonna) {
		List<Sponsor> listaSponsor = new ArrayList<Sponsor>();
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Sponsor ORDER BY " + nomeColonna);
			
			while(resultSet.next()) {
				String nome = resultSet.getString("nome");
				Sponsor sponsor = new Sponsor(nome);
				listaSponsor.add(sponsor);
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return listaSponsor;
	}
	
	@Override
	public void insertSponsor(Sponsor sponsor) {
		try {
			insertSponsorPS.setString(1, sponsor.getNome());
			insertSponsorPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void deleteSponsor(Sponsor sponsor) {
		try {
			deleteSponsorPS.setString(1, sponsor.getNome());
			deleteSponsorPS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void updateSponsor(Sponsor nuovaSponsor, String vecchioNome) {
		try {
			updateSponsorPS.setString(1, nuovaSponsor.getNome());
			updateSponsorPS.setString(2, vecchioNome);
			updateSponsorPS.executeUpdate();
		}
		catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
        }
	}
}
