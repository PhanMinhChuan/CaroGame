package Caro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CaroOff extends JFrame implements MouseListener, Runnable{

	public static void main(String[] args) {
		new CaroOff();
	}
	
	int n=20,s=40,m=50;
	Vector<Point> dadanh = new Vector<>();
	public JPanel p1;
	Socket soc;
	
	public CaroOff() {
		this.setTitle("Co Caro");
		this.setSize(m*2+n*s,m*2+n*s);
		this.setDefaultCloseOperation(3);
		this.addMouseListener(this);
		
		this.setVisible(true);
		
		createSwing();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.PINK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.BLACK);
		for (int i=0;i<=n;i++) {
			g.drawLine(m+3, m + i*s, m+n*s, m + i*s);
			g.drawLine(m + i*s, m +3, m + i*s,  m+n*s);
		}
		
		Toolkit t = Toolkit.getDefaultToolkit(); 
		Image imageO = t.getImage("image/o.png");
		Image imageX = t.getImage("image/x.png");
		for (int i=0;i<dadanh.size();i++) {
			int x = m + dadanh.get(i).x*s + s/2 - s/4;
			int y = m + dadanh.get(i).y*s + s/2 + s/4;
			
			if (i%2!=0) {
				g.drawImage(imageX, x-3, y-22, null);
			} else {
				g.drawImage(imageO, x-3, y-22, null);
			}
		}
	}
	
	public void run1() {
		while(true) {
			try {
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				int ix = Integer.parseInt(dis.readUTF());
				int iy = Integer.parseInt(dis.readUTF());
				dadanh.add(new Point(ix,iy));
				this.repaint();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	private void createSwing() {
		ImageIcon imageIcon = new ImageIcon("image/chuot.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(imageIcon.getImage(), new Point(0, 30), "chuột game"));
		// tạo main panel
		
		// tạo jscrool panl
		//JScrollPane scp = new JScrollPane(p1 = new JPanel());
		//scp.setPreferredSize(new Dimension(600, 710));
		//p1.setLayout(new GridLayout(n, n, 1, 1)); // grid lay out theo số nút

		//scp.setBorder(null); // xóa viền

		//Dimension bt = new Dimension(30, 30); // kích cỡ của mỗi nút
	}
	
	public boolean CheckDocVector(int x , Vector<Point> Ex) {
		Vector<Point> vectorGet = new Vector<>();
		int check =1;
		for (int i = 0; i < Ex.size(); i++) {
			if (Ex.get(i).x == x)
			vectorGet.add(Ex.get(i));
		}
		List<Integer> arrayY = new ArrayList<Integer>();
		for (int i = 0; i < vectorGet.size(); i++) {
			arrayY.add(vectorGet.get(i).y);
		}
		Collections.sort(arrayY); 
		for (int i = 1; i < vectorGet.size(); i++) {
			if (arrayY.get(i-1) + 1 != arrayY.get(i)) {
				check = 1;
			} else if (arrayY.get(i-1) + 1 == arrayY.get(i)) {
				check++;
			}
		}
		if (check == 5) return true;
		return false;
	}
	
	public boolean CheckNgangVector(int y , Vector<Point> Ex) {
		Vector<Point> vectorGet = new Vector<>();
		int check =1;
		for (int i = 0; i < Ex.size(); i++) {
			if (Ex.get(i).y == y)
			vectorGet.add(Ex.get(i));
		}
		List<Integer> arrayX = new ArrayList<Integer>();
		for (int i = 0; i < vectorGet.size(); i++) {
			arrayX.add(vectorGet.get(i).x);
		}
		Collections.sort(arrayX); 
		for (int i = 1; i < vectorGet.size(); i++) {
			if (arrayX.get(i-1) + 1 != arrayX.get(i)) {
				check = 1;
			} else if (arrayX.get(i-1) + 1 == arrayX.get(i)) {
				check++;
			}
		}
		
		if (check == 5) return true;
		return false;
	}
	
	public boolean CheckCheoXuongVector(int x, int y, Vector<Point> Ex) {
		Vector<Point> vectorGet = new Vector<>();
		int checkX = 1;
		int checkY = 1;
		for (int i = 0; i < Ex.size(); i++) {
			if (Ex.get(i).x - x == Ex.get(i).y - y) {
				vectorGet.add(Ex.get(i));
			}
		}
		List<Integer> arrayX = new ArrayList<Integer>();
		List<Integer> arrayY = new ArrayList<Integer>();
		for (int i = 0; i < vectorGet.size(); i++) {
			arrayX.add(vectorGet.get(i).x);
			arrayY.add(vectorGet.get(i).y);
		}
		Collections.sort(arrayX); 
		Collections.sort(arrayY); 
		
		for (int i = 1; i < vectorGet.size(); i++) {
			if (arrayX.get(i-1) + 1 != arrayX.get(i)) {
				checkX = 1;
			} else if (arrayX.get(i-1) + 1 == arrayX.get(i)) {
				checkX++;
			}
			if (arrayY.get(i-1) + 1 != arrayY.get(i)) {
				checkY = 1;
			} else if (arrayY.get(i-1) + 1 == arrayY.get(i)) {
				checkY++;
			}
		}
		if (checkX == 5 && checkY == 5) return true;
		return false;
	}
	
	public boolean CheckCheoLenVector(int x, int y, Vector<Point> Ex) {
		Vector<Point> vectorGet = new Vector<>();
		int checkX = 1;
		int checkY = 1;
		for (int i = 0; i < Ex.size(); i++) {
			if (x - Ex.get(i).x == Ex.get(i).y - y) {
				vectorGet.add(Ex.get(i));
			}
		}
		List<Integer> arrayX = new ArrayList<Integer>();
		List<Integer> arrayY = new ArrayList<Integer>();
		for (int i = 0; i < vectorGet.size(); i++) {
			arrayX.add(vectorGet.get(i).x);
			arrayY.add(vectorGet.get(i).y);
		}
		Collections.sort(arrayX); 
		Collections.sort(arrayY); 
		
		for (int i = 1; i < vectorGet.size(); i++) {
			if (arrayX.get(i-1) + 1 != arrayX.get(i)) {
				checkX = 1;
			} else if (arrayX.get(i-1) + 1 == arrayX.get(i)) {
				checkX++;
			}
			if (arrayY.get(i-1) + 1 != arrayY.get(i)) {
				checkY = 1;
			} else if (arrayY.get(i-1) + 1 == arrayY.get(i)) {
				checkY++;
			}
		}
		if (checkX == 5 && checkY == 5) return true;
		return false;
	}
	
	public int checkWinKhongChan() {
		//i = chan la O
		//i  = le  la X
		dadanh.get(0);
		// DOC
		Vector<Point> dadanhO = new Vector<>();
		Vector<Point> dadanhX = new Vector<>();

		for (int i = 0; i < dadanh.size(); i++) {
			int a = dadanh.get(i).x;
			if (i%2 == 0) {
				dadanhO.add(dadanh.get(i));
			} else {
				dadanhX.add(dadanh.get(i));
			}
			if(CheckDocVector(a, dadanhO)) {
				return 22;
			} else if (CheckDocVector(a, dadanhX)) {
				return 21;
			}
			int b = dadanh.get(i).y;
			if(CheckNgangVector(b, dadanhO)) {
				return 22;
			} else if (CheckNgangVector(b, dadanhX)) {
				return 21;
			}
			
			int x = dadanh.get(i).x;
			int y = dadanh.get(i).y;
			if(CheckCheoLenVector(x, y, dadanhO)) {
				return 22;
			} else if (CheckCheoLenVector(x, y, dadanhX)) {
				return 21;
			}
			
			if(CheckCheoXuongVector(x, y, dadanhO)) {
				return 22;
			} else if (CheckCheoXuongVector(x, y, dadanhX)) {
				return 21;
			}
			
		}
		// NGANG
		// Chéo nửa dưới từ trái qua phải
		// Chéo nửa trên từ trái qua phải
		// Chéo nửa dưới từ phải qua trái
		// Chéo nửa trên từ phải qua trái
		return -1;
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//System.out.println(x+","+y);
		if (x<m || x>=m+s*n) return;
		if (y<m || y>=m+s*n) return;
		
		int ix = (x-m)/s;
		int iy = (y-m)/s;
		
		for (Point p : dadanh) {
			if (ix==p.x && iy==p.y) return;
		}
		System.out.println(ix+","+iy);
		dadanh.add(new Point(ix,iy));
		
		this.repaint();	
		
		int index = checkWinKhongChan();
		if (index != -1) {
			int check = index % 10;
			if (check == 1) {
				String s = ": đã chiến thắng!";
				int a = JOptionPane.showConfirmDialog(this, s, "END MATCH", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon("image/x.png"));
				if (a == JOptionPane.OK_OPTION) {
				}
			} else if (check == 2) {
				String s = ": đã chiến thắng!";
				int a = JOptionPane.showConfirmDialog(this, s, "END MATCH", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon("image/o.png"));
				if (a == JOptionPane.OK_OPTION) {
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}