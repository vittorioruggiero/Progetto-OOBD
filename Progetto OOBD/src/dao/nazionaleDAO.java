package dao;

import java.util.List;

import entity.Nazionale;

public interface nazionaleDAO {

	List<Nazionale> getAllNazionali(String nomeColonna);

	void insertNazionale(Nazionale nazionale);

	void deleteElemento(String nome);

	void updateNazionale(Nazionale nuovaNazionale, String vecchioNome);

}