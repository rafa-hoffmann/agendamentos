package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Main {
	private JFrame frmAgendamento;
	private JPasswordField tfSenha_1;
	private JTextField tfLogin_1;
	private JTable tblAgendados;

	private JTable tblPeriodos;
	private DefaultTableModel tableModel;
	private JComboBox<Sala> comboSalas;
	private JPanel pnlCadastrar;
	private JTextField tfLogin;
	private JTextField tfNome;

	private JPasswordField tfSenha;
	private JButton btnCadastrar_1;
	private JPanel pnlLogin;
	private JPanel pnlAgendamento;
	private JLabel lblBemvindo;
	private JPasswordField tfNovaSenha;
	private JLabel lblInsiraNovaSenha;
	private JButton btnConfirmar;

	private JLabel lblSenhaAlteradaCom;
	private JButton btnRemover;

	private String usuarioLogado;
	Utils ut = new Utils();

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		File fAgendamentos = new File("agendamentos.txt");
		try {
			fAgendamentos.createNewFile();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Erro ao criar arquivo de agendamentos!");
		}
		File fProfessores = new File("professores.txt");
		try {
			fProfessores.createNewFile();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Erro ao criar arquivo de professores!");
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmAgendamento.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		ut.lerAgendamentos();
		ut.lerProfessores();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		Sala s1 = new Sala("A1");
		Sala s2 = new Sala("A2");
		Sala s3 = new Sala("A3");
		Sala s4 = new Sala("A4");
		Sala s5 = new Sala("A5");

		frmAgendamento = new JFrame();
		frmAgendamento.setTitle("Agendamento");
		frmAgendamento.setBounds(100, 100, 640, 480);
		frmAgendamento.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAgendamento.getContentPane().setLayout(new CardLayout(0, 0));

		pnlLogin = new JPanel();
		frmAgendamento.getContentPane().add(pnlLogin, "name_597581392579800");
		pnlLogin.setLayout(null);

		pnlAgendamento = new JPanel();
		frmAgendamento.getContentPane().add(pnlAgendamento, "name_597581406924700");
		pnlAgendamento.setLayout(null);

		lblBemvindo = new JLabel("Bem-vindo, ");
		lblBemvindo.setBounds(10, 11, 243, 14);
		pnlAgendamento.add(lblBemvindo);

		JPanel pnlAgendados = new JPanel();
		pnlAgendados.setBounds(349, 149, 265, 218);
		pnlAgendamento.add(pnlAgendados);
		pnlAgendados.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 265, 218);
		pnlAgendados.add(scrollPane);

		tblAgendados = new JTable();
		tableModel = new DefaultTableModel(new String[] { "Periodos", "Turno", "Professor", "Disciplina" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tblAgendados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblAgendados.setModel(tableModel);
		tblAgendados.getColumnModel().getColumn(0).setPreferredWidth(52);
		tblAgendados.getColumnModel().getColumn(1).setPreferredWidth(68);
		tblAgendados.getColumnModel().getColumn(2).setPreferredWidth(94);
		tblAgendados.getColumnModel().getColumn(3).setPreferredWidth(91);

		tblAgendados.setVisible(false);
		scrollPane.setViewportView(tblAgendados);
		tblAgendados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!tblAgendados.getSelectionModel().isSelectionEmpty()) {
					btnRemover.setEnabled(true);
				} else {
					btnRemover.setEnabled(false);
				}

			}
		});

		comboSalas = new JComboBox<Sala>();
		comboSalas.addItem(s1);
		comboSalas.addItem(s2);
		comboSalas.addItem(s3);
		comboSalas.addItem(s4);
		comboSalas.addItem(s5);
		comboSalas.setBounds(10, 89, 150, 20);
		pnlAgendamento.add(comboSalas);

		JLabel lblSelecioneSalaPara = new JLabel("Selecione sala para agendar hor\u00E1rio");
		lblSelecioneSalaPara.setBounds(10, 64, 377, 14);
		pnlAgendamento.add(lblSelecioneSalaPara);

		JButton btnVisualizar = new JButton("Visualizar");
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarTabela();
			}
		});
		btnVisualizar.setBounds(170, 88, 109, 21);
		pnlAgendamento.add(btnVisualizar);

		JButton btnAgendar = new JButton("Agendar");
		btnAgendar.setEnabled(false);
		btnAgendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				agendarHorario();
				atualizarTabela();
			}
		});
		btnAgendar.setBounds(121, 380, 89, 23);
		pnlAgendamento.add(btnAgendar);

		JButton btnAlterarSenha = new JButton("Alterar senha");
		btnAlterarSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInsiraNovaSenha.setVisible(true);
				tfNovaSenha.setVisible(true);
				btnConfirmar.setVisible(true);
			}
		});
		btnAlterarSenha.setBounds(136, 36, 143, 23);
		pnlAgendamento.add(btnAlterarSenha);

		JPanel pnlPeriodos = new JPanel();
		pnlPeriodos.setBounds(10, 149, 200, 218);
		pnlAgendamento.add(pnlPeriodos);
		pnlPeriodos.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 200, 218);
		pnlPeriodos.add(scrollPane_1);

		tblPeriodos = new JTable();
		DefaultTableModel modeloPeriodos = new DefaultTableModel(new Object[][] { { 1, "Manhã" }, { 2, "Manhã" },
				{ 3, "Manhã" }, { 4, "Manhã" }, { 5, "Tarde" }, { 6, "Tarde" }, { 7, "Tarde" }, { 8, "Tarde" },
				{ 9, "Noite" }, { 10, "Noite" }, { 11, "Noite" }, { 12, "Noite" }, },
				new String[] { "Periodos", "Turno" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblPeriodos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!tblPeriodos.getSelectionModel().isSelectionEmpty()) {
					btnAgendar.setEnabled(true);
				} else {
					btnAgendar.setEnabled(false);
				}

			}
		});
		tblPeriodos.setModel(modeloPeriodos);
		tblPeriodos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(tblPeriodos);

		btnRemover = new JButton("Remover");
		btnRemover.setEnabled(false);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removerAgendamento();
			}
		});
		btnRemover.setBounds(525, 380, 89, 23);
		pnlAgendamento.add(btnRemover);

		lblInsiraNovaSenha = new JLabel("Insira nova senha:");
		lblInsiraNovaSenha.setBounds(349, 11, 195, 14);
		lblInsiraNovaSenha.setVisible(false);
		pnlAgendamento.add(lblInsiraNovaSenha);

		tfNovaSenha = new JPasswordField();
		tfNovaSenha.setBounds(345, 30, 191, 23);
		tfNovaSenha.setVisible(false);
		pnlAgendamento.add(tfNovaSenha);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterarSenha();
			}
		});
		btnConfirmar.setBounds(499, 60, 115, 30);
		btnConfirmar.setVisible(false);
		pnlAgendamento.add(btnConfirmar);

		lblSenhaAlteradaCom = new JLabel("Senha alterada com sucesso.");
		lblSenhaAlteradaCom.setBounds(341, 92, 273, 14);
		lblSenhaAlteradaCom.setVisible(false);
		pnlAgendamento.add(lblSenhaAlteradaCom);

		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deslogar();
				pnlCadastrar.setVisible(false);
				pnlLogin.setVisible(true);
				pnlAgendamento.setVisible(false);
			}
		});
		btnSair.setBounds(10, 36, 102, 23);
		pnlAgendamento.add(btnSair);

		tfSenha_1 = new JPasswordField();
		tfSenha_1.setBounds(144, 245, 309, 36);
		pnlLogin.add(tfSenha_1);

		tfLogin_1 = new JTextField();
		tfLogin_1.setBounds(144, 177, 309, 36);
		pnlLogin.add(tfLogin_1);
		tfLogin_1.setColumns(10);

		JLabel lblUsuario = new JLabel("Usu\u00E1rio");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUsuario.setBounds(144, 139, 309, 30);
		pnlLogin.add(lblUsuario);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSenha.setBounds(144, 218, 309, 20);
		pnlLogin.add(lblSenha);

		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});

		btnLogar.setBounds(327, 292, 126, 62);
		pnlLogin.add(btnLogar);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pnlCadastrar.setVisible(true);
				pnlLogin.setVisible(false);
				pnlAgendamento.setVisible(false);
			}
		});
		btnCadastrar.setBounds(144, 292, 126, 62);
		pnlLogin.add(btnCadastrar);
		
		JLabel lblLogin_1 = new JLabel("Login");
		lblLogin_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblLogin_1.setBounds(144, 11, 309, 117);
		pnlLogin.add(lblLogin_1);

		pnlCadastrar = new JPanel();
		frmAgendamento.getContentPane().add(pnlCadastrar, "name_201601144603200");
		pnlCadastrar.setLayout(null);

		JLabel lblCadastroDeUsurio = new JLabel("Cadastro de usu\u00E1rio");
		lblCadastroDeUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeUsurio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCadastroDeUsurio.setBounds(10, 11, 604, 50);
		pnlCadastrar.add(lblCadastroDeUsurio);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogin.setBounds(10, 54, 147, 31);
		pnlCadastrar.add(lblLogin);

		tfLogin = new JTextField();
		tfLogin.setBounds(10, 86, 604, 38);
		pnlCadastrar.add(tfLogin);
		tfLogin.setColumns(10);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(10, 161, 147, 31);
		pnlCadastrar.add(lblNome);

		tfNome = new JTextField();
		tfNome.setBounds(10, 194, 604, 38);
		pnlCadastrar.add(tfNome);
		tfNome.setColumns(10);

		JLabel lblSenha2 = new JLabel("Senha");
		lblSenha2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha2.setBounds(10, 267, 147, 31);
		pnlCadastrar.add(lblSenha2);

		btnCadastrar_1 = new JButton("Cadastrar");
		btnCadastrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarUsuario();

			}
		});
		btnCadastrar_1.setBounds(448, 399, 166, 31);
		pnlCadastrar.add(btnCadastrar_1);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pnlLogin.setVisible(true);
				pnlAgendamento.setVisible(false);
				pnlCadastrar.setVisible(false);
			}
		});
		btnVoltar.setBounds(10, 399, 166, 31);
		pnlCadastrar.add(btnVoltar);

		tfSenha = new JPasswordField();
		tfSenha.setBounds(10, 299, 604, 38);
		pnlCadastrar.add(tfSenha);

	}

	/******/
	private void deslogar() {
		usuarioLogado = null;
	}

	private void removerAgendamento() {
		int linha = tblAgendados.getSelectedRow();

		int per = (int) tblAgendados.getValueAt(linha, 0);
		Sala sala = ((Sala) comboSalas.getSelectedItem());

		for (int i = 0; i < ut.agendamentos.size(); i++) {
			if (ut.agendamentos.get(i).getS().getNome().equals(sala.getNome())
					&& (ut.agendamentos.get(i).getPeriodo() == per)
					&& ut.agendamentos.get(i).getP().getLogin().equals(usuarioLogado)) {

				ut.agendamentos.remove(i);
				ut.salvarAgendamentos();

				atualizarTabela();
				return;
			}
		}
		JOptionPane.showMessageDialog(null, "Sem permissão!");
	}

	private void logar() {
		usuarioLogado = tfLogin_1.getText();
		String senha = new String(tfSenha_1.getPassword());

		if (usuarioLogado.isEmpty() || senha.equals("")) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			return;
		}

		for (int i = 0; i < ut.professores.size(); i++) {
			if (ut.professores.get(i).getLogin().compareTo(usuarioLogado) == 0
					&& ut.professores.get(i).getSenha().compareTo(md5(senha)) == 0) {

				String bemvindo = "Bem-vindo, " + usuarioLogado;
				lblBemvindo.setText(bemvindo);

				pnlLogin.setVisible(false);
				pnlAgendamento.setVisible(true);
				pnlCadastrar.setVisible(false);

				return;
			}
		}
		JOptionPane.showMessageDialog(null, "Login ou senha inválidos!");
	}

	private void alterarSenha() {
		String senha = new String(tfNovaSenha.getPassword());

		if (senha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Senha não pode ficar em branco!");
			return;
		}

		for (int i = 0; i < ut.professores.size(); i++) {
			if (ut.professores.get(i).getLogin().compareTo(usuarioLogado) == 0) {

				ut.professores.get(i).setSenha(md5(senha));
				ut.salvarProfessores();

				lblSenhaAlteradaCom.setVisible(true);
				return;
			}
		}
	}

	private void cadastrarUsuario() {
		Professor p;
		try {
			String nome = tfNome.getText();
			String login = tfLogin.getText();
			String senha = new String(tfSenha.getPassword());

			if (nome.isEmpty() || login.isEmpty() || senha.equals("")) {
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
				return;
			}

			for (int i = 0; i < ut.professores.size(); i++) {
				if (ut.professores.get(i).getLogin().compareTo(login) == 0) {
					JOptionPane.showMessageDialog(null, "Já existe usuário cadastro com esse login!");
					return;
				}
			}

			p = new Professor(login, nome, md5(senha));

			ut.professores.add(p);
			ut.salvarProfessores();

			JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
			pnlLogin.setVisible(true);
			pnlAgendamento.setVisible(false);
			pnlCadastrar.setVisible(false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
		}
	}

	public static String md5(String senha) {
		String md5 = null;

		if (null == senha)
			return null;

		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");

			digest.update(senha.getBytes(), 0, senha.length());

			md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}

	private void agendarHorario() {
		int linha = (tblPeriodos.getSelectedRow()) + 1;

		Professor p = null;
		for (int i = 0; i < ut.professores.size(); i++) {
			if (ut.professores.get(i).getLogin().compareTo(usuarioLogado) == 0) {
				p = ut.professores.get(i);
			}
		}

		for (int i = 0; i < ut.agendamentos.size(); i++) {
			if ((ut.agendamentos.get(i).getS().getNome().equals(((Sala) comboSalas.getSelectedItem()).getNome()))
					&& (ut.agendamentos.get(i).getPeriodo() == linha)) {
				JOptionPane.showMessageDialog(null, "Já existe horário agendado no período selecionado");
				return;
			}
		}

		String disciplina = JOptionPane.showInputDialog("Disciplina: ");

		if(!disciplina.isEmpty()) {
			Agendamento a = new Agendamento(p, ((Sala) comboSalas.getSelectedItem()), linha, disciplina);
			ut.agendamentos.add(a);

			ut.salvarAgendamentos();
		}else {
			JOptionPane.showMessageDialog(null, "Disciplina não pode ficar em branco");
			return;
		}
	}

	private void atualizarTabela() {
		tblAgendados.setVisible(true);
		tableModel.setRowCount(0);

		for (int i = 0; i < ut.agendamentos.size(); i++) {
			if (ut.agendamentos.get(i).getS().getNome().equals(((Sala) comboSalas.getSelectedItem()).getNome())) {
				String professor = ut.agendamentos.get(i).getP().getNome();
				String disciplina = ut.agendamentos.get(i).getDisciplina();
				int periodo = ut.agendamentos.get(i).getPeriodo();
				String turno = null;

				switch (periodo) {
				case 1:
				case 2:
				case 3:
				case 4:
					turno = "Manhã";
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					turno = "Tarde";
					break;
				case 9:
				case 10:
				case 11:
				case 12:
					turno = "Tarde";
					break;
				default:
					break;
				}

				Object[] data = { periodo, turno, professor, disciplina };

				tableModel.addRow(data);
			}
		}
	}
}
