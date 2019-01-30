package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import vonNeumannEmulatorKUR2.ALU;
import vonNeumannEmulatorKUR2.ControlUnit;
import vonNeumannEmulatorKUR2.InputUnit;
import vonNeumannEmulatorKUR2.KUR2;
import vonNeumannEmulatorKUR2.MemoryUnit;
import vonNeumannEmulatorKUR2.MicroPrograms.MicroCode;
import vonNeumannEmulatorKUR2.OutputUnit;

public class EmulatorPainter extends Painter{
	private KUR2Display kur2Display;
	
	protected KUR2 kur2;
	
	protected Font font;
	
	protected Rectangle memoryUnitRectangle=new Rectangle(250, 20, 295, 158);
	protected Rectangle aluRectangle=new Rectangle(250, 224, 10+(11*7+8)+10+(11*7+8)+10+(11*7+8)+10, 116);
	protected Rectangle controlUnitRectangle=new Rectangle(250, 360, 295, 220);
	protected Rectangle inputUnitRectangle=new Rectangle(20, aluRectangle.y, 15+11*7+8+15, 100);
	protected Rectangle outputUnitRectangle=new Rectangle(800-20-(15+11*7+8+15), aluRectangle.y, 15+11*7+8+15, 100);
	
	public EmulatorPainter(KUR2Display kur2Display){
		super();
		this.kur2Display=kur2Display;
		
		font=new Font("Monospaced", Font.PLAIN, 12);
	}
	
	@Override
	public void drawScreen(Graphics2D g2d){
		this.g2d=g2d;
		g2d.setFont(font);
		cls();
		
		kur2=kur2Display.getMainController().getKur2();
		if(kur2==null)return;
		
		drawPipeSystem();
		
		drawMemoryUnit(memoryUnitRectangle.x, memoryUnitRectangle.y);
		drawALU(aluRectangle.x, aluRectangle.y);
		drawControlUnitAt(controlUnitRectangle.x, controlUnitRectangle.y);
		drawInputUnit(inputUnitRectangle.x, inputUnitRectangle.y);
		drawOutputUnit(outputUnitRectangle.x, outputUnitRectangle.y);
	}
	
	protected void drawControlUnitAt(int x, int y){
		setColor(Color.WHITE);
		drawRect(x, y, controlUnitRectangle.width, controlUnitRectangle.height);
		ControlUnit controlUnit=kur2.getControlUnit();
		
		drawString("STEUERWERK", x+5, y+15);
		y+=10;
		
//		//decoder
//		int decX=x+190;
//		int decY=y+15;
//		drawRect(decX, decY, 71, 32);
//		drawString(" DECODER", decX+4, decY+13);
//		drawString(controlUnit.getInstructionHigh()+" "+controlUnit.getInstructionLow(), decX+4, decY+28);
//		drawLine(decX+21, decY+17, decX+21, decY+32);
		
		//microprog
		int micX=x+15;
		int micY=y+15;
		int micW=160;
		int micH=180;
		drawRect(micX, micY, micW, micH);
		drawLine(micX+12, micY, micX+12, micY+micH);
		drawString("F", micX+3, micY+12);
		drawString("M", micX+3, micY+12*8+6);
		for(int i=0; i<controlUnit.getInstructionFetchingMiroProgram().size(); i++){
			setColor(controlUnit.isInstructionFetching() && i==controlUnit.getMicroProgramCounter()?Color.RED:Color.WHITE);
			drawString(controlUnit.getInstructionFetchingMiroProgram().get(i), micX+15, micY+12+12*i);
		}
		for(int i=0; i<controlUnit.getCurrentMicroProgram().size(); i++){
			setColor(!controlUnit.isInstructionFetching() && i==controlUnit.getMicroProgramCounter()?Color.RED:Color.WHITE);
			drawString(controlUnit.getCurrentMicroProgram().get(i), micX+15, micY+12*8+6+12*i);
		}
		setColor(Color.WHITE);
		drawLine(micX, micY+12*7+4, micX+micW, micY+12*7+4);
		
		//data
		int datX=x+190;
		int datY=y+15+32+15;
		drawRect(datX, datY, 11*7+8, 32);
		drawString("DATA:", datX+4, datY+13);
		drawString(String.format("% 11d", controlUnit.getDataField()), datX+4, datY+25);
		
		//#
		int pcX=x+190;
		int pcY=y+15;
		drawRect(pcX, pcY, 11*7+8, 32);
		drawString("# (PC):", pcX+4, pcY+13);
		drawString(String.format("% 11d", controlUnit.getProgramCounter()), pcX+4, pcY+25);
		
//		//add
//		int addX=x+221;
//		int addY=y+156+7;
//		drawRect(addX, addY, 64, 32);
//		drawString("ADD:", addX+4, addY+13);
//		drawString(controlUnit.getAddressField(), addX+4, addY+25);
	}
	
