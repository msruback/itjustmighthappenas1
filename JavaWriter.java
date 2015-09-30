/*
 * Christian L. Morgan
 * Code takes data from Main, writes it to a string in Java syntax format, and
 * outputs the string.
 */
package codegen;

public class JavaWriter implements CodeWriter{
	private String code;
	
	public JavaWriter(){
		code="";
	}
	
	public String classStart(String className, String accessModifier, boolean isInterface, String[] classModifier){      
            code +=accessModifier+" ";
			for(int i=0; i<=classModifier.length-1;i++){
            	if(isInterface=true && classModifier[i]=="final"){
                	return "Error, Java interface cannot be final";
            	}else{
					code += classModifier[i]+' ';
				}
			}

            if(isInterface){
                code += "interface ";
			}
            code += className+" ";
        	code += "{";
           	return "No errors";     
	}
    
    public void classEnd(){
        code+="}";
    }
	
	public String getCode(){
		return code;
	}
}
