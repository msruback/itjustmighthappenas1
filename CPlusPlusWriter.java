/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codegen;





public class CPlusPlusWriter implements codeWriter {
    String code;
    
	public CPlusPlusWriter(){
		code='';
	}
    
    public String classStart(String className, String accessModifier, boolean isAbstract, String[] classModifier){
            //For c++, if a class is supposed to be abstract, each field will have
            //the virtual keyword to denote it as such

            output+=className+" ";
            output+="{";
            if(accessModifier=="private"){
                output+="private: \n";
			}else{
			 	output+="public: \n";
			}
            return "success";
            
    
    }
    
    public void classEnd(){
        output+="}";
    }
	public String getCode(){
		return code;
	}
}
