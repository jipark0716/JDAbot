package com.JDABot;

import com.JDABot.*;
import com.JDABot.Listener.MessageListener;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.GuildController;

public class Main{

	public static void main(String[] args){
		JDA jda = null;
		try {
			jda = new JDABuilder("NTcwNTMxMTk4MzQ3MjQ3NjE2.XMAitQ.Lx7IZ0GJLdF7DloXKC73VdWIN_k").addEventListeners(new MessageListener()).build();
		} catch (Exception e) {
			System.out.println("연결 실패");
		}
		System.out.println("연결 성공");
	}
	
}
