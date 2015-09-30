import org.json.*;
import java.io.*;

public class CodeGenerator{
	public static void codeGenerator(String [] args){
		String jsonPreParse, fileExtension, output;
		JSONObject classList;
		codeWriter toWrite;
		Scanner kybd = new Scanner(System.in);
		
		//File reading
		
		FileReader fileReader = new FileReader('//input//'+args[0]+'.txt');
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		while(bufferedReader.ready()){
			jsonPreParse += bufferedReader.readLine();
		}
		bufferedReader.close();
		
		//JSON parsing
		classList = new JSONObject(jsonPreParse);
		
		//Language switching
		switch(args[1].toLower()){
			case 'java':
				toWrite = new JavaWriter();
				fileExtension = '.java';
				break;
			case 'c++':
				toWrite = new CPlusPlusWriter();
				fileExtension = '.cpp'
				break;
		}
		//Code Writing
		for(int i = 0; i<classList.classes.length-1;i++){
			toWrite.classStart(classList.classes[i].className,classList.classes[i].accessModifier, classList.classes[i].isInterface, classList.classes[i].classModifier);
			toWrite.classEnd();
		}
		//Writing to file
		String output = toWrite.toString();
		FileWriter fileWriter = new FileWriter('//input//'+args[0]+fileExtension);
		fileWriter.write(output);
		fileWriter.close();
	}
}