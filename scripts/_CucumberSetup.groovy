// Change default env to test
scriptEnv = "test"

includeTargets << grailsScript("Init")
includeTargets << grailsScript("Bootstrap")
includeTargets << grailsScript("RunApp")
includeTargets << grailsScript("RunWar")

generateLog4jFile = true

target(setup: "The description of the script goes here!") {
   depends(classpath, checkVersion, parseArguments)

   taskdef (name: 'run-cucumber', classname: 'cuke4duke.ant.CucumberTask')
   jar(destfile: "${cucumberPluginDir}/class.jar", basedir: "${grailsSettings.classesDir.path}") {
       include name: "**/*.class"
   }

   def jrubyHome = "${cucumberPluginDir}/lib/.jruby"
   property(name: 'jruby.home', value: jrubyHome)
   path(id: 'jruby.classpath') {
       fileset( dir: "${grailsSettings.grailsHome}/lib") {
           include name: "**/*.jar"
       }

       fileset(dir: "${cucumberPluginDir}") {
           include name: "**/*.jar"
       }
       fileset(dir: "lib") {
           include name: "**/*.jar"
       }
   }
   property(name: 'jruby.classpath', refid: 'jruby.classpath')
   //echo('${jruby.classpath}')
   if (!new File(jrubyHome).isDirectory()) {
       echo("Installing required JRuby gems...")
       taskdef(name:"gem", classname:"cuke4duke.ant.GemTask")
       gem args: "install cuke4duke --version 0.3.0"
   }
}
