# Akka Cluster Sharding Demo

This is a simple demo of Akka Cluster Sharding. It demonstrates how Cluster Sharding distributes actors in the cluster and how to locate and communicate with specific actors spread across the cluster.

# Running the Demo

To see the demo in action, you will need to spin up 3 instances of this demo to form a 3 node cluster.  One of these nodes will have a bot running on it that randomly sends messages to different actors across the cluster.  When an actor recieves a message it will print confirmation to the stdout on the machine on which it is located.  This will allow you to see where the actors are located within the cluster.

Open 3 consoles.  In one console type:
```sbt "run 2551"```

In the other console type:
```sbt "run 2552"```

The third console will contain the instance with the bot.  To start it type:
```sbt "run 2553"```

Watch the stdout of each of these consoles to see the actors receiving their messages.  If you kill one of the first 2 intances you will see akka redistribute the actors across the remaining nodes.

