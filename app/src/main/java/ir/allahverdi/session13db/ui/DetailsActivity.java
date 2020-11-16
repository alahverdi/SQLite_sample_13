package ir.allahverdi.session13db.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import ir.allahverdi.session13db.Const;
import ir.allahverdi.session13db.R;
import ir.allahverdi.session13db.database.ProductDbHelper;
import ir.allahverdi.session13db.entity.Product;

public class DetailsActivity extends AppCompatActivity {

    int id , productImageId;
    EditText et_tittle_details , et_price_details , et_id_details;
    ImageView iv_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();

        setupToolbar();
        setupNavigationView();

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }

        id = intent.getIntExtra(Const.INTENT_PARAMETERS_ID , 0);
        productImageId = intent.getIntExtra(Const.INTENT_PARAMETERS_IMAGE , 0);
        //Toast.makeText(this, "image ID: " + productImageId , Toast.LENGTH_SHORT).show();
        Product product = new ProductDbHelper(this).select(id);

        et_id_details.setText(String.valueOf(product.getId()));
        et_tittle_details.setText(product.getName());
        et_price_details.setText(String.valueOf(product.getPrice()));
        iv_details.setImageResource(productImageId);
    }

    private void init() {
        et_tittle_details = findViewById(R.id.et_tittle_details);
        et_price_details = findViewById(R.id.et_price_details);
        et_id_details = findViewById(R.id.et_id_details);
        iv_details = findViewById(R.id.iv_details);
    }

    public void onclickDelete(View view) {
        int result = new ProductDbHelper(this).delete(id);
        Toast.makeText(this, "result : " + String.valueOf(result), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        setResult(RESULT_OK , intent);
        finish();
    }

    public void onclickUpdate(View view) {

        String name = et_tittle_details.getText().toString();
        int price = Integer.valueOf(et_price_details.getText().toString());
        int imageId = Integer.valueOf(iv_details.getId());


        Product product = new Product(id , name , price , imageId);

        int result = new ProductDbHelper(this).update(product);
        Toast.makeText(this, "result : " + String.valueOf(result), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        setResult(RESULT_OK , intent);
        finish();
    }

    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view_details);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_menu_priceChart:
                        Toast.makeText(DetailsActivity.this, "price chart clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_compare:
                        Toast.makeText(DetailsActivity.this, "compare clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_share:
                        Toast.makeText(DetailsActivity.this, "share clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_alert:
                        Toast.makeText(DetailsActivity.this, "alert clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_like:
                        Toast.makeText(DetailsActivity.this, "like clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_cart:
                        Toast.makeText(DetailsActivity.this, "shopping cart clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    private void setupToolbar() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout_DetailsActivity);
        Toolbar toolbar = findViewById(R.id.toolbar_details);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, 0, 0);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }
}