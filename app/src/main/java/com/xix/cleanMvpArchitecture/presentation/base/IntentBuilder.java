package com.xix.cleanMvpArchitecture.presentation.base;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

public  class IntentBuilder<T> {

        private final Context context;
        private final Intent intent;

        public IntentBuilder(Context context, Class<T> clazz) {
            this(context, new Intent(context, clazz));
        }

        public IntentBuilder(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }


        public void start(){
            context.startActivity(intent);
        }

        protected void putParcelable(String name, Parcelable parcelable){
            intent.putExtra(name,parcelable);
        }

    }