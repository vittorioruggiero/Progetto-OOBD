package entity;

import exception.GettoneNonValidoException;

public class Nazionale {
	private String nome;
	private double valoreGettone;
	
	public Nazionale(String nome, double valoreGettone) throws GettoneNonValidoException {
		super();
		if(valoreGettone<=0) throw new GettoneNonValidoException();
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

	public void setValoreGettone(double valoreGettone) throws GettoneNonValidoException {
		if(valoreGettone<=0) throw new GettoneNonValidoException();
		this.valoreGettone = valoreGettone;
	}
	
}
