package com.example.model

data class ServicePricing(
    val yer: Int = 0,
    val sar: Int,
    val usd: Int
) {
    fun getAmount(currency: PaymentCurrency): Int = when (currency) {
        PaymentCurrency.YER -> yer
        PaymentCurrency.SAR -> sar
        PaymentCurrency.USD -> usd
    }

    fun getFormatted(currency: PaymentCurrency): String {
        return "${getAmount(currency)} ${currency.code}"
    }

    fun getFormattedWithSymbol(currency: PaymentCurrency): String {
        return "${getAmount(currency)} ${currency.code} (${currency.symbolAr})"
    }

    fun getAllCurrenciesDisplay(): String {
        return "$sar SAR ($usd USD)"
    }
}

enum class PaymentCurrency(val code: String, val symbolAr: String, val label: String) {
    SAR("SAR", "ريال سعودي", "SAR (ريال سعودي)"),
    USD("USD", "دولار أمريكي", "USD (دولار أمريكي)"),
    YER("YER", "ريال يمني", "YER (ريال يمني)");

    companion object {
        // Active currencies requested: USD and SAR only (no Yemeni Riyal)
        val activeCurrencies = listOf(SAR, USD)
    }
}

/**
 * قائمة أنواع وماركات الهواتف المعتمدة للقائمة المنسدلة
 */
object PhoneModelsCatalog {
    val phoneModelsList = listOf(
        "سامسونج (Samsung Galaxy S / Note / Ultra)",
        "سامسونج (Samsung Galaxy A / M Series)",
        "آبل - آيفون (Apple iPhone 11 - 16 Pro Max)",
        "شاومي / ريدمي / بوكو (Xiaomi / Redmi / POCO)",
        "هواوي / هونر (Huawei Mate / P / Honor)",
        "أوبو / رينو (Oppo Reno / Find / A Series)",
        "ريلمي (Realme GT / C / Number Series)",
        "فيفو (Vivo V / Y / X Series)",
        "تكنو (Tecno Spark / Camon / Pova)",
        "إنفينكس (Infinix Hot / Note / Zero)",
        "جوجل بكسل (Google Pixel 6 - 9 Pro)",
        "وان بلس (OnePlus Nord / Flagship)",
        "موتورولا / لينوفو (Motorola / Lenovo)",
        "هاتف ذكي آخر (موديل غير مدرج)"
    )
}

/**
 * خيارات نوع المشكلة والخدمة المصرح بها مع صلاحية 3 ساعات مستمرة
 */
data class ProblemServiceOption(
    val id: String,
    val title: String,
    val shortName: String,
    val description: String,
    val targetSection: ServiceSection,
    val pricing: ServicePricing,
    val iconKey: String = "build"
)

object ProblemServicesCatalog {
    val options = listOf(
        ProblemServiceOption(
            id = "carrier_unlock_apn",
            title = "فك شفرات الشبكة وتفعيل بيانات الجوال لجميع شبكات الوطن العربي",
            shortName = "فك شفرات الشبكة وبيانات 4G",
            description = "فك تشفير بطاقات SIM المقفلة وتفعيل نقاط الوصول 3G/4G/5G لجميع مشغلي الاتصالات في الدول العربية.",
            targetSection = ServiceSection.APN_SETTINGS,
            pricing = ServicePricing(sar = 15, usd = 4),
            iconKey = "cell_tower"
        ),
        ProblemServiceOption(
            id = "lock_bypass_frp",
            title = "فك قفل الشاشة وتجاوز حساب جوجل (FRP / Screen Lock)",
            shortName = "فك قفل الشاشة وحساب FRP",
            description = "إزالة رمز PIN والنمط وكلمة المرور وتخطي قفل حماية المصنع وحساب Google بعد الفورمات.",
            targetSection = ServiceSection.LOCK_RECOVERY_SIMULATOR,
            pricing = ServicePricing(sar = 15, usd = 4),
            iconKey = "lock_open"
        ),
        ProblemServiceOption(
            id = "arabization_rom",
            title = "تعريب الهاتف وتثبيت الرومات الرسمية وضبط النظام",
            shortName = "تعريب الهاتف وتفليش النظام",
            description = "تعريب كامل للأجهزة الأمريكية والكورية والصينية وتثبيت الرومات الرسمية وإصلاح البوتلوب.",
            targetSection = ServiceSection.PHONE_SOLUTIONS,
            pricing = ServicePricing(sar = 15, usd = 4),
            iconKey = "translate"
        ),
        ProblemServiceOption(
            id = "data_recovery",
            title = "استرجاع الصور والملفات والمستندات المحذوفة",
            shortName = "استرداد الملفات والصور",
            description = "فحص عميق للذاكرة الداخلية والذاكرة الخارجية لاستعادة الصور والفيديوهات والملفات والمستندات المحذوفة.",
            targetSection = ServiceSection.DATA_RECOVERY_SCANNER,
            pricing = ServicePricing(sar = 15, usd = 4),
            iconKey = "restore_page"
        ),
        ProblemServiceOption(
            id = "digital_marketing",
            title = "حملات التسويق الرقمي وإعلانات السوشيال ميديا وتكبير الحسابات",
            shortName = "التسويق الرقمي وإعلانات السوشيال",
            description = "إدارة الحملات الإعلانية الممولة فيسبوك، انستغرام، تيك توك وتكبير المتابعين والمبيعات باستهداف خليجي وعربي.",
            targetSection = ServiceSection.MARKETING,
            pricing = ServicePricing(sar = 38, usd = 10),
            iconKey = "campaign"
        ),
        ProblemServiceOption(
            id = "software_repair",
            title = "برمجة وإصلاح أعطال السوفت وير الشاملة والحلول التقنية",
            shortName = "برمجة السوفت وير الشاملة",
            description = "تشخيص وإصلاح كافة أعطال النظام، تهيئة السيريال IMEI، وحلول متقدمة لأعطال الذاكرة والنظام.",
            targetSection = ServiceSection.PHONE_SOLUTIONS,
            pricing = ServicePricing(sar = 15, usd = 4),
            iconKey = "settings_suggest"
        )
    )

