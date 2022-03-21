import ExampleService.ExampleService
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import caliban.AkkaHttpAdapter
import sttp.tapir.json.circe._
import zio.Runtime
import zio.clock.Clock
import zio.console.Console
import zio.internal.Platform

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object ExampleApp extends App {
  implicit val system = ActorSystem()
  implicit val executionContext: ExecutionContext = system.dispatcher

  implicit val runtime: Runtime[ExampleService with Console with Clock] =
    Runtime.unsafeFromLayer(
      ExampleService.make(ExampleData.sampleCharacters) ++ Console.live ++ Clock.live,
      Platform.default
    )

  val interpreter = runtime.unsafeRun(ExampleApi.api.interpreter)

  val route =
    path("graphql") {
      AkkaHttpAdapter.makeHttpService(interpreter)
    } ~ path("graphiql") {
      getFromResource("graphiql.html")
    }

  val bindingFuture = Http().newServerAt("localhost", 8088).bind(route)
  println(s"Server online at http://localhost:8088/\nPress RETURN to stop...")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}
