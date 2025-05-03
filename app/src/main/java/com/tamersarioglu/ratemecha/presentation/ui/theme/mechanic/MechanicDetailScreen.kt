package com.tamersarioglu.ratemecha.presentation.ui.theme.mechanic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tamersarioglu.ratemecha.domain.model.Mechanic
import com.tamersarioglu.ratemecha.domain.model.Review
import com.tamersarioglu.ratemecha.presentation.ui.theme.home.RatingBar
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MechanicDetailScreen(
    mechanicId: String,
    onNavigateBack: () -> Unit,
    onAddReviewClick: () -> Unit,
    onReviewClick: (String) -> Unit,
    viewModel: MechanicDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(mechanicId) {
        viewModel.loadMechanic(mechanicId)
        viewModel.loadReviews(mechanicId)
    }

    val mechanicState by viewModel.mechanicState.collectAsStateWithLifecycle()
    val reviewsState by viewModel.reviewsState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = mechanicState.mechanic?.name ?: "Mechanic Details",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (mechanicState.mechanic != null) {
                FloatingActionButton(onClick = onAddReviewClick) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Review"
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (mechanicState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (mechanicState.errorMessage != null) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = mechanicState.errorMessage ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                mechanicState.mechanic?.let { mechanic ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 88.dp) // space for FAB
                    ) {
                        item {
                            MechanicDetailHeader(
                                mechanic = mechanic,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )

                            Divider(modifier = Modifier.padding(horizontal = 16.dp))

                            Text(
                                text = "Reviews (${mechanic.totalReviews})",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        if (reviewsState.isLoading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        } else if (reviewsState.errorMessage != null) {
                            item {
                                Text(
                                    text = reviewsState.errorMessage ?: "Unknown error",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        } else if (reviewsState.reviews.isEmpty()) {
                            item {
                                Text(
                                    text = "No reviews yet. Be the first to review!",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        } else {
                            items(reviewsState.reviews) { review ->
                                ReviewItem(
                                    review = review,
                                    onClick = { onReviewClick(review.id) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MechanicDetailHeader(
    mechanic: Mechanic,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = mechanic.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBar(
                rating = mechanic.averageRating,
                modifier = Modifier.height(20.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${mechanic.averageRating} (${mechanic.totalReviews} reviews)",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Address",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = mechanic.address,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "${mechanic.city}, ${mechanic.state} ${mechanic.zipCode}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Phone",
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = mechanic.phone,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (!mechanic.specialties.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Specialties",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = mechanic.specialties.joinToString(", "),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ReviewItem(
    review: Review,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = try {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(review.createdAt)
        dateFormat.format(date!!)
    } catch (e: Exception) {
        "Unknown date"
    }

    Card(
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.username,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBar(
                    rating = review.rating.toDouble(),
                    modifier = Modifier.height(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = review.rating.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (review.serviceType != null) {
                Text(
                    text = "Service: ${review.serviceType}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = review.comment,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}