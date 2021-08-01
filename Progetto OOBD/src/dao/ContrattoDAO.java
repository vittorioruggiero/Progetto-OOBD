package dao;

import java.util.List;

import entity.Contratto;
import exception.IncoerenzaAssociazioneProcuratoreException;

public interface ContrattoDAO {

	List<Contratto> getAllContratti(String nomeColonna, String scelta);

	void insertContratto(Contratto contratto) throws IncoerenzaAssociazioneProcuratoreException;

	void deleteContratto(Contratto contratto);

	void updateContratto(Contratto nuovoContratto, Contratto vecchioContratto) throws IncoerenzaAssociazioneProcuratoreException;

}