	protected void drawMemoryUnit(int x, int y){
		MemoryUnit memoryUnit=kur2.getMemoryUnit();
		
		setColor(Color.WHITE);
		drawRect(x, y, memoryUnitRectangle.width, memoryUnitRectangle.height);
		drawString("SPEICHERWERK", x+5, y+15);
		y+=10;
		
		//add
		int addX=x+15;
		int addY=y+15;
		drawRect(addX, addY, 11*7+8, 32);
		drawString("ADD:", addX+4, addY+13);
		if(memoryUnit.getAddressPipeConnectorControl().getValue()!=null)
			drawString(String.format("% 11d", (int)memoryUnit.getAddressPipeConnectorControl().getValue()), addX+4, addY+25);
		
//		//inst
//		int insX=x+15;
//		int insY=y+101;
//		drawRect(insX, insY, 92, 32);
//		drawString("INSTRUCTION:", insX+4, insY+13);
//		drawString(memoryUnit.getInstructionField(), insX+4, insY+25);
		
		//memory
		int memX=x+131;
		int memY=y+5;
		int memLength=memoryUnit.getMemory().size();
		int adress=memoryUnit.getCurrentAddress();
		drawRect(memX, memY, 19*7+16, 7*16+16);
		drawLine(memX, memY+16, memX+19*7+16, memY+16);
		drawString("ADRESSE     INHALT  ", memX+4, memY+12);
		drawLine(memX+8*7+8, memY, memX+8*7+8, memY+7*16+16);
		for(int i=0; i<7; i++){
			int memAdd=adress-3+i;
			if(memAdd<0 || memAdd>=memLength)continue;
			drawString(String.format("% 8d", memAdd)+" "+String.format("% 11d", memoryUnit.getMemory().get(memAdd)), memX+4, memY+i*16+16+13);
		}
//		setColor(memoryUnit.getInstructionField().equals(MicroCode.memWrite)?Color.RED:(memoryUnit.getInstructionField().equals(MicroCode.memRead)?Color.BLUE:Color.WHITE));
		drawRect(memX, memY+3*16+16, 19*7+16, 16);
		drawRect(memX-1, memY+3*16+16-1, 19*7+16+2, 16+2);
		drawRect(memX-2, memY+3*16+16-2, 19*7+16+4, 16+4);
		setColor(Color.WHITE);
	}
	
