package com.pratyushvkp.affinity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pratyushvkp.affinity.DestinationScreen
import com.pratyushvkp.affinity.R
import com.pratyushvkp.affinity.navigateTo

enum class BottomNavigationItem(val icon: Int, val navDestination: DestinationScreen) {
    SWIPE(R.drawable.ic_baseline_swipe,DestinationScreen.Swipe),
    CHATLIST(R.drawable.ic_baseline_chat,DestinationScreen.ChatList),
    PROFILE(R.drawable.ic_baseline_profile,DestinationScreen.Profile)
}

@Composable
fun BottomNavigationMenu(selectedItem: BottomNavigationItem, navController: NavController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(top = 4.dp)
        .background(Color.White)
    ) {
        for (item in BottomNavigationItem.values()){
            Image(painter = painterResource(id = item.icon),
                contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(4.dp)
                .weight(1f)
                .clickable {
                    navigateTo(navController, item.navDestination.route)
                },
            colorFilter = if (item == selectedItem) ColorFilter.tint(Color.Black)
            else ColorFilter.tint(Color.Gray))
        }
    }
}