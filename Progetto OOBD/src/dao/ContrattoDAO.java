package dao;

import java.util.List;

import entity.Contratto;
import exception.DateIncoerentiException;
import exception.DurataContrattoInsufficienteException;
import exception.IncoerenzaAssociazioneProcuratoreException;
import exception.PercentualeProcuratoreNonValidaException;
import exception.RetribuzioneNonValidaException;

public interface ContrattoDAO {

	List<Contratto> getAllContratti(String nomeColonna, String scelta) throws RetribuzioneNonValidaException, PercentualeProcuratoreNonValidaException, DurataContrattoInsufficienteException, DateIncoerentiException;

	void insertContratto(Contratto contratto) throws IncoerenzaAssociazioneProcuratoreException;

	void deleteContratto(Contratto contratto);

	void updateContratto(Contratto nuovoContratto, Contratto vecchioContratto) throws IncoerenzaAssociazioneProcuratoreException;

}