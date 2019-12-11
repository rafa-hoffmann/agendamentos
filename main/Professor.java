package main;

public class Professor {
	private String login;
	private String nome;
	private String senha;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Professor(String login, String nome, String senha) {
		super();
		this.login = login;
		this.nome = nome;
		this.senha = senha;
	}

}
