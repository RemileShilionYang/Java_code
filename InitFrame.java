import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;
import java.util.Random;
import javax.swing.*;

class InitFrame {
	
	static int[][] buttonArray = new int[15][15];
	
	public static void main(String[] args) {
		board();
	}
	
	static void board(){
		JFrame mazeBoard = new JFrame("迷宫求解");
 		Toolkit kit = Toolkit.getDefaultToolkit();
 		Dimension screenSize = kit.getScreenSize();
 		int screenWidth = screenSize.width;
 		int screenHeigth = screenSize.height;
 		mazeBoard.setBounds(screenWidth/2-400, screenHeigth/2-300, 800, 600);
 		mazeBoard.setResizable(false);
 		mazeBoard.setVisible(true);
 		ImageIcon mazeBack = new ImageIcon("back.jpg");
 		JLabel mazeLabel = new JLabel(mazeBack);
 		mazeLabel.setBounds(0, 0, mazeBack.getIconWidth(), mazeBack.getIconHeight());
 		JPanel mazePanel = (JPanel)mazeBoard.getContentPane();
 		mazePanel.setOpaque(false);
 		mazePanel.setLayout(new FlowLayout());
 		mazeBoard.getLayeredPane().setLayout(null);
 		mazeBoard.getLayeredPane().add(mazeLabel,new Integer(Integer.MIN_VALUE));
 		mazeBoard.setLayout(null);
 		JLabel tip = new JLabel("请定义迷宫墙壁");
		tip.setFont(new Font("微软雅黑",Font.PLAIN,32));
		tip.setForeground(new Color(0, 110, 210));
		tip.setBounds(535, 50, 250, 40);
		mazePanel.add(tip);
		JButton[][] button = new JButton[15][15];
 		int a = 25,b = 30;
 		Color Blue = new Color(0, 110, 210);
 		for(int i=0;i<15;i++){
 			for(int j=0;j<15;j++){
 				button[i][j] = new JButton();
 				button[i][j].setBounds(a, b, 31, 31);
 				button[i][j].setBackground(Color.ORANGE);
 				mazePanel.add(button[i][j]);
 				a=a+32;
 				buttonArray[i][j] = 0;
 				final int m=i,n=j;
 				button[i][j].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						int temp=0;
						if(button[m][n].getBackground()==Color.ORANGE&&temp==0){
							button[m][n].setBackground(Blue);
							temp=1;
							buttonArray[m][n] = 1;
						}
						if(button[m][n].getBackground()==Blue&&temp==0){
							button[m][n].setBackground(Color.ORANGE);
							temp=1;
							buttonArray[m][n] = 0;
						}
					}
 				});
 			}
 			a=25;
 			b=b+32;
 		}
 		otherButton(mazeBoard,mazePanel,button,Blue);
 		mazePanel.repaint();
	}
	
	static void otherButton(JFrame mazeBoard,JPanel mazePanel,JButton[][] button,Color Blue){
		
		JButton oneKeyDefine = new JButton(" 一键定义 ");
		oneKeyDefine.setFont(new Font("微软雅黑",Font.PLAIN,22));
		oneKeyDefine.setBounds(575, 100, 150, 40);
		oneKeyDefine.setBackground(Color.ORANGE);
		mazePanel.add(oneKeyDefine);
		oneKeyDefine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Random random = new Random();
				int p,q;
				for(int i=0;i<90;i++){
					p = random.nextInt(15);
					q=random.nextInt(15);
					if(p!=0&&q!=0){
						button[p][q].setBackground(Blue);
						buttonArray[p][q] = 1;
					}
					else i--;
				}
				button[0][0].setBackground(Color.ORANGE);
				buttonArray[0][0] = 0;
				button[9][9].setBackground(Color.ORANGE);
				buttonArray[14][14] = 0;
				oneKeyDefine.setEnabled(false);
			}
		});
		
		JButton oneKeyClear = new JButton(" 一键清空 ");
		oneKeyClear.setFont(new Font("微软雅黑",Font.PLAIN,22));
		oneKeyClear.setBounds(575, 160, 150, 40);
		oneKeyClear.setBackground(Color.ORANGE);
		mazePanel.add(oneKeyClear);
		oneKeyClear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<15;i++)
		 			for(int j=0;j<15;j++){
		 				button[i][j].setBackground(Color.ORANGE);
		 				buttonArray[i][j] = 0;
		 			}
				oneKeyDefine.setEnabled(true);
			}
		});
		
		JButton go = new JButton(" 继  续 ");
		go.setFont(new Font("微软雅黑",Font.PLAIN,22));
		go.setBounds(575, 280, 150, 40);
		go.setBackground(Color.ORANGE);
		mazePanel.add(go);
		go.setEnabled(false);
		go.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Mouse mouse = new Mouse();
				try {
					mouse.move(button);
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
				go.setEnabled(false);
			}
		});
		
		JButton sure = new JButton(" 确  定 ");
		sure.setFont(new Font("微软雅黑",Font.PLAIN,22));
		sure.setBounds(575, 220, 150, 40);
		sure.setBackground(Color.ORANGE);
		mazePanel.add(sure);
		sure.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				oneKeyDefine.setEnabled(false);
				oneKeyClear.setEnabled(false);
				sure.setEnabled(false);
				go.setEnabled(true);
				for(int i=0;i<15;i++)
		 			for(int j=0;j<15;j++)
		 				button[i][j].setEnabled(false);
				button[0][0].setBackground(Color.GREEN);
				button[14][14].setBackground(Color.GREEN);
				Maze mazeStatus = new Maze(buttonArray);
				try {
					mazeStatus.filePut(mazeStatus);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton close = new JButton(" 关  闭 ");
		close.setFont(new Font("微软雅黑",Font.PLAIN,22));
		close.setBounds(575, 340, 150, 40);
		close.setBackground(Color.ORANGE);
		mazePanel.add(close);
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				mazeBoard.dispose();
				File file = new File("Data.txt");
		        if(file.exists()) file.delete();
				System.exit(0);
			}
		});
		
	}
	
}
