package com.samadhanl.smartcropadivsorysystem.Components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samadhanl.smartcropadivsorysystem.R

// --- Data Classes for All Sections ---
data class WeatherInfo(
    // Data updated for Friday evening in Sonipat
    val temperature: String = "26°C",
    val condition: String = "Partly Cloudy",
    val location: String = "Sonipat, Haryana",
    val humidity: String = "80%",
    val visibility: String = "6 km",
    val windSpeed: String = "5 km/h",
    val uvIndex: String = "0 Low"
)

data class HourlyForecast(
    @DrawableRes val iconRes: Int,
    val time: String,
    val temperature: String,
    val precipitation: Int // Percentage
)

enum class PriorityLevel {
    HIGH, MEDIUM, LOW
}

data class FarmingAdvice(
    @DrawableRes val iconRes: Int,
    val title: String,
    val description: String,
    val priority: PriorityLevel
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherPage() {
    val weatherInfo = WeatherInfo()

    val hourlyForecasts = listOf(
        HourlyForecast(R.drawable.cloud, "5 PM", "26°C", 80),
        HourlyForecast(R.drawable.cloud, "6 PM", "25°C", 75),
        HourlyForecast(R.drawable.cloud, "7 PM", "24°C", 70),
        HourlyForecast(R.drawable.moon, "8 PM", "23°C", 65),
        HourlyForecast(R.drawable.moon, "9 PM", "22°C", 60),
        HourlyForecast(R.drawable.moon, "10 PM", "22°C", 55)
    )

    // Sample data for the Farming Advice section
    // NOTE: You'll need to add `irrigation.png`, `harvest.png`, `pest.png`, `alert.png`, and `calendar.png` to your drawables
    val farmingAdviceList = listOf(
        FarmingAdvice(
            iconRes = R.drawable.irrigation,
            title = "Irrigation Planning",
            description = "Heavy rain expected Wednesday-Thursday. Reduce watering schedule.",
            priority = PriorityLevel.HIGH
        ),
        FarmingAdvice(
            iconRes = R.drawable.harvest,
            title = "Harvest Window",
            description = "Good conditions Friday-Saturday for harvesting mature crops.",
            priority = PriorityLevel.MEDIUM
        ),
        FarmingAdvice(
            iconRes = R.drawable.pest,
            title = "Pest Alert",
            description = "High humidity may increase fungal disease risk. Monitor closely.",
            priority = PriorityLevel.MEDIUM
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .padding(top = 20.dp),
        containerColor = Color(0xFFF7F5F0),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Weather Insights",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        Text(
                            text = weatherInfo.location,
                            fontSize = 16.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Handle back navigation */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_icon),
                            contentDescription = "Back Icon",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4A7C59),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ITEM 1: The Image Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.sample_weather_img),
                        contentDescription = "Weather condition background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = weatherInfo.temperature,
                            color = Color.White,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = weatherInfo.condition,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }
            }

            // ITEM 2: Warning Banner Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF6ED)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.warning),
                            contentDescription = "Warning",
                            tint = Color(0xFFD9534F),
                            modifier = Modifier.size(24.dp)
                        )
                        Column(modifier = Modifier.padding(start = 16.dp)) {
                            Text(text = "High UV Index Warning", fontWeight = FontWeight.Bold, color = Color.Black)
                            Text(text = "Use sun protection today", color = Color.DarkGray, fontSize = 14.sp)
                        }
                    }
                }
            }

            // ITEM 3: Current Conditions Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Current Conditions",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            WeatherDetailItem(iconRes = R.drawable.waterdrop, label = "Humidity", value = weatherInfo.humidity)
                            WeatherDetailItem(iconRes = R.drawable.visibility, label = "Visibility", value = weatherInfo.visibility)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            WeatherDetailItem(iconRes = R.drawable.wind, label = "Wind Speed", value = weatherInfo.windSpeed)
                            WeatherDetailItem(iconRes = R.drawable.compass, label = "UV Index", value = weatherInfo.uvIndex)
                        }
                    }
                }
            }

            // ITEM 4: Forecast Section
            item {
                ForecastSection(hourlyForecasts)
            }

            // ITEM 5: The Farming Advice Section
            item {
                FarmingAdviceSection(adviceList = farmingAdviceList)
            }
        }
    }
}

/**
 * A reusable composable to display a single weather detail item.
 */
@Composable
fun WeatherDetailItem(@DrawableRes iconRes: Int, label: String, value: String, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(22.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}


// --- COMPOSABLES FOR FORECAST SECTION ---

@Composable
fun ForecastSection(hourlyForecasts: List<HourlyForecast>) {
    var selectedTab by remember { mutableStateOf("Today") }
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ForecastToggle(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Hourly Forecast", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    hourlyForecasts.forEach { forecast ->
                        HourlyForecastItem(forecast = forecast)
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastToggle(selectedTab: String, onTabSelected: (String) -> Unit) {
    val tabs = listOf("Today", "7-Day Forecast")
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
            tabs.forEach { tab ->
                val isSelected = selectedTab == tab
                val backgroundColor = if (isSelected) Color(0xFF4A7C59) else Color.Transparent
                val contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(backgroundColor)
                        .clickable { onTabSelected(tab) }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = tab, color = contentColor, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun HourlyForecastItem(forecast: HourlyForecast) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(painter = painterResource(id = forecast.iconRes), contentDescription = null, modifier = Modifier.size(28.dp), tint = MaterialTheme.colorScheme.onSurface)
            Text(text = forecast.time, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
        }
        Text(text = forecast.temperature, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(painter = painterResource(id = R.drawable.waterdrop), contentDescription = "Precipitation", modifier = Modifier.size(16.dp), tint = Color(0xFF4682B4))
            val precipColor = when {
                forecast.precipitation > 50 -> Color(0xFF0000CD)
                forecast.precipitation > 20 -> Color(0xFF4682B4)
                else -> Color(0xFF87CEEB)
            }
            Text(text = "${forecast.precipitation}%", color = precipColor, fontWeight = FontWeight.Medium)
        }
    }
}

// --- COMPOSABLES FOR FARMING ADVICE SECTION ---

@Composable
fun FarmingAdviceSection(adviceList: List<FarmingAdvice>) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "Farming Advice",
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Farming Advice",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // List of Advice Items
            adviceList.forEach { advice ->
                FarmingAdviceItem(advice = advice)
            }

            Divider(modifier = Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            // Action Buttons at the bottom
            ActionButtonsRow()
        }
    }
}

@Composable
fun FarmingAdviceItem(advice: FarmingAdvice) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            painter = painterResource(id = advice.iconRes),
            contentDescription = advice.title,
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = advice.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                PriorityTag(priority = advice.priority)
            }
            Text(
                text = advice.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PriorityTag(priority: PriorityLevel) {
    val (text, color) = when (priority) {
        PriorityLevel.HIGH -> "high" to Color(0xFFFBE2E1)
        PriorityLevel.MEDIUM -> "medium" to Color(0xFFFDF0D5)
        PriorityLevel.LOW -> "low" to Color(0xFFE1EFE6)
    }
    val textColor = when (priority) {
        PriorityLevel.HIGH -> Color(0xFFD9534F)
        PriorityLevel.MEDIUM -> Color(0xFFC48200)
        PriorityLevel.LOW -> Color(0xFF4A7C59)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ActionButtonsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.warning), contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Set Alerts")
        }
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.calendar), contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Farm Calendar")
        }
    }
}