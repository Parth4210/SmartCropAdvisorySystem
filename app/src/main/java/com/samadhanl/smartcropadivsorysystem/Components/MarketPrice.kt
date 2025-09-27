// In file: MarketPrice.kt
package com.samadhanl.smartcropadivsorysystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samadhanl.smartcropadivsorysystem.ui.theme.SmartCropAdivsorySystemTheme

// --- DATA CLASSES to hold information for different sections ---
data class Commodity(
    val name: String, val demand: String, val mandi: String, val grade: String,
    val price: String, val changePercent: Double, val isTrendingUp: Boolean
)
data class PriceHistory(
    val timeAgo: String, val wheatPrice: String, val ricePrice: String, val cottonPrice: String
)
data class MarketInsight(
    val title: String, val description: String, val timeAgo: String, val color: Color
)
data class PriceAlert(
    val name: String, val target: String, val current: String
)

// --- SAMPLE DATA for populating the UI ---
val sampleCommodities = listOf(
    Commodity("Wheat", "High Demand", "Ludhiana Mandi", "Grade A", "₹2,150", 2.3, true),
    Commodity("Rice (Basmati)", "Medium Demand", "Amritsar Mandi", "Premium", "₹3,850", 1.2, false),
    Commodity("Cotton", "High Demand", "Bathinda Mandi", "Grade A", "₹5,680", 4.7, true),
    Commodity("Sugarcane", "Medium Demand", "Jalandhar Mandi", "Good", "₹380", 0.8, true)
)
val samplePriceHistory = listOf(
    PriceHistory("1 week ago", "₹2100", "₹3900", "₹5420"),
    PriceHistory("2 weeks ago", "₹2080", "₹3950", "₹5380"),
    PriceHistory("1 month ago", "₹2050", "₹4000", "₹5200"),
    PriceHistory("3 months ago", "₹1950", "₹3800", "₹4950")
)
val sampleMarketInsights = listOf(
    MarketInsight("Wheat Prices Rising", "Strong export demand driving wheat prices up by 8% this month", "2 weeks", Color(0xFF43A047)),
    MarketInsight("Cotton Season Peak", "Harvest season approaching, expect price volatility", "1 month", Color(0xFF8D6E63)),
    MarketInsight("Rice Export Boost", "Government policy changes favoring rice exports", "3 months", Color(0xFF1E88E5))
)
val samplePriceAlerts = listOf(
    PriceAlert("Wheat", "₹2200", "₹2150"),
    PriceAlert("Cotton", "₹6000", "₹5680"),
    PriceAlert("Rice", "₹4000", "₹3850")
)

/**
 * The main Composable for the entire Market Prices screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketPriceScreen(onNavigateBack: () -> Unit) {
    var selectedTab by remember { mutableStateOf("Today's Rates") }
    var selectedLocation by remember { mutableStateOf("Punjab") }
    var isLocationDropdownExpanded by remember { mutableStateOf(false) }
    val locations = listOf("Punjab", "Haryana", "Uttar Pradesh", "Rajasthan", "Gujarat")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Column {
                    Text("Market Prices", fontWeight = FontWeight.Bold)
                    Text("Live commodity rates", fontSize = 12.sp)
                }},
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, "Back") } },
                actions = { IconButton(onClick = { /* Refresh */ }) { Icon(Icons.Default.Refresh, "Refresh") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4A7C59), titleContentColor = Color.White, navigationIconContentColor = Color.White, actionIconContentColor = Color.White)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color(0xFFF7F5F0)),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            // --- Header Section (Location, Search, Tabs) ---
            item {
                Spacer(Modifier.height(16.dp))
                ExposedDropdownMenuBox(
                    expanded = isLocationDropdownExpanded,
                    onExpandedChange = { isLocationDropdownExpanded = !isLocationDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedLocation,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        leadingIcon = { Icon(painterResource(R.drawable.ic_location_on), "Location") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isLocationDropdownExpanded) },
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = isLocationDropdownExpanded,
                        onDismissRequest = { isLocationDropdownExpanded = false }
                    ) {
                        locations.forEach { location ->
                            DropdownMenuItem(
                                text = { Text(location) },
                                onClick = {
                                    selectedLocation = location
                                    isLocationDropdownExpanded = false
                                },
                                trailingIcon = { if (location == selectedLocation) Icon(Icons.Default.Check, "Selected") }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = "", onValueChange = {}, placeholder = { Text("Search crops...") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(painterResource(R.drawable.ic_search), "Search") },
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(16.dp))

                // The Row is updated to space buttons evenly and prevent text wrapping.
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val tabs = listOf("Today's Rates", "Trends", "Watchlist")
                    tabs.forEach { tabTitle ->
                        val isSelected = selectedTab == tabTitle
                        Button(
                            onClick = { selectedTab = tabTitle },
                            // This weight modifier gives each button an equal share of the width.
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            // This padding improves the look of the text inside the button.
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) Color(0xFF4A7C59) else Color.White,
                                contentColor = if (isSelected) Color.White else Color.Black
                            )
                        ) {
                            Text(tabTitle)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // --- Tab-Specific Content ---
            when (selectedTab) {
                "Today's Rates" -> items(sampleCommodities) { commodity ->
                    CommodityInfoCard(commodity)
                    Spacer(Modifier.height(12.dp))
                }
                "Trends" -> {
                    item { Text("Price History", fontSize = 20.sp, fontWeight = FontWeight.Bold) }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Spacer(modifier = Modifier.weight(1.5f)) // Spacer to align with the cards
                            Text("Wheat:", modifier = Modifier.weight(1f), color = Color.Gray)
                            Text("Rice:", modifier = Modifier.weight(1f), color = Color.Gray)
                            Text("Cotton:", modifier = Modifier.weight(1f), color = Color.Gray)
                        }
                    }

                    items(samplePriceHistory) { history ->
                        PriceHistoryCard(history)
                        Spacer(Modifier.height(12.dp))
                    }
                    item {
                        Spacer(Modifier.height(16.dp))
                        Text("Market Insights", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    items(sampleMarketInsights) { insight ->
                        MarketInsightCard(insight)
                        Spacer(Modifier.height(12.dp))
                    }
                }
                "Watchlist" -> {
                    item { PriceAlertsCard() }
                    item {
                        Spacer(Modifier.height(16.dp))
                        TipsCard()
                    }
                }
            }
        }
    }
}

