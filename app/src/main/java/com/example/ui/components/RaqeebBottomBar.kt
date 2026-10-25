package com.example.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.RestorePage
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ServiceSection
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.ImmersiveBg
import com.example.ui.theme.ImmersiveBorder
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSlate

@Composable
fun RaqeebBottomBar(
    currentSection: ServiceSection,
    userProfile: com.example.model.UserProfile? = null,
    onSectionSelected: (ServiceSection) -> Unit
) {
    NavigationBar(
        containerColor = ImmersiveBg,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = ImmersiveBorder,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
    ) {
        val navItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = EmeraldPrimary,
            selectedTextColor = EmeraldPrimary,
            indicatorColor = EmeraldContainer,
            unselectedIconColor = TextMuted,
            unselectedTextColor = TextMuted
        )

        fun isAllowed(sec: ServiceSection): Boolean {
            return userProfile == null || userProfile.isSectionAllowed(sec)
        }

        val isPhoneSection = currentSection == ServiceSection.PHONE_SOLUTIONS || currentSection == ServiceSection.LOCK_RECOVERY_SIMULATOR
        val phoneAllowed = isAllowed(ServiceSection.PHONE_SOLUTIONS)
        NavigationBarItem(
            selected = isPhoneSection,
            onClick = { onSectionSelected(ServiceSection.PHONE_SOLUTIONS) },
            icon = {
                Icon(
                    imageVector = if (!phoneAllowed) Icons.Default.Lock else Icons.Default.PhoneAndroid,
                    contentDescription = "برمجة الهواتف",
                    tint = if (!phoneAllowed) Color(0xFFF59E0B) else if (isPhoneSection) EmeraldPrimary else TextMuted
                )
            },
            label = {
                Text(
                    text = if (!phoneAllowed) "البرمجة 🔒" else "البرمجة",
                    fontSize = 10.5.sp,
                    fontWeight = if (isPhoneSection) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = navItemColors
        )

        val mktAllowed = isAllowed(ServiceSection.MARKETING)
        NavigationBarItem(
            selected = currentSection == ServiceSection.MARKETING,
            onClick = { onSectionSelected(ServiceSection.MARKETING) },
            icon = {
                Icon(
                    imageVector = if (!mktAllowed) Icons.Default.Lock else Icons.Default.Campaign,
                    contentDescription = "التسويق",
                    tint = if (!mktAllowed) Color(0xFFF59E0B) else if (currentSection == ServiceSection.MARKETING) EmeraldPrimary else TextMuted
                )
            },
            label = {
                Text(
                    text = if (!mktAllowed) "التسويق 🔒" else "التسويق",
                    fontSize = 10.5.sp,
                    fontWeight = if (currentSection == ServiceSection.MARKETING) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = navItemColors
        )

        val recAllowed = isAllowed(ServiceSection.DATA_RECOVERY_SCANNER)
        NavigationBarItem(
            selected = currentSection == ServiceSection.DATA_RECOVERY_SCANNER,
            onClick = { onSectionSelected(ServiceSection.DATA_RECOVERY_SCANNER) },
            icon = {
                Icon(
                    imageVector = if (!recAllowed) Icons.Default.Lock else Icons.Default.RestorePage,
                    contentDescription = "استرداد البيانات",
                    tint = if (!recAllowed) Color(0xFFF59E0B) else if (currentSection == ServiceSection.DATA_RECOVERY_SCANNER) EmeraldPrimary else TextMuted
                )
            },
            label = {
                Text(
                    text = if (!recAllowed) "الاسترداد 🔒" else "الاسترداد",
                    fontSize = 10.5.sp,
                    fontWeight = if (currentSection == ServiceSection.DATA_RECOVERY_SCANNER) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = navItemColors
        )

        val apnAllowed = isAllowed(ServiceSection.APN_SETTINGS)
        NavigationBarItem(
            selected = currentSection == ServiceSection.APN_SETTINGS,
            onClick = { onSectionSelected(ServiceSection.APN_SETTINGS) },
            icon = {
                Icon(
                    imageVector = if (!apnAllowed) Icons.Default.Lock else Icons.Default.CellTower,
                    contentDescription = "تفعيل 4G",
                    tint = if (!apnAllowed) Color(0xFFF59E0B) else if (currentSection == ServiceSection.APN_SETTINGS) EmeraldPrimary else TextMuted
                )
            },
            label = {
                Text(
                    text = if (!apnAllowed) "بيانات 4G 🔒" else "بيانات 4G",
                    fontSize = 10.5.sp,
                    fontWeight = if (currentSection == ServiceSection.APN_SETTINGS) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = navItemColors
        )

        NavigationBarItem(
            selected = currentSection == ServiceSection.MY_REQUESTS,
            onClick = { onSectionSelected(ServiceSection.MY_REQUESTS) },
            icon = { Icon(Icons.Default.Assignment, contentDescription = "طلباتي") },
            label = { Text("طلباتي", fontSize = 11.sp, fontWeight = if (currentSection == ServiceSection.MY_REQUESTS) FontWeight.Bold else FontWeight.Normal) },
            colors = navItemColors
        )

        NavigationBarItem(
            selected = currentSection == ServiceSection.LOGIN_SUBSCRIPTION,
            onClick = { onSectionSelected(ServiceSection.LOGIN_SUBSCRIPTION) },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "تسجيل واشتراك") },
            label = { Text("الاشتراك", fontSize = 11.sp, fontWeight = if (currentSection == ServiceSection.LOGIN_SUBSCRIPTION) FontWeight.Bold else FontWeight.Normal) },
            colors = navItemColors
        )

        NavigationBarItem(
            selected = currentSection == ServiceSection.CONTACT_US,
            onClick = { onSectionSelected(ServiceSection.CONTACT_US) },
            icon = { Icon(Icons.Default.SupportAgent, contentDescription = "تواصل") },
            label = { Text("تواصل", fontSize = 11.sp, fontWeight = if (currentSection == ServiceSection.CONTACT_US) FontWeight.Bold else FontWeight.Normal) },
            colors = navItemColors
        )
    }
}

