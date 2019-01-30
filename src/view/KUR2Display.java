package view;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.MainController;
import model.RenderConstants;

public class KUR2Display {
	private JFrame jFrame;
	private Canvas canvas;
	private BufferStrategy bufferStrategy;
	
	private MainController mainController;
	
	private Painter currentPainter;
	private EmulatorPainter emulatorPainter;
	
	public KUR2Display(boolean fullscreen, int memorySize){
		initializeWindow(fullscreen);
		
		mainController=new MainController(this, memorySize);
		
		emulatorPainter=new EmulatorPainter(this);
		currentPainter=emulatorPainter;
		
		ControlWindow controlWindow=new ControlWindow(mainController);
		
		mainController.startEngine();
		jFrame.setVisible(true);
		controlWindow.setVisible(true);
	}
	
	public void repaint(){
		Graphics2D g2d=(Graphics2D)bufferStrategy.getDrawGraphics();
		
		currentPainter.drawScreen(g2d);
		
		g2d.dispose();
		bufferStrategy.show();
	}
	
	public static void main(String[] args){
		boolean fullscreen=false;
		int memorySize=32;
		
		for(String arg:args){
			if(arg.toLowerCase().equals("fullscreen")){
				fullscreen=true;
			}else if(arg.toLowerCase().startsWith("memory=")){
				try{
					memorySize=Integer.parseInt(arg.substring(7));
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "Fehler beim interpretieren der Argumente: Zahl konnte nicht gelesen werden: "+arg.substring(7), "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(ClassNotFoundException|IllegalAccessException|InstantiationException|UnsupportedLookAndFeelException e) {
			System.out.println("KUR2Display: Error setting look and feel: "+e.getMessage());
		}
		
		new KUR2Display(fullscreen, memorySize);
	}
	
	private void initializeWindow(boolean fullscreen){
		jFrame=new JFrame(RenderConstants.WINDOW_CAPTION);
		canvas=new Canvas();
		canvas.setBounds(0, 0, RenderConstants.SCREEN_WIDTH, RenderConstants.SCREEN_HEIGHT);
		
		JPanel contentPane=(JPanel)jFrame.getContentPane();
		contentPane.setPreferredSize(new Dimension(RenderConstants.SCREEN_WIDTH, RenderConstants.SCREEN_HEIGHT));
		contentPane.setLayout(null);
		contentPane.add(canvas);
		
		canvas.setIgnoreRepaint(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(fullscreen)
			jFrame.setUndecorated(true);
		jFrame.setResizable(false);
		jFrame.pack();
		jFrame.setLocationRelativeTo(null);
		
		if(fullscreen){
			GraphicsDevice graphicsDevice=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			if(graphicsDevice.isFullScreenSupported()){
				DisplayMode dm=new DisplayMode(RenderConstants.SCREEN_WIDTH, RenderConstants.SCREEN_HEIGHT, 32, 60);
				graphicsDevice.setFullScreenWindow(jFrame);
				graphicsDevice.setDisplayMode(dm);
			}else{
				System.out.println("VonNeumannDisplay: Error entering fullscreen mode: Fullscreen not supported");
				System.exit(-1);
			}
		}
		
		canvas.createBufferStrategy(2);
		bufferStrategy=canvas.getBufferStrategy();
	}

	public MainController getMainController() {
		return mainController;
	}

	public JFrame getjFrame() {
		return jFrame;
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
