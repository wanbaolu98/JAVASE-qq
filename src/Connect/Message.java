package Connect;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import user.User;

public class Message implements Serializable
{
	private  User  from;
	private  User to;
	private  String content;
	private  String type;
	private  String date;
	private  Map<String,String> frendList;
	
	public Map<String, String> getFrendList() {
		return frendList;
	}
	public void setFrendList(Map<String, String> frendList) {
		this.frendList = frendList;
	}
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Message [from=" + from + ", to=" + to + ", content=" + content + ", type=" + type + ", date=" + date
				+ ", frendList=" + frendList + "]";
	}
	
	

}
