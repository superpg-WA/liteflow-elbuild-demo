package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.StrUtil;

public class ThenELWrapper extends ELWrapper {

    public ThenELWrapper(ELWrapper ... elWrappers) {
        this.addWrapper(elWrappers);
    }

    public ThenELWrapper then(Object ... objects){
        ELWrapper[] elWrappers = ELBus.convertToNonLogicOpt(objects);
        // 校验与或非表达式
        this.addWrapper(elWrappers);
        return this;
    }

    @Override
    public ThenELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public ThenELWrapper id(String id) {
        this.setId(id);
        return this;
    }

    @Override
    public String toEL() {
        StringBuilder sb = new StringBuilder();
        sb.append("THEN(");
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
        sb.append(StrUtil.format("{}THEN(\n", StrUtil.repeat(ELBus.TAB, depth)));
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
