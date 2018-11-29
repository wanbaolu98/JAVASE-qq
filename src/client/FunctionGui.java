package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.MouseInputListener;


import Connect.Message;
import client.LoginGui.LoginActionListener;
import user.User;

public class FunctionGui extends JFrame
{
	//定义好友窗口中需要的组件
	private JPanel One_jp1,One_jp2,One_jp3;
	private JButton One_jb1,addJB;
	private JScrollPane One_js1;
	private JLabel jlb1,jlb2;
	private FunctionMouse functionMouse;
	private JTextField qianM,addfriend;
	private FunctionAction FunAction;
	private User my;
	private Map<String,String> allFriend;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Map<Integer,ChatFrame> hyMap;
	
	public FunctionGui(User use,Map  allFriend,ObjectOutputStream  out,ObjectInputStream in) throws HeadlessException 
	{
		super("好友列表");
		this.out = out;
		this.my= use;
		this.in = in;
		this.allFriend = allFriend;
		hyMap = new HashMap<Integer, ChatFrame>();
		Functioninit();
	}
	
	public void Functioninit()
	{
		//实例化鼠标和按钮监听器
		functionMouse = new FunctionMouse();
		FunAction = new FunctionAction();
		
		//将第一张面版实例化并设置为自定义布局
		One_jp3= new JPanel();
		One_jp3.setLayout(null);
		//创建两个标签分别显示头像和昵称
		jlb1 = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage("image//tx.jpg").getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
		jlb1.setBounds(10,10,80,80);
		
		jlb2 = new JLabel(my.getName());
		jlb2.setFont(new Font("华文行楷",1,25));
		jlb2.setForeground(Color.BLUE); 
		jlb2.setBounds(110,20,80,20);
		//创建一个单行文本框显示签名
		qianM = new JTextField(my.getQianm());
		qianM.setEditable(false);
		qianM.setBackground(Color.white);
		qianM.setBounds(110,60,150,20);
		
		addfriend = new JTextField();
		addJB = new JButton("添加好友");
		addJB.addActionListener(FunAction);
		addfriend.setBounds(0,110,150,25);
		addJB.setBounds(160,110,100,25);
		
		One_jp3.add(jlb1);
		One_jp3.add(jlb2);
		One_jp3.add(qianM);
		One_jp3.add(addfriend);
		One_jp3.add(addJB);
		
		
		//实例化0三个按钮，并设置位置和大小
		One_jb1=new JButton("我的好友");
		One_jb1.setBounds(0,140,280,35);
		//按钮事件响应，当按下好友按钮是可以展开和关闭按钮
		One_jb1.addActionListener(FunAction);
		One_jp3.add(One_jb1);
		
		//设置窗口并将面板1放入窗口
		this.add(One_jp3);	
		this.setResizable(false);
		this.setSize(295, 570);
		this.setLocation(1000,130);
		this.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{
				Message fsMes = new Message();
				fsMes.setFrom(my);
				fsMes.setType("deleter");
				
				try {
					out.writeObject(fsMes);
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		this.setVisible(true);
		this.paintComponents(getGraphics());
		startMessageReciverThread();
	}
	
	
	class FunctionAction implements ActionListener
	{
		private int dianj = 1;
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==addJB) 
			{
				String add = addfriend.getText();
				//判断需要添加的好友信息是否为空
				if(add.length()!=0)
				{
					if(my.getId().equals(add)) 
					{
						JOptionPane.showMessageDialog(FunctionGui.this, "您不能添加自己为好友");
					}else 
					{
						//将添加人、需要添加的账号以及信息类型封装为信息类
						Message  message=new Message();
						message.setContent(add);
						message.setFrom(my);
						message.setType("addFriend");
						//将信息发送给服务器
						try {
							out.writeObject(message);
							out.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}else {
				//定义一个变量记录位置
				int weiz;
				dianj ++;
				//清除面板所有组件重新绘画
				One_jp3.removeAll();
				
				//实例化第二个面板，存储好友信息
				One_jp2= new JPanel(new GridLayout(allFriend.size(),1));
				JLabel[] jbl = new JLabel[allFriend.size()];
				int j = jbl.length;
				//设置滚动条一页可以显示的好友数量
				if(jbl.length>10) {j = 10;}
				
				//构建虚拟好友
				Set<Entry<String,String>> it = allFriend.entrySet();
				int i = 0;
				for(Entry<String,String> a:it)
				{
						jbl[i] = new JLabel(a.getValue()+"",new ImageIcon("image/qq.gif"),JLabel.LEFT);
						jbl[i].setDisplayedMnemonic(Integer.parseInt(a.getKey()));
						jbl[i].addMouseListener(functionMouse);
						One_jp2.add(jbl[i]);
						i++;
				}
				
				if(e.getSource() == One_jb1) 
				{
					weiz = 170;
					if(dianj%2==0) 
					{
						One_js1 = new JScrollPane(One_jp2);
						One_js1.setBounds(0,weiz,280,j*20);
						One_jp3.add(One_js1);
						weiz = weiz+j*20;
					}
				}
				
				//重新绘制按钮位置大小，并加入面板1
				One_jb1.setBounds(0,140,280,35);
				One_jp3.add(One_jb1);
			
				//展开操作
				One_jp3.add(jlb1);
				One_jp3.add(jlb2);
				One_jp3.add(qianM);
				One_jp3.add(addfriend);
				One_jp3.add(addJB);
				//刷新面板
				One_jp3.updateUI();	
			}
		}
	}
	class FunctionMouse extends MouseAdapter
	{
		
		//鼠标按下事件响应
		public void mouseClicked(MouseEvent e) 
		{
			if(e.getClickCount() == 2) 
			{
				System.out.println(((JLabel) e.getSource()).getDisplayedMnemonic());
				//记录点击的标签名
				String friendName=((JLabel) e.getSource()).getText();
				//判断该好友是否已经打开过
				if(hyMap.containsKey(((JLabel) e.getSource()).getDisplayedMnemonic())) 
				{
					//打开过就将该窗口显示
					hyMap.get(((JLabel) e.getSource()).getDisplayedMnemonic()).setVisible(true);
				}else {
					//如果没有打开过就新建该好友窗口并将其显示
					ChatFrame y = new ChatFrame(my, ((JLabel) e.getSource()).getDisplayedMnemonic(),friendName, out,in);
					y.setVisible(true);
					hyMap.put(((JLabel) e.getSource()).getDisplayedMnemonic(), y);
				}
			}
		}
		public void mouseEntered(MouseEvent e) 
		{
			//设置每次鼠标停留在的标签为红色
			((Component) e.getSource()).setForeground(Color.RED);
		} 
		public void mouseExited(MouseEvent e) 
		{
			//当鼠标离开该标签就显示为黑色
			((Component) e.getSource()).setForeground(Color.black);
		}
	}
	
	public void startMessageReciverThread(){
		class  MessageReciverThread extends Thread{
			@Override
			public void run() {
				while(true) {
					try {
						Message  message=(Message)in.readObject();
						if(message.getType().equals("askAdd")) 
						{
							Message  faMes=new Message();
							int yourChoide=JOptionPane.showConfirmDialog(FunctionGui.this, message.getFrom().getName()+"请求添加好友，是否同意", "请求添加", JOptionPane.INFORMATION_MESSAGE);
							System.out.println(yourChoide);
							if(yourChoide==0) 
							{
								faMes.setContent("yes");
								allFriend.put(message.getFrom().getId(),  message.getFrom().getName());
							}else if(yourChoide==1)
							{
								faMes.setContent("no");
							}else if(yourChoide==2)
							{
								faMes.setContent("null");
							}
							faMes.setFrom(new User( message.getFrom().getName(),message.getFrom().getId()));
							faMes.setType("AskReturn");
							System.out.println(faMes.getContent());
							faMes.setTo(my);
							//将信息发送给服务器
							try {
								out.writeObject(faMes);
								out.flush();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else if(message.getType().equals("AskReturn"))
						{
							if(message.getContent().equals("no")) 
							{
								JOptionPane.showMessageDialog(FunctionGui.this,message.getFrom().getName()+ "拒绝了您的添加请求");
							}else
							{
								JOptionPane.showMessageDialog(FunctionGui.this,message.getFrom().getName()+ "同意了您的添加请求");
								allFriend.put(message.getFrom().getId(),  message.getFrom().getName());
							}
							
						}else if(message.getType().equals("addreturn"))
						{
							if(message.getContent().equals("fail")) 
							{
								JOptionPane.showMessageDialog(FunctionGui.this, "该用户不存在", "错误提示", JOptionPane.ERROR_MESSAGE);
							}else
							{
								JOptionPane.showMessageDialog(FunctionGui.this, "添加成功，等待对方回复");
							}
						}else if(message.getType().equals("xiaxian")) 
						{
							JOptionPane.showMessageDialog(FunctionGui.this, "账号在异地登陆", "错误提示", JOptionPane.ERROR_MESSAGE);
							System.exit(0);
						}else if(message.getType().equals("liaot")) 
						{
							if(hyMap.containsKey(Integer.parseInt(message.getFrom().getId()))) {
								hyMap.get(Integer.parseInt(message.getFrom().getId())).setVisible(true);
								hyMap.get(Integer.parseInt(message.getFrom().getId())).getTextArea().append(message.getFrom().getName()+"\t"+message.getDate()+"\r\n"+message.getContent()+"\r\n\r\n");
								hyMap.get(Integer.parseInt(message.getFrom().getId())).getTextArea().selectAll();
							}else 
							{
								ChatFrame  c=new ChatFrame(my,Integer.parseInt(message.getFrom().getId()),message.getFrom().getName(),out,in);
								c.setVisible(true);
								c.getTextArea().append(message.getFrom().getName()+"\t"+message.getDate()+"\r\n"+message.getContent()+"\r\n\r\n");
								c.getTextArea().selectAll();
								hyMap.put(Integer.parseInt(message.getFrom().getId()), c);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		new MessageReciverThread().start();
	}
	
	
	
	
	
}
