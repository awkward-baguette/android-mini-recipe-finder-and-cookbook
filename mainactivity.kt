package com.example.recipenest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipenest.ui.theme.RecipeNestTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

private val ForestGreen = Color(0xFF285943)
private val Cream = Color(0xFFFFFBF5)
private val SageGreen = Color(0xFFE5EFE5)
private val WarmBrown = Color(0xFF755744)

data class Recipe(
    val name: String,
    val category: String,
    val description: String,
    val emoji: String,
    val time: String,
    val ingredients: List<String> = emptyList(),
    val instructions: List<String> = emptyList()
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            RecipeNestTheme {
                RecipeNestHomeScreen()
            }
        }
    }
}

@Composable
fun RecipeNestHomeScreen() {

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    var selectedTab by remember {
        mutableStateOf("Home")
    }

    var selectedRecipe by remember {
        mutableStateOf<Recipe?>(null)
    }

    var favoriteRecipes by remember {
        mutableStateOf(setOf<String>())
    }

    val categories = listOf(
        "All",
        "Chicken",
        "Dessert",
        "Seafood",
        "Vegetarian"
    )

    val recipes = listOf(
        Recipe(
            name = "Chicken Curry",
            category = "Chicken",
            description = "Rich, warm and full of flavor",
            emoji = "🍛",
            time = "35 min",

            ingredients = listOf(
                "Chicken - 500 g",
                "Onions - 2 medium",
                "Tomatoes - 2",
                "Ginger-garlic paste - 1 tbsp",
                "Oil - 2 tbsp",
                "Salt and spices - as needed"
            ),

            instructions = listOf(
                "Wash and prepare the chicken.",
                "Heat oil in a pan and sauté the chopped onions.",
                "Add ginger-garlic paste and cook briefly.",
                "Add chopped tomatoes, salt and spices. Cook until softened.",
                "Add chicken and mix well with the masala.",
                "Add a little water, cover and cook until the chicken is fully cooked.",
                "Serve hot with rice or roti."
            )
        ),
        Recipe(
            name = "Chocolate Cake",
            category = "Dessert",
            description = "A sweet homemade delight",
            emoji = "🍰",
            time = "45 min",

            ingredients = listOf(
                "All-purpose flour - 1 cup",
                "Cocoa powder - 1/4 cup",
                "Sugar - 3/4 cup",
                "Milk - 1/2 cup",
                "Butter - 1/4 cup",
                "Baking powder - 1 tsp"
            ),

            instructions = listOf(
                "Preheat the oven to 180°C.",
                "Mix the flour, cocoa powder, sugar and baking powder.",
                "Add milk and melted butter, then mix until smooth.",
                "Pour the batter into a greased cake tin.",
                "Bake until a toothpick inserted into the centre comes out clean.",
                "Allow the cake to cool before serving."
            )
        ),
        Recipe(
            name = "Fish Curry",
            category = "Seafood",
            description = "A comforting coastal favorite",
            emoji = "🍲",
            time = "30 min",

            ingredients = listOf(
                "Fish pieces - 500 g",
                "Onion - 1 large",
                "Tomatoes - 2",
                "Ginger-garlic paste - 1 tbsp",
                "Curry powder - 2 tbsp",
                "Oil - 2 tbsp",
                "Salt - as needed"
            ),

            instructions = listOf(
                "Clean the fish pieces and keep them aside.",
                "Heat oil in a pan and sauté the chopped onion.",
                "Add ginger-garlic paste and cook briefly.",
                "Add tomatoes, curry powder and salt.",
                "Add water and simmer to prepare the curry base.",
                "Gently add the fish and cook until it is fully cooked.",
                "Serve hot with rice."
            )
        ),
        Recipe(
            name = "Vegetable Pasta",
            category = "Vegetarian",
            description = "Fresh vegetables and delicious pasta",
            emoji = "🍝",
            time = "25 min",

            ingredients = listOf(
                "Pasta - 200 g",
                "Onion - 1",
                "Bell pepper - 1",
                "Tomato sauce - 1/2 cup",
                "Garlic - 2 cloves",
                "Olive oil - 1 tbsp",
                "Salt and pepper - as needed"
            ),

            instructions = listOf(
                "Boil the pasta according to its package instructions.",
                "Heat olive oil in a pan.",
                "Add chopped garlic and onion and sauté briefly.",
                "Add the chopped bell pepper and cook until tender.",
                "Stir in the tomato sauce, salt and pepper.",
                "Add the cooked pasta and mix well.",
                "Serve warm."
            )
        ),
        Recipe(
            name = "Grilled Chicken",
            category = "Chicken",
            description = "A simple and savory meal",
            emoji = "🍗",
            time = "40 min",

            ingredients = listOf(
                "Chicken pieces - 500 g",
                "Yogurt - 1/4 cup",
                "Lemon juice - 1 tbsp",
                "Ginger-garlic paste - 1 tbsp",
                "Chilli powder - 1 tsp",
                "Salt - as needed",
                "Oil - 1 tbsp"
            ),

            instructions = listOf(
                "Mix yogurt, lemon juice, ginger-garlic paste, spices and salt.",
                "Coat the chicken with the marinade.",
                "Refrigerate and marinate for at least 30 minutes.",
                "Preheat your grill or oven.",
                "Grill the chicken, turning as needed, until fully cooked.",
                "Check that the thickest part reaches a safe internal temperature.",
                "Serve hot."
            )
        )
    )


    val filteredRecipes = recipes.filter { recipe ->

        val matchesSearch = recipe.name.contains(
            searchText.trim(),
            ignoreCase = true
        )

        val matchesCategory =
            selectedCategory == "All" ||
                    recipe.category == selectedCategory

        matchesSearch && matchesCategory
    }

    if (selectedRecipe == null) {
        Scaffold(
            containerColor = Cream,

            bottomBar = {

                NavigationBar(
                    containerColor = Color.White
                ) {

                    val tabs = listOf(
                        "Home",
                        "Favorites",
                        "My Cookbook"
                    )

                    val symbols = listOf(
                        "⌂",
                        "♡",
                        "▤"
                    )

                    tabs.forEachIndexed { index, tab ->

                        NavigationBarItem(
                            selected = selectedTab == tab,

                            onClick = {
                                selectedTab = tab
                            },

                            icon = {
                                Text(
                                    text = symbols[index],
                                    fontSize = 23.sp
                                )
                            },

                            label = {
                                Text(
                                    text = tab,
                                    fontSize = 10.sp,
                                    maxLines = 1
                                )
                            }
                        )
                    }
                }
            }

        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),

                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 24.dp
                ),

                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {

                // HEADER

                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {

                            Text(
                                text = "RecipeNest",
                                fontSize = 29.sp,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreen
                            )

                            Spacer(
                                modifier = Modifier.height(5.dp)
                            )

                            Text(
                                text = "Your little corner of good food.",
                                fontSize = 14.sp,
                                color = WarmBrown
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = SageGreen
                        ) {
                            Image(
                                painter = painterResource(
                                    id = R.mipmap.recipenestlogo
                                ),
                                contentDescription = "RecipeNest logo",
                                modifier = Modifier
                                    .size(60.dp)

                            )
                        }
                    }
                }

                // SEARCH BAR

                item {

                    OutlinedTextField(
                        value = searchText,

                        onValueChange = {
                            searchText = it
                        },

                        modifier = Modifier.fillMaxWidth(),

                        placeholder = {
                            Text("Search recipes...")
                        },

                        leadingIcon = {
                            Text(
                                text = "⌕",
                                fontSize = 27.sp
                            )
                        },

                        singleLine = true,

                        shape = RoundedCornerShape(18.dp),

                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = ForestGreen,
                            unfocusedIndicatorColor = Color(0xFFE5E0D8)
                        )
                    )
                }

                // WELCOME BANNER
                if (searchText.isBlank()) {
                    item {

                        Card(
                            shape = RoundedCornerShape(24.dp),

                            colors = CardDefaults.cardColors(
                                containerColor = ForestGreen
                            ),

                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),

                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = "A little inspiration,",
                                        fontSize = 15.sp,
                                        color = Color(0xFFDCE9D8)
                                    )

                                    Spacer(
                                        modifier = Modifier.height(5.dp)
                                    )

                                    Text(
                                        text = "a lot of flavor.",
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )

                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )

                                    Text(
                                        text = "Find your next favorite dish.",
                                        fontSize = 13.sp,
                                        color = Color.White
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.width(8.dp)
                                )

                                Text(
                                    text = "🥗",
                                    fontSize = 55.sp
                                )
                            }
                        }
                    }
                }


                // CATEGORIES
                if (searchText.isBlank()) {
                    item {

                        Column {

                            Text(
                                text = "Explore Categories",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreen
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            LazyRow(
                                horizontalArrangement =
                                    Arrangement.spacedBy(8.dp)
                            ) {

                                items(categories) { category ->

                                    FilterChip(
                                        selected =
                                            selectedCategory == category,

                                        onClick = {
                                            selectedCategory = category
                                        },

                                        label = {
                                            Text(category)
                                        },

                                        shape = RoundedCornerShape(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }


                // RECIPE SECTION TITLE

                // RECIPE SECTION TITLE
                item {
                    Column {
                        Text(
                            text = if (searchText.isBlank()) {
                                "Popular Recipes"
                            } else {
                                "Search Results"
                            },
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreen
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = if (searchText.isBlank()) {
                                "Something delicious awaits you."
                            } else {
                                "Recipes matching your search."
                            },
                            fontSize = 13.sp,
                            color = WarmBrown
                        )
                    }
                }

                // RECIPE CARDS

                if (filteredRecipes.isEmpty()) {

                    item {

                        Text(
                            text = "No recipes found. Try another search.",
                            color = WarmBrown,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                    }

                } else {

                    items(filteredRecipes) { recipe ->

                        RecipeCard(
                            recipe = recipe,
                            onClick = {
                                selectedRecipe = recipe
                            }
                        )
                    }
                }
            }
        }
    } else {

        RecipeDetailsScreen(
            recipe = selectedRecipe!!,

            onBack = {
                selectedRecipe = null
            },

            isFavorite = favoriteRecipes.contains(selectedRecipe!!.name),

            onFavoriteClick = {
                val recipeName = selectedRecipe!!.name

                favoriteRecipes =
                    if (favoriteRecipes.contains(recipeName)) {
                        favoriteRecipes - recipeName
                    } else {
                        favoriteRecipes + recipeName
                    }
            }
        )
    }

}
@Composable
fun RecipeCard(recipe: Recipe,onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(95.dp),

                shape = RoundedCornerShape(16.dp),

                color = SageGreen
            ) {

                BoxPlaceholder(
                    emoji = recipe.emoji
                )
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = recipe.category.uppercase(),
                    fontSize = 10.sp,
                    color = ForestGreen,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = recipe.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF292D29)
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = recipe.description,
                    fontSize = 12.sp,
                    color = WarmBrown
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "◷  ${recipe.time}",
                    fontSize = 12.sp,
                    color = ForestGreen
                )
            }
        }
    }
}

