package client;

import java.awt.*;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;


import Connect.Message;
import user.User;

public class RegisterGui extends JFrame
{
	private ObjectOutputStream  out;
	private ObjectInputStream  in;
	private LoginGui  loginFrame;
	private JLabel N_jp;
	private JPanel jp1,jp2;
	private JLabel JLb1,JLb2,JLb3,JLb4,JLb5,JLb6;
	private JPasswordField jpss1,jpss2;
	private JTextField jtext1,jtext2,jtext3;
	private JButton jb1,jb2;
	private JRadioButton rdbtnMale,rdbtnFemale;
	private ButtonGroup  sex;
	private RegisterAction registerAction;
	
	public RegisterGui(ObjectOutputStream  out,ObjectInputStream  in,LoginGui  loginFrame) throws HeadlessException 
	{
		super("注册账号");
		this.in = in;
		this.out = out;
		//将登陆界面传输过来，在后期返回登陆界面时直接调用，不需要重新new登陆界面
		this.loginFrame = loginFrame;
		Registerinit();
	}
	public void Registerinit() 
	{
		N_jp = new JLabel(new ImageIcon("image/zhuche.jpg"));
		
		registerAction = new RegisterAction();
		
		
		jp1 =new JPanel(null); 
		JLb1 =new JLabel("账号");
		JLb1.setBounds(40, 15, 80, 20);
		
		JLb2 =new JLabel("昵称");
		JLb2.setBounds(40, 50, 80, 20);
		
		JLb3 =new JLabel("密码");
		JLb3.setBounds(40, 85, 80, 20);
		JLb4 =new JLabel("确认密码");
		JLb4.setBounds(30, 120, 80, 20);
		JLb5 =new JLabel("性别");
		JLb5.setBounds(40, 155, 80, 20);
		JLb6 =new JLabel("个性签名");
		JLb6.setBounds(30, 190, 80, 20);
		
		jtext1 = new JTextField();
		jtext1.setBounds(100,15,120, 25);
		
		jtext2 = new JTextField();
		jtext2.setBounds(100,50,120, 25);
		jtext3 = new JTextField();
		jtext3.setBounds(100,190,120, 25);
		
		jpss1 = new JPasswordField();
		jpss1.setBounds(100,85,120, 25);
		
		
		
		jpss2 = new JPasswordField();
		jpss2.setBounds(100,120,120, 25);
		
		rdbtnMale = new JRadioButton("男");
		rdbtnMale.setBounds(110, 155, 51, 23);
		jp1.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("女");
		rdbtnFemale.setBounds(180, 155, 76, 23);
		jp1.add(rdbtnFemale );
		sex=new ButtonGroup();
		
		sex.add(rdbtnMale);
		sex.add(rdbtnFemale);
		
		
		jp1.add(JLb1);
		jp1.add(jtext1);
		jp1.add(JLb2);
		jp1.add(jtext2);
		jp1.add(JLb3);
		jp1.add(jpss1);
		jp1.add(JLb4);
		jp1.add(jpss2);
		jp1.add(JLb5);
		jp1.add(jtext3);
		jp1.add(JLb6);
		
		
		jp2 = new JPanel();
		jb1 = new JButton("确认注册");
		jb2 = new JButton("返回登录");
		//为两个按钮添加事件
		jb1.addActionListener(registerAction);
		jb2.addActionListener(registerAction);
		
		jp2.add(jb1);
		jp2.add(jb2);
		
		
		
		
		this.add(N_jp,"North");
		this.add(jp1);
		this.add(jp2,"South");
		
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit ().getImage ("image/qq.gif"));
		this.setSize(280, 500);
		this.setResizable(false);
		this.setLocation(800,200);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);
	}
	
	
	
	
	
	class RegisterAction implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			
			if(e.getSource()==jb1 ) 
			{
				//定义需要注册的账号信息
				if(jtext1.getText().length()>9) 
				{
					JOptionPane.showMessageDialog(RegisterGui.this, "账号不能大于十位");
//					new RegisterGui(out,in,loginFrame);
//					RegisterGui.this.dispose();
				}else if(jpss1.getText().equals(jpss2.getText())==false) 
				{
					JOptionPane.showMessageDialog(RegisterGui.this, "两次输入密码不一致");
				}else 
				{
					User u = new User();
					u.setId(jtext1.getText());
					u.setPassword(jpss1.getText());
					u.setName(jtext2.getText());
					u.setQianm(jtext3.getText());
					u.setSex(rdbtnMale.isSelected()?"男":"女");
					
					//定义需要传输的信息对象，并将需要注册的账号信息和信息类型封装在该对象中
					Message fsMes = new Message();
					fsMes.setFrom(u);
					fsMes.setType("register");
					
					//将信息传输给服务器
					try {
						out.writeObject(fsMes);
						out.flush();
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					//接收服务器返回结果并做相对应的处理
					try {
						Message fhMes=(Message)in.readObject();
						if(fhMes.getContent().equals("success")) {
							int yourChoide=JOptionPane.showConfirmDialog(RegisterGui.this, "注册成功，是否立即登陆", "注册结果", JOptionPane.INFORMATION_MESSAGE);
							System.out.println(yourChoide);
							if(yourChoide==0) 
							{
								loginFrame.setVisible(true);
								RegisterGui.this.dispose();
							}
						}else {
							JOptionPane.showMessageDialog(RegisterGui.this, "注册失败，该账号已存在", "注册结果", JOptionPane.ERROR_MESSAGE);
						}
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}else if(e.getSource()==jb2 ) 
			{
				//返回登陆界面，直接将原界面显示
				loginFrame.setVisible(true);
				RegisterGui.this.dispose(); 
			}
		}
		
	}
}
