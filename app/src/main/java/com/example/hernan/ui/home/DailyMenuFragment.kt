package com.example.hernan.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hernan.databinding.FragmentDailyMenuBinding
import com.example.hernan.data.model.Menu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

class DailyMenuFragment : Fragment() {

    private var _binding: FragmentDailyMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var menu: Menu
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    companion object {
        fun newInstance(menu: Menu): DailyMenuFragment {
            return DailyMenuFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("menu", menu)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyMenuBinding.inflate(inflater, container, false)
        menu = arguments?.getParcelable("menu")!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayMenuData()
        setupConfirmationButton()
        checkUserAttendance()
    }

    private fun displayMenuData() {
        val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
        binding.tvMenuDate.text = dateFormat.format(menu.date)
        binding.tvMainDish.text = menu.mainDish
        binding.tvSideDish.text = menu.sideDish
        binding.tvDessert.text = menu.dessert
        binding.tvCalories.text = "${menu.calories} kcal"
        binding.tvIngredients.text = menu.ingredients.joinToString(", ")
    }

    private fun setupConfirmationButton() {
        binding.btnConfirm.setOnClickListener {
            confirmAttendance()
        }
    }

    private fun checkUserAttendance() {
        auth.currentUser?.uid?.let { userId ->
            db.collection("attendance")
                .whereEqualTo("userId", userId)
                .whereEqualTo("menuId", menu.id)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        binding.btnConfirm.text = "Asistencia Confirmada"
                        binding.btnConfirm.isEnabled = false
                    }
                }
        }
    }

    private fun confirmAttendance() {
        auth.currentUser?.uid?.let { userId ->
            val attendance = hashMapOf(
                "userId" to userId,
                "menuId" to menu.id,
                "confirmed" to true,
                "timestamp" to FieldValue.serverTimestamp()
            )

            db.collection("attendance")
                .add(attendance)
                .addOnSuccessListener {
                    binding.btnConfirm.text = "Asistencia Confirmada"
                    binding.btnConfirm.isEnabled = false
                    Toast.makeText(context, "Asistencia registrada", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            Toast.makeText(context, "No has iniciado sesión", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}