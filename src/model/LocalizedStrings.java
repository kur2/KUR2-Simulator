package model;

import vonNeumannEmulatorKUR2.MicroPrograms;

public class LocalizedStrings {
	// == gui ==
	public static String guiFrameCaption="KUR2 - Bedienelemente";
	
	//program control
	public static String programControlTabName="Programmkontrolle";
	public static String programControlProgramInMemory="Programm im Speicher";
	public static String programControlLoad="Programm laden";
	public static String programControlReset="Maschine zurücksetzen";
	
	//simulation control
	public static String simulationControlTabName="Simulationskontrolle";
	public static String simulationControlStart="Simulation starten";
	public static String simulationControlPause="Simulation pausieren";
	public static String simulationControlSpeed="Simulationsgeschwindigkeit";
	
	//stepping
	public static String steppingTabName="Stepping";
	public static String steppingStep="Einzelschritt ausführen";
	public static String steppingMicroinstruction="Mikroinstruktion ausführen";
	public static String steppingMachineInstruction="Maschinenbefehl ausführen";
	
	//dialog
	public static String dialogMachineReset="Maschine zurückgesetzt";
	public static String dialogProgramLoaded="Programm geladen";
	
	// == visualization ==
	//Component names
	public static String memoryName="SPEICHERWERK";
	public static String aluName="RECHENWERK";
	public static String controlName="STEUERWERK";
	public static String inputName="EINGABEWERK";
	public static String outputName="AUSGABEWERK";
	
	//Component parts
	public static String memoryAddress="ADRESSE";
	public static String memoryContent="INHALT";
	
	// == microinstructions ==
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
	
	public static String getInstructionText(String microinstruction) {
		if(microinstruction.equals(MicroPrograms.intHalt))
			return intHalt;
		else if(microinstruction.equals(MicroPrograms.intProgCounterIncrease))
			return intProgCounterIncrease;
		else if(microinstruction.equals(MicroPrograms.intDecode))
			return intDecode;
		else if(microinstruction.equals(MicroPrograms.intDataToProgCounter))
			return intDataToProgCounter;
		else if(microinstruction.equals(MicroPrograms.intDataEqualsNull))
			return intDataEqualsNull;
		else if(microinstruction.equals(MicroPrograms.intDataNotNull))
			return intDataNotNull;
		else if(microinstruction.equals(MicroPrograms.intDataGreaterNull))
			return intDataGreaterNull;
		else if(microinstruction.equals(MicroPrograms.intDataLessNull))
			return intDataLessNull;
		else if(microinstruction.equals(MicroPrograms.intProgCounterToMemAdd))
			return intProgCounterToMemAdd;
		else if(microinstruction.equals(MicroPrograms.intDataToMemAdd))
			return intDataToMemAdd;
		else if(microinstruction.equals(MicroPrograms.intDataToInputAdd))
			return intDataToInputAdd;
		else if(microinstruction.equals(MicroPrograms.intDataToOutputAdd))
			return intDataToOutputAdd;
		
		//memory
		else if(microinstruction.equals(MicroPrograms.memLoadToControl))
			return memLoadToControl;
		else if(microinstruction.equals(MicroPrograms.memLoadToALU))
			return memLoadToALU;
		else if(microinstruction.equals(MicroPrograms.memStoreFromAlu))
			return memStoreFromAlu;
		else if(microinstruction.equals(MicroPrograms.memStop))
			return memStop;
		
		//in
		else if(microinstruction.equals(MicroPrograms.inWriteToALU))
			return inWriteToALU;
		else if(microinstruction.equals(MicroPrograms.inStop))
			return inStop;
		
		//out
		else if(microinstruction.equals(MicroPrograms.outReadFromALU))
			return outReadFromALU;
		else if(microinstruction.equals(MicroPrograms.outStop))
			return outStop;
		
		//alu
		else if(microinstruction.equals(MicroPrograms.aluWriteToMem))
			return aluWriteToMem;
		else if(microinstruction.equals(MicroPrograms.aluWriteToOutput))
			return aluWriteToOutput;
		else if(microinstruction.equals(MicroPrograms.aluWriteToControl))
			return aluWriteToControl;
		else if(microinstruction.equals(MicroPrograms.aluStop))
			return aluStop;
		else if(microinstruction.equals(MicroPrograms.aluReset))
			return aluReset;
		else if(microinstruction.equals(MicroPrograms.aluPush))
			return aluPush;
		else if(microinstruction.equals(MicroPrograms.aluAdd))
			return aluAdd;
		else if(microinstruction.equals(MicroPrograms.aluSub))
			return aluSub;
		else if(microinstruction.equals(MicroPrograms.aluMul))
			return aluMul;
		else if(microinstruction.equals(MicroPrograms.aluDiv))
			return aluDiv;
		else if(microinstruction.equals(MicroPrograms.aluMod))
			return aluMod;
		
		System.err.println("LocalizedStrings: Error getting Microinstruction: Unknown MicroCode value: "+microinstruction);
		return null;
	}
}
