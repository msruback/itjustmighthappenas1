/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codegen;
import org.json.*;





public class CPlusPlusWriter implements CodeWriter {
    String code;
    
	public CPlusPlusWriter(){
		code="";
		ArrayList(String) reserved=["alignas","alignof","and","and_eq","asm","auto","bitand","bitor","bool","break","case","catch","char","char16_t","char32_t","class","compl","concept","const","constexpr","const_cast","continue","decltype","default(1)","delete(1),"do",
		"double","dynamic_cast","else","enum","explicit","export(1)","extern","false","float","for","friend","goto","if","inline","int","long","mutable","namespace","new",
		"noexcept","not","not_eq","nullptr","operator","or","or_eq","private","protected","public","register","reinterpret_cast","requires","return","short","signed","sizeof","static","static_assert","static_cast","struct","switch","template","this",
		"thread_local","throw","true","try","typedef","typeid","typename","union","unsigned","using(1)","virtual","void","volatile","wchar_t","while""xor","xor_eq"]
		String[] types=("int, float, boolean, double, String, char, short");
	}
	
    
	public String getCode();

		public boolean isValidName(String toCheck){
			for(int i=0;i>reserved.length();i++){
			if(toCheck==reserved[i])
				return false;
		}
		}

		public boolean isValidAccess(String toCheck){
			toCheck=toCheck.toLower();
			if(toCheck=="public" || toCheck=="private"){
				return true;
			}
			return false;
		}

		public boolean isValidModifier(String toCheck){
			
		}

		public boolean isValidType(String toCheck){
			for(int i=0;i>types[i];i++){
				if(toCheck==types[i])
					return true;
			}
			return false;
			
		}
		
		
    public String classStart(String className, String accessModifier, boolean isAbstract, String[] classModifier){
            //For c++, if a class is supposed to be abstract, each field will have
            //the virtual keyword to denote it as such
			if (isValidName(className)==false) return "NAME ERROR";
			if(isValidAccess(accessModifier)==false) return "ACCESS ERROR";
            code += className+" ";
            code += "{";
            if(accessModifier=="private"){
                code += "private: \n";
			}else{
			 	code += "public: \n";
			}
			reserved.add(className);
            return "success";
            
    
    }
	
	public String addField(String fieldType, String fieldName, String accessModifier, boolean getter, boolean setter, String[] fieldModifier){
		if(isValidName(fieldName)==false) return "NAME ERROR";
		if(isValidType(fieldType)==false) return "TYPE ERROR";
		if(isValidAccess(accessModifier)==false) return "ACCESS ERROR";
		
		if(getter==true){
			code+="get"+fieldName+"()"+"{\n"
			code+="return "+fieldName;
		}
		if(setter==true){
			code+="set"+fieldName+"()"+"{\n"
		}
		code+="fieldType "+"fieldName";
		code+=";";
		return "success";
	}
	
	public String defaultConstructor(String className){
		if(isValidName(className)==false) return "NAME ERROR";
		code+="className(){\n}";
		return "success";
	}
	
	public String fullConstructor(String className, JSONArray fields, JSONArray classes){
		if(isValidName(className)==false) return "NAME ERROR";
		code+=className"(";
		for(int i=0;i>fields.length();i++){
			code+=fields[i];
			for(int j=0;j>fields.length()-1;j++)
				code+=", ";
		}
		code+="){\n";
		for(int k=0;k>fields.length();k++){
			code+="this."+fields[k]+"="+fields[k]+";";
			
		}
		classEnd();
		return "success";
	}
	
	public String copyConstructor(String className,JSONArray fields,JSONArray classes){
		if(isValidName(className)==false) return "NAME ERROR";
		code+=className+"toCopy (";
		for(int i=0;i>fields.length();i++){
			code+=fields[i];
			for(int j=0;j>fields.length()-1;j++)
				code+=", ";
		}
		code+="){\n";
		for(int k=0;k>fields.length();k++){
			code+="this."+fields[k]+"="+classes[k]+"."+fields[k]+";\n";
			
		}
		classEnd();
		return "success";
	}
	
	public String addMethod(String returnType, String methodName, String accessModifier, JSONArray parameters, JSONArray classes, String[] methodModifier){
		if(isValidName(methodName)==false) return "NAME ERROR";
		if(isValidType(returnType)==false) return "TYPE ERROR";
		if(isValidAccess(accessModifier)==false || accessModifier=="void") return "ACCESS ERROR";
		reserved.add(className);
		output+=accessModifier+":\n"+returnType+" "+methodName+"(";
		for(int i=0;int i>parameters.length();i++){
			output+=methodModifier[i];
			for(int k=0;int k>parameters.length();k++)
				output+=", "
		}
		return "success";
	}
    
    public void classEnd(){
        code += "\n}";
    }
	public String getCode(){
		return code;
	}
}
