package com.JDABot.Listener;


import java.nio.channels.Channel;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.annotation.Nonnull;

import com.JDABot.util.MyVector;
import com.JDABot.util.Util;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.GuildController;

public class MessageListener extends ListenerAdapter{
	StringBuffer result = new StringBuffer();

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		User user = event.getAuthor();
		if(user.isBot()) return; //봇이면 진행 안함
		MyVector<Member> members = new MyVector<Member>();
		TextChannel author = event.getTextChannel();
		Guild server = event.getGuild();
		JDA jda = event.getJDA();
		Message msg = event.getMessage();
		String content = event.getMessage().getContentRaw();
		String[] contentRow = content.split(" ");
		String mainCommand = contentRow[0]; //주 명령어
		if(mainCommand.equals("%help")) {
			author.sendMessage("헬프").queue();
		}else if(mainCommand.equals("사다리")) {
			if(contentRow.length<3) return;
			String channelName = contentRow[1]; //돌릴 채널 이름
			int teamCount = Integer.parseInt(contentRow[2]); //팀 갯수
			VoiceChannel searchTarget = server.getVoiceChannelsByName(channelName, true).get(0);
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
					result.append(system+"팀----------\n");
				}
				result.append(members.get(i).getNickname()+"\n");
			}
			try {author.sendMessage(result).queue(	);}
			catch (Exception e) {}
			result.delete(0,result.length());
		}else if(mainCommand.equals("팀결정")) {
			if(contentRow.length<2) return;
			GuildController gControl = new GuildController(server);
			String mssageId = contentRow[1];
			String targetContent = author.retrieveMessageById(mssageId).complete().getContentRaw();
			String[] targetRow = targetContent.split("\n");
			VoiceChannel temp = null;
			for(int i = 0,system = 1;i<targetRow.length;i++) {
				if(Util.isNumber(targetRow[i].charAt(0))&&targetRow[i].charAt(1) == '팀') {
					if(++system+1 > contentRow.length)return;
					temp = server.getVoiceChannelsByName(contentRow[system], true).get(0);
				}else {
					try {
						gControl.moveVoiceMember(server.getMembersByNickname(targetRow[i],true).get(0),temp).queue();
					} catch (InsufficientPermissionException e) {
						System.out.println(server+"에서 채널이동 권한 없음");
						return;
					}
				}
			}
		}else if(mainCommand.equals("돌아와라")) {
			if(contentRow.length<4)return;
			VoiceChannel returnChannel = server.getVoiceChannelsByName(contentRow[1], true).get(0);
			GuildController gControl = new GuildController(server);
			for(int i = 2 ; i <contentRow.length;i++) {
				for(Iterator<Member> member = server.getVoiceChannelsByName(contentRow[i],true).get(0).getMembers().iterator();member.hasNext();) {
					gControl.moveVoiceMember(member.next(),returnChannel).queue();
				}
			}
		}
	}

}
