package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.StrUtil;

public abstract class LoopELWrapper extends ELWrapper{

    protected Integer loopNumber;

    // 以loopFunction变量进行区分FOR、WHILE、ITERATOR
    protected String loopFunction;

    // for循环，显式定义循环次数
    public LoopELWrapper(Integer loopNumber, String loopFunction){
        this.loopNumber = loopNumber;
        this.loopFunction = loopFunction;
        this.addWrapper(null, 0);
    }

    public LoopELWrapper(ELWrapper elWrapper, String loopFunction){
        this.loopFunction = loopFunction;
        this.addWrapper(elWrapper, 0);
    }

    // Do只有一个参数
    public LoopELWrapper doOpt(Object object){
        ELWrapper elWrapper = ELBus.convertToNonLogicOpt(object);
        this.addWrapper(elWrapper);
        return this;
    }

    @Override
    public LoopELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public LoopELWrapper id(String id) {
        this.id(id);
        return this;
    }

    @Override
    public String toEL() {
        StringBuilder sb = new StringBuilder();
        sb.append(StrUtil.format("{}(", loopFunction));
        if(loopNumber != null){
            sb.append(loopNumber.toString());
        } else {
            sb.append(this.getElWrapperList().get(0).toEL());
        }
        sb.append(")");

        if(this.getElWrapperList().size() > 1) {
            sb.append(StrUtil.format(".DO({})", this.getElWrapperList().get(1).toEL()));
        }

        if(this.getElWrapperList().size() > 2){
            sb.append(StrUtil.format(".BREAK({})", this.getElWrapperList().get(2).toEL()));
        }

        if(this.getId() != null){
            sb.append(StrUtil.format(".id(\"{}\")", this.getId()));
        }
        if(this.getTag() != null){
            sb.append(StrUtil.format(".tag(\"{}\")", this.getTag()));
        }
        return sb.toString();
    }

    @Override
    protected String toEL(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append(StrUtil.format("{}{}(", StrUtil.repeat(ELBus.TAB, depth), loopFunction));
        if(loopNumber != null){
            sb.append(loopNumber.toString());
        } else {
            sb.append(this.getElWrapperList().get(0).toEL());
        }
        sb.append(")");

        if(this.getElWrapperList().size() > 1) {
            sb.append(StrUtil.format(".DO(\n{}\n{})", this.getElWrapperList().get(1).toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));
        }

        if(this.getElWrapperList().size() > 2){
            // 如果循环只能是单节点的话，不需要depth
            sb.append(StrUtil.format(".BREAK({})", this.getElWrapperList().get(2).toEL()));
        }

        if(this.getId() != null){
            sb.append(StrUtil.format(".id(\"{}\")", this.getId()));
        }
        if(this.getTag() != null){
            sb.append(StrUtil.format(".tag(\"{}\")", this.getTag()));
        }
        return sb.toString();
    }
}
