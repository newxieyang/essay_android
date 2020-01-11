package com.cullen.tatu.utils.lang;


import androidx.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * luguangqing
 * 2018/1/17.
 * 可观察对象封装
 */
public class Variable<T> {

    private T value;
    private T oldValue;

    private final PublishSubject<T> subject;

    public Variable(T value) {
        this.value = value;
        subject = PublishSubject.create();
    }

    public synchronized T getValue() {
        return value;
    }

    public synchronized T getOldValue() {
        return oldValue;
    }

    // rx2.0不允许发送null值
    public synchronized void setValue(@NonNull T value) {
        this.oldValue = this.value;
        this.value = value;
        subject.onNext(this.value);
    }

    public Observable<T> asObservable() {
        return subject.hide();
    }


}
