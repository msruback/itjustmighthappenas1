package codegen;
import org.json.*;
import java.io.*;

//Written by Matthew Rubacky
//Json library provided by www.json.org
public class CodeGenerator{
	public static void codeGenerator(String [] args){
		String jsonPreParse="", fileExtension="", output;
		String[] tempArray;
		JSONObject classList;
		CodeWriter toWrite;
		
		//File reading
		try{
		FileReader fileReader = new FileReader("//input//"+args[0]+".txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
			while(bufferedReader.ready()){
				jsonPreParse += bufferedReader.readLine();
			}
			bufferedReader.close();
		}catch(java.io.IOException e){
			System.err.println("File could not be read");
		}
		
		//JSON parsing
		classList = new JSONObject(jsonPreParse);
		
		//Language switching
		if(args[1].equalsIgnoreCase("java")){
				toWrite = new JavaWriter();
				fileExtension = ".java";
		} else if(args[1].equalsIgnoreCase("c++")){
				toWrite = new CPlusPlusWriter();
				fileExtension = ".cpp";
		} else if(args[1].equalsIgnoreCase("pseudo")){
				toWrite = new PseudoWriter();
				fileExtension = ".txt";
		} else {
				toWrite = null;
				System.err.println("Language not supported");
		}
		//Code Writing
		if(toWrite == null){
			for(int i = 0; i<classList.getJSONArray("classes").length()-1;i++){
				tempArray = new String[classList.getJSONArray("classes["+i+"].classModifier").length()];
				for(int k = 0;k<classList.getJSONArray("classes["+i+"].classModifier").length()-1;k++){
					tempArray[k] = classList.getString("classes["+i+"].classModifier["+k+"]");
				}
				toWrite.classStart(classList.getString("classes["+i+"].className"),classList.getString("classes["+i+"].accessModifier"), classList.getBoolean("classes["+i+"].isInterface"), tempArray);
				toWrite.classEnd();
			}
			//Writing to file
			output = toWrite.toString();
			try{
			FileWriter fileWriter = new FileWriter("//input//"+args[0]+fileExtension);
			fileWriter.write(output);
			fileWriter.close();
			}catch(java.io.IOException e){
				System.err.println("File could not be written to");
			}
		}
	}
}