package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.StrUtil;

/**
 * @author Ge_Zuao
 * @date 2023/09/23
 */
public class NotELWrapper extends ELWrapper{
    public NotELWrapper(ELWrapper elWrapper){
        this.addWrapper(elWrapper);
    }

    @Override
    public ELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public ELWrapper id(String id) {
        this.setId(id);
        return this;
    }

    @Override
    public String toEL() {
        StringBuilder sb = new StringBuilder();
        sb.append(StrUtil.format("NOT({})", this.getElWrapperList().get(0).toEL()));

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
        sb.append(StrUtil.format("{}NOT(\n{}\n{})", StrUtil.repeat(ELBus.TAB, depth), this.getElWrapperList().get(0).toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));

        if(this.getId() != null){
            sb.append(StrUtil.format(".id(\"{}\")", this.getId()));
        }
        if(this.getTag() != null){
            sb.append(StrUtil.format(".tag(\"{}\")", this.getTag()));
        }
        return sb.toString();
    }
}
