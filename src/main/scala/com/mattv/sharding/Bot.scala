package com.mattv.sharding

import akka.actor.Actor
import akka.cluster.sharding.ClusterSharding
import scala.concurrent.duration._

object Bot {
  private case object Tick

  val randomIds = Array(
    "red",
    "orange",
    "yellow",
    "green",
    "blue",
    "indigo",
    "violet"
  )
}

class Bot extends Actor {
  import Bot._
  import context.dispatcher

  val clusteredActors = ClusterSharding(context.system).shardRegion(ClusteredActor.shardName)

  val tickTask = context.system.scheduler.schedule(1.seconds, 1.seconds, self, Tick)

  override def preStart(): Unit = {
    println("BOT prestart")
  }

  override def postStop(): Unit = {
    println("BOT postStop")
    super.postStop()
  }

  def receive = {
    case Tick =>
      // pick a random id
      val id = randomIds(scala.util.Random.nextInt(randomIds.length))

      // say hello
      clusteredActors ! Message(id, "Hello!")
    case message => println(message)
  }

}
