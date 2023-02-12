package com.volokhinaleksey.materialdesign.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.volokhinaleksey.materialdesign.model.NasaDataDTO
import com.volokhinaleksey.materialdesign.ui.widgets.bottom_sheet_scaffold.BottomSheetScaffold
import com.volokhinaleksey.materialdesign.ui.widgets.bottom_sheet_scaffold.rememberBottomSheetScaffoldState

private val SheetElevation = 20.dp
private val SheetPeekHeight = 56.dp
private val SheetCornerShape = 20.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetBehavior(nasaDataDTO: NasaDataDTO) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContent = {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, top = 15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Divider(
                        modifier = Modifier
                            .size(width = 32.dp, height = 4.dp)
                            .clip(
                                RoundedCornerShape(30.dp)
                            ),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Text(
                    text = nasaDataDTO.title.orEmpty(),
                    modifier = Modifier.padding(8.dp),
                    fontSize = 25.sp
                )
                SetTextBottomSheet(text = nasaDataDTO.copyright.orEmpty())
                SetTextBottomSheet(text = nasaDataDTO.explanation.orEmpty())
                SetTextBottomSheet(text = nasaDataDTO.date.orEmpty())
                SetTextBottomSheet(text = nasaDataDTO.mediaType.orEmpty())
            }
        },
        sheetBackgroundColor = MaterialTheme.colorScheme.onSecondary,
        sheetElevation = SheetElevation,
        sheetShape = RoundedCornerShape(topStart = SheetCornerShape, topEnd = SheetCornerShape),
        scaffoldState = scaffoldState,
        sheetPeekHeight = SheetPeekHeight,
    )
}