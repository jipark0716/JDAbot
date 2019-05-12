package com.JDABot.Listener;

import java.util.Iterator;
import java.util.Vector;

import com.JDABot.DB.DBconnect;
import com.JDABot.util.SetAlarm;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter{

	@Override
	public void onReady(ReadyEvent event) {
		System.out.println("연결 성공");
		JDA jda = event.getJDA();
		
		new SetAlarm(jda).start();
	}
}
