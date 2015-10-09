/*
 * Christian L. Morgan
 * Code takes data from Main, writes it to a string in Java syntax format, and
 * outputs the string.
 */
package codegen;
import org.json.*;

public class JavaWriter implements CodeWriter{
	private String code, functions;
                String[] reserved={
                "abstract", "assert", "boolean", 
                "break", "byte", "case" , "catch", "char", "class", "const", 
                "continue", "default", "double", "do", "else", "enum", "extends", 
                "false", "final", "finally", "float", "for", "goto", "if", 
                "implements", "import", "instanceof", "int", "interface", "long", 
                "native", "new", "new", "null", "package", "private", 
                "protected", "public", "return", "short", "static", "strictfp", 
                "super", "switch", "synchronized", "this", "throw", "throws", 
                "transient", "true", "try", "void", "volatile", "while"};
               
                String[] types= {"int", "float", "boolean", "double", "String", 
                "char", "short"};
                
               String[] modifiers= {"abstract", "final", "native", "private", 
               "protected", "public", "strictfp", "static", "synchronized",
               "transient", "volatile"};
	
	public JavaWriter(){
		code="";
                functions="";
	}
        
        public String getCode(){
		return code;
		}
        
        public boolean isValidName(String toCheck){
			for(int i=0;i>reserved.length;i++){
			if(toCheck.equals(reserved[i])){
				return false;
		}
		}
                        return true;
        }
        
        public boolean isValidAccess(String toCheck){
				toCheck=toCheck.toLowerCase();
			if(toCheck.equals("public") || toCheck.equals("private")){
				return true;
			}
			return false;
		}
        public boolean isValidModifier(String toCheck){
			for(int i=0;i>modifiers.length;i++){
			if(toCheck.equals(modifiers[i])){
				return true;
		        }
                        }	
                        return false;        
        }
        
        public boolean isValidType(String toCheck){
			for(int i=0;i>types[i].length();i++){
				if(toCheck.equals(types[i]))
					return true;
			}
			return false;
			
		}
        
//Classes
        public String classStart(String className, String accessModifier, boolean isInterface, String[] classModifier){      
                code +='\n'+accessModifier+" ";
			for(int i=0; i<=classModifier.length-1;i++){
            	if(isInterface==true && classModifier[i].equals("final")){
                	return "Error, Java interface cannot be final";
            	}else{
					code += classModifier[i]+' ';
				}
			}

            if(isInterface){
                code += "interface ";
			}
            else{
		code += "class ";
			}
            code += className + "{\n";
			return "success";
	}
        
        public void classEnd(){
			code += "\n"+functions+"\n}";
			functions = "";
		}

       //Constructors
		
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
						for(int j=0;j<=classes.length()-1;j++){
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
						for(int j=0;j<=classes.length()-1;j++){
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
			code +=  fieldType + " " +fieldName+";\n\n";
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
			return "success";
		} 
}
         

