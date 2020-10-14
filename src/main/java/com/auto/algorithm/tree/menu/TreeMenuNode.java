package com.auto.algorithm.tree.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeMenuNode extends TreeObject<TreeMenuNode> implements Serializable {
    private String id;
    private String parentId;
    private String name;
    private Integer sort;
    private List<TreeMenuNode> children;
    private Boolean isAble;
    private Map<String, Object> attributes;
    private transient boolean hide = false;

    @Override
    public boolean isShow() {
        return !hide;
    }

    @Override
    void setHide(boolean isHide) {

    }


}

