package com.example.newsapp


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Settings(
    selectedCountry: String,
    onCountrySelected: (String, String) -> Unit
) {
    val countries = listOf(
        "United Arab Emirates", "Argentina", "Austria", "Australia", "Belgium", "Bulgaria",
        "Brazil", "Canada", "Switzerland", "China", "Colombia", "Czech Republic", "Germany",
        "Egypt", "Spain", "France", "United Kingdom", "Greece", "Hong Kong", "Hungary",
        "Indonesia", "Ireland", "Israel", "India", "Italy", "Japan", "South Korea", "Lithuania",
        "Latvia", "Morocco", "Mexico", "Malaysia", "Nigeria", "Netherlands", "Norway",
        "New Zealand", "Philippines", "Poland", "Portugal", "Romania", "Serbia", "Russia",
        "Saudi Arabia", "Sweden", "Singapore", "Slovakia", "Thailand", "Turkey", "Taiwan",
        "Ukraine", "United States", "Venezuela", "South Africa"
    )
    val countryToCodeMap = hashMapOf(
        "United Arab Emirates" to "ae",
        "Argentina" to "ar",
        "Austria" to "at",
        "Australia" to "au",
        "Belgium" to "be",
        "Bulgaria" to "bg",
        "Brazil" to "br",
        "Canada" to "ca",
        "Switzerland" to "ch",
        "China" to "cn",
        "Colombia" to "co",
        "Czech Republic" to "cz",
        "Germany" to "de",
        "Egypt" to "eg",
        "Spain" to "es",
        "France" to "fr",
        "United Kingdom" to "gb",
        "Greece" to "gr",
        "Hong Kong" to "hk",
        "Hungary" to "hu",
        "Indonesia" to "id",
        "Ireland" to "ie",
        "Israel" to "il",
        "India" to "in",
        "Italy" to "it",
        "Japan" to "jp",
        "South Korea" to "kr",
        "Lithuania" to "lt",
        "Latvia" to "lv",
        "Morocco" to "ma",
        "Mexico" to "mx",
        "Malaysia" to "my",
        "Nigeria" to "ng",
        "Netherlands" to "nl",
        "Norway" to "no",
        "New Zealand" to "nz",
        "Philippines" to "ph",
        "Poland" to "pl",
        "Portugal" to "pt",
        "Romania" to "ro",
        "Serbia" to "rs",
        "Russia" to "ru",
        "Saudi Arabia" to "sa",
        "Sweden" to "se",
        "Singapore" to "sg",
        "Slovakia" to "sk",
        "Thailand" to "th",
        "Turkey" to "tr",
        "Taiwan" to "tw",
        "Ukraine" to "ua",
        "United States" to "us",
        "Venezuela" to "ve",
        "South Africa" to "za"
    )

    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Select a Country",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .clickable { expanded = true }
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = selectedCountry,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                Icon(imageVector = if(!expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp, contentDescription =null)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface).height(400.dp)
                    .padding(8.dp)
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(text = country) },
                        onClick = {
                            val countryCode = countryToCodeMap[country]
                                onCountrySelected(country, countryCode.toString())
                                expanded = false
                        }
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
