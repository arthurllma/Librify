package br.unifor.librify.ui.features.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.unifor.librify.domain.model.ActivityStatus
import br.unifor.librify.domain.model.ActivityType
import br.unifor.librify.domain.model.Loan

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Atuais", "Histórico")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        val activeActivities by viewModel.getActiveActivities().collectAsState()
        val pastActivities by viewModel.getPastActivities().collectAsState()

        when (selectedTab) {
            0 -> ActivityList(activities = activeActivities, isPast = false)
            1 -> ActivityList(activities = pastActivities, isPast = true)
        }
    }
}

@Composable
fun ActivityList(activities: List<Loan>, isPast: Boolean) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (activities.isEmpty()) {
            item {
                Text(
                    text = if (isPast) "Nenhum histórico encontrado." else "Nenhuma atividade ativa no momento.",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        items(activities) { activity ->
            ActivityItem(activity)
        }
    }
}

@Composable
fun ActivityItem(activity: Loan) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = activity.bookTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                StatusBadge(status = activity.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = if (activity.type == ActivityType.LOAN) "Empréstimo" else "Reserva",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DateInfo(label = "Início", date = activity.startDate)
                DateInfo(label = if (activity.status == ActivityStatus.ACTIVE) "Devolução" else "Fim", date = activity.endDate)
            }
        }
    }
}

@Composable
fun DateInfo(label: String, date: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = date, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun StatusBadge(status: ActivityStatus) {
    val color = when (status) {
        ActivityStatus.ACTIVE -> Color(0xFF4CAF50) // Green
        ActivityStatus.AWAITING_PICKUP -> Color(0xFFFFA000) // Orange
        ActivityStatus.FINISHED -> Color(0xFF2196F3) // Blue
        ActivityStatus.EXPIRED -> Color(0xFFF44336) // Red
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small
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
