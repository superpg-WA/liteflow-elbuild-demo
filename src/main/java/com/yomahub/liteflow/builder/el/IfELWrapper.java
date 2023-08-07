package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

public class IfELWrapper extends ELWrapper{

    public IfELWrapper(NodeELWrapper ifWrapper, ELWrapper trueWrapper, ELWrapper falseWrapper) {
        this.setIfWrapper(ifWrapper);
        this.setTrueWrapper(trueWrapper);
        this.setFalseWrapper(falseWrapper);
    }

    public IfELWrapper(NodeELWrapper ifWrapper, ELWrapper trueWrapper) {
        this.setIfWrapper(ifWrapper);
        this.setTrueWrapper(trueWrapper);
    }

    public IfELWrapper(NodeELWrapper ifWrapper) {
        this.setIfWrapper(ifWrapper);
    }

    public IfELWrapper trueOpt(ELWrapper trueWrapper){
        this.setTrueWrapper(trueWrapper);
        return this;
    }

    public IfELWrapper falseOpt(ELWrapper trueWrapper){
        this.setFalseWrapper(trueWrapper);
        return this;
    }

    private void setIfWrapper(ELWrapper ifWrapper){
        this.addWrapper(ifWrapper, 0);
    }

    private NodeELWrapper getIfWrapper(){
        return (NodeELWrapper) this.getElWrapperList().get(0);
    }

    private void setTrueWrapper(ELWrapper trueWrapper){
        this.addWrapper(trueWrapper, 1);
    }

    private ELWrapper getTrueWrapper(){
        return this.getElWrapperList().get(1);
    }

    private void setFalseWrapper(ELWrapper falseWrapper){
        this.addWrapper(falseWrapper, 2);
    }

    private ELWrapper getFalseWrapper(){
        try{
            return this.getElWrapperList().get(2);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String toEL() {
        StringBuilder sb = new StringBuilder();
        sb.append(StrUtil.format("IF({}, {}", this.getIfWrapper().toEL(), this.getTrueWrapper().toEL()));
        if (ObjectUtil.isNotNull(this.getFalseWrapper())){
            sb.append(StrUtil.format(", {})", this.getFalseWrapper().toEL()));
        }else{
            sb.append(")");
        }
        return sb.toString();
    }
}
