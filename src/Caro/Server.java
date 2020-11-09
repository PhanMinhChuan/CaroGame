package Caro;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Server {

	public static void main(String[] args) {
		new Server();
	}
	Socket s1, s2;
	int n=20;
	Vector<Point> dadanh = new Vector<>();
	public Server(){
		try {
			ServerSocket server = new ServerSocket(5000);
			s1 = server.accept();
			new Xuly(this,s1).start();
			s2 = server.accept();
			new Xuly(this,s2).start();
		}catch (Exception e) {
		}
	}
}

class Xuly extends Thread{
	Server server;
	Socket s;
	public Xuly(Server server, Socket s) {
		this.server = server;
		this.s = s;
	}
	public void run() {
		//Xuly
		loop:while(true) {
			try {
				DataInputStream dis = new DataInputStream(s.getInputStream());	
				int ix = Integer.parseInt(dis.readUTF());
				int iy = Integer.parseInt(dis.readUTF());
				System.out.println(ix+","+iy);
				
				if (server.s1==null || server.s2==null) continue;
				
				if (!((s == server.s1 && server.dadanh.size()%2==0) || 
						(s == server.s2 && server.dadanh.size()%2==1)))
							continue;
				
				//Kiem tra tinh hop le
				//O trung
				for (Point p : server.dadanh) {
					if (ix == p.x && iy == p.y) continue loop;
				}
				
				
				server.dadanh.add(new Point(ix,iy));
				
				//Gui toa do cho ca 2 client
				DataOutputStream dos1 = new DataOutputStream(server.s1.getOutputStream());
				dos1.writeUTF(ix+"");
				dos1.writeUTF(iy+"");
				
				DataOutputStream dos2 = new DataOutputStream(server.s2.getOutputStream());
				dos2.writeUTF(ix+"");
				dos2.writeUTF(iy+"");
				int index = -1;
				if (server.dadanh.size() >= 9) {
					index = checkWin();
					if (index != 1) {
						if (index == 1) {
							dos1.writeInt(index);
						} else if (index == 2) {
							dos2.writeInt(index);
						}
					}
				}
				dos1.writeInt(index);
				dos2.writeInt(index);
				
			} catch (Exception e) {
			}
		}
	}

	public int checkWin() {
				server.dadanh.get(0);
				// DOC
				Vector<Point> dadanhO = new Vector<>();
				Vector<Point> dadanhX = new Vector<>();

				for (int i = 0; i < server.dadanh.size(); i++) {
					int a = server.dadanh.get(i).x;
					if (i%2 == 0) {
						dadanhO.add(server.dadanh.get(i));
					} else {
						dadanhX.add(server.dadanh.get(i));
					}
					if(CheckDocVector(a, dadanhO)) {
						return 22;
					} else if (CheckDocVector(a, dadanhX)) {
						return 21;
					}
					int b = server.dadanh.get(i).y;
					if(CheckNgangVector(b, dadanhO)) {
						return 22;
					} else if (CheckNgangVector(b, dadanhX)) {
						return 21;
					}
					
					int x = server.dadanh.get(i).x;
					int y = server.dadanh.get(i).y;
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
				return -1;
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
}