package codegen;
public interface CodeWriter{
	//Utility Methods
		//Returns Code
		public String getCode();
		//Returns whether or not the passed string is a valid name
		public boolean isValidName(String toCheck);
		//Returns whether or not the passed	string is a valid access modifier
		public boolean isValidAccess(String toCheck);
		//Returns whether or not the passed string is a valid modifier
		public boolean isValidModifier(String toCheck);
		//Returns whether or not the passed string is a valid type
		public boolean isValidType(String toCheck);
	//Class Methods
		//Writes the class header and adds a constructor if needed, Returns an error message if data passed is not valid
		public String classStart(String className, String accessModifier, boolean isInterface, String[] classModifier);
		//Ends the class, for example, in java this will add a }
		public void classEnd();
		//Constructor Methods
			//Write a copy constructor
			public String copyConstructor(String className,JSONArray fields,JSONArray classes);
			//Write a default constructor
			public String defaultConstructor(String className);
			//Write a fully initializing constructor
			public String fullConstructor(String className,JSONArray fields,JSONArray classes);
	//Field Method
		//Writes a field
		public String addField(String fieldType, String fieldName, String accessModifier, boolean getter, boolean setter, String[] fieldModifier);
	//Method Method
		//Writes a method
		public String addMethod(String returnType, String methodName, String accessModifier, JSONArray parameters, JSONArray classes, String[] methodModifier);
}