package com.sample;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class KP {
	
//	public static void main(String[] args) {
//	    ProcessHandle.allProcesses()
//	            .forEach(process -> System.out.println(processDetails(process)));
//	}
//
//	private static String processDetails(ProcessHandle process) {
//	    return String.format("%8d %8s %10s %26s %-40s",
//	            process.pid(),
//	            text(process.parent().map(ProcessHandle::pid)),
//	            text(process.info().user()),
//	            text(process.info().startInstant()),
//	            text(process.info().commandLine()));
//	}
//
//	private static String text(Optional<?> optional) {
//	    return optional.map(Object::toString).orElse("-");
//	}
	
	public static void main(String[] args) {

			String logOffTime = "18:"; //Shuts down at 6PM
			String logOffTime2 = "22:"; //Shuts down at 10PM if it's restarted again after 6 PM
			try {
				Robot robot = new Robot();
				PointerInfo a;
				Point b;
				Thread.sleep(1000);
				System.out.println("Started");
				while (true) {
					try {
						SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
						String time = parser.format(new Date().getTime());
						if (time.contains(logOffTime) || time.contentEquals(logOffTime2)) {
							System.out.println("It's time to log-off");
							closeRDP();
							System.exit(0);
						}
						a = MouseInfo.getPointerInfo();
						b = a.getLocation();
						int x, y;
						x = (int) b.getX();
						y = (int) b.getY();

						Thread.sleep(30000);

						a = MouseInfo.getPointerInfo();
						b = a.getLocation();
						int x1, y1;

						x1 = (int) b.getX();
						y1 = (int) b.getY();

						if (x == x1 && y == y1) {

							robot.mouseMove(x + 1, y + 1);
							System.out.println(x + "-" + y);
							Thread.sleep(30000);

							a = MouseInfo.getPointerInfo();
							b = a.getLocation();
							x = (int) b.getX();
							y = (int) b.getY();
							robot.mouseMove(x - 1, y - 1);
							System.out.println(x + "-" + y);
							Thread.sleep(30000);
						} else {
							System.out.print(".");
						}
					} catch (Exception e) {
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			} catch (AWTException | InterruptedException e) {
			}
	}
	

	public static void closeRDP() {
		try {
		    String line;
		    Process p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
		    BufferedReader input =
		            new BufferedReader(new InputStreamReader(p.getInputStream()));
		    
		    while ((line = input.readLine()) != null) {
		    	if(line.contains("mstsc")) {
		    		line = omitSpaces(line);
		    		System.out.println(line);
		    		System.out.println(line.split(" ")[0]);
		    		System.out.println(line.split(" ")[1]);
		    		int pid = Integer.parseInt(line.split(" ")[1]);
		    		destroyingProcessCreatedByDifferentProcess(pid);
		    	}
		    }
		    input.close();
		} catch (Exception err) {
		    err.printStackTrace();
		}
	}
	
	private static String omitSpaces(String line){
		return line.trim().replaceAll(" +", " ");
	}
	
	 public static void destroyingProcessCreatedByDifferentProcess(int pid) {
	        Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(pid);
	        ProcessHandle processHandle = optionalProcessHandle.get();
	        System.out.println("Killing RDP");
	        processHandle.destroy();
	        System.out.println("Killed RDP");
	        if (processHandle.isAlive()) {
	        	System.out.println("Destroying process forcibly");
	            processHandle.destroyForcibly();
	        }
	    }
}
