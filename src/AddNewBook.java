import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class AddNewBook extends JFrame {

	private JPanel contentPaneAddNewBook;
	private JTextField textFieldisbn;
	private JTextField textFieldbookid;
	private JTextField textFieldtitle;
	private JTextField textFieldaname;
	private JTextField textFieldpbname;
	private JTextField textFieldpbyear;
	private JTextField textFieldgenre;
	int flag = 0; //0 = reqd 1 = all

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewBook frame = new AddNewBook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AddNewBook() {
		Image logo = new ImageIcon(this.getClass().getResource("/resources/Icon.png")).getImage();
		setIconImage(logo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 900, 600);
		contentPaneAddNewBook = new JPanel();
		contentPaneAddNewBook.setBackground(SystemColor.menu);
		contentPaneAddNewBook.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneAddNewBook);
		contentPaneAddNewBook.setLayout(null);
		setResizable(false);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setBounds(800, 475, 75, 75);
		contentPaneAddNewBook.add(labelLogo);
		Image img = new ImageIcon(this.getClass().getResource("/resources/IconEvery.png")).getImage();
		labelLogo.setIcon(new ImageIcon(img));

		
		textFieldisbn = new JTextField();
		textFieldisbn.setBackground(Color.WHITE);
		textFieldisbn.setForeground(Color.BLACK);
		textFieldisbn.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldisbn.setBounds(220, 50, 200, 30);
		contentPaneAddNewBook.add(textFieldisbn);
		textFieldisbn.setColumns(10);
		
		JLabel lblisbn = new JLabel("ISBN");
		lblisbn.setForeground(Color.BLACK);
		lblisbn.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblisbn.setBounds(50, 50, 150, 30);
		contentPaneAddNewBook.add(lblisbn);
		
		JButton btncheck = new JButton("Check");
		btncheck.setBorder(new LineBorder(Color.WHITE, 2, true));
		btncheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = textFieldisbn.getText();
				Connection conn;
				try {
					conn = dbConnection.returnConnection();
					String query = "SELECT isbn FROM books WHERE isbn = ?;";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, textFieldisbn.getText());
					ResultSet rs = pst.executeQuery();
					while(rs.next())
						if(s.equals(rs.getString("isbn"))) {
							String querygenre = "SELECT c.genre FROM category c, book_copies bc WHERE isbn = ? AND bc.book_id = c.book_id;";
							PreparedStatement pstgenre = conn.prepareStatement(querygenre);
							pstgenre.setString(1, textFieldisbn.getText());
							ResultSet rs1 = pstgenre.executeQuery();
							while(rs1.next())
								textFieldgenre.setText(rs1.getString(1));
							rs1.close(); pstgenre.close();
							JOptionPane.showMessageDialog(null, "ISBN already exists. Enter only Book ID.");
							textFieldtitle.setEditable(false);
							textFieldaname.setEditable(false);
							textFieldpbname.setEditable(false);
							textFieldpbyear.setEditable(false);
							textFieldgenre.setEditable(false);
							flag = 1;
							break;
						}
					if(flag == 0) {
						JOptionPane.showMessageDialog(null, "Enter all details.");
						flag = 0;
					}
					rs.close(); pst.close(); conn.close();
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		btncheck.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btncheck.setBounds(450, 50, 115, 30);
		contentPaneAddNewBook.add(btncheck);
		
		JLabel lblbookid = new JLabel("Book ID");
		lblbookid.setForeground(Color.BLACK);
		lblbookid.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblbookid.setBounds(50, 100, 100, 30);
		contentPaneAddNewBook.add(lblbookid);
		
		JLabel lbltitle = new JLabel("Title");
		lbltitle.setForeground(Color.BLACK);
		lbltitle.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lbltitle.setBounds(50, 150, 150, 30);
		contentPaneAddNewBook.add(lbltitle);
		
		JLabel lblaname = new JLabel("Author Name");
		lblaname.setForeground(Color.BLACK);
		lblaname.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblaname.setBounds(50, 200, 150, 30);
		contentPaneAddNewBook.add(lblaname);
		
		JLabel lblpbyear = new JLabel("Publishing Year");
		lblpbyear.setForeground(Color.BLACK);
		lblpbyear.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblpbyear.setBounds(50, 350, 200, 30);
		contentPaneAddNewBook.add(lblpbyear);
		
		JLabel lblpbname = new JLabel("Publishing House");
		lblpbname.setForeground(Color.BLACK);
		lblpbname.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblpbname.setBounds(50, 300, 200, 30);
		contentPaneAddNewBook.add(lblpbname);
				
		textFieldbookid = new JTextField();
		textFieldbookid.setBackground(Color.WHITE);
		textFieldbookid.setEditable(false);
		textFieldbookid.setForeground(Color.BLACK);
		textFieldbookid.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldbookid.setBounds(220, 100, 200, 30);
		contentPaneAddNewBook.add(textFieldbookid);
		textFieldbookid.setColumns(10);
		
		textFieldtitle = new JTextField();
		textFieldtitle.setBackground(Color.WHITE);
		textFieldtitle.setForeground(Color.BLACK);
		textFieldtitle.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldtitle.setBounds(220, 150, 200, 30);
		contentPaneAddNewBook.add(textFieldtitle);
		textFieldtitle.setColumns(10);
		
		textFieldaname = new JTextField();
		textFieldaname.setBackground(Color.WHITE);
		textFieldaname.setForeground(Color.BLACK);
		textFieldaname.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldaname.setBounds(220, 200, 200, 30);
		contentPaneAddNewBook.add(textFieldaname);
		textFieldaname.setColumns(10);
		
		textFieldpbname = new JTextField();
		textFieldpbname.setBackground(Color.WHITE);
		textFieldpbname.setForeground(Color.BLACK);
		textFieldpbname.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldpbname.setBounds(220, 300, 200, 30);
		contentPaneAddNewBook.add(textFieldpbname);
		textFieldpbname.setColumns(10);
		
		textFieldpbyear = new JTextField();
		textFieldpbyear.setBackground(Color.WHITE);
		textFieldpbyear.setForeground(Color.BLACK);
		textFieldpbyear.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldpbyear.setBounds(220, 350, 200, 30);
		contentPaneAddNewBook.add(textFieldpbyear);
		textFieldpbyear.setColumns(10);
				
		int bookid = 0;
		try {
			Connection conn;
			conn = dbConnection.returnConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT max(book_id) FROM book_copies;");
			while(rs.next())
				bookid = rs.getInt("max(book_id)");
			st.close(); rs.close(); conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		textFieldbookid.setText(Integer.toString(++bookid));
		
		JButton btnInsertBook = new JButton("Add Book");
		btnInsertBook.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnInsertBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn;
				try {
					conn = dbConnection.returnConnection();
					if(flag == 1) {
						String querybc = "INSERT INTO book_copies(book_id, isbn) VALUES(?,?);";
						String querycat = "INSERT INTO category VALUES(?,?);";
						
						PreparedStatement pstbc = conn.prepareStatement(querybc);
						pstbc.setString(1, textFieldbookid.getText());
						pstbc.setString(2, textFieldisbn.getText());
						
						PreparedStatement pstcat = conn.prepareStatement(querycat);
						pstcat.setString(1, textFieldbookid.getText());
						pstcat.setString(2, textFieldgenre.getText());
						
						pstbc.execute(); pstcat.execute(); 
						pstbc.close(); pstcat.close();
						conn.close();
					}
					else {
						conn = dbConnection.returnConnection();
						String queryb = "INSERT INTO books VALUES(?,?,?,?,?);";
						String querybc = "INSERT INTO book_copies(book_id, isbn) VALUES(?,?);";
						String querycat = "INSERT INTO category VALUES(?,?);";
						
						PreparedStatement pstb = conn.prepareStatement(queryb);
						PreparedStatement pstbc = conn.prepareStatement(querybc);
						PreparedStatement pstcat = conn.prepareStatement(querycat);
						
						pstb.setString(1, textFieldisbn.getText());
						pstb.setString(2, textFieldtitle.getText());
						pstb.setString(3, textFieldaname.getText());
						pstb.setString(4, textFieldpbname.getText());
						pstb.setString(5, textFieldpbyear.getText());
												
						pstbc.setString(1, textFieldbookid.getText());
						pstbc.setString(2, textFieldisbn.getText());
						
						pstcat.setString(1, textFieldbookid.getText());
						pstcat.setString(2, textFieldgenre.getText());
						
						pstb.execute(); pstbc.execute(); pstcat.execute();
						pstb.close(); pstbc.close(); pstcat.close();
						conn.close();
					}
					
				} catch(Exception e) {
					e.printStackTrace();
				}
				StartUp insert = new StartUp();
				dispose();
				insert.frame.setVisible(true);
			}
		});
		btnInsertBook.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btnInsertBook.setBounds(199, 450, 150, 30);
		contentPaneAddNewBook.add(btnInsertBook);
		
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
		btnCancel.setBounds(548, 450, 130, 29);
		contentPaneAddNewBook.add(btnCancel);
		
		textFieldgenre = new JTextField();
		textFieldgenre.setBackground(Color.WHITE);
		textFieldgenre.setForeground(Color.BLACK);
		textFieldgenre.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldgenre.setBounds(220, 250, 200, 30);
		contentPaneAddNewBook.add(textFieldgenre);
		textFieldgenre.setColumns(10);
		
		JLabel lblgenre = new JLabel("Genre");
		lblgenre.setForeground(Color.BLACK);
		lblgenre.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblgenre.setBounds(50, 250, 150, 30);
		contentPaneAddNewBook.add(lblgenre);
	}
}