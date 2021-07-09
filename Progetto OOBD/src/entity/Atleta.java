package entity;

import java.time.LocalDate;

public class Atleta {
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private int presenzeNazionale;
	private Procuratore procuratore;
	private Nazionale nazionale;
	
	public Atleta(String codiceFiscale, String nome, String cognome, LocalDate dataNascita, int presenzeNazionale, Procuratore procuratore, Nazionale nazionale) {
		super();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.presenzeNazionale = presenzeNazionale;
		this.procuratore = procuratore;
		this.nazionale = nazionale;
	}
	
	public Atleta(String codiceFiscale, String nome, String cognome, LocalDate dataNascita, Procuratore procuratore) {
		super();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.procuratore = procuratore;
	}
	
	public Atleta(String codiceFiscale, String nome, String cognome, LocalDate dataNascita, int presenzeNazionale, Nazionale nazionale) {
		super();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.presenzeNazionale = presenzeNazionale;
		this.nazionale = nazionale;
	}
	
	public Atleta(String codiceFiscale, String nome, String cognome, LocalDate dataNascita) {
		super();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
	}

	public Procuratore getProcuratore() {
		return procuratore;
	}

	public void setProcuratore(Procuratore procuratore) {
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

	public void setCodiceFiscale(String codiceFiscale) {
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

	public void setPresenzeNazionale(int presenzeNazionale) {
		this.presenzeNazionale = presenzeNazionale;
	}
	
}
