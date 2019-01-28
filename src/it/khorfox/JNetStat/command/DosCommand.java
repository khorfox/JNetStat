package it.khorfox.JNetStat.command;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DosCommand {

	private String command;

	public DosCommand(String command) {
		this.command = command;
		
	}

	public ArrayList<String> execute() {
		ArrayList<String> output = new ArrayList<String>();
		String commandOutput = "";
		try {
			Process process = Runtime.getRuntime().exec(command);
			InputStream in = process.getInputStream();
			int ch;
			boolean skip = false;
			while((ch = in.read()) != -1) {
				// new line skip \r\n
				if(ch=='\r'){
					skip = true;
					output.add(commandOutput);
					commandOutput = "";
				}
				if(!skip) {
					commandOutput += (char)ch;
				} 
				else {
					skip=false;
				}
			}    
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

}
