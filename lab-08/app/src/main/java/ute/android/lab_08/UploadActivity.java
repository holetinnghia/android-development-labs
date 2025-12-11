package ute.android.lab_08;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    private VideoView videoPreview;
    private Button btnPickVideo, btnUpload;
    private ProgressBar progressBar;
    private TextView tvStatus;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Map config = new HashMap();
        config.put("cloud_name", BuildConfig.CLOUD_NAME);
        config.put("api_key", BuildConfig.API_KEY);
        config.put("api_secret", BuildConfig.API_SECRET);

        try {
            MediaManager.init(this, config);
        } catch (Exception e) {
        }

        videoPreview = findViewById(R.id.videoPreview);
        btnPickVideo = findViewById(R.id.btnPickVideo);
        btnUpload = findViewById(R.id.btnUpload);
        progressBar = findViewById(R.id.progressBar);
        tvStatus = findViewById(R.id.tvStatus);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        videoUri = uri;
                        videoPreview.setVideoURI(videoUri);
                        videoPreview.start();
                        btnUpload.setEnabled(true);
                        tvStatus.setText("Đã chọn video.");
                    }
                });

        btnPickVideo.setOnClickListener(v -> mGetContent.launch("video/*"));
        btnUpload.setOnClickListener(v -> uploadVideoToCloudinary());
    }

    private void uploadVideoToCloudinary() {
        if (videoUri != null) {
            progressBar.setVisibility(View.VISIBLE);
            tvStatus.setText("Đang upload video...");
            btnUpload.setEnabled(false);

            MediaManager.get().upload(videoUri)
                    .option("resource_type", "video")
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            double progress = (double) bytes / totalBytes;
                            runOnUiThread(() -> {
                                progressBar.setProgress((int) (progress * 100));
                                tvStatus.setText("Đang tải lên Cloud: " + (int) (progress * 100) + "%");
                            });
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            String url = (String) resultData.get("secure_url");

                            saveVideoToFirestore(url);
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            runOnUiThread(() -> {
                                progressBar.setVisibility(View.GONE);
                                btnUpload.setEnabled(true);
                                tvStatus.setText("Lỗi Upload: " + error.getDescription());
                            });
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                        }
                    })
                    .dispatch();
        }
    }

    private void saveVideoToFirestore(String videoUrl) {
        runOnUiThread(() -> tvStatus.setText("Đang lưu vào Database..."));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> videoData = new HashMap<>();
        videoData.put("videoUrl", videoUrl);
        videoData.put("userId", userId);
        videoData.put("createdAt", System.currentTimeMillis());
        videoData.put("likes", 0);

        db.collection("videos")
                .add(videoData)
                .addOnSuccessListener(documentReference -> {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        tvStatus.setText("Thành công! Đã lưu video vào Database.");
                        Toast.makeText(UploadActivity.this, "Hoàn tất 100%!", Toast.LENGTH_SHORT).show();
                        btnUpload.setEnabled(true);
                    });
                })
                .addOnFailureListener(e -> {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        btnUpload.setEnabled(true);
                        tvStatus.setText("Lỗi lưu DB: " + e.getMessage());
                    });
                });
    }
}