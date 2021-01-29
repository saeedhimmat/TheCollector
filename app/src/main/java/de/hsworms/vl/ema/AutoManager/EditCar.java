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

public class EditCar extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    Bitmap bitmap=null;
    private ImageView ProfileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    Cars toEditItem;
    TextView serviceI;
    TextView lastServe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_editor_page);


       int position;
        Button Done=(Button)findViewById(R.id.buttonDone);
        Intent intent=getIntent();
        toEditItem =intent.getParcelableExtra("TheItem");
        position = intent.getIntExtra("ThePos",0);
        Intent DoneButtonResult= new Intent();




        TextView hersteller = (TextView)findViewById(R.id.editText2);
        TextView model = (TextView)findViewById(R.id.editText4);
        TextView bauJahr = (TextView)findViewById(R.id.editText6);
        TextView pS = (TextView)findViewById(R.id.editText8);
        TextView typ = (TextView)findViewById(R.id.editText10);
        TextView kiloMeter = (TextView)findViewById(R.id.editText12);
        TextView preis = (TextView)findViewById(R.id.editText14);
        lastServe = (TextView)findViewById(R.id.editText16);

        ProfileImage = (ImageView) findViewById(R.id.imageView111);

        lastServe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickupFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), PICK_IMAGE);
            }
        });


    ProfileImage.setImageBitmap(BitmapExtraConverters.StringToBitMap(toEditItem.getImageResource()));
    hersteller.setText(toEditItem.getName());
    model.setText(toEditItem.getModel());
    bauJahr.setText(toEditItem.getBauJahr());
    pS.setText(toEditItem.getPs());
    typ.setText(toEditItem.getTyp());
    kiloMeter.setText(toEditItem.getKM());
    preis.setText(toEditItem.getPreis());
    lastServe.setText(toEditItem.getLastServer());





        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap!=null){
                    toEditItem.setmImageResource(BitmapExtraConverters.BitMapToString(bitmap));
                }
                toEditItem.setName(hersteller.getText().toString());
                toEditItem.setModel(model.getText().toString());
                toEditItem.setBauJahr(bauJahr.getText().toString());
                toEditItem.setPs(pS.getText().toString());
                toEditItem.setTyp(typ.getText().toString());
                toEditItem.setKM(kiloMeter.getText().toString());
                toEditItem.setPreis(preis.getText().toString());


                DoneButtonResult.putExtra("Done",toEditItem);
                DoneButtonResult.putExtra("DonePos",position);
                setResult(RESULT_OK,DoneButtonResult);
                  finish();
            }
        });

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Datum c= new Datum(dayOfMonth,month,year);
        String Time = c.GetTime(dayOfMonth,month,year);
        lastServe.setText(Time);
        toEditItem.setYearServer(year);
        toEditItem.setMonthServer(month);
        toEditItem.setDayServer(dayOfMonth);
        toEditItem.setLastServer(Time);

        String serviceinString=toEditItem.ServiceIn();
        Context context = getApplicationContext();
        CharSequence text = serviceinString;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
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