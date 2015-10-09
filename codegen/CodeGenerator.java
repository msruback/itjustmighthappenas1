package codegen;
import org.json.*;
import java.io.*;

//Written by Matthew Rubacky
//Json library provided by www.json.org
public class CodeGenerator{
	public static void isError(String errorString){
			if(!errorString.equals("success")){
				System.err.println(errorString);
			}
	}
	public static void main(String [] args){
		String jsonPreParse="", fileExtension="", output;
		String[] modifierArray;
		JSONObject input;
		JSONArray classArray;
		CodeWriter toWrite;
		boolean isInterface;
		
		//File reading
		try{
		FileReader fileReader = new FileReader(".//codegen//input//"+args[0]+".txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
			while(bufferedReader.ready()){
				jsonPreParse += bufferedReader.readLine();
			}
			bufferedReader.close();
		}catch(java.io.IOException e){
			isError("Error: File could not be read");
		}
		//JSON parsing
		input = new JSONObject(jsonPreParse);
		
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
		if(toWrite != null && input.has("classes")){
			classArray = input.getJSONArray("classes");
			JSONObject currentClass,currentField,currentMethod;
			JSONArray modifierJSONArray,constructorArray,fieldsArray,methodArray,parameters;
			boolean isValidType, getter, setter;
			String accessModifier;
			for(int i = 0; i<=classArray.length()-1;i++){
				currentClass = classArray.getJSONObject(i);
				//Check for properly formated class name and access modifier
				if(currentClass.has("className") && currentClass.has("accessModifier")){
					if(toWrite.isValidName(currentClass.getString("className")) && toWrite.isValidAccess(currentClass.getString("accessModifier"))){
						//handling classModifiers
						if(currentClass.has("classModifier")){
							modifierJSONArray = currentClass.getJSONArray("classModifier");
							modifierArray = new String[modifierJSONArray.length()];
							for(int k = 0;k<=modifierJSONArray.length()-1;k++){
								if(toWrite.isValidModifier(modifierJSONArray.getString(k))){
									modifierArray[k] = modifierJSONArray.getString(k);
								}else{
                                                                    modifierArray[k] = "";
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
						if(currentClass.has("constructors")){
							constructorArray = currentClass.getJSONArray("constructors");
						 	if(!constructorArray.getString(0).equalsIgnoreCase("none")){
								for(int k = 0; k<=constructorArray.length()-1;k++){
									if(constructorArray.getString(k).equalsIgnoreCase("copy")){
										isError(toWrite.copyConstructor(currentClass.getString("className"),currentClass.getJSONArray("fields"),classArray));
									}else if(constructorArray.getString(k).equalsIgnoreCase("default")){
										isError(toWrite.defaultConstructor(currentClass.getString("className")));
									}else if(constructorArray.getString(k).equalsIgnoreCase("full")){
										isError(toWrite.fullConstructor(currentClass.getString("className"),currentClass.getJSONArray("fields"),classArray));
									}
								}
							}
						}
						isError(toWrite.classStart(currentClass.getString("className"),currentClass.getString("accessModifier"), isInterface, modifierArray));
						//fields
						if(currentClass.has("fields")){
							fieldsArray=currentClass.getJSONArray("fields");
							for(int j = 0; j<=fieldsArray.length()-1;j++){
								isValidType = false;
								currentField = fieldsArray.getJSONObject(j);
								if(currentField.has("fieldName") && toWrite.isValidName(currentField.getString("fieldName")) && currentField.has("fieldType")){
									for(int k = 0; k<=classArray.length()-1;k++){
										if(currentField.getString("fieldType").equals(classArray.getJSONObject(k).getString("className"))){
											isValidType = true;
										}
									}
									if(!isValidType && toWrite.isValidType(currentField.getString("fieldType"))){
										isValidType = true;
									}
									if(isValidType = true){
										//handling Access Modifier
										accessModifier = "";
										if(currentField.has("accessModifier")){
											if(toWrite.isValidAccess(currentField.getString("accessModifier"))){
												accessModifier = currentField.getString("accessModifier");
											}
										}
										//handling fieldModifier
										if(currentField.has("fieldModifier")){
											modifierJSONArray = currentField.getJSONArray("fieldModifier");
											modifierArray = new String[modifierJSONArray.length()];
											for(int k = 0;k<=modifierJSONArray.length()-1;k++){
												if(toWrite.isValidModifier(modifierJSONArray.getString(k))){
													modifierArray[k] = modifierJSONArray.getString(k);
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
										isError(toWrite.addField(currentField.getString("fieldType"), currentField.getString("fieldName"), accessModifier, getter, setter, modifierArray));
									}else{
										isError("Error: Invalid fieldType for field "+currentField.getString("fieldName")+", field skipped");
									}
								}else{
									isError("Error: Invalid or missing fieldName, or missing fieldType, field skipped");
								}
							}
						}
						//methods
						if(currentClass.has("methods")){
							methodArray=currentClass.getJSONArray("methods");
							for(int j = 0; j<=methodArray.length()-1;j++){
								isValidType = false;
								currentMethod = methodArray.getJSONObject(j);
								if(currentMethod.has("methodName") && toWrite.isValidName(currentMethod.getString("methodName")) && currentMethod.has("methodType")){
									for(int k = 0; k<=classArray.length()-1;k++){
										if(currentMethod.getString("methodType").equals(classArray.getJSONObject(k).getString("className"))){
											isValidType = true;
										}
									}
									if(!isValidType && toWrite.isValidType(currentMethod.getString("methodType"))){
										isValidType = true;
									}
									if(isValidType = true){
										//handling Access Modifier
										accessModifier = "";
										if(currentMethod.has("accessModifier")){
											if(toWrite.isValidAccess(currentMethod.getString("accessModifier"))){
												accessModifier = currentMethod.getString("accessModifier");
											}
										}
										//handling fieldModifier
										if(currentMethod.has("methodModifier")){
											modifierJSONArray = currentMethod.getJSONArray("methodModifier");
											modifierArray = new String[modifierJSONArray.length()];
											for(int k = 0;k<=modifierJSONArray.length()-1;k++){
												if(toWrite.isValidModifier(modifierJSONArray.getString(k))){
													modifierArray[k] = modifierJSONArray.getString(k);
												}
											}
										}else{
											modifierArray = new String[0];
										}
										if(currentMethod.has("parameters")){
											parameters = currentMethod.getJSONArray("parameters");
										}else{
											parameters = new JSONArray();
										}
										isError(toWrite.addMethod(currentMethod.getString("methodType"), currentMethod.getString("methodName"), accessModifier, parameters, classArray, modifierArray));
									}else{
										isError("Error: Invalid methodType for method "+currentMethod.getString("methodName")+", method skipped");
									}
								}else{
									isError("Error: Invalid or missing methodName, or missing methodType, method skipped");
								}
							}
						}
						
						toWrite.classEnd();
					}else{
						isError("Error: Invalid className or accessModifier, class skipped.");
					}
				}else{
					isError("Error: Missing className or accessModifier, class skipped.");
				}
			}
			//Writing to file
			output = toWrite.getCode();
			try{
			File file = new File (".//codegen//output//"+args[0]+fileExtension);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(output);
			fileWriter.close();
			}catch(java.io.IOException e){
				isError("Error: File could not be written to");
			}
		}
	}
}