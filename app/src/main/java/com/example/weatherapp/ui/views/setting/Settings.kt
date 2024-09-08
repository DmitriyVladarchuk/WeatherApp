package com.example.weatherapp.ui.views.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.Typography


@Composable
fun Settings(navController: NavController, modifier: Modifier = Modifier, viewModel: ThemeViewModel = viewModel()) {

    val darkMode by viewModel.darkMode.collectAsState()
    val context = LocalContext.current

    Column(modifier = modifier
        .fillMaxSize()
        .padding(start = 30.dp, end = 30.dp)) {

        HeaderSettings {
            navController.popBackStack()
        }
        
        Text(
            text = stringResource(id = R.string.theme),
            style = Typography.labelLarge,
            fontSize = 24.sp
        )

        ItemTheme(
            text = stringResource(id = R.string.dark_mode),
            description = stringResource(id = R.string.description_dark_mode),
            isSelected = darkMode
        ) {
            if (!darkMode) viewModel.changeDarkMode()
        }

        ItemTheme(
            text = stringResource(id = R.string.light_mode),
            description = stringResource(id = R.string.description_light_mode),
            isSelected = !darkMode
        ) {
            if (darkMode) viewModel.changeDarkMode()
        }

        Feedback(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 30.dp)
        ) {
            // openUrl(context, "https://github.com/DmitriyVladarchuk/WeatherApp")
            sendEmail(context, context.getString(R.string.feedback_title_message), "dimadf135@gmail.com")
        }

        Common(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            clickableAbout = {  },
            clickableGitHub = { openUrl(context, "https://github.com/DmitriyVladarchuk/WeatherApp") }
        )

    }
}

@Composable
fun HeaderSettings(clickableBack: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 30.dp)
            .clickable { clickableBack() }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
            tint = colorResource(id = R.color.translucent),
            modifier = Modifier
                .padding(end = 6.dp)
                .size(21.dp),
            contentDescription = "Back"
        )

        Text(
            text = stringResource(id = R.string.settings),
            style = Typography.bodyMedium,
            color = colorResource(id = R.color.translucent),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}


@Composable
fun ItemTheme(text: String, description: String, isSelected: Boolean, clickable: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .clickable { clickable() }
    ) {

        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = text,
                style = Typography.bodyMedium,
                color = colorResource(id = R.color.translucent),
            )

            Text(
                text = description,
                style = Typography.labelSmall,
                fontSize = 14.sp,
                color = colorResource(id = R.color.translucent),
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (isSelected)
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.check),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(24.dp),
                tint = colorResource(id = R.color.content),
                contentDescription = ""
            )
    }
}

@Composable
fun Feedback(modifier: Modifier = Modifier, clickable: () -> Unit) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.feedback),
            style = Typography.labelLarge,
            fontSize = 24.sp,
        )

        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable { clickable() }
        ) {
            Text(
                text = stringResource(id = R.string.report_an_issue),
                style = Typography.bodyMedium,
                color = colorResource(id = R.color.translucent)
            )

            Text(
                text = stringResource(id = R.string.report_description),
                style = Typography.labelSmall,
                fontSize = 14.sp,
                color = colorResource(id = R.color.translucent),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Composable
fun Common(modifier: Modifier = Modifier, clickableAbout: () -> Unit, clickableGitHub: () -> Unit) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.common),
            style = Typography.labelLarge,
            fontSize = 24.sp,
        )

        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable { clickableAbout() }
        ) {
            Text(
                text = stringResource(id = R.string.about_app),
                style = Typography.bodyMedium,
                color = colorResource(id = R.color.translucent)
            )

            Text(
                text = stringResource(id = R.string.about_description),
                style = Typography.labelSmall,
                fontSize = 14.sp,
                color = colorResource(id = R.color.translucent),
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable { clickableGitHub() }
        ) {
            Text(
                text = stringResource(id = R.string.github),
                style = Typography.bodyMedium,
                color = colorResource(id = R.color.translucent)
            )

            Text(
                text = stringResource(id = R.string.github_description),
                style = Typography.labelSmall,
                fontSize = 14.sp,
                color = colorResource(id = R.color.translucent),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    context.startActivity(intent)
}

fun sendEmail(context: Context, title: String, email: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822" // Указывает, что это email
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, "Feedback")
    }
    context.startActivity(Intent.createChooser(intent, title))
}