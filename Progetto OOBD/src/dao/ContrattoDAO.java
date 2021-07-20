package dao;

import java.time.LocalDate;
import java.util.List;

import entity.Contratto;

public interface ContrattoDAO {

	List<Contratto> getAllContratti(String nomeColonna, String scelta);

	void insertContratto(String atleta, String club_sponsor, LocalDate dataInizio, LocalDate dataFine, double retribuzione, int percentualeProcuratore, String scelta);

	void deleteContratto(Contratto contratto);

	void updateContratto(Contratto nuovoContratto, Contratto vecchioContratto);

}