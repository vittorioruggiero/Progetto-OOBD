package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.NazionaleDAO;
import entity.Nazionale;

public class NazionaleDAOPostgresImpl implements NazionaleDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement insertNazionalePS;
	private PreparedStatement deleteNazionalePS;
	private PreparedStatement updateNazionalePS;
	
	public NazionaleDAOPostgresImpl(Connection connection) throws SQLException {
		this.connection = connection;
		insertNazionalePS = connection.prepareStatement("INSERT INTO Nazionale VALUES (?, ?)");
		deleteNazionalePS = connection.prepareStatement("DELETE FROM Nazionale WHERE nome = ? AND valoreGettone = ?");
		updateNazionalePS = connection.prepareStatement("UPDATE Nazionale SET nome = ?, valoreGettone = ? WHERE nome = ?");
	}
	
	@Override
	public List<Nazionale> getAllNazionali(String nomeColonna) {
		List<Nazionale> listaNazionali = new ArrayList<Nazionale>();
		try {
			this.statement = this.connection.createStatement();
			if(nomeColonna.equals("ValoreGettone")) nomeColonna = nomeColonna.concat(" DESC");
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Nazionale ORDER BY " + nomeColonna);
			
			while(resultSet.next()) {
				String nome = resultSet.getString("nome");
				double valoreGettone = resultSet.getDouble("valoreGettone");
				Nazionale nazionale = new Nazionale(nome, valoreGettone);
				listaNazionali.add(nazionale);
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return listaNazionali;
	}
	
	@Override
	public Nazionale getNazionale(String nomeCercato) {
		Nazionale nazionale = null;
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet = this.statement.executeQuery("SELECT * FROM Nazionale WHERE nome = '" + nomeCercato +"'");
			
			if(resultSet.next()) {
				String nome = resultSet.getString("nome");
				double valoreGettone = resultSet.getDouble("valoreGettone");
				nazionale = new Nazionale(nome, valoreGettone);
			}
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return nazionale;
	}
	
	@Override
	public List<String> getNomiNazionali() {
		List<String> listaNomiNazionali = new ArrayList<String>();
		try {
			this.statement = this.connection.createStatement();
			ResultSet resultSet = this.statement.executeQuery("SELECT nome FROM nazionale ORDER BY nome");
			
			while (resultSet.next()) listaNomiNazionali.add(resultSet.getString("nome"));
			resultSet.close();
		}
		catch (SQLException exception) {
			System.out.println("SQLException: " + exception.getMessage());
		}
		return listaNomiNazionali;
	}
	
	@Override
	public void insertNazionale(Nazionale nazionale) {
		try {
			insertNazionalePS.setString(1, nazionale.getNome());
			insertNazionalePS.setDouble(2, nazionale.getValoreGettone());
			insertNazionalePS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void deleteNazionale(Nazionale nazionale) {
		try {
			deleteNazionalePS.setString(1, nazionale.getNome());
			deleteNazionalePS.setDouble(2, nazionale.getValoreGettone());
			deleteNazionalePS.executeUpdate();
		}
			catch (SQLException exception) {
	            System.out.println("SQLException: " + exception.getMessage());
	        }
	}
	
	@Override
	public void updateNazionale(Nazionale nuovaNazionale, String vecchioNome) {
		try {
			updateNazionalePS.setString(1, nuovaNazionale.getNome());
			updateNazionalePS.setDouble(2, nuovaNazionale.getValoreGettone());
			updateNazionalePS.setString(3, vecchioNome);
			updateNazionalePS.executeUpdate();
		}
		catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
        }
	}
	
}
