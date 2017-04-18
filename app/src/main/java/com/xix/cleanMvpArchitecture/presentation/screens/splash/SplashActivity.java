package com.xix.cleanMvpArchitecture.presentation.screens.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xix.cleanMvpArchitecture.R;
import com.xix.cleanMvpArchitecture.presentation.base.mvp.BaseActivity;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.NavigationActivity;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity {
    private static final String LOGO_TEXT = "novemio  android";
    @BindView(R.id.tv_logoText) TextView mTextView;
    private int mIndex = 0;
    public static final long DELAY = 200;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setUnBinder(ButterKnife.bind(this));
        setUp();
    }

    @Override protected void setUp() {
        animateText();
    }

    public void animateText() {
        mIndex = 0;
        mTextView.setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, DELAY);
    }

    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {

        @Override public void run() {
            mTextView.setText(LOGO_TEXT.subSequence(0, mIndex++));
            if (mIndex <= LOGO_TEXT.length()) {
                mHandler.postDelayed(characterAdder, DELAY);
            } else {
                new Timer().schedule(new TimerTask() {
                    @Override public void run() {
                        NavigationActivity.intent(SplashActivity.this).start();
                        finish();
                    }
                }, 3 * DELAY);
            }
        }
    };
}
