package com.example.capstone06.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone06.Contact;
import com.example.capstone06.IntroActivity;
import com.example.capstone06.Login;
import com.example.capstone06.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class BottomMypage extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference storageReference;
    private Uri imageUri;
    Button btnRevoke, btnLogout, btnContact;
    private FirebaseAuth mAuth;
    private String TAG = "프래그먼트";

    // 구글api클라이언트
    private GoogleSignInClient mGoogleSignInClient;

    // 구글 계정
    private GoogleSignInAccount gsa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View fragmentView = inflater.inflate(R.layout.bottom_mypage, container, false);

        mAuth = FirebaseAuth.getInstance();
        // 현재 로그인한 사용자 가져오기
        FirebaseUser currentUser = mAuth.getCurrentUser();

        btnLogout = fragmentView.findViewById(R.id.btn_logout);
        btnRevoke = fragmentView.findViewById(R.id.btn_revoke);

        btnLogout.setOnClickListener(this);
        btnRevoke.setOnClickListener(this);

        if (currentUser != null) {
            String displayName = currentUser.getDisplayName(); // 사용자의 닉네임 가져오기
            // displayName을 @+id/nickname 텍스트뷰에 설정
            TextView nicknameTextView = fragmentView.findViewById(R.id.nickname);
            nicknameTextView.setText(displayName);
        }

        // TODO: 회원정보 수정하기 버튼 클릭 시
        Button editProfileButton = fragmentView.findViewById(R.id.btn_edit_profile);
        editProfileButton.setOnClickListener(view -> {
            // TextView를 EditText로 변경
            EditText nicknameEditText = fragmentView.findViewById(R.id.nickname);
            editProfileButton.setText("완료"); // 버튼 텍스트를 '완료'로 변경

            nicknameEditText.setEnabled(true); // EditText를 수정 가능하도록 설정

            // 완료 버튼 클릭 시
            editProfileButton.setOnClickListener(v -> {
                String updatedNickname = nicknameEditText.getText().toString();
                // Firebase 데이터베이스에 닉네임 업데이트 작업 수행
                updateNicknameInDatabase(updatedNickname);

                // EditText를 TextView로 재변경
                nicknameEditText.setEnabled(false);
                nicknameEditText.setFocusable(false);
                nicknameEditText.setFocusableInTouchMode(false);
                editProfileButton.setText("회원정보 수정하기"); // 버튼 텍스트를 다시 '회원정보 수정하기'로 변경
            });
        });

        // TODO: ImageView 클릭 시
        ImageView profileImageView = fragmentView.findViewById(R.id.profile_image);
        profileImageView.setOnClickListener(v -> {
            // 갤러리 또는 카메라에서 이미지 선택하여 업로드하는 코드 작성
            // 선택한 이미지를 Firebase Storage에 업로드하고 URL을 받아옵니다.
            // 받아온 URL을 Firebase 데이터베이스에 저장합니다.
            // 이미지 업로드 관련 코드를 추가하세요.
            openFileChooser();
        });

        return fragmentView;
    }


    private void updateNicknameInDatabase(String updatedNickname) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            // 사용자의 닉네임을 업데이트합니다.
            databaseReference.child("nickname").setValue(updatedNickname)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "닉네임이 업데이트되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "닉네임 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            // 이미지 업로드
            uploadImageToFirebaseStorage(imageUri);
        }
    }

    private void uploadImageToFirebaseStorage(Uri imageUri) {
        // 이미지를 업로드할 경로 및 파일명 설정 (예: profile_images 폴더에 userId.jpg 형태로 저장)
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_images/" + userId + ".jpg");

        // 이미지 업로드
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // 업로드 성공 시 이미지 다운로드 URL 받아오기
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        // 받아온 URL을 Firebase 데이터베이스에 저장하는 코드 추가
                        updateImageUrlInDatabase(imageUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    // 업로드 실패 시 처리
                    Toast.makeText(requireContext(), "이미지 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateImageUrlInDatabase(String imageUrl) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            // 사용자의 이미지 URL을 업데이트합니다.
            databaseReference.child("imageUrl").setValue(imageUrl)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "이미지 URL이 업데이트되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "이미지 URL 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();

        // 구글 로그아웃 처리
        if (mGoogleSignInClient != null) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(requireActivity(), task -> {
                        gsa = null;
                        Toast.makeText(requireContext(), R.string.success_logout, Toast.LENGTH_SHORT).show();

                        // 로그아웃 후 Login 액티비티로 이동
                        Intent intent = new Intent(requireContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        requireActivity().finish();
                    });
        }
    }

    private void revokeAccess() {
        mAuth.getCurrentUser().delete();

        // 구글 계정에 대한 액세스 철회 처리
        if (mGoogleSignInClient != null) {
            mGoogleSignInClient.revokeAccess()
                    .addOnCompleteListener(requireActivity(), task -> {
                        // 처리 완료 시 작업 수행
                    });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_logout) {
            signOut();
            Intent intent = new Intent(requireContext(), IntroActivity.class);
            startActivity(intent);
            requireActivity().finish(); // 현재 액티비티만 종료
        } else if (v.getId() == R.id.btn_revoke) {
            revokeAccess();
            Intent intent = new Intent(requireContext(), IntroActivity.class);
            startActivity(intent);
            requireActivity().finish(); // 현재 액티비티만 종료
        }
    }
}
