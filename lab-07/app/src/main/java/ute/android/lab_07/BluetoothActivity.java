package ute.android.lab_07;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {
    public static int REQUEST_BLUETOOTH = 1;
    public static final int REQUEST_BLUETOOTH_CONNECT = 2; // THÊM DÒNG NÀY

    Button btnPaired;
    ListView listDanhSach;

    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        btnPaired = (Button) findViewById(R.id.btnTimthietbi);
        listDanhSach = (ListView) findViewById(R.id.listTb);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (myBluetooth == null) {
            Toast.makeText(getApplicationContext(), "Thiết bị không hỗ trợ Bluetooth", Toast.LENGTH_LONG).show();
            finish();
        } else if (!myBluetooth.isEnabled()) {
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, REQUEST_BLUETOOTH);
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList();
            }
        });

        listDanhSach.setOnItemClickListener(myListClickListener);
    }

    private void pairedDevicesList() {
        // KIỂM TRA QUYỀN BLUETOOTH_CONNECT
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            // NẾU CHƯA CÓ QUYỀN: HIỂN THỊ DIALOG XIN QUYỀN
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                    REQUEST_BLUETOOTH_CONNECT);

            Toast.makeText(getApplicationContext(), "Đang yêu cầu cấp quyền...", Toast.LENGTH_SHORT).show();
            return; // Dừng lại, chờ kết quả từ dialog
        }

        // --- CODE CHỈ CHẠY KHI ĐÃ CÓ QUYỀN ---

        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList<String> list = new ArrayList<>();

        // ... (phần còn lại của hàm giữ nguyên)
        // ...

        // listDanhSach.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            Intent i = new Intent(BluetoothActivity.this, BluetoothControl.class);
            i.putExtra(EXTRA_ADDRESS, address);
            startActivity(i);
        }
    };
}