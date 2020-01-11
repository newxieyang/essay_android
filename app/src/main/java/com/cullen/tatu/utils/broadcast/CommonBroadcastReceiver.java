package com.cullen.tatu.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CommonBroadcastReceiver extends BroadcastReceiver {


    BroadCastReceiverInterface anInterface;

    public CommonBroadcastReceiver(BroadCastReceiverInterface anInterface) {
        this.anInterface = anInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        anInterface.onMessageReceiver(intent);
    }
}
