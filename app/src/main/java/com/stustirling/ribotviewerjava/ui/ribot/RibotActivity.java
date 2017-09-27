package com.stustirling.ribotviewerjava.ui.ribot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.stustirling.ribotviewerjava.R;
import com.stustirling.ribotviewerjava.shared.model.RibotModel;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public class RibotActivity extends AppCompatActivity {
    public static final String RIBOT = "ribot";

    private SimpleDateFormat dateTimeFormatter;

    @BindView(R.id.tb_rb_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_ra_title)
    TextView title;
    @BindView(R.id.tv_ra_date)
    TextView date;
    @BindView(R.id.iv_ra_avatar)
    ImageView avatar;
    @BindView(R.id.tv_ra_bio)
    TextView bio;
    @BindView(R.id.fab_ra_email)
    FloatingActionButton emailFab;

    public static void launchRibot(Context context,
                                   RibotModel ribotModel,
                                   ActivityOptionsCompat optionsCompat) {
        Intent intent = new Intent(context,RibotActivity.class);
        intent.putExtra(RIBOT,ribotModel);
        context.startActivity(intent,optionsCompat.toBundle());
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ribot);
        ButterKnife.bind(this);

        dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy");
        dateTimeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        setupView();

        RibotModel ribot = getIntent().getParcelableExtra(RIBOT);
        setUpWithRibot(ribot);
    }

    private void setupView() {
        supportPostponeEnterTransition();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if ( actionBar != null ) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void setUpWithRibot(final RibotModel ribot) {
        title.setText(getString(R.string.full_name_format,ribot.getFirstName(),ribot.getLastName()));

        @ColorInt int color = Color.parseColor(ribot.getColor());
        toolbar.setBackgroundColor(color);
        getWindow().setStatusBarColor(color);

        if ( ribot.getBio() != null ) {
            bio.setText(ribot.getBio());
        }
        date.setText(dateTimeFormatter.format(ribot.getDateOfBirth()));

        ViewCompat.setTransitionName(avatar,ribot.getId());
        supportStartPostponedEnterTransition();

        Drawable placeholder = getDrawable(R.drawable.small_ribot_logo);
        if ( placeholder != null ) {
            placeholder = placeholder.mutate();
            DrawableCompat.setTint(placeholder,color);
        }

        Glide.with(this)
                .load(ribot.getAvatar())
                .apply(RequestOptions.fitCenterTransform()
                        .placeholder(placeholder))
                .into(avatar);

        emailFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO)
                        .putExtra(Intent.EXTRA_EMAIL,ribot.getEmail());
                if ( emailIntent.resolveActivity(getPackageManager())!=null) {
                    startActivity(emailIntent);
                } else {
                    Toast.makeText(RibotActivity.this,
                            "Oops. You need an email app to do that.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
