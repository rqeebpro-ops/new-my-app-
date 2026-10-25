package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.PaymentChannel
import com.example.model.PaymentCurrency
import com.example.model.PhoneModelsCatalog
import com.example.model.PhoneServiceDetail
import com.example.model.ProblemServiceOption
import com.example.model.ProblemServicesCatalog
import com.example.model.ServicePricing
import com.example.model.UserProfile
import com.example.ui.theme.*
import com.example.util.AppIntentUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LoginSubscriptionScreen(
    userProfile: UserProfile,
    services: List<PhoneServiceDetail>,
    isGateMode: Boolean = false,
    onSaveSubscription: (
        name: String,
        phone: String,
        phoneModel: String,
        problemType: String,
        serviceId: String,
        serviceName: String,
        currency: PaymentCurrency,
        priceAmount: Int,
        paymentAccount: String,
        transactionRef: String
    ) -> Unit,
    onLogout: () -> Unit,
    onProceedToServices: () -> Unit,
    onOpenOwnerDatabase: (() -> Unit)? = null,
    onRedeemActivationCode: ((String) -> Unit)? = null
) {
    val context = LocalContext.current

    // Form inputs state as requested: الاسم واللقب، رقم الهاتف، نوع الهاتف، نوع المشكلة
    var fullName by remember { mutableStateOf(userProfile.fullName) }
    var phoneNumber by remember { mutableStateOf(userProfile.phoneNumber) }
    var phoneModel by remember {
        mutableStateOf(
            if (userProfile.phoneModel.isNotBlank()) userProfile.phoneModel
            else PhoneModelsCatalog.phoneModelsList.first()
        )
    }
    var isPhoneModelDropdownExpanded by remember { mutableStateOf(false) }

    // Problem selection dropdown (قائمة منسدلة لنوع المشكلة)
    var selectedProblemOption by remember {
        mutableStateOf(
            ProblemServicesCatalog.findById(userProfile.selectedServiceId)
                ?: ProblemServicesCatalog.findByTitle(userProfile.problemType)
                ?: ProblemServicesCatalog.options.first()
        )
    }
    var isProblemTypeDropdownExpanded by remember { mutableStateOf(false) }
    var problemType by remember { mutableStateOf(selectedProblemOption.title) }

    // Currency selection (SAR / USD only - no Yemeni Riyal as requested)
    var selectedCurrency by remember {
        mutableStateOf(
            if (userProfile.currency == PaymentCurrency.YER) PaymentCurrency.SAR else userProfile.currency
        )
    }

    // Pricing tied directly to the selected problem option
    val currentPricing = selectedProblemOption.pricing
    val currentAmount = currentPricing.getAmount(selectedCurrency)

    // Payment Channel selection
    val paymentChannels = remember {
        listOf(
            PaymentChannel(
                id = "kuraimi",
                name = "بنك الكريمي",
                accountOrPhone = AppIntentUtils.KURAMI_ACCOUNT,
                typeLabel = "رقم الحساب",
                badge = "حساب بنكي معتمد",
                instruction = "التحويل عبر تطبيق الكريمي جوال أو أقرب فرع/وكيل كريمي إكسبرس"
            ),
            PaymentChannel(
                id = "jeep",
                name = "محفظة جيب (Jeep)",
                accountOrPhone = AppIntentUtils.JEEP_WALLET_PHONE,
                typeLabel = "رقم المحفظة",
                badge = "محفظة كاك بنك",
                instruction = "التحويل المباشر من تطبيق جيب إلى رقم الحساب 782916997"
            ),
            PaymentChannel(
                id = "one_cash",
                name = "محفظة ون كاش (One Cash)",
                accountOrPhone = AppIntentUtils.ONE_CASH_PHONE,
                typeLabel = "رقم المحفظة",
                badge = "محفظة إلكترونية",
                instruction = "التحويل الفوري من تطبيق ون كاش إلى الرقم 782916997"
            ),
            PaymentChannel(
                id = "jawwali",
                name = "محفظة جوالي (Jawwali)",
                accountOrPhone = AppIntentUtils.JAWWALI_PHONE,
                typeLabel = "رقم المحفظة",
                badge = "بنك اليمن والكويت",
                instruction = "التحويل المباشر من تطبيق جوالي إلى الرقم 782916997"
            )
        )
    }

    var selectedPaymentChannel by remember {
        mutableStateOf(
            paymentChannels.firstOrNull { it.name in userProfile.paymentAccountUsed } ?: paymentChannels.first()
        )
    }
    var isWalletDropdownExpanded by remember { mutableStateOf(false) }

    var transactionRef by remember { mutableStateOf(userProfile.paymentTransactionRef) }
    var validationError by remember { mutableStateOf<String?>(null) }
    var hasSentNotificationToOwner by remember { mutableStateOf(false) }
    var showSuccessCard by remember { mutableStateOf(userProfile.isSubscribed) }

    // 4-Digit Activation PIN state
    var activationPin by remember { mutableStateOf("") }
    val isPinVerified = remember(activationPin) {
        AppIntentUtils.isValidActivationPin(activationPin)
    }

    fun getWalletIcon(channelId: String): ImageVector {
        return when (channelId) {
            "kuraimi" -> Icons.Default.AccountBalance
            "jeep" -> Icons.Default.AccountBalanceWallet
            "one_cash" -> Icons.Default.CreditCard
            "jawwali" -> Icons.Default.PhoneAndroid
            else -> Icons.Default.AccountBalanceWallet
        }
    }

    // Harmonious modern OutlinedTextField colors
    val modernTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = EmeraldPrimary,
        unfocusedBorderColor = ImmersiveBorder,
        focusedLabelColor = EmeraldPrimary,
        unfocusedLabelColor = TextSlate,
        focusedTextColor = TextWhite,
        unfocusedTextColor = TextWhite,
        focusedPlaceholderColor = TextMuted,
        unfocusedPlaceholderColor = TextMuted,
        cursorColor = EmeraldPrimary,
        focusedContainerColor = ImmersiveSurfaceVariant,
        unfocusedContainerColor = ImmersiveSurfaceCard
    )

    fun buildOwnerNotificationMessage(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd - hh:mm a", Locale("ar"))
        val nowFormatted = dateFormat.format(Date())
        return """
            🔔 إشعار طلب اشتراك وتفعيل خدمة 🔔
            ━━━━━━━━━━━━━━━━━━━━
            👤 الاسم واللقب: ${fullName.trim()}
            📱 رقم الهاتف: ${phoneNumber.trim()}
            📲 نوع وموديل الهاتف: ${phoneModel.trim()}
            ⚠️ نوع المشكلة والخدمة المطلوبة: ${selectedProblemOption.title}
            ━━━━━━━━━━━━━━━━━━━━
            ⏱️ مدة صلاحية الاستخدام: 3 ساعات مستمرة
            🔒 الصلاحية: مخصصة حصرياً لخدمة [${selectedProblemOption.shortName}]
            💰 قيمة اشتراك الخدمة: $currentAmount ${selectedCurrency.code} (${selectedCurrency.symbolAr})
            📊 التسعيرة المعتمدة:
               • ريال سعودي: ${currentPricing.sar} SAR
               • دولار أمريكي: ${currentPricing.usd} USD
            💵 العملة المحددة للدفع: ${selectedCurrency.code} (${selectedCurrency.symbolAr})
            💳 الحساب المحول إليه: ${selectedPaymentChannel.name} (${selectedPaymentChannel.accountOrPhone})
            ${if (transactionRef.isNotBlank()) "🔖 رقم السند / العملية: ${transactionRef.trim()}" else "🔖 حالة السداد: إشعار تحويل مباشر"}
            ⏰ وقت وتاريخ الإرسال: $nowFormatted
            ━━━━━━━━━━━━━━━━━━━━
            أرجو تفعيل الخدمة لي لمدة 3 ساعات وإرسال كود التفعيل المكون من 4 أرقام.
        """.trimIndent()
    }

    var logoTapCount by remember { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ImmersiveBg)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 🌟 Redesigned Header: Premium Glowing Hero Card with Official Banner & Logo
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Official Rqeebpro Brand Banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rqeebpro_banner_1791221166842),
                            contentDescription = "بانر مركز رقيب الرسمي",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0x33000000),
                                            Color(0x88060B18),
                                            ImmersiveSurfaceCard
                                        )
                                    )
                                )
                        )
                    }

                    // Content Section with Logo
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 14.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                // Logo Box with cyber cyan glow & secret 10-tap entrance to Owner Dashboard
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(DarkButtonBg)
                                        .border(1.2.dp, CyberCyan.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                                        .clickable {
                                            logoTapCount++
                                            if (logoTapCount >= 10) {
                                                logoTapCount = 0
                                                android.widget.Toast.makeText(context, "تم تفعيل وضع المالك بنجاح", android.widget.Toast.LENGTH_SHORT).show()
                                                onOpenOwnerDatabase?.invoke()
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.rqeebpro_logo_1791221152168),
                                        contentDescription = "شعار مركز رقيب الرسمي",
                                        modifier = Modifier.size(46.dp)
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = if (isGateMode) "بوابة الدخول والاشتراك" else "تسجيل الدخول والاشتراك",
                                            fontSize = 15.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextWhite
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = CyberCyanContainer,
                                            border = BorderStroke(0.5.dp, CyberCyan.copy(alpha = 0.4f))
                                        ) {
                                            Text(
                                                text = "rqeebpro3 v3.0",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = CyberCyan,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = "مركز رقيب للبرمجة والتسويق الرقمي",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = CyberCyanLight
                                    )
                                }
                            }

                            // Subtle Divider Line
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(ImmersiveBorderSubtle)
                            )

                            // Micro Trust Indicators Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Icon(Icons.Default.Security, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(14.dp))
                                    Text("تشفير وأمان", fontSize = 11.sp, color = TextSlate)
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Icon(Icons.Default.Speed, contentDescription = null, tint = AccentTeal, modifier = Modifier.size(14.dp))
                                    Text("تفعيل فوري", fontSize = 11.sp, color = TextSlate)
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Icon(Icons.Default.Phone, contentDescription = null, tint = AccentBlue, modifier = Modifier.size(14.dp))
                                    Text("دعم فني مباشر", fontSize = 11.sp, color = TextSlate)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Active Subscription Status Banner (if already subscribed and logged in)
        if (showSuccessCard || userProfile.isSubscribed) {
            item {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0x1F34D399),
                    border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.6f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(EmeraldPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = ImmersiveBg,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "الحساب مسجل والاشتراك مفعّل بنجاح!",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldPrimary
                                )
                                Text(
                                    text = "مرحباً بك: ${fullName.ifBlank { userProfile.fullName }}",
                                    fontSize = 12.sp,
                                    color = TextWhite
                                )
                            }
                        }

                        Button(
                            onClick = onProceedToServices,
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary, contentColor = ImmersiveBg),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Text("الانتقال إلى خدمات التطبيق مباشرة", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        // 📋 Section 1: User & Device Information & Problem Selection (بيانات المستخدم والجهاز واختيار المشكلة والخدمة)
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                border = BorderStroke(1.dp, ImmersiveBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Harmonious Section Header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(EmeraldContainer)
                                .border(1.dp, EmeraldPrimary.copy(alpha = 0.3f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "1. بيانات المشترك ونوع الهاتف والمشكلة",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                            Text(
                                text = "حدد نوع الهاتف ونوع المشكلة لتحديد الخدمة وقيمة الاشتراك",
                                fontSize = 11.sp,
                                color = TextSlate
                            )
                        }
                    }

                    // 1. الاسم واللقب
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            validationError = null
                        },
                        label = { Text("الاسم واللقب *", fontSize = 12.sp) },
                        placeholder = { Text("مثال: عبد الرقيب فرج", fontSize = 12.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.AccountCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                        },
                        singleLine = true,
                        colors = modernTextFieldColors,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 2. رقم الهاتف
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {
                            phoneNumber = it
                            validationError = null
                        },
                        label = { Text("رقم الهاتف (واتساب) *", fontSize = 12.sp) },
                        placeholder = { Text("مثال: 785502919", fontSize = 12.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        colors = modernTextFieldColors,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 3. نوع الهاتف (قائمة منسدلة Dropdown)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "نوع وماركة الهاتف *",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSlate
                        )

                        ExposedDropdownMenuBox(
                            expanded = isPhoneModelDropdownExpanded,
                            onExpandedChange = { isPhoneModelDropdownExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = phoneModel,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("اختر نوع وماركة الهاتف (قائمة منسدلة)", fontSize = 12.sp) },
                                leadingIcon = {
                                    Icon(Icons.Default.Smartphone, contentDescription = null, tint = AccentTeal, modifier = Modifier.size(18.dp))
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPhoneModelDropdownExpanded)
                                },
                                colors = modernTextFieldColors,
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = isPhoneModelDropdownExpanded,
                                onDismissRequest = { isPhoneModelDropdownExpanded = false },
                                modifier = Modifier.background(ImmersiveSurfaceCard)
                            ) {
                                PhoneModelsCatalog.phoneModelsList.forEach { brand ->
                                    val isSelected = phoneModel == brand
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Icon(
                                                    Icons.Default.Smartphone,
                                                    contentDescription = null,
                                                    tint = if (isSelected) EmeraldPrimary else TextSlate,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Text(
                                                    text = brand,
                                                    color = if (isSelected) EmeraldPrimary else TextWhite,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                    fontSize = 12.sp
                                                )
                                            }
                                        },
                                        onClick = {
                                            phoneModel = brand
                                            isPhoneModelDropdownExpanded = false
                                            validationError = null
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // 4. نوع المشكلة (قائمة منسدلة Dropdown)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "نوع المشكلة والخدمة المصرح بها (قائمة منسدلة) *",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSlate
                        )

                        ExposedDropdownMenuBox(
                            expanded = isProblemTypeDropdownExpanded,
                            onExpandedChange = { isProblemTypeDropdownExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = selectedProblemOption.title,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("اختر نوع المشكلة (تظهر قيمة الاشتراك فور الاختيار)", fontSize = 12.sp) },
                                leadingIcon = {
                                    Icon(Icons.Default.Build, contentDescription = null, tint = AccentOrange, modifier = Modifier.size(18.dp))
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isProblemTypeDropdownExpanded)
                                },
                                colors = modernTextFieldColors,
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = isProblemTypeDropdownExpanded,
                                onDismissRequest = { isProblemTypeDropdownExpanded = false },
                                modifier = Modifier.background(ImmersiveSurfaceCard)
                            ) {
                                ProblemServicesCatalog.options.forEach { option ->
                                    val isSelected = selectedProblemOption.id == option.id
                                    val optPricing = option.pricing
                                    val optAmt = optPricing.getAmount(selectedCurrency)
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                                                    Text(
                                                        text = option.title,
                                                        color = if (isSelected) EmeraldPrimary else TextWhite,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 12.sp
                                                    )
                                                    Text(
                                                        text = option.description,
                                                        color = TextSlate,
                                                        fontSize = 10.sp,
                                                        maxLines = 2,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                                Surface(
                                                    shape = RoundedCornerShape(6.dp),
                                                    color = if (isSelected) EmeraldContainer else DarkButtonBg,
                                                    border = BorderStroke(1.dp, if (isSelected) EmeraldPrimary else DarkButtonBorder)
                                                ) {
                                                    Text(
                                                        text = "$optAmt ${selectedCurrency.code}",
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (isSelected) EmeraldPrimary else TextWhite,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                                    )
                                                }
                                            }
                                        },
                                        onClick = {
                                            selectedProblemOption = option
                                            problemType = option.title
                                            isProblemTypeDropdownExpanded = false
                                            validationError = null
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // 5. بطاقة قيمة الاشتراك والصلاحية (تظهر فور اختيار نوع المشكلة)
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceVariant),
                        border = BorderStroke(1.2.dp, EmeraldPrimary.copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Price Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "قيمة الاشتراك للخدمة المختارة:",
                                        fontSize = 11.sp,
                                        color = TextSlate
                                    )
                                    Text(
                                        text = selectedProblemOption.shortName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextWhite,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = EmeraldContainer,
                                    border = BorderStroke(1.dp, EmeraldPrimary)
                                ) {
                                    Text(
                                        text = "$currentAmount ${selectedCurrency.code}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldPrimary,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                                    )
                                }
                            }

                            // 3 Hours Continuous Usage Duration Badge
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0x2238BDF8),
                                border = BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.5f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Speed,
                                        contentDescription = null,
                                        tint = CyberCyan,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "مدة استخدام الخدمة: 3 ساعات مستمرة فور الدخول",
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CyberCyanLight
                                    )
                                }
                            }

                            // Exclusive Access Notice
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0x1EF59E0B),
                                border = BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.4f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Security,
                                        contentDescription = null,
                                        tint = Color(0xFFFBBF24),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "تنبيه: لا يمكن استخدام أي خدمة أخرى في التطبيق إلا هذه الخدمة المحددة فقط.",
                                        fontSize = 11.sp,
                                        color = Color(0xFFFDE68A),
                                        lineHeight = 15.sp
                                    )
                                }
                            }

                            // Currency Selector (SAR / USD only - no YER)
                            Text(
                                text = "اختر العملة المفضلة لديك للسداد:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextSlate
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                PaymentCurrency.activeCurrencies.forEach { cur ->
                                    val isSelected = selectedCurrency == cur
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { selectedCurrency = cur },
                                        label = {
                                            Text(
                                                text = "${cur.code} (${cur.symbolAr})",
                                                fontSize = 11.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        leadingIcon = if (isSelected) {
                                            { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(12.dp)) }
                                        } else null,
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = EmeraldPrimary,
                                            selectedLabelColor = ImmersiveBg,
                                            selectedLeadingIconColor = ImmersiveBg,
                                            containerColor = DarkButtonBg,
                                            labelColor = TextWhite
                                        ),
                                        border = FilterChipDefaults.filterChipBorder(
                                            enabled = true,
                                            selected = isSelected,
                                            borderColor = DarkButtonBorder,
                                            selectedBorderColor = EmeraldPrimary
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }

                            // Currency Equivalents Breakdown Row (SAR & USD)
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = ImmersiveSurfaceCard,
                                border = BorderStroke(1.dp, ImmersiveBorderSubtle)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("السعودي (SAR)", fontSize = 10.sp, color = TextSlate)
                                        Text("${currentPricing.sar} SAR", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (selectedCurrency == PaymentCurrency.SAR) EmeraldPrimary else TextWhite)
                                    }
                                    Box(modifier = Modifier.width(1.dp).height(20.dp).background(ImmersiveBorder))
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("الأمريكي (USD)", fontSize = 10.sp, color = TextSlate)
                                        Text("${currentPricing.usd} USD", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (selectedCurrency == PaymentCurrency.USD) EmeraldPrimary else TextWhite)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 💳 Section 2: Electronic Wallets & Transfer Channels (المحافظ الإلكترونية وطرق السداد)
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                border = BorderStroke(1.dp, ImmersiveBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AccentBlueContainer)
                                .border(1.dp, AccentBlue.copy(alpha = 0.3f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = null,
                                tint = AccentBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "2. طرق السداد والمحافظ المعتمدة",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                            Text(
                                text = "اختر المحفظة وانسخ رقم الحساب لإتمام السداد",
                                fontSize = 11.sp,
                                color = TextSlate
                            )
                        }
                    }

                    // Active Wallet Card with Quick Copy
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = ImmersiveSurfaceVariant,
                        border = BorderStroke(1.dp, AccentBlue.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isWalletDropdownExpanded = !isWalletDropdownExpanded }
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(AccentBlueContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = getWalletIcon(selectedPaymentChannel.id),
                                            contentDescription = null,
                                            tint = AccentBlue,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Column {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Text(
                                                text = selectedPaymentChannel.name,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = TextWhite
                                            )
                                            Surface(
                                                shape = RoundedCornerShape(4.dp),
                                                color = ImmersiveSurface
                                            ) {
                                                Text(
                                                    text = selectedPaymentChannel.badge,
                                                    fontSize = 9.sp,
                                                    color = TextSlate,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "انقر لتغيير المحفظة (${if (isWalletDropdownExpanded) "إغلاق" else "عرض المزيد"})",
                                            fontSize = 10.sp,
                                            color = AccentBlue
                                        )
                                    }
                                }

                                Icon(
                                    imageVector = if (isWalletDropdownExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = AccentBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            // Account Number Banner with Copy Action
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = ImmersiveSurface,
                                border = BorderStroke(1.dp, ImmersiveBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "${selectedPaymentChannel.typeLabel}:",
                                            fontSize = 10.sp,
                                            color = TextSlate
                                        )
                                        Text(
                                            text = selectedPaymentChannel.accountOrPhone,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextWhite,
                                            letterSpacing = 1.sp
                                        )
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            AppIntentUtils.copyToClipboard(
                                                context,
                                                selectedPaymentChannel.name,
                                                selectedPaymentChannel.accountOrPhone
                                            )
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, DarkButtonBorder),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            containerColor = DarkButtonBg,
                                            contentColor = TextWhite
                                        ),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = null,
                                            modifier = Modifier.size(13.dp),
                                            tint = TextWhite
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("نسخ", fontSize = 11.sp, color = TextWhite)
                                    }
                                }
                            }

                            // Transfer instruction
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = TextSlate, modifier = Modifier.size(13.dp))
                                Text(
                                    text = selectedPaymentChannel.instruction,
                                    fontSize = 10.sp,
                                    color = TextSlate,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }

                    // Expanded Wallet List
                    AnimatedVisibility(
                        visible = isWalletDropdownExpanded,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(ImmersiveSurface)
                                .border(1.dp, ImmersiveBorderSubtle, RoundedCornerShape(14.dp))
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "اختر المحفظة من الحسابات المعتمدة أدناه:",
                                fontSize = 11.sp,
                                color = TextSlate,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )

                            paymentChannels.forEach { channel ->
                                val isSelected = selectedPaymentChannel.id == channel.id
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) ImmersiveSurfaceVariant else Color.Transparent,
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) AccentBlue else Color.Transparent
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedPaymentChannel = channel
                                            isWalletDropdownExpanded = false
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .clip(CircleShape)
                                                    .background(if (isSelected) AccentBlueContainer else DarkButtonBg),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = getWalletIcon(channel.id),
                                                    contentDescription = null,
                                                    tint = if (isSelected) AccentBlue else TextSlate,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                            Column {
                                                Text(
                                                    text = channel.name,
                                                    fontSize = 12.sp,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                    color = TextWhite
                                                )
                                                Text(
                                                    text = "${channel.typeLabel}: ${channel.accountOrPhone}",
                                                    fontSize = 11.sp,
                                                    color = TextSlate
                                                )
                                            }
                                        }

                                        if (isSelected) {
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = AccentBlueContainer
                                            ) {
                                                Text(
                                                    text = "محدد ✓",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = AccentBlue,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Optional Transaction Ref
                    OutlinedTextField(
                        value = transactionRef,
                        onValueChange = { transactionRef = it },
                        label = { Text("رقم سند الإيداع أو الحوالة (اختياري)", fontSize = 12.sp) },
                        placeholder = { Text("مثال: رقم العملية 1048593 أو إشعار التحويل", fontSize = 12.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = AccentBlue, modifier = Modifier.size(18.dp))
                        },
                        singleLine = true,
                        colors = modernTextFieldColors,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // ⚠️ Error Message Banner
        if (validationError != null) {
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0x26F87171),
                    border = BorderStroke(1.dp, Color(0xFFF87171)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFF87171), modifier = Modifier.size(20.dp))
                        Text(
                            text = validationError ?: "",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFCA5A5),
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // ✅ Notification Sent Status Confirmation (when WhatsApp is opened to owner)
        if (hasSentNotificationToOwner) {
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0x1F34D399),
                    border = BorderStroke(1.dp, EmeraldPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                            Text(
                                text = "تم توجيه إشعار الاشتراك والتفعيل عبر الواتساب",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldPrimary
                            )
                        }

                        Text(
                            text = "تأكد من الضغط على زر الإرسال في تطبيق واتساب، ثم اضغط على زر الدخول أدناه للدخول إلى خدمات التطبيق مباشرة.",
                            fontSize = 11.sp,
                            color = TextWhite,
                            lineHeight = 16.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    val msg = buildOwnerNotificationMessage()
                                    AppIntentUtils.openWhatsAppToOwner(context, msg)
                                },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.5f)),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = DarkButtonBg,
                                    contentColor = EmeraldLight
                                ),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(13.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("إعادة فتح واتساب", fontSize = 11.sp)
                            }

                            OutlinedButton(
                                onClick = {
                                    val msg = buildOwnerNotificationMessage()
                                    AppIntentUtils.copyToClipboard(context, "إشعار اشتراك تطبيق رقيب", msg)
                                },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, DarkButtonBorder),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = DarkButtonBg,
                                    contentColor = TextWhite
                                ),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(13.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("نسخ الإشعار", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        // 🔐 4-Digit Activation PIN Card (يظهر بعد إرسال الإشعار عبر الواتساب)
        if (hasSentNotificationToOwner) {
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = ImmersiveSurfaceCard),
                    border = BorderStroke(
                        1.5.dp,
                        if (isPinVerified) EmeraldPrimary else AccentTeal.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isPinVerified) EmeraldContainer else AccentTealContainer)
                                    .border(
                                        1.dp,
                                        if (isPinVerified) EmeraldPrimary.copy(alpha = 0.5f) else AccentTeal.copy(alpha = 0.5f),
                                        RoundedCornerShape(10.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isPinVerified) Icons.Default.CheckCircle else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (isPinVerified) EmeraldPrimary else AccentTeal,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "رمز التفعيل والتحقق (4 أرقام)",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextWhite
                                    )
                                    if (isPinVerified) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = EmeraldContainer
                                        ) {
                                            Text(
                                                text = "مفعّل ✓",
                                                fontSize = 11.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = EmeraldPrimary,
                                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = "أدخل كود التفعيل السري المكون من 4 أرقام لتأكيد الاشتراك وبدء الاستخدام",
                                    fontSize = 12.5.sp,
                                    color = TextSlate,
                                    lineHeight = 18.sp
                                )
                            }
                        }

                        // 4-Digit Boxes Visual Display
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            (0..3).forEach { index ->
                                val digitChar = activationPin.getOrNull(index)?.toString() ?: ""
                                val isCurrentTarget = activationPin.length == index
                                val isFilled = index < activationPin.length

                                Box(
                                    modifier = Modifier
                                        .size(width = 54.dp, height = 58.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (isPinVerified) Color(0x1F34D399)
                                            else if (isCurrentTarget) ImmersiveSurfaceVariant
                                            else DarkButtonBg
                                        )
                                        .border(
                                            width = if (isPinVerified || isCurrentTarget) 1.5.dp else 1.dp,
                                            color = when {
                                                isPinVerified -> EmeraldPrimary
                                                isCurrentTarget -> AccentTeal
                                                isFilled -> TextSlate
                                                else -> DarkButtonBorder
                                            },
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (isFilled) digitChar else "•",
                                        fontSize = if (isFilled) 22.sp else 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = when {
                                            isPinVerified -> EmeraldPrimary
                                            isFilled -> TextWhite
                                            isCurrentTarget -> AccentTeal
                                            else -> TextMuted
                                        },
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        // PIN Input Field
                        OutlinedTextField(
                            value = activationPin,
                            onValueChange = { input ->
                                val filtered = input.filter { it.isDigit() }
                                if (filtered.length <= 4) {
                                    activationPin = filtered
                                    validationError = null
                                }
                            },
                            label = { Text("اكتب رمز التفعيل المكون من 4 أرقام هنا", fontSize = 12.sp) },
                            placeholder = { Text("مثال: **** ", fontSize = 12.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (isPinVerified) EmeraldPrimary else AccentTeal,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                if (activationPin.isNotEmpty()) {
                                    IconButton(onClick = { activationPin = "" }) {
                                        Text("مسح", fontSize = 11.sp, color = TextSlate)
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            singleLine = true,
                            colors = modernTextFieldColors,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Status Feedback
                        when {
                            isPinVerified -> {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = EmeraldContainer,
                                    border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.5f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                                        Text(
                                            text = "رمز التفعيل صحيح ومطابق! اضغط على زر الدخول أدناه لبدء الاستخدام فوراً.",
                                            fontSize = 12.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = EmeraldPrimary
                                        )
                                    }
                                }
                            }
                            activationPin.length == 4 && !isPinVerified -> {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0x26F87171),
                                    border = BorderStroke(1.dp, Color(0xFFF87171)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFF87171), modifier = Modifier.size(16.dp))
                                        Text(
                                            text = "رمز التفعيل غير صحيح! تأكد من إدخال الرمز الصحيح المستلم من مالك التطبيق.",
                                            fontSize = 12.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFCA5A5)
                                        )
                                    }
                                }
                            }
                            else -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Default.Info, contentDescription = null, tint = TextSlate, modifier = Modifier.size(14.dp))
                                    Text(
                                        text = if (activationPin.isEmpty()) "بانتظار إدخال رمز التفعيل (4 أرقام)"
                                               else "متبقي ${4 - activationPin.length} أرقام لاكتمال الرمز",
                                        fontSize = 11.5.sp,
                                        color = TextSlate
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 🚀 Section 4: Action Buttons (إرسال الإشعار والدخول للتطبيق)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // Main WhatsApp Subscription & Notification Button
                Button(
                    onClick = {
                        // Strict validation of the 4 requested fields:
                        if (fullName.isBlank()) {
                            validationError = "يرجى كتابة الاسم واللقب"
                            return@Button
                        }
                        if (phoneNumber.isBlank()) {
                            validationError = "يرجى كتابة رقم الهاتف للتواصل"
                            return@Button
                        }
                        if (phoneNumber.trim().length < 6) {
                            validationError = "يرجى إدخال رقم هاتف صحيح"
                            return@Button
                        }
                        if (phoneModel.isBlank()) {
                            validationError = "يرجى تحديد نوع وموديل الهاتف"
                            return@Button
                        }
                        if (problemType.isBlank()) {
                            validationError = "يرجى تحديد أو كتابة نوع المشكلة"
                            return@Button
                        }

                        validationError = null
                        val notificationMsg = buildOwnerNotificationMessage()

                        // Send WhatsApp notification to App Owner at 785502919
                        AppIntentUtils.openWhatsAppToOwner(context, notificationMsg)
                        hasSentNotificationToOwner = true
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldPrimary,
                        contentColor = ImmersiveBg
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "الاشتراك وإرسال إشعار التفعيل",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Entry button: only accessible once notification has been dispatched AND PIN is verified!
                Button(
                    onClick = {
                        if (!hasSentNotificationToOwner) {
                            validationError = "تنبيه: لا يمكن الدخول إلى خدمات التطبيق إلا بعد إرسال إشعار الاشتراك والتفعيل أولاً."
                            return@Button
                        }
                        if (!isPinVerified) {
                            validationError = "تنبيه: يرجى إدخال رمز التفعيل المكون من 4 أرقام الصحيح والمقدم من مالك التطبيق."
                            return@Button
                        }
                        if (fullName.isBlank() || phoneNumber.isBlank() || phoneModel.isBlank()) {
                            validationError = "يرجى إكمال جميع الحقول المطلوبة أولاً"
                            return@Button
                        }

                        val option = selectedProblemOption
                        val priceAmt = option.pricing.getAmount(selectedCurrency)
                        onSaveSubscription(
                            fullName.trim(),
                            phoneNumber.trim(),
                            phoneModel.trim(),
                            option.title,
                            option.id,
                            option.title,
                            selectedCurrency,
                            priceAmt,
                            "${selectedPaymentChannel.name} (${selectedPaymentChannel.accountOrPhone})",
                            transactionRef.trim()
                        )
                        showSuccessCard = true
                        validationError = null
                        onRedeemActivationCode?.invoke(activationPin.trim())
                        onProceedToServices()
                    },
                    enabled = hasSentNotificationToOwner && isPinVerified,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isPinVerified) EmeraldPrimary else AccentTeal,
                        contentColor = ImmersiveBg,
                        disabledContainerColor = DarkButtonBg,
                        disabledContentColor = TextMuted
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 13.dp)
                ) {
                    Icon(
                        imageVector = if (isPinVerified) Icons.Default.CheckCircle else Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when {
                            !hasSentNotificationToOwner -> "الدخول للتطبيق (يتطلب إرسال الإشعار أولاً)"
                            !isPinVerified -> "الدخول للتطبيق (أدخل كود التفعيل المكون من 4 أرقام)"
                            else -> "تأكيد الكود والدخول لخدمات التطبيق ✓"
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Help disclaimer
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ImmersiveSurfaceCard,
                    border = BorderStroke(1.dp, ImmersiveBorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = AccentTeal, modifier = Modifier.size(15.dp))
                        Text(
                            text = "مركز رقيب للبرمجة والتسويق الرقمي • تفعيل فوري لكافة الخدمات البرمجية",
                            fontSize = 11.sp,
                            color = TextSlate,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }
}
