package br.unifor.librify.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.unifor.librify.data.repository.ActivityRepository
import br.unifor.librify.domain.model.Book
import br.unifor.librify.ui.components.BookCard
import br.unifor.librify.ui.theme.BlueHighlight

@Composable
fun HomeScreen(
    onBookClick: (String) -> Unit
) {
    // Using centralized books from Repository
    val allBooks = ActivityRepository.allBooks
    
    val featuredBooks = allBooks.take(4)
    val recommendedBooks = allBooks.drop(4).take(3)
    val monthlyHighlight = allBooks.first()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            GreetingSection(userName = "Usuário")
        }

        item {
            MonthlyHighlightBanner(
                book = monthlyHighlight,
                onDetailsClick = { onBookClick(monthlyHighlight.id) }
            )
        }

        item {
            BookSection(
                title = "Livros em destaque", 
                books = featuredBooks,
                onBookClick = onBookClick
            )
        }

        item {
            BookSection(
                title = "Recomendados para você", 
                books = recommendedBooks,
                onBookClick = onBookClick
            )
        }
    }
}

@Composable
fun GreetingSection(userName: String) {
    Column {
        Text(
            text = "Olá, $userName!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "O que vamos ler hoje?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun MonthlyHighlightBanner(book: Book, onDetailsClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = BlueHighlight),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.2f))
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1.5f)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = book.description,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onDetailsClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = BlueHighlight),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Ver detalhes", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
fun BookSection(title: String, books: List<Book>, onBookClick: (String) -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            items(books) { book ->
                BookCard(book = book, onClick = { onBookClick(book.id) })
            }
        }
    }
}
