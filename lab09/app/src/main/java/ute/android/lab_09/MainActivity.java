package ute.android.lab_09;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private static final String URL_SERVER = "https://socket-io-server-430o.onrender.com";

    private EditText edtMessage;
    private Button btnSend;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messageList;

    private String myUserName = "User_Android";
    private String roomID = "room_support_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        recyclerView = findViewById(R.id.recyclerViewChat);

        messageList = new ArrayList<>();
        adapter = new MessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        try {
            mSocket = IO.socket(URL_SERVER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.e("SOCKET", "Lỗi đường dẫn Server");
        }

        mSocket.connect();

        mSocket.emit("join_room", roomID);

        mSocket.on("receive_message", onNewMessage);

        btnSend.setOnClickListener(v -> attemptSend());
    }

    private void attemptSend() {
        String messageContent = edtMessage.getText().toString().trim();
        if (messageContent.isEmpty()) return;

        JSONObject data = new JSONObject();
        try {
            data.put("senderId", myUserName);
            data.put("content", messageContent);
            data.put("roomId", roomID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("send_message", data);

        updateMessageList(myUserName, messageContent);

        edtMessage.setText("");
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String sender = data.getString("senderId");
                        String content = data.getString("content");

                        updateMessageList(sender, content);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void updateMessageList(String sender, String content) {
        messageList.add(new Message(sender, content));
        adapter.notifyItemInserted(messageList.size() - 1);
        recyclerView.scrollToPosition(messageList.size() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("receive_message", onNewMessage);
    }
}