package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.StrUtil;

public class WhenELWrapper extends ELWrapper {

    private boolean any;
    private boolean ignoreError;

    public WhenELWrapper(ELWrapper ... elWrappers) {
        this.addWrapper(elWrappers);
    }

    public WhenELWrapper when(Object ... objects){
        ELWrapper[] elWrappers = ELBus.convertToNonLogicOpt(objects);
        // 校验与或非表达式
        this.addWrapper(elWrappers);
        return this;
    }

    public WhenELWrapper any(boolean any) {
        this.any = any;
        return this;
    }

    @Override
    public WhenELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public WhenELWrapper id(String id) {
        this.setId(id);
        return this;
    }

    @Override
    public String toEL() {
        StringBuilder sb = new StringBuilder();
        sb.append("WHEN(");
        for (int i = 0; i < this.getElWrapperList().size(); i++) {
            if (i > 0){
                sb.append(", ");
            }
            sb.append(this.getElWrapperList().get(i).toEL());
        }
        sb.append(")");

        if (this.any){
            sb.append(".any(true)");
        }
        if(this.ignoreError){
            sb.append(".ignoreError(true)");
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
        sb.append(StrUtil.format("{}WHEN(\n", StrUtil.repeat(ELBus.TAB, depth)));
        for (int i = 0; i < this.getElWrapperList().size(); i++) {
            if (i > 0){
                sb.append(",\n");
            }
            sb.append(this.getElWrapperList().get(i).toEL(depth + 1));
        }
        sb.append(StrUtil.format("\n{})", StrUtil.repeat(ELBus.TAB, depth)));

        if (this.any){
            sb.append(".any(true)");
        }
        if(this.ignoreError){
            sb.append(".ignoreError(true)");
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
