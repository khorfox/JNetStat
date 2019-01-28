package it.khorfox.JNetStat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.IntConsumer;

import it.khorfox.JNetStat.command.Netstat;
import it.khorfox.JNetStat.command.Shutdown;

public class Monitor {

	private long _sleep = 0;
	private long _iter=0;
	
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private int _threshold = 100;
	
	public Monitor(String sleep,String iteration) {
		_sleep = Long.parseLong(sleep) * 60 * 1000; //convert in mills
		_iter  = Long.parseLong(iteration);
	}

	private void trace() {
		IntConsumer consumer = (diff) -> { 
			System.out.println(dateFormat.format(new Date()) + " - " + diff);
		};
		execute(consumer);
	}

	private void shutdown(String threshold) {
		_threshold = Integer.parseInt(threshold);
		IntConsumer consumer = (diff) -> {
			if(diff <= _threshold) {
				(new Shutdown(60)).execute();
			}
		};
		execute(consumer);
	}

	private void execute(IntConsumer consumer) {
		System.out.println("Start monitoring");
		Netstat command = new Netstat();
		int packetReceivedOld = command.getPacketsReceived();
		int packetReceivedNew = packetReceivedOld;
		for(int i = 0;i<_iter;i++) {
			try {
				Thread.sleep(_sleep); 
				packetReceivedNew = command.getPacketsReceived();
				consumer.accept(packetReceivedNew - packetReceivedOld);
				packetReceivedOld = packetReceivedNew;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("End monitoring");
	}

	public static void main(String... args) {
		Monitor monitor;
		if ((args != null) && (args.length >= 3)) {
			 monitor = new Monitor(args[1],args[2]);
			if(args[0].equals("1")){
				monitor.trace();
			} else if (args.length == 4) {
				monitor.shutdown(args[3]);
			} else{
				System.out.println("Need to specify: mode (1,2) sleep(in min) iteration (threshold)");
			}

		}
	}

}