// --- REUSABLE COMPOSABLES for different cards and items ---

@Composable
fun CommodityInfoCard(commodity: Commodity) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.Top) {
                Column {
                    Text(commodity.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    DemandChip(commodity.demand)
                }
                Text(commodity.price, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Text("per quintal", fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            Text("${commodity.mandi} • ${commodity.grade}", fontSize = 14.sp, color = Color.Gray)
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                val trendColor = if (commodity.isTrendingUp) Color(0xFF43A047) else Color.Red
                Icon(painterResource(if (commodity.isTrendingUp) R.drawable.ic_notifications else R.drawable.ic_notifications), "Trend", tint = trendColor)
                Text(" ${commodity.changePercent}%", color = trendColor, fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f))
                OutlinedButton(onClick = {}, shape = RoundedCornerShape(12.dp)) {
                    Icon(painterResource(R.drawable.ic_chart), "Chart", Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Chart")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = {}, shape = RoundedCornerShape(12.dp)) {
                    Icon(painterResource(R.drawable.ic_notifications), "Alert", Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Alert")
                }
            }
        }
    }
}

@Composable
fun DemandChip(demand: String) {
    val (bgColor, textColor) = when (demand) {
        "High Demand" -> Color(0xFFE8F5E9) to Color(0xFF43A047)
        "Medium Demand" -> Color(0xFFFFF3E0) to Color(0xFFFB8C00)
        else -> Color.LightGray to Color.Black
    }
    Box(Modifier.padding(top = 4.dp).clip(RoundedCornerShape(8.dp)).background(bgColor).padding(horizontal = 8.dp, vertical = 4.dp)) {
        Text(demand, color = textColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PriceHistoryCard(history: PriceHistory) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        // This Row now uses weights to create perfectly aligned columns
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Column 1: Time Ago (takes up 1.5x the space of the others)
            Column(modifier = Modifier.weight(1.5f)) {
                Text(history.timeAgo.substringBefore(" "), fontWeight = FontWeight.Bold)
                Text(history.timeAgo.substringAfter(" "), fontSize = 12.sp, color = Color.Gray)
            }
            // Column 2: Wheat Price
            Text(history.wheatPrice, modifier = Modifier.weight(1f))
            // Column 3: Rice Price
            Text(history.ricePrice, modifier = Modifier.weight(1f))
            // Column 4: Cotton Price
            Text(history.cottonPrice, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun MarketInsightCard(insight: MarketInsight) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(Color.White)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Column(Modifier.weight(1f)) {
                Text(insight.title, fontWeight = FontWeight.Bold)
                Text(insight.description, fontSize = 14.sp, color = Color.Gray)
            }
            Spacer(Modifier.width(8.dp))
            Box(Modifier.clip(RoundedCornerShape(12.dp)).background(insight.color).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(insight.timeAgo, color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun PriceAlertsCard() {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(R.drawable.ic_star_outline), "Price Alerts")
                Spacer(Modifier.width(8.dp))
                Text("Price Alerts", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            samplePriceAlerts.forEach { alert ->
                Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    Column {
                        Text(alert.name, fontWeight = FontWeight.Bold)
                        Text("Target: ${alert.target} | Current: ${alert.current}", fontSize = 12.sp, color = Color.Gray)
                    }
                    OutlinedButton(onClick = {}, shape = RoundedCornerShape(12.dp)) {
                        Text("Watching")
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                Icon(painterResource(R.drawable.ic_add_alert), "Add Price Alert", Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Add Price Alert")
            }
        }
    }
}

@Composable
fun TipsCard() {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.padding(16.dp)) {
            Text("Tips for Better Pricing", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            val tips = listOf(
                "Monitor prices across multiple mandis",
                "Set alerts for your target selling prices",
                "Consider seasonal trends before harvesting",
                "Factor in transportation costs to different markets"
            )
            tips.forEach { tip ->
                Text("• $tip", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 4.dp))
            }
        }
    }
}


/**
 * The @Preview annotation lets you see your UI in Android Studio
 * without running the full app.
 */
@Preview(showBackground = true)
@Composable
fun MarketPriceScreenPreview() {
    SmartCropAdivsorySystemTheme {
        MarketPriceScreen(onNavigateBack = {})
    }
}