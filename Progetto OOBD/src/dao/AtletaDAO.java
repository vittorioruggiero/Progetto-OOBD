package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Atleta;
import exception.CodiceFiscaleNonValidoException;
import exception.CodiciFiscaliUgualiException;
import exception.PresenzeNazionaleNonValideException;

public interface AtletaDAO {

	List<Atleta> getAllAtleti(String nomeColonna) throws CodiceFiscaleNonValidoException, PresenzeNazionaleNonValideException, CodiciFiscaliUgualiException;
	
	Atleta getAtleta(String codiceFiscaleCercato) throws CodiceFiscaleNonValidoException, PresenzeNazionaleNonValideException, CodiciFiscaliUgualiException;
	
	List<String> getCodiciFiscaliAtleti();

	void insertAtleta(Atleta atleta);

	void deleteAtleta(Atleta atleta);

	void updateAtleta(Atleta nuovoAtleta, String vecchioCodiceFiscale);
	
	ArrayList<ArrayList<Object>> getSorgentiIntroito(String nomeColonna);
}