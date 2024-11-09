// matkulScreen.kt
package com.papb.praktikum2.screen

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.cardview.widget.CardView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.praktikum2.screen.GithubProfileActivity
import com.papb.praktikum2.R
import com.papb.praktikum2.Matkul
import com.papb.praktikum2.ui.theme.Praktikum2Theme

@Composable
fun MatkulScreen() {
    val db = FirebaseFirestore.getInstance()
    val matkulList = remember { mutableStateListOf<Matkul>() }

    // Fetch data when the Composable is first displayed
    LaunchedEffect(Unit) {
        fetchMatkulData(db, matkulList)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            MatkulListScreen(matkulList)

            val context = LocalContext.current

            FloatingActionButton(
                onClick = {
                    // Navigate to GitHub Profile Activity
                    val intent = Intent(context, GithubProfileActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(3.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Image(
                    painter = painterResource(id = R.drawable.github_logo),
                    contentDescription = "Go to GitHub Profile",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(48.dp)
                )
            }
        }
    }
}

private fun fetchMatkulData(db: FirebaseFirestore, matkulList: MutableList<Matkul>) {
    db.collection("matkul")
        .get()
        .addOnSuccessListener { result ->
            matkulList.clear()
            for (document in result) {
                try {
                    val matkulItem = document.toObject(Matkul::class.java)
                    matkulList.add(matkulItem)
                } catch (e: Exception) {
                    Log.e("Firestore", "Error parsing document: ${e.message}")
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.e("Firestore", "Error getting documents", exception)
        }
}

@Composable
fun MatkulListScreen(matkulList: List<Matkul>) {
    LazyColumn {
        items(matkulList) { item ->
            MatkulCard(matkul = item) // Gunakan MatkulCard yang sudah ada
        }
    }
}

@Composable
fun MatkulCard(matkul: Matkul) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Mata Kuliah: ${matkul.matkul}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Hari: ${matkul.hari}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Jam: ${matkul.jam}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Ruang: ${matkul.ruang}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Praktikum: ${if (matkul.praktikum) "Ya" else "Tidak"}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}