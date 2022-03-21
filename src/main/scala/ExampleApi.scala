import ExampleData.{CharacterArgs, CharactersArgs}
import ExampleService.ExampleService
import caliban.GraphQL.graphQL
import caliban.schema.GenericSchema
import caliban.wrappers.ApolloTracing.apolloTracing
import caliban.wrappers.Wrappers._
import caliban.{GraphQL, RootResolver}
import zio.URIO
import zio.clock.Clock
import zio.console.Console
import zio.duration.durationInt
import zio.stream.ZStream

import scala.language.postfixOps

object ExampleApi extends GenericSchema[ExampleService] {

  case class Queries(
    characters: CharactersArgs => URIO[ExampleService, List[ExampleData.Character]],
    character:  CharacterArgs => URIO[ExampleService, Option[ExampleData.Character]]
  )

  case class Mutations(deleteCharacter: CharacterArgs => URIO[ExampleService, Boolean])

  case class Subscriptions(characterDeleted: ZStream[ExampleService, Nothing, String])

//  implicit val roleSchema:           Schema[Any, Role]           = Schema.gen
//  implicit val characterSchema:      Schema[Any, Character]      = Schema.gen
//  implicit val characterArgsSchema:  Schema[Any, CharacterArgs]  = Schema.gen
//  implicit val charactersArgsSchema: Schema[Any, CharactersArgs] = Schema.gen
  val api: GraphQL[Console with Clock with ExampleService] =
    graphQL(
      RootResolver(
        Queries(
          args => ExampleService.getCharacters(args.origin),
          args => ExampleService.findCharacter(args.name)
        ),
        Mutations(args => ExampleService.deleteCharacter(args.name)),
        Subscriptions(ExampleService.deletedEvents)
      )
    ) @@
      maxFields(200) @@ // query analyzer that limit query fields
      maxDepth(30) @@ // query analyzer that limit query depth
      timeout(3000.millis) @@ // wrapper that fails slow queries
      printSlowQueries(500 millis) @@ // wrapper that logs slow queries
      printErrors @@ // wrapper that logs errors
      apolloTracing // wrapper for https://github.com/apollographql/apollo-tracing
}
