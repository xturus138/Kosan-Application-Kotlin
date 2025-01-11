package com.example.kosannew.management.property

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kosannew.R
import com.example.kosannew.databinding.FragmentKosanBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class KosanFragment : Fragment() {

    private lateinit var binding: FragmentKosanBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKosanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        binding.buttonBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.btnReset.setOnClickListener { clearInput() }
        binding.btnSubmit.setOnClickListener { handleSubmit() }
    }

    private fun clearInput() {
        with(binding) {
            etNameProperti.text?.clear()
            etAlamatProperti.text?.clear()
            etKodePosProperti.text?.clear()
            etProvinsi.text?.clear()
            etKotaKabupaten.text?.clear()
            etKecamatan.text?.clear()
            etKelurahan.text?.clear()
            etNomorWhatsapp.text?.clear()
            etCatatan.text?.clear()
            cbTempatTidur.isChecked = false
            cbHeater.isChecked = false
            cbAc.isChecked = false
        }
    }

    private fun handleSubmit() {
        if (checkInput()) {
            savePropertyData()
        }
    }

    private fun checkInput(): Boolean {
        val name = binding.etNameProperti.text.toString()
        val alamat = binding.etAlamatProperti.text.toString()
        val kodePos = binding.etKodePosProperti.text.toString()
        val provinsi = binding.etProvinsi.text.toString()
        val kotaKabupaten = binding.etKotaKabupaten.text.toString()
        val kecamatan = binding.etKecamatan.text.toString()
        val kelurahan = binding.etKelurahan.text.toString()
        val nomorWhatsapp = binding.etNomorWhatsapp.text.toString()

        return when {
            name.isEmpty() -> {
                binding.etNameProperti.error = "Nama properti harus diisi"
                false
            }
            alamat.isEmpty() -> {
                binding.etAlamatProperti.error = "Alamat properti harus diisi"
                false
            }
            kodePos.isEmpty() -> {
                binding.etKodePosProperti.error = "Kode pos harus diisi"
                false
            }
            provinsi.isEmpty() -> {
                binding.etProvinsi.error = "Provinsi harus diisi"
                false
            }
            kotaKabupaten.isEmpty() -> {
                binding.etKotaKabupaten.error = "Kota/Kabupaten harus diisi"
                false
            }
            kecamatan.isEmpty() -> {
                binding.etKecamatan.error = "Kecamatan harus diisi"
                false
            }
            kelurahan.isEmpty() -> {
                binding.etKelurahan.error = "Kelurahan harus diisi"
                false
            }
            nomorWhatsapp.isEmpty() -> {
                binding.etNomorWhatsapp.error = "Nomor WhatsApp harus diisi"
                false
            }
            else -> true
        }
    }

    private fun savePropertyData() {
        val userMap = mapOf(
            "name" to binding.etNameProperti.text.toString(),
            "alamat" to binding.etAlamatProperti.text.toString(),
            "kodePos" to binding.etKodePosProperti.text.toString(),
            "provinsi" to binding.etProvinsi.text.toString(),
            "kotaKabupaten" to binding.etKotaKabupaten.text.toString(),
            "kecamatan" to binding.etKecamatan.text.toString(),
            "kelurahan" to binding.etKelurahan.text.toString(),
            "nomorWhatsapp" to binding.etNomorWhatsapp.text.toString(),
            "tempatTidur" to binding.cbTempatTidur.isChecked,
            "heater" to binding.cbHeater.isChecked,
            "ac" to binding.cbAc.isChecked
        )

        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser?.uid ?: "")
            .collection("properties")
            .add(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                    Log.d("KosanFragment", "savePropertyData: Succsess!!!!!!!")
                    clearInput()
                    requireActivity().onBackPressed()
                } else {
                    Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }
}
