package vonNeumannEmulatorKUR2;

public class PipeConnector extends Pipe {
	private Object connectedObject;
	
	public PipeConnector(int cycleTarget, Object connectedObject){
		super(cycleTarget);
		
		this.connectedObject=connectedObject;
	}
	
	@Override
	public void simulateCycle(){
		if(!isReady()){
			super.simulateCycle();
			
			if(isReady()){
				if(connectedObject instanceof ControlUnit){
					((ControlUnit)connectedObject).setReady(false);
				}else if(connectedObject instanceof ALU){
					((ALU)connectedObject).setReady(false);
				}else if(connectedObject instanceof MemoryUnit){
					((MemoryUnit)connectedObject).setReady(false, this);
				}else if(connectedObject instanceof InputUnit){
					((InputUnit)connectedObject).setReady(false);
				}else if(connectedObject instanceof OutputUnit){
					((OutputUnit)connectedObject).setReady(false);
				}
			}
		}
	}
}
