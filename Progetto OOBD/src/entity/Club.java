package entity;

public class Club {
	private String nome;
	private String citta;
	
	public Club(String nome, String citta) {
		super();
		this.nome = nome;
		this.citta = citta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}
	
}
