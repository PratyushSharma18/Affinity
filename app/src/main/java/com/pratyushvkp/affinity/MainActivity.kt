package com.pratyushvkp.affinity


import android.app.Notification
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.pratyushvkp.affinity.ui.theme.AffinityTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pratyushvkp.affinity.ui.*
import com.pratyushvkp.affinity.ui.theme.SwipeScreen
import com.pratyushvkp.affinity.viewModel.AffinityViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class DestinationScreen(val route: String){
    object Signup : DestinationScreen("signup")
    object Login : DestinationScreen("login")
    object Profile : DestinationScreen("profile")
    object Swipe : DestinationScreen("swipe")
    object ChatList : DestinationScreen("chatList")
    object SingleChat : DestinationScreen("singleChat/{chatId}"){
        fun createRoute(id: String) = "singleChat/$id"
    }
}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContent {
           AffinityTheme{
   // Surface container using the 'background' color from the theme
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ){
                   SwipeAppNavigation()
               }
           }
       }
    }
}

@Composable
fun SwipeAppNavigation(){
    val navController = rememberNavController()
    val vm = hiltViewModel<AffinityViewModel>()
    
    NotificationMessage(vm = vm)

   NavHost(navController = navController, startDestination = DestinationScreen.Signup.route){
       composable(DestinationScreen.Signup.route){
           SignupScreen(navController,vm)
       }
       composable(DestinationScreen.Login.route){
           LoginScreen(navController,vm)
       }
       composable(DestinationScreen.Profile.route){
           ProfileScreen(navController,vm)
       }
       composable(DestinationScreen.Swipe.route){
           SwipeScreen(navController,vm)
       }
       composable(DestinationScreen.ChatList.route){
           ChatListScreen(navController,vm)
       }
       composable(DestinationScreen.SingleChat.route){
        val chatId = it.arguments?.getString("chatId")
           chatId?.let {
               SingleChatScreen(navController = navController, vm = vm, chatId = it)
           }
       }
   }
}