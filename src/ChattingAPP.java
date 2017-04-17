import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class ChattingAPP {
	Frame mainFrame = new Frame("My chatting application");
	TextField textFieldIP = new TextField(15);
	List messageList = new List(6);
	UDPServer udpServer;
	// 192.168.56.1
	
	public ChattingAPP(){
		udpServer = new UDPServer(this);
		//start the receiving part
		new Thread(udpServer).start();;
	}


/*
 * 	DatagramSocket ds;
	public ChattingAPP(){
		try{
			ds = new DatagramSocket(6677);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		new Thread(new Runnable(){
			public void run(){
				byte buf[] = new byte[1024];
				DatagramPacket dp = new DatagramPacket(buf, 1024);
				while(true){
					try{
						ds.receive(dp);
						messageList.add(new String(buf, 0, dp.getLength()) + ":from" +
						dp.getAddress().getHostAddress(),0);
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
		}).start(); 
	}
	*/

	public static void main(String[] args) {
		ChattingAPP chattingApp = new ChattingAPP();
		chattingApp.initialize();
	}

	public void initialize() {
		mainFrame.setSize(300, 300);
		mainFrame.add(messageList);

		Panel p = new Panel();
		p.setLayout(new BorderLayout());
		p.add("West", textFieldIP);
		TextField textFieldData = new TextField(20);
		p.add("East", textFieldData);
		mainFrame.add("South", p);

		mainFrame.setVisible(true);
		mainFrame.setResizable(false);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				udpServer.ds.close();
				mainFrame.setVisible(false);
				mainFrame.dispose();
				System.exit(0);
			}
		});

		textFieldData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] buf;
				buf = e.getActionCommand().getBytes();
				udpServer.sendMessage(buf);
				((TextField) e.getSource()).setText("");
			}

		});  
	}

}
