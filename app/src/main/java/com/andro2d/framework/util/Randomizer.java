package com.andro2d.framework.util;

import java.util.Random;

public class Randomizer {
	private static Random random = new Random();
	public Randomizer() {
	}
	public static int range(int start, int end){
		return random.nextInt(end-start+1)+start;
	}
}
