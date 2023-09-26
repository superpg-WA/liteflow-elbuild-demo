package com.yomahub.liteflow.builder.el;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ELWrapper {

    private final List<ELWrapper> elWrapperList = new ArrayList<>();

    // EL表达式都可以有tag和id，用于switch节点的选择
    private String tag;
    private String id;

    protected void addWrapper(ELWrapper wrapper){
        this.elWrapperList.add(wrapper);
    }

    protected void addWrapper(ELWrapper ... wrappers){
        this.elWrapperList.addAll(Arrays.asList(wrappers));
    }

    protected void addWrapper(ELWrapper wrapper, int index){
        this.elWrapperList.add(index, wrapper);
    }

    protected void setWrapper(ELWrapper wrapper, int index){
        this.elWrapperList.set(index, wrapper);
    }

    protected ELWrapper getFirstWrapper(){
        return this.elWrapperList.get(0);
    }

    protected List<ELWrapper> getElWrapperList(){
        return this.elWrapperList;
    }

    protected void setTag(String tag){
        this.tag = tag;
    }

    protected String getTag(){
        return this.tag;
    }

    protected void setId(String id){
        this.id = id;
    }

    protected String getId(){
        return this.id;
    }

    public abstract ELWrapper tag(String tag);

    public abstract ELWrapper id(String id);

    /**
     * 非格式化输出EL表达式
     *
     * @return {@link String}
     */
    public abstract String toEL();

    /**
     * 是否格式化输出树形结构的规则表达式
     *
     * @param format 格式
     * @return {@link String}
     */
    public String toEL(boolean format){
        if(!format) return toEL();
        return this.toEL(0);
    }

    /**
     * 格式化输出EL表达式
     *
     * @param depth 深度
     * @return {@link String}
     */
    protected abstract String toEL(int depth);
}
