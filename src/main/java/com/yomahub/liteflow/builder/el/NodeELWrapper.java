package com.yomahub.liteflow.builder.el;

import cn.hutool.core.util.StrUtil;

public class NodeELWrapper extends ELWrapper {

    private String nodeId;

    private String tag;

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

    public NodeELWrapper tag(String tag) {
        this.setTag(tag);
        return this;
    }

    protected String getNodeId() {
        return nodeId;
    }

    protected void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    protected String getTag() {
        return tag;
    }

    protected void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toEL() {
        NodeELWrapper nodeELWrapper = this.getNodeWrapper();
        StringBuilder sb = new StringBuilder();
        sb.append(StrUtil.format("{}", nodeELWrapper.getNodeId()));
        if (StrUtil.isNotBlank(nodeELWrapper.getTag())){
            sb.append(StrUtil.format(".tag(\"{}\")", nodeELWrapper.getTag()));
        }
        return sb.toString();
    }
}
