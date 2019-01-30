package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class InputController {
	//private MainController mainController;
	private InputControllerListener inputListener;
	private JFrame inputSourceFrame;
	
	private InputMap inputMap;
	private ActionMap actionMap;
	
	@SuppressWarnings("serial")
	public InputController(MainController mainController){
		//this.mainController=mainController;
		this.inputListener=mainController.getInputControllerListener();
		this.inputSourceFrame=mainController.getInputSourceFrame();
		
		
		/////////
		//MOUSE//
		/////////
		mainController.getKur2Display().getCanvas().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				inputListener.mousePressed(e);
			}
			public void mouseReleased(MouseEvent e){
				inputListener.mouseReleased(e);
			}
			public void mouseClicked(MouseEvent e){
				inputListener.mouseClicked(e);
			}
		});
		mainController.getKur2Display().getCanvas().addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e){
				inputListener.mouseMoved(e);
			}
			public void mouseDragged(MouseEvent e){
				inputListener.mouseDragged(e);
			}
		});
		mainController.getKur2Display().getCanvas().addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e){
				inputListener.mouseWheelMoved(e);
			}
		});
		
		
		////////////
		//KEYBOARD//
		////////////
		inputMap=((JPanel)inputSourceFrame.getContentPane()).getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		actionMap=((JPanel)inputSourceFrame.getContentPane()).getActionMap();
		
		//escape
		Action escPressed=new AbstractAction(){public void actionPerformed(ActionEvent arg0) {
			
		}};
		Action escReleased=new AbstractAction(){public void actionPerformed(ActionEvent arg0) {
			inputListener.escHit();
			//System.exit(0);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "escPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "escReleased");
		
		actionMap.put("escPressed", escPressed);
		actionMap.put("escReleased", escReleased);
		
		
		//arrow keys
		Action upPressed=new AbstractAction(){public void actionPerformed(ActionEvent arg0) {
			inputListener.cursorPressed(CursorDirection.UP);
		}};
		Action upReleased=new AbstractAction(){public void actionPerformed(ActionEvent arg0) {
			inputListener.cursorReleased(CursorDirection.UP);
		}};
		Action downPressed=new AbstractAction(){public void actionPerformed(ActionEvent arg0) {
			inputListener.cursorPressed(CursorDirection.DOWN);
		}};
		Action downReleased=new AbstractAction(){public void actionPerformed(ActionEvent arg0) {
			inputListener.cursorReleased(CursorDirection.DOWN);
		}};
		Action leftPressed=new AbstractAction(){public void actionPerformed(ActionEvent arg0) {
			inputListener.cursorPressed(CursorDirection.LEFT);
		}};
		Action leftReleased=new AbstractAction(){public void actionPerformed(ActionEvent arg0) {
			inputListener.cursorReleased(CursorDirection.LEFT);
		}};
		Action rightPressed=new AbstractAction(){public void actionPerformed(ActionEvent arg0) {
			inputListener.cursorPressed(CursorDirection.RIGHT);
		}};
		Action rightReleased=new AbstractAction(){public void actionPerformed(ActionEvent arg0) {
			inputListener.cursorReleased(CursorDirection.RIGHT);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "upPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "upReleased");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "downPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "downReleased");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "leftPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftReleased");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "rightPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rightReleased");
		
		actionMap.put("upPressed", upPressed);
		actionMap.put("upReleased", upReleased);
		actionMap.put("downPressed", downPressed);
		actionMap.put("downReleased", downReleased);
		actionMap.put("leftPressed", leftPressed);
		actionMap.put("leftReleased", leftReleased);
		actionMap.put("rightPressed", rightPressed);
		actionMap.put("rightReleased", rightReleased);
		
		
		//wasd
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "upPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "upReleased");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "downPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "downReleased");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "leftPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "leftReleased");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "rightPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "rightReleased");
		
		
		//Q
		Action qPressed=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyPressed(KeyEvent.VK_Q);
		}};
		Action qReleased=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyReleased(KeyEvent.VK_Q);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, false), "qPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, true), "qReleased");
		
		actionMap.put("qPressed", qPressed);
		actionMap.put("qReleased", qReleased);
		
		
		//E
		Action ePressed=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyPressed(KeyEvent.VK_E);
		}};
		Action eReleased=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyReleased(KeyEvent.VK_E);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, false), "ePressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, true), "eReleased");
		
		actionMap.put("ePressed", ePressed);
		actionMap.put("eReleased", eReleased);
		
		
		//R
		Action rPressed=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyPressed(KeyEvent.VK_R);
		}};
		Action rReleased=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyReleased(KeyEvent.VK_R);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0, false), "rPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0, true), "rReleased");
		
		actionMap.put("rPressed", rPressed);
		actionMap.put("rReleased", rReleased);
		
		
		//T
		Action tPressed=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyPressed(KeyEvent.VK_T);
		}};
		Action tReleased=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyReleased(KeyEvent.VK_T);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0, false), "tPressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0, true), "tReleased");
		
		actionMap.put("tPressed", tPressed);
		actionMap.put("tReleased", tReleased);
		
		
		//F1
		Action f1Pressed=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyPressed(KeyEvent.VK_F1);
		}};
		Action f1Released=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyReleased(KeyEvent.VK_F1);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false), "f1Pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, true), "f1Released");
		
		actionMap.put("f1Pressed", f1Pressed);
		actionMap.put("f1Released", f1Released);
		
		
		//F2
		Action f2Pressed=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyPressed(KeyEvent.VK_F2);
		}};
		Action f2Released=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyReleased(KeyEvent.VK_F2);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, false), "f2Pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, true), "f2Released");
		
		actionMap.put("f2Pressed", f2Pressed);
		actionMap.put("f2Released", f2Released);
		
		
		//F3
		Action f3Pressed=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyPressed(KeyEvent.VK_F3);
		}};
		Action f3Released=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyReleased(KeyEvent.VK_F3);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, false), "f3Pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, true), "f3Released");
		
		actionMap.put("f3Pressed", f3Pressed);
		actionMap.put("f3Released", f3Released);
		
		
		//F4
		Action f4Pressed=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyPressed(KeyEvent.VK_F4);
		}};
		Action f4Released=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyReleased(KeyEvent.VK_F4);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, false), "f4Pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0, true), "f4Released");
		
		actionMap.put("f4Pressed", f4Pressed);
		actionMap.put("f4Released", f4Released);
		
		
		//F5
		Action f5Pressed=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyPressed(KeyEvent.VK_F5);
		}};
		Action f5Released=new AbstractAction(){public void actionPerformed(ActionEvent e){
			inputListener.keyReleased(KeyEvent.VK_F5);
		}};
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, false), "f5Pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, true), "f5Released");
		
		actionMap.put("f5Pressed", f5Pressed);
		actionMap.put("f5Released", f5Released);
	}
	
	public interface InputControllerListener{
		public void escHit();
		
		public void cursorPressed(CursorDirection cursorDirection);
		public void cursorReleased(CursorDirection cursorDirection);
		public void keyPressed(int keyEvent);
		public void keyReleased(int keyEvent);
		
		public void mousePressed(MouseEvent e);
		public void mouseReleased(MouseEvent e);
		public void mouseClicked(MouseEvent e);
		public void mouseMoved(MouseEvent e);
		public void mouseDragged(MouseEvent e);
		public void mouseWheelMoved(MouseWheelEvent e);
	}
	
	public enum CursorDirection{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
}
