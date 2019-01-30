package vonNeumannEmulatorKUR2;

import java.util.ArrayList;

public class PipeSystem {
	private ArrayList<Pipe> pipes;
	
	public PipeSystem(ControlUnit controlUnit, ALU alu, MemoryUnit memoryUnit, InputUnit inputUnit, OutputUnit outputUnit){
		this(1, controlUnit, alu, memoryUnit, inputUnit, outputUnit);
	}
	
	public PipeSystem(int cycleTarget, ControlUnit controlUnit, ALU alu, MemoryUnit memoryUnit, InputUnit inputUnit, OutputUnit outputUnit){
		pipes=new ArrayList<Pipe>();
		
		//control
		{
			//input address
			PipeConnector cai=new PipeConnector(0, inputUnit);
			inputUnit.setAddressPipeConnectorControl(cai);
			controlUnit.setAddressPipeInput(cai);
			pipes.add(cai);
			
			//input control
			PipeConnector cci=new PipeConnector(0, inputUnit);
			inputUnit.setControlPipeConnectorControl(cci);
			controlUnit.setControlPipeInput(cci);
			pipes.add(cci);
			
			//memory address
			PipeConnector cam=new PipeConnector(0, memoryUnit);
			memoryUnit.setAddressPipeConnectorControl(cam);
			controlUnit.setAddressPipeMemory(cam);
			pipes.add(cam);
			
			//memory control
			PipeConnector ccm=new PipeConnector(0, memoryUnit);
			memoryUnit.setControlPipeConnectorControl(ccm);
			controlUnit.setControlPipeMemory(ccm);
			pipes.add(ccm);
			
			//output address
			PipeConnector cao=new PipeConnector(0, outputUnit);
			outputUnit.setAddressPipeConnectorControl(cao);
			controlUnit.setAddressPipeOutput(cao);
			pipes.add(cao);
			
			//output control
			PipeConnector cco=new PipeConnector(0, outputUnit);
			outputUnit.setControlPipeConnectorControl(cco);
			controlUnit.setControlPipeOutput(cco);
			pipes.add(cco);
			
			//alu control
			PipeConnector cca=new PipeConnector(0, alu);
			alu.setControlPipeConnectorControl(cca);
			controlUnit.setControlPipeALU(cca);
			pipes.add(cca);
		}
		
		//alu
		{
			//memory data
			PipeConnector adm=new PipeConnector(0, memoryUnit);
			memoryUnit.setDataPipeConnectorALU(adm);
			alu.setDataPipeMemory(adm);
			pipes.add(adm);
			
			//control data
			PipeConnector adc=new PipeConnector(0, controlUnit);
			controlUnit.setDataPipeConnectorALU(adc);
			alu.setDataPipeControl(adc);
			pipes.add(adc);
			
			//output data
			PipeConnector ado=new PipeConnector(0, outputUnit);
			outputUnit.setDataPipeConnectorALU(ado);
			alu.setDataPipeOutput(ado);
			pipes.add(ado);
		}
		
		//memory
		{
			//alu data
			PipeConnector mda=new PipeConnector(0, alu);
			alu.setDataPipeConnectorMemory(mda);
			memoryUnit.setDataPipeALU(mda);
			pipes.add(mda);
			
			//control data
			PipeConnector mdc=new PipeConnector(0, controlUnit);
			controlUnit.setDataPipeConnectorMemory(mdc);
			memoryUnit.setDataPipeControl(mdc);
			pipes.add(mdc);
		}
		
		//input
		{
			//alu data
			PipeConnector ida=new PipeConnector(0, alu);
			alu.setDataPipeConnectorInput(ida);
			inputUnit.setDataPipeALU(ida);
			pipes.add(ida);
		}
	}
	
	public void simulateCycle(){
		for(Pipe p:pipes)
			p.simulateCycle();
	}
	
	public void addPipe(Pipe p){
		pipes.add(p);
	}
	
	public boolean isReady(){
		for(Pipe p:pipes){
			if(!p.isReady())
				return false;
		}
		
		return true;
	}
}
