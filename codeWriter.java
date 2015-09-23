public interface codeWriter(){
	public codeWriter(){
	}
	public String classStart(String className, String security, String[] classType){
	}
	public String classEnd(){
	}
	public String fieldInt(String fieldName, String security, int value, boolean getter, boolean setter, String[] dataType){
	}
	public String fieldDouble(String fieldName, String security, double value, boolean getter, boolean setter, String[] dataType){
	}
	public String fieldLong(String fieldName, String security, long value, boolean getter, boolean setter, String[] dataType){
	}
	public String fieldShort(String fieldName, String security, short value, boolean getter, boolean setter, String[] dataType){
	}
	public String fieldFloat(String fieldName, String security, float value, boolean getter, boolean setter, String[] dataType){
	}
	public String fieldString(String fieldName, String security, String value, boolean getter, boolean setter, String[] dataType){
	}
	public String fieldChar(String fieldName, String security, char value, boolean getter, boolean setter, String[] dataType){
	}
	public String fieldBoolean(String fieldName, String security, boolean value, boolean getter, boolean setter, String[] dataType){
	}
	public String fieldOther(String fieldName, String security, String value, boolean getter, boolean setter, String[] dataType, boolean isValid){
	}
}