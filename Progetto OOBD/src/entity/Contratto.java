package entity;

import java.time.LocalDate;

public class Contratto {
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private double retribuzione;
	private int percentualeProcuratore;
	
	public Contratto(LocalDate dataInizio, LocalDate dataFine, double retribuzione, int percentualeProcuratore) {
		super();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.retribuzione = retribuzione;
		this.percentualeProcuratore = percentualeProcuratore;
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
}
