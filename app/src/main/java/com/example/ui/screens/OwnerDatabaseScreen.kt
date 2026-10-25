package com.example.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.ActivationCodeEntity
import com.example.data.local.ClientSubscriptionEntity
import com.example.ui.theme.AccentBlue
import com.example.ui.theme.AccentBlueContainer
import com.example.ui.theme.AccentOrange
import com.example.ui.theme.AccentOrangeContainer
import com.example.ui.theme.AccentPink
import com.example.ui.theme.AccentPinkContainer
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleContainer
import com.example.ui.theme.AccentTeal
import com.example.ui.theme.AccentTealContainer
import com.example.ui.theme.DarkButtonBg
import com.example.ui.theme.DarkButtonBorder
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.ImmersiveBg
import com.example.ui.theme.ImmersiveBorder
import com.example.ui.theme.ImmersiveBorderSubtle
import com.example.ui.theme.ImmersiveSurface
import com.example.ui.theme.ImmersiveSurfaceCard
import com.example.ui.theme.ImmersiveSurfaceVariant
import com.example.ui.theme.StatusError
import com.example.ui.theme.StatusSuccess
import com.example.ui.theme.StatusWarning
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSlate
import com.example.ui.theme.TextWhite
import com.example.util.AppIntentUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OwnerDatabaseScreen(
    isAuthenticated: Boolean,
    onAuthenticate: (phone: String, pass: String) -> Boolean,
    subscriptions: List<ClientSubscriptionEntity>,
    subscribersCount: Int,
    revenueYER: Int,
    revenueSAR: Int,
    revenueUSD: Int,
    onUpdateStatus: (id: Long, status: String, approved: Boolean) -> Unit,
    onUpdateNotes: (id: Long, notes: String) -> Unit,
    onDeleteClient: (id: Long) -> Unit,
    onAddManualClient: (
        fullName: String,
        phone: String,
        phoneModel: String,
        problemType: String,
        serviceName: String,
        currency: String,
        priceAmount: Int,
        paymentMethod: String,
        transactionRef: String,
        status: String,
        ownerNotes: String
    ) -> Unit,
    onLockDashboard: () -> Unit,
    activationCodes: List<ActivationCodeEntity> = emptyList(),
    activationCodesCount: Int = 0,
    usedActivationCodesCount: Int = 0,
    onGenerateCode: (
        subscriptionType: String,
        clientName: String,
        clientPhone: String,
        priceAmount: Int,
        currency: String,
        durationLabel: String,
        customCode: String?,
        notes: String
    ) -> Unit = { _, _, _, _, _, _, _, _ -> },
    onDeleteCode: (id: Long) -> Unit = {},
    onUpdateCodeStatus: (id: Long, newStatus: String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current

    if (!isAuthenticated) {
        OwnerAuthGate(
            onAuthenticate = onAuthenticate
        )
    } else {
        OwnerDatabaseDashboard(
            context = context,
            subscriptions = subscriptions,
            subscribersCount = subscribersCount,
            revenueYER = revenueYER,
            revenueSAR = revenueSAR,
            revenueUSD = revenueUSD,
            onUpdateStatus = onUpdateStatus,
            onUpdateNotes = onUpdateNotes,
            onDeleteClient = onDeleteClient,
            onAddManualClient = onAddManualClient,
            onLockDashboard = onLockDashboard,
            activationCodes = activationCodes,
            activationCodesCount = activationCodesCount,
            usedActivationCodesCount = usedActivationCodesCount,
            onGenerateCode = onGenerateCode,
            onDeleteCode = onDeleteCode,
            onUpdateCodeStatus = onUpdateCodeStatus
        )
    }
}

@Composable
private fun OwnerAuthGate(
    onAuthenticate: (phone: String, pass: String) -> Boolean
) {
    var phoneInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ImmersiveBg)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.95f),
            colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
            border = BorderStroke(1.2.dp, EmeraldPrimary.copy(alpha = 0.35f)),
            shape = RoundedCornerShape(22.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(74.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(DarkButtonBg)
                        .border(1.5.dp, EmeraldPrimary.copy(alpha = 0.7f), RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rqeebpro_logo_1791221152168),
                        contentDescription = "شعار رقيب برو",
                        modifier = Modifier.size(58.dp)
                    )
                }

                Text(
                    text = "لوحة تحكم وإدارة المالك",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "تسجيل دخول المالك حصراً برقم الهاتف وكلمة المرور لإدارة المشتركين والإيرادات وتوليد أكواد التفعيل.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSlate,
                    textAlign = TextAlign.Center,
                    lineHeight = 21.sp
                )

                // Phone Input Field (رقم هاتف المالك)
                OutlinedTextField(
                    value = phoneInput,
                    onValueChange = {
                        phoneInput = it
                        errorMessage = null
                    },
                    label = { Text("رقم هاتف المالك", fontSize = 13.sp) },
                    placeholder = { Text("مثال: 785502919 أو 782916997", fontSize = 13.sp) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = ImmersiveBorder,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        cursorColor = EmeraldPrimary,
                        focusedContainerColor = ImmersiveSurfaceVariant,
                        unfocusedContainerColor = ImmersiveSurfaceVariant
                    )
                )

                // Password Input Field (كلمة السر / كلمة المرور)
                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = {
                        passwordInput = it
                        errorMessage = null
                    },
                    label = { Text("كلمة السر / كلمة المرور", fontSize = 13.sp) },
                    placeholder = { Text("أدخل كلمة مرور المالك", fontSize = 13.sp) },
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (isPasswordVisible) "إخفاء كلمة المرور" else "إظهار كلمة المرور",
                                tint = TextSlate
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = ImmersiveBorder,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        cursorColor = EmeraldPrimary,
                        focusedContainerColor = ImmersiveSurfaceVariant,
                        unfocusedContainerColor = ImmersiveSurfaceVariant
                    )
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = StatusError,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = {
                        if (phoneInput.isBlank() || passwordInput.isBlank()) {
                            errorMessage = "يرجى إدخال رقم الهاتف وكلمة المرور الخاصة بالمالك."
                        } else {
                            val valid = onAuthenticate(phoneInput.trim(), passwordInput.trim())
                            if (!valid) {
                                errorMessage = "بيانات الدخول غير صحيحة. يرجى التحقق من رقم الهاتف وكلمة المرور."
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldPrimary,
                        contentColor = ImmersiveBg
                    ),
                    contentPadding = PaddingValues(vertical = 13.dp)
                ) {
                    Icon(Icons.Default.LockOpen, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("فتح لوحة التحكم وقاعدة البيانات", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun OwnerDatabaseDashboard(
    context: Context,
    subscriptions: List<ClientSubscriptionEntity>,
    subscribersCount: Int,
    revenueYER: Int,
    revenueSAR: Int,
    revenueUSD: Int,
    onUpdateStatus: (id: Long, status: String, approved: Boolean) -> Unit,
    onUpdateNotes: (id: Long, notes: String) -> Unit,
    onDeleteClient: (id: Long) -> Unit,
    onAddManualClient: (
        fullName: String,
        phone: String,
        phoneModel: String,
        problemType: String,
        serviceName: String,
        currency: String,
        priceAmount: Int,
        paymentMethod: String,
        transactionRef: String,
        status: String,
        ownerNotes: String
    ) -> Unit,
    onLockDashboard: () -> Unit,
    activationCodes: List<ActivationCodeEntity>,
    activationCodesCount: Int,
    usedActivationCodesCount: Int,
    onGenerateCode: (
        subscriptionType: String,
        clientName: String,
        clientPhone: String,
        priceAmount: Int,
        currency: String,
        durationLabel: String,
        customCode: String?,
        notes: String
    ) -> Unit,
    onDeleteCode: (id: Long) -> Unit,
    onUpdateCodeStatus: (id: Long, newStatus: String) -> Unit
) {
    // 0: المشتركين والإيرادات, 1: أكواد تفعيل الاشتراكات
    var selectedDashboardTab by remember { mutableIntStateOf(0) }

    // State for Subscribers Tab
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatusFilter by remember { mutableStateOf("الكل") }
    var selectedCurrencyFilter by remember { mutableStateOf("الكل") }
    var showAddClientDialog by remember { mutableStateOf(false) }
    var clientToEditNotes by remember { mutableStateOf<ClientSubscriptionEntity?>(null) }
    var clientToDelete by remember { mutableStateOf<ClientSubscriptionEntity?>(null) }

    // State for Activation Codes Tab
    var codeSearchQuery by remember { mutableStateOf("") }
    var selectedCodeFilter by remember { mutableStateOf("الكل") }
    var showGenerateCodeDialog by remember { mutableStateOf(false) }
    var codeToDelete by remember { mutableStateOf<ActivationCodeEntity?>(null) }

    val filteredSubscriptions = subscriptions.filter { sub ->
        val matchesQuery = searchQuery.isBlank() ||
                sub.fullName.contains(searchQuery, ignoreCase = true) ||
                sub.phoneNumber.contains(searchQuery, ignoreCase = true) ||
                sub.phoneModel.contains(searchQuery, ignoreCase = true) ||
                sub.serviceName.contains(searchQuery, ignoreCase = true) ||
                sub.transactionRef.contains(searchQuery, ignoreCase = true)

        val matchesStatus = when (selectedStatusFilter) {
            "الكل" -> true
            "قيد المراجعة" -> sub.status == "قيد المراجعة"
            "تم التحقق والسداد" -> sub.status == "تم التحقق والسداد"
            "مكتمل" -> sub.status == "مكتمل"
            "ملغي" -> sub.status == "ملغي"
            else -> true
        }

        val matchesCurrency = when (selectedCurrencyFilter) {
            "الكل" -> true
            else -> sub.currency == selectedCurrencyFilter
        }

        matchesQuery && matchesStatus && matchesCurrency
    }

    val pendingCount = subscriptions.count { it.status == "قيد المراجعة" }
    val approvedCount = subscriptions.count { it.status == "تم التحقق والسداد" || it.status == "مكتمل" }

    val filteredCodes = activationCodes.filter { code ->
        val matchesQuery = codeSearchQuery.isBlank() ||
                code.code.contains(codeSearchQuery, ignoreCase = true) ||
                code.subscriptionType.contains(codeSearchQuery, ignoreCase = true) ||
                code.clientName.contains(codeSearchQuery, ignoreCase = true) ||
                code.clientPhone.contains(codeSearchQuery, ignoreCase = true)

        val matchesFilter = when (selectedCodeFilter) {
            "الكل" -> true
            "نشط ومتاح" -> !code.isUsed && code.status == "نشط ومتاح"
            "مستخدم ومفعل" -> code.isUsed || code.status == "مستخدم ومفعل"
            "معلق" -> code.status == "معلق"
            "شامل VIP" -> code.subscriptionType.contains("شامل")
            "برمجة وفك شفرات" -> code.subscriptionType.contains("برمجة") || code.subscriptionType.contains("شفرات")
            "استرجاع بيانات" -> code.subscriptionType.contains("بيانات") || code.subscriptionType.contains("ملفات")
            "تسويق رقمي" -> code.subscriptionType.contains("تسويق")
            else -> true
        }

        matchesQuery && matchesFilter
    }

    val availableCodesCount = activationCodes.count { !it.isUsed && it.status == "نشط ومتاح" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ImmersiveBg),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Master Owner Header Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                border = BorderStroke(1.2.dp, EmeraldPrimary.copy(alpha = 0.4f)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(DarkButtonBg)
                                    .border(1.2.dp, EmeraldPrimary.copy(alpha = 0.6f), RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.rqeebpro_logo_1791221152168),
                                    contentDescription = "شعار رقيب برو",
                                    modifier = Modifier.size(34.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "لوحة تحكم المالك الشاملة",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextWhite
                                )
                                Text(
                                    text = "م. عبد الرقيب فرج | الرقم: 785502919",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = EmeraldPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        IconButton(
                            onClick = onLockDashboard,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(DarkButtonBg)
                                .border(1.dp, DarkButtonBorder, CircleShape)
                        ) {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "قفل اللوحة",
                                tint = AccentOrange,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    HorizontalDivider(color = ImmersiveBorder)

                    // Navigation Tabs: المشتركون والمدفوعات vs أكواد التفعيل
                    TabRow(
                        selectedTabIndex = selectedDashboardTab,
                        containerColor = ImmersiveSurfaceVariant,
                        contentColor = TextWhite,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp)),
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedDashboardTab]),
                                color = EmeraldPrimary,
                                height = 3.dp
                            )
                        },
                        divider = {}
                    ) {
                        Tab(
                            selected = selectedDashboardTab == 0,
                            onClick = { selectedDashboardTab = 0 },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Storage,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = if (selectedDashboardTab == 0) EmeraldPrimary else TextSlate
                                    )
                                    Text(
                                        text = "المشتركون والإيرادات",
                                        fontWeight = if (selectedDashboardTab == 0) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 12.5.sp,
                                        color = if (selectedDashboardTab == 0) TextWhite else TextSlate
                                    )
                                }
                            }
                        )

                        Tab(
                            selected = selectedDashboardTab == 1,
                            onClick = { selectedDashboardTab = 1 },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        Icons.Default.LockOpen,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = if (selectedDashboardTab == 1) AccentTeal else TextSlate
                                    )
                                    Text(
                                        text = "أكواد التفعيل (${activationCodes.size})",
                                        fontWeight = if (selectedDashboardTab == 1) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 12.5.sp,
                                        color = if (selectedDashboardTab == 1) TextWhite else TextSlate
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }

        // ==================== TAB 0: المشتركون والإيرادات ====================
        if (selectedDashboardTab == 0) {
            // Financial KPI Cards (YER, SAR, USD)
            item {
                Text(
                    text = "💰 إجمالي الإيرادات المسجلة والمحصلة بقاعدة البيانات:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // YER Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                        border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.35f)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("الريال اليمني", fontSize = 11.5.sp, color = TextSlate)
                            Text("$revenueYER", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = EmeraldPrimary)
                            Text("YER", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSlate)
                        }
                    }

                    // SAR Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                        border = BorderStroke(1.dp, AccentBlue.copy(alpha = 0.35f)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("الريال السعودي", fontSize = 11.5.sp, color = TextSlate)
                            Text("$revenueSAR", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = AccentBlue)
                            Text("SAR", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSlate)
                        }
                    }

                    // USD Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                        border = BorderStroke(1.dp, AccentPurple.copy(alpha = 0.35f)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("الدولار الأمريكي", fontSize = 11.5.sp, color = TextSlate)
                            Text("$revenueUSD", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = AccentPurple)
                            Text("USD", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSlate)
                        }
                    }
                }
            }

            // Database stats summary row
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                    border = BorderStroke(1.dp, ImmersiveBorder),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("إجمالي المشتركين", fontSize = 11.5.sp, color = TextSlate)
                                Text("$subscribersCount مشترك", fontSize = 14.5.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                            }
                            Column {
                                Text("قيد المراجعة", fontSize = 11.5.sp, color = TextSlate)
                                Text("$pendingCount طلب", fontSize = 14.5.sp, fontWeight = FontWeight.Bold, color = StatusWarning)
                            }
                            Column {
                                Text("المعتمدون", fontSize = 11.5.sp, color = TextSlate)
                                Text("$approvedCount مشترك", fontSize = 14.5.sp, fontWeight = FontWeight.Bold, color = StatusSuccess)
                            }
                            Column {
                                Text("حالة السيرفر", fontSize = 11.5.sp, color = TextSlate)
                                Text("محلي نشط", fontSize = 14.5.sp, fontWeight = FontWeight.Bold, color = AccentTeal)
                            }
                        }

                        HorizontalDivider(color = ImmersiveBorderSubtle)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { showAddClientDialog = true },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = EmeraldPrimary,
                                    contentColor = ImmersiveBg
                                )
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("إضافة عميل يدوياً", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = {
                                    val report = buildString {
                                        appendLine("📊 تقرير قاعدة بيانات المشتركين - مركز رقيب للبرمجة والتسويق")
                                        appendLine("المالك: عبد الرقيب فرج (785502919)")
                                        appendLine("• إجمالي المشتركين: $subscribersCount")
                                        appendLine("• الإيرادات بالسعودي: $revenueSAR SAR")
                                        appendLine("• الإيرادات بالدولار: $revenueUSD USD")
                                        appendLine("-----------------------------")
                                        subscriptions.forEachIndexed { i, sub ->
                                            appendLine("${i + 1}. ${sub.fullName} (${sub.phoneNumber})")
                                            appendLine("   الجهاز: ${sub.phoneModel} | الخدمة: ${sub.serviceName}")
                                            appendLine("   المبلغ: ${sub.priceAmount} ${sub.currency} | الحالة: ${sub.status}")
                                            appendLine("   الحوالة: ${sub.transactionRef}")
                                        }
                                    }
                                    AppIntentUtils.copyToClipboard(context, "تقرير المشتركين", report)
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, DarkButtonBorder),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = DarkButtonBg,
                                    contentColor = TextWhite
                                )
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("نسخ التقرير", fontSize = 12.5.sp)
                            }
                        }
                    }
                }
            }

            // Search & Filter Bar
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "مسح", tint = TextSlate, modifier = Modifier.size(16.dp))
                                }
                            }
                        },
                        placeholder = { Text("بحث باسم العميل، الهاتف، الجهاز، أو الحوالة...", fontSize = 13.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = EmeraldPrimary,
                            unfocusedBorderColor = ImmersiveBorder,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            cursorColor = EmeraldPrimary,
                            focusedContainerColor = ImmersiveSurfaceCard,
                            unfocusedContainerColor = ImmersiveSurfaceCard
                        ),
                        singleLine = true
                    )

                    // Status Filter Chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val statuses = listOf("الكل", "قيد المراجعة", "تم التحقق والسداد", "مكتمل", "ملغي")
                        items(statuses) { status ->
                            FilterChip(
                                selected = selectedStatusFilter == status,
                                onClick = { selectedStatusFilter = status },
                                label = { Text(status, fontSize = 11.5.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldContainer,
                                    selectedLabelColor = EmeraldPrimary,
                                    containerColor = DarkButtonBg,
                                    labelColor = TextSlate
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = selectedStatusFilter == status,
                                    borderColor = DarkButtonBorder,
                                    selectedBorderColor = EmeraldPrimary
                                )
                            )
                        }
                    }

                    // Currency Filter Chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val currencies = listOf("الكل", "YER", "SAR", "USD")
                        items(currencies) { curr ->
                            FilterChip(
                                selected = selectedCurrencyFilter == curr,
                                onClick = { selectedCurrencyFilter = curr },
                                label = { Text(if (curr == "الكل") "كل العملات" else curr, fontSize = 11.5.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AccentBlueContainer,
                                    selectedLabelColor = AccentBlue,
                                    containerColor = DarkButtonBg,
                                    labelColor = TextSlate
                                )
                            )
                        }
                    }
                }
            }

            // Subscriptions List
            item {
                Text(
                    text = "سجل المشتركين (${filteredSubscriptions.size}):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            if (filteredSubscriptions.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                        border = BorderStroke(1.dp, ImmersiveBorder),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Storage,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(42.dp)
                            )
                            Text(
                                text = "لا توجد سجلات مطابقة للبحث أو الفلتر حالياً",
                                color = TextSlate,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center
                            )
                            Button(
                                onClick = { showAddClientDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary, contentColor = ImmersiveBg),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("إضافة عميل يدوياً", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                items(filteredSubscriptions, key = { it.id }) { client ->
                    ClientSubscriptionItemCard(
                        client = client,
                        context = context,
                        onApprove = {
                            onUpdateStatus(client.id, "تم التحقق والسداد", true)
                        },
                        onComplete = {
                            onUpdateStatus(client.id, "مكتمل", true)
                        },
                        onEditNotes = {
                            clientToEditNotes = client
                        },
                        onDelete = {
                            clientToDelete = client
                        }
                    )
                }
            }
        }

        // ==================== TAB 1: مولد وإدارة أكواد التفعيل ====================
        if (selectedDashboardTab == 1) {
            // Activation Codes Overview & Master Code Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                    border = BorderStroke(1.2.dp, AccentTeal.copy(alpha = 0.4f)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "مولد أكواد التفعيل حسب نوع الاشتراك",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = TextWhite
                                )
                                Text(
                                    text = "أنشئ أكواد من 4 أرقام للمشتركين حسب الباقة لتفعيل التطبيق فوراً",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSlate,
                                    lineHeight = 17.sp
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(AccentTealContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.LockOpen, contentDescription = null, tint = AccentTeal, modifier = Modifier.size(20.dp))
                            }
                        }

                        HorizontalDivider(color = ImmersiveBorderSubtle)

                        // 4 Stats columns
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("إجمالي الأكواد", fontSize = 11.sp, color = TextSlate)
                                Text("${activationCodes.size}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextWhite)
                            }
                            Column {
                                Text("متاحة للتفعيل", fontSize = 11.sp, color = TextSlate)
                                Text("$availableCodesCount", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = EmeraldPrimary)
                            }
                            Column {
                                Text("تم استخدامها", fontSize = 11.sp, color = TextSlate)
                                Text("${activationCodes.count { it.isUsed }}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = AccentTeal)
                            }
                            Column {
                                Text("كود المالك الرئيسي", fontSize = 11.sp, color = TextSlate)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("8492", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = AccentOrange)
                                    IconButton(
                                        onClick = {
                                            AppIntentUtils.copyToClipboard(context, "كود المالك السري", "8492")
                                        },
                                        modifier = Modifier.size(22.dp)
                                    ) {
                                        Icon(Icons.Default.ContentCopy, contentDescription = "نسخ", tint = AccentOrange, modifier = Modifier.size(12.dp))
                                    }
                                }
                            }
                        }

                        // Generate New Code Action Button
                        Button(
                            onClick = { showGenerateCodeDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EmeraldPrimary,
                                contentColor = ImmersiveBg
                            ),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "إنشاء كود تفعيل جديد حسب نوع الاشتراك",
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Search & Filter for Codes
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = codeSearchQuery,
                        onValueChange = { codeSearchQuery = it },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = AccentTeal, modifier = Modifier.size(18.dp))
                        },
                        trailingIcon = {
                            if (codeSearchQuery.isNotEmpty()) {
                                IconButton(onClick = { codeSearchQuery = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "مسح", tint = TextSlate, modifier = Modifier.size(16.dp))
                                }
                            }
                        },
                        placeholder = { Text("بحث برقم الكود (4 أرقام)، اسم العميل، أو الباقة...", fontSize = 12.5.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentTeal,
                            unfocusedBorderColor = ImmersiveBorder,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            cursorColor = AccentTeal,
                            focusedContainerColor = ImmersiveSurfaceCard,
                            unfocusedContainerColor = ImmersiveSurfaceCard
                        ),
                        singleLine = true
                    )

                    // Code Filter Chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val filters = listOf("الكل", "نشط ومتاح", "مستخدم ومفعل", "معلق", "شامل VIP", "برمجة وفك شفرات", "استرجاع بيانات", "تسويق رقمي")
                        items(filters) { f ->
                            FilterChip(
                                selected = selectedCodeFilter == f,
                                onClick = { selectedCodeFilter = f },
                                label = { Text(f, fontSize = 11.5.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AccentTealContainer,
                                    selectedLabelColor = AccentTeal,
                                    containerColor = DarkButtonBg,
                                    labelColor = TextSlate
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = selectedCodeFilter == f,
                                    borderColor = DarkButtonBorder,
                                    selectedBorderColor = AccentTeal
                                )
                            )
                        }
                    }
                }
            }

            // Codes List Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "قائمة الأكواد (${filteredCodes.size}):",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "اضغط على أي كود لنسخه أو إرساله للعميل",
                        fontSize = 11.sp,
                        color = TextSlate
                    )
                }
            }

            if (filteredCodes.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                        border = BorderStroke(1.dp, ImmersiveBorder),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.LockOpen,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(42.dp)
                            )
                            Text(
                                text = "لا توجد أكواد تفعيل مطابقة للبحث أو الفلتر",
                                color = TextSlate,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center
                            )
                            Button(
                                onClick = { showGenerateCodeDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary, contentColor = ImmersiveBg),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("إنشاء كود تفعيل جديد الآن", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                items(filteredCodes, key = { it.id }) { codeEntity ->
                    ActivationCodeItemCard(
                        codeEntity = codeEntity,
                        context = context,
                        onToggleStatus = {
                            val newStatus = when (codeEntity.status) {
                                "نشط ومتاح" -> "معلق"
                                "معلق" -> "نشط ومتاح"
                                else -> "نشط ومتاح"
                            }
                            onUpdateCodeStatus(codeEntity.id, newStatus)
                        },
                        onDelete = {
                            codeToDelete = codeEntity
                        }
                    )
                }
            }
        }
    }

    // Dialog: Add Client Manually
    if (showAddClientDialog) {
        AddManualClientDialog(
            onDismiss = { showAddClientDialog = false },
            onSubmit = { name, phone, model, problem, service, currency, amount, payment, ref, status, notes ->
                onAddManualClient(name, phone, model, problem, service, currency, amount, payment, ref, status, notes)
                showAddClientDialog = false
            }
        )
    }

    // Dialog: Edit Notes
    clientToEditNotes?.let { client ->
        EditClientNotesDialog(
            client = client,
            onDismiss = { clientToEditNotes = null },
            onSave = { newNotes, newStatus ->
                onUpdateNotes(client.id, newNotes)
                onUpdateStatus(client.id, newStatus, newStatus == "تم التحقق والسداد" || newStatus == "مكتمل")
                clientToEditNotes = null
            }
        )
    }

    // Dialog: Delete Client
    clientToDelete?.let { client ->
        AlertDialog(
            onDismissRequest = { clientToDelete = null },
            title = { Text("تأكيد حذف السجل من قاعدة البيانات", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 15.sp) },
            text = {
                Text(
                    "هل أنت متأكد من حذف بيانات العميل: ${client.fullName} (${client.phoneNumber}) من قاعدة بيانات المالك نهائياً؟",
                    color = TextSlate,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteClient(client.id)
                        clientToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusError)
                ) {
                    Text("نعم، حذف", color = TextWhite, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { clientToDelete = null }) {
                    Text("إلغاء", color = TextSlate)
                }
            },
            containerColor = ImmersiveSurfaceCard
        )
    }

    // Dialog: Generate Activation Code
    if (showGenerateCodeDialog) {
        GenerateActivationCodeDialog(
            onDismiss = { showGenerateCodeDialog = false },
            onGenerate = { subType, clientName, clientPhone, priceAmt, currency, duration, customPin, notes ->
                onGenerateCode(subType, clientName, clientPhone, priceAmt, currency, duration, customPin, notes)
                showGenerateCodeDialog = false
            }
        )
    }

    // Dialog: Delete Activation Code
    codeToDelete?.let { code ->
        AlertDialog(
            onDismissRequest = { codeToDelete = null },
            title = { Text("حذف كود التفعيل", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 15.sp) },
            text = {
                Text(
                    "هل تريد حذف كود التفعيل: [ ${code.code} ] المخصص لباقة (${code.subscriptionType}) نهائياً؟",
                    color = TextSlate,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteCode(code.id)
                        codeToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusError)
                ) {
                    Text("حذف الكود", color = TextWhite, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { codeToDelete = null }) {
                    Text("إلغاء", color = TextSlate)
                }
            },
            containerColor = ImmersiveSurfaceCard
        )
    }
}

