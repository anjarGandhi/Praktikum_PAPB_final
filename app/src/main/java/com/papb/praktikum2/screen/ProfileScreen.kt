// profileScreen.kt
package com.papb.praktikum2.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter
import com.papb.praktikum2.ui.theme.Praktikum2Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread

data class GithubProfile(
    val avatarUrl: String,
    val name: String,
    val username: String,
    val followers: Int,
    val following: Int
)

class GithubProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Praktikum2Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    GithubProfileScreen()
                }
            }
        }
    }
}

@Composable
fun GithubProfileScreen() {
    var profile by remember { mutableStateOf<GithubProfile?>(null) }
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            val url = URL("https://api.github.com/users/anjarGandhi")
            val result = url.readText()
            val json = JSONObject(result)

            val profileData = GithubProfile(
                avatarUrl = json.getString("avatar_url"),
                name = json.getString("name"),
                username = json.getString("login"),
                followers = json.getInt("followers"),
                following = json.getInt("following")
            )

            profile = profileData
        }
    }

    // UI for Github Profile
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        profile?.let {
            Image(
                painter = rememberImagePainter(it.avatarUrl),
                contentDescription = "Github Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )

            Text(text = "Name: ${it.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Username: ${it.username}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Followers: ${it.followers}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Following: ${it.following}", style = MaterialTheme.typography.bodyMedium)
        } ?: run {
            Text(
                text = "Loading...",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
