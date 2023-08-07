package com.yomahub.liteflow.builder.el;

public class ThenELWrapper extends ELWrapper {

    public ThenELWrapper(ELWrapper ... elWrappers) {
        this.addWrapper(elWrappers);
    }

    public ThenELWrapper then(ELWrapper ... elWrappers){
        this.addWrapper(elWrappers);
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
        return sb.toString();
    }
}
