import java.net.*;
public class UDPServer implements Runnable{
	ChattingAPP chat;
	DatagramSocket ds;
	
	public UDPServer(ChattingAPP chat){
		this.chat = chat;

		try{
			ds = new DatagramSocket(6677);
			
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void receiveMessage(){
		byte buf[] = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf, 1024);
		while(true){
			try{
				ds.receive(dp);
				chat.messageList.add("From " +
						dp.getAddress().getHostAddress() + ": " + new String(buf, 0, dp.getLength()) ,0);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
	}
	
	public void sendMessage(byte[] buf){
		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length,
					InetAddress.getByName(chat.textFieldIP.getText()), 6677);
			ds.send(dp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void run() {
		receiveMessage();	
	}
	
}
