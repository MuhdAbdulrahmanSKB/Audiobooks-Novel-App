package skb.skebbi.theskebbiapps.yarfillo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;
    Animation bottonAnim,bounce,bounceslow,slideleft,slideright;
    TextView textView,from;
    ImageView splashIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
    
        // Animations
        bottonAnim = AnimationUtils.loadAnimation(this, R.anim.botton_animation);
        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        bounceslow = AnimationUtils.loadAnimation(this, R.anim.bounce_slow);
        slideright = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        slideleft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
    
        //Hooks
        splashIcon = findViewById(R.id.splashIcon);
        textView = findViewById(R.id.theskebiiapps);
        from = findViewById(R.id.textView5);
    
        splashIcon.setAnimation(bounce);
        textView.setAnimation(slideright);
        from.setAnimation(slideleft);
    
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}