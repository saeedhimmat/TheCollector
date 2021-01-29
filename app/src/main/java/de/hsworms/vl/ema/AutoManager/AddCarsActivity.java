package de.hsworms.vl.ema.AutoManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class AddCarsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Cars carItem=new Cars();
    Bitmap bitmap =null;
    String Default;
    private ImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    TextView lastservice;
    ArrayList<Cars> mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        Intent intent=getIntent();
        mainList =intent.getParcelableArrayListExtra("TheOne");

        setContentView(R.layout.cars_activity);



        TextView hersteller = (TextView)findViewById(R.id.editTextTextPersonName2);
        TextView model = (TextView)findViewById(R.id.editTextTextPersonName4);
        TextView bauJahr = (TextView)findViewById(R.id.editTextTextPersonName6);
        TextView pS = (TextView)findViewById(R.id.editTextTextPersonName8);
        TextView typ = (TextView)findViewById(R.id.editTextTextPersonName10);
        TextView kiloMeter = (TextView)findViewById(R.id.editTextTextPersonName12);
        TextView preis = (TextView)findViewById(R.id.editTextTextPersonName14);
        lastservice = (TextView)findViewById(R.id.editTextTextPersonName16);

        lastservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickupFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button addButton=(Button)findViewById(R.id.buttonAdd);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String herstell = hersteller.getText().toString();
                String mod = model.getText().toString();
                String bau = bauJahr.getText().toString();
                String p = pS.getText().toString();
                String typp = typ.getText().toString();
                String kilom = kiloMeter.getText().toString();
                String preiss = preis.getText().toString();
                String lastserves = lastservice.getText().toString();

                if(bitmap ==null){
                    Default = BitmapExtraConverters.BitMapToString(BitmapExtraConverters.getBitmapFromVectorDrawable(getApplicationContext(),R.drawable.ic_android));
                }
                else {
                    Default=BitmapExtraConverters.BitMapToString(bitmap);
                }

                   carItem.setmImageResource(Default);
                   carItem.setName(herstell);
                   carItem.setModel(mod);
                   carItem.setBauJahr(bau);
                   carItem.setPs(p);
                   carItem.setTyp(typp);
                   carItem.setKM(kilom);
                   carItem.setPreis(preiss);
                   carItem.setLastServer(lastserves);
                    mainList.add(carItem);





                ArrayList<Cars> mainlisted;
                Intent intent=getIntent();
                mainlisted =intent.getParcelableArrayListExtra("TheOne");
                Intent resultIntentButton = new Intent();
                resultIntentButton.putExtra("Cars",mainlisted);
                setResult(RESULT_OK,resultIntentButton);
                finish();
            }

        });






        ProfileImage = (ImageView) findViewById(R.id.imageView11);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), PICK_IMAGE);
            }
        });






    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Datum c= new Datum(dayOfMonth,month,year);
        String Time = c.GetTime(dayOfMonth,month,year);
        lastservice.setText(Time);
        carItem.setYearServer(year);
        carItem.setMonthServer(month);
        carItem.setDayServer(dayOfMonth);

        String serviceinString=carItem.ServiceIn();
        Context context = getApplicationContext();
        CharSequence text = serviceinString;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                bitmap =BitmapExtraConverters.getResizedBitmap(bitmap,400);
                ProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}