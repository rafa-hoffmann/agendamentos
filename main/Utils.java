package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Utils {
	public ArrayList<Agendamento> agendamentos = new ArrayList<Agendamento>();
	public ArrayList<Professor> professores = new ArrayList<Professor>();

	public void salvarAgendamentos() {
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter("agendamentos.txt", false));

			if (!agendamentos.isEmpty()) {
				for (int i = 0; i < agendamentos.size(); i++) {
					Agendamento temp = agendamentos.get(i);
					bw.write(String.format("%s\0%s\0%s\0%s\0%s\0%s\0\n", temp.getP().getLogin(), temp.getP().getNome(),
							temp.getP().getSenha(), temp.getS().getNome(), temp.getPeriodo(), temp.getDisciplina()));
				}
			}

			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo!");
		}

	}

	public void salvarProfessores() {
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter("professores.txt", false));

			if (!professores.isEmpty()) {
				for (int i = 0; i < professores.size(); i++) {
					Professor temp = professores.get(i);
					bw.write(String.format("%s\0%s\0%s\0\n", temp.getLogin(), temp.getNome(), temp.getSenha()));
				}
			}

			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo!");
		}
	}

	public void lerAgendamentos() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("agendamentos.txt"));

			String linha = null;
			try {
				while ((linha = br.readLine()) != null) {
					String[] div = linha.split("\0");
					String login = div[0];
					String nome = div[1];
					String senha = div[2];
					String nomeSala = div[3];
					int periodo = Integer.parseInt(div[4]);
					String disciplina = div[5];

					Sala s = new Sala(nomeSala);
					Professor p = new Professor(login, nome, senha);
					agendamentos.add(new Agendamento(p, s, periodo, disciplina));
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "agendamentos.txt corrompido!");
				System.exit(0);
			}
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Arquivo de agendamentos não encontrado!");
		}
	}

	public void lerProfessores() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("professores.txt"));

			String linha = null;
			try {
				while ((linha = br.readLine()) != null) {
					String[] div = linha.split("\0");
					String login = div[0];
					String nome = div[1];
					String senha = div[2];

					professores.add(new Professor(login, nome, senha));
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "professores.txt corrompido!");
				System.exit(0);
			}
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Arquivo de professores não encontrado!");
		}
	}
}
