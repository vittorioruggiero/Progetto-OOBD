package entity;

import java.time.LocalDate;

import exception.CodiceFiscaleNonValidoException;

public class Procuratore {
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	
	public Procuratore(String codiceFiscale, String nome, String cognome, LocalDate dataNascita) throws CodiceFiscaleNonValidoException {
		super();
		if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) throws CodiceFiscaleNonValidoException {
		if(!codiceFiscale.matches("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")) throw new CodiceFiscaleNonValidoException();
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
	
}
