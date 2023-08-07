package com.yomahub.liteflow.builder.el;

import java.util.Arrays;

public class ELBus {

    public static ThenELWrapper then(ELWrapper ... elWrappers){
        return new ThenELWrapper(elWrappers);
    }

    public static ThenELWrapper then(Object ... elWrappers){
        return new ThenELWrapper(convert(elWrappers));
    }

    public static WhenELWrapper when(ELWrapper ... elWrappers){
        return new WhenELWrapper(elWrappers);
    }

    public static WhenELWrapper when(Object ... elWrappers){
        return new WhenELWrapper(convert(elWrappers));
    }

    public static IfELWrapper ifOpt(NodeELWrapper ifELWrapper, ELWrapper trueELWrapper, ELWrapper falseELWrapper){
        return new IfELWrapper(ifELWrapper, trueELWrapper, falseELWrapper);
    }

    public static IfELWrapper ifOpt(Object ifELWrapper, Object trueELWrapper, Object falseELWrapper){
        return new IfELWrapper((NodeELWrapper) convert(ifELWrapper), convert(trueELWrapper), convert(falseELWrapper));
    }

    public static IfELWrapper ifOpt(NodeELWrapper ifELWrapper, ELWrapper trueELWrapper){
        return new IfELWrapper(ifELWrapper, trueELWrapper);
    }

    public static IfELWrapper ifOpt(Object ifELWrapper, Object trueELWrapper){
        return new IfELWrapper((NodeELWrapper) convert(ifELWrapper), convert(trueELWrapper));
    }

    public static IfELWrapper ifOpt(NodeELWrapper ifELWrapper){
        return new IfELWrapper(ifELWrapper);
    }

    public static IfELWrapper ifOpt(Object ifELWrapper){
        return new IfELWrapper((NodeELWrapper) convert(ifELWrapper));
    }

    public static NodeELWrapper node(String nodeId){
        return new NodeELWrapper(nodeId);
    }

    public static ELWrapper[] convert(Object... objects){
        return Arrays.stream(objects).map(o -> {
            if (o instanceof String) {
                return new NodeELWrapper(o.toString());
            } else if (o instanceof ELWrapper) {
                return (ELWrapper) o;
            } else {
                throw new RuntimeException("param is error");
            }
        }).toArray(ELWrapper[]::new);
    }

    public static ELWrapper convert(Object object){
        if (object instanceof String) {
            return new NodeELWrapper(object.toString());
        } else if (object instanceof ELWrapper) {
            return (ELWrapper) object;
        } else {
            throw new RuntimeException("param is error");
        }
    }
}
