import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class Info extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Info frame = new Info();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Info() {
		Image logo = new ImageIcon(this.getClass().getResource("resources/Icon.png")).getImage();
		setIconImage(logo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 900, 600);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 180, 850, 300);
		contentPane.add(scrollPane);
		
		JLabel lblabstract = new JLabel("<html>Book-O-Graphy is an application which is used for the smooth and efficient functioning"
				+ "<br>of small and medium sized libraries. The system is used by the librarian to record various "
				+ "<br>transactions like issue of books, return of books, addition of new books, addition of new "
				+ "<br>subscribers and renewal of subscriptions."
				+ "<br>-- Every subscriber has a unique card number."
				+ "<br>-- There can be multiple books with the same ISBN but every Book ID is mapped to a single"
				+ "<br>ISBN i.e., multiple books with the same ISBN will have different Book ID(s)."
				+ "<br>-- Each time a book is borrowed, it’s availability in the library is set to 0 and when a book"
				+ "<br>is returned it is set to 1 using the triggers lend_book and return_book. The trigger also "
				+ "<br>increases the number of books borrowed by a subscriber each time a book is borrowed "
				+ "<br>and vice versa."
				+ "<br>-- The stored procedure calculates return date as issue date + 7 and the librarian is advised "
				+ "<br>to levy a fine if the book has not been returned before the return date."
				+ "<br>-- The database used follows 3NF normalization."
				+ "<br>-- The backend is implemented using MySQL and the front end is a Java swing application.</html>");
		scrollPane.setViewportView(lblabstract);
		lblabstract.setForeground(Color.WHITE);
		lblabstract.setFont(new Font("Calibri", Font.BOLD, 21));
		
		JLabel lblBookography = new JLabel("");
		lblBookography.setBounds(10, 10, 515, 130);
		getContentPane().add(lblBookography);
		Image img = new ImageIcon(this.getClass().getResource("/resources/Info.png")).getImage();
		lblBookography.setIcon(new ImageIcon(img));
		
		JLabel lblALibrary = new JLabel("- A Library Management System");
		lblALibrary.setForeground(Color.BLACK);
		lblALibrary.setFont(new Font("Viner Hand ITC", Font.PLAIN, 23));
		lblALibrary.setBounds(503, 135, 371, 40);
		contentPane.add(lblALibrary);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBorder(new LineBorder(new Color(105, 105, 105), 2, true));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StartUp start = new StartUp();
				dispose();
				start.frame.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		btnBack.setBounds(784, 514, 90, 30);
		contentPane.add(btnBack);
		
		JLabel label = new JLabel("<html>Done by: Bhargavi N A - 1RN15CS035, Gouthami S - 1RN15CS044</html>");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Monotype Corsiva", Font.BOLD, 21));
		label.setBounds(148, 514, 600, 30);
		contentPane.add(label);
	}
}
