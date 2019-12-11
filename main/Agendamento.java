package main;

public class Agendamento {
	private Professor p;
	private Sala s;
	private int periodo;
	private String disciplina;

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public Professor getP() {
		return p;
	}

	public void setP(Professor p) {
		this.p = p;
	}

	public Sala getS() {
		return s;
	}

	public void setS(Sala s) {
		this.s = s;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public Agendamento(Professor p, Sala s, int periodo, String disciplina) {
		super();
		this.p = p;
		this.s = s;
		this.periodo = periodo;
		this.disciplina = disciplina;
	}
}
