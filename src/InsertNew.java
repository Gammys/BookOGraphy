import java.awt.*;
//import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class InsertNew extends JFrame {

	private JPanel contentPaneInsertNew;
	private JTextField textFieldcardno;
	private JTextField textFieldname;
	private JTextField textFieldaddress;
	private JTextField textFieldphno;
	private JTextField textFieldsubdur;
	private JTextField textFieldsubamt;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertNew frame = new InsertNew();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection conn = null;
	public InsertNew() {
		Image logo = new ImageIcon(this.getClass().getResource("/resources/Icon.png")).getImage();
		setIconImage(logo);
		try {
			conn = dbConnection.returnConnection();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(50, 50, 900, 600);
			contentPaneInsertNew = new JPanel();
			contentPaneInsertNew.setForeground(Color.BLACK);
			contentPaneInsertNew.setBackground(SystemColor.menu);
			contentPaneInsertNew.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPaneInsertNew);
			contentPaneInsertNew.setLayout(null);
			setResizable(false);
			
			JLabel labelLogo = new JLabel("");
			labelLogo.setBounds(800, 475, 75, 75);
			contentPaneInsertNew.add(labelLogo);
			Image img = new ImageIcon(this.getClass().getResource("/resources/IconEvery.png")).getImage();
			labelLogo.setIcon(new ImageIcon(img));
			
			JLabel lblcardno = new JLabel("Card No");
			lblcardno.setForeground(Color.BLACK);
			lblcardno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			lblcardno.setBounds(115, 100, 200, 30);
			contentPaneInsertNew.add(lblcardno);
			
			textFieldcardno = new JTextField();
			textFieldcardno.setForeground(Color.BLACK);
			textFieldcardno.setBackground(Color.WHITE);
			textFieldcardno.setEditable(false);
			textFieldcardno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			textFieldcardno.setBounds(320, 100, 150, 30);
			contentPaneInsertNew.add(textFieldcardno);
			textFieldcardno.setColumns(10);
			
			JLabel lblname = new JLabel("Name");
			lblname.setForeground(Color.BLACK);
			lblname.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			lblname.setBounds(115, 150, 200, 30);
			contentPaneInsertNew.add(lblname);
			
			textFieldname = new JTextField();
			textFieldname.setForeground(Color.BLACK);
			textFieldname.setBackground(Color.WHITE);
			textFieldname.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			textFieldname.setBounds(320, 150, 150, 30);
			contentPaneInsertNew.add(textFieldname);
			textFieldname.setColumns(10);
			
			JLabel lbladdress = new JLabel("Address");
			lbladdress.setForeground(Color.BLACK);
			lbladdress.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			lbladdress.setBounds(115, 200, 200, 30);
			contentPaneInsertNew.add(lbladdress);
			
			textFieldaddress = new JTextField();
			textFieldaddress.setForeground(Color.BLACK);
			textFieldaddress.setBackground(Color.WHITE);
			textFieldaddress.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			textFieldaddress.setBounds(320, 200, 300, 30);
			contentPaneInsertNew.add(textFieldaddress);
			textFieldaddress.setColumns(10);
			
			JLabel lblphno = new JLabel("Phone No");
			lblphno.setForeground(Color.BLACK);
			lblphno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			lblphno.setBounds(115, 250, 200, 30);
			contentPaneInsertNew.add(lblphno);
			
			JLabel lblsub_dur = new JLabel("Subscription Duration");
			lblsub_dur.setForeground(Color.BLACK);
			lblsub_dur.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			lblsub_dur.setBounds(115, 300, 200, 30);
			contentPaneInsertNew.add(lblsub_dur);
			
			JLabel lblsub_amt = new JLabel("Amount");
			lblsub_amt.setForeground(Color.BLACK);
			lblsub_amt.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			lblsub_amt.setBounds(115, 350, 200, 30);
			contentPaneInsertNew.add(lblsub_amt);
			
			textFieldphno = new JTextField();
			textFieldphno.setForeground(Color.BLACK);
			textFieldphno.setBackground(Color.WHITE);
			textFieldphno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			textFieldphno.setBounds(320, 250, 150, 30);
			contentPaneInsertNew.add(textFieldphno);
			textFieldphno.setColumns(10);
			
			textFieldsubdur = new JTextField();
			textFieldsubdur.setForeground(Color.BLACK);
			textFieldsubdur.setBackground(Color.WHITE);
			textFieldsubdur.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			textFieldsubdur.setBounds(320, 300, 150, 30);
			contentPaneInsertNew.add(textFieldsubdur);
			textFieldsubdur.setColumns(10);
			
			textFieldsubamt = new JTextField();
			textFieldsubamt.setForeground(Color.BLACK);
			textFieldsubamt.setBackground(Color.WHITE);
			textFieldsubamt.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			textFieldsubamt.setBounds(320, 350, 150, 30);
			contentPaneInsertNew.add(textFieldsubamt);
			textFieldsubamt.setColumns(10);
			
			int cardno = 0;
			try {
				Connection conn;
				conn = dbConnection.returnConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT max(card_no) FROM subscription;");
				while(rs.next())
					cardno = rs.getInt("max(card_no)");
				st.close(); rs.close(); conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			textFieldcardno.setText(Integer.toString(++cardno));
			
			JButton btnInsert = new JButton("Add New Subscriber");
			btnInsert.setBorder(new LineBorder(Color.WHITE, 2, true));
			btnInsert.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Connection conn;
					try {
						conn = dbConnection.returnConnection();
						String query = "insert into subscription values(?,?,?,?,?,?);";
						PreparedStatement pst = conn.prepareStatement(query);
						pst.setString(1, textFieldcardno.getText());
						pst.setString(2, textFieldname.getText());
						pst.setString(3, textFieldaddress.getText());
						pst.setString(4, textFieldphno.getText());
						pst.setString(5, textFieldsubdur.getText());
						pst.setString(6, textFieldsubamt.getText());
						pst.execute(); pst.close();
						query = "INSERT INTO card (card_no) VALUES (?);";
						pst = conn.prepareStatement(query);
						pst.setString(1, textFieldcardno.getText());
						pst.execute();
						pst.close(); conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					StartUp insert = new StartUp();
					dispose();
					insert.frame.setVisible(true);
				}
			});
			btnInsert.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			btnInsert.setBounds(176, 430, 250, 30);
			contentPaneInsertNew.add(btnInsert);
			
			JButton btnCancel = new JButton("Cancel");
			btnCancel.setBorder(new LineBorder(Color.WHITE, 2, true));
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					StartUp cancel = new StartUp();
					dispose();
					cancel.frame.setVisible(true);
				}
			});
			btnCancel.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			btnCancel.setBounds(602, 430, 115, 30);
			contentPaneInsertNew.add(btnCancel);
			
			JButton btnCalculateAmount = new JButton("Calculate Amount");
			btnCalculateAmount.setBorder(new LineBorder(Color.WHITE, 2, true));
			btnCalculateAmount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int subdur = Integer.parseInt(textFieldsubdur.getText());
					textFieldsubamt.setText(Integer.toString(subdur * 100));
				}
			});
			btnCalculateAmount.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
			btnCalculateAmount.setBounds(500, 300, 200, 30);
			contentPaneInsertNew.add(btnCalculateAmount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
