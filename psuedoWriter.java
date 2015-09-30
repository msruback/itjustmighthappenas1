//Written by Matthew Rubacky

package codegen;

public class pseudoWriter implements codeWriter{
	private String code;
	public pseudoWriter(){
		code = '';
	};
	
	public String classStart(String className, String accessModifier, boolean isInterface, String classModifier[]){
		code +='\n'+accessModifier+' ';
		for(int i=0;i<=classModifier.length()-1;i++){
			code += classModifier[i]+' ';
		}
		if(istInterface){
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