@Composable
private fun ActivationCodeItemCard(
    codeEntity: ActivationCodeEntity,
    context: Context,
    onToggleStatus: () -> Unit,
    onDelete: () -> Unit
) {
    val dateString = remember(codeEntity.createdAt) {
        try {
            val sdf = SimpleDateFormat("yyyy/MM/dd", Locale("ar"))
            sdf.format(Date(codeEntity.createdAt))
        } catch (_: Exception) {
            "حديثاً"
        }
    }

    val (statusColor, statusBg) = when {
        codeEntity.isUsed || codeEntity.status == "مستخدم ومفعل" -> AccentTeal to AccentTealContainer
        codeEntity.status == "معلق" -> StatusError to Color(0x26EF4444)
        else -> EmeraldPrimary to EmeraldContainer
    }

    val typeColor = when {
        codeEntity.subscriptionType.contains("شامل") -> AccentOrange
        codeEntity.subscriptionType.contains("برمجة") || codeEntity.subscriptionType.contains("شفرات") -> AccentBlue
        codeEntity.subscriptionType.contains("بيانات") || codeEntity.subscriptionType.contains("ملفات") -> AccentPink
        codeEntity.subscriptionType.contains("تسويق") -> AccentPurple
        else -> AccentTeal
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
        border = BorderStroke(1.2.dp, if (!codeEntity.isUsed && codeEntity.status == "نشط ومتاح") EmeraldPrimary.copy(alpha = 0.5f) else ImmersiveBorder),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header: 4-digit code in glowing badge & subscription type
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 4-Digit PIN Box
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (codeEntity.isUsed) DarkButtonBg else Color(0x2634D399))
                            .border(
                                1.5.dp,
                                if (codeEntity.isUsed) DarkButtonBorder else EmeraldPrimary,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                AppIntentUtils.copyToClipboard(context, "كود التفعيل", codeEntity.code)
                            }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = codeEntity.code,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = if (codeEntity.isUsed) TextSlate else EmeraldPrimary,
                            letterSpacing = 3.sp
                        )
                    }

                    IconButton(
                        onClick = {
                            AppIntentUtils.copyToClipboard(context, "كود التفعيل", codeEntity.code)
                        },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(DarkButtonBg)
                    ) {
                        Icon(
                            Icons.Default.ContentCopy,
                            contentDescription = "نسخ الكود",
                            tint = EmeraldPrimary,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }

                // Status Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = statusBg,
                    border = BorderStroke(1.dp, statusColor.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = if (codeEntity.isUsed) "مستخدم ومفعل" else codeEntity.status,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            HorizontalDivider(color = ImmersiveBorderSubtle)

            // Subscription Details
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                DetailRow(
                    label = "نوع الاشتراك:",
                    value = codeEntity.subscriptionType,
                    valueColor = typeColor,
                    isBold = true
                )
                DetailRow(
                    label = "المدة والصلاحية:",
                    value = codeEntity.durationLabel,
                    valueColor = TextWhite
                )
                if (codeEntity.priceAmount > 0) {
                    DetailRow(
                        label = "قيمة الاشتراك:",
                        value = "${codeEntity.priceAmount} ${codeEntity.currency}",
                        valueColor = EmeraldPrimary,
                        isBold = true
                    )
                }
                if (codeEntity.clientName.isNotBlank()) {
                    DetailRow(
                        label = "مخصص للعميل:",
                        value = "${codeEntity.clientName} ${if (codeEntity.clientPhone.isNotBlank()) "(${codeEntity.clientPhone})" else ""}",
                        valueColor = AccentBlue
                    )
                }
                DetailRow(
                    label = "تاريخ الإنشاء:",
                    value = dateString,
                    valueColor = TextMuted
                )
                if (codeEntity.notes.isNotBlank()) {
                    DetailRow(
                        label = "ملاحظات:",
                        value = codeEntity.notes,
                        valueColor = StatusWarning
                    )
                }
            }

            HorizontalDivider(color = ImmersiveBorderSubtle)

            // Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // WhatsApp Send to Client Button
                Button(
                    onClick = {
                        val message = """
                            مرحباً بك عزيزي المشترك،
                            تم إصدار كود تفعيل اشتراكك في تطبيق رقيب للبرمجة والتسويق:
                            
                            🔑 كود التفعيل: ${codeEntity.code}
                            📦 نوع الاشتراك: ${codeEntity.subscriptionType}
                            ⏳ مدة الصلاحية: ${codeEntity.durationLabel}
                            
                            طريقة التفعيل:
                            1. افتح تطبيق رقيب على هاتفك.
                            2. اضغط على 'الاشتراك وإرسال إشعار التفعيل'.
                            3. أدخل كود التفعيل أعلاه المكون من 4 أرقام في خانة التحقق.
                            4. اضغط 'الدخول لخدمات التطبيق' فوراً!
                        """.trimIndent()
                        if (codeEntity.clientPhone.isNotBlank()) {
                            AppIntentUtils.openWhatsAppWithNumber(context, codeEntity.clientPhone, message)
                        } else {
                            AppIntentUtils.openWhatsApp(context, message)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary, contentColor = ImmersiveBg),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 7.dp)
                ) {
                    Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("إرسال واتساب", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                }

                // Copy Code & Details Button
                OutlinedButton(
                    onClick = {
                        val copyText = "كود تفعيل تطبيق رقيب (${codeEntity.subscriptionType}): ${codeEntity.code}"
                        AppIntentUtils.copyToClipboard(context, "بيانات كود التفعيل", copyText)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, DarkButtonBorder),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkButtonBg, contentColor = TextWhite),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 7.dp)
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp), tint = AccentTeal)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("نسخ الكود", fontSize = 11.5.sp)
                }

                // Toggle status (Active / Suspended)
                IconButton(
                    onClick = onToggleStatus,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkButtonBg)
                ) {
                    Icon(
                        imageVector = if (codeEntity.status == "معلق") Icons.Default.Check else Icons.Default.Close,
                        contentDescription = "تغيير الحالة",
                        tint = if (codeEntity.status == "معلق") StatusSuccess else AccentOrange,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Delete code
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkButtonBg)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "حذف الكود", tint = StatusError, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ClientSubscriptionItemCard(
    client: ClientSubscriptionEntity,
    context: Context,
    onApprove: () -> Unit,
    onComplete: () -> Unit,
    onEditNotes: () -> Unit,
    onDelete: () -> Unit
) {
    val dateString = remember(client.createdAt) {
        try {
            val sdf = SimpleDateFormat("yyyy/MM/dd - hh:mm a", Locale("ar"))
            sdf.format(Date(client.createdAt))
        } catch (_: Exception) {
            "الآن"
        }
    }

    val (statusColor, statusContainer) = when (client.status) {
        "تم التحقق والسداد" -> StatusSuccess to EmeraldContainer
        "مكتمل" -> AccentTeal to AccentTealContainer
        "ملغي" -> StatusError to Color(0x26EF4444)
        else -> StatusWarning to AccentOrangeContainer
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
        border = BorderStroke(1.dp, ImmersiveBorder),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row: Name, Device, and Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(DarkButtonBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                    }
                    Column {
                        Text(
                            text = client.fullName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Text(
                            text = "جهاز: ${client.phoneModel.ifBlank { "غير محدد" }}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSlate
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = statusContainer,
                    border = BorderStroke(1.dp, statusColor.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = client.status,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            HorizontalDivider(color = ImmersiveBorderSubtle)

            // Details Grid
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                DetailRow(label = "الخدمة المطلوبة:", value = client.serviceName, valueColor = AccentTeal)
                if (client.problemType.isNotBlank()) {
                    DetailRow(label = "نوع المشكلة:", value = client.problemType, valueColor = AccentOrange)
                }
                DetailRow(
                    label = "قيمة الاشتراك المنفصل:",
                    value = "${client.priceAmount} ${client.currency}",
                    valueColor = EmeraldPrimary,
                    isBold = true
                )
                if (client.paymentMethod.isNotBlank()) {
                    DetailRow(label = "وسيلة السداد:", value = client.paymentMethod, valueColor = TextWhite)
                }
                if (client.transactionRef.isNotBlank()) {
                    DetailRow(label = "رقم الحوالة/الإشعار:", value = client.transactionRef, valueColor = AccentBlue)
                }
                DetailRow(label = "تاريخ التسجيل:", value = dateString, valueColor = TextMuted)
                if (client.ownerNotes.isNotBlank()) {
                    DetailRow(label = "ملاحظات المالك:", value = client.ownerNotes, valueColor = StatusWarning)
                }
            }

            HorizontalDivider(color = ImmersiveBorderSubtle)

            // Owner Action Row (Call, WhatsApp, Approve, Complete, Edit, Delete)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Direct WhatsApp Button
                Button(
                    onClick = {
                        val message = """
                            مرحباً بك أستاذ ${client.fullName}،
                            معك المهندس عبد الرقيب فرج (مالك مركز رقيب للبرمجة والتسويق).
                            بخصوص طلبك لخدمة: ${client.serviceName}
                            هاتفك: ${client.phoneModel}
                            نحن على أتم الاستعداد لخدمتك فوراً.
                        """.trimIndent()
                        AppIntentUtils.openWhatsAppWithNumber(context, client.phoneNumber, message)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary, contentColor = ImmersiveBg),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("واتساب", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                }

                // Direct Call Button
                OutlinedButton(
                    onClick = {
                        AppIntentUtils.dialPhoneNumber(context, client.phoneNumber)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, DarkButtonBorder),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkButtonBg, contentColor = TextWhite),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(15.dp), tint = AccentBlue)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("اتصال", fontSize = 11.5.sp)
                }

                // Approve Button
                if (client.status == "قيد المراجعة") {
                    IconButton(
                        onClick = onApprove,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(EmeraldContainer)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = "اعتماد السداد", tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                    }
                } else if (client.status == "تم التحقق والسداد") {
                    IconButton(
                        onClick = onComplete,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(AccentTealContainer)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "إكمال الخدمة", tint = AccentTeal, modifier = Modifier.size(18.dp))
                    }
                }

                // Edit Notes Button
                IconButton(
                    onClick = onEditNotes,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkButtonBg)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "تعديل الملاحظات", tint = TextSlate, modifier = Modifier.size(16.dp))
                }

                // Delete Button
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkButtonBg)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "حذف السجل", tint = StatusError, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = TextWhite,
    isBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 11.5.sp, color = TextSlate)
        Text(
            text = value,
            fontSize = 12.5.sp,
            fontWeight = if (isBold) FontWeight.ExtraBold else FontWeight.Medium,
            color = valueColor,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun GenerateActivationCodeDialog(
    onDismiss: () -> Unit,
    onGenerate: (
        subscriptionType: String,
        clientName: String,
        clientPhone: String,
        priceAmount: Int,
        currency: String,
        durationLabel: String,
        customCode: String?,
        notes: String
    ) -> Unit
) {
    val subscriptionTypes = listOf(
        "اشتراك شامل VIP لكافة الخدمات",
        "اشتراك حلول وبرمجة الهاتف وفك الشفرات",
        "اشتراك محاكي فك القفل وتجاوز حساب جوجل FRP",
        "اشتراك فاحص استرداد البيانات والملفات",
        "اشتراك ضبط وتفعيل بيانات 3G/4G",
        "اشتراك التسويق الرقمي وإدارة الحملات",
        "اشتراك سنوي VIP كامل",
        "اشتراك شهري مميز",
        "كود مخصص / تجريبي"
    )

    val durations = listOf(
        "سنة كاملة",
        "6 أشهر",
        "3 أشهر",
        "شهر واحد (30 يوماً)",
        "مدى الحياة VIP"
    )

    val currencies = listOf("YER", "SAR", "USD")

    var selectedType by remember { mutableStateOf(subscriptionTypes.first()) }
    var selectedDuration by remember { mutableStateOf(durations.first()) }
    var selectedCurrency by remember { mutableStateOf("YER") }
    var priceText by remember { mutableStateOf("2000") }
    var clientName by remember { mutableStateOf("") }
    var clientPhone by remember { mutableStateOf("") }
    var customCodeInput by remember { mutableStateOf((1000..9999).random().toString()) }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(EmeraldContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.LockOpen, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                }
                Text("🔑 إنشاء كود تفعيل اشتراك جديد", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 15.sp)
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Subscription Type Selector
                item {
                    Text("اختر نوع الاشتراك المخصص للكود:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextSlate)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        items(subscriptionTypes) { type ->
                            FilterChip(
                                selected = selectedType == type,
                                onClick = {
                                    selectedType = type
                                    // Set smart default price
                                    priceText = when {
                                        type.contains("شامل") || type.contains("سنوي") -> if (selectedCurrency == "YER") "10000" else if (selectedCurrency == "SAR") "80" else "20"
                                        type.contains("بيانات") -> if (selectedCurrency == "YER") "4000" else if (selectedCurrency == "SAR") "30" else "8"
                                        type.contains("تسويق") -> if (selectedCurrency == "YER") "5000" else if (selectedCurrency == "SAR") "40" else "10"
                                        type.contains("تجريبي") -> "0"
                                        else -> if (selectedCurrency == "YER") "2000" else if (selectedCurrency == "SAR") "15" else "4"
                                    }
                                },
                                label = { Text(type, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldContainer,
                                    selectedLabelColor = EmeraldPrimary,
                                    containerColor = DarkButtonBg,
                                    labelColor = TextSlate
                                )
                            )
                        }
                    }
                }

                // 4-Digit Code Box & Randomizer
                item {
                    Text("رمز التفعيل (4 أرقام):", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextSlate)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = customCodeInput,
                            onValueChange = { input ->
                                val filtered = input.filter { it.isDigit() }
                                if (filtered.length <= 4) {
                                    customCodeInput = filtered
                                }
                            },
                            label = { Text("الكود (4 أرقام)") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                letterSpacing = 2.sp,
                                textAlign = TextAlign.Center
                            )
                        )

                        Button(
                            onClick = {
                                customCodeInput = (1000..9999).random().toString()
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AccentTealContainer, contentColor = AccentTeal),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 12.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("توليد عشوائي", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Duration Selector
                item {
                    Text("مدة الاشتراك:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextSlate)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        items(durations) { dur ->
                            FilterChip(
                                selected = selectedDuration == dur,
                                onClick = { selectedDuration = dur },
                                label = { Text(dur, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AccentBlueContainer,
                                    selectedLabelColor = AccentBlue,
                                    containerColor = DarkButtonBg,
                                    labelColor = TextSlate
                                )
                            )
                        }
                    }
                }

                // Currency & Price
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = priceText,
                            onValueChange = { priceText = it.filter { ch -> ch.isDigit() } },
                            label = { Text("المبلغ") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text("العملة:", fontSize = 11.sp, color = TextSlate)
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                currencies.forEach { curr ->
                                    FilterChip(
                                        selected = selectedCurrency == curr,
                                        onClick = { selectedCurrency = curr },
                                        label = { Text(curr, fontSize = 10.sp) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }

                // Client Details (Optional)
                item {
                    Text("بيانات العميل المستفيد (اختياري):", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextSlate)
                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        label = { Text("اسم العميل") },
                        placeholder = { Text("مثال: محمد علي") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = clientPhone,
                        onValueChange = { clientPhone = it },
                        label = { Text("رقم هاتف العميل") },
                        placeholder = { Text("مثال: 771234567") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // Notes
                item {
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("ملاحظات خاصة بالمالك") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        minLines = 2
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val codeFinal = if (customCodeInput.length == 4) customCodeInput else (1000..9999).random().toString()
                    val amountFinal = priceText.toIntOrNull() ?: 2000
                    onGenerate(selectedType, clientName, clientPhone, amountFinal, selectedCurrency, selectedDuration, codeFinal, notes)
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary, contentColor = ImmersiveBg),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("توليد وحفظ الكود فوراً", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء", color = TextSlate)
            }
        },
        containerColor = ImmersiveSurfaceCard
    )
}

@Composable
private fun AddManualClientDialog(
    onDismiss: () -> Unit,
    onSubmit: (
        fullName: String,
        phone: String,
        phoneModel: String,
        problemType: String,
        serviceName: String,
        currency: String,
        priceAmount: Int,
        paymentMethod: String,
        transactionRef: String,
        status: String,
        ownerNotes: String
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var phoneModel by remember { mutableStateOf("") }
    var problemType by remember { mutableStateOf("") }
    var serviceName by remember { mutableStateOf("إسترداد البيانات والملفات") }
    var currency by remember { mutableStateOf("YER") }
    var priceAmount by remember { mutableStateOf("4000") }
    var paymentMethod by remember { mutableStateOf("الكريمي (3001845477)") }
    var transactionRef by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("تم التحقق والسداد") }
    var ownerNotes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("➕ إضافة عميل جديد لقاعدة بيانات المالك", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 15.sp)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("الاسم واللقب") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = phoneModel,
                    onValueChange = { phoneModel = it },
                    label = { Text("نوع / موديل الهاتف") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = serviceName,
                    onValueChange = { serviceName = it },
                    label = { Text("الخدمة المطلوبة") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = priceAmount,
                        onValueChange = { priceAmount = it },
                        label = { Text("المبلغ") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = currency,
                        onValueChange = { currency = it },
                        label = { Text("العملة (YER/SAR/USD)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                OutlinedTextField(
                    value = transactionRef,
                    onValueChange = { transactionRef = it },
                    label = { Text("رقم الحوالة أو الإشعار") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = ownerNotes,
                    onValueChange = { ownerNotes = it },
                    label = { Text("ملاحظات المالك") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && phone.isNotBlank()) {
                        val amount = priceAmount.toIntOrNull() ?: 2000
                        onSubmit(name, phone, phoneModel, problemType, serviceName, currency, amount, paymentMethod, transactionRef, status, ownerNotes)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary, contentColor = ImmersiveBg),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("حفظ في قاعدة البيانات", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء", color = TextSlate)
            }
        },
        containerColor = ImmersiveSurfaceCard
    )
}

@Composable
private fun EditClientNotesDialog(
    client: ClientSubscriptionEntity,
    onDismiss: () -> Unit,
    onSave: (notes: String, status: String) -> Unit
) {
    var notes by remember { mutableStateOf(client.ownerNotes) }
    var status by remember { mutableStateOf(client.status) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("تعديل حالة وملاحظات: ${client.fullName}", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 15.sp)
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("تغيير حالة الطلب:", fontSize = 12.sp, color = TextSlate)
                val statuses = listOf("قيد المراجعة", "تم التحقق والسداد", "مكتمل", "ملغي")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(statuses) { s ->
                        FilterChip(
                            selected = status == s,
                            onClick = { status = s },
                            label = { Text(s, fontSize = 11.5.sp) }
                        )
                    }
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("ملاحظات المالك الخاصة") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(notes, status) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary, contentColor = ImmersiveBg),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("حفظ التغييرات", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء", color = TextSlate)
            }
        },
        containerColor = ImmersiveSurfaceCard
    )
}
