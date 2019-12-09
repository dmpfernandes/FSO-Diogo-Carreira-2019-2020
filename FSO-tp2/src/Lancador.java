import java.awt.EventQueue;

import javax.swing.JFrame;
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
import java.util.Vector;
import java.awt.event.ActionEvent;
import TrabalhoPratico2.canalComunicacao.CanalComunicacoes;

public class Lancador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtAreaLogs;
	private JButton btnAddCoreografo, btnRemoverCoreografo, btnIniciarTodosCoreografos, btnAddDancarino,
			btnRemoverDancarino, btnIniciartodosDancarinos, btnResetLogs;
	private JCheckBox chckbxActivaTodosCoreografos, chckbxActivaTodosDancarinos;
	private JList<String> jListDancarinos, jListCoreografos;
	private HashMap<String, Dancarino> dancarinos;
	private HashMap<String, Coreografo> coreografos;
	private Vector<String> coreografoVector, dancarinoVector;
	private List<String> listDancarinos;
	private List<String> listCoreografos;

	private String dancarinoSelecionado;
	private String coreografoSelecionado;
	private CanalComunicacoes canal;
	private HashMap<String, Thread> coreografosThread;
	private HashMap<String, Thread> dancarinosThread;
	private int counterDancarino, counterCoreografo = 0;

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
		canal = new CanalComunicacoes("teste.txt");
		Thread threadCanal = new Thread(canal);
		threadCanal.start();

		listDancarinos = new ArrayList<String>();
		listCoreografos = new ArrayList<String>();
		dancarinos = new HashMap<String, Dancarino>();
		coreografoVector = new Vector<String>();
		dancarinoVector = new Vector<String>();
		coreografos = new HashMap<String, Coreografo>();
		coreografosThread = new HashMap<String, Thread>();
		dancarinosThread = new HashMap<String, Thread>();
		initialize();
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
				int nextIndex = getNextIndex("coreografo");
				listCoreografos.add("Coreografo-" + nextIndex);
				coreografoVector.addElement("Coreografo-" + nextIndex);
				jListCoreografos.setListData(coreografoVector);
			}
		});
		btnAddCoreografo.setBounds(25, 311, 193, 38);
		contentPane.add(btnAddCoreografo);

		btnRemoverCoreografo = new JButton("Remover");
		btnRemoverCoreografo.setBounds(25, 361, 193, 38);
		btnRemoverCoreografo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jListCoreografos.getSelectedIndex() != -1) {
					int elemIndex = jListCoreografos.getSelectedIndex();
					System.out.println("Lista pre: " + listCoreografos.toString());
					coreografos.get(listCoreografos.get(elemIndex)).killApp();
					coreografos.remove(listCoreografos.get(elemIndex));
					coreografosThread.remove(listCoreografos.get(elemIndex));
					listCoreografos.remove(elemIndex);
					coreografoVector.remove(elemIndex);
					System.out.println("Lista pos: " + listCoreografos.toString());
					jListCoreografos.setListData(coreografoVector);
				}
			}
		});
		contentPane.add(btnRemoverCoreografo);

		btnIniciarTodosCoreografos = new JButton("Iniciar todos");
		btnIniciarTodosCoreografos.setBounds(25, 411, 193, 38);
		btnIniciarTodosCoreografos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listCoreografos.stream().forEach(c -> {
					if (!coreografos.containsKey(c)) {
						coreografos.put(c, new Coreografo(canal));
					}
				});
			}
		});
		contentPane.add(btnIniciarTodosCoreografos);

		chckbxActivaTodosCoreografos = new JCheckBox("Activa todos");
		chckbxActivaTodosCoreografos.setBounds(25, 462, 128, 23);
		chckbxActivaTodosCoreografos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				coreografos.entrySet().stream().forEach(c -> ((Coreografo)c.getValue().run()));
				if (chckbxActivaTodosCoreografos.isSelected()) {
					coreografos.entrySet().stream().forEach(c -> {
						if (coreografosThread.containsKey(c.getKey())) {
							c.getValue().startCommands();
						} else {
							Thread t = new Thread(c.getValue());
							t.setName(c.getKey());
							t.start();
							coreografosThread.put(c.getKey(), t);
						}

					});
				} else {
					coreografos.entrySet().stream().forEach(c -> {
						c.getValue().stopCommands();

					});
				}

			}
		});
		contentPane.add(chckbxActivaTodosCoreografos);

		btnAddDancarino = new JButton("Adicionar");
		btnAddDancarino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextIndex = getNextIndex("dancarino");
				listDancarinos.add("Dancarino-" + nextIndex);
				dancarinoVector.addElement("Dancarino-" + nextIndex);
				jListDancarinos.setListData(dancarinoVector);
			}
		});
		btnAddDancarino.setBounds(350, 311, 193, 38);
		contentPane.add(btnAddDancarino);

		btnRemoverDancarino = new JButton("Remover");
		btnRemoverDancarino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jListDancarinos.getSelectedIndex() != -1) {
					int elemIndex = jListDancarinos.getSelectedIndex();
					System.out.println("Lista pre: " + listDancarinos.toString());
					dancarinos.get(listDancarinos.get(elemIndex)).killApp();
					dancarinos.remove(listDancarinos.get(elemIndex));
					dancarinosThread.remove(listDancarinos.get(elemIndex));
					listDancarinos.remove(elemIndex);
					dancarinoVector.remove(elemIndex);
					System.out.println("Lista pos: " + listDancarinos.toString());
					jListDancarinos.setListData(dancarinoVector);
				}
			}
		});
		btnRemoverDancarino.setBounds(350, 361, 193, 38);
		contentPane.add(btnRemoverDancarino);

		btnIniciartodosDancarinos = new JButton("IniciarTodos");
		btnIniciartodosDancarinos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listDancarinos.stream().forEach(d -> {
					if (!dancarinos.containsKey(d)) {
						Dancarino dancarino = new Dancarino(canal);
						dancarinos.put(d, dancarino);
						dancarino.startRobot();
					}
				});
			}
		});
		btnIniciartodosDancarinos.setBounds(350, 411, 193, 38);
		contentPane.add(btnIniciartodosDancarinos);

		chckbxActivaTodosDancarinos = new JCheckBox("Activa todos");
		chckbxActivaTodosDancarinos.setBounds(350, 462, 128, 23);
		chckbxActivaTodosDancarinos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxActivaTodosDancarinos.isSelected()) {
					dancarinos.entrySet().stream().forEach(d -> {
						if (dancarinosThread.containsKey(d.getKey())) {
							d.getValue().startCommands();
						} else {
							Thread t = new Thread(d.getValue());
							t.setName(d.getKey());
							t.start();
							dancarinosThread.put(d.getKey(), t);
						}

					});
				} else {
					dancarinos.entrySet().stream().forEach(d -> {
						d.getValue().stopCommands();
					});
				}
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

		jListDancarinos = new JList<String>(dancarinoVector);
		jListDancarinos.setBounds(350, 79, 193, 185);
		contentPane.add(jListDancarinos);

		jListCoreografos = new JList<String>(coreografoVector);
		jListCoreografos.setBounds(25, 79, 193, 185);
		contentPane.add(jListCoreografos);
	}

	public int getNextIndex(String tipo) {
		if(tipo.equals("coreografo")) {
			return counterCoreografo++;
		}else {
			return counterDancarino++;
		}


	}
}
