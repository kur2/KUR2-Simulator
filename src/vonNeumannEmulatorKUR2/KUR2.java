package vonNeumannEmulatorKUR2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class KUR2 {
	private ControlUnit controlUnit;
	private ALU alu;
	private MemoryUnit memoryUnit;
	private InputUnit inputUnit;
	private OutputUnit outputUnit;
	
	private PipeSystem pipeSystem;
	
	public KUR2(){
		buildDefaultMachine();
	}
	
	public KUR2(int memorySize){
		buildDefaultMachine(memorySize);
	}
	
	public void simulateInstruction(){
		simulateMicrocodeInstruction();
		while(controlUnit.isMicrocodeLeftInCurrentInstruction()){
			controlUnit.beginNextMicrocodeInstruction();
			simulateMicrocodeInstruction();
		}
		controlUnit.beginNextMicrocodeInstruction();
	}
	
	public void simulateMicrocodeInstruction(){
		while(!isAllComponentsReady()){
			simulateCycle(false);
		}
		controlUnit.beginNextMicrocodeInstruction();
	}
	
	public void simulateCycle(boolean allowNextMicrocodeInstructionToBegin){
		if(!isAllComponentsReady()){
			controlUnit.simulateCycle();
			memoryUnit.simulateCycle();
			alu.simulateCycle();
			inputUnit.simulateCycle();
			outputUnit.simulateCycle();
			pipeSystem.simulateCycle();
		}else if(allowNextMicrocodeInstructionToBegin){
			controlUnit.beginNextMicrocodeInstruction();
		}
	}
	
	public boolean isAllComponentsReady(){
		return controlUnit.isReady()
				&& alu.isReady()
				&& memoryUnit.isReady()
				&& inputUnit.isReady()
				&& outputUnit.isReady()
				&& pipeSystem.isReady();
	}
	
	
	public void buildDefaultMachine(){
		buildDefaultMachine(32);
	}
	
	public void buildDefaultMachine(int memorySize){
		controlUnit=new ControlUnit(this);
		alu=new ALU();
		memoryUnit=new MemoryUnit(memorySize);
		inputUnit=new InputUnit();
		outputUnit=new OutputUnit();
		
		pipeSystem=new PipeSystem(controlUnit, alu, memoryUnit, inputUnit, outputUnit);
	}
	
//	public void loadTestProgram(){
//		loadProgram("res/progs/testProgram.txt");
//	}
	
	public void resetMachine() {
		if(isMachineBuilt()) {
			controlUnit=new ControlUnit(this);
			alu=new ALU();
			memoryUnit=new MemoryUnit(memoryUnit.getMemory().size());
			inputUnit=new InputUnit();
			outputUnit=new OutputUnit();
			
			pipeSystem=new PipeSystem(controlUnit, alu, memoryUnit, inputUnit, outputUnit);
		}
	}
	
	private boolean isMachineBuilt() {
		return controlUnit!=null && alu!=null && memoryUnit!=null && inputUnit!=null && outputUnit!=null && pipeSystem!=null;
	}
	
	public void loadProgram(String filePath){
		if(memoryUnit==null){
			System.out.println("KUR2: Error loading program from file \""+filePath+"\": Emulator has no memory unit");
			System.exit(-1);
		}
		if(controlUnit==null){
			System.out.println("KUR2: Error loading program: Emulator has no control unit");
			System.exit(-1);
		}
		
		Scanner scanner=null;
		try{
			File file=new File(filePath);
			
			scanner=new Scanner(file);
			
			ArrayList<Integer> program=new ArrayList<Integer>();
			while(scanner.hasNext()){
				if(scanner.hasNextInt()){
					program.add(scanner.nextInt());
				}else{
					scanner.next();
				}
			}
			
			memoryUnit.loadProgram(program);
			controlUnit.resetExecution();
		}catch(IOException e){
			System.out.println("KUR2: Error loading program from file \""+filePath+"\": "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Die Datei "+filePath+" konnte nicht geöffnet werden. Befindet sie sich am richtigen Ort?", "Fehler", JOptionPane.OK_OPTION);
			System.exit(-1);
		}finally{
			if(scanner!=null)
				scanner.close();
		}
	}
	
	public void printMemoryDump(){
		System.out.println("Printing memory dump:");
		System.out.println("--BOM---");
		
		for(Integer s:memoryUnit.getMemory())
			System.out.println(s);
		
		System.out.println("--EOM---");
	}
	
	public void saveMemoryDump(){
		try{
			String filePath="res/progs/speicherauszug.txt";
			PrintWriter out=new PrintWriter(filePath);
			
			out.println("Printing memory dump:");
			out.println("--BOM---");
			
			for(Integer s:memoryUnit.getMemory())
				out.println(s);
			
			out.println("--EOM---");
			
			out.close();
		}catch(IOException e){
			System.out.println("KUR2: Error saving memory dump: "+e.getMessage());
			System.exit(-1);
		}
	}

	public ControlUnit getControlUnit() {
		return controlUnit;
	}

	public ALU getAlu() {
		return alu;
	}

	public MemoryUnit getMemoryUnit() {
		return memoryUnit;
	}

	public InputUnit getInputUnit() {
		return inputUnit;
	}

	public OutputUnit getOutputUnit() {
		return outputUnit;
	}

	public PipeSystem getPipeSystem() {
		return pipeSystem;
	}
}
