package br.unifor.librify.ui.features.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.unifor.librify.ui.components.BookCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onBookClick: (String) -> Unit,
    viewModel: CatalogViewModel = viewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val books by viewModel.filteredBooks.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        // RF07.1: Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Buscar por título ou autor...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // RF07.2: Category Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { viewModel.onCategorySelect(category) },
                    label = { Text(category) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // RF07.3: Vertical Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(books) { book ->
                BookCard(
                    book = book,
                    onClick = { onBookClick(book.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
