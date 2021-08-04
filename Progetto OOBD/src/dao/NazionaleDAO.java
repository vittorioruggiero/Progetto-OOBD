package dao;

import java.util.List;

import entity.Nazionale;
import exception.GettoneNonValidoException;

public interface NazionaleDAO {

	List<Nazionale> getAllNazionali(String nomeColonna) throws GettoneNonValidoException;
	
	Nazionale getNazionale(String nomeCercato) throws GettoneNonValidoException;
	
	 List<String> getNomiNazionali();

	void insertNazionale(Nazionale nazionale);

	void deleteNazionale(Nazionale nazionale);

	void updateNazionale(Nazionale nuovaNazionale, String vecchioNome);

}