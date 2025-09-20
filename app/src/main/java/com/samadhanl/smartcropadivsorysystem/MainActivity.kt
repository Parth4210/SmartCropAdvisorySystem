package com.samadhanl.smartcropadivsorysystem

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File
import java.io.IOException
import com.samadhanl.smartcropadivsorysystem.ui.theme.SmartCropAdivsorySystemTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartCropAdivsorySystemTheme {
                var curr_time:Int
                var greet:String = "Hello"
                fun greetings():String {
//                    This function is used to change the greetings displayed above the farner's name
                    val calendar = Calendar.getInstance()
                    val curr_hour = calendar.get(Calendar.HOUR_OF_DAY)
                    curr_time = curr_hour
                    if (curr_time > 4 && curr_time < 12) {
                        return "Good Morning"
                    } else if (curr_time > 12 && curr_time < 4) {
                        return "Good Afternoon"
                    } else if (curr_time == 12) {
                        return "Good Noon"
                    } else {
                        return "Good Evening"
                    }
                }
                greet = greetings()
                val name1 = remember { mutableStateOf("Farmer's name") }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(top = 23.dp),
                            title = { Text(text = greet , fontWeight = FontWeight.Bold) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF4A7C59),
                                titleContentColor = Color.White
                            ),
                        )
                            Text(text = name1.value,
                                modifier = Modifier.padding(top = 80.dp, start = 17.dp),
                                color = Color.White
                            )
                        Button(
                            onClick = {},
                            modifier = Modifier.padding(start = 270.dp, top = 50.dp)
                                .background(color = Color.Transparent),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent

                            ),

                        ){
                            Image(
                                painter = painterResource(id = R.drawable.settings_icon),
                                contentDescription = "Settings Icon",
                                modifier = Modifier.size(25.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.padding(start = 330.dp, top = 50.dp)
                                .background(color = Color.Transparent),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent

                            ),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.user_icon),
                                contentDescription = "Settings Icon",
                                modifier = Modifier.size(25.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }


                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                    }
                }
            }
        }
    }
}
