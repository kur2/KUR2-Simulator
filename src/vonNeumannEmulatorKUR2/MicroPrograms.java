package vonNeumannEmulatorKUR2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MicroPrograms {
	private static Map<Integer, ArrayList<String>> microProgramMap;
	
	public static ArrayList<String> getMicroProgramForInstruction(int instruction){
		ArrayList<String> microProg=microProgramMap.get(instruction);
		
		if(microProg==null){
			System.out.println("MicroPrograms: Error getting micro program for instruction \""+instruction+"\": Instruction unknown");
			System.exit(-1);
		}
		
		return microProg;
	}
	
	//Microcode
	//internal
	public static String intHalt					="   HALT";
	public static String intProgCounterIncrease		="   # ERHOEHEN";
	public static String intDecode					="   MICROPROG WAEHLEN";
	public static String intDataToProgCounter		="   DATA -> #";
	public static String intDataEqualsNull			="   DATA = 0 ?";
	public static String intDataNotNull				="   DATA =/= 0 ?";
	public static String intDataGreaterNull			="   DATA > 0 ?";
	public static String intDataLessNull			="   DATA < 0 ?";
	public static String intProgCounterToMemAdd		="   # -> SP ADR";
	public static String intDataToMemAdd			="   DATA -> SP ADR";
	public static String intDataToInputAdd			="   DATA -> EIN ADR";
	public static String intDataToOutputAdd			="   DATA -> AUS ADR";
	
	//memory
	public static String memLoadToControl			="SP LADEN -> ST";
	public static String memLoadToALU				="SP LADEN -> RW";
	public static String memStoreFromAlu			="SP SPEICHERN <- RW";
	public static String memStop					="SP STOP";
	
	//in
	public static String inWriteToALU				="EIN EINGABE -> RW";
	public static String inStop						="EIN STOP";
	
	//out
	public static String outReadFromALU				="AUS AUSGABE <- RW";
	public static String outStop					="AUS STOP";
	
	//alu
	public static String aluWriteToMem				="RW ACC -> SP";
	public static String aluWriteToOutput			="RW ACC -> AUS";
	public static String aluWriteToControl			="RW ACC -> ST";
	public static String aluStop					="RW STOP";
	public static String aluReset					="RW ACC = 0";
	public static String aluPush					="RW LADEN";
	public static String aluAdd						="RW PLUS";
	public static String aluSub						="RW MINUS";
	public static String aluMul						="RW MAL";
	public static String aluDiv						="RW GETEILT";
	public static String aluMod						="RW MODULO";
	
	static{
		rebuildMicroPrograms();
	}
	
	public static void rebuildMicroPrograms() {
		microProgramMap=new HashMap<>();
		
		//---------------META--------------
		//instruction fetching microprog
		ArrayList<String> instructionFetchProg=new ArrayList<String>();
		instructionFetchProg.add(intProgCounterToMemAdd);
		instructionFetchProg.add(memLoadToControl);
		instructionFetchProg.add(memStop);
		instructionFetchProg.add(intDecode);
		instructionFetchProg.add(intProgCounterIncrease);
		instructionFetchProg.add(intProgCounterToMemAdd);
		instructionFetchProg.add(intProgCounterIncrease);
		microProgramMap.put(InstructionSet.fetch, instructionFetchProg);
		
		
		//---------------FLOW--------------
		//empty microprog/no op
		ArrayList<String> emptyProg=new ArrayList<String>();
		microProgramMap.put(InstructionSet.noop, emptyProg);
		
		//halt
		ArrayList<String> haltProg=new ArrayList<String>();
		haltProg.add(intHalt);
		microProgramMap.put(InstructionSet.halt, haltProg);
		
		
		//---------------JUMP--------------
		//jump
		ArrayList<String> jumpProg=new ArrayList<String>();
		jumpProg.add(memLoadToControl);
		jumpProg.add(memStop);
		jumpProg.add(intDataToProgCounter);
		microProgramMap.put(InstructionSet.jump, jumpProg);
		
		//conditional jump = 0
		ArrayList<String> cJumpE0Prog=new ArrayList<String>();
		cJumpE0Prog.add(aluWriteToControl);
		cJumpE0Prog.add(aluStop);
		cJumpE0Prog.add(intDataEqualsNull);
		cJumpE0Prog.add(memLoadToControl);
		cJumpE0Prog.add(memStop);
		cJumpE0Prog.add(intDataToProgCounter);
		microProgramMap.put(InstructionSet.jumpE0, cJumpE0Prog);
		
		//conditional jump != 0
		ArrayList<String> cJumpN0Prog=new ArrayList<String>();
		cJumpN0Prog.add(aluWriteToControl);
		cJumpN0Prog.add(aluStop);
		cJumpN0Prog.add(intDataNotNull);
		cJumpN0Prog.add(memLoadToControl);
		cJumpN0Prog.add(memStop);
		cJumpN0Prog.add(intDataToProgCounter);
		microProgramMap.put(InstructionSet.jumpN0, cJumpN0Prog);
		
		//conditional jump > 0
		ArrayList<String> cJumpG0Prog=new ArrayList<String>();
		cJumpG0Prog.add(aluWriteToControl);
		cJumpG0Prog.add(aluStop);
		cJumpG0Prog.add(intDataGreaterNull);
		cJumpG0Prog.add(memLoadToControl);
		cJumpG0Prog.add(memStop);
		cJumpG0Prog.add(intDataToProgCounter);
		microProgramMap.put(InstructionSet.jumpG0, cJumpG0Prog);
		
		//conditional jump < 0
		ArrayList<String> cJumpL0Prog=new ArrayList<String>();
		cJumpL0Prog.add(aluWriteToControl);
		cJumpL0Prog.add(aluStop);
		cJumpL0Prog.add(intDataLessNull);
		cJumpL0Prog.add(memLoadToControl);
		cJumpL0Prog.add(memStop);
		cJumpL0Prog.add(intDataToProgCounter);
		microProgramMap.put(InstructionSet.jumpL0, cJumpL0Prog);
		
		//------------LOAD/STORE-----------
		//write null to acc
		ArrayList<String> nullProg=new ArrayList<String>();
		nullProg.add(aluReset);
		nullProg.add(aluStop);
		microProgramMap.put(InstructionSet.nullAcc, nullProg);

		//load constant value
		ArrayList<String> cLoadProg=new ArrayList<String>();
		cLoadProg.add(memLoadToALU);
		cLoadProg.add(memStop);
		cLoadProg.add(aluPush);
		cLoadProg.add(aluStop);
		microProgramMap.put(InstructionSet.loadC, cLoadProg);
		
		//load value from memory
		ArrayList<String> loadProg=new ArrayList<String>();
		loadProg.add(memLoadToControl);
		loadProg.add(memStop);
		loadProg.add(intDataToMemAdd);
		loadProg.add(memLoadToALU);
		loadProg.add(memStop);
		loadProg.add(aluPush);
		loadProg.add(aluStop);
		microProgramMap.put(InstructionSet.loadA, loadProg);
		
		//store value
		ArrayList<String> storeProg=new ArrayList<String>();
		storeProg.add(memLoadToControl);
		storeProg.add(memStop);
		storeProg.add(intDataToMemAdd);
		storeProg.add(aluWriteToMem);
		storeProg.add(memStoreFromAlu);
		storeProg.add(memStop);
		storeProg.add(aluStop);
		microProgramMap.put(InstructionSet.store, storeProg);
		
		
		//--------------PLUS---------------
		//acc + c
		ArrayList<String> addCProg=new ArrayList<String>();
		addCProg.add(memLoadToALU);
		addCProg.add(memStop);
		addCProg.add(aluAdd);
		addCProg.add(aluStop);
		microProgramMap.put(InstructionSet.addC, addCProg);
		
		//acc + <a>
		ArrayList<String> addAProg=new ArrayList<String>();
		addAProg.add(memLoadToControl);
		addAProg.add(memStop);
		addAProg.add(intDataToMemAdd);
		addAProg.add(memLoadToALU);
		addAProg.add(memStop);
		addAProg.add(aluAdd);
		addAProg.add(aluStop);
		microProgramMap.put(InstructionSet.addA, addAProg);
		
		
		//--------------MINUS--------------
		//acc - c
		ArrayList<String> subCProg=new ArrayList<String>();
		subCProg.add(memLoadToALU);
		subCProg.add(memStop);
		subCProg.add(aluSub);
		subCProg.add(aluStop);
		microProgramMap.put(InstructionSet.subC, subCProg);
		
		//acc - <a>
		ArrayList<String> subAProg=new ArrayList<String>();
		subAProg.add(memLoadToControl);
		subAProg.add(memStop);
		subAProg.add(intDataToMemAdd);
		subAProg.add(memLoadToALU);
		subAProg.add(memStop);
		subAProg.add(aluSub);
		subAProg.add(aluStop);
		microProgramMap.put(InstructionSet.subA, subAProg);
		
		
		//--------------MULT---------------
		//acc * c
		ArrayList<String> multCProg=new ArrayList<String>();
		multCProg.add(memLoadToALU);
		multCProg.add(memStop);
		multCProg.add(aluMul);
		multCProg.add(aluStop);
		microProgramMap.put(InstructionSet.multC, multCProg);
		
		//acc * <a>
		ArrayList<String> multAProg=new ArrayList<String>();
		multAProg.add(memLoadToControl);
		multAProg.add(memStop);
		multAProg.add(intDataToMemAdd);
		multAProg.add(memLoadToALU);
		multAProg.add(memStop);
		multAProg.add(aluMul);
		multAProg.add(aluStop);
		microProgramMap.put(InstructionSet.multA, multAProg);
		
		
		//--------------DIV----------------
		//acc / c
		ArrayList<String> divCProg=new ArrayList<String>();
		divCProg.add(memLoadToALU);
		divCProg.add(memStop);
		divCProg.add(aluDiv);
		divCProg.add(aluStop);
		microProgramMap.put(InstructionSet.divC, divCProg);
		
		//acc / <a>
		ArrayList<String> divAProg=new ArrayList<String>();
		divAProg.add(memLoadToControl);
		divAProg.add(memStop);
		divAProg.add(intDataToMemAdd);
		divAProg.add(memLoadToALU);
		divAProg.add(memStop);
		divAProg.add(aluDiv);
		divAProg.add(aluStop);
		microProgramMap.put(InstructionSet.divA, divAProg);
		
		
		//--------------MOD----------------
		//acc % c
		ArrayList<String> modCProg=new ArrayList<String>();
		modCProg.add(memLoadToALU);
		modCProg.add(memStop);
		modCProg.add(aluMod);
		modCProg.add(aluStop);
		microProgramMap.put(InstructionSet.modC, modCProg);
		
		//acc % <a>
		ArrayList<String> modAProg=new ArrayList<String>();
		modAProg.add(memLoadToControl);
		modAProg.add(memStop);
		modAProg.add(intDataToMemAdd);
		modAProg.add(memLoadToALU);
		modAProg.add(memStop);
		modAProg.add(aluMod);
		modAProg.add(aluStop);
		microProgramMap.put(InstructionSet.modA, modAProg);
		
		
		//--------------I/O----------------
		//input value
		ArrayList<String> inputProg=new ArrayList<String>();
		inputProg.add(memLoadToControl);
		inputProg.add(memStop);
		inputProg.add(intDataToInputAdd);
		inputProg.add(inWriteToALU);
		inputProg.add(aluPush);
		inputProg.add(aluStop);
		inputProg.add(inStop);
		microProgramMap.put(InstructionSet.input, inputProg);
		
		//output value
		ArrayList<String> outputProg=new ArrayList<String>();
		outputProg.add(memLoadToControl);
		outputProg.add(memStop);
		outputProg.add(intDataToOutputAdd);
		outputProg.add(aluWriteToOutput);
		outputProg.add(outReadFromALU);
		outputProg.add(outStop);
		outputProg.add(aluStop);
		microProgramMap.put(InstructionSet.output, outputProg);
	}
	
	public class InstructionSet{
		//fetch
		public static final int fetch=-1;
		
		//flow
		public static final int noop=00;
		public static final int halt=01;
		
		//jump
		public static final int jump=10;
		public static final int jumpE0=11;
		public static final int jumpN0=12;
		public static final int jumpG0=13;
		public static final int jumpL0=14;
		
		//load/store
		public static final int nullAcc=20;
		public static final int loadC=21;
		public static final int loadA=22;
		public static final int store=23;
		
		//+
		public static final int addC=30;
		public static final int addA=31;
		
		//-
		public static final int subC=40;
		public static final int subA=41;
		
		//*
		public static final int multC=50;
		public static final int multA=51;
		
		//:
		public static final int divC=60;
		public static final int divA=61;
		
		//%
		public static final int modC=70;
		public static final int modA=71;
		
		//I/O
		public static final int input=80;
		public static final int output=81;
	}
}
