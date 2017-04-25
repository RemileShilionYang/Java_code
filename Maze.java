import java.io.*;

class Maze {
	
	private int[][] panel;
	
	public Maze(int[][] a){
		panel = a;
	}
	
	public void filePut(Maze mazeStatus) throws IOException{
		File file = new File("Data.txt");
		if(file.exists()) file.delete();
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Data.txt",true)));
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++)
				out.print(mazeStatus.panel[i][j]+"\n");
		}	
		out.close();
		FindTheOut.receive();
	}
	
}
