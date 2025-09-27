package com.samadhanl.smartcropadivsorysystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samadhanl.smartcropadivsorysystem.ui.theme.SmartCropAdivsorySystemTheme

// Data classes to hold the information for the screen components
data class SoilMetric(
    val name: String,
    val value: String,
    val idealRange: String,
    val description: String
)

data class Recommendation(
    val title: String,
    val description: String,
    val priority: String,
    val icon: ImageVector // Using built-in icons
)
/**
 * The main Composable for the Soil Health Analysis screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoilHealthScreen(onNavigateBack: () -> Unit) {
    // Sample data to display in the UI
    val metrics = listOf(
        SoilMetric("pH Level", "6.8", "Ideal: 6.0-7.0", "Slightly acidic - perfect for most crops"),
        SoilMetric("Moisture", "45", "Ideal: 40-60%", "Optimal moisture level maintained"),
        SoilMetric("Temperature", "24", "Ideal: 20-30°C", "Ideal temperature for seed germination"),
        SoilMetric("Conductivity", "1.2", "Ideal: 0.8-2.0 dS/m", "Good nutrient availability")
    )
    val recommendations = listOf(
        Recommendation("Add Potassium Fertilizer", "Apply 50kg/hectare...", "high", Icons.Default.Science),
        Recommendation("Maintain Moisture Levels", "Current moisture is optimal...", "medium", Icons.Default.WaterDrop),
        Recommendation("Monitor pH Regularly", "pH is good but check weekly...", "low", Icons.Default.Analytics)
    )
    var selectedTab by remember { mutableStateOf("Analysis") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Soil Health Analysis", fontWeight = FontWeight.Bold)
                        Text("Field #1 - Last updated 2 hours ago", fontSize = 12.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF7F5F0))
        ) {
            // Header Image with overlayed info chips
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    // You need an image named 'crops_background.jpg' in res/drawable for this to work
                    Image(
                        painter = painterResource(id = R.drawable.crops_background),
                        contentDescription = "Crops background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        InfoChip("Healthy Soil", Color(0xFF43A047), Color.White)
                        Spacer(Modifier.width(8.dp))
                        InfoChip("Score: 78/100", Color.Black.copy(alpha = 0.3f), Color.White)
                    }
                }
            }

            // Tabs for filtering content
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val tabs = listOf("Analysis", "Nutrients", "Fertilizer")
                    tabs.forEach { tabTitle ->
                        val isSelected = selectedTab == tabTitle
                        Button(
                            onClick = { selectedTab = tabTitle },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) Color(0xFF4A7C59) else Color.White,
                                contentColor = if (isSelected) Color.White else Color.Black
                            )
                        ) { Text(tabTitle) }
                    }
                }
            }

            // Grid of metric cards
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        MetricCard(metrics[0], Modifier.weight(1f))
                        MetricCard(metrics[1], Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        MetricCard(metrics[2], Modifier.weight(1f))
                        MetricCard(metrics[3], Modifier.weight(1f))
                    }
                }
            }

            // Recommendations section
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Recommendations", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(16.dp))
                    recommendations.forEach { recommendation ->
                        RecommendationCard(recommendation)
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

// Reusable Composables for different parts of the screen

@Composable
fun MetricCard(metric: SoilMetric, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(metric.name, color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(metric.value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Icon(Icons.Default.CheckCircle, "Ideal", tint = Color(0xFF43A047))
            }
            Text(metric.idealRange, fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.height(4.dp))
            Text(metric.description, fontSize = 12.sp, color = Color.Gray, lineHeight = 14.sp)
        }
    }
}

@Composable
fun RecommendationCard(recommendation: Recommendation) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDECE8))
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = recommendation.icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF4A7C59)
            )
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(recommendation.title, fontWeight = FontWeight.Bold)
                Text(recommendation.description, fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(Modifier.width(8.dp))
            PriorityChip(recommendation.priority)
        }
    }
}

@Composable
fun PriorityChip(priority: String) {
    val (bgColor, textColor) = when (priority.lowercase()) {
        "high" -> Color(0xFFFBE9E7) to Color(0xFFD32F2F)
        "medium" -> Color(0xFFFFF3E0) to Color(0xFFFB8C00)
        else -> Color(0xFFE8F5E9) to Color(0xFF43A047)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(priority, color = textColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun InfoChip(text: String, bgColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text, color = textColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun SoilHealthScreenPreview() {
    SmartCropAdivsorySystemTheme {
        SoilHealthScreen(onNavigateBack = {})
    }
}