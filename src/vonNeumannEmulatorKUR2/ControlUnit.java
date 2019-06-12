package vonNeumannEmulatorKUR2;

import java.util.ArrayList;

import vonNeumannEmulatorKUR2.MicroPrograms.InstructionSet;

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
		String controlToken=microInstruction;
		if(controlToken==null)
			controlToken="";
		if(controlToken.equals(
		//internal
		MicroPrograms.intProgCounterToMemAdd)) {
			addressPipeMemory.setValue(programCounter);
		}else if(controlToken.equals(MicroPrograms.intProgCounterIncrease)) {
			programCounter++;
		}else if(controlToken.equals(MicroPrograms.intDecode)){
			currentMicroProgram=MicroPrograms.getMicroProgramForInstruction(dataField);
		}else if(controlToken.equals(MicroPrograms.intHalt)){
			halted=true;
			if(printMemDumpOnHalt){
				kur2.printMemoryDump();
				kur2.saveMemoryDump();
			}
			if(systemExitOnHalt)
				System.exit(0);
		}else if(controlToken.equals(MicroPrograms.intDataToProgCounter)){
			programCounter=dataField;
		}else if(controlToken.equals(MicroPrograms.intDataEqualsNull)){
			if(dataField!=0)
				interruptMicroProg=true;
		}else if(controlToken.equals(MicroPrograms.intDataNotNull)){
			if(dataField==0)
				interruptMicroProg=true;
		}else if(controlToken.equals(MicroPrograms.intDataGreaterNull)){
			if(dataField<=0)
				interruptMicroProg=true;
		}else if(controlToken.equals(MicroPrograms.intDataLessNull)){
			if(dataField>=0)
				interruptMicroProg=true;
		}else if(controlToken.equals(MicroPrograms.intDataToMemAdd)){
			addressPipeMemory.setValue(dataField);
		}else if(controlToken.equals(MicroPrograms.intDataToInputAdd)){
			addressPipeInput.setValue(dataField);
		}else if(controlToken.equals(MicroPrograms.intDataToOutputAdd)){
			addressPipeOutput.setValue(dataField);
		
		//input
		}else if(controlToken.equals(MicroPrograms.inWriteToALU)){
			controlPipeInput.setValue(microInstruction);
		}else if(controlToken.equals(MicroPrograms.inStop)){
			controlPipeInput.setValue(null);
		
		//output
		}else if(controlToken.equals(MicroPrograms.outReadFromALU)){
			controlPipeOutput.setValue(microInstruction);
		}else if(controlToken.equals(MicroPrograms.outStop)){
			controlPipeOutput.setValue(null);
		
		//memory
		}else if(controlToken.equals(MicroPrograms.memLoadToControl)
				|| controlToken.equals(MicroPrograms.memLoadToALU)
				|| controlToken.equals(MicroPrograms.memStoreFromAlu)) {
			controlPipeMemory.setValue(microInstruction);
		}else if(controlToken.equals(MicroPrograms.memStop)){
			controlPipeMemory.setValue(null);
		
		//alu
		}else if(controlToken.equals(MicroPrograms.aluWriteToControl)
				|| controlToken.equals(MicroPrograms.aluWriteToMem)
				|| controlToken.equals(MicroPrograms.aluWriteToOutput)
				|| controlToken.equals(MicroPrograms.aluReset)
				|| controlToken.equals(MicroPrograms.aluPush)
				|| controlToken.equals(MicroPrograms.aluAdd)
				|| controlToken.equals(MicroPrograms.aluSub)
				|| controlToken.equals(MicroPrograms.aluMul)
				|| controlToken.equals(MicroPrograms.aluDiv)
				|| controlToken.equals(MicroPrograms.aluMod)){
			controlPipeALU.setValue(microInstruction);
		}else if(controlToken.equals(MicroPrograms.aluStop)){
			controlPipeALU.setValue(null);
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
