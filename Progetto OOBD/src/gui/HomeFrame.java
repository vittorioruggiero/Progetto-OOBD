package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomeFrame extends JFrame {
	private Controller controller;
	private JPanel contentPane;

	public HomeFrame(Controller controller) {
		setTitle("HomeFrame");
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel selezioneLabel = new JLabel("Seleziona cosa vuoi gestire");
		selezioneLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		selezioneLabel.setBounds(126, 10, 202, 19);
		contentPane.add(selezioneLabel);
		
		JButton procuratoriButton = new JButton("Procuratori");
		procuratoriButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		procuratoriButton.setBounds(56, 39, 110, 27);
		contentPane.add(procuratoriButton);
		
		JButton atletiButton = new JButton("Atleti");
		atletiButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		atletiButton.setBounds(56, 76, 110, 27);
		contentPane.add(atletiButton);
		
		JButton nazionaliButton = new JButton("Nazionali");
		nazionaliButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.apriNazionaleFrame();
			}
		});
		nazionaliButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		nazionaliButton.setBounds(176, 39, 110, 27);
		contentPane.add(nazionaliButton);
		
		JButton contrattiButton = new JButton("Contratti");
		contrattiButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		contrattiButton.setBounds(176, 76, 110, 27);
		contentPane.add(contrattiButton);
		
		JButton clubButton = new JButton("Club");
		clubButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		clubButton.setBounds(296, 39, 110, 27);
		contentPane.add(clubButton);
		
		JButton sponsorButton = new JButton("Sponsor");
		sponsorButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		sponsorButton.setBounds(296, 76, 110, 27);
		contentPane.add(sponsorButton);
	}

}
