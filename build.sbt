name := "ClusterShardingDemo"

version := "1.0"

scalaVersion := "2.12.1"

val akkaVersion = "2.5.12"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"             % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster"           % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-sharding"  % akkaVersion,
  "com.typesafe"      %  "config"                 % "1.3.1"
)