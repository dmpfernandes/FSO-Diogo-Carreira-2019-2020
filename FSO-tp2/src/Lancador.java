import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;

import TrabalhoPratico2.canalComunicacao.CanalComunicacoes;

import javax.swing.event.ListSelectionEvent;

public class Lancador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtAreaLogs;
	private JButton btnAddCoreografo, btnRemoverCoreografo, btnIniciarTodosCoreografos, btnAddDancarino, btnRemoverDancarino, btnIniciartodosDancarinos, btnResetLogs;
	private JCheckBox chckbxActivaTodosCoreografos, chckbxActivaTodosDancarinos;
	private JList txtAreaDancarinos, txtAreaCoreografos;
	private HashMap<String, Dancarino> dancarinos;
	private HashMap<String, Coreografo> coreografos;
	private List<String> listDancarinos;
	private List<String> listCoreografos;
	
	private String dancarinoSelecionado;
	private String coreografoSelecionado;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lancador frame = new Lancador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Lancador() {
		initialize();
		CanalComunicacoes canal = new CanalComunicacoes("teste.txt");
		listDancarinos = new ArrayList<String>();
		listCoreografos = new ArrayList<String>();
		dancarinos = new HashMap<String, Dancarino>();
		coreografos = new HashMap<String, Coreografo>();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		contentPane = new JPanel();
		setBounds(100, 100, 650, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel txtFldCoreografos = new JLabel("Coreógrafos");
		txtFldCoreografos.setBounds(25, 22, 96, 16);
		contentPane.add(txtFldCoreografos);
		
		JLabel lblDanarinos = new JLabel("Dançarinos");
		lblDanarinos.setBounds(350, 22, 96, 16);
		contentPane.add(lblDanarinos);
		
		btnAddCoreografo = new JButton("Adicionar");
		btnAddCoreografo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listCoreografos.add("Coreografo-" + getNextIndex("dancarino"));
			}
		});
		btnAddCoreografo.setBounds(25, 311, 193, 38);
		contentPane.add(btnAddCoreografo);
		
		btnRemoverCoreografo = new JButton("Remover");
		btnRemoverCoreografo.setBounds(25, 361, 193, 38);
		btnRemoverCoreografo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(coreografoSelecionado != null) {
					coreografos.remove(coreografoSelecionado);
				}
			}
		});
		contentPane.add(btnRemoverCoreografo);
		
		btnIniciarTodosCoreografos = new JButton("Iniciar todos");
		btnIniciarTodosCoreografos.setBounds(25, 411, 193, 38);
		btnIniciarTodosCoreografos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listCoreografos.stream().forEach(c -> {
					coreografos.put(c, new Coreografo());
				});
			}
		});
		contentPane.add(btnIniciarTodosCoreografos);
		
		chckbxActivaTodosCoreografos = new JCheckBox("Activa todos");
		chckbxActivaTodosCoreografos.setBounds(25, 462, 128, 23);
		chckbxActivaTodosCoreografos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				coreografos.entrySet().stream().forEach(c -> ((Coreografo)c.getValue().run()));
				coreografos.entrySet().stream().forEach(c -> {
					Thread t = new Thread(c.getValue());
					t.start();
				});
			}
		});
		contentPane.add(chckbxActivaTodosCoreografos);
		
		btnAddDancarino = new JButton("Adicionar");
		btnAddDancarino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listDancarinos.add("Dancarino-" + getNextIndex("dancarino"));
			}
		});
		btnAddDancarino.setBounds(350, 311, 193, 38);
		contentPane.add(btnAddDancarino);
		
		btnRemoverDancarino = new JButton("Remover");
		btnRemoverDancarino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dancarinoSelecionado != null) {
					dancarinos.remove(dancarinoSelecionado);
				}
			}
		});
		btnRemoverDancarino.setBounds(350, 361, 193, 38);
		contentPane.add(btnRemoverDancarino);
		
		btnIniciartodosDancarinos = new JButton("IniciarTodos");
		btnIniciartodosDancarinos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listDancarinos.forEach(d -> dancarinos.put(d, new Dancarino()));
				dancarinos.entrySet().forEach(d -> d.getValue().startRobot());
			}
		});
		btnIniciartodosDancarinos.setBounds(350, 411, 193, 38);
		contentPane.add(btnIniciartodosDancarinos);
		
		chckbxActivaTodosDancarinos = new JCheckBox("Activa todos");
		chckbxActivaTodosDancarinos.setBounds(350, 462, 128, 23);
		chckbxActivaTodosDancarinos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dancarinos.entrySet().stream().forEach(c -> c.getValue().run());
			}
		});
		contentPane.add(chckbxActivaTodosDancarinos);
		
		btnResetLogs = new JButton("Limpa Logging");
		btnResetLogs.setBounds(25, 518, 567, 38);
		btnResetLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAreaLogs.setText("");
			}
		});
		contentPane.add(btnResetLogs);
		
		txtAreaLogs = new JTextArea();
		txtAreaLogs.setBounds(25, 568, 567, 78);
		contentPane.add(txtAreaLogs);
		txtAreaLogs.setColumns(10);
		
		txtAreaDancarinos = new JList<String>();
		txtAreaDancarinos.setBounds(350, 79, 193, 185);
		txtAreaDancarinos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				dancarinoSelecionado = (String) e.getSource();
			}
		});
		contentPane.add(txtAreaDancarinos);
		
		txtAreaCoreografos = new JList<String>();
		txtAreaCoreografos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				coreografoSelecionado = (String) e.getSource();
			}
		});
		txtAreaCoreografos.setBounds(25, 79, 193, 185);
		contentPane.add(txtAreaCoreografos);
	}
	
	public int getNextIndex(String tipo) {
		return (tipo.equals("coreografo")) ? coreografos.size() : dancarinos.size();
		
	}
}
