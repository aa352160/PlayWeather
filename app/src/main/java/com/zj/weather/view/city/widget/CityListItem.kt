package com.zj.weather.view.city.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zj.weather.R
import com.zj.model.room.entity.CityInfo
import com.zj.utils.swipe.SwipeDeleteLayout
import com.zj.utils.view.ImageLoader
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CityListItem(
    cityInfo: CityInfo,
    isShowDelete: Boolean = true,
    toWeatherDetails: (CityInfo) -> Unit,
    onDeleteListener: (CityInfo) -> Unit
) {
    val swipeState = rememberSwipeableState(0)

    val coroutineScope = rememberCoroutineScope()
    SwipeDeleteLayout(swipeState = swipeState, isShowChild = isShowDelete, childContent = {
        Column(modifier = Modifier.fillMaxHeight()) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(0.dp, 5.dp, 5.dp, 0.dp),
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(color = Color.Red)
                        .clickable {
                            onDeleteListener(cityInfo)
                            coroutineScope.launch {
                                swipeState.animateTo(0)
                            }
                        }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.city_delete),
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }) {
        Column {
            Card(shape = RoundedCornerShape(5.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.primaryVariant)
                        .clickable {
                            toWeatherDetails(cityInfo)
                        }
                        .padding(horizontal = 10.dp, vertical = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${cityInfo.province} ${cityInfo.city} ${cityInfo.name}",
                        color =
                        if (cityInfo.isIndex > 0) Color(
                            red = 53,
                            green = 128,
                            blue = 186
                        ) else Color.Unspecified
                    )
                    if (cityInfo.isLocation == 1) {
                        ImageLoader(
                            data = R.drawable.ic_baseline_location_on_24,
                            modifier = Modifier.wrapContentWidth(Alignment.End)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview(showBackground = false, name = "城市item")
@Composable
fun CityItemPreview() {
    val cityInfo = CityInfo(
        name = "朱江",
        province = "微子国",
        city = "南街"
    )
    CityListItem(cityInfo = cityInfo, true, {}, {})
}
