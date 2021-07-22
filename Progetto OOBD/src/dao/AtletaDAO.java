package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Atleta;

public interface AtletaDAO {

	List<Atleta> getAllAtleti(String nomeColonna);
	
	Atleta getAtleta(String codiceFiscaleCercato);

	void insertAtleta(Atleta atleta);

	void deleteAtleta(Atleta atleta);

	void updateAtleta(Atleta nuovoAtleta, String vecchioCodiceFiscale);
	
	ArrayList<ArrayList<Object>> getSorgentiIntroito(String nomeColonna);
}