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

//        el = ELBus.when(ELBus.node("a").tag("jack"))
//                  .when(ELBus.then("b", "c"))
//                  .when(ELBus.ifOpt("d")).toEL();
//        System.out.println(el);

        /*
        THEN("a", WHEN("b", "c"), THEN("d", WHEN("e", "f")), "g")
         */
        el = ELBus.then("a",
                ELBus.when("b").when("c"),
                ELBus.then("d").then(ELBus.when("e", "f")),
                ELBus.node("g")).toEL(true);
        System.out.println(el);
        System.out.println("");
        /*
        WHEN(THEN("a", "b"), THEN(WHEN("c", "d"), "e"), "f")
         */
        el = ELBus.when(ELBus.then("a", "b"),
                ELBus.then(ELBus.when("c", "d"), "e"),
                "f").toEL(true);
        System.out.println(el);

        /*
        THEN(WHEN("a", THEN("b", "c")), WHEN(THEN("d", "e"), "f", "g"), "h")
         */
        el = ELBus.then(ELBus.when("a", ELBus.then("b").then("c")),
                ELBus.when(ELBus.then("d", "e")).when("f", "g").id("WhenId").tag("WhenTag").id("changeId"),
                "h").tag("ThenTag").toEL(true);
        System.out.println(el);

//        // THEN(a, OR(b, c, d))
//        el = ELBus.then(
//                "a",
//                ELBus.or("b", "c", "d")
//        ).toEL(true);
//        System.out.println(el);
//
        // switch
        /*
        THEN(SWITCH(x).TO(THEN(a, b), WHEN(c, d, e)), WHEN(f, g), h)
         */
        el = ELBus.then(ELBus.switchOpt("x")
                        .to(ELBus.then("a", "b").tag("tag1"))
                        .to(ELBus.when("c", "d", "e").id("id2").tag("tag2")),
                ELBus.when("f", "g"), "h").toEL(true);
        System.out.println(el);

        /*
        WHEN(SWITCH(x).TO(THEN(a, b, c), WHEN(d, e)), THEN(WHEN(f, SWITCH(y).TO(g, h)), i), j)
         */
        el = ELBus.when(ELBus.switchOpt("x")
                .to(ELBus.then("a", "b").then("c")).to(ELBus.when("d").when("e")),
                ELBus.then(ELBus.when("f", ELBus.switchOpt("y").to("g", "h")), "i"))
                .when("j").toEL(true);
        System.out.println(el);

        /*
        THEN(SWITCH(x).TO(THEN(a, b), WHEN(c, d, e)).DEFAULT(f), WHEN(g, h, i), j)
         */
        el = ELBus.then(ELBus.switchOpt("x")
                .to(ELBus.then("a", "b"))
                .to(ELBus.when("c", "d").when("e"))
                .defaultOpt("f"),
                ELBus.when("g").when("h").when("i"))
                .then("j").toEL(true);
        System.out.println(el);

        /*
        THEN(WHEN(SWITCH(x).TO(a, b, c).DEFAULT(d), THEN(e, f)), WHEN(g, SWITCH(y).TO(WHEN(h, i), j).DEFAULT(k), l), m)
         */
        el = ELBus.then(
                ELBus.when(
                        ELBus.switchOpt("x")
                                .to("a").to("b").to(ELBus.node("c"))
                                .defaultOpt(ELBus.node("d")),
                        ELBus.then("e", ELBus.node("f"))
                ),
                ELBus.when("g",
                        ELBus.switchOpt("y")
                                .to(ELBus.when("h", "i"), "j")
                                .defaultOpt("k"),
                        "l")
        ).then("m").toEL(true);
        System.out.println(el);

        /*
        SWITCH(x).TO(
          THEN(a, b),
          WHEN(c, d, e),
          THEN(f, g, h)
        ).DEFAULT(i)
         */
        el = ELBus.switchOpt("x").to(
                ELBus.then("a", "b"),
                ELBus.when("c", "d", "e"),
                ELBus.then("f", "g", "h")
        ).defaultOpt("i").toEL(true);
        System.out.println(el);

        // if
        /*
        IF(a, IF(b, c).else(d)).else(IF(e, f).else(g))
         */
        el = ELBus.ifOpt("a", ELBus.ifOpt("b", "c").elseOpt("d")).elseOpt(ELBus.ifOpt("e", "f", "g")).toEL();
        System.out.println(el);

        el = ELBus.ifOpt("a", ELBus.ifOpt("b", "c").elseOpt("d"))
                .elIfOpt("e", "f").elseOpt("g").toEL(true);
        System.out.println(el);

        /*
        IF(x1, a).ELIF(x2, b).ELIF(x3, c).ELIF(x4, d).ELSE(THEN(m, n));
         */
        el = ELBus.ifOpt("x1", "a")
                .elIfOpt("x2", "b")
                .elIfOpt("x3", "c")
                .elIfOpt("x4", "d")
                .elseOpt(ELBus.then("m", "n")).toEL();
        System.out.println(el);

        /*
        IF(a, THEN(b, IF(c, d, e))).else(IF(f, THEN(g, IF(h, i, j)), k))
         */
        el = ELBus.ifOpt("a", ELBus.then("b", ELBus.ifOpt("c", "d").elseOpt("e"))).elseOpt(
                ELBus.ifOpt("f", ELBus.then("g", ELBus.ifOpt("h", "i", "j")).then("k"))
        ).toEL(true);
        System.out.println(el);

        el = ELBus.ifOpt("h", "i", "j").toEL(true);
        System.out.println(el);

        /*
        IF(a, THEN(b, IF(c, THEN(d, IF(e, f).else(g)), h))).else(IF(i, THEN(j, IF(k, l, m)), n))
         */
        el = ELBus.ifOpt("a",
                ELBus.then("b",
                        ELBus.ifOpt("c", ELBus.then("d", ELBus.ifOpt("e", "f").elseOpt("g")), "h")))
                .elseOpt(ELBus.ifOpt("i", ELBus.then("j", ELBus.ifOpt("k", "l", "m")), "n")).toEL(true);
        System.out.println(el);
