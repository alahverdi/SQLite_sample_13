package ir.allahverdi.session13db.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.allahverdi.session13db.R;
import ir.allahverdi.session13db.entity.Product;

public class ProductAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return this.productList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_items_layout , null);
        }

        Product product = this.productList.get(position);

        TextView tv_id = view.findViewById(R.id.tv_id);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_price = view.findViewById(R.id.tv_price);
        ImageView iv_item = view.findViewById(R.id.iv_item);

        tv_id.setText(String.valueOf(product.getId()));
        tv_name.setText(product.getName());
        tv_price.setText(String.valueOf(product.getPrice()));
        switch (tv_id.getText().toString()) {
            case "1":
                iv_item.setImageResource(R.drawable.kaftar);
                break;
            case "2":
                iv_item.setImageResource(R.drawable.khavar);
                break;
            case "3":
                iv_item.setImageResource(R.drawable.twitter_account);
                break;
            case "4":
                iv_item.setImageResource(R.drawable.namaz);
                break;
            case "5":
                iv_item.setImageResource(R.drawable.asal);
                break;
            case "6":
                iv_item.setImageResource(R.drawable.changal);
                break;
            case "7":
                iv_item.setImageResource(R.drawable.farmoon);
                break;
            case "8":
                iv_item.setImageResource(R.drawable.shahabsang);
                break;
            case "9":
                iv_item.setImageResource(R.drawable.carton);
                break;
            case "10":
                iv_item.setImageResource(R.drawable.shishe);
                break;
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        return null;
    }


}
