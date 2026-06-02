package br.unifor.librify.ui.features.publish

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
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
import br.unifor.librify.domain.model.PublicationStatus
import br.unifor.librify.ui.components.LibrifyButton
import br.unifor.librify.ui.components.LibrifyTextField

@Composable
fun PublishScreen(
    viewModel: PublishViewModel = viewModel()
) {
    val title by viewModel.title.collectAsState()
    val author by viewModel.author.collectAsState()
    val genre by viewModel.genre.collectAsState()
    val description by viewModel.description.collectAsState()
    val publications by viewModel.myPublications.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // RF12: Title and Introduction
        item {
            Column {
                Text(
                    text = "Publicar Novo Livro",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Preencha as informações para enviar seu livro para avaliação.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // RF12: Submission Form
        item {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                LibrifyTextField(value = title, onValueChange = viewModel::onTitleChange, label = "Título do Livro")
                LibrifyTextField(value = author, onValueChange = viewModel::onAuthorChange, label = "Autor")
                LibrifyTextField(value = genre, onValueChange = viewModel::onGenreChange, label = "Gênero/Categoria")
                LibrifyTextField(
                    value = description, 
                    onValueChange = viewModel::onDescriptionChange, 
                    label = "Descrição/Sinopse",
                    modifier = Modifier.height(120.dp)
                )
            }
        }

        // RF12.1: Upload Area Simulation
        item {
            UploadArea()
        }

        // RF12.2: Submit Button
        item {
            LibrifyButton(
                text = "Enviar para avaliação",
                onClick = { viewModel.submitPublication() }
            )
        }

        // RF13: Status Tracking Section
        item {
            Text(
                text = "Minhas Publicações",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(publications) { pub ->
            PublicationStatusItem(pub)
        }
    }
}

@Composable
fun UploadArea() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        onClick = { /* Simulate file picker */ }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.CloudUpload,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Clique para upload do arquivo (PDF)",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun PublicationStatusItem(publication: Publication) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = publication.title, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(text = "Enviado em: ${publication.date}", style = MaterialTheme.typography.labelSmall)
            }
            
            StatusBadge(status = publication.status)
        }
    }
}

@Composable
fun StatusBadge(status: PublicationStatus) {
    val color = when (status) {
        PublicationStatus.PENDING -> Color(0xFFFFA000) // Orange
        PublicationStatus.APPROVED -> Color(0xFF4CAF50) // Green
        PublicationStatus.REJECTED -> Color(0xFFF44336) // Red
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(width = 1.dp, color = color)
    ) {
        Text(
            text = status.displayName,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}
