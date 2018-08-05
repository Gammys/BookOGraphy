import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class DeleteSubscriber extends JFrame {

	private JPanel contentPaneDeleteSubscription;
	private JTextField textFieldcardno;
	private JTable tableDetails;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteSubscriber frame = new DeleteSubscriber();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public DeleteSubscriber() {
		Image logo = new ImageIcon(this.getClass().getResource("/resources/Icon.png")).getImage();
		setIconImage(logo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 900, 600);
		contentPaneDeleteSubscription = new JPanel();
		contentPaneDeleteSubscription.setBackground(SystemColor.menu);
		contentPaneDeleteSubscription.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneDeleteSubscription);
		contentPaneDeleteSubscription.setLayout(null);
		setResizable(false);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setBounds(800, 475, 75, 75);
		contentPaneDeleteSubscription.add(labelLogo);
		Image img = new ImageIcon(this.getClass().getResource("/resources/IconEvery.png")).getImage();
		labelLogo.setIcon(new ImageIcon(img));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Connection conn = dbConnection.returnConnection();
					String query = "delete from subscription where card_no = ?;";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1,textFieldcardno.getText());
					pst.execute();
					pst.close(); conn.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
				StartUp cancel = new StartUp();
				dispose();
				cancel.frame.setVisible(true);
			}
		});
		btnDelete.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnDelete.setBounds(221, 350, 115, 30);
		contentPaneDeleteSubscription.add(btnDelete);
		
		JLabel lblcardno = new JLabel("Card No");
		lblcardno.setForeground(Color.BLACK);
		lblcardno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblcardno.setBounds(50, 50, 75, 30);
		contentPaneDeleteSubscription.add(lblcardno);
		
		textFieldcardno = new JTextField();
		textFieldcardno.setForeground(Color.BLACK);
		textFieldcardno.setBackground(Color.WHITE);
		textFieldcardno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldcardno.setBounds(150, 50, 150, 30);
		contentPaneDeleteSubscription.add(textFieldcardno);
		textFieldcardno.setColumns(10);
		
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
		btnCancel.setBounds(557, 350, 115, 30);
		contentPaneDeleteSubscription.add(btnCancel);
		
		JButton btnLoadDetails = new JButton("Check Issued Books");
		btnLoadDetails.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnLoadDetails.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnLoadDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Connection conn = dbConnection.returnConnection();
					String query = "select bl.book_id as 'Book ID', b.name as 'Name', out_date as 'Issue Date', due_date as 'Due Date' from book_lending bl, book_copies bc, books b where card_no = ? and bl.book_id = bc.book_id and bc.isbn = b.isbn;";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1,textFieldcardno.getText());
					ResultSet rs = pst.executeQuery();
					while(rs.next()) {
						btnDelete.setEnabled(false);
						break;
					}
					rs.close(); pst.close();
					pst = conn.prepareStatement(query);
					pst.setString(1,textFieldcardno.getText());
					rs = pst.executeQuery();
					tableDetails.setModel(DbUtils.resultSetToTableModel(rs));
					tableDetails.setRowHeight(30);
					rs.close(); pst.close(); conn.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLoadDetails.setBounds(350, 50, 225, 30);
		contentPaneDeleteSubscription.add(btnLoadDetails);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 119, 777, 139);
		contentPaneDeleteSubscription.add(scrollPane);
		
		tableDetails = new JTable();
		tableDetails.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		UIManager.put("TableHeader.font",new Font("Pristina", Font.BOLD, 20) );
		scrollPane.setViewportView(tableDetails);
	}
}
