package com.JDABot.util;

import java.util.List;
import java.util.Random;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class Util {

	public static boolean isNumber(String input) {
		try {
			Long.parseLong(input);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public static boolean isNumber(char input) {
		int temp = input - '0';
		if(temp>9)return false;
		return true;
	}
	
	public static <E> void shuffle(List<E> input){
		Random r = new Random();
		if(input.size()==0) {System.out.println("½´¹ß"); return;}
		for(int i = input.size() ; i >= 0;i--) {
			int temp = i==0?0:r.nextInt(i);
			input.add(input.get(temp));
			input.remove(temp);
		}
	}
	
}
