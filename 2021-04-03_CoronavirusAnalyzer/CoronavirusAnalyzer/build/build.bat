dir /s /B ..\src\*.java  > sources.txt

javac -cp ../lib/batik-all-1.14.jar;../lib/xml-apis-ext-1.3.04.jar  -d ../class  @sources.txt

xcopy /s /y ..\res ..\class

jar -cfe CoronavirusAnalyzer.jar CoronavirusAnalyzer.CoronavirusAnalyzer  -C ..\class .
