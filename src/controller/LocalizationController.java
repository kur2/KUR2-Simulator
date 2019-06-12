package controller;

import model.LocalizedStrings;

public class LocalizationController {
	public enum Language{
		GERMAN,
		ENGLISH
	}
	
	public static void setLanguage(Language language) {
		switch(language) {
		case GERMAN:
			setStringsToGerman();
			break;
		case ENGLISH:
			setStringsToEnglish();
			break;
		}
	}
	
	private static void setStringsToGerman() {
		// == gui ==
		LocalizedStrings.guiFrameCaption="KUR2 - Bedienelemente";
		
		//program control
		LocalizedStrings.programControlTabName="Programmkontrolle";
		LocalizedStrings.programControlProgramInMemory="Programm im Speicher";
		LocalizedStrings.programControlLoad="Programm laden";
		LocalizedStrings.programControlReset="Maschine zurücksetzen";
		
		//simulation control
		LocalizedStrings.simulationControlTabName="Simulationskontrolle";
		LocalizedStrings.simulationControlStart="Simulation starten";
		LocalizedStrings.simulationControlPause="Simulation pausieren";
		LocalizedStrings.simulationControlSpeed="Simulationsgeschwindigkeit";
		
		//stepping
		LocalizedStrings.steppingTabName="Stepping";
		LocalizedStrings.steppingStep="Einzelschritt ausführen";
		LocalizedStrings.steppingMicroinstruction="Mikroinstruktion ausführen";
		LocalizedStrings.steppingMachineInstruction="Maschinenbefehl ausführen";
		
		//dialog
		LocalizedStrings.dialogMachineReset="Maschine zurückgesetzt";
		LocalizedStrings.dialogProgramLoaded="Programm geladen";
		
		// == visualization ==
		//Component names
		LocalizedStrings.memoryName="SPEICHERWERK";
		LocalizedStrings.aluName="RECHENWERK";
		LocalizedStrings.controlName="STEUERWERK";
		LocalizedStrings.inputName="EINGABEWERK";
		LocalizedStrings.outputName="AUSGABEWERK";
		
		//Component parts
		LocalizedStrings.memoryAddress="ADRESSE";
		LocalizedStrings.memoryContent="INHALT";
		
		// == microinstructions ==
		//internal
		LocalizedStrings.intHalt					="   HALT";
		LocalizedStrings.intProgCounterIncrease		="   # ERHOEHEN";
		LocalizedStrings.intDecode					="   MICROPROG WAEHLEN";
		LocalizedStrings.intDataToProgCounter		="   DATA -> #";
		LocalizedStrings.intDataEqualsNull			="   DATA = 0 ?";
		LocalizedStrings.intDataNotNull				="   DATA =/= 0 ?";
		LocalizedStrings.intDataGreaterNull			="   DATA > 0 ?";
		LocalizedStrings.intDataLessNull			="   DATA < 0 ?";
		LocalizedStrings.intProgCounterToMemAdd		="   # -> SP ADR";
		LocalizedStrings.intDataToMemAdd			="   DATA -> SP ADR";
		LocalizedStrings.intDataToInputAdd			="   DATA -> EIN ADR";
		LocalizedStrings.intDataToOutputAdd			="   DATA -> AUS ADR";
		
		//memory
		LocalizedStrings.memLoadToControl			="SP LADEN -> ST";
		LocalizedStrings.memLoadToALU				="SP LADEN -> RW";
		LocalizedStrings.memStoreFromAlu			="SP SPEICHERN <- RW";
		LocalizedStrings.memStop					="SP STOP";
		
		//in
		LocalizedStrings.inWriteToALU				="EIN EINGABE -> RW";
		LocalizedStrings.inStop						="EIN STOP";
		
		//out
		LocalizedStrings.outReadFromALU				="AUS AUSGABE <- RW";
		LocalizedStrings.outStop					="AUS STOP";
		
		//alu
		LocalizedStrings.aluWriteToMem				="RW ACC -> SP";
		LocalizedStrings.aluWriteToOutput			="RW ACC -> AUS";
		LocalizedStrings.aluWriteToControl			="RW ACC -> ST";
		LocalizedStrings.aluStop					="RW STOP";
		LocalizedStrings.aluReset					="RW ACC = 0";
		LocalizedStrings.aluPush					="RW LADEN";
		LocalizedStrings.aluAdd						="RW PLUS";
		LocalizedStrings.aluSub						="RW MINUS";
		LocalizedStrings.aluMul						="RW MAL";
		LocalizedStrings.aluDiv						="RW GETEILT";
		LocalizedStrings.aluMod						="RW MODULO";
	}
	
