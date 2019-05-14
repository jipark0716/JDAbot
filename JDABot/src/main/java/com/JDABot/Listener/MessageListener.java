package com.JDABot.Listener;


import java.lang.reflect.Method;
import java.nio.channels.Channel;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.annotation.Nonnull;

import com.JDABot.util.MyVector;
import com.JDABot.util.Util;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.GuildController;
import oracle.net.aso.a;

public class MessageListener extends ListenerAdapter{
	StringBuffer result = new StringBuffer();

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		User author = event.getAuthor();
		
		if(author.isBot()) return; //º¿ÀÌ¸é ÁøÇà ¾ÈÇÔ
		Message msg = event.getMessage();
		String messageContent = msg.getContentRaw();
		int errorCode = -1;
		
		if(messageContent.indexOf("help") == 0 && messageContent.length() == 4) {
			errorCode = help(msg);
		}else if(messageContent.indexOf("»ç´Ù¸® ") == 0) {
			errorCode = ghostLeg(msg);
		}else if(messageContent.indexOf("ÆÀ°áÁ¤ ") == 0) {
			errorCode = chooseTeam(msg);
		}else if(messageContent.indexOf("µ¹¾Æ¿Í¶ó ") == 0) {
			errorCode = returnMember(msg);
		}
		result.delete(0,result.length());
		
		
		try {
			Util.printError(errorCode, this.getClass().getDeclaredMethod("onMessageReceived", MessageReceivedEvent.class),messageContent);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		User author = event.getAuthor();
		if(author.isBot()) return;
		Message msg = event.getMessage();
		String messageContent = msg.getContentRaw();
		int errorCode = help(msg);
		
		if(messageContent.indexOf("help") == 0 && messageContent.length() == 4) {
			errorCode = help(msg);
		}
	}


	private int ghostLeg(Message msg) {

		MyVector<Member> members = new MyVector<Member>();
		String[] contentRow = msg.getContentRaw().split(" ");
		TextChannel textChannel = msg.getTextChannel();
		Guild guild = textChannel.getGuild();
		
		if(contentRow.length<3) return 1;
		String channelName = contentRow[1]; //µ¹¸± Ã¤³Î ÀÌ¸§
		int teamCount = Integer.parseInt(contentRow[2]); //ÆÀ °¹¼ö
		VoiceChannel searchTarget = guild.getVoiceChannelsByName(channelName, true).get(0);
		if(contentRow.length>3) {
			Vector<String> filterName = new Vector<String>();
			for(int i = 0 ; i < contentRow[3].split(";").length;i++) {
				filterName.add(contentRow[3].split(";")[i]);
			}
			for(Iterator<Member> membersTemp = searchTarget.getMembers().iterator();membersTemp.hasNext();) {
				Member memberTemp = membersTemp.next();
				if(filterName.contains(memberTemp.getNickname())) continue;
				members.add(memberTemp);
			}
		}else members.addAll(searchTarget.getMembers());
		members.shuffle();
		for(int i = 0,system=0 ; i <members.size();i++) {
			if(members.size()/teamCount * system <= i && system != teamCount) {
				system++;
				result.append(system+"ÆÀ----------\n");
			}
			result.append(members.get(i).getNickname()+"\n");
		}
		try {textChannel.sendMessage(result).queue(	);}
		catch (Exception e) {return 15;}
		return 0;
	}
	private int returnMember(Message msg) {
		String[] contentRow = msg.getContentRaw().split(" ");
		Guild guild = msg.getGuild();
		
		if(contentRow.length<4) return 1;
		VoiceChannel returnChannel = guild.getVoiceChannelsByName(contentRow[1], true).get(0);
		GuildController gControl = new GuildController(guild);
		for(int i = 2 ; i <contentRow.length;i++) {
			VoiceChannel temp = null;
			try {
				temp = guild.getVoiceChannelsByName(contentRow[i],true).get(0);
			} catch (IndexOutOfBoundsException e) {
				return 31;
			}
			for(Iterator<Member> member = temp.getMembers().iterator();member.hasNext();) {
				try {
					gControl.moveVoiceMember(member.next(),returnChannel).queue();
				} catch (Exception e) {
					return 16;
				}
			}
		}
		return 0;
	}
	private int chooseTeam(Message msg) {
		String[] contentRow = msg.getContentRaw().split(" ");
		TextChannel textChannel = msg.getTextChannel();
		Guild guild = textChannel.getGuild();
		
		if(contentRow.length<2) return 1;
		GuildController gControl = new GuildController(guild);
		String mssageId = contentRow[1];
		String targetContent = textChannel.retrieveMessageById(mssageId).complete().getContentRaw();
		String[] targetRow = targetContent.split("\n");
		VoiceChannel temp = null;
		for(int i = 0,system = 1;i<targetRow.length;i++) {
			if(Util.isNumber(targetRow[i].charAt(0))&&targetRow[i].charAt(1) == 'ÆÀ') {
				if(++system+1 > contentRow.length)return 1;
				try {
					temp = guild.getVoiceChannelsByName(contentRow[system], true).get(0);
				} catch (IndexOutOfBoundsException e) {
					return 31;
				}
			}else {
				try {
					gControl.moveVoiceMember(guild.getMembersByNickname(targetRow[i],true).get(0),temp).queue();
				} catch (InsufficientPermissionException e) {
					return 16;
				} catch (IndexOutOfBoundsException e) {
					return 32;
				}
			}
		}
		return 0;
	}
	private int help(Message msg) {
		MessageChannel textChannel = msg.getTextChannel();
		
		result.append("µµ¿ò");
		
		try {
			textChannel.sendMessage(result).queue();
		} catch (Exception e) {
			return 15;
		}
		
		return 0;
	}
}
