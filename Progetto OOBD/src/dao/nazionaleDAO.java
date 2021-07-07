package dao;

import java.util.List;

import entity.Nazionale;

public interface nazionaleDAO {

	List<Nazionale> getAllNazionali(String nomeColonna);

	void insertNazionale(Nazionale nazionale);

	void deleteNazionale(Nazionale nazionale);

	void updateNazionale(Nazionale nuovaNazionale, String vecchioNome);

}