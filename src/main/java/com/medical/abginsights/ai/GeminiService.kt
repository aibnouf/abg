package com.medical.abginsights.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.medical.abginsights.data.model.AbgData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Service class for Google Gemini AI integration
 * Uses FREE Google AI SDK - no costs involved
 * 
 * IMPORTANT: Replace "YOUR_API_KEY_HERE" with your actual Gemini API key
 * Get free API key from: https://makersuite.google.com/app/apikey
 */
class GeminiService {
    
    companion object {
        // TODO: Replace with your actual API key or load from BuildConfig/local.properties
        private const val API_KEY = "YOUR_API_KEY_HERE"
        private const val MODEL_NAME = "gemini-1.5-flash"
    }
    
    private val model = GenerativeModel(
        modelName = MODEL_NAME,
        apiKey = API_KEY
    )
    
    /**
     * Generates comprehensive ABG interpretation using Gemini AI
     */
    suspend fun interpretAbg(abgData: AbgData): String = withContext(Dispatchers.IO) {
        val prompt = """
            You are an expert medical AI assistant specializing in Arterial Blood Gas (ABG) interpretation.
            
            Analyze the following ABG values:
            - pH: ${abgData.ph}
            - pCO2: ${abgData.pco2} mmHg
            - HCO3: ${abgData.hco3} mEq/L
            - PaO2: ${abgData.pao2} mmHg
            - BE: ${abgData.be} mEq/L
            
            Please provide a detailed interpretation including:
            1. Primary acid-base disorder (acidosis/alkalosis, metabolic/respiratory)
            2. Compensation status (uncompensated, partially compensated, fully compensated)
            3. Oxygenation status
            4. Clinical significance
            
            Format your response clearly with bullet points and be concise but thorough.
        """.trimIndent()
        
        try {
            val response = model.generateContent(prompt)
            response.text ?: "Unable to generate interpretation. Please try again."
        } catch (e: Exception) {
            throw Exception("Failed to interpret ABG: ${e.message}")
        }
    }
    
    /**
     * Suggests possible underlying conditions based on ABG interpretation
     */
    suspend fun suggestConditions(abgData: AbgData, interpretation: String): String = 
        withContext(Dispatchers.IO) {
            val prompt = """
                Based on the following ABG values and interpretation, suggest possible underlying medical conditions:
                
                ABG Values:
                - pH: ${abgData.ph}
                - pCO2: ${abgData.pco2} mmHg
                - HCO3: ${abgData.hco3} mEq/L
                - PaO2: ${abgData.pao2} mmHg
                - BE: ${abgData.be} mEq/L
                
                Interpretation: $interpretation
                
                List 3-5 most likely underlying conditions that could cause this ABG pattern.
                For each condition, provide:
                - Condition name
                - Brief explanation of why this condition matches the ABG pattern
                - Key clinical features to look for
                
                Be specific and clinically relevant. This is for medical professionals.
            """.trimIndent()
            
            try {
                val response = model.generateContent(prompt)
                response.text ?: "Unable to generate condition suggestions. Please try again."
            } catch (e: Exception) {
                throw Exception("Failed to suggest conditions: ${e.message}")
            }
        }
    
    /**
     * Provides treatment recommendations based on ABG analysis
     */
    suspend fun recommendTreatments(
        abgData: AbgData, 
        interpretation: String,
        conditions: String
    ): String = withContext(Dispatchers.IO) {
        val prompt = """
            Based on the following ABG analysis, provide treatment recommendations:
            
            ABG Values:
            - pH: ${abgData.ph}
            - pCO2: ${abgData.pco2} mmHg
            - HCO3: ${abgData.hco3} mEq/L
            - PaO2: ${abgData.pao2} mmHg
            - BE: ${abgData.be} mEq/L
            
            Interpretation: $interpretation
            
            Suggested Conditions: $conditions
            
            Provide initial treatment recommendations including:
            1. Immediate interventions needed
            2. Supportive care measures
            3. Monitoring parameters
            4. When to escalate care
            
            Important: Emphasize that these are suggestions to assist clinical decision-making, 
            not definitive treatment plans. The treating physician must consider the full clinical context.
            
            Be specific, actionable, and prioritize patient safety.
        """.trimIndent()
        
        try {
            val response = model.generateContent(prompt)
            response.text ?: "Unable to generate treatment recommendations. Please try again."
        } catch (e: Exception) {
            throw Exception("Failed to recommend treatments: ${e.message}")
        }
    }
    
    /**
     * Performs complete ABG analysis in a single call for efficiency
     */
    suspend fun performFullAnalysis(abgData: AbgData): Triple<String, String, String> = 
        withContext(Dispatchers.IO) {
            val prompt = """
                You are an expert medical AI assistant specializing in Arterial Blood Gas (ABG) interpretation.
                
                Analyze the following ABG values comprehensively:
                - pH: ${abgData.ph}
                - pCO2: ${abgData.pco2} mmHg
                - HCO3: ${abgData.hco3} mEq/L
                - PaO2: ${abgData.pao2} mmHg
                - BE: ${abgData.be} mEq/L
                
                Provide three sections in your response:
                
                ## INTERPRETATION
                Provide detailed interpretation including:
                - Primary acid-base disorder
                - Compensation status
                - Oxygenation status
                - Clinical significance
                
                ## SUGGESTED CONDITIONS
                List 3-5 most likely underlying conditions with brief explanations
                
                ## TREATMENT RECOMMENDATIONS
                Provide initial treatment recommendations including:
                - Immediate interventions
                - Supportive care
                - Monitoring parameters
                - When to escalate care
                
                IMPORTANT DISCLAIMER: These suggestions assist clinical decision-making but are not 
                definitive treatment plans. The treating physician must consider the full clinical context.
                
                Format with clear section headers and bullet points.
            """.trimIndent()
            
            try {
                val response = model.generateContent(prompt)
                val fullText = response.text ?: throw Exception("No response generated")
                
                // Parse the response into three sections
                val sections = parseFullAnalysis(fullText)
                sections
            } catch (e: Exception) {
                throw Exception("Failed to perform full analysis: ${e.message}")
            }
        }
    
    /**
     * Parses the full analysis response into three sections
     */
    private fun parseFullAnalysis(text: String): Triple<String, String, String> {
        val interpretationRegex = "##\\s*INTERPRETATION\\s*(.+?)(?=##|$)".toRegex(RegexOption.DOT_MATCHES_ALL)
        val conditionsRegex = "##\\s*SUGGESTED CONDITIONS\\s*(.+?)(?=##|$)".toRegex(RegexOption.DOT_MATCHES_ALL)
        val treatmentRegex = "##\\s*TREATMENT RECOMMENDATIONS\\s*(.+?)(?=##|$)".toRegex(RegexOption.DOT_MATCHES_ALL)
        
        val interpretation = interpretationRegex.find(text)?.groupValues?.get(1)?.trim() 
            ?: "Interpretation not available"
        val conditions = conditionsRegex.find(text)?.groupValues?.get(1)?.trim() 
            ?: "Condition suggestions not available"
        val treatment = treatmentRegex.find(text)?.groupValues?.get(1)?.trim() 
            ?: "Treatment recommendations not available"
        
        return Triple(interpretation, conditions, treatment)
    }
}
