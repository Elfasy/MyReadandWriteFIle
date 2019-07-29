package affily.id.myreadandwritefile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnNew, btnOpen, btnSave;
    private EditText edtCotent, edtTitle;

    private File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtTitle = findViewById(R.id.edt_title);
        edtCotent = findViewById(R.id.edt_file);

        btnNew = findViewById(R.id.btn_new);
        btnNew.setOnClickListener(this);
        btnOpen = findViewById(R.id.btn_open);
        btnOpen.setOnClickListener(this);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

        path = getFilesDir();
    }

    public void newFile() {
        edtCotent.setText("");
        edtTitle.setText("");

        Toast.makeText(this, "clearing file", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title){
        FileModel fileModel = FileHelper.readFromFile(this,title);
        edtTitle.setText(fileModel.getFilename());
        edtCotent.setText(fileModel.getData());
        Toast.makeText(this,"Loading " + fileModel.getFilename() + " data",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_new:
                newFile();
                break;
            case R.id.btn_open:
                showList();
                break;
            case R.id.btn_save:
                saveFile();
                break;
        }
    }

    private void showList(){
        ArrayList<String> stringArrayList = new ArrayList<>();
        Collections.addAll(stringArrayList,path.list());

        final CharSequence[] items = stringArrayList.toArray(new CharSequence[stringArrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yg diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                loadData(items[i].toString());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void showToast(String pesan){
        Toast.makeText(this,pesan,Toast.LENGTH_SHORT).show();
    }
    public void saveFile(){
        if (edtTitle.getText().toString().isEmpty()){
            showToast("Title harus diisi terlebih dahulu");
        }else if (edtCotent.getText().toString().isEmpty()){
            showToast("Konten harus diisi terlebih dahulu");
        }else{
            String title = edtTitle.getText().toString().trim();
            String text = edtCotent.getText().toString().trim();
            FileModel fileModel = new FileModel();
            fileModel.setFilename(title);
            fileModel.setData(text);
            FileHelper.writeToFile(fileModel,this);
            showToast("saving " + fileModel.getFilename());
        }
    }
}
