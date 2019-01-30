package vonNeumannEmulatorKUR2;

import vonNeumannEmulatorKUR2.MicroPrograms.MicroCode;

public class ALU {
	private int accumulator;
	private int operand;
	
	private Pipe dataPipeMemory, dataPipeControl, dataPipeOutput;
	private PipeConnector dataPipeConnectorInput, dataPipeConnectorMemory, controlPipeConnectorControl;
	
	private boolean ready;
	
	public ALU(){
		accumulator=0;
		operand=0;
		
		ready=false;
	}
	
	public void simulateCycle(){
		if(!ready){
			ready=true;
			
			operand=dataPipeConnectorInput.getValue()!=null?(int)dataPipeConnectorInput.getValue():dataPipeConnectorMemory.getValue()!=null?(int)dataPipeConnectorMemory.getValue():operand;
			
			if(controlPipeConnectorControl.getValue()==null){
				if(dataPipeControl.getValue()!=null){
					dataPipeControl.setValue(null);
				}else if(dataPipeMemory.getValue()!=null){
					dataPipeMemory.setValue(null);
				}else if(dataPipeOutput.getValue()!=null){
					dataPipeOutput.setValue(null);
				}
				return;
			}
			
			switch((String)controlPipeConnectorControl.getValue()){
			case MicroCode.aluWriteToMem:
				dataPipeMemory.setValue(accumulator);
				break;
			case MicroCode.aluWriteToOutput:
				dataPipeOutput.setValue(accumulator);
				break;
			case MicroCode.aluWriteToControl:
				dataPipeControl.setValue(accumulator);
				break;
			case MicroCode.aluReset:
				accumulator=0;
				break;
			case MicroCode.aluPush:
				accumulator=operand;
				break;
			case MicroCode.aluAdd:
				accumulator+=operand;
				break;
			case MicroCode.aluSub:
				accumulator-=operand;
				break;
			case MicroCode.aluMul:
				accumulator*=operand;
				break;
			case MicroCode.aluDiv:
				accumulator/=operand;
				break;
			case MicroCode.aluMod:
				accumulator%=operand;
				break;
			}
		}
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public Pipe getDataPipeMemory() {
		return dataPipeMemory;
	}

	public void setDataPipeMemory(Pipe dataPipeMemory) {
		this.dataPipeMemory = dataPipeMemory;
	}

	public Pipe getDataPipeControl() {
		return dataPipeControl;
	}

	public void setDataPipeControl(Pipe dataPipeControl) {
		this.dataPipeControl = dataPipeControl;
	}

	public PipeConnector getDataPipeConnectorInput() {
		return dataPipeConnectorInput;
	}

	public void setDataPipeConnectorInput(PipeConnector dataPipeConnectorInput) {
		this.dataPipeConnectorInput = dataPipeConnectorInput;
	}

	public PipeConnector getDataPipeConnectorMemory() {
		return dataPipeConnectorMemory;
	}

	public void setDataPipeConnectorMemory(PipeConnector dataPipeConnectorMemory) {
		this.dataPipeConnectorMemory = dataPipeConnectorMemory;
	}

	public PipeConnector getControlPipeConnectorControl() {
		return controlPipeConnectorControl;
	}

	public void setControlPipeConnectorControl(PipeConnector controlPipeConnectorControl) {
		this.controlPipeConnectorControl = controlPipeConnectorControl;
	}

	public Pipe getDataPipeOutput() {
		return dataPipeOutput;
	}

	public void setDataPipeOutput(Pipe dataPipeOutput) {
		this.dataPipeOutput = dataPipeOutput;
	}

	public int getAccumulator() {
		return accumulator;
	}

	public int getOperand() {
		return operand;
	}
}
