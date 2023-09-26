package com.yomahub.liteflow.builder.el;

import com.sun.org.apache.xpath.internal.operations.And;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.xml.soap.Node;
import java.util.Arrays;

public class ELBus {

    public static String TAB = "\t";

    // then参数校验
    public static ThenELWrapper then(ELWrapper ... elWrappers){
        checkNotBooleanArgs(elWrappers);
        return new ThenELWrapper(elWrappers);
    }

    public static ThenELWrapper then(Object ... objects){
        ELWrapper[] elWrappers = convertToNonLogicOpt(objects);
        return new ThenELWrapper(elWrappers);
    }

    public static WhenELWrapper when(ELWrapper ... elWrappers){
        checkNotBooleanArgs(elWrappers);
        return new WhenELWrapper(elWrappers);
    }

    public static WhenELWrapper when(Object ... objects){
        ELWrapper[] elWrappers = convertToNonLogicOpt(objects);
        return new WhenELWrapper(elWrappers);
    }

    // if 的单节点和与或非限制
    public static IfELWrapper ifOpt(NodeELWrapper ifELWrapper, Object trueELWrapper, Object falseELWrapper){
        return new IfELWrapper(ifELWrapper, convertToNonLogicOpt(trueELWrapper), convertToNonLogicOpt(falseELWrapper));
    }

    public static IfELWrapper ifOpt(String ifELWrapper, Object trueELWrapper, Object falseELWrapper){
        return new IfELWrapper((NodeELWrapper) convertToLogicOpt(ifELWrapper), convertToNonLogicOpt(trueELWrapper), convertToNonLogicOpt(falseELWrapper));
    }

    public static IfELWrapper ifOpt(AndELWrapper ifELWrapper, Object trueELWrapper, Object falseELWrapper){
        return new IfELWrapper(ifELWrapper, convertToNonLogicOpt(trueELWrapper), convertToNonLogicOpt(falseELWrapper));
    }

    public static IfELWrapper ifOpt(OrELWrapper ifELWrapper, Object trueELWrapper, Object falseELWrapper){
        return new IfELWrapper(ifELWrapper, convertToNonLogicOpt(trueELWrapper), convertToNonLogicOpt(falseELWrapper));
    }

    public static IfELWrapper ifOpt(NotELWrapper ifELWrapper, Object trueELWrapper, Object falseELWrapper){
        return new IfELWrapper(ifELWrapper, convertToNonLogicOpt(trueELWrapper), convertToNonLogicOpt(falseELWrapper));
    }

    public static IfELWrapper ifOpt(NodeELWrapper ifELWrapper, Object trueELWrapper){
        return new IfELWrapper(ifELWrapper, convertToNonLogicOpt(trueELWrapper));
    }

    public static IfELWrapper ifOpt(String ifELWrapper, Object trueELWrapper){
        return new IfELWrapper((NodeELWrapper) convertToLogicOpt(ifELWrapper), convertToNonLogicOpt(trueELWrapper));
    }

    public static IfELWrapper ifOpt(AndELWrapper ifELWrapper, Object trueELWrapper){
        return new IfELWrapper(ifELWrapper, convertToNonLogicOpt(trueELWrapper));
    }

    public static IfELWrapper ifOpt(OrELWrapper ifELWrapper, Object trueELWrapper){
        return new IfELWrapper(ifELWrapper, convertToNonLogicOpt(trueELWrapper));
    }

    public static IfELWrapper ifOpt(NotELWrapper ifELWrapper, Object trueELWrapper){
        return new IfELWrapper(ifELWrapper, convertToNonLogicOpt(trueELWrapper));
    }

    public static NodeELWrapper node(String nodeId){
        return new NodeELWrapper(nodeId);
    }

    // switch 的单节点限制
    public static SwitchELWrapper switchOpt(NodeELWrapper nodeELWrapper){
        return new SwitchELWrapper(nodeELWrapper);
    }

    public static SwitchELWrapper switchOpt(String nodeELWrapper){
        return new SwitchELWrapper(convert(nodeELWrapper));
    }

    // for组件限制 int、单节点、String
    public static ForELWrapper forOpt(Integer loopNumber){
        return new ForELWrapper(loopNumber, "FOR");
    }

    public static ForELWrapper forOpt(NodeELWrapper nodeELWrapper){
        return new ForELWrapper(nodeELWrapper, "FOR");
    }

    public static ForELWrapper forOpt(String nodeELWrapper){
        return new ForELWrapper(convert(nodeELWrapper), "FOR");
    }

