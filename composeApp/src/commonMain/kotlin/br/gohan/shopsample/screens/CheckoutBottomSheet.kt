package br.gohan.shopsample.screens

import KottieAnimation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.gohan.shopsample.components.Button
import br.gohan.shopsample.ui.Dimens
import kotlinx.coroutines.delay
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import shopsample.composeapp.generated.resources.Res
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun BottomSheet(showBottomSheet: (Boolean) -> Unit) {
    val sheetState = rememberModalBottomSheetState()

    var animation by remember { mutableStateOf("") }

    var paymentStatus by remember { mutableStateOf(PaymentAnimation.Processing) }

    LaunchedEffect(paymentStatus) {
        animation = Res.readBytes("files/${paymentStatus.fileName}").decodeToString()
    }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation)
    )

    val animationState by animateKottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        iterations = Int.MAX_VALUE
    )

    LaunchedEffect(Unit) {
        delay(3000)
        paymentStatus = if (Random.nextBoolean()) {
            PaymentAnimation.Success
        } else {
            PaymentAnimation.Failure
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet(false)
        },
        sheetState = sheetState
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.height(330.dp).fillMaxSize()
        ) {
            KottieAnimation(
                composition = composition,
                progress = { animationState.progress },
                modifier = if (paymentStatus == PaymentAnimation.Processing) Modifier.size(160.dp) else Modifier.size(
                    180.dp
                )
            )
            Text(paymentStatus.status, fontSize = Dimens.fontNormal)
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                modifier = Modifier.padding(horizontal = 20.dp),
                message = if (paymentStatus == PaymentAnimation.Success) "Done" else "Cancel Payment"
            ) {
                showBottomSheet(false)
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

enum class PaymentAnimation(val fileName: String, val status: String) {
    Success("success-payment.json", "Payment Success"),
    Failure("failed-payment.json", "Payment Failed"),
    Processing("processing-payment.json", "Processing Payment")
}
