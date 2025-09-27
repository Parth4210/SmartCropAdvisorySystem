package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.samadhanl.smartcropadivsorysystem.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// ===================================================================
//               DATA MODELS FOR THE SCREEN
// ===================================================================
enum class Severity { Low, Medium, High }
enum class DetectionStatus { Treated, Pending }

data class PestDetection(
    val name: String,
    val severity: Severity,
    val crop: String,
    val time: String,
    val confidence: Int,
    val status: DetectionStatus
)

// ===================================================================
//               MAIN PEST DETECTION SCREEN
// ===================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PestDetectionScreen(navController: NavController) {
    // Sample data for the recent detections list
    val recentDetections = listOf(
        PestDetection("Aphids", Severity.Medium, "Tomato", "2 hours ago", 94, DetectionStatus.Treated),
        PestDetection("Leaf Rust", Severity.High, "Wheat", "Yesterday", 87, DetectionStatus.Pending),
        PestDetection("Cutworm", Severity.Low, "Cabbage", "3 days ago", 92, DetectionStatus.Treated)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Pest Detection", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painterResource(id = R.drawable.back_icon), "Back Icon")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4A7C59),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF7F5F0)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // -- Item 1: Main Action Card --
            item {
                Spacer(modifier = Modifier.height(8.dp)) // Top spacing
                PestIdentificationCard()
            }

            // -- Item 2: Recent Detections Section --
            item {
                RecentDetectionsSection(detections = recentDetections)
            }

            // -- Item 3: Photography Tips Card --
            item {
                PhotographyTipsCard()
                Spacer(modifier = Modifier.height(16.dp)) // Bottom spacing
            }
        }
    }
}

// ===================================================================
//               REUSABLE COMPONENT PIECES
// ===================================================================

@Composable
fun PestIdentificationCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(painterResource(id = R.drawable.bug_icon), "Pest Icon", modifier = Modifier.size(60.dp))
            Text("Identify Pests & Diseases", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Text("Take a clear photo of affected leaves or plants for accurate identification", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /* TODO: Take Photo */ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A7C59))) {
                Row(horizontalArrangement = Arrangement.SpaceAround,verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp)) {
                    Icon(painterResource(id = R.drawable.camera_icon), "Take Photo", modifier = Modifier.size(25.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Take Photo", fontSize = 18.sp)
                }
            }
            Button(onClick = { /* TODO: Upload Gallery */ }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface), border = BorderStroke(1.dp, Color(0xFF4A7C59))) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp)) {
                    Icon(painterResource(id = R.drawable.upload_icon), "Upload", tint = Color(0xFF4A7C59), modifier = Modifier.size(25.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Upload from Gallery", fontSize = 18.sp, color = Color(0xFF4A7C59))
                }
            }
        }
    }
}

@Composable
fun RecentDetectionsSection(detections: List<PestDetection>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Recent Detections",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
        detections.forEach { detection ->
            DetectionCard(detection = detection)
        }
    }
}

@Composable
fun DetectionCard(detection: PestDetection) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(detection.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                SeverityTag(severity = detection.severity)
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "${detection.crop} • ${detection.time} • ${detection.confidence}% confidence",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                StatusIndicator(status = detection.status)
            }
        }
    }
}

@Composable
fun SeverityTag(severity: Severity) {
    val (text, color) = when (severity) {
        Severity.Low -> "Low" to Color(0xFFE0E0E0)
        Severity.Medium -> "Medium" to Color(0xFFD3B8AE)
        Severity.High -> "High" to Color(0xFFF4B9B8)
    }
    Box(
        modifier = Modifier
            .background(color, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(text, color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatusIndicator(status: DetectionStatus) {
    val (text, icon, color) = when (status) {
        DetectionStatus.Treated -> Triple("Treated", R.drawable.correct, Color(0xFF43A047))
        DetectionStatus.Pending -> Triple("Pending", R.drawable.pending, Color(0xFFFFA000))
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painterResource(id = icon), contentDescription = text, tint = color, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, color = color, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun PhotographyTipsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.warning), "Tips", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Photography Tips", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Column {
                TipItem(text = "Take photos in bright, natural light")
                TipItem(text = "Focus on affected areas with clear details")
                TipItem(text = "Include surrounding healthy areas for comparison")
                TipItem(text = "Take multiple angles if possible")
                TipItem(text = "Avoid blurry or distant shots")
            }
        }
    }
}

@Composable
fun TipItem(text: String) {
    Row(modifier = Modifier.padding(start = 8.dp, top = 4.dp), verticalAlignment = Alignment.Top) {
        Text("•", modifier = Modifier.padding(end = 8.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}