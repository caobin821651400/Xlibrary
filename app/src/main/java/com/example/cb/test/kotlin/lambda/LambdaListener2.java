package com.example.cb.test.kotlin.lambda;

public class LambdaListener2 {
    LambdaListener lambdaListener;

    public interface LambdaListener {
        void test();
    }

    public void setLambda(LambdaListener listener) {
        this.lambdaListener = listener;
    }

    public void onClick() {
        if (lambdaListener != null) {
            lambdaListener.test();
        }
    }
}
