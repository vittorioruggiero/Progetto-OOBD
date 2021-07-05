package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import entity.Nazionale;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class NazionaleFrame extends JFrame {
	private Controller controller;
	private JPanel contentPane;
	private JTable table;
	private JTextField nomeTF;
	private JTextField valoreGettoneTF;

	public NazionaleFrame(Controller controller) {
		setTitle("NazionaleFrame");
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nazionaliLabel = new JLabel("Nazionali");
		nazionaliLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		nazionaliLabel.setBounds(219, 23, 73, 13);
		contentPane.add(nazionaliLabel);
		
		JButton indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NazionaleFrame.this.controller.apriHomeFrame(NazionaleFrame.this);
			}
		});
		indietroButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		indietroButton.setBounds(434, 318, 93, 19);
		contentPane.add(indietroButton);
		
		//Codice di scrollPane scritto a mano per evitare problemi
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 517, 131);
		this.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("SansSerif", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
				}
			)
					{
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
					}
			);
		scrollPane.setViewportView(table);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeLabel.setBounds(10, 199, 93, 19);
		contentPane.add(nomeLabel);
		
		JLabel valoreGettoneLabel = new JLabel("Valore gettone");
		valoreGettoneLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		valoreGettoneLabel.setBounds(10, 226, 93, 19);
		contentPane.add(valoreGettoneLabel);
		
		nomeTF = new JTextField();
		nomeTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nomeTF.setBounds(113, 199, 96, 19);
		contentPane.add(nomeTF);
		nomeTF.setColumns(10);
		
		valoreGettoneTF = new JTextField();
		valoreGettoneTF.setFont(new Font("SansSerif", Font.PLAIN, 14));
		valoreGettoneTF.setBounds(113, 226, 96, 20);
		contentPane.add(valoreGettoneTF);
		valoreGettoneTF.setColumns(10);
		
		JButton inserisciButton = new JButton("Inserisci");
		inserisciButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		inserisciButton.setBounds(243, 199, 93, 19);
		contentPane.add(inserisciButton);
		
		JButton rimuoviButton = new JButton("Rimuovi");
		rimuoviButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rimuoviButton.setBounds(243, 226, 93, 19);
		contentPane.add(rimuoviButton);
		
		JButton modificaButton = new JButton("Modifica");
		modificaButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		modificaButton.setBounds(243, 255, 93, 19);
		contentPane.add(modificaButton);
		
		JLabel ordinaLabel = new JLabel("Ordina per");
		ordinaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		ordinaLabel.setBounds(243, 284, 93, 19);
		contentPane.add(ordinaLabel);
		
		JComboBox ordinaComboBox = new JComboBox();
		ordinaComboBox.setBounds(243, 306, 93, 19);
		contentPane.add(ordinaComboBox);
		
	}
	
	public void setNazionali(List<Nazionale> listaNazionali) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.addColumn("Nome");
		model.addColumn("Valore gettone");
		for(int i=0; i<listaNazionali.size(); i++) model.addRow(new Object[] {listaNazionali.get(i).getNome(), listaNazionali.get(i).getValoreGettone()});
	}
}
