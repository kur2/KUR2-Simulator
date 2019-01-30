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
	
	static{
		microProgramMap=new HashMap<>();
		
		//---------------META--------------
		//instruction fetching microprog
		ArrayList<String> instructionFetchProg=new ArrayList<String>();
		instructionFetchProg.add(MicroCode.intProgCounterToMemAdd);
		instructionFetchProg.add(MicroCode.memLoadToControl);
		instructionFetchProg.add(MicroCode.memStop);
		instructionFetchProg.add(MicroCode.intDecode);
		instructionFetchProg.add(MicroCode.intProgCounterIncrease);
		instructionFetchProg.add(MicroCode.intProgCounterToMemAdd);
		instructionFetchProg.add(MicroCode.intProgCounterIncrease);
		microProgramMap.put(InstructionSet.fetch, instructionFetchProg);
		
		
		//---------------FLOW--------------
		//empty microprog/no op
		ArrayList<String> emptyProg=new ArrayList<String>();
		microProgramMap.put(InstructionSet.noop, emptyProg);
		
		//halt
		ArrayList<String> haltProg=new ArrayList<String>();
		haltProg.add(MicroCode.intHalt);
		microProgramMap.put(InstructionSet.halt, haltProg);
		
		
		//---------------JUMP--------------
		//jump
		ArrayList<String> jumpProg=new ArrayList<String>();
		jumpProg.add(MicroCode.memLoadToControl);
		jumpProg.add(MicroCode.memStop);
		jumpProg.add(MicroCode.intDataToProgCounter);
		microProgramMap.put(InstructionSet.jump, jumpProg);
		
		//conditional jump = 0
		ArrayList<String> cJumpE0Prog=new ArrayList<String>();
		cJumpE0Prog.add(MicroCode.aluWriteToControl);
		cJumpE0Prog.add(MicroCode.aluStop);
		cJumpE0Prog.add(MicroCode.intDataEqualsNull);
		cJumpE0Prog.add(MicroCode.memLoadToControl);
		cJumpE0Prog.add(MicroCode.memStop);
		cJumpE0Prog.add(MicroCode.intDataToProgCounter);
		microProgramMap.put(InstructionSet.jumpE0, cJumpE0Prog);
		
		//conditional jump != 0
		ArrayList<String> cJumpN0Prog=new ArrayList<String>();
		cJumpN0Prog.add(MicroCode.aluWriteToControl);
		cJumpN0Prog.add(MicroCode.aluStop);
		cJumpN0Prog.add(MicroCode.intDataNotNull);
		cJumpN0Prog.add(MicroCode.memLoadToControl);
		cJumpN0Prog.add(MicroCode.memStop);
		cJumpN0Prog.add(MicroCode.intDataToProgCounter);
		microProgramMap.put(InstructionSet.jumpN0, cJumpN0Prog);
		
		//conditional jump > 0
		ArrayList<String> cJumpG0Prog=new ArrayList<String>();
		cJumpG0Prog.add(MicroCode.aluWriteToControl);
		cJumpG0Prog.add(MicroCode.aluStop);
		cJumpG0Prog.add(MicroCode.intDataGreaterNull);
		cJumpG0Prog.add(MicroCode.memLoadToControl);
		cJumpG0Prog.add(MicroCode.memStop);
		cJumpG0Prog.add(MicroCode.intDataToProgCounter);
		microProgramMap.put(InstructionSet.jumpG0, cJumpG0Prog);
		
		//conditional jump < 0
		ArrayList<String> cJumpL0Prog=new ArrayList<String>();
		cJumpL0Prog.add(MicroCode.aluWriteToControl);
		cJumpL0Prog.add(MicroCode.aluStop);
		cJumpL0Prog.add(MicroCode.intDataLessNull);
		cJumpL0Prog.add(MicroCode.memLoadToControl);
		cJumpL0Prog.add(MicroCode.memStop);
		cJumpL0Prog.add(MicroCode.intDataToProgCounter);
		microProgramMap.put(InstructionSet.jumpL0, cJumpL0Prog);
		
		//------------LOAD/STORE-----------
		//write null to acc
		ArrayList<String> nullProg=new ArrayList<String>();
		nullProg.add(MicroCode.aluReset);
		nullProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.nullAcc, nullProg);

		//load constant value
		ArrayList<String> cLoadProg=new ArrayList<String>();
		cLoadProg.add(MicroCode.memLoadToALU);
		cLoadProg.add(MicroCode.memStop);
		cLoadProg.add(MicroCode.aluPush);
		cLoadProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.loadC, cLoadProg);
		
		//load value from memory
		ArrayList<String> loadProg=new ArrayList<String>();
		loadProg.add(MicroCode.memLoadToControl);
		loadProg.add(MicroCode.memStop);
		loadProg.add(MicroCode.intDataToMemAdd);
		loadProg.add(MicroCode.memLoadToALU);
		loadProg.add(MicroCode.memStop);
		loadProg.add(MicroCode.aluPush);
		loadProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.loadA, loadProg);
		
		//store value
		ArrayList<String> storeProg=new ArrayList<String>();
		storeProg.add(MicroCode.memLoadToControl);
		storeProg.add(MicroCode.memStop);
		storeProg.add(MicroCode.intDataToMemAdd);
		storeProg.add(MicroCode.aluWriteToMem);
		storeProg.add(MicroCode.memStoreFromAlu);
		storeProg.add(MicroCode.memStop);
		storeProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.store, storeProg);
		
		
		//--------------PLUS---------------
		//acc + c
		ArrayList<String> addCProg=new ArrayList<String>();
		addCProg.add(MicroCode.memLoadToALU);
		addCProg.add(MicroCode.memStop);
		addCProg.add(MicroCode.aluAdd);
		addCProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.addC, addCProg);
		
		//acc + <a>
		ArrayList<String> addAProg=new ArrayList<String>();
		addAProg.add(MicroCode.memLoadToControl);
		addAProg.add(MicroCode.memStop);
		addAProg.add(MicroCode.intDataToMemAdd);
		addAProg.add(MicroCode.memLoadToALU);
		addAProg.add(MicroCode.memStop);
		addAProg.add(MicroCode.aluAdd);
		addAProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.addA, addAProg);
		
		
		//--------------MINUS--------------
		//acc - c
		ArrayList<String> subCProg=new ArrayList<String>();
		subCProg.add(MicroCode.memLoadToALU);
		subCProg.add(MicroCode.memStop);
		subCProg.add(MicroCode.aluSub);
		subCProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.subC, subCProg);
		
		//acc - <a>
		ArrayList<String> subAProg=new ArrayList<String>();
		subAProg.add(MicroCode.memLoadToControl);
		subAProg.add(MicroCode.memStop);
		subAProg.add(MicroCode.intDataToMemAdd);
		subAProg.add(MicroCode.memLoadToALU);
		subAProg.add(MicroCode.memStop);
		subAProg.add(MicroCode.aluSub);
		subAProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.subA, subAProg);
		
		
		//--------------MULT---------------
		//acc * c
		ArrayList<String> multCProg=new ArrayList<String>();
		multCProg.add(MicroCode.memLoadToALU);
		multCProg.add(MicroCode.memStop);
		multCProg.add(MicroCode.aluMul);
		multCProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.multC, multCProg);
		
		//acc * <a>
		ArrayList<String> multAProg=new ArrayList<String>();
		multAProg.add(MicroCode.memLoadToControl);
		multAProg.add(MicroCode.memStop);
		multAProg.add(MicroCode.intDataToMemAdd);
		multAProg.add(MicroCode.memLoadToALU);
		multAProg.add(MicroCode.memStop);
		multAProg.add(MicroCode.aluMul);
		multAProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.multA, multAProg);
		
		
		//--------------DIV----------------
		//acc / c
		ArrayList<String> divCProg=new ArrayList<String>();
		divCProg.add(MicroCode.memLoadToALU);
		divCProg.add(MicroCode.memStop);
		divCProg.add(MicroCode.aluDiv);
		divCProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.divC, divCProg);
		
		//acc / <a>
		ArrayList<String> divAProg=new ArrayList<String>();
		divAProg.add(MicroCode.memLoadToControl);
		divAProg.add(MicroCode.memStop);
		divAProg.add(MicroCode.intDataToMemAdd);
		divAProg.add(MicroCode.memLoadToALU);
		divAProg.add(MicroCode.memStop);
		divAProg.add(MicroCode.aluDiv);
		divAProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.divA, divAProg);
		
		
		//--------------MOD----------------
		//acc % c
		ArrayList<String> modCProg=new ArrayList<String>();
		modCProg.add(MicroCode.memLoadToALU);
		modCProg.add(MicroCode.memStop);
		modCProg.add(MicroCode.aluMod);
		modCProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.modC, modCProg);
		
		//acc % <a>
		ArrayList<String> modAProg=new ArrayList<String>();
		modAProg.add(MicroCode.memLoadToControl);
		modAProg.add(MicroCode.memStop);
		modAProg.add(MicroCode.intDataToMemAdd);
		modAProg.add(MicroCode.memLoadToALU);
		modAProg.add(MicroCode.memStop);
		modAProg.add(MicroCode.aluMod);
		modAProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.modA, modAProg);
		
		
		//--------------I/O----------------
		//input value
		ArrayList<String> inputProg=new ArrayList<String>();
		inputProg.add(MicroCode.memLoadToControl);
		inputProg.add(MicroCode.memStop);
		inputProg.add(MicroCode.intDataToInputAdd);
		inputProg.add(MicroCode.inWriteToALU);
		inputProg.add(MicroCode.aluPush);
		inputProg.add(MicroCode.aluStop);
		inputProg.add(MicroCode.inStop);
		microProgramMap.put(InstructionSet.input, inputProg);
		
		//output value
		ArrayList<String> outputProg=new ArrayList<String>();
		outputProg.add(MicroCode.memLoadToControl);
		outputProg.add(MicroCode.memStop);
		outputProg.add(MicroCode.intDataToOutputAdd);
		outputProg.add(MicroCode.aluWriteToOutput);
		outputProg.add(MicroCode.outReadFromALU);
		outputProg.add(MicroCode.outStop);
		outputProg.add(MicroCode.aluStop);
		microProgramMap.put(InstructionSet.output, outputProg);
	}
	
	public class MicroCode{
		//internal
		public static final String intHalt						="   HALT";
		public static final String intProgCounterIncrease		="   # ERHOEHEN";
		public static final String intDecode					="   MICROPROG WAEHLEN";
		public static final String intDataToProgCounter			="   DATA -> #";
		public static final String intDataEqualsNull			="   DATA = 0 ?";
		public static final String intDataNotNull				="   DATA =/= 0 ?";
		public static final String intDataGreaterNull			="   DATA > 0 ?";
		public static final String intDataLessNull				="   DATA < 0 ?";
		public static final String intProgCounterToMemAdd		="   # -> SP ADR";
		public static final String intDataToMemAdd				="   DATA -> SP ADR";
		public static final String intDataToInputAdd			="   DATA -> EIN ADR";
		public static final String intDataToOutputAdd			="   DATA -> AUS ADR";
		
		//memory
		public static final String memLoadToControl				="SP LADEN -> ST";
		public static final String memLoadToALU					="SP LADEN -> RW";
		public static final String memStoreFromAlu				="SP SPEICHERN <- RW";
		public static final String memStop						="SP STOP";
		
		//in
		public static final String inWriteToALU					="EIN EINGABE -> RW";
		public static final String inStop						="EIN STOP";
		
		//out
		public static final String outReadFromALU				="AUS AUSGABE <- RW";
		public static final String outStop						="AUS STOP";
		
		//alu
		public static final String aluWriteToMem				="RW ACC -> SP";
		public static final String aluWriteToOutput				="RW ACC -> AUS";
		public static final String aluWriteToControl			="RW ACC -> ST";
		public static final String aluStop						="RW STOP";
		public static final String aluReset						="RW ACC = 0";
		public static final String aluPush						="RW LADEN";
		public static final String aluAdd						="RW PLUS";
		public static final String aluSub						="RW MINUS";
		public static final String aluMul						="RW MAL";
		public static final String aluDiv						="RW GETEILT";
		public static final String aluMod						="RW MODULO";
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
