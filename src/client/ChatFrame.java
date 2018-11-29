package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Connect.Message;
import user.User;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.awt.event.ActionEvent;

public class ChatFrame extends JFrame {
	private ObjectOutputStream out;
	private ObjectInputStream  in;
	private String friend;
	private  User my;
	private JTextArea textArea,textArea_1;
	private JLabel jp;
	private JButton button,button_1;
	private ChatActionListener chatAction;
	private int id;

	public JTextArea getTextArea() {
		return textArea;
	}

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public ChatFrame(User my,int id,String friend,ObjectOutputStream  out,ObjectInputStream  in) {
		this.out=out;
		this.in = in;
		this.id = id;
		this.friend=friend;
		this.my=my;
		if(friend.equals("群聊")) {
			setTitle("群聊");
		}else {
			setTitle("和"+friend+"聊天中");
		}
		init();
		this.setForeground(Color.white);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 580, 500);
	}
		
	public void init() {
		chatAction = new ChatActionListener();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 400, 264);
		contentPane.add(scrollPane);
		
		textArea= new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 299, 400, 114);
		contentPane.add(scrollPane_1);
		
		textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		textArea_1.addKeyListener(new KeyAdapter() 
		{
			 public void keyPressed(KeyEvent e) 
			 {
				 if(e.isControlDown()&&e.getKeyCode()==KeyEvent.VK_ENTER) 
				 {
					 faSong();
				 }
			 }
			 
			});
		
		jp = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage("image//nv.JPG").getScaledInstance(150, 490, Image.SCALE_DEFAULT)));
		jp.setBounds(415,10,150,490);
		this.add(jp);
		
		button = new JButton("发送");
		button.addActionListener(chatAction);
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				//1.先提取用户编辑好的消息，然后将消息显示在上面的界面上
//				String m=textArea_1.getText();
//				textArea_1.setText("");
//				textArea.append("["+my+"]\t"+new Date().toLocaleString()+"\r\n   "+m+"\r\n\r\n");
//				textArea.setCaretPosition(textArea.getText().length());
//				//2.将消息发送到服务器端，让服务器转发给对应的用户
//				Message  message=new Message();
//				message.setContent(m);
//				if(user.equals("群聊")) {
//					message.setType("groupchat");
//				}else {
//					message.setType("chat");
//				}
//				
//				try {
//					out.writeObject(message);
//					out.flush();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			
//			}
//		});
		button.setBounds(180, 428, 93, 23);
		contentPane.add(button);
		
		button_1 = new JButton("关闭");
		button_1.setBounds(300, 428, 93, 23);
		contentPane.add(button_1);
	}
	
	public void faSong()
	{
		//接收编辑好的信息
		String m=textArea_1.getText();
		if(m.length()!=0) 
		{
			//信息上传后，将书写框清空
			textArea_1.setText("");
			//将信息打印在上面显示的窗口上
			textArea.append("["+my.getName()+"]\t"+new Date().toLocaleString()+"\r\n"+m+"\r\n\r\n");
			//new一个消息对象
			Message  message=new Message();
			//将自己的信息封装在消息对象中，方便服务器转发的时候可以告诉接收方是谁发送的消息
			message.setFrom(my);
			//将需要接收方的id发给服务器，服务器方便查找到对应的用户并将信息发给他
			message.setTo(new User(String.valueOf(id)));
			//将输入的内容封装在消息对象中
			message.setContent(m);
			//确定这个消息时聊天类型的
			message.setType("liaot");
		 
			try {
				//通过对象输出流将消息发给服务器，并刷新流
					out.writeObject(message);
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}else 
		{
			JOptionPane.showMessageDialog(ChatFrame .this, "发送的消息不能为空");
		}
	}
	
	 class ChatActionListener implements ActionListener 
	{
		 public void actionPerformed(ActionEvent e) 
		 {
			 if(e.getSource()==button) 
			 {
				 faSong();
			 } 
		 }
	}
}
