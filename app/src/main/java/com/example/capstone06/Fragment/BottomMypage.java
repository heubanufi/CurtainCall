package com.example.capstone06.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.capstone06.Contact;
import com.example.capstone06.IntroActivity;
import com.example.capstone06.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BottomMypage extends Fragment implements View.OnClickListener {
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
        View view = inflater.inflate(R.layout.bottom_mypage, container, false);

        mAuth = FirebaseAuth.getInstance();
        // 현재 로그인한 사용자 가져오기
        FirebaseUser currentUser = mAuth.getCurrentUser();

        btnLogout = view.findViewById(R.id.btn_logout);
        btnRevoke = view.findViewById(R.id.btn_revoke);

        btnLogout.setOnClickListener(this);
        btnRevoke.setOnClickListener(this);

        if (currentUser != null) {
            String displayName = currentUser.getDisplayName(); // 사용자의 닉네임 가져오기
            // displayName을 @+id/nickname 텍스트뷰에 설정
            TextView nicknameTextView = view.findViewById(R.id.nickname);
            nicknameTextView.setText(displayName);
        }

        // 여기서 구글 API 클라이언트를 가져옵니다. MainActivity에서 가져오도록 수정해야 할 수도 있습니다.
        //mGoogleSignInClient = Login.getGoogleSignInClient();

        btnContact = view.findViewById(R.id.btn_contact); // XML에서 버튼을 찾아와 변수에 할당
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btn_contact 버튼 클릭 시 Contact 액티비티로 이동하는 코드
                Intent intent = new Intent(getContext(), Contact.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();

        // 구글 로그아웃 처리
        if (mGoogleSignInClient != null) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(requireActivity(), task -> {
                        gsa = null;
                        Toast.makeText(requireContext(), R.string.success_logout, Toast.LENGTH_SHORT).show();
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
