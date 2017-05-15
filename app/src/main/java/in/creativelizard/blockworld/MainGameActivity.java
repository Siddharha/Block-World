package in.creativelizard.blockworld;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ai.api.*;
import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;


public class MainGameActivity extends AppCompatActivity implements AIListener {

    private static final String TAG = "response";
    private ImageView imgPlayer;
    private AIConfiguration config;
    private AIService aiService;
    private boolean isListening;
    private Button btnListen;
    private LinearLayout plr;
    private TextView tvPlayer;
    private FrameLayout llChat;
    private long speedNumber = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        initialize();
        onActionListener();
    }

    private void onActionListener() {
        aiService.setListener(this);
    }

    private void initialize() {
        imgPlayer = (ImageView)findViewById(R.id.imgPlayer);
        tvPlayer = (TextView)findViewById(R.id.tvPlayer);
        llChat = (FrameLayout) findViewById(R.id.llChat);
        config = new AIConfiguration("c28dce2cb4ab492298f8a39aafcc3c19",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        isListening = false;
        btnListen = (Button)findViewById(R.id.btnListen);
        plr = (LinearLayout)findViewById(R.id.plr);
        llChat.setVisibility(View.INVISIBLE);
    }

    public void clkSpeak(View view){

        if(!isListening){
            aiService.startListening();
            ((Button)view).setText("Stop Listening!");
        }else {
            aiService.stopListening();
            ((Button)view).setText("Speak");
        }

    }


    @Override
    public void onError(AIError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAudioLevel(float level) {

        float f = level;
    }

    @Override
    public void onListeningStarted() {
        Log.e(TAG,"start Listening!!");
        isListening = true;
    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {
        Log.e(TAG,"Speak");
        btnListen.setText("Speak");
        isListening = false;
        llChat.setVisibility(View.VISIBLE);
        tvPlayer.setText("Thinking...");
    }

    public void onResult(final AIResponse response) {

        if(response.getResult().getParameters().containsKey("Move")){
            String rp = response.getResult().getParameters().get("Move").getAsString();

            if(response.getResult().getParameters().get("Speed")!=null) {
                if (response.getResult().getParameters().get("Speed").getAsString().equals("s")) {
                    try {
                        speedNumber = response.getResult().getParameters().get("number").getAsLong();
                    }catch (NumberFormatException e){
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }else {
                speedNumber = 1;
            }
            // process response object

            llChat.setVisibility(View.INVISIBLE);
            Log.e(TAG,rp);
            Log.e(TAG,String.valueOf(speedNumber));
            switch (rp) {
                case "r":
                    plr.animate().translationX(200).setDuration(500/speedNumber).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            plr.clearAnimation();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    break;
// process response object
                case "l":
                    plr.animate().translationX(-200).setDuration(500/speedNumber).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            plr.clearAnimation();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    break;
                case "u":
                    plr.animate().translationY(-200).setDuration(500/speedNumber).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            plr.clearAnimation();
                           plr.setPivotX(plr.getX());
                            plr.setPivotY(plr.getY());
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    break;
                case "d":
                    plr.animate().translationY(200).setDuration(500/speedNumber).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            plr.clearAnimation();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    break;
            }
        }else {
            Toast.makeText(this, "Unable to Identify your Speech", Toast.LENGTH_SHORT).show();
            tvPlayer.setText("OOps!!");
            llChat.animate().alpha(0).setDuration(200).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    llChat.setAlpha(1);
                    llChat.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }


    }
}
