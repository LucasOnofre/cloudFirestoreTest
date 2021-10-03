package com.onoffrice.android.cloudfirestoretest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.onoffrice.android.cloudfirestoretest.databinding.ActivityMainBinding

private const val NAME_KEY = "name"
private const val AGE_KEY = "age"
private const val DB_NAME = "dados_pessoais"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.saveBtn.setOnClickListener {
            saveData()
        }
        binding.getDataBtn.setOnClickListener {
            getData()
        }
    }

    private fun saveData() {

        val name = binding.name.text.toString()
        val age = binding.age.text.toString()

        if (name.isNotEmpty() && age.isNotEmpty()) {

            val userInfo = UserInfo(
                name = binding.name.text.toString(),
                age = binding.age.text.toString()
            )

            db.collection(DB_NAME)
                .add(userInfo)
                .addOnSuccessListener {
                    Log.d("Teste", "Documento Salvo!")
                    Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Log.d("Teste", "Documento não foi Salvo!", it)
                    Toast.makeText(this, "Ops! Algo deu errado!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun getData() {
        db.collection(DB_NAME)
            .get()
            .addOnSuccessListener { result ->
                val resultList = mutableListOf<UserInfo>()
                result.documents.forEach {
                    resultList.add(UserInfo(name = it.data?.get(NAME_KEY).toString(), age = it.data?.get(AGE_KEY).toString()))
                }
                binding.nameFetch.text = resultList[0].name
                binding.ageFetch.text = resultList[0].age
            }
            .addOnFailureListener { exception ->
                Log.w("Teste", "Erro ao buscar falhas", exception)
            }
    }
}
