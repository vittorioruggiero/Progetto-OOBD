package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Procuratore;
import exception.CodiceFiscaleNonValidoException;

public interface ProcuratoreDAO {

	List<Procuratore> getAllProcuratori(String nomeColonna) throws CodiceFiscaleNonValidoException;
	
	Procuratore getProcuratore(String codiceFiscaleCercato) throws CodiceFiscaleNonValidoException;
	
	List<String> getCodiciFiscaliProcuratori();

	void insertProcuratore(Procuratore procuratore);

	void deleteProcuratore(Procuratore procuratore);

	void updateProcuratore(Procuratore nuovoProcuratore, String vecchioCodiceFiscale);
	
	ArrayList<ArrayList<Object>> getProcuratoriMaxGuadagni(String nomeColonna);

}