    fun findById(id: String): ProblemServiceOption? = options.firstOrNull { it.id == id }

    fun findByTitle(title: String): ProblemServiceOption? = options.firstOrNull {
        it.title == title || it.shortName == title || title.contains(it.shortName) || it.title.contains(title)
    }

    fun findForSection(section: ServiceSection): ProblemServiceOption? = options.firstOrNull {
        it.targetSection == section
    }
}

data class UserProfile(
    val fullName: String = "",
    val phoneNumber: String = "",
    val phoneModel: String = "",
    val problemType: String = "",
    val selectedServiceId: String = "",
    val selectedServiceName: String = "",
    val paymentAccountUsed: String = "",
    val paymentTransactionRef: String = "",
    val currency: PaymentCurrency = PaymentCurrency.SAR,
    val servicePriceAmount: Int = 15,
    val isSubscribed: Boolean = false,
    val subscriptionTimestamp: Long = 0L,
    val subscriptionDurationHours: Int = 3
) {
    val totalDurationMillis: Long
        get() = subscriptionDurationHours * 60 * 60 * 1000L

    fun isSubscriptionActive(currentTime: Long = System.currentTimeMillis()): Boolean {
        if (!isSubscribed || subscriptionTimestamp <= 0L) return false
        val elapsed = currentTime - subscriptionTimestamp
        return elapsed < totalDurationMillis
    }

    fun remainingTimeMillis(currentTime: Long = System.currentTimeMillis()): Long {
        if (!isSubscribed || subscriptionTimestamp <= 0L) return 0L
        val elapsed = currentTime - subscriptionTimestamp
        return (totalDurationMillis - elapsed).coerceAtLeast(0L)
    }

    fun getFormattedRemainingTime(currentTime: Long = System.currentTimeMillis()): String {
        val remaining = remainingTimeMillis(currentTime)
        if (remaining <= 0L) return "00:00:00 (منتهي)"
        val totalSec = remaining / 1000
        val hours = totalSec / 3600
        val minutes = (totalSec % 3600) / 60
        val seconds = totalSec % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun getAllowedProblemOption(): ProblemServiceOption? {
        return ProblemServicesCatalog.findById(selectedServiceId)
            ?: ProblemServicesCatalog.findByTitle(problemType)
            ?: ProblemServicesCatalog.findByTitle(selectedServiceName)
            ?: ProblemServicesCatalog.options.firstOrNull()
    }

    fun isSectionAllowed(section: ServiceSection): Boolean {
        // Universal navigation sections (support, profile, my requests, owner database) are always viewable
        if (section == ServiceSection.LOGIN_SUBSCRIPTION ||
            section == ServiceSection.CONTACT_US ||
            section == ServiceSection.MY_REQUESTS ||
            section == ServiceSection.OWNER_DATABASE
        ) {
            return true
        }

        // If subscription is expired or inactive, services are locked
        if (!isSubscriptionActive()) return false

        val allowedOption = getAllowedProblemOption() ?: return false

        // Check if section matches the allowed option
        if (allowedOption.targetSection == section) return true

        // For carrier unlock & apn: both APN_SETTINGS and carrier tools inside PHONE_SOLUTIONS are permitted
        if (allowedOption.id == "carrier_unlock_apn" && (section == ServiceSection.APN_SETTINGS || section == ServiceSection.PHONE_SOLUTIONS)) {
            return true
        }

        return false
    }
}

data class PaymentChannel(
    val id: String,
    val name: String,
    val accountOrPhone: String,
    val typeLabel: String,
    val badge: String,
    val instruction: String
)
