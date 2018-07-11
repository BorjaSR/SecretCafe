package es.bsalazar.secretcafe.app.drinks.detail;

import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.bsalazar.secretcafe.Injector;
import es.bsalazar.secretcafe.R;
import es.bsalazar.secretcafe.data.entities.Drink;
import es.bsalazar.secretcafe.data.remote.StorageManager;
import es.bsalazar.secretcafe.utils.Constants;
import es.bsalazar.secretcafe.utils.ShowState;

public class DrinkDetailActivity extends AppCompatActivity {

    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.sticky_header_detail)
    LinearLayout sticky_header_detail;
    @BindView(R.id.sticky_name_drink_detail)
    TextView sticky_name;
    @BindView(R.id.image_drink_detail)
    ImageView image;
    @BindView(R.id.name_drink_detail)
    TextView name;
    @BindView(R.id.drink_description_detail)
    TextView description;
    @BindView(R.id.drink_price_detail)
    TextView price;

    private Unbinder unbinder;
    private Drink drink;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        unbinder = ButterKnife.bind(this);

        drink = (Drink) getIntent().getSerializableExtra(Constants.EXTRA_KEY_DRINK);
        byte[] byteArray = getIntent().getByteArrayExtra(Constants.EXTRA_KEY_BYTE_ARRAY);

        if (byteArray != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            image.setImageBitmap(bmp);
        } else
            image.setImageDrawable(getDrawable(R.drawable.default_image));

        setView();
    }

    @OnClick(R.id.container_drink_detail)
    public void pressBackground() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setView() {

        //SCROLL BEHAVIOR
        scroll.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY >= image.getHeight())
                sticky_header_detail.setVisibility(View.VISIBLE);
            else
                sticky_header_detail.setVisibility(View.GONE);
        });


        if (drink != null) {
            name.setText(drink.getName());
            sticky_name.setText(drink.getName());
            description.setText(drink.getDescription());
            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
            price.setText(formatoImporte.format(drink.getPrice()));
        }
    }
}
