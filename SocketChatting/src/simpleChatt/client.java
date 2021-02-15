import java.net.*;
import java.io.*;

public class client {

	public static void main(String[] args) throws IOException{
		Socket s = new Socket("localhost", 4567);
		

		OutputStream output = s.getOutputStream();	//서버에 데이터 보내기 위해 outputstream 객체 생
		PrintWriter pr = new PrintWriter(output);	//printwriter로 outputstream wrapping
		pr.println("it works!");	
		pr.flush();


		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader br = new BufferedReader(in);
		
		String str = br.readLine();
		System.out.println("server: "+str);
	}

}
