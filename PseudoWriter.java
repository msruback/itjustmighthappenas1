//Written by Matthew Rubacky

package codegen;
import java.util.regex.*;
import org.json.*;

public class PseudoWriter implements CodeWriter{
	private String code, functions;
	
	//Utility
		
		public PseudoWriter(){
			code = "";
			functions = "";
		};
	
		public String getCode(){		
			return code;
		};
	
		public boolean isValidName(String toCheck){
			Pattern validName = Pattern.compile("\\w*");
			if(validName.matcher("toCheck").matches()){
				return true;
			}else{
				return false;
			}
		}
	
		public boolean isValidAccess(String toCheck){
			Pattern validName = Pattern.compile("\\w*");
			if(validName.matcher("toCheck").matches()){
				return true;
			}else{
				return false;
			}
		}
	
		public boolean isValidModifier(String toCheck){
			Pattern validName = Pattern.compile("\\w*");
			if(validName.matcher("toCheck").matches()){
				return true;
			}else{
				return false;
			}
		}
	
		public boolean isValidType(String toCheck){
			Pattern validName = Pattern.compile("\\w*");
			if(validName.matcher("toCheck").matches()){
				return true;
			}else{
				return false;
			}
		}
		
	//Class
	
		public String classStart(String className, String accessModifier, boolean isInterface, String classModifier[]){
			code +='\n'+accessModifier+" ";
			for(int i=0;i<=classModifier.length-1;i++){
				code += classModifier[i]+" ";
			}
			if(isInterface){
				code += "interface ";
			} else {
				code += "class ";
			}
			code += className + "{\n\n";
			return "success";
		};
		
		public void classEnd(){
			code += "\n"+functions+"\n}";
			functions = "";
		};
		
		//Constructor
		
			public String copyConstructor(String className,JSONArray fields,JSONArray classes){
				functions+="public "+className+"("+className+" toCopy){\n";
				JSONObject currentField;
				boolean isValidType;
				for(int i=0;i<=fields.length()-1;i++){
					isValidType = false;
					currentField = fields.getJSONObject(i);
					if(this.isValidType(currentField.getString("fieldType"))){
						isValidType = true;
					}else{
						for(int j=0;j<=classes.length();j++){
							if(currentField.getString("fieldType").equals(classes.getJSONObject(j).getString("className"))){
								isValidType = true;
							}
						}
					}
					if(isValidType){
						if(this.isValidName(currentField.getString("fieldName"))){
							functions += currentField.getString("fieldName")+" = toCopy."+currentField.getString("fieldName")+";\n";
						}
					}
				}
				functions += "}\n\n";
				return "success";
			}
		
			public String defaultConstructor(String className){
				functions+="public "+className+"();\n\n";
				return "success";
			}
		
			public String fullConstructor(String className,JSONArray fields,JSONArray classes){
				functions+="public "+className+"(";
				String setting="";
				JSONObject currentField;
				boolean isValidType;
				for(int i=0;i<=fields.length()-1;i++){
					isValidType = false;
					currentField = fields.getJSONObject(i);
					if(this.isValidType(currentField.getString("fieldType"))){
						isValidType = true;
					}else{
						for(int j=0;j<=classes.length();j++){
							if(currentField.getString("fieldType").equals(classes.getJSONObject(j).getString("className"))){
								isValidType = true;
							}
						}	
					}
					if(isValidType){
						if(this.isValidName(currentField.getString("fieldName"))){
							functions += currentField.getString("fieldType")+" set"+currentField.getString("fieldName");
							if((i+1)<fields.length()){
								functions +=", ";
							}
							setting += currentField.getString("fieldName")+" = set"+currentField.getString("fieldName")+";\n";
						}
					}
				}
				functions +="){\n"+ setting+"}\n\n";
				return "success";
			}
		
	//Fields
		public String addField(String fieldType, String fieldName, String accessModifier, boolean getter, boolean setter, String[] fieldModifier){
			code += accessModifier+" ";
			for (int i=0; i<=fieldModifier.length-1;i++){
				code += fieldModifier[i]+" ";
			}
			code += fieldName+";\n\n";
			if(getter){
				functions += "public " + fieldType + " get" +fieldName+"(){\n";
				functions += "return "+fieldName+";\n}\n\n";
			}
			if(setter){
				functions += "public void set" +fieldName+"("+fieldType+" toSet){\n";
				functions += fieldName+" = toSet;\n}\n\n";
			}
			return "success";
		}
	//Methods
		public String addMethod(String returnType, String methodName, String accessModifier, JSONArray parameters,JSONArray classes, String[] methodModifier){
			JSONObject currentParam;
			boolean isValidType;
			functions += accessModifier+" ";
			for (int i=0; i<=methodModifier.length-1;i++){
				functions += methodModifier[i]+" ";
			}
			functions += returnType + " " + methodName + "(";
			for (int i=0; i<=parameters.length()-1;i++){
				currentParam = parameters.getJSONObject(i);
				isValidType = false;
				if(this.isValidType(currentParam.getString("fieldType"))){
					isValidType = true;
				}else{
					for(int j=0;j<=classes.length();j++){
						if(currentParam.getString("fieldType").equals(classes.getJSONObject(j).getString("className"))){
							isValidType = true;
						}
					}
					if(isValidType){
						if(this.isValidName(currentParam.getString("fieldName"))){
							functions += currentParam.getString("fieldType") + " " + currentParam.getString("fieldName") + ", ";
						}
					}
				}
			}
			functions += ");\n\n";
			return functions;
		}
}