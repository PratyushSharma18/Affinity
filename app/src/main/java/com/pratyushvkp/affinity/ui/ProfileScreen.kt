package com.pratyushvkp.affinity.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pratyushvkp.affinity.*
import com.pratyushvkp.affinity.viewModel.AffinityViewModel

enum class Gender{
    MALE, FEMALE, ANY
}
@Composable
fun ProfileScreen(navController: NavController,vm: AffinityViewModel) {
    val inProgress = vm.inProgress.value
    if (inProgress)
        CommonProgressSpinner()
    else{
        val userData = vm.userData.value
        val g = if (userData?.gender.isNullOrEmpty()) "MALE"
        else userData!!.gender!!.uppercase()
        val gPref = if (userData?.genderPreference.isNullOrEmpty()) "FEMALE"
        else userData!!.genderPreference!!.uppercase()
        var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
        var username by rememberSaveable { mutableStateOf(userData?.username ?: "") }
        var bio by rememberSaveable { mutableStateOf(userData?.bio ?: "") }
        var gender by rememberSaveable { mutableStateOf(Gender.valueOf(g)) }
        var genderPreference by rememberSaveable { mutableStateOf(Gender.valueOf(gPref)) }

        val scrollState = rememberScrollState()


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ProfileContent(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                vm = vm,
                name = name,
                username = username,
                bio = bio,
                gender = gender,
                genderPreference = genderPreference,
                onNameChange = { name = it },
                onUsernameChange = { username = it },
                onBioChange = { bio = it },
                onGenderChange = { gender = it },
                onGenderPreferenceChange = { genderPreference = it },
                onSave = {
                    vm.updateProfileData(name, username, bio, gender, genderPreference)
                },
                onBack = { navigateTo(navController, DestinationScreen.Swipe.route) },
                onLogout = {
                    vm.onLogout()
                    navigateTo(navController, DestinationScreen.Login.route)
                }
            )
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.PROFILE,
                navController = navController
                )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
   modifier: Modifier,
   vm: AffinityViewModel,
   name: String,
   username: String,
   bio: String,
   gender: Gender,
   genderPreference: Gender,
   onNameChange: (String) -> Unit,
   onUsernameChange: (String) -> Unit,
   onBioChange: (String) -> Unit,
   onGenderChange: (Gender) -> Unit,
   onGenderPreferenceChange: (Gender) -> Unit,
   onSave: () -> Unit,
   onBack: () -> Unit,
   onLogout : () -> Unit
) {
    val imageUrl = vm.userData.value?.imageUrl

    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", modifier = Modifier.clickable { onBack.invoke() })
            Text(text = "Save", modifier = Modifier.clickable { onSave.invoke() })
        }
        CommonDivider()

        ProfileImage(imageUrl = imageUrl, vm = vm)

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(100.dp))
            TextField(
                value = name,
                onValueChange = onNameChange,
                colors = TextFieldDefaults.textFieldColors
                    (
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                    )
            )
        }



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Username", modifier = Modifier.width(100.dp))
        TextField(
            value = username,
            onValueChange = onUsernameChange,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.Transparent
                )
        )
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Bio", modifier = Modifier.width(100.dp))
        TextField(
            value = bio,
            onValueChange = onBioChange,
            modifier = Modifier
                .height(150.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.Transparent
                ),
            singleLine = false
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "I am a:", modifier = Modifier
                .width(100.dp)
                .padding(8.dp)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = gender == Gender.MALE,
                    onClick = { onGenderChange(Gender.MALE) })
                Text(
                    text = "Man",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onGenderChange(Gender.MALE) })
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = gender == Gender.FEMALE,
                    onClick = { onGenderChange(Gender.FEMALE) })
                Text(
                    text = "Woman",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onGenderChange(Gender.FEMALE) })
            }
        }
    }
        CommonDivider()


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Looking for:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = genderPreference == Gender.MALE,
                        onClick = { onGenderPreferenceChange(Gender.MALE) })
                    Text(
                        text = "Men",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(Gender.MALE) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = genderPreference == Gender.FEMALE,
                        onClick = { onGenderPreferenceChange(Gender.FEMALE) })
                    Text(
                        text = "Women",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(Gender.FEMALE) })
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = genderPreference == Gender.ANY,
                        onClick = { onGenderPreferenceChange(Gender.ANY) })
                    Text(
                        text = "Any",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(Gender.ANY) })
                }
            }
        }

       CommonDivider()

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(29.dp),
        horizontalArrangement = Arrangement.Center
            ) {
            Text(text = "Logout", modifier = Modifier.clickable { onLogout.invoke() },
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace
                ),
                color = Color.Blue
                )
        }

    }
}


@Composable
fun ProfileImage(imageUrl: String?, vm: AffinityViewModel){

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){uri: Uri? ->
        uri?.let { vm.uploadProfileImage(uri) }
    }

 Box(modifier = Modifier.height(IntrinsicSize.Min)) {
     Column(modifier = Modifier
         .padding(8.dp)
         .fillMaxWidth()
         .clickable {
             launcher.launch("image/*")
         },
     horizontalAlignment = Alignment.CenterHorizontally
         ) {
         Card(shape = CircleShape, modifier = Modifier
             .padding(8.dp)
             .size(100.dp)) {
             CommonImage(data = imageUrl)
         }
         Text(text = "Change profile picture")
     }

     val isLoading = vm.inProgress.value
     if (isLoading)
         CommonProgressSpinner()
 }
}