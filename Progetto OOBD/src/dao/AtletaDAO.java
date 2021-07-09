package dao;

import java.util.List;

import entity.Atleta;

public interface AtletaDAO {

	List<Atleta> getAllAtleti(String nomeColonna);

	void insertAtleta(Atleta atleta);

	void deleteAtleta(Atleta atleta);

	void updateAtleta(Atleta nuovoAtleta, String vecchioCodiceFiscale);

}