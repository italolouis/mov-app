package br.com.movapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.movapp.R;
import br.com.movapp.dao.ImagemDAO;
import br.com.movapp.helper.PessoaHelper;
import br.com.movapp.model.Pessoa;
import br.com.movapp.retrofit.RetrofitInicializador;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener{
    private PessoaHelper helper;
    private Button btn_cadastro;
    private ImageView img_cadastro_photo;
    private Button btn_cadastro_photo;
    private final int SELECT_PHOTO = 101;
    private final int CAPTURE_PHOTO = 102;
    final private int REQUEST_CODE_WRITE_STORAGE = 1;
    private String retrieveImage;
    Uri uri;
    ImagemDAO imagemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new PessoaHelper(CadastroActivity.this);

        btn_cadastro = (Button) findViewById(R.id.btn_cadastro);
        btn_cadastro.setOnClickListener(CadastroActivity.this);

        btn_cadastro_photo = (Button) findViewById(R.id.btnCadastro_photo);
        btn_cadastro_photo.setOnClickListener(CadastroActivity.this);

        img_cadastro_photo = (ImageView) findViewById(R.id.imageViewCadastro_photo);

        imagemDAO = new ImagemDAO(CadastroActivity.this);
        imagemDAO.open();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = imageReturnedIntent.getData();
                    String selectedImagePath = getPath(imageUri);

                    String dateTime = getCurrentTime();
                    imagemDAO.saveImagePath(selectedImagePath,dateTime);

                    retrieveImage = imagemDAO.getImagePath();
                    File newImageFile = new File(retrieveImage);

                    Picasso.with(CadastroActivity.this).load(Uri.fromFile(newImageFile)).into(img_cadastro_photo);

                }
                break;

            case CAPTURE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap bmp = imageReturnedIntent.getExtras().getParcelable("data");
                    helper.setImagem(bmp);
                    img_cadastro_photo.setImageBitmap(bmp);
                }
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if(v == btn_cadastro){
            Pessoa pessoa = helper.getPessoa();
            /*PessoaDAO pessoaDAO = new PessoaDAO(this);
            pessoaDAO.inserePessoa(pessoa);
            pessoaDAO.close();*/

            Call call = new RetrofitInicializador().getUsuarioSerice().insere(pessoa);
            //Metodo assimilar ao execute, mas vai fazer a execução assincrona
            call.enqueue(new Callback() {
                @Override
                //Conseguiu conectar com o servidor
                public void onResponse(Call call, Response response) {
                    if(response.code() == 201){
                        Log.i("onResponde", "Requisicao com sucesso");
                        Intent loginIntent = new Intent(CadastroActivity.this, LoginActivity.class);
                        CadastroActivity.this.startActivity(loginIntent);
                    }
                }

                //Nao deu certo a execução
                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.e("onFailure", "Requisicao falhou");
                    Toast.makeText(CadastroActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }else if(v == btn_cadastro_photo){
            int hasWriteStoragePermission = 0;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                hasWriteStoragePermission = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_WRITE_STORAGE);
                }
            }

            listDialogue();
        }
    }

    public void listDialogue(){
        final ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        arrayAdapter.add("Take Photo");
        arrayAdapter.add("Select Gallery");

        ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(arrayAdapter);

        final MaterialDialog alert = new MaterialDialog(this).setContentView(listView);

        alert.setPositiveButton("Cancel", new View.OnClickListener() {
            @Override public void onClick(View v) {
                alert.dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                    alert.dismiss();
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "propic.jpg";
                    uri = Uri.parse(root);
                    startActivityForResult(i, CAPTURE_PHOTO);

                }else {

                    alert.dismiss();
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);

                }
            }
        });

        alert.show();

    }

    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null,
                null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }


}
