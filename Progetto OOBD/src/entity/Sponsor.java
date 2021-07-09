package entity;

public class Sponsor {
	private String nome;
	private String stato;

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Sponsor(String nome, String stato) {
		super();
		this.nome = nome;
		this.stato = stato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	};
	
}
