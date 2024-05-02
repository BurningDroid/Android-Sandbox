package com.aaron.flippingcard.ui.page

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.aaron.flippingcard.R
import com.aaron.flippingcard.ui.page.model.CardItem


private const val animationDuration = 800

@Composable
fun CardUi(
    card: CardItem,
    accelerometer: Accelerometer,
    onFlip: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val rotated = card.isFlipped
        val flipping by animateFloatAsState(
            targetValue = if (rotated) 180f else 0f,
            animationSpec = tween(animationDuration, easing = LinearOutSlowInEasing)
        )
        val alphaFront by animateFloatAsState(
            targetValue = if (rotated) 0f else 1f,
            animationSpec = tween(animationDuration, easing = LinearOutSlowInEasing)
        )
        val alphaBack by animateFloatAsState(
            targetValue = if (rotated) 1f else 0f,
            animationSpec = tween(animationDuration, easing = LinearOutSlowInEasing)
        )

        val cardShape = RoundedCornerShape(40.dp)
        val modifier = Modifier
            .graphicsLayer {
//                rotationY = rotation
                rotationY = flipping

//                rotationX = accelerometer.x
//                rotationY = accelerometer.y
//                rotationZ = accelerometer.z

                cameraDistance = 8 * density
            }
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = cardShape
            )
            .background(
                color = Color.LightGray.copy(alpha = 0.8F),
                shape = cardShape
            )
            .size(
                width = 335.dp,
                height = 472.dp
            )
            .clip(cardShape)
            .clickable(onClick = onFlip)

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            CardBack(
                card = card,
                alpha = alphaBack,
                rotation = flipping,
            )
            CardFront(
                card = card,
                alpha = alphaFront,
            )
        }
    }
}

@Composable
private fun CardFront(
    card: CardItem,
    alpha: Float,
) {
    val painter = rememberAsyncImagePainter(model = card.image)
    Image(
        painter = painter,
        contentDescription = "profile image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                this.alpha = alpha
                cameraDistance = 8 * density
            },
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun CardBack(
    card: CardItem,
    alpha: Float,
    rotation: Float,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                this.alpha = alpha
                rotationY = rotation * -1
                cameraDistance = 8 * density
            },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberAsyncImagePainter(model = R.drawable.img_thumbnail_3)
            Image(
                painter = painter,
                contentDescription = "profile image",
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, bottom = 24.dp)
                    .size(36.dp)
                    .border(1.dp, Color.White, CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "DJ. Jedi",
                fontSize = 16.sp
            )
        }

        Column(
            modifier = Modifier.weight(1F),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = card.message,
                modifier = Modifier.padding(horizontal = 52.dp),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "2024. 5. 2",
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}
