package br.unifor.librify.ui.features.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        // Upgrade 2: Branding Icon
        Icon(
            imageVector = Icons.AutoMirrored.Filled.MenuBook,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Librify",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
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
                viewModel.onLoginClick(onLoginSuccess)
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(onClick = onRegisterClick) {
            Text(text = "Não tem uma conta? Cadastre-se")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Upgrade 3: Test Credentials Hint
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.fillAdminCredentials() },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Acesso de Teste (Admin)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "E-mail: admin@librify.com | Senha: Admin123",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "(Toque aqui para preencher automaticamente)",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun AlignEnd(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
        content()
    }
}
