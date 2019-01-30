package vonNeumannEmulatorKUR2;

import javax.swing.JOptionPane;

import vonNeumannEmulatorKUR2.MicroPrograms.MicroCode;

public class OutputUnit {
	private PipeConnector dataPipeConnectorALU, addressPipeConnectorControl, controlPipeConnectorControl;
	
	private boolean ready;
	
	public OutputUnit(){
		ready=false;
	}
	
	public void simulateCycle(){
		if(!ready){
			ready=true;
			
			if(controlPipeConnectorControl.getValue()==MicroCode.outReadFromALU){
				JOptionPane.showMessageDialog(null, "Ausgegebener Wert: "+(int)dataPipeConnectorALU.getValue(), "Ausgabe auf Port "+(int)addressPipeConnectorControl.getValue(), JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
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
}
