package vonNeumannEmulatorKUR2;

import javax.swing.JOptionPane;

import vonNeumannEmulatorKUR2.MicroPrograms.MicroCode;

public class InputUnit {
	private boolean ready;
	
	private Pipe dataPipeALU;
	private PipeConnector addressPipeConnectorControl, controlPipeConnectorControl;
	
	public InputUnit(){
		ready=false;
	}
	
	public void simulateCycle(){
		if(!ready){
			ready=true;
			
			if(controlPipeConnectorControl.getValue()==null){
				dataPipeALU.setValue(null);
			}else if(controlPipeConnectorControl.getValue()==MicroCode.inWriteToALU){
				String input=null;
				int inputValue=-1;
				boolean inputAccepted=false;
				while(!inputAccepted){
					input=JOptionPane.showInputDialog(null, input==null?"Eingabe wird erbeten:":"\""+input+"\" ist kein gültiger Eingabewert! Bitte gültigen Wert eingeben:", "Dateneingabe auf Port "+(int)addressPipeConnectorControl.getValue(), JOptionPane.OK_CANCEL_OPTION);
					if(input==null)
						break;
					try{
						inputValue=Integer.parseInt(input);
						inputAccepted=true;
					}catch(NumberFormatException nfe){
						inputAccepted=false;
					}
				}
//				if(input!=null){
				dataPipeALU.setValue(inputValue);
//				}
			}else if(controlPipeConnectorControl.getValue()==MicroCode.inStop){
				dataPipeALU.setValue(null);
			}
		}
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public Pipe getDataPipeALU() {
		return dataPipeALU;
	}

	public void setDataPipeALU(Pipe dataPipeALU) {
		this.dataPipeALU = dataPipeALU;
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
