package client;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import Connect.Message;

import javax.swing.*;

public class ChatGui extends JFrame
{
	private JPanel JP;
	private JButton JB;
	private JTextArea JTA;
	private JEditorPane JTF;
	private JSplitPane JSp;
	private String fsid,jsid;
	
	public ChatGui (String fsid,String jsid) throws HeadlessException 
	{
		super(jsid);
		this.fsid = fsid;
		this.jsid = jsid;
		Chatinit();
	}
	
	public void Chatinit()
	{
		JSp = new JSplitPane(JSplitPane.VERTICAL_SPLIT );
		
		JTA = new JTextArea();
		JTA.setBounds(0,0,400,130);
		JSp.add(JTA);
		
		JP = new JPanel(new BorderLayout());
		JP.setBounds(0,130,400,120);
		
		JTF = new JEditorPane();
		JP.add(JTF);
		
		JB = new JButton("·¢ËÍ");
		JP.add(JB,"East");
		
		
		JSp.setDividerLocation(120);
		JSp.add(JP);
		
		
		this.add(JSp);
		
		
		
		this.setLocation(600,250);
		this.setIconImage(Toolkit.getDefaultToolkit ().getImage ("image/qq.gif"));
		this.setSize(400, 250);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	class ChatMouse extends MouseAdapter
	{
//		public void mouseClicked(MouseEvent e) 
//		{
//			if( e.getSource() == JB) 
//			{
//				Message mes = new Message();
//				mes.setFasong(fsid);
//				mes.setJieshou(jsid);
//			}
//		}
	}
	
}
