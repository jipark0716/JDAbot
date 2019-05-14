package com.JDABot.util;

import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
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
		if(input.size()==0) {return;}
		for(int i = input.size() ; i >= 0;i--) {
			int temp = i==0?0:r.nextInt(i);
			input.add(input.get(temp));
			input.remove(temp);
		}
	}
	public static long getNextTime(int nextTime) {
		int nowTime = 0;
		nowTime = Integer.parseInt(new SimpleDateFormat("HHmm").format(System.currentTimeMillis()));
		nowTime = (nowTime/100)*60 + nowTime%100;
		nextTime = (nextTime/100)*60 + nextTime%100;
		return (nextTime - nowTime)*1000*60;
	}
	public static void printError(int errorCode,Method method,String msg) {
		if(errorCode == -1) return;

		System.out.print(method.getClass().getName()+" -> ");
		System.out.println(method.getName());
		System.out.print(errorCode+" : ");
		System.out.println(msg);
	}
}
