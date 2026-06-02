package br.unifor.librify.ui.features.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastro") },
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
            
            LibrifyTextField(
                value = name,
                onValueChange = viewModel::onNameChange,
                label = "Nome Completo"
            )
            Spacer(modifier = Modifier.height(16.dp))
            
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
            
            Spacer(modifier = Modifier.height(32.dp))
            
            LibrifyButton(
                text = "Cadastrar",
                onClick = {
                    viewModel.onRegisterClick()
                    onRegisterSuccess()
                }
            )
        }
    }
}
