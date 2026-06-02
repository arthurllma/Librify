package br.unifor.librify.ui.features.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.unifor.librify.ui.components.LibrifyButton
import br.unifor.librify.ui.components.LibrifyTextField

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Librify",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))
        
        LibrifyTextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            label = "E-mail"
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        LibrifyTextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = "Senha",
            visualTransformation = PasswordVisualTransformation()
        )
        
        AlignEnd {
            TextButton(onClick = onForgotPasswordClick) {
                Text(text = "Esqueci minha senha")
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        LibrifyButton(
            text = "Entrar",
            onClick = {
                viewModel.onLoginClick()
                onLoginSuccess()
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(onClick = onRegisterClick) {
            Text(text = "Não tem uma conta? Cadastre-se")
        }
    }
}

@Composable
private fun AlignEnd(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
        content()
    }
}
