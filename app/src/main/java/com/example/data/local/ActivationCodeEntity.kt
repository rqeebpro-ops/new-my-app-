package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activation_codes")
data class ActivationCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val code: String,                     // 4-digit code e.g. "8492", "4591"
    val subscriptionType: String,         // e.g. "شامل لكافة الخدمات", "برمجة وفك شفرات", "محاكي فك القفل وتخطي FRP", "استرداد الملفات والبيانات", "ضبط وتفعيل 3G/4G", "تسويق رقمي وإعلانات", "سنوي VIP", "شهري"
    val clientName: String = "",          // Client name for whom code is issued
    val clientPhone: String = "",         // Client phone number
    val priceAmount: Int = 2000,
    val currency: String = "YER",         // YER, SAR, USD
    val durationLabel: String = "30 يوماً", // e.g. "سنة كاملة", "30 يوماً", "دائم"
    val isUsed: Boolean = false,
    val status: String = "نشط ومتاح",      // "نشط ومتاح", "مستخدم ومفعل", "معلق"
    val usedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val notes: String = ""
)
