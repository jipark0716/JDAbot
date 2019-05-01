package com.JDABot.util;

import java.util.Random;
import java.util.Vector;

public class MyVector<E> extends Vector<E>{
	
	Random r = null;
	
	public MyVector(){
		r = new Random();
	}
	
	public void shuffle() {
		if(super.size()==0)return;
		for(int i = super.size() ; i >= 0;i--) {
			int temp = i==0?0:r.nextInt(i);
			super.add(super.get(temp));
			super.remove(temp);
		}
	}

}
