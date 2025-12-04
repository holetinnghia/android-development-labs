package ute.android.lab_07;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothControl extends AppCompatActivity {

    ImageButton btnTb1, btnTb2, btnDis;
    TextView txt1, txtMAC;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    Set<BluetoothDevice> pairedDevices1;
    String address = null;
    private ProgressDialog progress;
    int flaglamp1 = 0;
    int flaglamp2 = 0;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(BluetoothActivity.EXTRA_ADDRESS);

        setContentView(R.layout.activity_control);

        btnTb1 = (ImageButton) findViewById(R.id.btnTb1);
        btnTb2 = (ImageButton) findViewById(R.id.btnTb2);
        txt1 = (TextView) findViewById(R.id.textV1);
        txtMAC = (TextView) findViewById(R.id.textViewMAC);
        btnDis = (ImageButton) findViewById(R.id.btnDisc);

        new ConnectBT().execute();

        btnTb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thietTbil();
            }
        });

        btnTb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thiettbi7();
            }
        });

        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();
            }
        });
    }

    private void thietTbil() {
        if (btSocket != null) {
            try {
                if (this.flaglamp1 == 0) {
                    this.flaglamp1 = 1;
                    btSocket.getOutputStream().write("1".toString().getBytes());
                    txt1.setText("Thiết bị số 1 đang bật");
                    return;
                } else {
                    if (this.flaglamp1 != 1) return;

                    this.flaglamp1 = 0;
                    btSocket.getOutputStream().write("A".toString().getBytes());
                    txt1.setText("Thiết bị số 1 đang tắt");
                    return;
                }
            } catch (IOException e) {
                msg("Lỗi");
            }
        }
    }

    private void thiettbi7() {
        if (btSocket != null) {
            try {
                if (this.flaglamp2 == 0) {
                    this.flaglamp2 = 1;
                    btSocket.getOutputStream().write("7".toString().getBytes());
                    txt1.setText("Thiết bị số 7 đang bật");
                    return;
                } else {
                    if (this.flaglamp2 != 1) return;

                    this.flaglamp2 = 0;
                    btSocket.getOutputStream().write("G".toString().getBytes());
                    txt1.setText("Thiết bị số 7 đang tắt");
                    return;
                }
            } catch (IOException e) {
                msg("Lỗi");
            }
        }
    }

    private void Disconnect() {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
            }
        }
        msg("Ngắt kết nối thành công");
        finish();
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(BluetoothControl.this, "Đang kết nối...", "Xin vui lòng đợi!!!");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);

                    if (ActivityCompat.checkSelfPermission(BluetoothControl.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        return null;
                    }

                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                msg("Kết nối thất bại. Kiểm tra thiết bị.");
                finish();
            } else {
                msg("Kết nối thành công.");
                isBtConnected = true;
                pairedDevicesList1();
            }
            progress.dismiss();
        }
    }

    private void pairedDevicesList1() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        pairedDevices1 = myBluetooth.getBondedDevices();

        if (pairedDevices1.size() > 0) {
            for (BluetoothDevice bt : pairedDevices1) {
                if (bt.getAddress().equals(address)) {
                    txtMAC.setText(bt.getName() + " - " + bt.getAddress());
                    return;
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Không tìm thấy thiết bị kết nối.", Toast.LENGTH_LONG).show();
        }
    }
}