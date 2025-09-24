package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samadhanl.smartcropadivsorysystem.Components.CropRecommendationCard

import com.samadhanl.smartcropadivsorysystem.Components.FooterBtns
import com.samadhanl.smartcropadivsorysystem.Components.MarketPriceCard
import com.samadhanl.smartcropadivsorysystem.R
import com.samadhanl.smartcropadivsorysystem.Components.Small_card
import com.samadhanl.smartcropadivsorysystem.Components.WarningBanner
import com.samadhanl.smartcropadivsorysystem.Components.WeatherCard
import java.util.Calendar

// ===================================================================
//               DASHBOARD SCREEN CODE
// ===================================================================

// --- Data for Dashboard ---
data class DashboardCardItem(val title: String, val subtitle: String, val iconRes: Int, val color: Color)
data class WeatherData(val temperature: String = "28°C", val condition: String = "Partly Cloudy", val humidity: String = "65%", val highLow: String = "32°/24°")

// --- Main Composable for the Dashboard ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val dashboardItems = listOf(
        DashboardCardItem("Detect Pest", "Take photo to identify", R.drawable.camera, Color(0xFFE53935)),
        DashboardCardItem("Soil Health", "Check soil condition", R.drawable.leaf, Color(0xFF43A047)),
        DashboardCardItem("Weather", "7-day forecast", R.drawable.cloud, Color(0xFF1E88E5)),
        DashboardCardItem("Market Prices", "Today's rates", R.drawable.market, Color(0xFF8D6E63))
    )

    val greet = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (hour) {
            in 5..11 -> "Good Morning,"
            12 -> "Good Noon,"
            in 13..16 -> "Good Afternoon,"
            else -> "Good Evening,"
        }
    }
    val name by remember { mutableStateOf("Farmer's name") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = greet, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        Text(text = name, fontSize = 16.sp)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Settings action */ }) {
                        Image(painter = painterResource(id = R.drawable.settings_icon), contentDescription = "Settings Icon", modifier = Modifier.size(25.dp), colorFilter = ColorFilter.tint(Color.White))
                    }
                    IconButton(onClick = { /* TODO: User profile action */ }) {
                        Image(painter = painterResource(id = R.drawable.user_icon), contentDescription = "User Icon", modifier = Modifier.size(25.dp), colorFilter = ColorFilter.tint(Color.White))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4A7C59), titleContentColor = Color.White, actionIconContentColor = Color.White)
            )
        },
        containerColor = Color(0xFFF7F5F0)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item { WeatherCard(weatherData = WeatherData()) }
            item { WarningBanner(message = "Aphid activity reported in nearby farms", modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) }
            item { WarningBanner(message = "Heavy rain expected in 10 days", modifier = Modifier.padding(16.dp)) }

            // Grid of four dashboard cards
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.height(360.dp).padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center,
                    userScrollEnabled = false // Disable scrolling for the inner grid
                ) {
                    items(dashboardItems) { item ->
                        Small_card(title = item.title, subtitle = item.subtitle, iconRes = item.iconRes, iconBackgroundColor = item.color)
                    }
                }
            }

            item { CropRecommendationCard("AI Crop Recommendations") }
            item { MarketPriceCard() }
            item { FooterBtns() }
        }
    }
}