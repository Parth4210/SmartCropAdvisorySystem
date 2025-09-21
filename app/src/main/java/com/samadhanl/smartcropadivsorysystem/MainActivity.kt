package com.samadhanl.smartcropadivsorysystem

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
                                .height(200.dp)
                                .padding(top = 37.dp),
                            title = { Text(text = greet , fontWeight = FontWeight.Bold) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF4A7C59),
                                titleContentColor = Color.White
                            ),
                        )
                            Text(text = name1.value,
                                modifier = Modifier.padding(top = 116.dp, start = 17.dp),
                                color = Color.White
                            )
                        Button(
                            onClick = {},
                            modifier = Modifier.padding(start = 277.dp, top = 82.dp)
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
                            modifier = Modifier.padding(start = 335.dp, top = 82.dp)
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
                            .padding(innerPadding),

                        // these are the fucking modifiers to move the fucking banner

                        verticalArrangement = Arrangement.spacedBy(-10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        WarningBanner(
                            message = "Aphid activity reported in nearby farms",
                            modifier = Modifier.padding(16.dp) //
                        )
                        WarningBanner(
                            message = "Heavy rain expected in 10 days",
                            modifier = Modifier.padding(16.dp)
                        )

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
