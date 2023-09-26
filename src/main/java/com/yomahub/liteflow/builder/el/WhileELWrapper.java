package com.yomahub.liteflow.builder.el;

public class WhileELWrapper extends LoopELWrapper{
    public WhileELWrapper(ELWrapper elWrapper, String loopFunction){
        super(elWrapper, loopFunction);
    }

    // DO的参数应该只允许定义一个组件，否则不知道这些组件之间的关系
    // 但是参数的类型不限制
    @Override
    public WhileELWrapper doOpt(Object object){
        ELWrapper elWrapper = ELBus.convertToNonLogicOpt(object);
        super.addWrapper(elWrapper);
        return this;
    }

    // break有单独的要求，break返回布尔值，可以是与或非表达式或者单节点
    public WhileELWrapper breakOpt(Object object){
        ELWrapper elWrapper = ELBus.convertToLogicOpt(object);
        super.addWrapper(elWrapper);
        return this;
    }

    @Override
    public WhileELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public WhileELWrapper id(String id) {
        this.id(id);
        return this;
    }
}
