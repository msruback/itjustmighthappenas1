package codegen;
import org.json.*;
import java.io.*;

//Written by Matthew Rubacky
//Json library provided by www.json.org
public class CodeGenerator{
	public void isSuccess(String errorString){
		if(!errorString.equals("success")){
			System.err.println(errorString);
		}
	}
	public static void codeGenerator(String [] args){
		String jsonPreParse="", fileExtension="", output;
		String[] modifierArray;
		JSONObject classList;
		CodeWriter toWrite;
		boolean isInterface;
		
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
				isError("Error: Language not supported");
		}
		//Code Writing
		if(toWrite != null && classList.has("classes")){
			JSONObject currentClass,currentField,currentMethod;
			boolean isValidType, getter, setter;
			String accessModifier;
			for(int i = 0; i<classList.getJSONArray("classes").length()-1;i++){
				currentClass = classList.get("classes["+i+"]");
				//Check for properly formated class name and access modifier
				if(currentClass.has("className") && currentClass.has("accessModifier")){
					if(toWrite.isValidName(currentClass.getString("className")) && toWrite.isValidAccess(currentClass.getString("accessModifier"){
						//handling classModifiers
						if(currentClass.has("classModifier")){
							modifierArray = new String[currentClass.getJSONArray("classModifier").length()];
							for(int k = 0;k<=currentClass.getJSONArray("classModifier").length()-1;k++){
								if(toWrite.isValidModifier(currentClass.getString("classModifier["+k+"]"))){
									modifierArray[k] = currentClass.getString("classModifier["+k+"]");
								}
							}
						}else{
							modifierArray = new String[0];
						}
						//handling missing isInterface
						if(currentClass.has("isInterface")){
							isInterface = currentClass.getBoolean("isInterface");
						}else{
							isInterface = false;
						}
						//handling constructors
						if(currentClass.has("constructors") && !currentClass.getString("constructor[0]").equalsIgnoreCase("none")){
							for(int k = 0; k<=currentClass.getJSONArray("constructors").length()-1;k++){
								if(currentClass.getString("constructor["+k+"]").equalsIgnoreCase("default")){
									isError(toWrite.defaultConstructor(currentClass.getString("className"),currentClass.getJSONArray("fields"),classList.getJSONArray("classes"));
								}else if(currentClass.getString("constructor["+k+"]").equalsIgnoreCase("copy")){
									isError(toWrite.copyConstructor(currentClass.getString("className")));
								}else if(currentClass.getString("constructor["+k+"]").equalsIgnoreCase("full")){
									isError(toWrite.fullConstructor(currentClass.getString("className"),currentClass.getJSONArray("fields"),classList.getJSONArray("classes"));
								}
							}
						}
						isError(toWrite.classStart(currentClass.getString("className"),currentClass.getString("accessModifier"), isInterface, modifierArray));
						//fields
						if(currentClass.has("fields")){
							for(int j = 0; j<currentClass.getJSONArray("fields").length()-1;j++){
								isValidType = false;
								currentField = currentClass.get("fields["j"]");
								if(currentField.has("fieldName") && toWrite.isValidName(currentField.getString("fieldName")) && currentField.has("filedType")){
									for(int = k; k<classList.getJSONArray("classes").length()-1;k++){
										if(currentField.getString("fieldType").equals(classList.getString("classes["+k+"].className"))){
											isValidType = true;
										}
									}
									if(!isValidType && isValidType(currentField.getString("fieldType"))){
										isValidType = true;
									}
									if(isValidType = true){
										//handling Access Modifier
										if(currentField.has("accessModifier"){
											if(toWrite.isValidAccess(currentField.getString("accessModifier"))){
												accessModifier = currentField.getString("accessModifier");
											}else{
												accessModifier = "";
											}
										}
										//handling fieldModifier
										if(currentField.has("fieldModifier")){
											modifierArray = new String[currentField.getJSONArray("fieldModifier").length()];
											for(int k = 0;k<=currentField.getJSONArray("fieldModifier").length()-1;k++){
												if(toWrite.isValidModifier(currentField.getString("fieldModifier["+k+"]"))){
													modifierArray[k] = currentField.getString("fieldModifier["+k+"]");
												}
											}
										}else{
											modifierArray = new String[0];
										}
										//handling Getter
										if(currentField.has("getter")){
											getter = currentField.getBoolean("getter");
										}else{
											getter = false;
										}
										//handling Setter
										if(currentField.has("setter")){
											setter = currentField.getBoolean("setter");
										}else{
											setter = false;
										}
										isError(toWrite.addField(currentField.getString("fieldType"), currentField.getString("fieldName"), accessModifier, getter, setter, modifierArray))
									}
								}
							}
						}
						//methods
						if(currentClass.has("methods")){
							for(int j = 0; j<currentClass.getJSONArray("methods").length()-1;j++){
								isValidType = false;
								currentMethod = currentClass.get("method["j"]");
								if(currentMethod.has("methodName") && toWrite.isValidName(currentMethod.getString("methodName")) && currentMethod.has("methodType")){
									for(int = k; k<classList.getJSONArray("classes").length()-1;k++){
										if(currentMethod.getString("methodType").equals(classList.getString("classes["+k+"].className"))){
											isValidType = true;
										}
									}
									if(!isValidType && isValidType(currentMethod.getString("methodType"))){
										isValidType = true;
									}
									if(isValidType = true){
										//handling Access Modifier
										if(currentMethod.has("accessModifier"){
											if(toWrite.isValidAccess(currentMethod.getString("accessModifier"))){
												accessModifier = currentMethod.getString("accessModifier");
											}else{
												accessModifier = "";
											}
										}
										//handling fieldModifier
										if(currentMethod.has("methodModifier")){
											modifierArray = new String[currentMethod.getJSONArray("methodModifier").length()];
											for(int k = 0;k<=currentMethod.getJSONArray("methodModifier").length()-1;k++){
												if(toWrite.isValidModifier(currentMethod.getString("methodModifier["+k+"]"))){
													modifierArray[k] = currentMethod.getString("methodModifier["+k+"]");
												}
											}
										}else{
											modifierArray = new String[0];
										}
										isError(toWrite.addMethod(currentMethod.getString("methodType"), currentMethod.getString("methodName"), accessModifier, currentMethod.getJSONObject("parameters"), modifierArray))
									}
								}
							}
						}
						toWrite.classEnd();
					}
				}
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