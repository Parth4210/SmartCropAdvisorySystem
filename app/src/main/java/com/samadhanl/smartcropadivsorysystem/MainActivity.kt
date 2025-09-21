package com.samadhanl.smartcropadivsorysystem

import android.os.Bundle
import androidx.compose.runtime.getValue
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
                        return "Good Morning,"
                    } else if (curr_time > 12 && curr_time < 4) {
                        return "Good Afternoon,"
                    } else if (curr_time == 12) {
                        return "Good Noon,"
                    } else {
                        return "Good Evening,"
                    }
                }
                greet = greetings()
                val name1 = remember { mutableStateOf("Farmer's name") }
                Scaffold(
                    topBar = {
                        TopAppBar(

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(top = 25.dp),
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
                            modifier = Modifier
                                .padding(start = 277.dp, top = 55.dp)
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
                            modifier = Modifier
                                .padding(start = 335.dp, top = 55.dp)
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


                    },


                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .background(color = Color(0xFFF7F5F0))
                            .fillMaxSize()
                            .padding(innerPadding),

                        // these are the fucking modifiers to move the fucking banner

                        verticalArrangement = Arrangement.spacedBy(-10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color(0xFF4A7C59))
                                .fillMaxWidth()
                                .height(90.dp),

                        ){
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 14.dp)
                            ){

                            Card (
                                modifier = Modifier
                                    .height(60.dp)
                                    .width(350.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF5C896A)
                                )

                            ){  }
                            }
                        }
                        WarningBanner(
                            message = "Aphid activity reported in nearby farms",
                            modifier = Modifier.padding(16.dp) //
                        )
                        WarningBanner(
                            message = "Heavy rain expected in 10 days",
                            modifier = Modifier.padding(16.dp)
                        )

                    }
                    //It contains the four buttons which provides the access to farmer to access the features  of the app
                    Box(

                    ){
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 390.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(70.dp),
                        ){
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Small_card()
                            Small_card()
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Small_card()
                            Small_card()
                        }
                    }




                    }
                }

            }
        }
    }
}

@Composable
fun WarningBanner(
    message: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = Color(0xFFFFF7F0)
    val borderColor = Color(0xFFE69A8D)
    val contentColor = Color(0xFF8B4513)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Warning Icon",
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = message,
            color = contentColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WarningBannerPreview() {
    WarningBanner(message = "Aphid activity reported in nearby farms")
}


@Composable
fun Small_card(){
    //Used on the dashboard page for creating the navigation buttons
    var isClicked by remember { mutableStateOf(false) }

        Card(
            modifier = Modifier
                .clickable { isClicked = !isClicked }
                .width(140.dp)
                .height(140.dp)
                .border(
                    width = 2.dp,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(5.dp)
                ),

            colors = CardDefaults.cardColors(
                containerColor = if(isClicked) Color(0xFF979797) else Color(0xFFFFFFFF)

            )
        ) {
            Text(
                text = "Hello",
                modifier = Modifier.padding(10.dp)
            )

    }
}