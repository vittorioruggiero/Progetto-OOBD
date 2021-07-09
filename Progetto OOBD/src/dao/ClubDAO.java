package dao;

import java.util.List;

import entity.Club;

public interface ClubDAO {

	List<Club> getAllClub(String nomeColonna);
	
	Club getClub(String nomeCercato);

	void insertClub(Club club);

	void deleteClub(Club club);

	void updateClub(Club nuovoClub, String vecchioNome);

}