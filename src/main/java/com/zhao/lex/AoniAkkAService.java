package com.zhao.lex;

import akka.actor.ActorRef;

/**
 * Created by root on 10/16/17.
 */
public interface AoniAkkAService {
    public ActorRef exec(String name, String content);
}
