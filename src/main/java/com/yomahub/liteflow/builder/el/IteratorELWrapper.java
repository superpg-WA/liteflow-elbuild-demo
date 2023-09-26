package com.yomahub.liteflow.builder.el;

public class IteratorELWrapper extends LoopELWrapper{
    public IteratorELWrapper(ELWrapper elWrapper, String loopFunction){
        super(elWrapper, loopFunction);
    }

    // DO的参数应该只允许定义一个组件，否则不知道这些组件之间的关系
    // 但是参数的类型不限制
    @Override
    public IteratorELWrapper doOpt(Object object){
        ELWrapper elWrapper = ELBus.convertToNonLogicOpt(object);
        super.addWrapper(elWrapper);
        return this;
    }

    @Override
    public IteratorELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public IteratorELWrapper id(String id) {
        this.id(id);
        return this;
    }
}
