package com.mattv.sharding

import akka.actor.{Actor, Props}
import akka.cluster.sharding.ShardRegion

final case class Message(id: String, msg: String)

object ClusteredActor {
  def props = Props(new ClusteredActor)

  val shardName: String = "ClusteredActor"

  val numberOfShards = 100

  val idExtractor: ShardRegion.ExtractEntityId = {
    case message: Message => (message.id, message.msg)
  }

  val shardResolver: ShardRegion.ExtractShardId = {
    case msg: Message => (math.abs(msg.id.hashCode) % numberOfShards).toString
  }
}

class ClusteredActor extends Actor {
  def receive: Receive = {
    case message => println(message + " received by " + self.path.name)
  }

  override def preStart(): Unit = {
    println("ClusteredActor prestart " + self.path.name)
  }

  override def postStop(): Unit = {
    println("ClusteredActor postStop " + self.path.name)
    super.postStop()
  }

}
