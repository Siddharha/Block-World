package in.creativelizard.blockworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainGameActivity extends AppCompatActivity {

    private ImageView imgPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        initialize();
    }

    private void initialize() {
        imgPlayer = (ImageView)findViewById(R.id.imgPlayer);
    }
}
