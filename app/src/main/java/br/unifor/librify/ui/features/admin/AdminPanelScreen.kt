package br.unifor.librify.ui.features.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.unifor.librify.domain.model.Publication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(
    onBackClick: () -> Unit,
    viewModel: AdminPanelViewModel = viewModel()
) {
    val pendingPubs by viewModel.pendingPublications.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Painel Administrativo") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Publicações Pendentes",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (pendingPubs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhuma publicação pendente.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(pendingPubs) { pub ->
                        AdminPublicationItem(
                            publication = pub,
                            onApprove = { viewModel.approvePublication(pub.id) },
                            onReject = { viewModel.rejectPublication(pub.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminPublicationItem(
    publication: Publication,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = publication.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = "Por: ${publication.author}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Gênero: ${publication.genre}", style = MaterialTheme.typography.labelSmall)
            }
            
            Row {
                IconButton(onClick = onReject) {
                    Icon(Icons.Default.Close, contentDescription = "Rejeitar", tint = Color.Red)
                }
                IconButton(onClick = onApprove) {
                    Icon(Icons.Default.Check, contentDescription = "Aprovar", tint = Color(0xFF4CAF50))
                }
            }
        }
    }
}
