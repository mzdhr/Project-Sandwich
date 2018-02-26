package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getSimpleName();
    private ImageView mSandwichImageView;
    private TextView mPlaceOfOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mIngredientsTextView;
    private TextView mAlsoKnowTextView;
    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findViews();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = null;
        try {
            mSandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(mSandwichImageView);

        setTitle(mSandwich.getMainName());
    }

    private void findViews() {
        mSandwichImageView = findViewById(R.id.image_iv);
        mPlaceOfOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);
        mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        mAlsoKnowTextView = (TextView) findViewById(R.id.also_known_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        StringBuilder alsoKnownAsStringBuilder = new StringBuilder();
        for (int i = 0; i < mSandwich.getAlsoKnownAs().size(); i++) {
            alsoKnownAsStringBuilder.append(mSandwich.getAlsoKnownAs().get(i));
        }
        StringBuilder ingredientsStringBuilder = new StringBuilder();
        for (int i = 0; i < mSandwich.getIngredients().size(); i++) {
            ingredientsStringBuilder.append(mSandwich.getIngredients().get(i));
        }

        mAlsoKnowTextView.setText(alsoKnownAsStringBuilder.toString());
        mIngredientsTextView.setText(ingredientsStringBuilder.toString());
        mPlaceOfOriginTextView.setText(mSandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(mSandwich.getDescription());
    }
}
