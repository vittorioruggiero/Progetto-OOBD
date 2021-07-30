package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Procuratore;

public interface ProcuratoreDAO {

	List<Procuratore> getAllProcuratori(String nomeColonna);
	
	Procuratore getProcuratore(String codiceFiscaleCercato);
	
	List<String> getCodiciFiscaliProcuratori();

	void insertProcuratore(Procuratore procuratore);

	void deleteProcuratore(Procuratore procuratore);

	void updateProcuratore(Procuratore nuovoProcuratore, String vecchioCodiceFiscale);
	
	ArrayList<ArrayList<Object>> getProcuratoriMaxGuadagni(String nomeColonna);

}