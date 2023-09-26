package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.StrUtil;

public class SwitchELWrapper extends ELWrapper{

    // to参数允许为任意个，default可以有也可以没有。
    // 额外定义defaultELWrapper是比较简单的方法。
    private ELWrapper defaultELWrapper;

    public SwitchELWrapper(ELWrapper elWrapper){
        this.addWrapper(elWrapper, 0);
    }

    public SwitchELWrapper to(Object... objects){
        ELWrapper[] elWrappers = ELBus.convertToNonLogicOpt(objects);
        this.addWrapper(elWrappers);
        return this;
    }

    public SwitchELWrapper defaultOpt(Object object){
        defaultELWrapper = ELBus.convertToNonLogicOpt(object);
        return this;
    }

    @Override
    public SwitchELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public SwitchELWrapper id(String id) {
        this.setId(id);
        return this;
    }

    @Override
    public String toEL() {
        StringBuilder sb = new StringBuilder();
        sb.append(StrUtil.format("SWITCH({})", this.getFirstWrapper().toEL()));
        // 减少报错
        if(this.getElWrapperList().size() > 1) {
            sb.append(".TO(");
            for (int i = 1; i < this.getElWrapperList().size(); i++) {
                sb.append(this.getElWrapperList().get(i).toEL());
                if (i != this.getElWrapperList().size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");
        }
        // default可以不存在
        if(defaultELWrapper != null){
            sb.append(StrUtil.format(".DEFAULT({})", defaultELWrapper.toEL()));
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
        sb.append(StrUtil.format("{}SWITCH({})", StrUtil.repeat(ELBus.TAB, depth), this.getFirstWrapper().toEL()));
        // 减少报错
        if(this.getElWrapperList().size() > 1) {
            sb.append(".TO(\n");
            for (int i = 1; i < this.getElWrapperList().size(); i++) {
                sb.append(this.getElWrapperList().get(i).toEL(depth + 1));
                if (i != this.getElWrapperList().size() - 1) {
                    sb.append(",\n");
                }
            }
            sb.append(StrUtil.format("\n{})", StrUtil.repeat(ELBus.TAB, depth)));
        }
        // default可以不存在
        if(defaultELWrapper != null){
            sb.append(StrUtil.format(".DEFAULT(\n{}\n{})", defaultELWrapper.toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));
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
