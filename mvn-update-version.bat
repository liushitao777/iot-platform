rem set MAVEN_OPTS=-Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=3000
mvn versions:set -DnewVersion=1.2.0 -DgenerateBackupPoms=false --settings D:\software\MavenRepository\settings-zdkj.xml
mvn versions:update-child-modules -DgenerateBackupPoms=false --settings D:\software\MavenRepository\settings-zdkj.xml
