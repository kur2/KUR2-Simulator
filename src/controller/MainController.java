package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JFrame;

import controller.InputController.CursorDirection;
import controller.InputController.InputControllerListener;
import model.Constants;
import view.KUR2Display;
import vonNeumannEmulatorKUR2.KUR2;

public class MainController implements ActionListener{
	private KUR2Display kur2Display;
	private InputControllerListener inputControllerListener;
	
	private KUR2 kur2;
	
	private ClockGenerator clockGenerator;
	
	private int framesPassed,fps;
	private long lastFrameTime;
	
	private boolean paused;
	
	private int executionDelay=10;
	private int executionDelayTarget=10;
	
	public MainController(KUR2Display kur2Display, int memorySize){
		this.kur2Display=kur2Display;
		new InputController(this);
		
		paused=true;
		
		kur2=new KUR2(memorySize);
		
//		kur2.loadTestProgram();
//		kur2.printMemoryDump();
	}
	
	public void startEngine(){
		clockGenerator=new ClockGenerator(Constants.FRAME_DELAY, this);
		clockGenerator.start();
	}
	
	public void stopEngine(){
		if(clockGenerator!=null)
			clockGenerator.shutDown();
		clockGenerator=null;
	}
	
	public void actionPerformed(ActionEvent ae){
		countFps();
		
		if(!paused){
			if(executionDelay==0){
				executionDelay=executionDelayTarget;
				kur2.simulateCycle(true);
			}else{
				executionDelay--;
			}
		}
		
		kur2Display.repaint();
	}
	
	public void executeCycleAndRepaint() {
		kur2.simulateCycle(true);
		kur2Display.repaint();
	}
	
	public void executeMicrocodeInstructionAndRepaint() {
		kur2.simulateMicrocodeInstruction();
		kur2Display.repaint();
	}
	
	public void executeInstructionAndRepaint() {
		kur2.simulateInstruction();
		kur2Display.repaint();
	}
	
	private void countFps(){
		if(System.nanoTime() >= lastFrameTime+1000*1000*1000){
			lastFrameTime=System.nanoTime();
			fps=framesPassed;
			framesPassed=1;
		}else{
			framesPassed++;
		}
	}
	
	public InputControllerListener getInputControllerListener(){
		if(inputControllerListener==null)
			inputControllerListener=new InputControllerListener() {
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {}
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {}
				
				@Override
				public void mouseMoved(MouseEvent e) {}
				
				@Override
				public void mouseDragged(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {}
				
				@Override
				public void keyReleased(int keyEvent) {}
				
				@Override
				public void keyPressed(int keyEvent) {}
				
				@Override
				public void escHit() {System.exit(0);}
				
				@Override
				public void cursorReleased(CursorDirection cursorDirection) {}
				
				@Override
				public void cursorPressed(CursorDirection cursorDirection) {
					switch(cursorDirection){
					case RIGHT:
						executionDelayTarget--;
						executionDelayTarget=executionDelayTarget<0?0:executionDelayTarget;
						executionDelay=executionDelay<executionDelayTarget?executionDelay:executionDelayTarget;
						break;
					case DOWN:
						paused=true;
						break;
					case UP:
						paused=false;
						break;
					case LEFT:
						executionDelayTarget++;
						break;
					}
				}
			};
		return inputControllerListener;
	}
	
	public JFrame getInputSourceFrame(){
		return kur2Display.getjFrame();
	}

	public KUR2 getKur2() {
		return kur2;
	}

	public KUR2Display getKur2Display() {
		return kur2Display;
	}

	public int getFps() {
		return fps;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public void setExecutionSpeed(int speed){
		executionDelayTarget=60-speed;
	}
	
	public String getExecutionSpeedInPercent(){
		String speed="";
		if(executionDelayTarget>0){
			speed=""+(int)(30*100/executionDelayTarget)+"%";
		}else{
			speed="MAX";
		}
		return speed;
	}
}
