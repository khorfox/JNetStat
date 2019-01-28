package it.khorfox.JNetStat.command;

import java.util.ArrayList;

public class Netstat extends DosCommand {

	public Netstat() {
		super("cmd /c netstat -es");
	}

	public int getPacketsReceived() {
		ArrayList<String> result = super.execute();
//		result.forEach(System.out::println);
		int ret = result.stream()
				.filter(line -> line.trim().startsWith("Packets Received"))
				.mapToInt(line -> Integer.parseInt(line.substring(line.indexOf("=")+2))).sum();
		return ret;
	}

}
