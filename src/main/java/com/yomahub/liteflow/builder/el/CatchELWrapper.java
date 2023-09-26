package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.StrUtil;

public class CatchELWrapper extends ELWrapper{

    public CatchELWrapper(ELWrapper elWrapper){
        this.addWrapper(elWrapper);
    }

    public CatchELWrapper doOpt(Object object){
        ELWrapper elWrapper = ELBus.convertToNonLogicOpt(object);
        this.addWrapper(elWrapper);
        return this;
    }

    @Override
    public CatchELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public CatchELWrapper id(String id) {
        this.setId(id);
        return this;
    }

    @Override
    public String toEL() {
        StringBuilder sb = new StringBuilder();
        sb.append(StrUtil.format("CATCH({})", this.getElWrapperList().get(0).toEL()));

        if(this.getElWrapperList().size() > 1){
            sb.append(StrUtil.format(".DO({})", this.getElWrapperList().get(1).toEL()));
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
        sb.append(StrUtil.format("{}CATCH(\n{}\n{})",
                StrUtil.repeat(ELBus.TAB, depth),
                this.getElWrapperList().get(0).toEL(depth + 1),
                StrUtil.repeat(ELBus.TAB, depth)));

        if(this.getElWrapperList().size() > 1){
            sb.append(StrUtil.format(".DO(\n{}\n{})", this.getElWrapperList().get(1).toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));
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
