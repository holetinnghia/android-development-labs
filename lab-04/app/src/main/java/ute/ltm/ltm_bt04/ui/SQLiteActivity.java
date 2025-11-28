package ute.ltm.ltm_bt04.ui;

import ute.ltm.ltm_bt04.*;
import ute.ltm.ltm_bt04.adapter.NotesAdapter;
import ute.ltm.ltm_bt04.model.NotesModel;
import ute.ltm.ltm_bt04.sqlite.DatabaseHandler;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SQLiteActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;
    ImageButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        Toast.makeText(this, "Đã vào trang SQLite", Toast.LENGTH_LONG).show();

        listView = findViewById(R.id.listViewNotes);
        fabAdd = findViewById(R.id.fabAdd);
        arrayList = new ArrayList<>();

        adapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);

        databaseHandler = new DatabaseHandler(this);

        GetDataNotes();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem(); // Gọi hàm hiện Dialog
            }
        });
    }

    private void GetDataNotes() {
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        arrayList.clear();
        if (cursor != null) {
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                arrayList.add(new NotesModel(id, ten));
            }

            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAddNotes){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notes);

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText("THÊM GHI CHÚ"); // Tái sử dụng layout

        EditText editText = dialog.findViewById(R.id.editTextNotes);
        Button btnThem = dialog.findViewById(R.id.buttonXacNhan);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = editText.getText().toString().trim();
                if(ten.equals("")){
                    Toast.makeText(SQLiteActivity.this, "Vui lòng nhập tên ghi chú", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHandler.QueryData("INSERT INTO Notes VALUES(null, '" + ten + "')");
                    Toast.makeText(SQLiteActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataNotes();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DialogCapNhatNotes(String ten, final int id){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notes);

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        txtTitle.setText("CẬP NHẬT GHI CHÚ");

        EditText editText = dialog.findViewById(R.id.editTextNotes);
        Button btnXacNhan = dialog.findViewById(R.id.buttonXacNhan);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        editText.setText(ten);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = editText.getText().toString().trim();
                if(tenMoi.equals("")){
                    Toast.makeText(SQLiteActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHandler.QueryData("UPDATE Notes SET NameNotes = '" + tenMoi + "' WHERE Id = '" + id + "'");
                    Toast.makeText(SQLiteActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataNotes();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DialogDelete(String ten, final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa ghi chú '" + ten + "' không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHandler.QueryData("DELETE FROM Notes WHERE Id = '" + id + "'");
                Toast.makeText(SQLiteActivity.this, "Đã xóa " + ten, Toast.LENGTH_SHORT).show();
                GetDataNotes();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}