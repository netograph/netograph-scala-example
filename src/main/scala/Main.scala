package io.netograph.example

import io.netograph.user.user.{TempCaptureRequest}
import java.util.concurrent.Executor
import io.grpc.{Attributes, CallCredentials, Metadata}


case class IdTokenCredentials(idToken: String) extends CallCredentials {
  override def applyRequestMetadata(requestInfo: CallCredentials.RequestInfo,
                                    appExecutor: Executor,
                                    applier: CallCredentials.MetadataApplier): Unit = {
    appExecutor.execute(() => {
      val authorizationHeaderKey = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)
      val headers = new Metadata()
      headers.put(authorizationHeaderKey, idToken)
      applier.apply(headers)
    })
  }
  override def thisUsesUnstableApi() = ()
}


object Main extends App {
    val NetographToken = "TOKEN"

    val channel = io.grpc.ManagedChannelBuilder.forAddress(
      "grpc.netograph.io", 443,
    ).build
    val blockingStub = io.netograph.user.user.UserGrpc.blockingStub(channel).withCallCredentials(
      IdTokenCredentials(NetographToken)
    )
    val response = blockingStub.tempCapture(TempCaptureRequest(
      urls=Seq("https://google.com"),
    ))
    println(response)
}
