package com.mattv.sharding

import akka.actor.{ActorSystem, Props}
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings, ShardRegion}
import com.typesafe.config.ConfigFactory

case class MessageWrapper(id: Int, message: Message)

final case class Get(counterId: Long)
final case class EntityEnvelope(id: Long, payload: Any)

object Application {
  def main(args: Array[String]): Unit = {
    val port: String = args(0)
    println("Starting cluster sharding demo listening on port " + port)

    // set the ports in the config
    val config = ConfigFactory.parseString(
      s"""
        akka.remote.netty.tcp.port=$port
        akka.remote.artery.canonical.port=$port
        """).withFallback(ConfigFactory.load())

    // all actor systems who wish to join this cluster must have the same name
    val system = ActorSystem("ClusterShardingDemo", config)

    ClusterSharding(system).start(
      settings = ClusterShardingSettings(system),
      typeName = ClusteredActor.shardName,
      entityProps = Props[ClusteredActor],
      extractShardId = ClusteredActor.shardResolver,
      extractEntityId = ClusteredActor.idExtractor
    )

    if (port != "2551" && port != "2552") {
      println("start the bot")
      system.actorOf(Props[Bot], "bot")
    }
  }
}
