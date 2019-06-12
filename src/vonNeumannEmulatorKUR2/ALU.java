package vonNeumannEmulatorKUR2;

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
			
			String controlToken=(String)getControlPipeConnectorControl().getValue();
			if(controlToken==null)
				controlToken="";
			if(controlToken.equals(MicroPrograms.aluWriteToMem)) {
				dataPipeMemory.setValue(accumulator);
			}else if(controlToken.equals(MicroPrograms.aluWriteToOutput)) {
				dataPipeOutput.setValue(accumulator);
			}else if(controlToken.equals(MicroPrograms.aluWriteToControl)) {
				dataPipeControl.setValue(accumulator);
			}else if(controlToken.equals(MicroPrograms.aluReset)) {
				accumulator=0;
			}else if(controlToken.equals(MicroPrograms.aluPush)) {
				accumulator=operand;
			}else if(controlToken.equals(MicroPrograms.aluAdd)) {
				accumulator+=operand;
			}else if(controlToken.equals(MicroPrograms.aluSub)) {
				accumulator-=operand;
			}else if(controlToken.equals(MicroPrograms.aluMul)) {
				accumulator*=operand;
			}else if(controlToken.equals(MicroPrograms.aluDiv)) {
				accumulator/=operand;
			}else if(controlToken.equals(MicroPrograms.aluMod)) {
				accumulator%=operand;
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
