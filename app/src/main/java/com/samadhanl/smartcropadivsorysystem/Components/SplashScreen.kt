package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen() {
    val splashGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF7bb661), Color(0xFFf7f3e3))
    )
    Box(
        modifier = Modifier.fillMaxSize().background(brush = splashGradient),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            modifier = Modifier.padding(horizontal = 24.dp).shadow(elevation = 16.dp, shape = RoundedCornerShape(32.dp), spotColor = Color(0xFF7bb661))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 36.dp, vertical = 48.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(80.dp).clip(CircleShape).background(Color(0xFFe6c36b)).shadow(elevation = 8.dp, shape = CircleShape, spotColor = Color(0xFFe6c36b))
                ) {
                    Text(text = "🌱", fontSize = 44.sp)
                }
                Spacer(modifier = Modifier.height(18.dp))
                Text("Smart Crop Advisory", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6b4f1d), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Empowering Farmers", fontSize = 18.sp, color = Color(0xFF4e944f), fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(10.dp))
                Text("Growing Together", fontSize = 15.sp, color = Color(0xFF8d7752), fontStyle = FontStyle.Italic, textAlign = TextAlign.Center)
            }
        }
    }
}