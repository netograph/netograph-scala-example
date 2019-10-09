scalaVersion := "2.13.1"
name := "netograph"
organization := "io.netograph.example"
version := "0.1"

PB.targets in Compile := Seq(
    scalapb.gen() -> (sourceManaged in Compile).value
)
PB.protoSources in Compile := Seq(
    sourceDirectory.value / "/main/protobuf/ngapi/dsetapi",
    sourceDirectory.value / "/main/protobuf/ngapi/userapi"
)

libraryDependencies ++= Seq(
    "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
)
libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"

