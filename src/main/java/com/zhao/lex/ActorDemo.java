package com.zhao.lex;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author XDZ
 * Created by root on 10/11/17.
 */
public class ActorDemo {

    private static final Logger LOG = LoggerFactory.getLogger(ActorDemo.class);

    public static void exec() {
        LOG.info("=========AoniAkkA TEST============");
        ActorSystem system = ActorSystem.create("actor-demo", ConfigFactory.load());
        ActorRef bob = system.actorOf(Greeter.props("Bob", "Howya doing"));
        ActorRef alice = system.actorOf(Greeter.props("Alice", "Happy to meet you"));
        ActorRef mike = system.actorOf(Greeter.props("Mike","Are you Ready"));
        mike.tell(new Greet(mike), ActorRef.noSender());
        bob.tell(new Greet(alice), ActorRef.noSender());
        alice.tell(new Greet(bob), ActorRef.noSender());
        alice.tell(new Greet(alice), ActorRef.noSender());
        alice.tell(new Greet(mike), ActorRef.noSender());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        system.shutdown();
    }


    public static class Greet {
        public final ActorRef target;
        public Greet(ActorRef target) {
            this.target = target;
        }
    }

    private static class TellName {
        public final String name;
        public TellName(String name) {
            this.name = name;
        }
    }

    private static Object AskName = new Object();

    public static class Greeter extends UntypedActor {
        private final String myName;
        private final String greeting;

        Greeter(String name, String greeting) {
            this.myName = name;
            this.greeting = greeting;
        }

        public static Props props(String name, String greeting) {
            return Props.create(Greeter.class, name, greeting);
        }
        @Override
        public void onReceive(Object message) throws Exception {
            if(message instanceof Greet) {
                ((Greet) message).target.tell(AskName, self());
            }else if(message == AskName) {
                sender().tell(new TellName(myName), self());
            }else if(message instanceof TellName) {
                LOG.info("=====================AKKADEMO===================");
                LOG.info(greeting + ", " + ((TellName)message).name);
                System.out.println(greeting + ", " + ((TellName)message).name);
            }
        }
    }

}
