package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.StrUtil;

public class AndELWrapper extends ELWrapper{

    public AndELWrapper(ELWrapper ... elWrappers){
        this.addWrapper(elWrappers);
    }

    public AndELWrapper and(Object ... object){
        ELWrapper[] wrapper = ELBus.convertToLogicOpt(object);
        this.addWrapper(wrapper);
        return this;
    }

    @Override
    public AndELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public AndELWrapper id(String id) {
        this.setId(id);
        return this;
    }

    @Override
    public String toEL() {
        StringBuilder sb = new StringBuilder();
        sb.append("AND(");
        for (int i = 0; i < this.getElWrapperList().size(); i++) {
            if (i > 0){
                sb.append(", ");
            }
            sb.append(this.getElWrapperList().get(i).toEL());
        }
        sb.append(")");

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
        sb.append(StrUtil.format("{}AND(\n", StrUtil.repeat(ELBus.TAB, depth)));
        for (int i = 0; i < this.getElWrapperList().size(); i++) {
            if (i > 0){
                sb.append(",\n");
            }
            sb.append(this.getElWrapperList().get(i).toEL(depth + 1));
        }
        sb.append(StrUtil.format("\n{})", StrUtil.repeat(ELBus.TAB, depth)));

        if(this.getId() != null){
            sb.append(StrUtil.format(".id(\"{}\")", this.getId()));
        }
        if(this.getTag() != null){
            sb.append(StrUtil.format(".tag(\"{}\")", this.getTag()));
        }
        return sb.toString();
    }
}