	protected void drawALU(int x, int y){
		ALU alu=kur2.getAlu();
		int width=aluRectangle.width;
		int height=aluRectangle.height;
		drawRect(x, y, width, height);
		drawString("RECHENWERK", x+5, y+15);
		y+=10;
		
//		//inst
//		int insX=x+15;
//		int insY=y+109;
//		drawRect(insX, insY, 92, 32);
//		drawString("INSTRUCTION:", insX+4, insY+13);
//		drawString(alu.getInstruction(), insX+4, insY+25);
		
		//accu
		int accX=x+10;
		int accY=y+15;
		drawRect(accX, accY, 11*7+8, 32);
		drawString("ACC:", accX+4, accY+13);
		drawString(String.format("% 11d", alu.getAccumulator()), accX+4, accY+25);
		
		//operand
		int opdX=x+10+(11*7+8)+10+(11*7+8)+10;
		int opdY=y+15;
		drawRect(opdX, opdY, 11*7+8, 32);
		drawString("OPERAND:", opdX+4, opdY+13);
		drawString(String.format("% 11d", alu.getOperand()), opdX+4, opdY+25);
		
		//alu
		int aluX=x+10+(11*7+8)+10;
		int aluY=y+62;
		drawRect(aluX, aluY, 11*7+8, 32);
		String s="   ";
		if(alu.getControlPipeConnectorControl().getValue()!=null)
			switch((String)alu.getControlPipeConnectorControl().getValue()){
			case MicroCode.aluAdd:
				s+="+";
				break;
			case MicroCode.aluSub:
				s+="-";
				break;
			case MicroCode.aluDiv:
				s+=":";
				break;
			case MicroCode.aluMod:
				s+="%";
				break;
			case MicroCode.aluMul:
				s+="*";
				break;
			case MicroCode.aluPush:
				s+="<-";
				break;
			}
		drawString(s, aluX+4, aluY+19);
	}
	
	protected void drawInputUnit(int x, int y){
		InputUnit inputUnit=kur2.getInputUnit();
		
		int inWidth=inputUnitRectangle.width;
		int inHeight=inputUnitRectangle.height;
		
		setColor(Color.WHITE);
		drawRect(x, y, inWidth, inHeight);
		drawString("EINGABEWERK", x+5, y+15);
		y+=10;
		
		//add
		int addX=x+15;
		int addY=y+15;
		drawRect(addX, addY, 11*7+8, 32);
		drawString("ADD:", addX+4, addY+13);
		if(inputUnit.getAddressPipeConnectorControl().getValue()!=null)
			drawString(String.format("% 11d", (int)inputUnit.getAddressPipeConnectorControl().getValue()), addX+4, addY+25);
	}
	
	protected void drawOutputUnit(int x, int y){
		OutputUnit outputUnit=kur2.getOutputUnit();
		
		int outWidth=outputUnitRectangle.width;
		int outHeight=outputUnitRectangle.height;
		
		setColor(Color.WHITE);
		drawRect(x, y, outWidth, outHeight);
		drawString("AUSGABEWERK", x+5, y+15);
		y+=10;
		
		//add
		int addX=x+15;
		int addY=y+15;
		drawRect(addX, addY, 11*7+8, 32);
		drawString("ADD:", addX+4, addY+13);
		if(outputUnit.getAddressPipeConnectorControl().getValue()!=null)
			drawString(String.format("% 11d", (int)outputUnit.getAddressPipeConnectorControl().getValue()), addX+4, addY+25);
	}
	
//	protected void drawIOUnit(int x, int y){
//		IOUnit ioUnit=emulator.getIoUnit();
//		drawRect(x, y, 122, 119);
//		drawString("EIN-/AUSGABEWERK", x+5, y+15);
//		y+=10;
//		
//		//inst
//		int insX=x+15;
//		int insY=y+15;
//		drawRect(insX, insY, 92, 32);
//		drawString("INSTRUCTION:", insX+4, insY+13);
//		drawString(ioUnit.getInstruction(), insX+4, insY+25);
//		
//		//data
//		int datX=x+15;
//		int datY=y+62;
//		drawRect(datX, datY, 64, 32);
//		drawString("DATA:", datX+4, datY+13);
//		if(ioUnit.getDataField()!=null)
//			drawString(ioUnit.getDataField(), datX+4, datY+25);
//	}
	
