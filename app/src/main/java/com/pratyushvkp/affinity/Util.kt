package com.pratyushvkp.affinity

import android.app.Notification
import android.inputmethodservice.Keyboard
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.pratyushvkp.affinity.viewModel.AffinityViewModel

fun navigateTo(navController: NavController, route: String){
    navController.navigate(route) {
        popUpTo(route)
        launchSingleTop = true
    }
}

@Composable
fun CommonProgressSpinner() {
    Row(modifier = Modifier
        .alpha(0.5f)
        .background(Color.LightGray)
        .clickable(enabled = false) {}
        .fillMaxSize(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NotificationMessage(vm : AffinityViewModel){
    val notifState = vm.popupNotification.value
    val notifMessage = notifState?.getContentOrNull()
    if(!notifMessage.isNullOrEmpty())
        Toast.makeText(LocalContext.current, notifMessage, Toast.LENGTH_SHORT).show()
}


@Composable
fun CheckSignedIn(vm: AffinityViewModel, navController: NavController){
    val alreadyLoggedIn = remember { mutableStateOf(false) }
    val signedIn = vm.signedIn.value
    if (signedIn && !alreadyLoggedIn.value){
        alreadyLoggedIn.value = true
        navController.navigate(DestinationScreen.Profile.route){
            popUpTo(0)
        }
    }
}


@Composable
fun CommonDivider() {
Divider(
    color = Color.LightGray,
    thickness =  3.dp,
    modifier = Modifier
        .alpha(0.3f)
        .padding(top = 8.dp, bottom = 8.dp)
     )
 }


@Composable
fun CommonImage(
    data: String?,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale = ContentScale.Crop
){
    val painter = rememberImagePainter(data = data)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
        )
    if (painter.state is ImagePainter.State.Loading)
        CommonProgressSpinner()
}