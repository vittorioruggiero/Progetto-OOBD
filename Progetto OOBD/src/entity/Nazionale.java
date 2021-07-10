package entity;


public class Nazionale {
	private String nome;
	private double valoreGettone;
	
	public Nazionale(String nome, double valoreGettone) {
		super();
		this.nome = nome;
		this.valoreGettone = valoreGettone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValoreGettone() {
		return valoreGettone;
	}

	public void setValoreGettone(double valoreGettone) {
		this.valoreGettone = valoreGettone;
	}
	
}
