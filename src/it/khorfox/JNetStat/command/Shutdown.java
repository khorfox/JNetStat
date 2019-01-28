package it.khorfox.JNetStat.command;

public class Shutdown extends DosCommand {

	public Shutdown(int interval) {
		super("cmd /c shutdown /s /t " + interval);
	}
	
}
