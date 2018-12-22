package com.auto.util;


import org.springframework.web.util.HtmlUtils;

import java.beans.PropertyEditorSupport;

public class StringEscapeEditor extends PropertyEditorSupport {
    public StringEscapeEditor() {
    }

    public String getAsText() {
        Object value = this.getValue();
        return value != null ? value.toString() : "";
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null) {
            this.setValue((Object)null);
        } else {
            this.setValue(HtmlUtils.htmlEscape(text));
        }

    }
}