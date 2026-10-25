package com.example

import com.example.model.PhoneProblemType
import com.example.model.PhoneModelsCatalog
import com.example.model.ProblemServicesCatalog
import com.example.model.ServiceSection
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testProblemServicesCatalogProvidesAllProblemTypes() {
    val options = ProblemServicesCatalog.options
    assertTrue(options.isNotEmpty())
    assertTrue(options.any { it.targetSection == ServiceSection.LOCK_RECOVERY_SIMULATOR })
    assertTrue(options.any { it.targetSection == ServiceSection.APN_SETTINGS })
    assertTrue(options.any { it.targetSection == ServiceSection.DATA_RECOVERY_SCANNER })
    assertTrue(options.any { it.targetSection == ServiceSection.PHONE_SOLUTIONS })
    assertTrue(options.any { it.targetSection == ServiceSection.MARKETING })
  }

  @Test
  fun testPhoneModelsCatalog() {
    val models = PhoneModelsCatalog.phoneModelsList
    assertTrue(models.isNotEmpty())
    assertTrue(models.any { it.contains("Samsung") })
    assertTrue(models.any { it.contains("Xiaomi") })
  }

  @Test
  fun testServiceSectionsContainLockSimulator() {
    val sections = ServiceSection.values()
    assertTrue(sections.contains(ServiceSection.LOCK_RECOVERY_SIMULATOR))
    assertTrue(sections.contains(ServiceSection.PHONE_SOLUTIONS))
    assertTrue(sections.contains(ServiceSection.MARKETING))
  }
}
