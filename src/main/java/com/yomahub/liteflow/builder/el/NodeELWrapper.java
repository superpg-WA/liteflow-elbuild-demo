package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.StrUtil;

public class NodeELWrapper extends ELWrapper {

    private String nodeId;

//    private String tag;

    public NodeELWrapper(String nodeId) {
        this.nodeId = nodeId;
        this.setNodeWrapper(this);
    }

    private void setNodeWrapper(ELWrapper elWrapper){
        this.addWrapper(elWrapper, 0);
    }

    private NodeELWrapper getNodeWrapper(){
        return (NodeELWrapper) this.getFirstWrapper();
    }

    protected String getNodeId() {
        return nodeId;
    }

    protected void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public NodeELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    @Override
    public NodeELWrapper id(String id) {
        this.setId(id);
        return this;
    }

    @Override
    public String toEL() {
        NodeELWrapper nodeELWrapper = this.getNodeWrapper();
        StringBuilder sb = new StringBuilder();
        sb.append(StrUtil.format("node(\"{}\")", nodeELWrapper.getNodeId()));
        if (StrUtil.isNotBlank(nodeELWrapper.getTag())){
            sb.append(StrUtil.format(".tag(\"{}\")", nodeELWrapper.getTag()));
        }
        return sb.toString();
    }

    @Override
    protected String toEL(int depth) {
        NodeELWrapper nodeELWrapper = this.getNodeWrapper();
        StringBuilder sb = new StringBuilder();
        sb.append(StrUtil.repeat(ELBus.TAB, depth));
        sb.append(StrUtil.format("node(\"{}\")", nodeELWrapper.getNodeId()));
        if (StrUtil.isNotBlank(nodeELWrapper.getTag())){
            sb.append(StrUtil.format(".tag(\"{}\")", nodeELWrapper.getTag()));
        }
        return sb.toString();
    }
}
