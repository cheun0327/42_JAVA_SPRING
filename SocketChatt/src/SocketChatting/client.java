package SocketChatting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class client {

	public static void main(String[] args) {
		Thread systemIn;
		
		// 서버 IP와 포트로 연결되는 소켓 채널 생
		try(SocketChannel socket = SocketChannel.open
				(new InetSocketAddress("172.30.1.29", 15000))){
			// 모니터 출력에 출력할 채널 생성
			WritableByteChannel out = Channel.newChannel(System.out);
			
			// 버퍼 생성
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			// 출력 담당 스레드 생성 및 실행
			systemIn = new Thread(new SystemIn(socket));
			systemIn.start();
			
			while (true) {
				// 읽어서 버퍼에 넣고
				socket.read(buf);
				buf.flip();
				// 모니터 출
				out.write(buf);
				buf.clear();
			}
		}	catch (IOException e) {
			System.out.println("connection has been closed\\n");
		}

	}

}

class SystemIn implements Runnable{
	
	SocketChannel socket;
	
	// 연결된 소켓 채널과 모니터 출력용 채널을 생성자로 받
	SystemIn(SocketChannel socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		// 키보드 입력받을 채널과 저장할 버퍼 생
		ReadableByteChannel in = Channels.newChannel(System.in);
		ByteBuffer buf = ByteBuffer.allocate(1024);
	}
	
	try {
		while (true) {
			// 읽어올 때까지 블로킹 상태로 대
			in.read(buf);
			buf.flip();
			// 입력한 내용을 서버로 출
			socket.write(buf);
			buf.clear();
		}
	}	catch (IOException e) {
		System.out.println("Cannot Chattt");
	}
}
