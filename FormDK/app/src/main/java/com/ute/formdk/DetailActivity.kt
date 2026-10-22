package com.ute.formdk

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ute.formdk.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_FULL_NAME = "EXTRA_FULL_NAME"
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
        const val EXTRA_PHONE = "EXTRA_PHONE"
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
        const val EXTRA_CLASS_ROOM = "EXTRA_CLASS_ROOM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.detailMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        displayReceivedData()
        setupBackButton()
    }

    private fun displayReceivedData() {
        val fullName = intent.getStringExtra(EXTRA_FULL_NAME)
        val email = intent.getStringExtra(EXTRA_EMAIL)
        val phone = intent.getStringExtra(EXTRA_PHONE)
        val studentId = intent.getStringExtra(EXTRA_STUDENT_ID)
        val classRoom = intent.getStringExtra(EXTRA_CLASS_ROOM)

        if (!fullName.isNullOrBlank()) {
            binding.tvFullNameValue.text = fullName
        }
        if (!email.isNullOrBlank()) {
            binding.tvEmailValue.text = email
        }
        if (!phone.isNullOrBlank()) {
            binding.tvPhoneValue.text = phone
        }
        if (!studentId.isNullOrBlank()) {
            binding.tvStudentIdValue.text = studentId
        }
        if (!classRoom.isNullOrBlank()) {
            binding.tvClassRoomValue.text = classRoom
        }
    }

    private fun setupBackButton() {
        binding.btnBackToRegister.setOnClickListener {
            // Có button gọi Intent chuyển qua trang kia
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
