package com.example.mynotes.tools;

public interface Callback<T> {

    void onSuccess(T result);

    void onError(Throwable err);
}
