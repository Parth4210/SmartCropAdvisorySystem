package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samadhanl.smartcropadivsorysystem.R

@Composable
fun CropRecommendationCard(title: String) {
    Card(
        modifier = Modifier.padding(16.dp).width(368.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.sprout), contentDescription = "Crop image", modifier = Modifier.size(20.dp), colorFilter = ColorFilter.tint(Color(0xFF4B7D5A)))
                Text(modifier = Modifier.padding(start = 15.dp), text = title, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.Black)
            }
            // Small brown cards
            repeat(3) {
                Card(
                    modifier = Modifier.fillMaxWidth().height(80.dp).padding(top = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E4DC))
                ) {}
            }
        }
    }
}