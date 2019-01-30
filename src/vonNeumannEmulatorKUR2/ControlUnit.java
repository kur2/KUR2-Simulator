package vonNeumannEmulatorKUR2;

import java.util.ArrayList;

import vonNeumannEmulatorKUR2.MicroPrograms.InstructionSet;
import vonNeumannEmulatorKUR2.MicroPrograms.MicroCode;

public class ControlUnit {
	private KUR2 kur2;
	
	private Pipe addressPipeMemory, addressPipeInput, addressPipeOutput;
	private Pipe controlPipeMemory, controlPipeInput, controlPipeOutput, controlPipeALU;
	private PipeConnector dataPipeConnectorMemory, dataPipeConnectorALU;
	
	private int dataField;
	private int programCounter;
	private ArrayList<String> instructionFetchingMiroProgram;
	private ArrayList<String> currentMicroProgram;
	
	private boolean instructionFetching;
	private int microProgramCounter;
	
	private boolean ready;
	private boolean halted;
	private boolean interruptMicroProg;
	
	private boolean printInstructions,printMicroCode,printFetchingMicroCode,printMemDumpOnHalt,systemExitOnHalt;
	
	public ControlUnit(KUR2 kur2){
		this(kur2, false, false, false, true, false);
	}
	
	public ControlUnit(KUR2 kur2, boolean printInstructions, boolean printMicroCode, boolean printFetchingMicroCode, boolean printMemDumpOnHalt, boolean systemExitOnHalt){
		this.kur2=kur2;
		this.printInstructions=printInstructions;
		this.printMicroCode=printMicroCode;
		this.printFetchingMicroCode=printFetchingMicroCode;
		this.printMemDumpOnHalt=printMemDumpOnHalt;
		this.systemExitOnHalt=systemExitOnHalt;
		
		instructionFetchingMiroProgram=MicroPrograms.getMicroProgramForInstruction(InstructionSet.fetch);//FETCH
		
		resetExecution();
		
		ready=true;
		halted=true;
	}
	
	public void beginNextMicrocodeInstruction(){
		if(halted)
			return;
		
		microProgramCounter++;
		ready=false;
		
		while(interruptMicroProg || (instructionFetching && microProgramCounter>=instructionFetchingMiroProgram.size()) || (!instructionFetching && microProgramCounter>=currentMicroProgram.size())){
			interruptMicroProg=false;
			instructionFetching=!instructionFetching;
			microProgramCounter=0;
			if(printInstructions)
				System.out.println("---------"+(instructionFetching?"Starting Fetching":"Starting Microprogram"));
		}
	}
	
	public boolean isMicrocodeLeftInCurrentInstruction(){
		if(instructionFetching)
			return true;
		
		return microProgramCounter<=currentMicroProgram.size()-2;
	}
	
	public void simulateCycle(){
		updateDataField();
		
		if(!ready && !halted){
			if(printMicroCode && (!instructionFetching || printFetchingMicroCode))
				System.out.println("ControlUnit: Executing: "+(instructionFetching?instructionFetchingMiroProgram.get(microProgramCounter):currentMicroProgram.get(microProgramCounter)));
			
			executeMicrocodeInstruction(instructionFetching?instructionFetchingMiroProgram.get(microProgramCounter):currentMicroProgram.get(microProgramCounter));
			
			ready=true;
		}
	}
	
