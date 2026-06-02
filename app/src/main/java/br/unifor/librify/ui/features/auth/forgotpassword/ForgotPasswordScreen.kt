package br.unifor.librify.ui.features.auth.forgotpassword

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.unifor.librify.ui.components.LibrifyButton
import br.unifor.librify.ui.components.LibrifyTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onSendSuccess: () -> Unit,
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    val email by viewModel.email.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recuperar Senha") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Insira seu e-mail para receber as instruções de recuperação.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            LibrifyTextField(
                value = email,
                onValueChange = viewModel::onEmailChange,
                label = "E-mail"
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            LibrifyButton(
                text = "Enviar",
                onClick = {
                    viewModel.onSendClick()
                    onSendSuccess()
                }
            )
        }
    }
}
