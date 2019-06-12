package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.MainController;
import model.Constants;
import model.LocalizedStrings;

public class ControlWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private MainController mainController;
	
	private JTabbedPane tabbedPane;
	
	private JLabel speedLabel;
	private JSlider speedSlider;
	
	private String programLocation;
	private JLabel lblCurrentProgram;
	
	public ControlWindow(MainController mainController){
		this.mainController=mainController;
		
		setTitle(LocalizedStrings.guiFrameCaption);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		tabbedPane.addChangeListener(new ChangeListener() {@Override public void stateChanged(ChangeEvent e) {
			pauseSimulation();
		}});
		
		initProgramControl();
		initSimulationControl();
		initSteppingControl();
		
		pack();
		JFrame mainFrame=mainController.getKur2Display().getjFrame();
		setLocation(mainFrame.getLocation().x, mainFrame.getLocation().y);
		
		loadDefaultProgram();
	}
	
	private void initProgramControl() {
		JPanel programPanel=new JPanel();
		tabbedPane.add(LocalizedStrings.programControlTabName, programPanel);
		GridBagLayout gbl_programPanel = new GridBagLayout();
		gbl_programPanel.columnWidths = new int[]{0, 120, 0};
		gbl_programPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_programPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_programPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		programPanel.setLayout(gbl_programPanel);
		
		JButton btnProgrammLaden = new JButton(LocalizedStrings.programControlLoad);
		btnProgrammLaden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseProgram();
			}
		});
		
		JLabel label = new JLabel(LocalizedStrings.programControlProgramInMemory);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		programPanel.add(label, gbc_label);
		
		lblCurrentProgram = new JLabel("-");
		GridBagConstraints gbc_lblCurrentProgram = new GridBagConstraints();
		gbc_lblCurrentProgram.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCurrentProgram.insets = new Insets(0, 0, 5, 0);
		gbc_lblCurrentProgram.gridx = 1;
		gbc_lblCurrentProgram.gridy = 0;
		programPanel.add(lblCurrentProgram, gbc_lblCurrentProgram);
		GridBagConstraints gbc_btnProgrammLaden = new GridBagConstraints();
		gbc_btnProgrammLaden.gridwidth = 2;
		gbc_btnProgrammLaden.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProgrammLaden.insets = new Insets(0, 0, 5, 5);
		gbc_btnProgrammLaden.gridx = 0;
		gbc_btnProgrammLaden.gridy = 1;
		programPanel.add(btnProgrammLaden, gbc_btnProgrammLaden);
		
		JButton btnProgrammZurcksetzen = new JButton(LocalizedStrings.programControlReset);
		btnProgrammZurcksetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetProgram();
			}
		});
		GridBagConstraints gbc_btnProgrammZurcksetzen = new GridBagConstraints();
		gbc_btnProgrammZurcksetzen.gridwidth = 2;
		gbc_btnProgrammZurcksetzen.insets = new Insets(0, 0, 0, 5);
		gbc_btnProgrammZurcksetzen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProgrammZurcksetzen.gridx = 0;
		gbc_btnProgrammZurcksetzen.gridy = 2;
		programPanel.add(btnProgrammZurcksetzen, gbc_btnProgrammZurcksetzen);
	}
	
	private void initSimulationControl() {
		JPanel simulationPanel = new JPanel();
		tabbedPane.addTab(LocalizedStrings.simulationControlTabName, simulationPanel);
		GridBagLayout gbl_simulationPanel = new GridBagLayout();
		gbl_simulationPanel.columnWidths = new int[]{0, 0, 0};
		gbl_simulationPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_simulationPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_simulationPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		simulationPanel.setLayout(gbl_simulationPanel);
		
		JButton btnSimulationStarten = new JButton(LocalizedStrings.simulationControlStart);
		btnSimulationStarten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startSimulation();
			}
		});
		GridBagConstraints gbc_btnSimulationStarten = new GridBagConstraints();
		gbc_btnSimulationStarten.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSimulationStarten.insets = new Insets(0, 0, 5, 5);
		gbc_btnSimulationStarten.gridx = 0;
		gbc_btnSimulationStarten.gridy = 0;
		simulationPanel.add(btnSimulationStarten, gbc_btnSimulationStarten);
		
		JButton btnSimulationPausieren = new JButton(LocalizedStrings.simulationControlPause);
		btnSimulationPausieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseSimulation();
			}
		});
		GridBagConstraints gbc_btnSimulationPausieren = new GridBagConstraints();
		gbc_btnSimulationPausieren.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSimulationPausieren.insets = new Insets(0, 0, 5, 0);
		gbc_btnSimulationPausieren.gridx = 1;
		gbc_btnSimulationPausieren.gridy = 0;
		simulationPanel.add(btnSimulationPausieren, gbc_btnSimulationPausieren);
		
		JLabel lblSimulationsgeschwindigkeit = new JLabel(LocalizedStrings.simulationControlSpeed);
		GridBagConstraints gbc_lblSimulationsgeschwindigkeit = new GridBagConstraints();
		gbc_lblSimulationsgeschwindigkeit.anchor = GridBagConstraints.WEST;
		gbc_lblSimulationsgeschwindigkeit.insets = new Insets(0, 0, 5, 5);
		gbc_lblSimulationsgeschwindigkeit.gridx = 0;
		gbc_lblSimulationsgeschwindigkeit.gridy = 1;
		simulationPanel.add(lblSimulationsgeschwindigkeit, gbc_lblSimulationsgeschwindigkeit);
		
		speedLabel = new JLabel("100%");
		GridBagConstraints gbc_speedLabel = new GridBagConstraints();
		gbc_speedLabel.insets = new Insets(0, 0, 5, 0);
		gbc_speedLabel.gridx = 1;
		gbc_speedLabel.gridy = 1;
		simulationPanel.add(speedLabel, gbc_speedLabel);
		speedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		speedSlider = new JSlider();
		GridBagConstraints gbc_speedSlider = new GridBagConstraints();
		gbc_speedSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_speedSlider.gridwidth = 2;
		gbc_speedSlider.insets = new Insets(0, 0, 0, 5);
		gbc_speedSlider.gridx = 0;
		gbc_speedSlider.gridy = 2;
		simulationPanel.add(speedSlider, gbc_speedSlider);
		speedSlider.setValue(30);
		speedSlider.setMaximum(60);
		speedSlider.setPreferredSize(new Dimension(400, 50));
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				updateSpeed();
			}
		});
	}
	
	private void initSteppingControl() {
		JPanel steppingPanel=new JPanel();
		tabbedPane.add(LocalizedStrings.steppingTabName, steppingPanel);
		GridBagLayout gbl_steppingPanel = new GridBagLayout();
		gbl_steppingPanel.columnWidths = new int[]{0, 0};
		gbl_steppingPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_steppingPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_steppingPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		steppingPanel.setLayout(gbl_steppingPanel);
		
		JButton btnNchstenBefehlAusfhren = new JButton(LocalizedStrings.steppingMachineInstruction);
		btnNchstenBefehlAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeInstruction();
			}
		});
		
		JButton btnEinzelschrittAusfhren = new JButton(LocalizedStrings.steppingStep);
		btnEinzelschrittAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeStep();
			}
		});
		GridBagConstraints gbc_btnEinzelschrittAusfhren = new GridBagConstraints();
		gbc_btnEinzelschrittAusfhren.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEinzelschrittAusfhren.insets = new Insets(0, 0, 5, 0);
		gbc_btnEinzelschrittAusfhren.gridx = 0;
		gbc_btnEinzelschrittAusfhren.gridy = 0;
		steppingPanel.add(btnEinzelschrittAusfhren, gbc_btnEinzelschrittAusfhren);
		
		JButton btnMicroinstruktionAusfhren = new JButton(LocalizedStrings.steppingMicroinstruction);
		btnMicroinstruktionAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeMicroInstruction();
			}
		});
		GridBagConstraints gbc_btnMicroinstruktionAusfhren = new GridBagConstraints();
		gbc_btnMicroinstruktionAusfhren.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMicroinstruktionAusfhren.insets = new Insets(0, 0, 5, 0);
		gbc_btnMicroinstruktionAusfhren.gridx = 0;
		gbc_btnMicroinstruktionAusfhren.gridy = 1;
		steppingPanel.add(btnMicroinstruktionAusfhren, gbc_btnMicroinstruktionAusfhren);
		GridBagConstraints gbc_btnNchstenBefehlAusfhren = new GridBagConstraints();
		gbc_btnNchstenBefehlAusfhren.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNchstenBefehlAusfhren.gridx = 0;
		gbc_btnNchstenBefehlAusfhren.gridy = 2;
		steppingPanel.add(btnNchstenBefehlAusfhren, gbc_btnNchstenBefehlAusfhren);
	}
	
	private void loadDefaultProgram() {
		File defaultProgramFile=new File(Constants.defaultProgramPath);
		if(defaultProgramFile.exists()) {
			loadProgram(Constants.defaultProgramPath, null);
		}
	}
	
	private void resetProgram() {
		if(programLocation!=null) {
			mainController.getKur2().resetMachine();
			mainController.getKur2().loadProgram(programLocation);
			JOptionPane.showMessageDialog(this, LocalizedStrings.dialogMachineReset, "KUR2", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void chooseProgram() {
		JFileChooser fileChooser=new JFileChooser(Constants.programFolderPath);
		if(fileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
			loadProgram(fileChooser.getSelectedFile().getPath());
		}
	}
	
	private void loadProgram(String path) {
		loadProgram(path, LocalizedStrings.dialogProgramLoaded);
	}
	
	private void loadProgram(String path, String message) {
		programLocation=path;
		lblCurrentProgram.setText(programLocation==null?"-":programLocation);
		
		mainController.getKur2().loadProgram(programLocation);
		if(message!=null)
			JOptionPane.showMessageDialog(this, message, "KUR2", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void startSimulation() {
		ControlWindow.this.mainController.setPaused(false);
	}
	
	private void pauseSimulation() {
		ControlWindow.this.mainController.setPaused(true);
	}
	
	private void updateSpeed() {
		ControlWindow.this.mainController.setExecutionSpeed(speedSlider.getValue());
		speedLabel.setText(ControlWindow.this.mainController.getExecutionSpeedInPercent());
		speedLabel.repaint();
	}
	
	private void executeStep() {
		mainController.executeCycleAndRepaint();
	}
	
	private void executeMicroInstruction() {
		mainController.executeMicrocodeInstructionAndRepaint();
	}
	
	private void executeInstruction() {
		mainController.executeInstructionAndRepaint();
	}
}
