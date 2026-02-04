package com.medical.abginsights.data.model

import java.util.Date

/**
 * Represents ABG (Arterial Blood Gas) measurement data
 */
data class AbgData(
    val ph: Double,
    val pco2: Double,
    val hco3: Double,
    val pao2: Double,
    val be: Double
) {
    fun isValid(): Boolean {
        return ph in 6.8..7.8 &&
                pco2 in 10.0..100.0 &&
                hco3 in 5.0..50.0 &&
                pao2 in 40.0..600.0 &&
                be in -30.0..30.0
    }
}

/**
 * Represents the complete ABG analysis result
 */
data class AbgAnalysis(
    val id: String = "",
    val userId: String = "",
    val abgData: AbgData,
    val interpretation: String = "",
    val suggestedConditions: String = "",
    val treatmentRecommendations: String = "",
    val timestamp: Date = Date(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * Interpretation categories for ABG results
 */
enum class AcidBaseStatus {
    NORMAL,
    METABOLIC_ACIDOSIS,
    METABOLIC_ALKALOSIS,
    RESPIRATORY_ACIDOSIS,
    RESPIRATORY_ALKALOSIS,
    MIXED_DISORDER
}

/**
 * Compensation status
 */
enum class CompensationStatus {
    UNCOMPENSATED,
    PARTIALLY_COMPENSATED,
    FULLY_COMPENSATED
}

/**
 * Oxygenation status
 */
enum class OxygenationStatus {
    NORMAL,
    HYPOXEMIA_MILD,
    HYPOXEMIA_MODERATE,
    HYPOXEMIA_SEVERE
}
