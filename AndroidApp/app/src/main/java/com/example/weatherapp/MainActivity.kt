package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import com.example.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.ui.layout.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //todo: save some state in Bundle, some in SharedPreferences?
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainPage()
                }
            }
        }
    }
}

@Composable
fun HardwareButton(onClick: () -> Unit,
                   icon:  ImageVector,
                   description: String,
                   text: String){
    Row(
        modifier = Modifier
            .border(BorderStroke(width = 1.dp, color = Color.LightGray))
            .padding(8.dp)
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ){
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.contentColorFor(
                    MaterialTheme.colors.background
                )
            ),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text)
        }
    }
}

@Composable
fun HardwareRow(
    desc : HardwareState,
    onHardwareDelete : (String) -> Unit
){
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .shadow(elevation = 2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.primaryVariant)
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = desc.nickname,
                    color = Color.White
                )
            }
            HardwareButton(
                //todo: edit ip address
                onClick = { },
                icon = Icons.Default.Settings,
                description = "Settings",
                text = "Edit"
            )
            HardwareButton(
                //todo: display last sent data
                onClick = { },
                icon = Icons.Default.Info,
                description = "Info",
                text = "Info"
            )
            HardwareButton(
                // todo: some "are you sure?" dialog
                onClick = { onHardwareDelete(desc.nickname) },
                icon = Icons.Default.Delete,
                description = "Delete",
                text = "Delete"
            )
        }
    }
}


@Composable
//@Preview(showBackground = true)
fun MainPage(
    viewModel: MainViewModel = viewModel(),
){
    val hardwareList by viewModel.hardwares.collectAsState()
    val apiKey by viewModel.apiKey.collectAsState()
    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )
    val isHardwareDialogOpen = remember {
        mutableStateOf(false)
    }
    val isPollingEnabled by viewModel.pollingEnabled.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val onHardwareDelete = viewModel::onHardwareDelete
    val onPollToggle = viewModel::onPollingToggle
    val onHardwareAdd = viewModel::onHardwareAdd
    val onSetApiKey = viewModel::onSetApiKey

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Weather Station") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier.clickable(onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        })
                    )
                }
            )},
        bottomBar = {
            BottomAppBar {
                Text(text = "TBD")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            StartButton(onPollToggle = onPollToggle, isPollingEnabled = isPollingEnabled)
        },
        drawerGesturesEnabled = false,
        drawerContent = {
            TextButton(
                onClick = { coroutineScope.launch { scaffoldState.drawerState.close() } },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close settings")
            }
            Divider()
            Settings(onSetApiKey = onSetApiKey, apiKey = apiKey)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            for(h in hardwareList){
                HardwareRow(h.component2(), onHardwareDelete)
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        isHardwareDialogOpen.value = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondaryVariant,
                        contentColor = MaterialTheme.colors.contentColorFor(
                            MaterialTheme.colors.secondaryVariant
                        )
                    ),
                    modifier = Modifier.fillMaxWidth()
                ){
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add hardware",
                    )
                }
            }
        }
        AddHardwareDialog(isOpen = isHardwareDialogOpen, onHardwareAdd = onHardwareAdd)

    }
}

@Composable
fun AddHardwareDialog(
    isOpen : MutableState<Boolean>,
    onHardwareAdd : (String, String) -> Unit
){
    if(isOpen.value){
        var nickname by remember { mutableStateOf("") }
        var ipAddress by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { isOpen.value = false },
            title = { Text("Add hardware") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nickname,
                        onValueChange = {
                            nickname = it
                        },
                        label = { Text("Name") }
                    )
                    OutlinedTextField(
                        value = ipAddress,
                        onValueChange = {
                            ipAddress = it
                        },
                        label = { Text("IP address") }
                    )

                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if(nickname != "" && ipAddress != ""){
                            onHardwareAdd(nickname, ipAddress)
                            isOpen.value = false
                        }
                        // todo some feedback that the values are missing
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isOpen.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun StartButton(
    onPollToggle : () -> Unit,
    isPollingEnabled : Boolean,
){
    FloatingActionButton(
        onClick = onPollToggle
    ) {
        Icon(
            imageVector = if (isPollingEnabled) Icons.Filled.Close else Icons.Default.PlayArrow,
            contentDescription = "Start",
        )
    }
}

@Composable
fun Settings(
    onSetApiKey: (String) -> Unit,
    apiKey: String?
){
    val apiKeyDialogOpen = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(8.dp).fillMaxWidth()
    ) {
        TextButton(
            onClick = { apiKeyDialogOpen.value = true },
            modifier = Modifier.padding(8.dp).fillMaxWidth()
        ) {
            Text("API key")
        }
    }

    ApiKeyDialog(isOpen = apiKeyDialogOpen, onSetApiKey = onSetApiKey, apiKey = apiKey)
}

// could we make it nicer with state hoisting?
@Composable
fun ApiKeyDialog(
    isOpen : MutableState<Boolean>,
    onSetApiKey : (String) -> Unit,
    apiKey: String?
){
    if(isOpen.value){
        var apiKeyVal by remember { mutableStateOf(apiKey ?: "") }

        AlertDialog(
            onDismissRequest = { isOpen.value = false },
            title = { Text("Set API key") },
            text = {
                Column {
                    OutlinedTextField(
                        value = apiKeyVal,
                        onValueChange = {
                            apiKeyVal = it
                        },
                        label = { Text("Name") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if(apiKeyVal != ""){
                            onSetApiKey(apiKeyVal)
                            isOpen.value = false
                        }
                        // todo some feedback that the values are missing
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isOpen.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
