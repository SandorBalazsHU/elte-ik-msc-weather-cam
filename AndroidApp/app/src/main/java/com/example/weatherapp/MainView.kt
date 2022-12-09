package com.example.weatherapp

import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.hardware.HardwareEntity
import kotlinx.coroutines.launch


@Composable
fun MainPage(
    viewModel: MainViewModel
){

    val hardwareList by viewModel.hardwareUnits.collectAsState()
    val apiKey by viewModel.apiKey.collectAsState()
    val pollingInterval by viewModel.pollingInterval.collectAsState()
    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )
    var isHardwareDialogOpen by remember {
        mutableStateOf(false)
    }
    val isPollingEnabled by viewModel.pollingEnabled.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val onHardwareDelete = viewModel::onHardwareDelete
    val onPollToggle = viewModel::onPollingToggle
    val onHardwareAdd = viewModel::onHardwareAdd
    val onSetApiKey = viewModel::onSetApiKey
    val onPollingIntervalChanged = viewModel::onPollingTimeSet

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
            )
        },
        bottomBar = {
            BottomAppBar {
                Text(text = "mvp version")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            StartButton(onPollToggle, isPollingEnabled)
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
            Settings(onSetApiKey, onPollingIntervalChanged, apiKey, pollingInterval)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp, 16.dp)
        ) {
            items(hardwareList.toList()){
                HardwareRow(it.second, onHardwareAdd, onHardwareDelete)
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            isHardwareDialogOpen = true
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
        }

        AddHardwareDialog(
            isOpen = isHardwareDialogOpen,
            onHardwareAdd = onHardwareAdd,
            onClosed = { isHardwareDialogOpen = false }
        )

    }
}

@Composable
fun HardwareRow(
    desc : HardwareEntity,
    onHardwareAdd : (String, String) -> Unit,
    onHardwareDelete : (String) -> Unit
){
    var isDelOpen by remember {
        mutableStateOf(false)
    }
    var isEditOpen by remember {
        mutableStateOf(false)
    }
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
                onClick = { isEditOpen = true },
                icon = Icons.Default.Settings,
                description = "Settings",
                text = "Edit"
            )
            HardwareButton(
                onClick = { isDelOpen = true },
                icon = Icons.Default.Delete,
                description = "Delete",
                text = "Delete"
            )
        }
    }
    EditHardwareDialog(isEditOpen, desc, onHardwareAdd) { isEditOpen = false }
    DelHardwareDialog(isDelOpen, desc.nickname, onHardwareDelete) { isDelOpen = false }
}



@Composable
fun HardwareButton(onClick: () -> Unit,
                   icon: ImageVector,
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
fun DelHardwareDialog(
    isOpen: Boolean,
    name: String,
    onHardwareDelete: (String) -> Unit,
    onClosed: () -> Unit
){
    if(isOpen) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Are you sure you want to delete: $name?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onHardwareDelete(name)
                        onClosed()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClosed
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun EditHardwareDialog(
    isOpen: Boolean,
    ent: HardwareEntity,
    onHardwareAdd: (String, String) -> Unit,
    onClosed: () -> Unit
){
    if(isOpen){
        var ipAddress by remember { mutableStateOf(ent.ipAddress) }
        val context = LocalContext.current

        AlertDialog(
            onDismissRequest = onClosed,
            title = { Text("Edit hardware") },
            text = {
                Column {
                    OutlinedTextField(
                        value = ipAddress,
                        onValueChange = {
                            ipAddress = it
                        },
                        label = { Text("Address") }
                    )

                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if(ipAddress == ""){
                            Toast.makeText(
                                context,
                                "Address must be nonempty!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (!URLUtil.isValidUrl(ipAddress)) {
                            Toast.makeText(
                                context,
                                "Address must be a valid url!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            onHardwareAdd(ent.nickname, ipAddress)
                            onClosed()
                        }
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClosed
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}


@Composable
fun AddHardwareDialog(
    isOpen: Boolean,
    onHardwareAdd: (String, String) -> Unit,
    onClosed: () -> Unit
){
    if(isOpen){
        var nickname by remember { mutableStateOf("") }
        var ipAddress by remember { mutableStateOf("") }
        val context =  LocalContext.current

        AlertDialog(
            onDismissRequest = onClosed,
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
                        label = { Text("Address") }
                    )

                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if(nickname == "" || ipAddress == ""){
                            Toast.makeText(
                                context,
                                "Nickname and Address must be nonempty!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (!URLUtil.isValidUrl(ipAddress)) {
                            Toast.makeText(
                                context,
                                "Address must be a valid url!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            onHardwareAdd(nickname, ipAddress)
                            onClosed()
                        }
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClosed
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun StartButton(
    onPollToggle: () -> Unit,
    isPollingEnabled: Boolean,
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
    onPollingIntervalChanged: (Int) -> Unit,
    apiKey: String?,
    pollingInterval: Int
){
    var apiKeyDialogOpen by remember {
        mutableStateOf(false)
    }
    var pollIntervalDialogOpen by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        TextButton(
            onClick = { apiKeyDialogOpen = true },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text("API key")
        }
        TextButton(
            onClick = { pollIntervalDialogOpen = true },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text("Polling interval")
        }
    }

    ApiKeyDialog(
        isOpen = apiKeyDialogOpen,
        onSetApiKey = onSetApiKey,
        apiKey = apiKey,
        onClosed = {
            apiKeyDialogOpen = false
        }
    )
    PollingIntervalDialog(
        isOpen = pollIntervalDialogOpen,
        onPollingIntervalChanged = onPollingIntervalChanged,
        pollingInterval = pollingInterval,
        onClosed = {
            pollIntervalDialogOpen = false
        }
    )
}

@Composable
fun ApiKeyDialog(
    isOpen: Boolean,
    apiKey: String?,
    onSetApiKey: (String) -> Unit,
    onClosed: () -> Unit
){
    if(isOpen){
        var apiKeyVal by remember { mutableStateOf(apiKey ?: "") }

        AlertDialog(
            onDismissRequest = onClosed,
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
                            onClosed()
                        }
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClosed
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun PollingIntervalDialog(
    isOpen: Boolean,
    pollingInterval: Int,
    onPollingIntervalChanged: (Int) -> Unit,
    onClosed: () -> Unit,
){
    if(isOpen){
        val optionsList = listOf(1, 15, 20, 30, 60)
        val defaultIndex: Int = optionsList.indexOf(pollingInterval)
        var selectedIndex by remember {
            mutableStateOf(defaultIndex)
        }

        AlertDialog(
            onDismissRequest = onClosed,
            title = { Text("Set polling interval in minutes") },
            text = {
                LazyColumn {
                    items(optionsList){
                        SelectableOption(value = it, selectedValue = optionsList[selectedIndex]) {
                            selectedValue ->
                                selectedIndex = optionsList.indexOf(selectedValue)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onPollingIntervalChanged(optionsList[selectedIndex])
                        onClosed()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClosed
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SelectableOption(
    value: Int,
    selectedValue: Int,
    onClick: (Int) -> Unit
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .selectable(
            selected = (value == selectedValue),
            onClick = {
                onClick(value)
            }
        )
        .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (value == selectedValue),
            onClick = {
                onClick(value)
            }
        )
        Text(
            text = "$value minutes",
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}