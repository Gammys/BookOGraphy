import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class RenewSubscription extends JFrame {

	private JPanel contentPaneRenewSubscription;
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
					RenewSubscription frame = new RenewSubscription();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RenewSubscription() {
		Image logo = new ImageIcon(this.getClass().getResource("/resources/Icon.png")).getImage();
		setIconImage(logo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 900, 600);
		contentPaneRenewSubscription = new JPanel();
		contentPaneRenewSubscription.setBackground(SystemColor.menu);
		contentPaneRenewSubscription.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneRenewSubscription);
		contentPaneRenewSubscription.setLayout(null);
		setResizable(false);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setBounds(800, 475, 75, 75);
		contentPaneRenewSubscription.add(labelLogo);
		Image img = new ImageIcon(this.getClass().getResource("/resources/IconEvery.png")).getImage();
		labelLogo.setIcon(new ImageIcon(img));

		JLabel lblcardno = new JLabel("Card No");
		lblcardno.setForeground(Color.BLACK);
		lblcardno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblcardno.setBounds(115, 100, 200, 30);
		contentPaneRenewSubscription.add(lblcardno);
		
		textFieldcardno = new JTextField();
		textFieldcardno.setForeground(Color.BLACK);
		textFieldcardno.setBackground(Color.WHITE);
		textFieldcardno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldcardno.setBounds(320, 100, 150, 30);
		contentPaneRenewSubscription.add(textFieldcardno);
		textFieldcardno.setColumns(10);
		textFieldcardno.setText(ExistingSubscriber.id);
		
		JLabel lblname = new JLabel("Name");
		lblname.setForeground(Color.BLACK);
		lblname.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblname.setBounds(115, 150, 200, 30);
		contentPaneRenewSubscription.add(lblname);
		
		textFieldname = new JTextField();
		textFieldname.setForeground(Color.BLACK);
		textFieldname.setBackground(Color.WHITE);
		textFieldname.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldname.setBounds(320, 150, 150, 30);
		contentPaneRenewSubscription.add(textFieldname);
		textFieldname.setColumns(10);
		
		JLabel lbladdress = new JLabel("Address");
		lbladdress.setForeground(Color.BLACK);
		lbladdress.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lbladdress.setBounds(115, 200, 200, 30);
		contentPaneRenewSubscription.add(lbladdress);
		
		textFieldaddress = new JTextField();
		textFieldaddress.setForeground(Color.BLACK);
		textFieldaddress.setBackground(Color.WHITE);
		textFieldaddress.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldaddress.setBounds(320, 200, 300, 30);
		contentPaneRenewSubscription.add(textFieldaddress);
		textFieldaddress.setColumns(10);
		
		JLabel lblphno = new JLabel("Phone No");
		lblphno.setForeground(Color.BLACK);
		lblphno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblphno.setBounds(115, 250, 200, 30);
		contentPaneRenewSubscription.add(lblphno);
		
		JLabel lblsub_dur = new JLabel("Subscription Duration");
		lblsub_dur.setForeground(Color.BLACK);
		lblsub_dur.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblsub_dur.setBounds(115, 300, 200, 30);
		contentPaneRenewSubscription.add(lblsub_dur);
		
		JLabel lblsub_amt = new JLabel("Amount");
		lblsub_amt.setForeground(Color.BLACK);
		lblsub_amt.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblsub_amt.setBounds(115, 350, 200, 30);
		contentPaneRenewSubscription.add(lblsub_amt);
		
		textFieldphno = new JTextField();
		textFieldphno.setForeground(Color.BLACK);
		textFieldphno.setBackground(Color.WHITE);
		textFieldphno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldphno.setBounds(320, 250, 150, 30);
		contentPaneRenewSubscription.add(textFieldphno);
		textFieldphno.setColumns(10);
		
		try {
			Connection conn;
			conn = dbConnection.returnConnection();
			textFieldcardno.setEditable(false);
			String query = "SELECT name, address, phno FROM subscription WHERE card_no = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, textFieldcardno.getText());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				textFieldname.setText(rs.getString("name"));
				textFieldaddress.setText(rs.getString("address"));
				textFieldphno.setText(rs.getString("phno"));
			}
			textFieldname.setEditable(false);
			textFieldaddress.setEditable(false);
			textFieldphno.setEditable(false);
			rs.close(); pst.close(); conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		textFieldsubdur = new JTextField();
		textFieldsubdur.setForeground(Color.BLACK);
		textFieldsubdur.setBackground(Color.WHITE);
		textFieldsubdur.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldsubdur.setBounds(320, 300, 150, 30);
		contentPaneRenewSubscription.add(textFieldsubdur);
		textFieldsubdur.setColumns(10);
		
		textFieldsubamt = new JTextField();
		textFieldsubamt.setForeground(Color.BLACK);
		textFieldsubamt.setBackground(Color.WHITE);
		textFieldsubamt.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldsubamt.setBounds(320, 350, 150, 30);
		contentPaneRenewSubscription.add(textFieldsubamt);
		textFieldsubamt.setColumns(10);
		
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
		contentPaneRenewSubscription.add(btnCalculateAmount);
		
		JButton btnrenew = new JButton("Renew");
		btnrenew.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnrenew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn;
				try {
					conn = dbConnection.returnConnection();
					String query = "update subscription set sub_dur = ?, sub_amt = ? where card_no = ?;";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, textFieldsubdur.getText());
					pst.setString(2, textFieldsubamt.getText());
					pst.setString(3, textFieldcardno.getText());
					pst.executeUpdate();
					pst.close(); conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				StartUp renew = new StartUp();
				dispose();
				renew.frame.setVisible(true);
			}
		});
		btnrenew.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnrenew.setBounds(216, 430, 115, 30);
		contentPaneRenewSubscription.add(btnrenew);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExistingSubscriber cancel = new ExistingSubscriber();
				dispose();
				cancel.setVisible(true);
			}
		});
		btnCancel.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnCancel.setBounds(547, 430, 115, 30);
		contentPaneRenewSubscription.add(btnCancel);
	}
}