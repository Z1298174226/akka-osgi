package com.zhao.lex;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.osgi.ActorSystemActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author XDZ
 * Created by root on 10/9/17.
 */
//public class Activator  extends AbstractBindingAwareConsumer implements AutoCloseable {
public class Activator extends ActorSystemActivator implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(Activator.class);
    private AoniAkkAService akkaService;
    private ActorRef mike;
    private ActorRef bob;
    private ActorRef alice;

    @Override
    public void configure(BundleContext context, ActorSystem system) {
        LOG.info("Core bundle configured");
        bob = system.actorOf(ActorDemo.Greeter.props("Bob", "Howya doing"));
        alice = system.actorOf(ActorDemo.Greeter.props("Alice", "Happy to meet you"));
        mike = system.actorOf(ActorDemo.Greeter.props("Mike", "Are you Ready"));
        registerGreetService(context, system);
        mike.tell(new ActorDemo.Greet(mike), ActorRef.noSender());
        bob.tell(new ActorDemo.Greet(alice), ActorRef.noSender());
        alice.tell(new ActorDemo.Greet(bob), ActorRef.noSender());
        alice.tell(new ActorDemo.Greet(alice), ActorRef.noSender());
        alice.tell(new ActorDemo.Greet(mike), ActorRef.noSender());
     //   system.shutdown();
        LOG.info("akka service registered");
    }

    public void registerGreetService(BundleContext context, ActorSystem system) {
        akkaService = new AoniAkkAServiceImpl(system);
        context.registerService(AoniAkkAService.class, akkaService, null);
        context.registerService(ActorSystem.class, system, null);
    }

    @Override
    public void close() {
        LOG.info("close passing");
    }


}