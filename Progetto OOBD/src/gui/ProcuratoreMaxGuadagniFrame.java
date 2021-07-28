package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

public class ProcuratoreMaxGuadagniFrame extends JFrame {
	private Controller controller;
	private JPanel contentPane;
	private JTable table;
	private JButton indietroButton;
	private JComboBox<String> ordinaComboBox;

	public ProcuratoreMaxGuadagniFrame(Controller controller) {
		setTitle("ProcuratoreMaxGuadagniFrame");
		setResizable(false);
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 749, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel procuratoreMaxGuadagniLabel = new JLabel("Max guadagni per procuratore");
		procuratoreMaxGuadagniLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		procuratoreMaxGuadagniLabel.setBounds(275, 15, 216, 19);
		contentPane.add(procuratoreMaxGuadagniLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 714, 131);
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
				controller.riapriProcuratoreFrame();
			}
		});
		indietroButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		indietroButton.setBounds(631, 229, 93, 19);
		contentPane.add(indietroButton);
		
		JLabel ordinaLabel = new JLabel("Ordina per");
		ordinaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		ordinaLabel.setBounds(498, 210, 93, 19);
		contentPane.add(ordinaLabel);
		
		ordinaComboBox = new JComboBox<String>();
		ordinaComboBox.setFont(new Font("SansSerif", Font.PLAIN, 10));
		ordinaComboBox.setBounds(498, 231, 123, 19);
		ordinaComboBox.addItem("Procuratore");
		ordinaComboBox.addItem("Atleta_max_guadagno");
		ordinaComboBox.addItem("Max_guadagno_da_atleta");
		ordinaComboBox.addItem("Club_max_guadagno");
		ordinaComboBox.addItem("Max_guadagno_da_club");
		contentPane.add(ordinaComboBox);
		ordinaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ricaricaProcuratoriMaxGuadagni();
			}
		});
		}
	
	public void setProcuratoreMaxGuadagni(ArrayList<ArrayList<Object>> listaProcuratoriMaxGuadagni) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.addColumn("Procuratore");
		model.addColumn("Atleta max guadagno");
		model.addColumn("Max guadagno da atleta");
		model.addColumn("Club max guadagno");
		model.addColumn("Max guadagno da club");
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		
		for(int i=0; i<listaProcuratoriMaxGuadagni.size(); i++) model.addRow(new Object[] {
				listaProcuratoriMaxGuadagni.get(i).get(0),
				listaProcuratoriMaxGuadagni.get(i).get(1),
				listaProcuratoriMaxGuadagni.get(i).get(2),
				listaProcuratoriMaxGuadagni.get(i).get(3),
				listaProcuratoriMaxGuadagni.get(i).get(4)
		});
	}
	
	public void ricaricaProcuratoriMaxGuadagni() {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		table.setEnabled(false);
		model.setRowCount(0);
		model.setColumnCount(0);
		controller.setProcuratoriMaxGuadagniInOrdine((String) ordinaComboBox.getSelectedItem());
		table.setEnabled(true);
	}
}
