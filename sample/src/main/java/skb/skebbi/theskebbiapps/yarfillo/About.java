package skb.skebbi.theskebbiapps.yarfillo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class About extends AppCompatActivity {
    Animation bottonAnim,bounce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    
        Button call = findViewById(R.id.button);
        Button sms = findViewById(R.id.button3);
        Button share = findViewById(R.id.button2);
//        ImageView image = findViewById(R.id.imageView);
        // Animations
        bottonAnim = AnimationUtils.loadAnimation(this, R.anim.botton_animation);
//        bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        
        //Hooks
        
        call.setAnimation(bottonAnim);
        share.setAnimation(bottonAnim);
        sms.setAnimation(bottonAnim);
//        image.setAnimation(bounce);
    
        call.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            String number = "09065211228";
            if (!number.equals("")) {
                Intent intent = new Intent(Intent.ACTION_DIAL); intent.setData(Uri.parse("tel:" + number)); startActivity(intent);
            } else {
                Toast.makeText(About.this, "Please enter the Phone number to continue", Toast.LENGTH_SHORT).show();
            }
        }
        });
        sms.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            String number = "09065211228";
            if (!number.equals("")) {
                Intent intent = new Intent(Intent.ACTION_VIEW); intent.setType("vnd.android-dir/mms-sms"); intent.setData(Uri.parse("sms:" + number)); startActivity(intent);
            } else {
                Toast.makeText(About.this, "Please enter the Phone number to continue", Toast.LENGTH_SHORT).show();
            }
        } });
        
        share.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            try {
                String appUrl = "https://play.google.com/store/apps/details?id=skb.skebbi.hnovellit1";
                Intent sharing = new Intent(Intent.ACTION_SEND); sharing.setType("text/plain"); sharing.putExtra(Intent.EXTRA_SUBJECT, "Download Now"); sharing.putExtra(Intent.EXTRA_TEXT, appUrl); startActivity(Intent.createChooser(sharing, "Share via"));
            } catch (Exception e) { e.printStackTrace();
            }
        }
        });
        
        
        
    
    }
}