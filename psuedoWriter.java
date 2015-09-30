//Written by Matthew Rubacky

package codegen;

public class PseudoWriter implements CodeWriter{
	private String code;
	public PseudoWriter(){
		code = "";
	};
	
	public String classStart(String className, String accessModifier, boolean isInterface, String classModifier[]){
		code +='\n'+accessModifier+" ";
		for(int i=0;i<=classModifier.length-1;i++){
			code += classModifier[i]+" ";
		}
		if(isInterface){
			code += "interface ";
		} else {
			code += "class";
		}
		code += className + "{\n";
		return "success";
	};
	public void classEnd(){
		code += "\n}";
	};
	public String getCode(){
		return code;
	};
}