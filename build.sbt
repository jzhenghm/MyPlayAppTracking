import play.Play._
import ByteConversions._

name := """myplay"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

//sbt bundle required to run it on sbt-conductr-sandbox	
SandboxKeys.imageVersion in Global := "1.1.2"
	
BundleKeys.nrOfCpus := 1.0
BundleKeys.memory := 64.MiB
BundleKeys.diskSpace := 10.MB
BundleKeys.roles := Set("web")
BundleKeys.endpoints := Map("my-app" -> Endpoint("http", services = Set(URI("http://:9000"))))
javaOptions in Bundle ++= Seq("-Dhttp.address=$MY_APP_BIND_IP", "-Dhttp.port=$MY_APP_BIND_PORT")
	
	
	

//resolvers += "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases"
resolvers += "typesafe-releases" at "http://repo.typesafe.com/typesafe/maven-releases"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "1.1.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.0",
  "com.h2database" % "h2" % "1.4.177",
  ws,
 // jdbc,
 // cache,  
  specs2 % Test,
  "com.typesafe.conductr" %% "play24-conductr-bundle-lib" % "1.4.0"
)     



//compile in Test <<= PostCompile(Test)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true




