package it.khorfox.JNetStat.sample;

import java.io.IOException;
import java.io.InputStream;
/*
 * 
 * Thanks to: https://stackoverflow.com/questions/16452964/how-do-i-execute-windows-commands-in-java
 * 
 * Source: https://www.codepuran.com/java/execute-dos-command-java/
 * 
 */
public class ExecuteDOSCommand {
	public static void main(String[] args) {
		final String dosCommand = "ipconfig";
		try {
			final Process process = Runtime.getRuntime().exec(dosCommand );
			final InputStream in = process.getInputStream();
			int ch;
			while((ch = in.read()) != -1) {
				System.out.print((char)ch);
			}        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
