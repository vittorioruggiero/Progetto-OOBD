package dao;

import java.util.List;

import entity.Nazionale;

public interface NazionaleDAO {

	List<Nazionale> getAllNazionali(String nomeColonna);
	
	Nazionale getNazionale(String nomeCercato);
	
	 List<String> getNomiNazionali();

	void insertNazionale(Nazionale nazionale);

	void deleteNazionale(Nazionale nazionale);

	void updateNazionale(Nazionale nuovaNazionale, String vecchioNome);

}