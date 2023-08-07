package com.yomahub.liteflow;

import com.yomahub.liteflow.builder.el.ELBus;

public class TestMain {

    public static void main(String[] args) {
        String el = ELBus.then("a", ELBus.when("b", "c").any(true), ELBus.ifOpt("d", "e")).toEL();
        System.out.println(el);

        el = ELBus.then("a")
             .then(ELBus.when("b", "c"))
             .then(ELBus.ifOpt("d", "e", "g")).toEL();
        System.out.println(el);

        el = ELBus.when(ELBus.node("a").tag("jack"))
                  .when(ELBus.then("b", "c"))
                  .when(ELBus.ifOpt("d").trueOpt(ELBus.then("e", "f"))).toEL();
        System.out.println(el);
    }
}