	private void executeMicrocodeInstruction(String microInstruction){
		switch(microInstruction){
		//internal
		case MicroCode.intProgCounterToMemAdd:
			addressPipeMemory.setValue(programCounter);
			break;
		case MicroCode.intProgCounterIncrease:
			programCounter++;
			break;
		case MicroCode.intDecode:
			currentMicroProgram=MicroPrograms.getMicroProgramForInstruction(dataField);
			break;
		case MicroCode.intHalt:
			halted=true;
			if(printMemDumpOnHalt){
				kur2.printMemoryDump();
				kur2.saveMemoryDump();
			}
			if(systemExitOnHalt)
				System.exit(0);
			break;
		case MicroCode.intDataToProgCounter:
			programCounter=dataField;
			break;
		case MicroCode.intDataEqualsNull:
			if(dataField!=0)
				interruptMicroProg=true;
			break;
		case MicroCode.intDataNotNull:
			if(dataField==0)
				interruptMicroProg=true;
			break;
		case MicroCode.intDataGreaterNull:
			if(dataField<=0)
				interruptMicroProg=true;
			break;
		case MicroCode.intDataLessNull:
			if(dataField>=0)
				interruptMicroProg=true;
			break;
		case MicroCode.intDataToMemAdd:
			addressPipeMemory.setValue(dataField);
			break;
		case MicroCode.intDataToInputAdd:
			addressPipeInput.setValue(dataField);
			break;
		case MicroCode.intDataToOutputAdd:
			addressPipeOutput.setValue(dataField);
			break;
		
		//input
		case MicroCode.inWriteToALU:
			controlPipeInput.setValue(microInstruction);
			break;
		case MicroCode.inStop:
			controlPipeInput.setValue(null);
			break;
		
		//output
		case MicroCode.outReadFromALU:
			controlPipeOutput.setValue(microInstruction);
			break;
		case MicroCode.outStop:
			controlPipeOutput.setValue(null);
			break;
		
		//memory
		case MicroCode.memLoadToControl:
		case MicroCode.memLoadToALU:
		case MicroCode.memStoreFromAlu:
			controlPipeMemory.setValue(microInstruction);
			break;
		case MicroCode.memStop:
			controlPipeMemory.setValue(null);
			break;
		
		//alu
		case MicroCode.aluWriteToControl:
		case MicroCode.aluWriteToMem:
		case MicroCode.aluWriteToOutput:
		case MicroCode.aluReset:
		case MicroCode.aluPush:
		case MicroCode.aluAdd:
		case MicroCode.aluSub:
		case MicroCode.aluMul:
		case MicroCode.aluDiv:
		case MicroCode.aluMod:
			controlPipeALU.setValue(microInstruction);
			break;
		case MicroCode.aluStop:
			controlPipeALU.setValue(null);
			break;
		}
	}
	
	public void resetExecution(){
		programCounter=0;
		currentMicroProgram=MicroPrograms.getMicroProgramForInstruction(InstructionSet.noop);
		
		instructionFetching=true;
		microProgramCounter=0;
		halted=false;
		
		ready=false;
	}
	
	private void updateDataField(){
		dataField=dataPipeConnectorALU.getValue()!=null?(int)dataPipeConnectorALU.getValue():dataPipeConnectorMemory.getValue()!=null?(int)dataPipeConnectorMemory.getValue():dataField;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
//		this.ready = ready;
		updateDataField();
	}

	public boolean isHalted() {
		return halted;
	}

	public int getProgramCounter() {
		return programCounter;
	}

	public ArrayList<String> getInstructionFetchingMiroProgram() {
		return instructionFetchingMiroProgram;
	}

	public ArrayList<String> getCurrentMicroProgram() {
		return currentMicroProgram;
	}

	public boolean isInstructionFetching() {
		return instructionFetching;
	}

	public int getMicroProgramCounter() {
		return microProgramCounter;
	}

	public Pipe getAddressPipeMemory() {
		return addressPipeMemory;
	}

	public void setAddressPipeMemory(Pipe addressPipeMemory) {
		this.addressPipeMemory = addressPipeMemory;
	}

	public Pipe getAddressPipeInput() {
		return addressPipeInput;
	}

	public void setAddressPipeInput(Pipe addressPipeInput) {
		this.addressPipeInput = addressPipeInput;
	}

	public Pipe getAddressPipeOutput() {
		return addressPipeOutput;
	}

	public void setAddressPipeOutput(Pipe addressPipeOutput) {
		this.addressPipeOutput = addressPipeOutput;
	}

	public Pipe getControlPipeMemory() {
		return controlPipeMemory;
	}

	public void setControlPipeMemory(Pipe controlPipeMemory) {
		this.controlPipeMemory = controlPipeMemory;
	}

	public Pipe getControlPipeInput() {
		return controlPipeInput;
	}

	public void setControlPipeInput(Pipe controlPipeInput) {
		this.controlPipeInput = controlPipeInput;
	}

	public Pipe getControlPipeOutput() {
		return controlPipeOutput;
	}

	public void setControlPipeOutput(Pipe controlPipeOutput) {
		this.controlPipeOutput = controlPipeOutput;
	}

	public Pipe getControlPipeALU() {
		return controlPipeALU;
	}

	public void setControlPipeALU(Pipe controlPipeALU) {
		this.controlPipeALU = controlPipeALU;
	}

	public PipeConnector getDataPipeConnectorMemory() {
		return dataPipeConnectorMemory;
	}

	public void setDataPipeConnectorMemory(PipeConnector dataPipeConnectorMemory) {
		this.dataPipeConnectorMemory = dataPipeConnectorMemory;
	}

	public PipeConnector getDataPipeConnectorALU() {
		return dataPipeConnectorALU;
	}

	public void setDataPipeConnectorALU(PipeConnector dataPipeConnectorALU) {
		this.dataPipeConnectorALU = dataPipeConnectorALU;
	}

	public int getDataField() {
		return dataField;
	}
}
