import java.net.*;
import java.io.*;

public class server {

	public static void main(String[] args) throws IOException{
		ServerSocket ss = new ServerSocket(4567);
		Socket s = ss.accept();
		
		System.out.println("client connected");
		
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader br = new BufferedReader(in);
		
		String str = br.readLine();
		System.out.println("client: "+str);
		
		PrintWriter pr = new PrintWriter(s.getOutputStream());
		pr.println("yes");	
		pr.flush();
	}

}
