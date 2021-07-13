package dao;

import java.util.List;

import entity.Atleta;

public interface AtletaDAO {

	List<Atleta> getAllAtleti(String nomeColonna);
	
	Atleta getAtleta(String codiceFiscaleCercato);

	void insertAtleta(Atleta atleta, String nazionale, int presenzeNazionale, String procuratore);

	void deleteAtleta(Atleta atleta);

	void updateAtleta(Atleta nuovoAtleta, String vecchioCodiceFiscale);

}