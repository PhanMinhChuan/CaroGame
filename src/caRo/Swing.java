package caRo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Swing extends JFrame implements MouseListener {

	public JPanel mainPanel, p1, p3, p10;
	private static JButton[][] arrButton; // mang button
	private static boolean[][] arrEnd; // mang ko cho bấm :v
	public static int[][] arr; // mang x o
	private int[][] arrWin; // mang win
	private boolean turn = false;// lượt đánh

	private boolean chanHaiDau = true;

	private boolean choiMoi = false; // button new

	private int locationX = 0;
	private int locationY = 0;

	private boolean nguoiWin = false; // ai thắng ?

	public static boolean statrr = false; // bt OK
	
	public static Swing test;// settig
	
	public static void main(String[] args) {
		int wait = 200;
		JButton[][] arr = new JButton[24][24];
		Swing.test = new Swing(arr, wait);
		Swing.test.setVisible(true);
		startGame();
	}

	public Swing(JButton[][] arrr, int wait) {

		// khai báo các mảng cần thiết

		this.arrButton = arrr;
		arrEnd = new boolean[arrButton.length][arrButton[0].length];
		arr = new int[arrButton.length][arrButton[0].length];
		arrWin = new int[arrButton.length][arrButton[0].length];

		// tạo swing

		createSwing();

		setTitle("Caro");
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1250, 730));
		setSize(1500, 800);
		setLocationRelativeTo(null);
