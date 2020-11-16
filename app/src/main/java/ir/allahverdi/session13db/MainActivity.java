package ir.allahverdi.session13db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import ir.allahverdi.session13db.adapter.ProductAdapter;
import ir.allahverdi.session13db.database.ProductDbHelper;
import ir.allahverdi.session13db.entity.Product;
import ir.allahverdi.session13db.ui.DetailsActivity;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ProductAdapter adapter;
    EditText et_search;

    String[] names = {"کبوتر پرشی بوم ندیده",
            "ماشین قدرتی خاور",
            "اکانت توییتر پاک پاک",
            "نماز و روزه اموات",
            "مقداری عسل",
            "چنگال یکبار مصرف",
            "فرمون آردی آی",
            "شهاب سنگ خاص",
            "کارتن موز",
            "شیشه خالی",};

    int[] prices = {85000, 47000, 86000, 12000, 98000, 5000, 61000, 45000, 15000, 35000};

    int[] imagesId = {R.drawable.kaftar,
            R.drawable.khavar,
            R.drawable.twitter_account,
            R.drawable.namaz,
            R.drawable.asal,
            R.drawable.changal,
            R.drawable.farmoon,
            R.drawable.shahabsang,
            R.drawable.carton,
            R.drawable.shishe,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
        setupToolbar();
        setupNavigationView();
        //insertIntoDatabase();
        selectFromDatabase();
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

    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view_details);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_menu_insert:
                        insertIntoDatabase();
                        selectFromDatabase();
                        break;
                    case R.id.nav_menu_register:
                        Toast.makeText(MainActivity.this, "انتقال به صفحه ثبت نام", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_profile:
                        Toast.makeText(MainActivity.this, "انتقال به صفحه پروفایل کاربری", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_orderTracking:
                        Toast.makeText(MainActivity.this, "انتقال به صفحه پیگیری سفارش", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_reportBug:
                        Toast.makeText(MainActivity.this, "انتقال به صفحه گزارش مشکل", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_help:
                        Toast.makeText(MainActivity.this, "انتقال به صفحه راهنما", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_menu_contactUs:
                        Toast.makeText(MainActivity.this, "انتقال به صفحه تماس با ما", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    private void selectFromDatabase() {
        adapter = new ProductAdapter(this, getDataFromDatabase());
        listView.setAdapter(adapter);
    }

    private void insertIntoDatabase() {
        long result = 0;
        for (int i = 0; i < names.length; i++) {
            Product product = new Product(names[i], prices[i], imagesId[i]);
            result = new ProductDbHelper(this).insert(product);
        }
        Toast.makeText(this, "number of inserts : " + String.valueOf(result), Toast.LENGTH_LONG).show();
    }

    private void init() {

        listView = findViewById(R.id.listview);
        et_search = findViewById(R.id.et_search);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = et_search.getText().toString();
                List<Product> list = new ProductDbHelper(MainActivity.this).select(search);
                adapter = new ProductAdapter(MainActivity.this, list);
                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(Const.INTENT_PARAMETERS_ID, product.getId());
                intent.putExtra(Const.INTENT_PARAMETERS_IMAGE, product.getImageId());

                startActivityForResult(intent, 73);
            }
        });
    } // end init()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 73) {
            if (resultCode == RESULT_OK) {
                adapter = new ProductAdapter(this, getDataFromDatabase());
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "result code : CANCEL", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<Product> getDataFromDatabase() {

        return new ProductDbHelper(this).select();
    }

}