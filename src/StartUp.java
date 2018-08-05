import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class StartUp {
	
	JFrame frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					StartUp window = new StartUp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection conn = null;
	public StartUp() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			initialize();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.getContentPane().setFont(new Font("Calibri", Font.BOLD, 21));
		frame.setBounds(50, 50, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		Image logo = new ImageIcon(this.getClass().getResource("/resources/Icon.png")).getImage();
		frame.setIconImage(logo);
			
		JButton buttonExistingSubscriber = new JButton("Existing Subscriber");
		buttonExistingSubscriber.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		buttonExistingSubscriber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExistingSubscriber exist = new ExistingSubscriber();
					frame.dispose();
					exist.setVisible(true);
				
			}
		});
		buttonExistingSubscriber.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		buttonExistingSubscriber.setBounds(131, 300, 250, 30);
		frame.getContentPane().add(buttonExistingSubscriber);
		
		JButton buttonNewSubscriber = new JButton("New Subscriber");
		buttonNewSubscriber.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		buttonNewSubscriber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InsertNew startUp = new InsertNew();
				frame.dispose();
				startUp.setVisible(true);
			}
		});
		buttonNewSubscriber.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		buttonNewSubscriber.setBounds(512, 300, 250, 30);
		frame.getContentPane().add(buttonNewSubscriber);
		
		JButton btnAddBook = new JButton("Add a New Book");
		btnAddBook.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddNewBook add = new AddNewBook();
				frame.dispose();
				add.setVisible(true);
			}
		});
		btnAddBook.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnAddBook.setBounds(131, 400, 250, 30);
		frame.getContentPane().add(btnAddBook);
		
		JButton btnDeleteSubscriber = new JButton("Delete Subscriber");
		btnDeleteSubscriber.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		btnDeleteSubscriber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeleteSubscriber delete = new DeleteSubscriber();
				frame.dispose();
				delete.setVisible(true);
			}
		});
		btnDeleteSubscriber.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnDeleteSubscriber.setBounds(512, 400, 250, 30);
		frame.getContentPane().add(btnDeleteSubscriber);
		
		JLabel labellogo = new JLabel("");
		labellogo.setBounds(122, 50, 650, 200);
		frame.getContentPane().add(labellogo);
		Image img = new ImageIcon(this.getClass().getResource("resources/Logo.png")).getImage();
		labellogo.setIcon(new ImageIcon(img));
		
		JButton btnNewDetails = new JButton("Project Information");
		btnNewDetails.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		btnNewDetails.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnNewDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Info info = new Info();
				frame.dispose();
				info.setVisible(true);
			}
		});
		btnNewDetails.setBounds(650, 500, 225, 30);
		frame.getContentPane().add(btnNewDetails);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnExit.setBorder(new LineBorder(SystemColor.controlDkShadow, 2, true));
		btnExit.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnExit.setBounds(800, 16, 75, 30);
		frame.getContentPane().add(btnExit);
		
		JLabel label = new JLabel("<html>Done by: Bhargavi N A - 1RN15CS035, Gouthami S - 1RN15CS044</html>");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		label.setBounds(25, 500, 600, 30);
		frame.getContentPane().add(label);
	}
}