//		setResizable(false);
//		setVisible(true);
	}

	
	public static void startGame() {
		statrr = true;
		setBackGroud();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arrEnd[i][j] = false;
				arrButton[i][j].setEnabled(true);
			}
		}
	}

	private void createSwing() {
		//ImageIcon chuot = new ImageIcon("image/chuot.png");
		//setCursor(Toolkit.getDefaultToolkit().createCustomCursor(chuot.getImage(), new Point(0, 30), "chuột game"));
		
		ImageIcon imageIcon = new ImageIcon("image/chuot.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(30, 35,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(imageIcon.getImage(), new Point(0, 30), "chuột game"));
		// tạo main panel

		add(mainPanel = new JPanel());
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));



		addButton();
	}


	public static void setBackGroud() {

		// điền 2 màu theo đường chéo

		for (int i = 0; i < arrButton.length; i++) {
			for (int j = 0; j < arrButton[i].length; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						arrButton[i][j].setBackground(new Color(255, 240, 245));
					} else {
						arrButton[i][j].setBackground(new Color(240, 248, 255));
					}
				} else {
					if (j % 2 == 0) {
						arrButton[i][j].setBackground(new Color(240, 248, 255));
					} else {
						arrButton[i][j].setBackground(new Color(255, 240, 245));
					}
				}
			}
		}
	}

	private void addButton() {

		// tạo jscrool panl

		JScrollPane scp = new JScrollPane(p1 = new JPanel());
		scp.setPreferredSize(new Dimension(600, 710));

		p1.setLayout(new GridLayout(arrButton.length, arrButton[0].length, 1, 1)); // grid lay out theo số nút

		p1.setBackground(Color.white);
		mainPanel.add(scp);
		scp.setBorder(null); // xóa viền

		Dimension bt = new Dimension(30, 30); // kích cỡ của mỗi nút

		// điền nút ,add sự kiện , tạo các thuộc tính nút

		for (int i = 0; i < arrButton.length; i++) {
			for (int j = 0; j < arrButton[i].length; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						arrButton[i][j] = new JButton();
						arrButton[i][j].setPreferredSize(bt);
//						arrButton[i][j].setBackground(new Color(255, 240, 245));
						arrButton[i][j].addMouseListener(this);
						arrButton[i][j].setBorderPainted(false);
						arrButton[i][j].setEnabled(false);
						p1.add(arrButton[i][j]);
					} else {
						arrButton[i][j] = new JButton();
						arrButton[i][j].setPreferredSize(bt);
//						arrButton[i][j].setBackground(new Color(240, 248, 255));
						arrButton[i][j].addMouseListener(this);
						arrButton[i][j].setBorderPainted(false);
						arrButton[i][j].setEnabled(false);
						p1.add(arrButton[i][j]);
					}
				} else {
					if (j % 2 == 0) {
						arrButton[i][j] = new JButton();
						arrButton[i][j].setPreferredSize(bt);
//						arrButton[i][j].setBackground(new Color(240, 248, 255));
						arrButton[i][j].addMouseListener(this);
						arrButton[i][j].setBorderPainted(false);
						arrButton[i][j].setEnabled(false);
						p1.add(arrButton[i][j]);
					} else {
						arrButton[i][j] = new JButton();
						arrButton[i][j].setPreferredSize(bt);
//						arrButton[i][j].setBackground(new Color(255, 240, 245));
						arrButton[i][j].addMouseListener(this);
						arrButton[i][j].setBorderPainted(false);
						arrButton[i][j].setEnabled(false);
						p1.add(arrButton[i][j]);
					}
				}
				arrEnd[i][j] = true; // cho ko đánh đc :v phải ấn start để statrr chuyển thành true rồi sét hết cái
										// này
				// thành false mới đánh đc
			}
		}
	}

	public int checkWinKhongChan() {
		int count = 0;
		
		// DOC
		for (int j = 0; j < arr[0].length; j++) {
			for (int i = 0; i < arr.length; i++) {
				int a = arr[i][j];
				System.out.println(a);
				for (int k = i; k < arr.length; k++) {
					if (arr[k][j] == 0 || arr[k][j] != a) {
						if (count == 5) {
							System.out.println(a+ 20);
							return a + 20;
						} else if (count != 5) {
							count = 0;
							break;
						}
					} else if (arr[k][j] == a) {
						count++;
						arrWin[k][j] = 10;
					}
				}
				if (count == 5) {
					System.out.println(a+ 20);
					return a + 20;
				} else {
					count = 0;
					for (int k = 0; k < arr.length; k++) {
						for (int k2 = 0; k2 < arr[k].length; k2++) {
							arrWin[k][k2] = 0;
						}
					}
				}
			}
		}
		// ngang
		for (int j = 0; j < arr[0].length; j++) {
			for (int i = 0; i < arr.length; i++) {
				int a = arr[i][j];
				for (int k = i; k < arr.length; k++) {
					if (arr[k][j] == 0 || arr[k][j] != a) {
						if (count == 5) {
							return a + 20;
						} else if (count != 5) {
							count = 0;
							break;
						}
					} else if (arr[k][j] == a) {
						count++;
						arrWin[k][j] = 10;
					}
				}
				if (count == 5)
					return a + 20;
				else {
					count = 0;
					for (int k = 0; k < arr.length; k++) {
						for (int k2 = 0; k2 < arr[k].length; k2++) {
							arrWin[k][k2] = 0;
						}
					}
				}
	
			}
		}
	
		// chéo
	
		// chéo nửa dưới từ trái qua phải
	
		int plus = -1;
		for (int i = 0; i < arr.length; i++) {
			plus++;
			for (int j = 0; j < arr.length - i; j++) {
				int a = arr[j + plus][j];
				for (int k = j; k < arr.length - i; k++) {
					if (arr[k + plus][k] == 0 || arr[k + plus][k] != a) {
						if (count == 5) {
							return a + 30;
						} else if (count != 5) {
							count = 0;
							break;
						}
					} else if (arr[k + plus][k] == a) {
						count++;
						arrWin[k + plus][k] = 10;
					}
				}
				if (count == 5)
					return a + 30;
				else {
					count = 0;
					for (int k2 = 0; k2 < arr.length; k2++) {
						for (int k = 0; k < arr[k2].length; k++) {
							arrWin[k2][k] = 0;
						}
					}
				}
			}
		}
		plus = -1;
	
		// chéo nửa trên từ trái qua phải
	
		for (int i = 0; i < arr[0].length; i++) {
			plus++;
			for (int j = 0; j < arr.length - i; j++) {
				int a = arr[j][j + plus];
				for (int k = j; k < arr.length - i; k++) {
					if (arr[k][k + plus] == 0 || arr[k][k + plus] != a) {
						if (count == 5) {
							return a + 30;
						} else if (count != 5) {
							count = 0;
							break;
						}
					} else if (arr[k][k + plus] == a) {
						count++;
						arrWin[k][k + plus] = 10;
					}
				}
				if (count == 5)
					return a + 30;
				else {
					count = 0;
					for (int k2 = 0; k2 < arr.length; k2++) {
						for (int k = 0; k < arr[k2].length; k++) {
							arrWin[k2][k] = 0;
						}
					}
				}
			}
		}
		plus = -1;
	
		// chéo nửa dưới từ phải qua trái
	
		for (int i = 0; i < arr.length; i++) {
			for (int j = arr[i].length - 1; j - i > 0; j--) {
				int a = arr[i + arr[0].length - j - 1][j];
				for (int k = j; k - i > 0; k--) {
					if (arr[i + arr[0].length - k - 1][k] == 0 || arr[i + arr[0].length - k - 1][k] != a) {
						if (count == 5) {
							return a + 40;
						} else if (count != 5) {
							count = 0;
							break;
						}
					} else if (arr[i + arr[0].length - k - 1][k] == a) {
						count++;
						arrWin[i + arr[0].length - k - 1][k] = 10;
					}
				}
				if (count == 5)
					return a + 40;
				else {
					count = 0;
					for (int k2 = 0; k2 < arr.length; k2++) {
						for (int k = 0; k < arr[k2].length; k++) {
							arrWin[k2][k] = 0;
						}
					}
				}
			}
		}
	
		// chéo nửa trên từ phải qua trái
	
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < i + 1; j++) {
				int a = arr[i - j][j];
				for (int k = j; k < i + 1; k++) {
					if (arr[i - k][k] == 0 || arr[i - k][k] != a) {
						if (count == 5) {
							return a + 40;
						} else if (count != 5) {
							count = 0;
							break;
						}
					} else if (arr[i - k][k] == a) {
						count++;
						arrWin[i - k][k] = 10;
					}
				}
				if (count == 5)
					return a + 40;
				else {
					count = 0;
					for (int k2 = 0; k2 < arr.length; k2++) {
						for (int k = 0; k < arr[k2].length; k++) {
							arrWin[k2][k] = 0;
						}
					}
				}
			}
		}
	
		return -1;
	}

	
	@Override
	public void mousePressed(MouseEvent e) {

		// statrr == true là khi ấn start mới đc chơi

		if (statrr == true) {
			for (int i = 0; i < arrButton.length; i++) {
				for (int j = 0; j < arrButton[i].length; j++) {
					if (e.getSource() == arrButton[i][j] && e.getButton() == 1 && arrEnd[i][j] == false) {
						if (turn == false) {

							// danh va click
							arrButton[i][j].setIcon(new ImageIcon("image/x.png"));
							turn = true;
							arrEnd[i][j] = true;
							arr[i][j] = 1;

							// to mau

							// ten
							//jb.setValue(100); // thinking time

						} else if (turn == true) {

							// danh va click
							arrButton[i][j].setIcon(new ImageIcon("image/o.png"));
							turn = false;
							arrEnd[i][j] = true;
							arr[i][j] = 2;

							// to mau

							//jb.setValue(100); // thinking time

							// ten

						}
					}
				}
			}

			// mỗi lần kich hiện màu vàng

			//setBackGroud(); // to hết màu thường xong tô màu vàng bằng dòng ở dưới
			//arrButton[locationX][locationY].setBackground(new Color(240, 222, 69));

			// label đổi lượt


			// Xử lý win

			int index = checkWinKhongChan();
			if (index != -1) {
				int check = index % 10;
				if (check == 1) {
					String s = ": đã chiến thắng!";
					int a = JOptionPane.showConfirmDialog(this, s, "END MATCH", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, new ImageIcon("image/x.png"));
					nguoiWin = true;
					if (a == JOptionPane.OK_OPTION) {
					}
				} else if (check == 2) {
					String s = ": đã chiến thắng!";
					int a = JOptionPane.showConfirmDialog(this, s, "END MATCH", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, new ImageIcon("image/o.png"));
					nguoiWin = false;
					if (a == JOptionPane.OK_OPTION) {
					}
 				}
				choiMoi = true; // để bấm start nó ko hiện ra bảng hỏi yes no
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}