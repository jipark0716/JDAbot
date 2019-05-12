package com.JDABot.Listener;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDA.Status;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceGuildMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.GuildController;

public class ChangeMemberOption extends ListenerAdapter{
	
	public void onGuildVoiceGuildMute(@Nonnull GuildVoiceGuildMuteEvent event) {
		JDA jda = event.getJDA();
		Guild server = event.getGuild();
		GuildController gControl = new GuildController(server);
		List<GuildVoiceState> gVoice = server.getVoiceStates();		
		
//		for(Iterator<GuildVoiceState> gVoices = gVoice.iterator();gVoices.hasNext();) {
//			GuildVoiceState temp = gVoices.next();
//			temp.
//		}
		
		Member target = event.getMember();
		Member tree = null;
		try {
			tree = server.getMember(jda.getUserById("394358042172588033"));
		} catch (Exception e) {
			System.out.println(e.getClass().getName());
			System.out.println(e.getMessage());
			return;
		}
		
		if(tree!=null&&target.equals(tree)) {
			gControl.setMute(tree,true).queue();
//			jda.getTextChannelById("565880846452654094").sendMessage("나무는 발언건 없다").queue();
		}
	}
	
}
