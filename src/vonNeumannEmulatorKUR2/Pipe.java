package vonNeumannEmulatorKUR2;

public class Pipe {
	protected Object value;
	private Pipe nextPipeSegment;
	
	protected int cyclesElapsed,cycleTarget;
	
	protected boolean ready;
	
	public Pipe(int cycleTarget){
		ready=true;
		this.cycleTarget=cycleTarget;
	}
	
	public Pipe(Pipe nextPipeSegment, int cycleTarget){
		this(cycleTarget);
		this.nextPipeSegment=nextPipeSegment;
	}
	
	public void simulateCycle(){
		if(ready){
			if(nextPipeSegment!=null)
				nextPipeSegment.setValue(value);
			return;
		}
		
		if(cyclesElapsed==cycleTarget){
			ready=true;
			if(nextPipeSegment!=null)
				nextPipeSegment.setValue(value);
		}else{
			cyclesElapsed++;
		}
	}

	public Object getValue() {
		return value;
	}

	public boolean isReady() {
		if(nextPipeSegment!=null)
			return ready && nextPipeSegment.isReady();
		
		return ready;
	}

	public void setValue(Object value) {
		this.ready=false;
		this.cyclesElapsed=0;
		this.value = value;
	}
}