@Composable
fun BoxPlaceholder(emoji: String) {

    androidx.compose.foundation.layout.Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = emoji,
            fontSize = 43.sp
        )
    }
}
@Composable
fun RecipeDetailsScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {

    Scaffold(
        containerColor = Cream
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),

            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 12.dp,
                bottom = 32.dp
            ),

            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // BACK BUTTON

            item {

                Text(
                    text = "←  Back to recipes",
                    color = ForestGreen,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,

                    modifier = Modifier
                        .clickable {
                            onBack()
                        }
                        .padding(vertical = 8.dp)
                )
            }


            // RECIPE IMAGE AREA

            item {

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),

                    shape = RoundedCornerShape(24.dp),

                    color = SageGreen
                ) {

                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier.fillMaxSize(),

                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = recipe.emoji,
                            fontSize = 100.sp
                        )
                    }
                }
            }


            // RECIPE TITLE AND DESCRIPTION

            item {

                Column {

                    Text(
                        text = recipe.category.uppercase(),

                        color = ForestGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = recipe.name,

                        color = ForestGreen,
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = recipe.description,

                        color = WarmBrown,
                        fontSize = 15.sp
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = SageGreen
                    ) {

                        Text(
                            text = "◷  Cooking time: ${recipe.time}",

                            color = ForestGreen,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,

                            modifier = Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 10.dp
                            )
                        )
                    }            // FAVORITE BUTTON
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Surface(
                        onClick = onFavoriteClick,
                        shape = RoundedCornerShape(14.dp),
                        color = if (isFavorite) ForestGreen else SageGreen
                    ) {
                        Text(
                            text = if (isFavorite) {
                                "♥  Added to Favorites"
                            } else {
                                "♡  Add to Favorites"
                            },
                            color = if (isFavorite) Color.White else ForestGreen,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            )
                        )
                    }
                }
            }


            // INGREDIENTS HEADING

            item {

                Text(
                    text = "Ingredients",

                    color = ForestGreen,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            // INGREDIENT LIST

            if (recipe.ingredients.isEmpty()) {

                item {

                    Text(
                        text = "Ingredients are not available yet.",
                        color = WarmBrown,
                        fontSize = 14.sp
                    )
                }

            } else {

                items(recipe.ingredients) { ingredient ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.White,
                                RoundedCornerShape(14.dp)
                            )
                            .padding(14.dp),

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "✓",
                            color = ForestGreen,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Text(
                            text = ingredient,
                            color = Color(0xFF292D29),
                            fontSize = 15.sp
                        )
                    }
                }
            }


            // INSTRUCTIONS HEADING

            item {

                Text(
                    text = "Cooking Instructions",

                    color = ForestGreen,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,

                    modifier = Modifier.padding(
                        top = 8.dp
                    )
                )
            }


            // COOKING STEPS

            if (recipe.instructions.isEmpty()) {

                item {

                    Text(
                        text = "Cooking instructions are not available yet.",
                        color = WarmBrown,
                        fontSize = 14.sp
                    )
                }

            } else {

                items(
                    recipe.instructions.size
                ) { index ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.White,
                                RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp),

                        verticalAlignment = Alignment.Top
                    ) {

                        Surface(
                            modifier = Modifier.size(36.dp),

                            shape = RoundedCornerShape(12.dp),

                            color = SageGreen
                        ) {

                            androidx.compose.foundation.layout.Box(
                                modifier = Modifier.fillMaxSize(),

                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "${index + 1}",

                                    color = ForestGreen,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.width(14.dp)
                        )

                        Text(
                            text = recipe.instructions[index],

                            color = Color(0xFF292D29),
                            fontSize = 15.sp,
                            lineHeight = 23.sp,

                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