//
        /*
        SWITCH(x).TO(
         THEN(
            IF(a, THEN(b, c)).else(d),
            WHEN(e, f, THEN(g, h))
          ),
          WHEN(j, k, THEN(
            IF(l, THEN(m, n)).else(o),
            WHEN(p, q, r)
          ))
        )
         */
        el = ELBus.switchOpt("x").to(ELBus.then(
                ELBus.ifOpt("a", ELBus.then("b", "c")).elseOpt("d"),
                ELBus.when("e", "f", ELBus.then("g", "h"))
            ),
                ELBus.when("j", "k", ELBus.then(ELBus.ifOpt("l", ELBus.then("m", "n")).elseOpt("o"),
                        ELBus.when("p", "q", "r")))).toEL(true);
        System.out.println(el);

        // Loop
        el = ELBus.forOpt(5).doOpt(ELBus.then("a", "b")).toEL(true);
        System.out.println(el);

        el = ELBus.forOpt("f").doOpt(ELBus.then("a", "b")).toEL(true);
        System.out.println(el);

        el = ELBus.whileOpt("w").doOpt(ELBus.then("a", "b")).breakOpt("c").toEL(true);
        System.out.println(el);

        el = ELBus.iteratorOpt("x").doOpt(ELBus.then("a", "b")).toEL(true);
        System.out.println(el);

        /*
        SWITCH(x).TO(
          THEN(
            IF(a, THEN(b, c)).ELSE(d),
            WHEN(e, f,
              FOR(g).DO(
                THEN(
                  IF(h, THEN(i, j)).ELSE(k),
                  WHEN(l, m, THEN(n, o))
                )
              )
            )
          ),
          WHEN(t, u, THEN(
            FOR(v).DO(
              THEN(
                IF(w, THEN(x, y)).ELSE(z),
                WHEN(aa, bb, THEN(cc, dd))
              )
            )
          )),
          THEN(ii, jj)
        )
         */
        el = ELBus.switchOpt("x").to(
                ELBus.then(
                        ELBus.ifOpt(
                                "a",
                                ELBus.then("b", "c"))
                        .elseOpt("d"),
                        ELBus.when(
                                "e",
                                "f",
                                ELBus.forOpt("g").doOpt(ELBus.then(
                                        ELBus.ifOpt("h", ELBus.then("i", "j")).elseOpt("k"),
                                        ELBus.when("l", "m", ELBus.then("n", "o"))
                                )))
                ),
                ELBus.when("t", "u", ELBus.then(ELBus.forOpt("v").doOpt(
                        ELBus.then(
                                ELBus.ifOpt("w", ELBus.then("x", "y")).elseOpt("z"),
                                ELBus.when("aa", "bb", ELBus.then("cc", "dd"))
                        )
                ))),
                ELBus.then("ii", "jj")
        ).toEL(true);
        System.out.println(el);

        // catch
        el = ELBus.catchException(ELBus.then("a", "b")).doOpt("c").toEL(true);
        System.out.println(el);

        el = ELBus.then(ELBus.catchException(ELBus.then("a", "b")).doOpt("x"), "c").toEL(true);
        System.out.println(el);

        el = ELBus.forOpt("x").doOpt(
                ELBus.catchException(ELBus.then("a", "b", "c"))
        ).toEL(true);
        System.out.println(el);

        // and or not
        el = ELBus.ifOpt(ELBus.and("x", "y"), "a").elseOpt("b").toEL(true);
        System.out.println(el);

        el = ELBus.ifOpt(ELBus.or("x", "y"), "a").elseOpt("b").toEL(true);
        System.out.println(el);

        el = ELBus.ifOpt(ELBus.not("x"), "a", "b").toEL(true);
        System.out.println(el);

        /*
            IF(
                OR(
                    AND(x1, x3), NOT(OR(x3, x4))
                ),
                a, b
            );
         */
        el = ELBus.ifOpt(ELBus.or(
                ELBus.and("x1", "x2"),
                ELBus.not(
                        ELBus.or("x3", "x4")
                )
        ),"a", "b").toEL(true);
        System.out.println(el);

        // 文档中复杂编排的例子
        /* 复杂编排例子一
        THEN(
            A,
            WHEN(
                THEN(B, C),
                THEN(D, E, F),
                THEN(
                    SWITCH(G).to(
                        THEN(H, I, WHEN(J, K)).id("t1"),
                        THEN(L, M).id("t2")
                    ),
                    N
                )
            ),
            Z
        );
         */
        el = ELBus.then(
                "a",
                ELBus.when(
                        ELBus.then("b", "c"),
                        ELBus.then("d", "e").then("f"),
                        ELBus.then(
                                ELBus.switchOpt("g").to(
                                        ELBus.then("h", "i",
                                                ELBus.when("j", "k")).id("t1"),
                                        ELBus.then("l", "m").id("t2")
                                ),
                                ELBus.then("n")
                        )
                ),
                "z"
        ).toEL();
        System.out.println(el);

        /* 复杂编排例子二
        THEN(
            A,
            SWITCH(B).to(
                THEN(D, E, F).id("t1"),
                THEN(
                    C,
                    WHEN(
                        THEN(
                            SWITCH(G).to(THEN(H, I).id("t2"), J),
                            K
                        ),
                        THEN(L, M)
                    )
                ).id("t3")
            ),
            Z
        );
         */
        el = ELBus.then(
                "a",
                ELBus.switchOpt("b").to(
                        ELBus.then("d", "e", "f").id("t1"),
                        ELBus.then(
                                "c",
                                ELBus.when(
                                        ELBus.then(
                                                ELBus.switchOpt("g").to(
                                                        ELBus.then("h", "i").id("t2"),
                                                        "j"
                                                ),
                                                "k"
                                        ),
                                        ELBus.then("l", "m")
                                )
                        ).id("t3")
                ),
                "z"
        ).toEL();
        System.out.println(el);

    }
}
