package br.com.teste.controledevendas.design.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.teste.controledevendas.design.theme.Green200
import br.com.teste.controledevendas.design.theme.MarginPaddingSizeMedium
import br.com.teste.controledevendas.design.theme.MarginPaddingSizeSmall

@Composable
fun ExpandableItem(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color,
    content: @Composable () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var expandIcon by remember {
        mutableStateOf(Icons.Default.ExpandMore)
    }

    expandIcon = if (expanded) {
        Icons.Default.ExpandLess
    } else {
        Icons.Default.ExpandMore
    }

    Column(
        modifier = modifier
            .clickable {
                expanded = !expanded
            }
            .graphicsLayer {
                shape = RoundedCornerShape(8.dp)
                clip = true
            }
            .background(color = backgroundColor)
            .padding(
                horizontal = MarginPaddingSizeSmall,
                vertical = MarginPaddingSizeSmall
            )
    ) {
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = title
            )

            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically),
                imageVector = expandIcon,
                contentDescription = null
            )
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier.padding(top = MarginPaddingSizeMedium)
            ) {
                content()
            }
        }
    }
}

@Composable
@Preview
fun ExpandableItemPreview() {
    ExpandableItem(
        title = "Title", 
        backgroundColor = Green200,
    ) {
        Text(text = "Content")
    }
}