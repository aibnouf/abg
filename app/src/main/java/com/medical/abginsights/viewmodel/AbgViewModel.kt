package com.medical.abginsights.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medical.abginsights.ai.GeminiService
import com.medical.abginsights.data.model.AbgAnalysis
import com.medical.abginsights.data.model.AbgData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class AbgViewModel : ViewModel() {
    
    private val geminiService = GeminiService()
    
    private val _currentAnalysis = MutableStateFlow<AbgAnalysis?>(null)
    val currentAnalysis: StateFlow<AbgAnalysis?> = _currentAnalysis.asStateFlow()
    
    private val _analysisHistory = MutableStateFlow<List<AbgAnalysis>>(emptyList())
    val analysisHistory: StateFlow<List<AbgAnalysis>> = _analysisHistory.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    /**
     * Analyzes ABG data and generates comprehensive interpretation
     */
    fun analyzeAbg(abgData: AbgData) {
        if (!abgData.isValid()) {
            _error.value = "Invalid ABG values. Please check your inputs."
            return
        }
        
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                // Create initial analysis object
                val analysis = AbgAnalysis(
                    id = UUID.randomUUID().toString(),
                    userId = "", // Will be set when user authentication is implemented
                    abgData = abgData,
                    timestamp = Date(),
                    isLoading = true
                )
                _currentAnalysis.value = analysis
                
                // Perform full analysis using Gemini AI
                val (interpretation, conditions, treatment) = geminiService.performFullAnalysis(abgData)
                
                // Update with results
                val completedAnalysis = analysis.copy(
                    interpretation = interpretation,
                    suggestedConditions = conditions,
                    treatmentRecommendations = treatment,
                    isLoading = false
                )
                
                _currentAnalysis.value = completedAnalysis
                
                // Add to history
                _analysisHistory.value = listOf(completedAnalysis) + _analysisHistory.value
                
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred during analysis"
                _currentAnalysis.value = _currentAnalysis.value?.copy(
                    isLoading = false,
                    error = e.message
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Clears the current analysis
     */
    fun clearCurrentAnalysis() {
        _currentAnalysis.value = null
        _error.value = null
    }
    
    /**
     * Clears error message
     */
    fun clearError() {
        _error.value = null
    }
    
    /**
     * Loads a previous analysis from history
     */
    fun loadAnalysis(analysisId: String) {
        val analysis = _analysisHistory.value.find { it.id == analysisId }
        _currentAnalysis.value = analysis
    }
    
    /**
     * Deletes an analysis from history
     */
    fun deleteAnalysis(analysisId: String) {
        _analysisHistory.value = _analysisHistory.value.filter { it.id != analysisId }
        if (_currentAnalysis.value?.id == analysisId) {
            _currentAnalysis.value = null
        }
    }
    
    /**
     * Clears all analysis history
     */
    fun clearHistory() {
        _analysisHistory.value = emptyList()
    }
}

