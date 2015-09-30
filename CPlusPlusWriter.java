/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codegen;





public class CPlusPlusWriter implements CodeWriter {
    String code;
    
	public CPlusPlusWriter(){
		code="";
	}
    
    public String classStart(String className, String accessModifier, boolean isAbstract, String[] classModifier){
            //For c++, if a class is supposed to be abstract, each field will have
            //the virtual keyword to denote it as such

            code += className+" ";
            code += "{";
            if(accessModifier=="private"){
                code += "private: \n";
			}else{
			 	code += "public: \n";
			}
            return "success";
            
    
    }
    
    public void classEnd(){
        code += "}";
    }
	public String getCode(){
		return code;
	}
}