	protected void drawPipeSystem(){
		//control pipes
		//c->alu
		setColor(kur2.getControlUnit().getControlPipeALU().getValue()==null?Color.WHITE:Color.RED);
		drawLine(controlUnitRectangle.x, controlUnitRectangle.y+30, controlUnitRectangle.x-30, controlUnitRectangle.y+30);
		drawLine(controlUnitRectangle.x-30, controlUnitRectangle.y+30, controlUnitRectangle.x-30, aluRectangle.y+aluRectangle.height-30);
		drawLine(controlUnitRectangle.x, aluRectangle.y+aluRectangle.height-30, controlUnitRectangle.x-30, aluRectangle.y+aluRectangle.height-30);
		
		//c->mem
		setColor(kur2.getControlUnit().getControlPipeMemory().getValue()==null?Color.WHITE:Color.RED);
		drawLine(controlUnitRectangle.x, controlUnitRectangle.y+45, controlUnitRectangle.x-45, controlUnitRectangle.y+45);
		drawLine(controlUnitRectangle.x-45, controlUnitRectangle.y+45, controlUnitRectangle.x-45, memoryUnitRectangle.y+memoryUnitRectangle.height-30);
		drawLine(controlUnitRectangle.x, memoryUnitRectangle.y+memoryUnitRectangle.height-30, controlUnitRectangle.x-45, memoryUnitRectangle.y+memoryUnitRectangle.height-30);
		
		//c->out
		setColor(kur2.getControlUnit().getControlPipeOutput().getValue()==null?Color.WHITE:Color.RED);
		drawLine(controlUnitRectangle.x+controlUnitRectangle.width, controlUnitRectangle.y+60, controlUnitRectangle.x+controlUnitRectangle.width+60, controlUnitRectangle.y+60);
		drawLine(controlUnitRectangle.x+controlUnitRectangle.width+60, controlUnitRectangle.y+60, controlUnitRectangle.x+controlUnitRectangle.width+60, outputUnitRectangle.y+45);
		drawLine(controlUnitRectangle.x+controlUnitRectangle.width+60, outputUnitRectangle.y+45, outputUnitRectangle.x, outputUnitRectangle.y+45);
		
		//c->inp
		setColor(kur2.getControlUnit().getControlPipeInput().getValue()==null?Color.WHITE:Color.RED);
		drawLine(controlUnitRectangle.x, controlUnitRectangle.y+90, controlUnitRectangle.x-90, controlUnitRectangle.y+90);
		drawLine(controlUnitRectangle.x-90, controlUnitRectangle.y+90, controlUnitRectangle.x-90, inputUnitRectangle.y+60);
		drawLine(inputUnitRectangle.x+inputUnitRectangle.width, inputUnitRectangle.y+60, controlUnitRectangle.x-90, inputUnitRectangle.y+60);
		
		
		//adress pipes
		//c->mem
		setColor(kur2.getControlUnit().getAddressPipeMemory().getValue()==null?Color.LIGHT_GRAY:Color.GREEN);
		drawLine(controlUnitRectangle.x, controlUnitRectangle.y+60, controlUnitRectangle.x-60, controlUnitRectangle.y+60);
		drawLine(controlUnitRectangle.x-60, controlUnitRectangle.y+60, controlUnitRectangle.x-60, memoryUnitRectangle.y+memoryUnitRectangle.height-45);
		drawLine(controlUnitRectangle.x, memoryUnitRectangle.y+memoryUnitRectangle.height-45, controlUnitRectangle.x-60, memoryUnitRectangle.y+memoryUnitRectangle.height-45);
		
		//c->inp
		setColor(kur2.getControlUnit().getAddressPipeInput().getValue()==null?Color.LIGHT_GRAY:Color.GREEN);
		drawLine(controlUnitRectangle.x, controlUnitRectangle.y+75, controlUnitRectangle.x-75, controlUnitRectangle.y+75);
		drawLine(controlUnitRectangle.x-75, controlUnitRectangle.y+75, controlUnitRectangle.x-75, aluRectangle.y+45);
		drawLine(inputUnitRectangle.x+inputUnitRectangle.width, inputUnitRectangle.y+45, controlUnitRectangle.x-75, aluRectangle.y+45);
		
		//c->out
		setColor(kur2.getControlUnit().getAddressPipeOutput().getValue()==null?Color.LIGHT_GRAY:Color.GREEN);
		drawLine(controlUnitRectangle.x+controlUnitRectangle.width, controlUnitRectangle.y+75, controlUnitRectangle.x+controlUnitRectangle.width+75, controlUnitRectangle.y+75);
		drawLine(controlUnitRectangle.x+controlUnitRectangle.width+75, controlUnitRectangle.y+75, controlUnitRectangle.x+controlUnitRectangle.width+75, outputUnitRectangle.y+60);
		drawLine(controlUnitRectangle.x+controlUnitRectangle.width+75, outputUnitRectangle.y+60, outputUnitRectangle.x, outputUnitRectangle.y+60);
		
		
		//data pipes
		//in->alu
		setColor(kur2.getInputUnit().getDataPipeALU().getValue()==null?Color.DARK_GRAY:Color.BLUE);
		drawLine(inputUnitRectangle.x+inputUnitRectangle.width, inputUnitRectangle.y+30, aluRectangle.x, aluRectangle.y+30);
		
		//alu->mem
		setColor(kur2.getAlu().getDataPipeMemory().getValue()==null?Color.DARK_GRAY:Color.BLUE);
		drawLine(aluRectangle.x+aluRectangle.width-30, aluRectangle.y, memoryUnitRectangle.x+memoryUnitRectangle.width-30, memoryUnitRectangle.y+memoryUnitRectangle.height);
		
		//mem->alu
		setColor(kur2.getMemoryUnit().getDataPipeALU().getValue()==null?Color.DARK_GRAY:Color.BLUE);
		drawLine(aluRectangle.x+30, aluRectangle.y, memoryUnitRectangle.x+30, memoryUnitRectangle.y+memoryUnitRectangle.height);
		
		//mem->control
		setColor(kur2.getMemoryUnit().getDataPipeControl().getValue()==null?Color.DARK_GRAY:Color.BLUE);
		drawLine(memoryUnitRectangle.x+memoryUnitRectangle.width, memoryUnitRectangle.y+memoryUnitRectangle.height-30, memoryUnitRectangle.x+memoryUnitRectangle.width+45, memoryUnitRectangle.y+memoryUnitRectangle.height-30);
		drawLine(memoryUnitRectangle.x+memoryUnitRectangle.width+45, memoryUnitRectangle.y+memoryUnitRectangle.height-30, controlUnitRectangle.x+controlUnitRectangle.width+45, controlUnitRectangle.y+45);
		drawLine(controlUnitRectangle.x+controlUnitRectangle.width, controlUnitRectangle.y+45, controlUnitRectangle.x+controlUnitRectangle.width+45, controlUnitRectangle.y+45);
		
		//alu->control
		setColor(kur2.getAlu().getDataPipeControl().getValue()==null?Color.DARK_GRAY:Color.BLUE);
		drawLine(aluRectangle.x+aluRectangle.width, aluRectangle.y+45, aluRectangle.x+aluRectangle.width+30, aluRectangle.y+45);
		drawLine(aluRectangle.x+aluRectangle.width+30, aluRectangle.y+45, controlUnitRectangle.x+controlUnitRectangle.width+30, controlUnitRectangle.y+30);
		drawLine(controlUnitRectangle.x+controlUnitRectangle.width, controlUnitRectangle.y+30, controlUnitRectangle.x+controlUnitRectangle.width+30, controlUnitRectangle.y+30);
		
		//alu->out
		setColor(kur2.getAlu().getDataPipeOutput().getValue()==null?Color.DARK_GRAY:Color.BLUE);
		drawLine(aluRectangle.x+aluRectangle.width, aluRectangle.y+30, outputUnitRectangle.x, outputUnitRectangle.y+30);
	}
}
