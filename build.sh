mvn clean install
mvn exec:exec "-Dexec.args=-classpath %classpath -Xms512m -Xmx2048m mypackage.project.Assignment" -Dexec.executable=java -Dexec.classpathScope=runtime process-classes
