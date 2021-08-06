package entity;

import java.time.LocalDate;

import exception.CodiceFiscaleNonValidoException;
import exception.CodiciFiscaliUgualiException;
import exception.PresenzeNazionaleNonValideException;

public class Atleta {
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private int presenzeNazionale;
	private Procuratore procuratore;
	private Nazionale nazionale;
	
	public Atleta(String codiceFiscale, String nome, String cognome, LocalDate dataNascita, int presenzeNazionale, Procuratore procuratore, Nazionale nazionale) throws CodiceFiscaleNonValidoException, PresenzeNazionaleNonValideException, CodiciFiscaliUgualiException {
		super();
		if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
		if(presenzeNazionale<0) throw new PresenzeNazionaleNonValideException();
		if(codiceFiscale.equals(procuratore.getCodiceFiscale())) throw new CodiciFiscaliUgualiException();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.presenzeNazionale = presenzeNazionale;
		this.procuratore = procuratore;
		this.nazionale = nazionale;
	}
	
	public Atleta(String codiceFiscale, String nome, String cognome, LocalDate dataNascita, Procuratore procuratore) throws CodiceFiscaleNonValidoException, PresenzeNazionaleNonValideException, CodiciFiscaliUgualiException {
		super();
		if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
		if(codiceFiscale.equals(procuratore.getCodiceFiscale())) throw new CodiciFiscaliUgualiException();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.procuratore = procuratore;
	}
	
	public Atleta(String codiceFiscale, String nome, String cognome, LocalDate dataNascita, int presenzeNazionale, Nazionale nazionale) throws CodiceFiscaleNonValidoException, PresenzeNazionaleNonValideException {
		super();
		if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
		if(presenzeNazionale<0) throw new PresenzeNazionaleNonValideException();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.presenzeNazionale = presenzeNazionale;
		this.nazionale = nazionale;
	}
	
	public Atleta(String codiceFiscale, String nome, String cognome, LocalDate dataNascita) throws CodiceFiscaleNonValidoException, PresenzeNazionaleNonValideException {
		super();
		if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
	}

	public Procuratore getProcuratore() {
		return procuratore;
	}

	public void setProcuratore(Procuratore procuratore) throws CodiciFiscaliUgualiException {
		if(codiceFiscale.equals(procuratore.getCodiceFiscale())) throw new CodiciFiscaliUgualiException();
		this.procuratore = procuratore;
	}

	public Nazionale getNazionale() {
		return nazionale;
	}

	public void setNazionale(Nazionale nazionale) {
		this.nazionale = nazionale;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) throws CodiceFiscaleNonValidoException, CodiciFiscaliUgualiException {
		if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
		if(procuratore!=null && codiceFiscale.equals(procuratore.getCodiceFiscale())) throw new CodiciFiscaliUgualiException();
		this.codiceFiscale = codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public int getPresenzeNazionale() {
		return presenzeNazionale;
	}

	public void setPresenzeNazionale(int presenzeNazionale) throws PresenzeNazionaleNonValideException {
		if(presenzeNazionale<0) throw new PresenzeNazionaleNonValideException();
		this.presenzeNazionale = presenzeNazionale;
	}
	
}
