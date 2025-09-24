package com.samadhanl.smartcropadivsorysystem

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samadhanl.smartcropadivsorysystem.ui.theme.SmartCropAdivsorySystemTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

// --- Main Activity: The single entry point of your app ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartCropAdivsorySystemTheme {
                AppNavigator()
            }
        }
    }
}

// --- Navigation Controller: Manages all screen transitions ---
@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_flow") {
        // Route for the entire login flow (splash + login screen)
        composable("login_flow") {
            LoginFlow(navController = navController)
        }
        // Route for the main dashboard screen
        composable("dashboard") {
            DashboardScreen()
        }
    }
}

// ===================================================================
//               LOGIN AND SPLASH SCREEN CODE
// ===================================================================

// --- Data and ViewModel for Login ---
data class AuthUiState(
    val showSplash: Boolean = true,
    val mobile: String = "",
    val email: String = "",
    val password: String = "",
    val error: String? = null
)

class AuthViewModel : ViewModel() {
    var uiState by mutableStateOf(AuthUiState())
        private set

    init {
        viewModelScope.launch {
            delay(2500)
            uiState = uiState.copy(showSplash = false)
        }
    }

    fun onMobileChange(newValue: String) {
        if (newValue.all { it.isDigit() } && newValue.length <= 10) {
            uiState = uiState.copy(mobile = newValue)
        }
    }

    fun onEmailChange(newValue: String) {
        uiState = uiState.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState = uiState.copy(password = newValue)
    }

    // *** IMPORTANT CHANGE HERE ***
    // This function now triggers the navigation on success
    fun onLoginClicked(onLoginSuccess: () -> Unit) {
        uiState = uiState.copy(error = null)

        if (uiState.mobile.length != 10) {
            uiState = uiState.copy(error = "Mobile number must be 10 digits.")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches()) {
            uiState = uiState.copy(error = "Please enter a valid email address.")
            return
        }
        if (uiState.password.length < 6) {
            uiState = uiState.copy(error = "Password must be at least 6 characters.")
            return
        }

        // --- Handle Login Logic Here ---
        // If validation passes, call the success callback to navigate
        onLoginSuccess()
    }
}

// --- Composable that handles showing Splash or Login ---
@Composable
fun LoginFlow(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val uiState = authViewModel.uiState
    Crossfade(targetState = uiState.showSplash) { isShowingSplash ->
        if (isShowingSplash) {
            SplashScreen()
        } else {
            LoginScreen(
                uiState = uiState,
                onMobileChange = authViewModel::onMobileChange,
                onEmailChange = authViewModel::onEmailChange,
                onPasswordChange = authViewModel::onPasswordChange,
                onLogin = {
                    // When login is clicked, we call the ViewModel function
                    // and pass it the navigation action.
                    authViewModel.onLoginClicked {
                        // This is the onLoginSuccess lambda
                        navController.navigate("dashboard") {
                            // This pops the login screen from the back stack, so the user can't go back to it
                            popUpTo("login_flow") { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: AuthUiState,
    onMobileChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFf7f3e3)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- Header ---
        Text(text = "🌾", fontSize = 32.sp)
        Text("Farmer's Login", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6b4f1d), modifier = Modifier.padding(top = 8.dp))
        Text("Welcome to Smart Crop Advisory", fontSize = 16.sp, color = Color(0xFF8d7752), modifier = Modifier.padding(top = 4.dp, bottom = 20.dp))

        // --- Form Card ---
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFfffbe6)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Mobile Number", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(value = uiState.mobile, onValueChange = onMobileChange, placeholder = { Text("Enter mobile number") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true)
                Spacer(Modifier.height(16.dp))

                Text("Email Address", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(value = uiState.email, onValueChange = onEmailChange, placeholder = { Text("Enter email address") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true)
                Spacer(Modifier.height(16.dp))

                Text("Password", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(value = uiState.password, onValueChange = onPasswordChange, placeholder = { Text("Enter password") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), singleLine = true)
                Spacer(Modifier.height(16.dp))

                if (uiState.error != null) {
                    Text(text = uiState.error, color = Color(0xFFd32f2f), fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
                }

                Button(onClick = onLogin, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7bb661)), elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)) {
                    Text("Log in", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(10.dp))
                ClickableText(text = AnnotatedString("Forgot Password?"), onClick = {}, style = TextStyle(color = Color(0xFF4e944f), fontSize = 15.sp, textAlign = TextAlign.Center, textDecoration = TextDecoration.Underline), modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(10.dp))
                ClickableText(text = AnnotatedString("New user? Register"), onClick = {}, style = TextStyle(color = Color(0xFF4e944f), fontSize = 15.sp, textAlign = TextAlign.Center, textDecoration = TextDecoration.Underline), modifier = Modifier.fillMaxWidth())
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text("🌱 Empowering Farmers • Growing Together", color = Color(0xFF8d7752), fontSize = 14.sp, fontStyle = FontStyle.Italic, modifier = Modifier.padding(bottom = 24.dp))
    }
}


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

// --- Helper Composables for Dashboard ---
@Composable
fun WarningBanner(message: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).border(width = 1.dp, color = Color(0xFFE69A8D), shape = RoundedCornerShape(12.dp)).background(Color(0xFFFFF7F0)).padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning Icon", tint = Color(0xFF8B4513), modifier = Modifier.size(24.dp))
        Text(text = message, color = Color(0xFF8B4513))
    }
}

@Composable
fun WeatherCard(weatherData: WeatherData, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Icon(painter = painterResource(id = R.drawable.ic_weather_sun), contentDescription = "Weather condition", modifier = Modifier.size(48.dp))
                Text(text = weatherData.temperature, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text(text = weatherData.condition, color = Color.White, fontSize = 16.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(id = R.drawable.ic_humidity), contentDescription = "Humidity", modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(text = weatherData.humidity, color = Color.White, fontSize = 16.sp)
                }
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(id = R.drawable.ic_thermostat), contentDescription = "High/Low temperature", modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(text = weatherData.highLow, color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun Small_card(title: String, subtitle: String, iconRes: Int, iconBackgroundColor: Color) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val defaultCardColor = Color.White
    val pressedCardColor = Color(0xFF5C896A)

    Card(
        modifier = Modifier.padding(8.dp).clickable(interactionSource = interactionSource, indication = null, onClick = { Toast.makeText(context, "$title Clicked", Toast.LENGTH_SHORT).show() }).width(160.dp).height(150.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = if (isPressed) pressedCardColor else defaultCardColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier.size(48.dp).clip(CircleShape).background(iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = iconRes), contentDescription = title, modifier = Modifier.size(24.dp))
            }
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

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

@Composable
fun MarketPriceCard() {
    Card(
        modifier = Modifier.padding(16.dp).width(368.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.trend), contentDescription = "Market trend", modifier = Modifier.size(20.dp))
                Text(modifier = Modifier.padding(start = 15.dp), text = "Today's Market Prices", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.Black)
            }
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Card(modifier = Modifier.weight(1f).height(100.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E4DC))) {}
                Spacer(modifier = Modifier.width(12.dp))
                Card(modifier = Modifier.weight(1f).height(100.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E4DC))) {}
            }
            Button(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7F5F0), contentColor = Color.Black)
            ) {
                Text(text = "View All Prices", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun FooterBtns() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = {},
            modifier = Modifier.weight(1f).height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7F5F0), contentColor = Color.Black)
        ) {
            Text(text = "Contact Us", fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = {},
            modifier = Modifier.weight(1f).height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7F5F0), contentColor = Color.Black)
        ) {
            Text(text = "Soil Guide", fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center)
        }
    }
}