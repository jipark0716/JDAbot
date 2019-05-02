package com.JDABot.util;

import java.sql.Time;
import java.util.*;

import com.JDABot.DB.DBconnect;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class SetAlarm extends Thread{

	Vector<String> res = new Vector<String>();
	int nextEventTime = 0;
	private JDA jda = null;
	
	public SetAlarm(JDA jda) {
		super();
		this.jda = jda;
		return;
	}
	
	@Override
	public void run() {
		StringBuffer result = new StringBuffer();
		while(true) {
			result.delete(0,result.length());
			res = DBconnect.sendQueryVecter("select * from next_event_time_view", 3);
			try {
				Thread.sleep(Util.getNextTime(Integer.parseInt(res.get(0))));
				for(int i = 1 ; i <res.size();i++) {
					result.append(res.get(i)+"\n");
				}
				for(Iterator<Guild> server = jda.getGuilds().iterator();server.hasNext();) {
					server.next().getTextChannels().get(0).sendMessage(result).queue();
				}
				Thread.sleep(60*1000);
			}catch (NullPointerException e) {
				System.out.println("SetAlrm1");
			}
			catch (Exception e) {
				System.out.println("SetAlrm2");
			}
			res.clear();
		}
	}
}
