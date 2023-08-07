package com.yomahub.liteflow.builder.el;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class ELWrapper {

    private List<ELWrapper> elWrapperList = new ArrayList<>();

    protected void addWrapper(ELWrapper wrapper){
        this.elWrapperList.add(wrapper);
    }

    protected void addWrapper(ELWrapper ... wrappers){
        this.elWrapperList.addAll(Arrays.asList(wrappers));
    }

    protected void addWrapper(ELWrapper wrapper, int index){
        this.elWrapperList.add(index, wrapper);
    }

    protected ELWrapper getFirstWrapper(){
        return this.elWrapperList.get(0);
    }

    protected List<ELWrapper> getElWrapperList(){
        return this.elWrapperList;
    }

    public abstract String toEL();

}
