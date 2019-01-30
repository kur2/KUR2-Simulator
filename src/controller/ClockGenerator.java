package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockGenerator extends Thread{
	private ActionListener actionListener;
	
	private int clockDelay;
	private long lastNanos;
	
	private boolean running, stopping;
	
	public ClockGenerator(int clockDelay, ActionListener actionListener){
		this.actionListener=actionListener;
		this.clockDelay=clockDelay;
	}
	
//	@Override
//	public void start(){
//		running=true;
//		stopping=false;
//		super.start();
//	}
	
	public void shutDown(){
		running=false;
		stopping=true;
	}
	
	@Override
	public void run(){
		running=true;
		stopping=false;
		
		lastNanos=System.nanoTime();
		
		while(running){
			while(System.nanoTime()<lastNanos+(clockDelay*1000*1000)){
				
			}
			lastNanos=System.nanoTime();
			
			ActionEvent actionEvent=new ActionEvent(this, 0, null);
			actionListener.actionPerformed(actionEvent);
			
			long timeTaken=(System.nanoTime()-lastNanos)/(1000*1000);
			long sleepTime=(long)(2f/3f*(clockDelay-timeTaken));
			
			try {
				if(sleepTime>0)
					sleep(sleepTime);
			} catch (InterruptedException e) {
				break;
			}
			
			if(stopping)
				break;
		}
	}
}
