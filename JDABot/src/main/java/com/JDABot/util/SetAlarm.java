package com.JDABot.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

import com.JDABot.DB.DBconnect;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class SetAlarm extends Thread{

	ResultSet res = null;
	int nextEventTime = 0;
	private JDA jda = null;
	MyVector<TextChannel> target = null;
	long waitTime = Util.getNextTime(2400);
	
	public SetAlarm(JDA jda) {
		super();
		this.jda = jda;
		target = new MyVector<TextChannel>();
		target.add(jda.getTextChannelById("573834530553724928"));
		target.add(jda.getTextChannelById("570529469589225484"));
		return;
	}
	
	@Override
	public void run() {
		StringBuffer result = new StringBuffer();
		while(true) {
			result.delete(0,result.length());
			res = DBconnect.sendQuery("select * from next_event_view");
			try {
				for(int system = 0;res.next();system++) {
					if(system==0) waitTime = Util.getNextTime(res.getInt(1));
					result.append(res.getString(2)+"\n"+res.getString(3)+"\n");
				}
				DBconnect.close();
			} catch (Exception e) {
				System.out.println(e.getClass().getName());
				System.out.println(e.getMessage());
			}
			try {sleep(waitTime);}
			catch (Exception e) {System.out.println("setAlarm.java 48");};
			
			for(Iterator<TextChannel> targetTC = target.iterator();targetTC.hasNext();) {
				TextChannel temp = targetTC.next();
			  try {
				temp.sendMessage(result).queue();
			  } catch (Exception e) {
				System.out.println(temp);
				System.out.println(e.getClass().getName());
				System.out.println(e.getMessage());
			  }
			}
		} 
	}
}
