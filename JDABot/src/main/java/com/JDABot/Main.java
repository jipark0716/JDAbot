package com.JDABot;

import com.JDABot.*;
import com.JDABot.DB.DBconnect;
import com.JDABot.Listener.ChangeMemberOption;
import com.JDABot.Listener.MessageListener;
import com.JDABot.Listener.Ready;
import com.JDABot.util.SetAlarm;

import java.util.Scanner;

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
		
		Scanner scanner = new Scanner(System.in);
		
		JDA jda = null;
		try {
			jda = new JDABuilder("NTcwNTMxMTk4MzQ3MjQ3NjE2.XMAitQ.Lx7IZ0GJLdF7DloXKC73VdWIN_k").build();
		} catch (Exception e) {
			System.out.println("연결 실패");
		}
		jda.addEventListener(new Ready());
		jda.addEventListener(new MessageListener());
//		jda.addEventListener(new ChangeMemberOption());
		
		if(scanner.next().equals("exit")) {
			DBconnect.close();
			jda.shutdownNow();
			scanner.close();
			System.exit(1);
		}
		
	}
	
}
