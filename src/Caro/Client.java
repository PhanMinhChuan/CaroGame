package Caro;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Client extends JFrame implements MouseListener,Runnable{

	public static void main(String[] args) {
		new Client();
	}
	
	int n=20,s=40,m=50;
	Vector<Point> dadanh = new Vector<>();
	Socket soc;
	public Client() {
		this.setTitle("Co Caro");
		this.setSize(m*2+n*s,m*2+n*s);
		this.setDefaultCloseOperation(3);
		this.addMouseListener(this);
		
		try {
			soc = new Socket("localhost",5000);
		} catch (Exception e) {
		}
		
		new Thread(this).start();
		this.setVisible(true);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.PINK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.BLACK);
		for (int i=0;i<=n;i++) {
			g.drawLine(m+3, m + i*s, m+n*s, m + i*s);
			g.drawLine(m + i*s, m +3, m + i*s,  m+n*s);
		}
		
		ImageIcon imageIcon = new ImageIcon("image/chuot.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(imageIcon.getImage(), new Point(0, 30), "chuột game"));
		
//		g.setFont(new Font("arial", Font.BOLD, s));
//		for (int i=0;i<dadanh.size();i++) {
//			String st ="o";
//			if (i%2!=0) st = "x";
//			
//			int x = m+ dadanh.get(i).x*s + s/2 - s/4;
//			int y = m+ dadanh.get(i).y*s + s/2 + s/4;
//			
//			g.drawString(st, x, y);
//		}
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

	
	public void run() {
		while(true) {
			try {
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				int ix = Integer.parseInt(dis.readUTF());
				int iy = Integer.parseInt(dis.readUTF());
				dadanh.add(new Point(ix,iy));
				this.repaint();
				
				int index = dis.readInt();
				System.out.println(index);
				
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
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		
		if (x<m || x>=m+s*n) return;
		if (y<m || y>=m+s*n) return;
		
		int ix = (x-m)/s;
		int iy = (y-m)/s;
		System.out.println(ix+","+iy);
		//Gui toa do o
		try {
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			dos.writeUTF(ix+"");
			dos.writeUTF(iy+"");
		}catch (Exception e1) {
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
}