package main;

public class Sala {
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Sala(String nome) {
		super();
		this.nome = nome;
	}
	@Override
	public String toString() {
		return nome;
	}
}
