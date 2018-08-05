import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class ExistingSubscriber extends JFrame {

	private JPanel contentPaneExistingSubscriber;
	private JTextField textFieldcardno;
	private JTable tableAccessDetails;
	private JButton btnLoadDetails;
	private JButton btnLendNewBook;
	private JButton btnCancel;
	private JButton btnReturnBook;
	public static String id;
	private JButton btnrenew;
	private String bookid;
	private JTextField textFieldnoofbooks;
	private JLabel lblnofobooks;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExistingSubscriber frame = new ExistingSubscriber();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ExistingSubscriber() {
		Image logo = new ImageIcon(this.getClass().getResource("/resources/Icon.png")).getImage();
		setIconImage(logo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 900, 600);
		contentPaneExistingSubscriber = new JPanel();
		contentPaneExistingSubscriber.setBackground(SystemColor.menu);
		contentPaneExistingSubscriber.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneExistingSubscriber);
		contentPaneExistingSubscriber.setLayout(null);
		setResizable(false);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setBounds(800, 475, 75, 75);
		contentPaneExistingSubscriber.add(labelLogo);
		Image img = new ImageIcon(this.getClass().getResource("/resources/IconEvery.png")).getImage();
		labelLogo.setIcon(new ImageIcon(img));
		
		JLabel lblcardno = new JLabel("Card No");
		lblcardno.setForeground(Color.BLACK);
		lblcardno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblcardno.setBounds(100, 74, 75, 30);
		contentPaneExistingSubscriber.add(lblcardno);
		
		textFieldcardno = new JTextField();
		textFieldcardno.setForeground(Color.BLACK);
		textFieldcardno.setBackground(Color.WHITE);
		textFieldcardno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldcardno.setBounds(200, 74, 150, 30);
		contentPaneExistingSubscriber.add(textFieldcardno);
		textFieldcardno.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 178, 750, 175);
		contentPaneExistingSubscriber.add(scrollPane);
		
		tableAccessDetails = new JTable();
		tableAccessDetails.setForeground(Color.BLACK);
		tableAccessDetails.setBackground(Color.WHITE);
		UIManager.put("TableHeader.font",new Font("Pristina", Font.BOLD, 20));
		tableAccessDetails.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				Connection conn;
				try {
					conn = dbConnection.returnConnection();
					int row = tableAccessDetails.getSelectedRow();
					bookid = (tableAccessDetails.getModel().getValueAt(row, 0).toString());
//					String query = "DELETE FROM book_lending where book_id = '"+bookid+"';";
//					PreparedStatement pst = conn.prepareStatement(query);
//					pst.execute();
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(tableAccessDetails);
		tableAccessDetails.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		
		btnReturnBook = new JButton("Return Book");
		btnReturnBook.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnReturnBook.setEnabled(false);
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn;
				try {
					conn = dbConnection.returnConnection();
					LocalDate localDate = LocalDate.now();
					java.util.Date date = java.sql.Date.valueOf(localDate);
					String query2 = "SELECT due_date FROM book_lending where book_id = '"+bookid+"';";
					PreparedStatement pst2 = conn.prepareStatement(query2);
					ResultSet rs = pst2.executeQuery();
					while(rs.next()){
						Date date1 = rs.getDate("due_date");
						if(date.after(date1))
							JOptionPane.showMessageDialog(null, "Fine levied.");
					}
					String query = "DELETE FROM book_lending where book_id = '"+bookid+"';";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.execute();
					pst.close(); pst2.close(); rs.close();
					conn.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
				try {
					conn = dbConnection.returnConnection();
					String query = "SELECT bl.book_id AS 'Book ID', b.name AS 'Name', out_date AS 'Issue Date', due_date AS 'Due Date' from book_lending bl, book_copies bc, books b WHERE card_no = ? AND bl.book_id = bc.book_id AND bc.isbn = b.isbn;";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1,textFieldcardno.getText());
					id = textFieldcardno.getText();
					ResultSet rs = pst.executeQuery();
					tableAccessDetails.setModel(DbUtils.resultSetToTableModel(rs));
					tableAccessDetails.setRowHeight(30);
					pst.close(); rs.close();
					query = "SELECT no_of_books FROM card WHERE card_no = ?";
					pst = conn.prepareStatement(query);
					pst.setString(1, textFieldcardno.getText());
					rs = pst.executeQuery();
					while(rs.next())
						textFieldnoofbooks.setText(rs.getString("no_of_books"));
					rs.close(); pst.close(); conn.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		btnReturnBook.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnReturnBook.setBounds(514, 440, 175, 30);
		contentPaneExistingSubscriber.add(btnReturnBook);
		
		btnLoadDetails = new JButton("Load Details");
		btnLoadDetails.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnLoadDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Connection conn = dbConnection.returnConnection();
					String query = "SELECT bl.book_id AS 'Book ID', b.name AS 'Name', out_date AS 'Issue Date', due_date AS 'Due Date' from book_lending bl, book_copies bc, books b WHERE card_no = ? AND bl.book_id = bc.book_id AND bc.isbn = b.isbn;";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1,textFieldcardno.getText());
					id = textFieldcardno.getText();
					ResultSet rs = pst.executeQuery();
					tableAccessDetails.setModel(DbUtils.resultSetToTableModel(rs));
					tableAccessDetails.setRowHeight(30);
					pst.close(); rs.close();
					query = "SELECT no_of_books FROM card WHERE card_no = ?";
					pst = conn.prepareStatement(query);
					pst.setString(1, textFieldcardno.getText());
					rs = pst.executeQuery();
					while(rs.next())
						textFieldnoofbooks.setText(rs.getString("no_of_books"));
					btnLendNewBook.setEnabled(true);
					btnrenew.setEnabled(true);
					btnReturnBook.setEnabled(true);
					rs.close(); pst.close(); conn.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLoadDetails.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnLoadDetails.setBounds(400, 74, 150, 30);
		contentPaneExistingSubscriber.add(btnLoadDetails);
		
		btnLendNewBook = new JButton("Lend New Book");
		btnLendNewBook.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnLendNewBook.setEnabled(false);
		btnLendNewBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Integer.parseInt(textFieldnoofbooks.getText()) < 3) {
					LendBook lend = new LendBook();
					dispose();
					lend.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Too many books!");
			}
		});
		btnLendNewBook.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnLendNewBook.setBounds(296, 440, 175, 29);
		contentPaneExistingSubscriber.add(btnLendNewBook);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StartUp cancel = new StartUp();
				dispose();
				cancel.frame.setVisible(true);
			}
		});
		btnCancel.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnCancel.setBounds(732, 440, 115, 30);
		contentPaneExistingSubscriber.add(btnCancel);
		
		btnrenew = new JButton("Renew Subscription");
		btnrenew.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnrenew.setEnabled(false);
		btnrenew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RenewSubscription renew = new RenewSubscription();
				dispose();
				renew.setVisible(true);
			}
		});
		btnrenew.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnrenew.setBounds(43, 440, 210, 29);
		contentPaneExistingSubscriber.add(btnrenew);
		
		lblnofobooks = new JLabel("No of Books Issued");
		lblnofobooks.setForeground(Color.BLACK);
		lblnofobooks.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblnofobooks.setBounds(100, 380, 190, 30);
		contentPaneExistingSubscriber.add(lblnofobooks);
		
		textFieldnoofbooks = new JTextField();
		textFieldnoofbooks.setForeground(Color.BLACK);
		textFieldnoofbooks.setBackground(Color.WHITE);
		textFieldnoofbooks.setEditable(false);
		textFieldnoofbooks.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldnoofbooks.setBounds(310, 380, 150, 30);
		contentPaneExistingSubscriber.add(textFieldnoofbooks);
		textFieldnoofbooks.setColumns(10);
	
	}	
}