package com.yomahub.liteflow.builder.el;

public class WhenELWrapper extends ELWrapper {

    private boolean any;

    public WhenELWrapper(ELWrapper ... elWrappers) {
        this.addWrapper(elWrappers);
    }

    public WhenELWrapper when(ELWrapper ... elWrappers){
        this.addWrapper(elWrappers);
        return this;
    }

    public WhenELWrapper any(boolean any) {
        this.any = any;
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
        return sb.toString();
    }
}
