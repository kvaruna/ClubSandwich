package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView tv_alsoKnownAs;
    private TextView tv_originFrom;
    private TextView tv_description;
    private TextView tv_ingredients;
    private ImageView iv_ingredients;

    private Sandwich sandwich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        iv_ingredients = findViewById(R.id.image_iv);
        tv_alsoKnownAs = findViewById(R.id.also_known_tv);
        tv_description = findViewById(R.id.description_tv);
        tv_ingredients = findViewById(R.id.ingredients_tv);
        tv_originFrom = findViewById(R.id.origin_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }


        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);


        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(json);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(iv_ingredients);

        setTitle("Details of "+sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    public StringBuilder list(List<String> list){
        StringBuilder stringBuilder= new StringBuilder();
        for (int i=0;i<list.size();i++){
            stringBuilder.append(list.get(i)).append("\n");
        }
        return stringBuilder;
    }

    private void populateUI(String json) {
        Log.d("This is json", "" + json);
        if (sandwich.getPlaceOfOrigin().isEmpty()){
            tv_originFrom.setText(R.string.empty_data);
        }else {
            tv_originFrom.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getAlsoKnownAs().isEmpty()){
            tv_alsoKnownAs.setText(R.string.empty_data);
        }else {
            tv_alsoKnownAs.setText(list(sandwich.getAlsoKnownAs()));
        }
        tv_description.setText(sandwich.getDescription());
        tv_ingredients.setText(list(sandwich.getIngredients()));
    }


}
