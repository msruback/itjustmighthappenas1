import org.json.*;
import java.io.*;

public class Main{
	public static void main(String [] args){
		String fileLocation, jsonPreParse, language;
		JSONObject classList;
		codeWriter toWrite;
		Scanner kybd = new Scanner(System.in);
		
		//File reading
		System.out.println('Please provide a file location. Not entering anything will use input.txt');
		fileLocation = kybd.next();
		FileReader fileReader = new FileReader('input.txt');
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		while(bufferedReader.ready()){
			jsonPreParse += bufferedReader.readLine();
		}
		bufferedReader.close();
		
		//JSON parsing
		classList = new JSONObject(jsonPreParse);
		
		//Code Writing
		do{
		System.out.println('Please choose a language. Possible Options are Java, and C++');
		language = kybd.next();
		}while(language.equalsIgnoreCase('java')||language.equalsIgnoreCase('c++'));
		switch(language.toLower()){
			case 'java':
				toWrite = new javaWriter();
				break;
			case 'c++':
				toWrite = new cPlusPlusWriter();
				break;
		}
		for(int i = 0; i<classList.classes.length-1;i++){
			toWrite.classStart(classList.classes[i].className,classList.classes[i].accessModifier, classList.classes[i].classType);
			toWrite.classEnd();
		}
		
	}
}