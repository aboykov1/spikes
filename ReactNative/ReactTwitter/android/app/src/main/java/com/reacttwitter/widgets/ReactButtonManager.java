package com.reacttwitter.widgets;

import android.graphics.Color;

import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ReactButtonManager extends BaseViewManager<ReactButton, ReactButtonShadowNode> {

    public static final String REACT_CLASS = "RCTNovodaButton";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ReactButton createViewInstance(ThemedReactContext reactContext) {
        return new ReactButton(reactContext);
    }

    @ReactProp(name = "enabled")
    public void setEnabled(ReactButton reactButton, boolean enabled) {
        reactButton.setEnabled(enabled);
    }

    @ReactProp(name = "text")
    public void setText(ReactButton reactButton, String text) {
        reactButton.setText(text);
    }

    @ReactProp(name = "textColor")
    public void setTextColor(ReactButton reactButton, String color) {
        reactButton.setTextColor(Color.parseColor(color));
    }

    @ReactProp(name = "backgroundImage")
    public void setBackgroundImage(ReactButton reactButton, String backgroundImage) {
        reactButton.setBackgroundImage(backgroundImage);
    }

    @Override
    public ReactButtonShadowNode createShadowNodeInstance() {
        return new ReactButtonShadowNode();
    }

    @Override
    public Class<ReactButtonShadowNode> getShadowNodeClass() {
        return ReactButtonShadowNode.class;
    }

    @Override
    public void updateExtraData(ReactButton root, Object extraData) {
        // noop
    }
}