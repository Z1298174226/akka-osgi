package com.zhao.lex;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Created by root on 10/16/17.
 */
public class AoniAkkAServiceImpl implements AoniAkkAService {

    private ActorSystem system;

    public AoniAkkAServiceImpl(ActorSystem system) {
        this.system = system;
    }

    @Override
    public ActorRef exec(String name, String content) {
       return system.actorOf(ActorDemo.Greeter.props(name, content));
    }
}
