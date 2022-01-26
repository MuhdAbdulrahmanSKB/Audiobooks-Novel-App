package skb.skebbi.theskebbiapps.yarfillo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Developer extends AppCompatActivity {
    TextView mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
    
        mail = findViewById(R.id.textView12);
        mail.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            try {
                Intent intent = new Intent(Intent.ACTION_SENDTO); intent.setData(Uri.parse("mailto:"));
                // only email apps should handle this String[] to = {"example@gmail.com", "abc@gmail.com"}; intent.putExtra(Intent.EXTRA_EMAIL, to); intent.putExtra(Intent.EXTRA_SUBJECT, "Example Subject"); intent.putExtra(Intent.EXTRA_TEXT, "This is a Example Text");
                startActivity(Intent.createChooser(intent, "Send Email"));
            } catch (Exception e) { e.printStackTrace();
            }
        }
        });
    }
}