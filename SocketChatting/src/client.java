import java.net.*;
import java.io.*;

public class client {

	public static void main(String[] args) throws IOException{
		Socket s = new Socket("localhost", 4567);
		

		PrintWriter pr = new PrintWriter(s.getOutputStream());
		pr.println("it works!");
		pr.flush();


		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		
		String str = br.readLine();
		System.out.println("server: "+str);
	}

}
