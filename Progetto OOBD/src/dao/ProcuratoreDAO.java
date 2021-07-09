package dao;

import java.util.List;

import entity.Procuratore;

public interface ProcuratoreDAO {

	List<Procuratore> getAllProcuratori(String nomeColonna);
	
	Procuratore getProcuratore(String codiceFiscaleCercato);

	void insertProcuratore(Procuratore procuratore);

	void deleteProcuratore(Procuratore procuratore);

	void updateProcuratore(Procuratore nuovoProcuratore, String vecchioCodiceFiscale);

}