package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

public class IfELWrapper extends ELWrapper{

    // 采用if(x,a,b)定义，输出也是这种形式
    private static final int IF_FORMAT = 1;
    // 采用if(x,a)定义，如果调用了else，
    private static final int IF_ELSE_FORMAT = 2;
    // elif 输出形式
    private static final int ELIF_FORMAT = 3;

    private int format;

    /**
     * 如果缠绕器
     * 单节点作为判断条件
     *
     * @param ifWrapper    如果包装器
     * @param trueWrapper  真正包装器
     * @param falseWrapper 假包装器
     */
    public IfELWrapper(NodeELWrapper ifWrapper, ELWrapper trueWrapper, ELWrapper falseWrapper) {
        this.setIfWrapper(ifWrapper);
        this.setTrueWrapper(trueWrapper);
        this.setFalseWrapper(falseWrapper);
        this.format = IF_FORMAT;
    }

    /**
     * 如果缠绕器
     *
     * @param ifWrapper   如果包装器
     * @param trueWrapper 真正包装器
     */
    public IfELWrapper(NodeELWrapper ifWrapper, ELWrapper trueWrapper) {
        this.setIfWrapper(ifWrapper);
        this.setTrueWrapper(trueWrapper);
        this.format = IF_ELSE_FORMAT;
    }

    /**
     * 与节点作为判断条件
     *
     * @param andELWrapper
     * @param trueWrapper
     * @param falseWrapper
     */
    public IfELWrapper(AndELWrapper andELWrapper, ELWrapper trueWrapper, ELWrapper falseWrapper) {
        this.setIfWrapper(andELWrapper);
        this.setTrueWrapper(trueWrapper);
        this.setFalseWrapper(falseWrapper);
        this.format = IF_FORMAT;
    }

    public IfELWrapper(AndELWrapper andELWrapper, ELWrapper trueWrapper){
        this.setIfWrapper(andELWrapper);
        this.setTrueWrapper(trueWrapper);
        this.format = IF_ELSE_FORMAT;
    }

    /**
     * 或节点作为判断条件
     * @param andELWrapper
     * @param trueWrapper
     * @param falseWrapper
     */
    public IfELWrapper(OrELWrapper andELWrapper, ELWrapper trueWrapper, ELWrapper falseWrapper) {
        this.setIfWrapper(andELWrapper);
        this.setTrueWrapper(trueWrapper);
        this.setFalseWrapper(falseWrapper);
        this.format = IF_FORMAT;
    }

    public IfELWrapper(OrELWrapper andELWrapper, ELWrapper trueWrapper){
        this.setIfWrapper(andELWrapper);
        this.setTrueWrapper(trueWrapper);
        this.format = IF_ELSE_FORMAT;
    }

    /**
     * 非节点作为判断条件
     * @param andELWrapper
     * @param trueWrapper
     * @param falseWrapper
     */
    public IfELWrapper(NotELWrapper andELWrapper, ELWrapper trueWrapper, ELWrapper falseWrapper) {
        this.setIfWrapper(andELWrapper);
        this.setTrueWrapper(trueWrapper);
        this.setFalseWrapper(falseWrapper);
        this.format = IF_FORMAT;
    }

    public IfELWrapper(NotELWrapper andELWrapper, ELWrapper trueWrapper){
        this.setIfWrapper(andELWrapper);
        this.setTrueWrapper(trueWrapper);
        this.format = IF_ELSE_FORMAT;
    }

    /**
     * else语句
     *
     * @param falseObject false分支
     * @return {@link IfELWrapper}
     */
    public IfELWrapper elseOpt(Object falseObject){
        ELWrapper falseWrapper = ELBus.convertToNonLogicOpt(falseObject);
        // 找到最深层的if组件
        ELWrapper prev = this;
        ELWrapper succ = this;
        while(prev instanceof IfELWrapper){
            succ = prev;
            prev = prev.getElWrapperList().size() >= 3 ? prev.getElWrapperList().get(2) : null;
        }
        ((IfELWrapper) succ).setFalseWrapper(falseWrapper);
        return this;
    }

    /**
     * elif语句
     *
     * @param ifObject   判断组件
     * @param trueObject true分支
     * @return {@link IfELWrapper}
     */
    public IfELWrapper elIfOpt(Object ifObject, Object trueObject) {
        ELWrapper ifWrapper = ELBus.convertToLogicOpt(ifObject);
        ELWrapper trueWrapper = ELBus.convertToNonLogicOpt(trueObject);
        IfELWrapper elIfWrapper = new IfELWrapper((NodeELWrapper) ifWrapper, trueWrapper);
        elIfWrapper.setFormat(ELIF_FORMAT);
        // 找到最深层的if组件
        ELWrapper prev = this;
        ELWrapper succ = this;
        while(prev instanceof IfELWrapper){
            succ = prev;
            prev = prev.getElWrapperList().size() >= 3 ? prev.getElWrapperList().get(2) : null;
        }
        ((IfELWrapper) succ).elseOpt(elIfWrapper);
        return this;
    }

