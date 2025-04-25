package com.lonar.master.a2zmaster.common;

public class NotificationModel {

	String to;
	String priority;
	String content_available;
	Notification notification;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getContent_available() {
		return content_available;
	}
	public void setContent_available(String content_available) {
		this.content_available = content_available;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	@Override
	public String toString() {
		return "NotificationModel [to=" + to + ", priority=" + priority + ", content_available=" + content_available
				+ ", notification=" + notification + "]";
	}
	
	
}