	private static void setStringsToEnglish() {
		// == gui ==
		LocalizedStrings.guiFrameCaption="KUR2 - Control interface";
		
		//program control
		LocalizedStrings.programControlTabName="Program control";
		LocalizedStrings.programControlProgramInMemory="Program in memory";
		LocalizedStrings.programControlLoad="Load program";
		LocalizedStrings.programControlReset="Reset machine";
		
		//simulation control
		LocalizedStrings.simulationControlTabName="Simulation control";
		LocalizedStrings.simulationControlStart="Start simulation";
		LocalizedStrings.simulationControlPause="Pause simulation";
		LocalizedStrings.simulationControlSpeed="Simulation speed";
		
		//stepping
		LocalizedStrings.steppingTabName="Stepping";
		LocalizedStrings.steppingStep="Execute single step";
		LocalizedStrings.steppingMicroinstruction="Execute microinstruction";
		LocalizedStrings.steppingMachineInstruction="Execute machine instruction";
		
		//dialog
		LocalizedStrings.dialogMachineReset="Machine reset";
		LocalizedStrings.dialogProgramLoaded="Program loaded";
		
		// == visualization ==
		//Component names
		LocalizedStrings.memoryName="MEMORY";
		LocalizedStrings.aluName="ALU";
		LocalizedStrings.controlName="CONTROL";
		LocalizedStrings.inputName="INPUT";
		LocalizedStrings.outputName="OUTPUT";
		
		//Component parts
		LocalizedStrings.memoryAddress="ADDRESS";
		LocalizedStrings.memoryContent="CONTENT";
		
		// == microinstructions ==
		//internal
		LocalizedStrings.intHalt					="   HALT";
		LocalizedStrings.intProgCounterIncrease		="   # INCREASE";
		LocalizedStrings.intDecode					="   CHOOSE MICROPROG";
		LocalizedStrings.intDataToProgCounter		="   DATA -> #";
		LocalizedStrings.intDataEqualsNull			="   DATA = 0 ?";
		LocalizedStrings.intDataNotNull				="   DATA =/= 0 ?";
		LocalizedStrings.intDataGreaterNull			="   DATA > 0 ?";
		LocalizedStrings.intDataLessNull			="   DATA < 0 ?";
		LocalizedStrings.intProgCounterToMemAdd		="   # -> MEM ADR";
		LocalizedStrings.intDataToMemAdd			="   DATA -> MEM ADR";
		LocalizedStrings.intDataToInputAdd			="   DATA -> IN ADR";
		LocalizedStrings.intDataToOutputAdd			="   DATA -> OUT ADR";
		
		//memory
		LocalizedStrings.memLoadToControl			="MEM LOAD -> CTR";
		LocalizedStrings.memLoadToALU				="MEM LOAD -> ALU";
		LocalizedStrings.memStoreFromAlu			="MEM STORE <- ALU";
		LocalizedStrings.memStop					="MEM STOP";
		
		//in
		LocalizedStrings.inWriteToALU				="IN INPUT -> ALU";
		LocalizedStrings.inStop						="IN STOP";
		
		//out
		LocalizedStrings.outReadFromALU				="OUT OUTPUT <- ALU";
		LocalizedStrings.outStop					="OUT STOP";
		
		//alu
		LocalizedStrings.aluWriteToMem				="ALU ACC -> MEM";
		LocalizedStrings.aluWriteToOutput			="ALU ACC -> OUT";
		LocalizedStrings.aluWriteToControl			="ALU ACC -> CTR";
		LocalizedStrings.aluStop					="ALU STOP";
		LocalizedStrings.aluReset					="ALU ACC = 0";
		LocalizedStrings.aluPush					="ALU LOAD";
		LocalizedStrings.aluAdd						="ALU ADD";
		LocalizedStrings.aluSub						="ALU SUBTRACT";
		LocalizedStrings.aluMul						="ALU MULTIPLY";
		LocalizedStrings.aluDiv						="ALU DIVIDE";
		LocalizedStrings.aluMod						="ALU MODULO";
	}
}
