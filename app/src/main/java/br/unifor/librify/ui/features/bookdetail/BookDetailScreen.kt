package br.unifor.librify.ui.features.bookdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.unifor.librify.domain.model.Book
import br.unifor.librify.domain.model.Review
import br.unifor.librify.ui.components.LibrifyButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    onBackClick: () -> Unit,
    viewModel: BookDetailViewModel = viewModel()
) {
    val book by viewModel.book.collectAsState()
    val reviews by viewModel.reviews.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Livro") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        book?.let { currentBook ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // RF08: Rendering dynamic properties from Repository
                item {
                    BookHeader(currentBook)
                }

                item {
                    Column {
                        Text(
                            text = "Descrição",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentBook.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        LibrifyButton(
                            text = "Reservar livro", 
                            onClick = { viewModel.onReserveClick() }
                        )
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Avaliar livro")
                        }
                        TextButton(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Adicionar à minha lista")
                        }
                    }
                }

                item {
                    Text(
                        text = "Avaliações e Comentários",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(reviews) { review ->
                    ReviewItem(review)
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun BookHeader(book: Book) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(width = 120.dp, height = 180.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = book.author,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            SuggestionChip(
                onClick = { },
                label = { Text(book.category) }
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB400))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${book.rating} / 5.0",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = review.userName, fontWeight = FontWeight.Bold)
                Text(text = review.date, style = MaterialTheme.typography.labelSmall)
            }
            Row {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (index < review.rating) Color(0xFFFFB400) else Color.LightGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = review.comment, style = MaterialTheme.typography.bodySmall)
        }
    }
}
