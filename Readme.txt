Compile instructions:
Go to the parent folder of the codegen and run "javac -cp ./:codegen/json.jar codegen/CodeGenerator.java"

Run instructions:
Go to the parent folder of the codegen folder and run "java -cp ./:codegen/json.jar codegen.Codegenerator $fileName $language"

$fileName should be the name of the .txt file in the input folder that you want to use, and $language should be the language you want to write to.

Supported Values for language currently are (case does not matter):
"pseudo",
"java",
"c++"
