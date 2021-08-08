package entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import exception.DateIncoerentiException;
import exception.DurataContrattoInsufficienteException;
import exception.PercentualeProcuratoreNonValidaException;
import exception.RetribuzioneNonValidaException;

public class Contratto {
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private double retribuzione;
	private int percentualeProcuratore;
	private Atleta atleta;
	private Club club;
	private Sponsor sponsor;

	public Contratto(LocalDate dataInizio, LocalDate dataFine, double retribuzione, int percentualeProcuratore, Atleta atleta, Club club) throws RetribuzioneNonValidaException, PercentualeProcuratoreNonValidaException, DurataContrattoInsufficienteException, DateIncoerentiException {
		super();
		if(dataInizio.isAfter(dataFine)) throw new DateIncoerentiException();
		if(ChronoUnit.YEARS.between(dataInizio, dataFine)<1) throw new DurataContrattoInsufficienteException();
		if(retribuzione<=0) throw new RetribuzioneNonValidaException();
		if((percentualeProcuratore<=0 || percentualeProcuratore>=100)) throw new PercentualeProcuratoreNonValidaException();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.retribuzione = retribuzione;
		this.percentualeProcuratore = percentualeProcuratore;
		this.atleta = atleta;
		this.club = club;
	}
	
	public Contratto(LocalDate dataInizio, LocalDate dataFine, double retribuzione, Atleta atleta, Club club) throws RetribuzioneNonValidaException, DurataContrattoInsufficienteException, DateIncoerentiException {
		super();
		if(dataInizio.isAfter(dataFine)) throw new DateIncoerentiException();
		if(ChronoUnit.YEARS.between(dataInizio, dataFine)<1) throw new DurataContrattoInsufficienteException();
		if(retribuzione<=0) throw new RetribuzioneNonValidaException();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.retribuzione = retribuzione;
		this.atleta = atleta;
		this.club = club;
	}
	
	public Contratto(LocalDate dataInizio, LocalDate dataFine, double retribuzione, int percentualeProcuratore, Atleta atleta, Sponsor sponsor) throws RetribuzioneNonValidaException, PercentualeProcuratoreNonValidaException, DurataContrattoInsufficienteException, DateIncoerentiException {
		super();
		if(dataInizio.isAfter(dataFine)) throw new DateIncoerentiException();
		if(ChronoUnit.YEARS.between(dataInizio, dataFine)<1) throw new DurataContrattoInsufficienteException();
		if(retribuzione<=0) throw new RetribuzioneNonValidaException();
		if((percentualeProcuratore<=0 || percentualeProcuratore>=100)) throw new PercentualeProcuratoreNonValidaException();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.retribuzione = retribuzione;
		this.percentualeProcuratore = percentualeProcuratore;
		this.atleta = atleta;
		this.sponsor = sponsor;
	}
	
	public Contratto(LocalDate dataInizio, LocalDate dataFine, double retribuzione, Atleta atleta, Sponsor sponsor) throws RetribuzioneNonValidaException, DurataContrattoInsufficienteException, DateIncoerentiException {
		super();
		if(dataInizio.isAfter(dataFine)) throw new DateIncoerentiException();
		if(ChronoUnit.YEARS.between(dataInizio, dataFine)<1) throw new DurataContrattoInsufficienteException();
		if(retribuzione<=0) throw new RetribuzioneNonValidaException();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.retribuzione = retribuzione;
		this.atleta = atleta;
		this.sponsor = sponsor;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) throws DurataContrattoInsufficienteException, DateIncoerentiException {
		if(dataInizio.isAfter(dataFine)) throw new DateIncoerentiException();
		if(ChronoUnit.YEARS.between(dataInizio, dataFine)<1) throw new DurataContrattoInsufficienteException();
		this.dataInizio = dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) throws DurataContrattoInsufficienteException, DateIncoerentiException {
		if(dataInizio.isAfter(dataFine)) throw new DateIncoerentiException();
		if(ChronoUnit.YEARS.between(dataInizio, dataFine)<1) throw new DurataContrattoInsufficienteException();
		this.dataFine = dataFine;
	}

	public double getRetribuzione() {
		return retribuzione;
	}

	public void setRetribuzione(double retribuzione) throws RetribuzioneNonValidaException {
		if(retribuzione<=0) throw new RetribuzioneNonValidaException();
		this.retribuzione = retribuzione;
	}

	public int getPercentualeProcuratore() {
		return percentualeProcuratore;
	}

	public void setPercentualeProcuratore(int percentualeProcuratore) throws PercentualeProcuratoreNonValidaException {
		if((percentualeProcuratore<=0 || percentualeProcuratore>=100)) throw new PercentualeProcuratoreNonValidaException();
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
	
	public double getGuadagnoProcuratore() {
		return retribuzione*percentualeProcuratore/100;
	}
}
