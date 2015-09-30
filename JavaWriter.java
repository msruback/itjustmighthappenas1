/*
 * Christian L. Morgan
 * Code takes data from Main, writes it to a string in Java syntax format, and
 * outputs the string.
 */
package woah;

public class JavaWriter implements codeWriter {

 private String output="";
   
    public String classStart(String className, String accessModifier, boolean isInterface, String classModifier){      
            output+=accessModifier+" ";

            if(classModifier!="NONE"){
                output+=classModifier+" ";
               }

            if(isInterface){
                output+="interface ";}
            
            if(isInterface=true && accessModifier=="final"){
                return "Error, Java interface cannot be final";
            }
            else{
            output+=className+" ";
            output+="{";
            return "No errors";}           
}
    
    public String classEnd(){
        output+="}";
        return output;
    }
}
