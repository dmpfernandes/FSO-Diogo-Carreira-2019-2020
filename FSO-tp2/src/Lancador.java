import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class Lancador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextArea txtAreaLogs;
	private JButton btnAddCoreografo, btnRemoverCoreografo, btnIniciarTodosCoreografos, btnAddDancarino, btnRemoverDancarino, btnIniciartodosDancarinos, btnResetLogs;
	private JCheckBox chckbxActivaTodosCoreografos, chckbxActivaTodosCoreografosDancarino;
	private JTextArea txtAreaDancarinos, txtAreaCoreografos;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lancador window = new Lancador();
					window.frame.setVisible(true);
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel txtFldCoreografos = new JLabel("Coreógrafos");
		txtFldCoreografos.setBounds(25, 22, 96, 16);
		frame.getContentPane().add(txtFldCoreografos);
		
		JLabel lblDanarinos = new JLabel("Dançarinos");
		lblDanarinos.setBounds(350, 22, 96, 16);
		frame.getContentPane().add(lblDanarinos);
		
		btnAddCoreografo = new JButton("Adicionar");
		btnAddCoreografo.setBounds(25, 311, 193, 38);
		frame.getContentPane().add(btnAddCoreografo);
		
		btnRemoverCoreografo = new JButton("Remover");
		btnRemoverCoreografo.setBounds(25, 361, 193, 38);
		frame.getContentPane().add(btnRemoverCoreografo);
		
		btnIniciarTodosCoreografos = new JButton("Iniciar todos");
		btnIniciarTodosCoreografos.setBounds(25, 411, 193, 38);
		frame.getContentPane().add(btnIniciarTodosCoreografos);
		
		chckbxActivaTodosCoreografos = new JCheckBox("Activa todos");
		chckbxActivaTodosCoreografos.setBounds(25, 462, 128, 23);
		frame.getContentPane().add(chckbxActivaTodosCoreografos);
		
		btnAddDancarino = new JButton("Adicionar");
		btnAddDancarino.setBounds(350, 311, 193, 38);
		frame.getContentPane().add(btnAddDancarino);
		
		btnRemoverDancarino = new JButton("Remover");
		btnRemoverDancarino.setBounds(350, 361, 193, 38);
		frame.getContentPane().add(btnRemoverDancarino);
		
		btnIniciartodosDancarinos = new JButton("IniciarTodos");
		btnIniciartodosDancarinos.setBounds(350, 411, 193, 38);
		frame.getContentPane().add(btnIniciartodosDancarinos);
		
		chckbxActivaTodosCoreografosDancarino = new JCheckBox("Activa todos");
		chckbxActivaTodosCoreografosDancarino.setBounds(350, 462, 128, 23);
		frame.getContentPane().add(chckbxActivaTodosCoreografosDancarino);
		
		btnResetLogs = new JButton("Limpa Logging");
		btnResetLogs.setBounds(25, 518, 567, 38);
		frame.getContentPane().add(btnResetLogs);
		
		txtAreaLogs = new JTextArea();
		txtAreaLogs.setBounds(25, 568, 567, 78);
		frame.getContentPane().add(txtAreaLogs);
		txtAreaLogs.setColumns(10);
		
		txtAreaDancarinos = new JTextArea();
		txtAreaDancarinos.setBounds(350, 79, 193, 185);
		frame.getContentPane().add(txtAreaDancarinos);
		
		txtAreaCoreografos = new JTextArea();
		txtAreaCoreografos.setBounds(25, 79, 193, 185);
		frame.getContentPane().add(txtAreaCoreografos);
	}
}
