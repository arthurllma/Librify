package br.unifor.librify.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onChatClick: () -> Unit,
    onAdminClick: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val userName by viewModel.userName.collectAsState()
    val userEmail by viewModel.userEmail.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meu Perfil") },
                actions = {
                    // RF14: Notification Bell
                    IconButton(onClick = { /* Navigate to Notifications */ }) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notificações")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                // RF15: Profile Header
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = userName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(text = userEmail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Profile Actions
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Editar Perfil")
                    }
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Configurações")
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // RF16: Account Settings
                Text(
                    text = "Conta",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item { ProfileMenuItem(icon = Icons.Default.Lock, title = "Alterar senha", onClick = { }) }
            item {
                ProfileMenuItem(
                    icon = Icons.Default.SmartToy, 
                    title = "Chat com Assistente", 
                    onClick = onChatClick
                )
            }
            item {
                ProfileMenuItem(
                    icon = Icons.Default.AdminPanelSettings, 
                    title = "Painel Administrativo", 
                    onClick = onAdminClick
                )
            }
            item {
                ProfileMenuItem(
                    icon = Icons.AutoMirrored.Filled.ExitToApp, 
                    title = "Sair da conta", 
                    textColor = MaterialTheme.colorScheme.error,
                    isBold = true,
                    onClick = { viewModel.logout(onLogout) }
                )
            }
            
            // Extra spacer to ensure the last item is never cut off
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    isBold: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon, 
                contentDescription = null, 
                tint = if (textColor == MaterialTheme.colorScheme.error) textColor else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title, 
                style = MaterialTheme.typography.titleMedium, 
                color = textColor,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, 
                contentDescription = null, 
                tint = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
