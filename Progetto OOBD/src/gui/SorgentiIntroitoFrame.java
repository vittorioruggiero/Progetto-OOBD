package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SorgentiIntroitoFrame extends JFrame {
	private Controller controller;
	private JPanel contentPane;
	private JTable table;
	private JButton indietroButton;
	private JComboBox<String> ordinaComboBox;

	public SorgentiIntroitoFrame(Controller controller) {
		setTitle("SorgentiIntroitoFrame");
		setResizable(false);
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel sorgentiIntroitoLabel = new JLabel("Sorgenti di introito");
		sorgentiIntroitoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		sorgentiIntroitoLabel.setBounds(199, 15, 137, 19);
		contentPane.add(sorgentiIntroitoLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 512, 131);
		this.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("SansSerif", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		
		indietroButton = new JButton("Indietro");
		indietroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.riapriAtletaFrame();
			}
		});
		indietroButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		indietroButton.setBounds(416, 231, 93, 19);
		contentPane.add(indietroButton);
		
		JLabel ordinaLabel = new JLabel("Ordina per");
		ordinaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		ordinaLabel.setBounds(280, 210, 93, 19);
		contentPane.add(ordinaLabel);
		
		ordinaComboBox = new JComboBox<String>();
		ordinaComboBox.setFont(new Font("SansSerif", Font.PLAIN, 10));
		ordinaComboBox.setBounds(280, 231, 110, 19);
		ordinaComboBox.addItem("CodiceFiscale");
		ordinaComboBox.addItem("GuadagniDaNazionale");
		ordinaComboBox.addItem("GuadagniDaClub");
		ordinaComboBox.addItem("GuadagniDaSponsor");
		contentPane.add(ordinaComboBox);
		ordinaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ricaricaSorgentiIntroito();
			}
		});
		}
	
	public void setSorgentiIntroito(ArrayList<ArrayList<Object>> listaSorgentiIntroito) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.addColumn("Codice fiscale");
		model.addColumn("Guadagni da nazionale");
		model.addColumn("Guadagni da club");
		model.addColumn("Guadagni da sponsor");
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(55);
		
		for(int i=0; i<listaSorgentiIntroito.size(); i++) model.addRow(new Object[] {
				listaSorgentiIntroito.get(i).get(0),
				listaSorgentiIntroito.get(i).get(1),
				listaSorgentiIntroito.get(i).get(2),
				listaSorgentiIntroito.get(i).get(3)
		});
	}
	
	public void ricaricaSorgentiIntroito() {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		table.setEnabled(false);
		model.setRowCount(0);
		model.setColumnCount(0);
		controller.setSorgentiIntroitoInOrdine((String) ordinaComboBox.getSelectedItem());
		table.setEnabled(true);
	}
}
