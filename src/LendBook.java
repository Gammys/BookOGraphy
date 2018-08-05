import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import net.proteanit.sql.DbUtils;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class LendBook extends JFrame {

	private JPanel contentPaneLendBook;
	private JTable tableSearchResults;
	private JTextField textFieldbookid;
	private JTextField textFieldSearch;
	private JTextField textFieldduedate;
	private JTextField textFieldcardno;
	private JTextField textFieldoutdate;
	private JTextField textFieldname;
	private JButton btncalcduedate;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LendBook frame = new LendBook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LendBook() {
		Image logo = new ImageIcon(this.getClass().getResource("/resources/Icon.png")).getImage();
		setIconImage(logo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 900, 600);
		contentPaneLendBook = new JPanel();
		contentPaneLendBook.setBackground(SystemColor.menu);
		contentPaneLendBook.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneLendBook);
		contentPaneLendBook.setLayout(null);
		setResizable(false);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setBounds(800, 475, 75, 75);
		contentPaneLendBook.add(labelLogo);
		Image img = new ImageIcon(this.getClass().getResource("/resources/IconEvery.png")).getImage();
		labelLogo.setIcon(new ImageIcon(img));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 125, 850, 200);
		contentPaneLendBook.add(scrollPane);
		
		tableSearchResults = new JTable();
		tableSearchResults.setForeground(Color.BLACK);
		tableSearchResults.setBackground(Color.WHITE);
		UIManager.put("TableHeader.font",new Font("Pristina", Font.BOLD, 20) );
		tableSearchResults.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				Connection conn;
				try {
					conn = dbConnection.returnConnection();
					int row = tableSearchResults.getSelectedRow();
					String bookid = (tableSearchResults.getModel().getValueAt(row, 0).toString());
					String query = "SELECT book_id, name FROM book_copies bc, books b where bc.book_id = '"+bookid+"'and bc.isbn = b.isbn";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					while(rs.next()) {
						textFieldbookid.setText(rs.getString("book_id"));
						textFieldname.setText(rs.getString("name"));
					}
					btncalcduedate.setEnabled(true);
					rs.close(); pst.close(); conn.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(tableSearchResults);
		tableSearchResults.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		tableSearchResults.setRowHeight(30);
		
		textFieldoutdate = new JTextField();
		textFieldoutdate.setForeground(Color.BLACK);
		textFieldoutdate.setBackground(Color.WHITE);
		textFieldoutdate.setEditable(false);
		textFieldoutdate.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldoutdate.setBounds(146, 430, 150, 30);
		contentPaneLendBook.add(textFieldoutdate);
		textFieldoutdate.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnCancel.setBounds(557, 500, 115, 30);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExistingSubscriber cancel = new ExistingSubscriber();
				dispose();
				cancel.setVisible(true);
			}
		});
		btnCancel.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		contentPaneLendBook.add(btnCancel);
		
		JButton btnIssue = new JButton("Issue");
		btnIssue.setBorder(new LineBorder(Color.WHITE, 2, true));
		btnIssue.setBounds(221, 500, 115, 30);
		btnIssue.setEnabled(false);
		btnIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Book Issued!");
				try {
					Connection conn = dbConnection.returnConnection();
					String query = "select bc.book_id as 'Book ID', b.isbn as 'ISBN', b.name as 'Name', b.a_name "
							+ "as 'Author', c.genre as 'Genre', b.pb_name as 'Publisher', b.pb_year as 'Year' "
							+ "from book_copies bc, books b, category c "
							+ "where bc.isbn = b.isbn and bc.avbl = 1 and (c.genre like ? or b.a_name like ? "
							+ "or b.name like ?) and c.book_id = bc.book_id;";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, "%" + textFieldSearch.getText() + "%");
					pst.setString(2, "%" + textFieldSearch.getText() + "%");
					pst.setString(3, "%" + textFieldSearch.getText() + "%");
					ResultSet rs = pst.executeQuery();
					tableSearchResults.setModel(DbUtils.resultSetToTableModel(rs));
					rs.close(); pst.close(); conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnIssue.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		contentPaneLendBook.add(btnIssue);
		
		textFieldbookid = new JTextField();
		textFieldbookid.setForeground(Color.BLACK);
		textFieldbookid.setBackground(Color.WHITE);
		textFieldbookid.setBounds(275, 350, 75, 30);
		textFieldbookid.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		contentPaneLendBook.add(textFieldbookid);
		textFieldbookid.setColumns(10);
		
		JLabel lblbookid = new JLabel("Selected Book");
		lblbookid.setForeground(Color.BLACK);
		lblbookid.setBounds(25, 350, 130, 30);
		lblbookid.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		contentPaneLendBook.add(lblbookid);
		
		JLabel lblSearch = new JLabel("Search by title, genre or author");
		lblSearch.setForeground(Color.BLACK);
		lblSearch.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblSearch.setBounds(50, 75, 300, 30);
		contentPaneLendBook.add(lblSearch);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setForeground(Color.BLACK);
		textFieldSearch.setBackground(Color.WHITE);
		textFieldSearch.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldSearch.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				try {
					Connection conn = dbConnection.returnConnection();
					String query = "select bc.book_id as 'Book ID', b.isbn as 'ISBN', b.name as 'Name', b.a_name as 'Author', "
							+ "c.genre as 'Genre', b.pb_name as 'Publisher', b.pb_year as 'Year' "
							+ "from book_copies bc, books b, category c "
							+ "where bc.isbn = b.isbn and bc.avbl = 1 and (c.genre like ? or b.a_name like ? or "
							+ "b.name like ?) and c.book_id = bc.book_id;";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, "%" + textFieldSearch.getText() + "%");
					pst.setString(2, "%" + textFieldSearch.getText() + "%");
					pst.setString(3, "%" + textFieldSearch.getText() + "%");
					ResultSet rs = pst.executeQuery();
					tableSearchResults.setModel(DbUtils.resultSetToTableModel(rs));
					rs.close(); pst.close(); conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		textFieldSearch.setBounds(350, 75, 200, 30);
		contentPaneLendBook.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		JLabel lbloutdate = new JLabel("Issue Date");
		lbloutdate.setForeground(Color.BLACK);
		lbloutdate.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lbloutdate.setBounds(15, 433, 120, 30);
		contentPaneLendBook.add(lbloutdate);
		
		JLabel lblduedate = new JLabel("Return Date");
		lblduedate.setForeground(Color.BLACK);
		lblduedate.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblduedate.setBounds(568, 430, 120, 30);
		contentPaneLendBook.add(lblduedate);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		textFieldoutdate.setText(dtf.format(localDate));
		
		textFieldduedate = new JTextField();
		textFieldduedate.setForeground(Color.BLACK);
		textFieldduedate.setBackground(Color.WHITE);
		textFieldduedate.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldduedate.setBounds(699, 430, 150, 30);
		contentPaneLendBook.add(textFieldduedate);
		textFieldduedate.setColumns(10);
		
		JLabel lblcardno = new JLabel("Card No");
		lblcardno.setForeground(Color.BLACK);
		lblcardno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblcardno.setBounds(50, 25, 100, 30);
		contentPaneLendBook.add(lblcardno);
				
		textFieldcardno = new JTextField();
		textFieldcardno.setForeground(Color.BLACK);
		textFieldcardno.setBackground(Color.WHITE);
		textFieldcardno.setEditable(false);
		textFieldcardno.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldcardno.setBounds(181, 25, 150, 30);
		contentPaneLendBook.add(textFieldcardno);
		textFieldcardno.setColumns(10);
		textFieldcardno.setText(ExistingSubscriber.id);
		
		btncalcduedate = new JButton("Calculate Return Date");
		btncalcduedate.setBorder(new LineBorder(Color.WHITE, 2, true));
		btncalcduedate.setEnabled(false);
		btncalcduedate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection conn;
				try {
					conn = dbConnection.returnConnection();
					String query = "SELECT COUNT(*) FROM book_lending WHERE card_no = ? GROUP BY card_no;";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, textFieldcardno.getText());
					ResultSet rs = pst.executeQuery();
					int noofbooks = 0;
					while(rs.next())
						noofbooks = rs.getInt(1);
					if(noofbooks >= 3) {
						JOptionPane.showMessageDialog(null, "Too many books!");
						btnIssue.setEnabled(false);
						rs.close(); pst.close();
					}
					else {
						query = "INSERT INTO book_lending (book_id, card_no, out_date) VALUES (?,?,?)";
						PreparedStatement pst1 = conn.prepareStatement(query);
						pst1.setString(1, textFieldbookid.getText());
						pst1.setString(2, textFieldcardno.getText());
						pst1.setString(3, textFieldoutdate.getText());
						pst1.execute();
						query = "CALL new_procedure";
						java.sql.CallableStatement cst = conn.prepareCall(query);
						cst.execute();
						query = "SELECT due_date FROM book_lending WHERE book_id = ? AND card_no = ?;";
						pst = conn.prepareStatement(query);
						pst.setString(1, textFieldbookid.getText());
						pst.setString(2, textFieldcardno.getText());
						ResultSet rs1 = pst.executeQuery();
						while(rs1.next()) {
							textFieldduedate.setText(rs1.getString("due_date"));
							break;
						}
						textFieldduedate.setEditable(false);
						btnIssue.setEnabled(true);
						rs1.close(); pst1.close(); conn.close();
					}
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		btncalcduedate.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		btncalcduedate.setBounds(307, 431, 250, 30);
		contentPaneLendBook.add(btncalcduedate);
		
		JLabel lblId = new JLabel("ID");
		lblId.setForeground(Color.BLACK);
		lblId.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblId.setBounds(225, 350, 40, 30);
		contentPaneLendBook.add(lblId);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		lblTitle.setBounds(429, 350, 60, 30);
		contentPaneLendBook.add(lblTitle);
		
		textFieldname = new JTextField();
		textFieldname.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		textFieldname.setBackground(Color.WHITE);
		textFieldname.setForeground(Color.BLACK);
		textFieldname.setBounds(500, 350, 350, 30);
		contentPaneLendBook.add(textFieldname);
		textFieldname.setColumns(10);
	}
}