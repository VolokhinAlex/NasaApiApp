package com.volokhinaleksey.materialdesign.ui.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.volokhinaleksey.materialdesign.model.NasaDataDTO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetModal(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    nasaDataDTO: NasaDataDTO
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
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

}

@Composable
private fun SetTextBottomSheet(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(8.dp),
        fontSize = 20.sp
    )
}
