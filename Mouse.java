import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

class Mouse {
	
	public void move(JButton[][] button) throws IOException, InterruptedException{
		Thread th = new Thread(new Runnable(){
			public void run() {
				Scanner in;
				try {
					in = new Scanner(new FileReader("Data.txt"));
					int a = in.nextInt();
					if(a == -1) noWay();
					else{
						int b = in.nextInt();
			            while(in.hasNextInt()){
				        button[a][b].setBackground(Color.RED);
				        Thread.sleep(100);
				        a = in.nextInt();
				        b = in.nextInt();
			            }
			            in.close();
					}
				} catch (FileNotFoundException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		th.start();
	}
	
	public void noWay(){
		JDialog no = new JDialog();
		Toolkit kit = Toolkit.getDefaultToolkit();
 		Dimension screenSize = kit.getScreenSize();
 		int screenWidth = screenSize.width;
 		int screenHeigth = screenSize.height;
 		no.setBounds(screenWidth/2-150, screenHeigth/2-100, 300, 200);
 		no.setResizable(false);
 		no.setVisible(true);
 		no.setTitle("没有路了~");
 		Container co = no.getContentPane();
		co.setBackground(Color.WHITE);
		JLabel tip = new JLabel("没有路了~~");
		tip.setFont(new Font("微软雅黑",Font.PLAIN,26));
		tip.setForeground(new Color(0, 110, 210));
		tip.setBounds(75, 35, 150, 40);
		no.add(tip);
		JButton one = new JButton(" 确  定 ");
		one.setFont(new Font("微软雅黑",Font.PLAIN,22));
		one.setBounds(90, 100, 120, 35);
		one.setBackground(Color.ORANGE);
		no.add(one);
		one.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				no.dispose();
			}
		});
	}

}
