package com.medical.abginsights.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.medical.abginsights.data.model.AbgData
import com.medical.abginsights.viewmodel.AbgViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    viewModel: AbgViewModel,
    onNavigateBack: () -> Unit,
    onAnalysisComplete: () -> Unit
) {
    var ph by remember { mutableStateOf("7.40") }
    var pco2 by remember { mutableStateOf("40.0") }
    var hco3 by remember { mutableStateOf("24.0") }
    var pao2 by remember { mutableStateOf("95.0") }
    var be by remember { mutableStateOf("0.0") }
    
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    // Navigate to results when analysis completes
    LaunchedEffect(viewModel.currentAnalysis.collectAsState().value) {
        if (viewModel.currentAnalysis.value != null && !isLoading) {
            onAnalysisComplete()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ABG Analysis") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Enter ABG Values",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            
            // pH Input
            OutlinedTextField(
                value = ph,
                onValueChange = { ph = it },
                label = { Text("pH") },
                supportingText = { Text("Normal: 7.35 - 7.45") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // pCO2 Input
            OutlinedTextField(
                value = pco2,
                onValueChange = { pco2 = it },
                label = { Text("pCO₂ (mmHg)") },
                supportingText = { Text("Normal: 35 - 45 mmHg") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // HCO3 Input
            OutlinedTextField(
                value = hco3,
                onValueChange = { hco3 = it },
                label = { Text("HCO₃ (mEq/L)") },
                supportingText = { Text("Normal: 22 - 26 mEq/L") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // PaO2 Input
            OutlinedTextField(
                value = pao2,
                onValueChange = { pao2 = it },
                label = { Text("PaO₂ (mmHg)") },
                supportingText = { Text("Normal: 80 - 100 mmHg") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // BE Input
            OutlinedTextField(
                value = be,
                onValueChange = { be = it },
                label = { Text("Base Excess (mEq/L)") },
                supportingText = { Text("Normal: -2 to +2 mEq/L") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Error Message
            error?.let { errorMessage ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            // Analyze Button
            Button(
                onClick = {
                    viewModel.clearError()
                    try {
                        val abgData = AbgData(
                            ph = ph.toDouble(),
                            pco2 = pco2.toDouble(),
                            hco3 = hco3.toDouble(),
                            pao2 = pao2.toDouble(),
                            be = be.toDouble()
                        )
                        viewModel.analyzeAbg(abgData)
                    } catch (e: NumberFormatException) {
                        viewModel.clearError()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Analyzing...")
                } else {
                    Text("Analyze ABG")
                }
            }
            
            // Quick Reference Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Normal ABG Values Reference",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = """
                            • pH: 7.35 - 7.45
                            • pCO₂: 35 - 45 mmHg
                            • HCO₃: 22 - 26 mEq/L
                            • PaO₂: 80 - 100 mmHg
                            • BE: -2 to +2 mEq/L
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
