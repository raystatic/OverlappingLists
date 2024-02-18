package com.example.overlappinglist

import android.os.Bundle
import android.widget.ScrollView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.overlappinglist.databinding.ActivityMainBinding
import com.example.overlappinglist.ui.theme.OverlappingListTheme
import com.example.overlappingrecyclerview.DogsAdapter
import com.example.overlappingrecyclerview.OverlappingDecoration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OverlappingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }

    companion object {
        val RESOURCES = listOf(
            R.drawable.img,
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6,
            R.drawable.img_7,
            R.drawable.img_8,
            R.drawable.img_9,
            R.drawable.img_10,
            R.drawable.img_11,
            R.drawable.img_12
        )
    }

}

@Composable
fun Home(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Recycler View Overlapping Items")
            Spacer(modifier = modifier.height(16.dp))
            RecyclerviewOverlapping()
        }
        Spacer(modifier = modifier
            .height(10.dp))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Compose Overlapping Items")
            Spacer(modifier = modifier.height(16.dp))
            ComposeOverlapping()
        }
    }
}

@Composable
fun RecyclerviewOverlapping() {
    val dogsAdapter = DogsAdapter()
    AndroidViewBinding(factory = ActivityMainBinding::inflate) {
        rvDogs.apply {
            layoutManager = LinearLayoutManager(this@AndroidViewBinding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = dogsAdapter
            addItemDecoration(OverlappingDecoration())
        }
    }
    dogsAdapter.submitData(MainActivity.RESOURCES)
}

@Composable
fun ComposeOverlapping(
    modifier: Modifier = Modifier
) {


    LazyRow {
        item {
            Spacer(modifier = modifier.width(16.dp))
        }

        item {
            OverlappingRow(overlappingPercentage = 0.20f) {
                for (i in MainActivity.RESOURCES) {
                    Image(
                        painter = painterResource(id = i),
                        contentDescription = "image_$i",
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color(0xFFFFA0A0), CircleShape)
                    )
                }
            }
        }

        item {
            Spacer(modifier = modifier.width(16.dp))
        }
    }
}

@Composable
fun OverlappingRow(
    modifier: Modifier = Modifier,
    overlappingPercentage: Float,
    content: @Composable () -> Unit
) {

    val factor = (1 - overlappingPercentage)

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            val height = placeables.maxOf { it.height }
            val width = (placeables.subList(1, placeables.size).sumOf { it.width } * factor + (placeables.getOrNull(0)?.width ?: 0)).toInt()
            layout(width, height) {
                var x = 0
                for (placeable in placeables) {
                    placeable.placeRelative(x, 0, 0f)
                    x += (placeable.width * factor).toInt()
                }
            }
        }
    )
    
}

@Preview
@Composable
fun HomePreview() {
    Home()
}