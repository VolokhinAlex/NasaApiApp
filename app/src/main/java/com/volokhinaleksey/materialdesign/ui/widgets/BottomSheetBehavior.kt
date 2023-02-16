package com.volokhinaleksey.materialdesign.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.volokhinaleksey.materialdesign.R
import com.volokhinaleksey.materialdesign.model.PictureOfTheDayDTO
import com.volokhinaleksey.materialdesign.ui.theme.montserratFontFamily
import com.volokhinaleksey.materialdesign.ui.widgets.bottom_sheet_scaffold.BottomSheetScaffold
import com.volokhinaleksey.materialdesign.ui.widgets.bottom_sheet_scaffold.rememberBottomSheetScaffoldState

private val SheetElevation = 20.dp
private val SheetPeekHeight = 56.dp
private val SheetCornerShape = 20.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetBehavior(nasaDataDTO: PictureOfTheDayDTO) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val copyright = buildAnnotatedString {
        append(stringResource(R.string.copyright))
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        ) {
            append(nasaDataDTO.copyright.orEmpty())
        }
    }
    val date = buildAnnotatedString {
        append("Photo date: ")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
            )
        ) {
            append(nasaDataDTO.date.orEmpty())
        }
    }
    val imgUrl = buildAnnotatedString {
        pushStyle(SpanStyle(fontSize = 20.sp, fontFamily = montserratFontFamily))
        val linkText = "Click this link to open the image in browser"
        val startIndex = linkText.indexOf("link")
        val endIndex = startIndex + 4

        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontFamily = montserratFontFamily,
                textDecoration = TextDecoration.Underline
            ), start = startIndex, end = endIndex
        )

        append(linkText)

        addStringAnnotation(
            tag = "URL",
            annotation = nasaDataDTO.url.orEmpty(),
            start = startIndex,
            end = endIndex
        )
    }
    val uriHandler = LocalUriHandler.current
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
                SetTextBottomSheet(text = copyright)
                SetTextBottomSheet(text = nasaDataDTO.explanation.orEmpty())
                SetTextBottomSheet(text = date)
                ClickableText(
                    text = imgUrl,
                    modifier = Modifier.padding(8.dp)
                ) {
                    imgUrl
                        .getStringAnnotations(tag = "URL", start = it, end = it)
                        .firstOrNull()?.let { url ->
                            uriHandler.openUri(url.item)
                        }
                }
            }
        },
        sheetBackgroundColor = MaterialTheme.colorScheme.onSecondary,
        sheetElevation = SheetElevation,
        sheetShape = RoundedCornerShape(topStart = SheetCornerShape, topEnd = SheetCornerShape),
        scaffoldState = scaffoldState,
        sheetPeekHeight = SheetPeekHeight,
    )
}