package socketChatting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class server {

	public static void main(String[] args) {
		//클라이언트 관리 컬렉션 
		Set<SocketChannel> allClient = new HashSet<>();
		
		// channel open
		try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
			
			//서비스 포트 설정 및 non-blocking 모드로 설정
			serverSocket.bind(new InetSocketAddress(15000));
			serverSocket.configureBlocking(false);
			
			// 채널 관리자(Selector) 생성 및 채널 등록
			Selector selector = Selector.open();
			// 접속을 요청하는 이벤트를 selecotr가 감지 : Operation-set bit for socket-accept operations.
			serverSocket.register(selector,  SelectionKey.OP_ACCEPT);
			
			System.out.println("--------Server Ready--------");
			
			// 입출력 시 사용할 바이트 버퍼
			ByteBuffer inputBuf = ByteBuffer.allocate(1024);
			ByteBuffer outputBuf = ByteBuffer.allocate(1024);

			// 클라이언트 접속 시작
			while (true) {
				// selector가 서버 소켓 채널의 이벤트 감지 // 이번트 발생까지 스레드 블로
				selector.select();
				
				// 발생한 이벤트 모두 Iterator에 담음
				// selector가 가지고 있는 selectionkey들 중, 이벤트 발생 채널의 객체만 모
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				
				while (iterator.hasNext()) {
					
					// 현재 처리할 이벤트를 임시 저장,iterator에서 삭제
					SelectionKey key = iterator.next();
					iterator.remove();
					
					// 연결 요청중인 클라이언트 처리 조건문 
					if (key.isAcceptable()) {
						
						// 연결 요청에 대한 socket channel을 생성
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel clientSocket = server.accept();
						
						// Selector의 관리를 받기 위해서 논블로킹 채널로 변경
						clientSocket.configureBlocking(false);
						
						// 연결된 클라이언트를 컬렉션에 추가
						allClient.add(clientSocket);
						
						// 아이디 입력을 위한 출력을 해당 채널에 해줌
						clientSocket.write(ByteBuffer.wrap("Enter ID : ".getBytes()));
						
						// 아이디를 입력받을 차례이므로 읽기 모드로 셀렉터에 등록
						clientSocket.register(selector, SelectionKey.OP_READ, new ClientInfo());
						
					}
					// 읽기 이벤트가 발생한 경우 (client -> server)
					else if (key.isReadable()) {
						
						// 현재 채널 정보를 가져옴 (attach된 사용자 정보도 가져옴)
						SocketChannel readSocket = (SocketChannel) key.channel();
						ClientInfo info = (ClientInfo) key.attachment();
						
						
						// 채널에서 데이터를 읽어옴
						try { 
							readSocket.read(inputBuf);
						}// client가 연결을 끊었다면 예외 처
						catch (Exception e) {
							// 현재 SelectionKey를 셀렉터의 관리 대상에서 삭제 
							key.channel();	
							allClient.remove(readSocket);
							
							// 서버에 종료 메시지 출력
							String end = info.getID() + "'s connection has been closed\n";
							System.out.print(end);
							
							// 자신을 제외한 클라이언트에게 종료 메시지 출력
							outputBuf.put(end.getBytes());
							for (SocketChannel s ; allClient) {
								if (!readSocket.equals(s)) {
									outputBuf.flip();
									s.write(outputBuf);								}
							}
							
							inputBuf.clear();
							outputBuf.clear();
							continue;
						}
						
						// 읽어온 데이터와 아이디 정보를 결합해 출력한 버퍼 생성
						inputBuf.flip();
					}
				}
			}
		}
	}
}

class clientInfo {
	
	private boolean idCheck = true;
	private String id;
	
	boolean isID() {
		return idCheck;
	}
	
	private void setCheck() {
		idCheck = false;
	}
	
	String getID() {
		return id;
	}
	
	void setID(String id) {
		this.id = id;
		setCheck();
	}
}
