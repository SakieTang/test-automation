# test-automation
## Mac
**Java VM 1.8+, and do not use 9.0 or 10.0
**Java version 1.8+

## iOS
**Xcode :** 9.2</br>
**default simulator :** iPhone X - 11.2</br>

## Android
**emulator :** Pixel2_api27

## Appium
**Appium client :** 1.4.0</br>
**Appium server :** 1.7.2</br>

## running environment
edit "ListOfUser.yaml" file (src/main/resources/testData/ListOfUser.yaml)</br>
Setting TestNG configuration in local : -DPropertyManager.file=</br>

## note
[Automation Testing Cases](https://paper.dropbox.com/doc/Automation-Testing-Cases-pPxUXk3VKPmPkW8GmQuVP)</br>
<a href="https://paper.dropbox.com/doc/Automation-Training-fxptCMuRYJqWviBpEnH5T">Automation Training</a>

## report
**daily report :** https://s3.us-east-2.amazonaws.com/tact-automation-reports/yyyymmdd/report.html</br>

## run project from cmd
1. mvn install -DskipTests </br>
2. mvn clean </br>
3. mvn compile </br>
4. **need to start selenium server and make sure the chromedriver.exe is in the same dir as selenium server </br>
   java -jar selenium-server-standalone-3.9.1.jar -port 4723 </br>
5. iOS        : mvn test / mvn test -DsuiteXmlFile=testNG.xml </br>
   Android    : mvn test -DsuiteXmlFile=testNGAndroid.xml </br>
   ai-Web,iOS : mvn test -DsuiteXmlFile=testNGAIWeb.xml </br>
OR</br>
   iOS        : mvn install -DsuiteXmlFile=testNG.xml </br>
   Android    : mvn install -DsuiteXmlFile=testNGAndroid.xml </br>
   ai-Web,iOS : mvn install -DsuiteXmlFile=testNGAIWeb.xml </br>