package com.samadhanl.smartcropadivsorysystem

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samadhanl.smartcropadivsorysystem.ui.theme.SmartCropAdivsorySystemTheme
import java.util.Calendar

// Data class for the small cards
data class DashboardCardItem(
    val title: String,
    val subtitle: String,
    val iconRes: Int,
    val color: Color
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Prepare the data for the four dashboard cards
        //Icons are here
        val dashboardItems = listOf(
            DashboardCardItem("Detect Pest", "Take photo to identify", R.drawable.camera, Color(0xFFE53935)),
            DashboardCardItem("Soil Health", "Check soil condition", R.drawable.leaf, Color(0xFF43A047)),
            DashboardCardItem("Weather", "7-day forecast", R.drawable.cloud, Color(0xFF1E88E5)),
            DashboardCardItem("Market Prices", "Today's rates", R.drawable.market, Color(0xFF8D6E63))
        )

        setContent {
            SmartCropAdivsorySystemTheme {
                //greeting based on the time
                val greet = remember {
                    val calendar = Calendar.getInstance()
                    val curr_hour = calendar.get(Calendar.HOUR_OF_DAY)
                    when (curr_hour) {
                        in 5..11 -> "Good Morning,"
                        12 -> "Good Noon,"
                        in 13..16 -> "Good Afternoon,"
                        else -> "Good Evening,"
                    }
                }
                val name1 by remember { mutableStateOf("Farmer's name") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        // title and action icons
                        TopAppBar(
                            title = {
                                Column {
                                    Text(text = greet, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                                    Text(text = name1, fontSize = 16.sp)
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* TODO: Settings action */ }) {
                                    Image(
                                        painter = painterResource(id = R.drawable.settings_icon),
                                        contentDescription = "Settings Icon",
                                        modifier = Modifier.size(25.dp),
                                        colorFilter = ColorFilter.tint(Color.White)
                                    )
                                }
                                IconButton(onClick = { /* TODO: User profile action */ }) {
                                    Image(
                                        painter = painterResource(id = R.drawable.user_icon),
                                        contentDescription = "User Icon",
                                        modifier = Modifier.size(25.dp),
                                        colorFilter = ColorFilter.tint(Color.White)
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF4A7C59),
                                titleContentColor = Color.White,
                                actionIconContentColor = Color.White
                            )
                        )
                    },
                    containerColor = Color(0xFFF7F5F0) // Background color for the whole screen
                ) { innerPadding ->
                    //display a scrollable list of items
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // First Banner
                        item {
                            WarningBanner(
                                message = "Aphid activity reported in nearby farms",
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            )
                        }
                        // Second Banner
                        item {
                            WarningBanner(
                                message = "Heavy rain expected in 10 days",
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        // Grid of four dashboard cards
                        item {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier
                                    .height(340.dp) // height for the grid area
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalArrangement = Arrangement.Center
                            ) {
                                items(dashboardItems) { item ->
                                    Small_card(
                                        title = item.title,
                                        subtitle = item.subtitle,
                                        iconRes = item.iconRes,
                                        iconBackgroundColor = item.color
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WarningBanner(message: String, modifier: Modifier = Modifier) {
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
        Text(text = message, color = contentColor)
    }
}

@Composable
fun Small_card(
    title: String,
    subtitle: String,
    iconRes: Int,
    iconBackgroundColor: Color
) {
    val context = LocalContext.current
    var isClicked by remember { mutableStateOf(false) }

   // the clicking feature of the buttons
   // and
   // layout of the button

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                isClicked = !isClicked
                Toast
                    //after clicking it shows the temp notification.
                    .makeText(context, "$title Clicked", Toast.LENGTH_SHORT)
                    .show()
            }
            .width(160.dp)
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isClicked) Color(0xFFE0E0E0) else Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = title,
                    // colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(24.dp)
                )
            }
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

// Full preview of the app

@Preview(showBackground = true)
@Composable
fun FullScreenPreview() {
    // preview will show a sample of your full screen
    val dashboardItems = listOf(
        DashboardCardItem("Detect Pest", "Take photo to identify", R.drawable.camera, Color(0xFFE53935)),
        DashboardCardItem("Soil Health", "Check soil condition", R.drawable.leaf, Color(0xFF43A047)),
        DashboardCardItem("Weather", "7-day forecast", R.drawable.cloud, Color(0xFF1E88E5)),
        DashboardCardItem("Market Prices", "Today's rates", R.drawable.market, Color(0xFF8D6E63))
    )
    SmartCropAdivsorySystemTheme {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item { WarningBanner(message = "Aphid activity reported in nearby farms", modifier = Modifier.padding(16.dp)) }
            item {
                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.height(340.dp).padding(8.dp)) {
                    items(dashboardItems) { item -> Small_card(title = item.title, subtitle = item.subtitle, iconRes = item.iconRes, iconBackgroundColor = item.color) }
                }
            }
        }
    }
}