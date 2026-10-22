package com.ute.formdk

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.ute.formdk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRealtimeValidation()
        setupButtons()
    }

    /**
     * Xác thực theo thời gian thực (realtime) khi người dùng nhập dữ liệu
     */
    private fun setupRealtimeValidation() {
        // Họ và tên
        binding.etFullName.doOnTextChanged { text, _, _, _ ->
            if (binding.tilFullName.isErrorEnabled && !text.isNullOrBlank()) {
                binding.tilFullName.error = null
            }
        }

        // Email
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            val email = text?.toString()?.trim().orEmpty()
            if (binding.tilEmail.isErrorEnabled) {
                if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.tilEmail.error = null
                }
            }
        }

        // Số điện thoại
        binding.etPhone.doOnTextChanged { text, _, _, _ ->
            val phone = text?.toString()?.trim().orEmpty()
            if (binding.tilPhone.isErrorEnabled) {
                if (phone.length in 10..11 && phone.all { it.isDigit() }) {
                    binding.tilPhone.error = null
                }
            }
        }

        // Mã sinh viên (MSV)
        binding.etStudentId.doOnTextChanged { text, _, _, _ ->
            val studentId = text?.toString()?.trim().orEmpty()
            if (binding.tilStudentId.isErrorEnabled) {
                if (studentId.isNotEmpty() && studentId.length >= 5) {
                    binding.tilStudentId.error = null
                }
            }
        }

        // Lớp
        binding.etClassRoom.doOnTextChanged { text, _, _, _ ->
            val classRoom = text?.toString()?.trim().orEmpty()
            if (binding.tilClassRoom.isErrorEnabled && classRoom.isNotEmpty()) {
                binding.tilClassRoom.error = null
            }
        }
    }

    private fun setupButtons() {
        // Nút Đăng ký: Kiểm tra thông tin -> Pass hết thì chuyển sang Chi tiết mang theo dữ liệu
        binding.btnRegister.setOnClickListener {
            val isFullNameValid = validateFullName()
            val isEmailValid = validateEmail()
            val isPhoneValid = validatePhone()
            val isStudentIdValid = validateStudentId()
            val isClassRoomValid = validateClassRoom()

            if (isFullNameValid && isEmailValid && isPhoneValid && isStudentIdValid && isClassRoomValid) {
                val fullName = binding.etFullName.text?.toString()?.trim().orEmpty()
                val email = binding.etEmail.text?.toString()?.trim().orEmpty()
                val phone = binding.etPhone.text?.toString()?.trim().orEmpty()
                val studentId = binding.etStudentId.text?.toString()?.trim().orEmpty()
                val classRoom = binding.etClassRoom.text?.toString()?.trim().orEmpty()

                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_FULL_NAME, fullName)
                    putExtra(DetailActivity.EXTRA_EMAIL, email)
                    putExtra(DetailActivity.EXTRA_PHONE, phone)
                    putExtra(DetailActivity.EXTRA_STUDENT_ID, studentId)
                    putExtra(DetailActivity.EXTRA_CLASS_ROOM, classRoom)
                }
                startActivity(intent)
            }
        }

        // Button gọi Intent trực tiếp chuyển qua trang Chi tiết (theo yêu cầu: mỗi màn hình có button gọi intent chuyển qua trang kia)
        binding.btnGoToDetail.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateFullName(): Boolean {
        val fullName = binding.etFullName.text?.toString()?.trim().orEmpty()
        return when {
            fullName.isEmpty() -> {
                binding.tilFullName.error = "Vui lòng nhập họ và tên"
                false
            }
            fullName.length < 2 -> {
                binding.tilFullName.error = "Họ và tên quá ngắn"
                false
            }
            else -> {
                binding.tilFullName.error = null
                true
            }
        }
    }

    private fun validateEmail(): Boolean {
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        return when {
            email.isEmpty() -> {
                binding.tilEmail.error = "Vui lòng nhập email"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.tilEmail.error = "Email không đúng định dạng (VD: example@email.com)"
                false
            }
            else -> {
                binding.tilEmail.error = null
                true
            }
        }
    }

    private fun validatePhone(): Boolean {
        val phone = binding.etPhone.text?.toString()?.trim().orEmpty()
        return when {
            phone.isEmpty() -> {
                binding.tilPhone.error = "Vui lòng nhập số điện thoại"
                false
            }
            !phone.all { it.isDigit() } || phone.length !in 10..11 -> {
                binding.tilPhone.error = "Số điện thoại không hợp lệ (gồm 10-11 chữ số)"
                false
            }
            else -> {
                binding.tilPhone.error = null
                true
            }
        }
    }

    private fun validateStudentId(): Boolean {
        val studentId = binding.etStudentId.text?.toString()?.trim().orEmpty()
        return when {
            studentId.isEmpty() -> {
                binding.tilStudentId.error = "Vui lòng nhập mã sinh viên"
                false
            }
            studentId.length < 5 -> {
                binding.tilStudentId.error = "Mã sinh viên phải có ít nhất 5 ký tự"
                false
            }
            else -> {
                binding.tilStudentId.error = null
                true
            }
        }
    }

    private fun validateClassRoom(): Boolean {
        val classRoom = binding.etClassRoom.text?.toString()?.trim().orEmpty()
        return when {
            classRoom.isEmpty() -> {
                binding.tilClassRoom.error = "Vui lòng nhập tên lớp"
                false
            }
            classRoom.length < 2 -> {
                binding.tilClassRoom.error = "Tên lớp không hợp lệ"
                false
            }
            else -> {
                binding.tilClassRoom.error = null
                true
            }
        }
    }
}