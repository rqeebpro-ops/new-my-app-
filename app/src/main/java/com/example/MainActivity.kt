package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.ServiceSection
import com.example.ui.MainViewModel
import com.example.ui.components.RaqeebBottomBar
import com.example.ui.components.RaqeebTopBar
import com.example.ui.dialogs.RequestServiceDialog
import com.example.ui.screens.ApnConfigScreen
import com.example.ui.screens.ContactUsScreen
import com.example.ui.screens.DataRecoveryScreen
import com.example.ui.screens.LockSimulatorScreen
import com.example.ui.screens.LoginSubscriptionScreen
import com.example.ui.screens.MarketingScreen
import com.example.ui.screens.MyRequestsScreen
import com.example.ui.screens.OwnerDatabaseScreen
import com.example.ui.screens.PhoneServicesScreen
import com.example.ui.screens.RestrictedServiceScreen
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.ImmersiveBg
import com.example.ui.theme.RaqeebTheme
import com.example.ui.theme.TextWhite
import com.example.util.AppIntentUtils

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RaqeebTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    RaqeebMainApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun RaqeebMainApp(viewModel: MainViewModel) {
    val context = LocalContext.current
    val currentSection by viewModel.currentSection.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedProblemFilter.collectAsStateWithLifecycle()
    val requests by viewModel.userRequests.collectAsStateWithLifecycle()
    val isScanning by viewModel.isScanning.collectAsStateWithLifecycle()
    val scanProgress by viewModel.scanProgress.collectAsStateWithLifecycle()
    val scannedMedia by viewModel.scannedMedia.collectAsStateWithLifecycle()
    val selectedMediaIds by viewModel.selectedMediaIds.collectAsStateWithLifecycle()
    val restoredMediaIds by viewModel.restoredMediaIds.collectAsStateWithLifecycle()
    val isDiagnosing by viewModel.isDiagnosingNetwork.collectAsStateWithLifecycle()
    val diagnosticResult by viewModel.networkDiagnosticResult.collectAsStateWithLifecycle()
    val marketingBudget by viewModel.marketingBudget.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val remainingTimeText by viewModel.remainingTimeText.collectAsStateWithLifecycle()

    // Owner State Flows
    val isOwnerAuthenticated by viewModel.isOwnerAuthenticated.collectAsStateWithLifecycle()
    val ownerSubscriptions by viewModel.allClientSubscriptions.collectAsStateWithLifecycle()
    val subscribersCount by viewModel.subscribersCount.collectAsStateWithLifecycle()
    val revenueYER by viewModel.revenueYER.collectAsStateWithLifecycle()
    val revenueSAR by viewModel.revenueSAR.collectAsStateWithLifecycle()
    val revenueUSD by viewModel.revenueUSD.collectAsStateWithLifecycle()
    val activationCodes by viewModel.allActivationCodes.collectAsStateWithLifecycle()
    val activationCodesCount by viewModel.activationCodesCount.collectAsStateWithLifecycle()
    val usedActivationCodesCount by viewModel.usedActivationCodesCount.collectAsStateWithLifecycle()

    var showQuickRequestDialog by remember { mutableStateOf(false) }

    // If user is not yet subscribed/logged in, show the mandatory Login & Subscription Gate Screen
    // (Owner can bypass this directly into the OWNER_DATABASE screen)
    if (!userProfile.isSubscribed && currentSection != ServiceSection.OWNER_DATABASE) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = ImmersiveBg,
            topBar = {
                RaqeebTopBar(
                    title = "بوابة تسجيل الدخول والاشتراك",
                    onContactWhatsApp = {
                        AppIntentUtils.openWhatsAppToOwner(context, "مرحباً مركز رقيب، أود الاستفسار عن الاشتراك والدخول للتطبيق.")
                    },
                    onOpenProfile = null,
                    onOpenOwnerDatabaseSecret = {
                        viewModel.setSection(ServiceSection.OWNER_DATABASE)
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LoginSubscriptionScreen(
                    userProfile = userProfile,
                    services = viewModel.phoneServices,
                    isGateMode = true,
                    onSaveSubscription = { name, phone, phoneModel, problemType, serviceId, serviceName, currency, priceAmount, paymentAccount, transactionRef ->
                        viewModel.saveLoginAndSubscription(
                            name = name,
                            phone = phone,
                            phoneModel = phoneModel,
                            problemType = problemType,
                            serviceId = serviceId,
                            serviceName = serviceName,
                            currency = currency,
                            priceAmount = priceAmount,
                            paymentAccount = paymentAccount,
                            transactionRef = transactionRef,
                            markAsPaid = true
                        )
                    },
                    onLogout = {
                        viewModel.logoutOrClearSubscription()
                    },
                    onProceedToServices = {
                        val targetSection = userProfile.getAllowedProblemOption()?.targetSection ?: ServiceSection.PHONE_SOLUTIONS
                        viewModel.setSection(targetSection)
                    },
                    onOpenOwnerDatabase = {
                        viewModel.setSection(ServiceSection.OWNER_DATABASE)
                    },
                    onRedeemActivationCode = { pin ->
                        viewModel.markActivationCodeUsed(pin)
                    }
                )
            }
        }
        return
    }

    val sectionTitle = when (currentSection) {
        ServiceSection.PHONE_SOLUTIONS -> "حلول وبرمجة الهاتف"
        ServiceSection.LOCK_RECOVERY_SIMULATOR -> "محاكي فك وتذكير القفل"
        ServiceSection.MARKETING -> "خدمات التسويق الرقمي"
        ServiceSection.DATA_RECOVERY_SCANNER -> "فاحص استرداد البيانات"
        ServiceSection.APN_SETTINGS -> "تفعيل بيانات 3G/4G"
        ServiceSection.LOGIN_SUBSCRIPTION -> "تسجيل الدخول والاشتراك"
        ServiceSection.MY_REQUESTS -> "طلباتي واستشاراتي"
        ServiceSection.CONTACT_US -> "مركز المساعدة والدعم"
        ServiceSection.OWNER_DATABASE -> "قاعدة بيانات المالك"
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ImmersiveBg,
        topBar = {
            RaqeebTopBar(
                title = sectionTitle,
                onContactWhatsApp = {
                    AppIntentUtils.openWhatsAppToOwner(context, "مرحباً مركز رقيب، أود الاستفسار عن خدماتكم.")
                },
                onOpenProfile = {
                    viewModel.setSection(ServiceSection.LOGIN_SUBSCRIPTION)
                },
                onOpenOwnerDatabaseSecret = {
                    viewModel.setSection(ServiceSection.OWNER_DATABASE)
                }
            )
        },
        bottomBar = {
            RaqeebBottomBar(
                currentSection = currentSection,
                userProfile = userProfile,
                onSectionSelected = { viewModel.setSection(it) }
            )
        },
        floatingActionButton = {
            if (currentSection == ServiceSection.PHONE_SOLUTIONS || currentSection == ServiceSection.MARKETING) {
                ExtendedFloatingActionButton(
                    onClick = { showQuickRequestDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Default.Build, contentDescription = null)
                    Text(
                        text = "طلب خدمة",
                        modifier = Modifier.padding(start = 6.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Sticky Active 3-Hour Subscription Status Bar
            if (userProfile.isSubscribed && currentSection != ServiceSection.OWNER_DATABASE) {
                Surface(
                    color = Color(0x1F34D399),
                    border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Icons.Default.VerifiedUser,
                                contentDescription = null,
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = "الخدمة المصرح بها: ${userProfile.getAllowedProblemOption()?.shortName ?: userProfile.problemType}",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = EmeraldContainer,
                            border = BorderStroke(0.5.dp, EmeraldPrimary)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = EmeraldPrimary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = remainingTimeText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldPrimary
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                val isSectionPermitted = viewModel.isSectionAllowed(currentSection)
                if (!isSectionPermitted) {
                    RestrictedServiceScreen(
                        currentSectionTitle = sectionTitle,
                        userProfile = userProfile,
                        remainingTimeText = remainingTimeText,
                        onNavigateToAllowedService = { viewModel.setSection(it) },
                        onNavigateToChangeSubscription = { viewModel.setSection(ServiceSection.LOGIN_SUBSCRIPTION) }
                    )
                } else {
                    when (currentSection) {
                        ServiceSection.PHONE_SOLUTIONS -> {
                            PhoneServicesScreen(
                                services = viewModel.phoneServices,
                                selectedFilter = selectedFilter,
                                onFilterChange = { viewModel.setProblemFilter(it) },
                                onNavigateSection = { viewModel.setSection(it) },
                                onSubmitRequest = { title, category, subCategory, device, phone, notes ->
                                    viewModel.submitServiceRequest(title, category, subCategory, device, phone, notes) {}
                                }
                            )
                        }

                        ServiceSection.LOCK_RECOVERY_SIMULATOR -> {
                            LockSimulatorScreen(
                                onBackToPhoneServices = { viewModel.setSection(ServiceSection.PHONE_SOLUTIONS) }
                            )
                        }

                        ServiceSection.MARKETING -> {
                            MarketingScreen(
                                packages = viewModel.marketingPackages,
                                marketingBudget = marketingBudget,
                                onBudgetChange = { viewModel.setMarketingBudget(it) },
                                onSubmitRequest = { title, category, subCategory, device, phone, notes ->
                                    viewModel.submitServiceRequest(title, category, subCategory, device, phone, notes) {}
                                }
                            )
                        }

                        ServiceSection.DATA_RECOVERY_SCANNER -> {
                            DataRecoveryScreen(
                                isScanning = isScanning,
                                scanProgress = scanProgress,
                                scannedMedia = scannedMedia,
                                selectedIds = selectedMediaIds,
                                restoredIds = restoredMediaIds,
                                onStartScan = { viewModel.startMediaScan() },
                                onToggleSelect = { viewModel.toggleMediaSelection(it) },
                                onRestoreSelected = { viewModel.restoreSelectedMedia() },
                                onSubmitLabRequest = { title, category, subCategory, device, phone, notes ->
                                    viewModel.submitServiceRequest(title, category, subCategory, device, phone, notes) {}
                                }
                            )
                        }

                        ServiceSection.APN_SETTINGS -> {
                            ApnConfigScreen(
                                profiles = viewModel.apnProfiles,
                                isDiagnosing = isDiagnosing,
                                diagnosticResult = diagnosticResult,
                                onRunDiagnostic = { viewModel.runNetworkDiagnostic(it) }
                            )
                        }

                        ServiceSection.LOGIN_SUBSCRIPTION -> {
                            LoginSubscriptionScreen(
                                userProfile = userProfile,
                                services = viewModel.phoneServices,
                                isGateMode = false,
                                onSaveSubscription = { name, phone, phoneModel, problemType, serviceId, serviceName, currency, priceAmount, paymentAccount, transactionRef ->
                                    viewModel.saveLoginAndSubscription(
                                        name = name,
                                        phone = phone,
                                        phoneModel = phoneModel,
                                        problemType = problemType,
                                        serviceId = serviceId,
                                        serviceName = serviceName,
                                        currency = currency,
                                        priceAmount = priceAmount,
                                        paymentAccount = paymentAccount,
                                        transactionRef = transactionRef,
                                        markAsPaid = true
                                    )
                                },
                                onLogout = {
                                    viewModel.logoutOrClearSubscription()
                                },
                                onProceedToServices = {
                                    val targetSection = userProfile.getAllowedProblemOption()?.targetSection ?: ServiceSection.PHONE_SOLUTIONS
                                    viewModel.setSection(targetSection)
                                },
                                onRedeemActivationCode = { pin ->
                                    viewModel.markActivationCodeUsed(pin)
                                }
                            )
                        }

                        ServiceSection.MY_REQUESTS -> {
                            MyRequestsScreen(
                                requests = requests,
                                onDeleteRequest = { viewModel.deleteRequest(it) }
                            )
                        }

                        ServiceSection.CONTACT_US -> {
                            ContactUsScreen()
                        }

                        ServiceSection.OWNER_DATABASE -> {
                            OwnerDatabaseScreen(
                                isAuthenticated = isOwnerAuthenticated,
                                onAuthenticate = { phone, pass -> viewModel.authenticateOwner(phone, pass) },
                                subscriptions = ownerSubscriptions,
                                subscribersCount = subscribersCount,
                                revenueYER = revenueYER,
                                revenueSAR = revenueSAR,
                                revenueUSD = revenueUSD,
                                onUpdateStatus = { id, status, approved ->
                                    viewModel.updateClientSubscriptionStatus(id, status, approved)
                                },
                                onUpdateNotes = { id, notes ->
                                    viewModel.updateClientSubscriptionNotes(id, notes)
                                },
                                onDeleteClient = { id ->
                                    viewModel.deleteClientSubscription(id)
                                },
                                onAddManualClient = { name, phone, model, problem, service, currency, amount, payment, ref, status, notes ->
                                    viewModel.addManualClientSubscription(name, phone, model, problem, service, currency, amount, payment, ref, status, notes)
                                },
                                onLockDashboard = {
                                    viewModel.setOwnerModeDirect(false)
                                },
                                activationCodes = activationCodes,
                                activationCodesCount = activationCodesCount,
                                usedActivationCodesCount = usedActivationCodesCount,
                                onGenerateCode = { subType, clientName, clientPhone, priceAmt, currency, duration, customPin, notes ->
                                    viewModel.generateActivationCode(subType, clientName, clientPhone, priceAmt, currency, duration, customPin, notes)
                                },
                                onDeleteCode = { id ->
                                    viewModel.deleteActivationCode(id)
                                },
                                onUpdateCodeStatus = { id, status ->
                                    viewModel.updateActivationCodeStatus(id, status)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showQuickRequestDialog) {
        val initialCategory = if (currentSection == ServiceSection.MARKETING) "تسويق إلكتروني" else "صيانة وبرمجة هواتف"
        val initialSubCategory = if (currentSection == ServiceSection.MARKETING) "حملة إعلانية ممولة" else "فك شفرة أو إقفال هاتف"

        RequestServiceDialog(
            initialCategory = initialCategory,
            initialSubCategory = initialSubCategory,
            onDismiss = { showQuickRequestDialog = false },
            onSubmit = { title, category, subCategory, device, phone, notes ->
                viewModel.submitServiceRequest(title, category, subCategory, device, phone, notes) {}
            }
        )
    }
}

// Greeting kept for test compatibility
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}
