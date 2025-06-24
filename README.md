
# Jetpack Compose and XML-Based Navigation in Android

This project demonstrates how to manage **navigation** in an Android app using both **Jetpack Compose** with **Kotlin DSL** and **XML-based navigation**. This guide explains how to handle navigation using both approaches, making it easier to integrate them in a single project, and provides flexible navigation management.

## Key Features

* **Kotlin DSL Navigation with Jetpack Compose**: Dynamically create navigation graphs with Kotlin DSL for type-safe and readable routing.
* **XML-based Navigation**: A traditional approach where navigation is defined in XML files and parsed at build time.
* **Modular Architecture**: Communication between feature modules and core modules via shared `NavController`.
* **Argument Handling**: Passing arguments between composables in Jetpack Compose or fragments in XML-based navigation.
* **Deep Link Handling**: Both approaches support handling deep links for navigating to specific screens via URLs.

---

## Table of Contents

1. [Kotlin DSL Navigation](#kotlin-dsl-navigation)
2. [XML-based Navigation](#xml-based-navigation)
3. [Modular Architecture and Navigation Communication](#modular-architecture-and-navigation-communication)
4. [Deep Link Handling](#deep-link-handling)
5. [Dependencies](#dependencies)
6. [Setup Instructions](#setup-instructions)

---

## Kotlin DSL Navigation

In Jetpack Compose, we can define the navigation graph programmatically using Kotlin DSL, which is flexible and type-safe.

### 1. **Setup Navigation Graph in Jetpack Compose**

The `SetupNavGraph` function sets up the navigation between `HomeScreen` and `DetailsScreen` programmatically:

```kotlin
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destination.HomeScreen.fullRoute) {
        composable(Destination.HomeScreen.fullRoute) {
            HomeScreen(navController)
        }

        composable(
            Destination.UserDetailsScreen().fullRoute,
            arguments = listOf(
                navArgument(Destination.UserDetailsScreen.FIRST_NAME_KEY) {
                    type = NavType.StringType
                    defaultValue = "John"
                },
                navArgument(Destination.UserDetailsScreen.LAST_NAME_KEY) {
                    type = NavType.StringType
                    defaultValue = "Doe"
                }
            )
        ) { backStackEntry ->
            // Pass arguments to DetailsScreen
            DetailsScreen(backStackEntry)
        }
    }
}
```

### 2. **Handling Arguments in Jetpack Compose**

You can define arguments and pass them between screens. For instance, you pass `firstName` and `lastName` to the `DetailsScreen`:

```kotlin
// Define arguments in UserDetailsScreen
class UserDetailsScreen : Destination(DETAIL_ROUTE, FIRST_NAME_KEY, LAST_NAME_KEY) {
    operator fun invoke(firstName: String, lastName: String): String = route.appendParams(
        FIRST_NAME_KEY to firstName,
        LAST_NAME_KEY to lastName
    )

    companion object {
        const val FIRST_NAME_KEY = "firstName"
        const val LAST_NAME_KEY = "lastName"
    }
}
```

In **DetailsScreen**, retrieve the arguments:

```kotlin
@Composable
fun DetailsScreen(backStackEntry: NavBackStackEntry) {
    val firstName = backStackEntry.arguments?.getString(Destination.UserDetailsScreen.FIRST_NAME_KEY) ?: "Unknown"
    val lastName = backStackEntry.arguments?.getString(Destination.UserDetailsScreen.LAST_NAME_KEY) ?: "Unknown"
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "First Name: $firstName")
        Text(text = "Last Name: $lastName")
    }
}
```

### 3. **Navigating Between Screens**

In **HomeScreen**, you can navigate to **DetailsScreen** with dynamic arguments:

```kotlin
@Composable
fun HomeScreen(navController: NavHostController) {
    Button(onClick = {
        navController.navigate(Destination.UserDetailsScreen().invoke("John", "Doe"))
    }) {
        Text(text = "Go to Details Screen")
    }
}
```

---

## XML-based Navigation

XML-based navigation is another approach to handle screen navigation. This method involves defining the navigation graph in an XML file and referencing it programmatically.

### 1. **Setup XML-based Navigation**

In your `res/navigation` folder, create an XML file (e.g., `nav_graph.xml`) to define your navigation graph:

```xml
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/nav_graph"
    app:startDestination="@id/homeFragment">


</navigation>
```

### 2. **Set Up the `NavController` and Navigation Actions**

You can set up and use the `NavController` programmatically to manage navigation. Here’s how to set up the navigation graph in **MainActivity**:

```kotlin
val navController = findNavController(R.id.nav_host_fragment_activity_main)
navController.setGraph(R.navigation.nav_graph)
```

You can then use the `NavController` to navigate:

```kotlin
val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment("John", "Doe")
navController.navigate(action)
```

---

## Modular Architecture and Navigation Communication

### 1. **Shared Navigation Logic**

The navigation logic is handled by a shared **Core Module** that manages the `NavController` and **NavGraph**. The **Feature Modules** (like **HomeScreen**, **DetailsScreen**) communicate with the shared `NavController` for navigation actions.

Here’s how you can set up the `NavController` in the **Core Module**:

```kotlin
fun NavHostController.setupGraph(context: Context) = createGraph(
    startDestination = Destination.HomeScreen.fullRoute
) {
    fragment<HomeFragment>(Destination.HomeScreen.fullRoute) {
        label = context.getStringResource(R.string.title_home)
    }
    fragment<DetailsFragment>(Destination.UserDetailsScreen().fullRoute) {
        label = context.getStringResource(R.string.title_details)

        argument(FIRST_NAME_KEY) {
            type = NavType.StringType
            defaultValue = "John"
        }
        argument(LAST_NAME_KEY) {
            type = NavType.StringType
            defaultValue = "Doe"
        }
    }
}
```

### 2. **Communication Across Modules**

By defining shared **routes** and **navigation logic** in the Core module, Feature modules can easily access them and navigate to the necessary screens.

---

## Deep Link Handling

Both **Jetpack Compose navigation** and **XML-based navigation** support deep links. Here’s an example of defining a deep link in your navigation graph:

### For **Jetpack Compose**:

```kotlin
composable(
    route = "details/{firstName}/{lastName}",
    deepLinks = listOf(NavDeepLink(uriPattern = "myapp://user/{firstName}/{lastName}"))
) { backStackEntry ->
    val firstName = backStackEntry.arguments?.getString("firstName") ?: "Unknown"
    val lastName = backStackEntry.arguments?.getString("lastName") ?: "Unknown"
    DetailsScreen(firstName, lastName)
}
```

### For **XML-based navigation**:

```xml
<fragment
    android:id="@+id/detailsFragment"
    android:name="com.example.testnavigation.xml.ui.details.DetailsFragment"
    android:label="Details"
    app:deepLink="myapp://user/{firstName}/{lastName}" />
```

---

## Dependencies

Ensure your project includes the required dependencies for **Jetpack Compose** and **Navigation**:

```gradle
implementation "androidx.navigation:navigation-compose:2.4.0"
implementation "androidx.navigation:navigation-fragment-ktx:2.4.0"
implementation "androidx.navigation:navigation-ui-ktx:2.4.0"
```

---

## Setup Instructions

1. Clone the repository:

   ```bash
   git clone https://github.com/Kdsaid/NavigationXmlDSL.git
   ```

2. Open the project in **Android Studio**.

3. Sync your project with Gradle by clicking **File** > **Sync Project with Gradle Files**.

4. Build and run the project on your device or emulator.

---

## Conclusion

This project demonstrates how to set up both **Jetpack Compose** navigation with **Kotlin DSL** and **XML-based navigation** in the same app. By using **modular architecture**, shared navigation controllers, and type-safe routes, you can manage complex navigation logic with ease, while maintaining flexibility to use both modern and traditional navigation approaches.

---

### License

This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.


