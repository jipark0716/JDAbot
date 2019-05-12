package com.JDABot.util;

import java.sql.Time;
import java.util.*;

import com.JDABot.DB.DBconnect;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class SetAlarm extends Thread{

	Vector<String> res = new Vector<String>();
	int nextEventTime = 0;
	private JDA jda = null;
	MyVector<TextChannel> target = null;
	
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
			res = DBconnect.sendQueryVecter("select * from next_event_time_view", 3);
		  try {
			if(res == null) {
				Thread.sleep(Util.getNextTime(2400));
				continue;
			}
			Thread.sleep(Util.getNextTime(Integer.parseInt(res.get(0))));	
		  }catch (Exception e) {
			  System.out.println(e.getClass().getName());
		  }
			for(int i = 1 ; i <res.size();i++) {
				result.append(res.get(i)+"\n");
			}
			for(Iterator<TextChannel> textChannel = target.iterator();textChannel.hasNext();) {
			  try {
				textChannel.next().sendMessage(result).queue();
			  } catch (Exception e) {
				System.out.println(e.getClass().getName());
				System.out.println(e.getMessage());
			  }
			}
		  try {Thread.sleep(60*1000);}
		  catch (Exception e) {System.out.println(e.getClass().getName());}
			res.clear();
		} 
	}
}
