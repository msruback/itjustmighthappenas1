/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;





public class CPlusPlusWriter implements codeWriter {
    String output="";
    
    
    public String classStart(String className, String accessModifier, boolean isAbstract, String classModifier){
            //For c++, if a class is supposed to be abstract, each field will have
            //the virtual keyword to denote it as such

            output+=className+" ";
            output+="{";
            if(accessModifier=="private")
                output+="private: \n";
            else output+="public: \n";
            return "ERROR";
            
    
    }
    
    public void classEnd(){
        output+="}";
    }
    public static void main(String[] args) {
        
        //All this is for testing output
        boolean isAbstract=true;
        CPlusPlusWriter test=new CPlusPlusWriter();
        
        test.classStart("CLASSNAME","private", isAbstract, "NONE");
        test.classEnd();

        System.out.println(test.output);
        System.out.println();
        
    }
}
