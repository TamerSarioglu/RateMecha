package com.tamersarioglu.ratemecha.presentation.ui.theme.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tamersarioglu.ratemecha.domain.model.Mechanic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMechanicClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mechanic Reviews") },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onSearch = viewModel::search,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            FilterChips(
                selectedCity = state.selectedCity,
                selectedState = state.selectedState,
                selectedSpecialty = state.selectedSpecialty,
                onCitySelected = viewModel::onCitySelected,
                onStateSelected = viewModel::onStateSelected,
                onSpecialtySelected = viewModel::onSpecialtySelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (state.mechanics.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (state.searchQuery.isBlank() &&
                                state.selectedCity == null &&
                                state.selectedState == null &&
                                state.selectedSpecialty == null) {
                                "Search for mechanics by name, location, or specialty"
                            } else {
                                "No mechanics found matching your search"
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    MechanicList(
                        mechanics = state.mechanics,
                        onMechanicClick = onMechanicClick
                    )
                }

                if (state.errorMessage != null) {
                    Text(
                        text = state.errorMessage ?: "An error occurred",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search mechanics...") },
        modifier = modifier,
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        }
    )
}

@Composable
fun FilterChips(
    selectedCity: String?,
    selectedState: String?,
    selectedSpecialty: String?,
    onCitySelected: (String?) -> Unit,
    onStateSelected: (String?) -> Unit,
    onSpecialtySelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    // This would be expanded to include scrollable chips
    // Simplified for this example
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Filters:",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Example filter chip implementation would go here
        // Omitted for brevity
    }
}

@Composable
fun MechanicList(
    mechanics: List<Mechanic>,
    onMechanicClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(mechanics) { mechanic ->
            MechanicItem(
                mechanic = mechanic,
                onClick = { onMechanicClick(mechanic.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun MechanicItem(
    mechanic: Mechanic,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = mechanic.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "${mechanic.city}, ${mechanic.state}",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mechanic.averageRating.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(4.dp))

                RatingBar(
                    rating = mechanic.averageRating,
                    modifier = Modifier.height(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "(${mechanic.totalReviews})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (mechanic.specialties.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Specialties: ${mechanic.specialties.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    stars: Int = 5
) {
    Row(modifier = modifier) {
        repeat(stars) { index ->
            val starFill = when {
                index < rating.toInt() -> 1f
                index == rating.toInt() && rating % 1 > 0 -> (rating % 1).toFloat()
                else -> 0f
            }

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (starFill > 0 ) Color(0xFFFFC107) else Color.Gray.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}