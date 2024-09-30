package com.aaron.listre_order

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListPane(
    vm: MainViewModel
) {
    val lazyListState = rememberLazyListState()
    val reorderableLazyColumnState = rememberReorderableLazyListState(lazyListState) { from, to ->
        vm.onMove(from.index, to.index)
    }

    Scaffold(
        modifier = Modifier
            .safeDrawingPadding(),
        topBar = {
            TextButton(onClick = vm::onClickDone) {
                Text(text = "Done")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = lazyListState,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = vm.items,
                key = { _, item -> item.id }
            ) { _, item ->
                ReorderableItem(reorderableLazyColumnState, key = item.id) {
                    ItemUi(
                        item = item,
                        modifier = Modifier.draggableHandle()
                    )
                }
            }
        }
    }
}

@Composable
fun ItemUi(
    item: Item,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, shape)
            .background(MaterialTheme.colorScheme.secondaryContainer, shape)
            .padding(
                horizontal = 20.dp,
                vertical = 12.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = item.id.toString(),
                color = MaterialTheme.colorScheme.onSurface.copy(0.5F),
                style = MaterialTheme.typography.labelMedium,
            )

            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "",
            modifier = modifier
        )
    }
}
