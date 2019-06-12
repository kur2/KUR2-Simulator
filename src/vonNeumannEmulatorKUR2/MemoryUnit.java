package vonNeumannEmulatorKUR2;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MemoryUnit {
	protected Pipe dataPipeALU, dataPipeControl;
	protected PipeConnector dataPipeConnectorALU, addressPipeConnectorControl, controlPipeConnectorControl;
	
	protected ArrayList<Integer> memory;
	
	protected boolean ready;
	
	public MemoryUnit(int memSize){
		memory=new ArrayList<Integer>();
		
		setMemorySizeAndClear(memSize);
		
		ready=true;
	}
	
	public void simulateCycle(){
		if(!ready){
			ready=true;
			
			if(controlPipeConnectorControl.getValue()==null){
				if(dataPipeControl.getValue()!=null)
					dataPipeControl.setValue(null);
				if(dataPipeALU.getValue()!=null)
					dataPipeALU.setValue(null);
				return;
			}
			
			String controlToken=(String)controlPipeConnectorControl.getValue();
			if(controlToken==null)
				controlToken="";
			if(controlToken.equals(MicroPrograms.memLoadToControl)) {
				dataPipeControl.setValue(memory.get((int)addressPipeConnectorControl.getValue()));
			}else if(controlToken.equals(MicroPrograms.memLoadToALU)){
				dataPipeALU.setValue(memory.get((int)addressPipeConnectorControl.getValue()));
			}else if(controlToken.equals(MicroPrograms.memStoreFromAlu)) {
				memory.set((int)addressPipeConnectorControl.getValue(), (int)dataPipeConnectorALU.getValue());
			}
		}
	}
	
	public void setMemorySizeAndClear(int size){
		memory.clear();
		
		for(int i=0; i<size; i++)
			memory.add(0);
	}
	
	public void loadProgram(ArrayList<Integer> program){
		for(int i=0; i<program.size(); i++)
			memory.set(i, program.get(i));
	}
	
	public void clearMemory(){
		setMemorySizeAndClear(memory.size());
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready, Object source) {
		if(source.equals(addressPipeConnectorControl)){
			int add=(Integer)addressPipeConnectorControl.getValue();
			if(add>=memory.size()){
				JOptionPane.showMessageDialog(null, "Speicherzugriff fehlgeschlagen: Es gibt keine Speicherzelle mit Adresse "+add, "Fehler", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}
			return;
		}
		this.ready = ready;
	}

	public ArrayList<Integer> getMemory() {
		return memory;
	}

	public void setMemory(ArrayList<Integer> memory) {
		this.memory = memory;
	}

	public Pipe getDataPipeALU() {
		return dataPipeALU;
	}

	public void setDataPipeALU(Pipe dataPipeALU) {
		this.dataPipeALU = dataPipeALU;
	}

	public Pipe getDataPipeControl() {
		return dataPipeControl;
	}

	public void setDataPipeControl(Pipe dataPipeControl) {
		this.dataPipeControl = dataPipeControl;
	}

	public PipeConnector getDataPipeConnectorALU() {
		return dataPipeConnectorALU;
	}

	public void setDataPipeConnectorALU(PipeConnector dataPipeConnectorALU) {
		this.dataPipeConnectorALU = dataPipeConnectorALU;
	}

	public PipeConnector getAddressPipeConnectorControl() {
		return addressPipeConnectorControl;
	}

	public void setAddressPipeConnectorControl(PipeConnector addressPipeConnectorControl) {
		this.addressPipeConnectorControl = addressPipeConnectorControl;
	}

	public PipeConnector getControlPipeConnectorControl() {
		return controlPipeConnectorControl;
	}

	public void setControlPipeConnectorControl(PipeConnector controlPipeConnectorControl) {
		this.controlPipeConnectorControl = controlPipeConnectorControl;
	}
	
	public int getCurrentAddress(){
		if(addressPipeConnectorControl.getValue()==null)
			return 0;
		return (int)addressPipeConnectorControl.getValue();
	}
}