    // while 的单节点和与或非限制
    public static WhileELWrapper whileOpt(NodeELWrapper nodeELWrapper){
        return new WhileELWrapper(nodeELWrapper, "WHILE");
    }

    public static WhileELWrapper whileOpt(String nodeELWrapper){
        return new WhileELWrapper(convert(nodeELWrapper), "WHILE");
    }

    public static WhileELWrapper whileOpt(AndELWrapper nodeELWrapper){
        return new WhileELWrapper(nodeELWrapper, "WHILE");
    }

    public static WhileELWrapper whileOpt(OrELWrapper nodeELWrapper){
        return new WhileELWrapper(nodeELWrapper, "WHILE");
    }

    public static WhileELWrapper whileOpt(NotELWrapper nodeELWrapper){
        return new WhileELWrapper(nodeELWrapper, "WHILE");
    }

    // iterator单节点限制
    public static IteratorELWrapper iteratorOpt(NodeELWrapper nodeELWrapper){
        return new IteratorELWrapper(nodeELWrapper, "ITERATOR");
    }

    public static IteratorELWrapper iteratorOpt(String nodeELWrapper){
        return new IteratorELWrapper(convert(nodeELWrapper), "ITERATOR");
    }

    // catch 的参数类型不能为与或非表达式
    public static CatchELWrapper catchException(Object object){
        return new CatchELWrapper(convertToNonLogicOpt(object));
    }

    // 与或非表达式，
    // 这里做了拦截，实际运行中的参数，只会有单节点和与或非表达式
    public static AndELWrapper and(Object ... objects){
        ELWrapper[] elWrappers = convertToLogicOpt(objects);
        return new AndELWrapper(elWrappers);
    }

    public static OrELWrapper or(Object ... objects){
        ELWrapper[] elWrappers = convertToLogicOpt(objects);
        return new OrELWrapper(elWrappers);
    }

    public static NotELWrapper not(NodeELWrapper notElWrapper){
        return new NotELWrapper(notElWrapper);
    }

    public static NotELWrapper not(String notElWrapper){
        return new NotELWrapper(convert(notElWrapper));
    }

    public static NotELWrapper not(AndELWrapper notElWrapper){
        return new NotELWrapper(notElWrapper);
    }

    public static NotELWrapper not(OrELWrapper notElWrapper){
        return new NotELWrapper(notElWrapper);
    }

    public static NotELWrapper not(NotELWrapper notElWrapper){
        return new NotELWrapper(notElWrapper);
    }

    // 校验参数是否为ELWrapper类型或者String类型
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

    // 转换为只包含与或非的组件
    public static ELWrapper[] convertToLogicOpt(Object... objects){
        ELWrapper[] elWrappers = convert(objects);
        checkBooleanArgs(elWrappers);
        return elWrappers;
    }

    public static ELWrapper convertToLogicOpt(Object object){
        ELWrapper elWrapper = convert(object);
        checkBooleanArgs(elWrapper);
        return elWrapper;
    }

    // 转换为不包含与或非表达式的组件
    public static ELWrapper[] convertToNonLogicOpt(Object ... objects){
        ELWrapper[] elWrappers = convert(objects);
        checkNotBooleanArgs(elWrappers);
        return elWrappers;
    }

    public static ELWrapper convertToNonLogicOpt(Object object){
        ELWrapper elWrapper = convert(object);
        checkNotBooleanArgs(elWrapper);
        return elWrapper;
    }

    // 检查参数都不返回bool值
    public static void checkNotBooleanArgs(ELWrapper ... elWrappers) {
        for(ELWrapper elWrapper : elWrappers){
            if(elWrapper instanceof AndELWrapper){
                throw new RuntimeException("param is error");
            } else if(elWrapper instanceof OrELWrapper){
                throw new RuntimeException("param is error");
            } else if(elWrapper instanceof NotELWrapper){
                throw new RuntimeException("param is error");
            }
        }
    }

    // 检查参数是否都能返回bool值
    public static void checkBooleanArgs(ELWrapper ... elWrappers) {
        for(ELWrapper elWrapper : elWrappers){
            if(!(elWrapper instanceof AndELWrapper)
            && !(elWrapper instanceof OrELWrapper)
            && !(elWrapper instanceof NotELWrapper)
            && !(elWrapper instanceof NodeELWrapper)){
                throw new RuntimeException("param is error");
            }
        }
    }
}