    private void setIfWrapper(ELWrapper ifWrapper){
        this.addWrapper(ifWrapper, 0);
    }

    private ELWrapper getIfWrapper(){
        return this.getElWrapperList().get(0);
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

    protected void setFormat(int formatCode){
        this.format = formatCode;
    }

    protected int getFormat(){
        return this.format;
    }

    private ELWrapper getFalseWrapper(){
        try{
            return this.getElWrapperList().get(2);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public IfELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public IfELWrapper id(String id) {
        this.setId(id);
        return this;
    }

    @Override
    public String toEL() {
        StringBuilder sb = new StringBuilder();
        switch (this.format){
            case IF_FORMAT:
                sb.append(StrUtil.format("IF({}, {}, {})", this.getIfWrapper().toEL(), this.getTrueWrapper().toEL(), this.getFalseWrapper().toEL()));
                break;
            case IF_ELSE_FORMAT:
                sb.append(StrUtil.format("IF({}, {})", this.getIfWrapper().toEL(), this.getTrueWrapper().toEL()));
                if (ObjectUtil.isNotNull(this.getFalseWrapper())){
                    if(this.getFalseWrapper() instanceof IfELWrapper && ((IfELWrapper) this.getFalseWrapper()).getFormat() == ELIF_FORMAT){
                        sb.append(this.getFalseWrapper().toEL());
                    } else {
                        sb.append(StrUtil.format(".ELSE({})", this.getFalseWrapper().toEL()));
                    }
                }
                break;
            case ELIF_FORMAT:
                sb.append(StrUtil.format(".ELIF({}, {})", this.getIfWrapper().toEL(), this.getTrueWrapper().toEL()));
                if (ObjectUtil.isNotNull(this.getFalseWrapper())){
                    if(this.getFalseWrapper() instanceof IfELWrapper && ((IfELWrapper) this.getFalseWrapper()).getFormat() == ELIF_FORMAT){
                        sb.append(this.getFalseWrapper().toEL());
                    } else {
                        sb.append(StrUtil.format(".ELSE({})", this.getFalseWrapper().toEL()));
                    }
                }
                break;
            default:
                break;
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
        sb.append(StrUtil.repeat(ELBus.TAB, depth));
        switch (this.format){
            case IF_FORMAT:
                sb.append(StrUtil.format("IF(\n{},\n{},\n{}\n{})", this.getIfWrapper().toEL(depth + 1), this.getTrueWrapper().toEL(depth + 1), this.getFalseWrapper().toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));
                break;
            case IF_ELSE_FORMAT:
                sb.append(StrUtil.format("IF(\n{},\n{}\n{})", this.getIfWrapper().toEL(depth + 1), this.getTrueWrapper().toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));
                if (ObjectUtil.isNotNull(this.getFalseWrapper())){
//                    sb.append(StrUtil.format(".ELSE(\n{}\n{})", this.getFalseWrapper().toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));
                    if(this.getFalseWrapper() instanceof IfELWrapper && ((IfELWrapper) this.getFalseWrapper()).getFormat() == ELIF_FORMAT){
                        sb.append(this.getFalseWrapper().toEL(depth));
                    } else {
                        sb.append(StrUtil.format(".ELSE(\n{}\n{})", this.getFalseWrapper().toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));
                    }
                }
                break;
            case ELIF_FORMAT:
                // elif 树形结构输出
                sb.append(StrUtil.format(".ELIF(\n{},\n{}\n{})", this.getIfWrapper().toEL(depth + 1), this.getTrueWrapper().toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));
                if (ObjectUtil.isNotNull(this.getFalseWrapper())){
//                    sb.append(StrUtil.format(".ELSE(\n{}\n{})", this.getFalseWrapper().toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));
                    if(this.getFalseWrapper() instanceof IfELWrapper && ((IfELWrapper) this.getFalseWrapper()).getFormat() == ELIF_FORMAT){
                        sb.append(this.getFalseWrapper().toEL(depth));
                    } else {
                        sb.append(StrUtil.format(".ELSE(\n{}\n{})", this.getFalseWrapper().toEL(depth + 1), StrUtil.repeat(ELBus.TAB, depth)));
                    }
                }
                break;
            default:
                break;
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
