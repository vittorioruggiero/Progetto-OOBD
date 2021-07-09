package entity;

import java.time.LocalDate;

public class Contratto {
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private double retribuzione;
	private int percentualeProcuratore;
	private Atleta atleta;
	private Club club;
	private Sponsor sponsor;

	public Contratto(LocalDate dataInizio, LocalDate dataFine, double retribuzione, int percentualeProcuratore, Atleta atleta, Club club) {
		super();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.retribuzione = retribuzione;
		this.percentualeProcuratore = percentualeProcuratore;
		this.atleta = atleta;
		this.club = club;
	}
	
	public Contratto(LocalDate dataInizio, LocalDate dataFine, double retribuzione, int percentualeProcuratore, Atleta atleta, Sponsor sponsor) {
		super();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.retribuzione = retribuzione;
		this.percentualeProcuratore = percentualeProcuratore;
		this.atleta = atleta;
		this.sponsor = sponsor;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}

	public double getRetribuzione() {
		return retribuzione;
	}

	public void setRetribuzione(double retribuzione) {
		this.retribuzione = retribuzione;
	}

	public int getPercentualeProcuratore() {
		return percentualeProcuratore;
	}

	public void setPercentualeProcuratore(int percentualeProcuratore) {
		this.percentualeProcuratore = percentualeProcuratore;
	}
	
	public Atleta getAtleta() {
		return atleta;
	}

	public void setAtleta(Atleta atleta) {
		this.atleta = atleta;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
}
