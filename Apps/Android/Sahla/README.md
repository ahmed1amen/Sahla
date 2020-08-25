## Ibnsina Pharma Customer (Android Application).
Welcome to Sahla Android mobile application, your gate to ISP digital services
The Application is designed to be user-friendly and compatible with different mobile interfaces. This product is part of a program created to serve our customers and deliver all our services through digital platforms. through this Application; you can create sales orders, monitor your transactions, and view the most recent governmental publications. you can reach these features from the web portal or the mobile application available in its Android version on the Google Play and its iOS version on the App Store.

Products's Features:

Online Ordering: create new sales order and view the status of the previously requested orders.
Promotion: view all the proposed promotions from Ibnsina and its suppliers
Finance: track your invoices, statements and your balance status with Ibnsina.
Publications : View, download and share all the governmental publications
# Description
this is the application used by pharmacists to make online orders, assign promotions ...etc
# Installation
- **[Koltin](https://kotlinlang.org/docs/tutorials/getting-started.html)** version 1.3.72 Programming Language with [koltin KTX](https://developer.android.com/kotlin/ktx) dependencies. 
- The latest **Compile Sdk Version** 30.
- Latest stable **Gradle** version 4.0.0.
- Latest stable **[Android Studio](https://developer.android.com/studio/)** version 4.0.0.

## This application applies :
- MVVM as architecture design pattern.
- Binding [Live data](https://developer.android.com/topic/libraries/architecture/livedata) with views using [view binding](https://developer.android.com/topic/libraries/view-binding) and [data binding](https://developer.android.com/topic/libraries/data-binding)
- **[Binding Adapters](https://developer.android.com/topic/libraries/data-binding/binding-adapters)** for less boilerplate codes in the view.
- **[Timber](https://github.com/JakeWharton/timber)** for automatic debug tags logging.
- [Material Design Components](https://material.io/components) for UI styles and shapes.
- **[Retrofit](https://square.github.io/retrofit/)** for network calls and **Gson Converter Factory** for response serializations.
- **[Navigation Graph](https://developer.android.com/guide/navigation/navigation-getting-started)** for fragment transactions and **[Safe Args](https://developer.android.com/guide/navigation/navigation-pass-data)** to pass data between fragments.
- **[Android Hilt](https://developer.android.com/training/dependency-injection/hilt-android)** for dependency injection.

# Usage
## Creating view model
view models will be the layer between the view and the business model, we are currently use it to hold the business logic, validation and network calls.

**creating basic view model** :
```koltin
class SplashViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {}
```
**creating view model that access application**

we create view model inherits from android view model when we need to access application instance in the view model, this might be for **localization needs** or whenever the need of context instance inside the view model.
```koltin
class AuthViewModel @ViewModelInject constructor(private val applicationContext: Application) : AndroidViewModel(applicationContext) {
  // type your code here.
    fun loginWithEmailAndPassword() {
        auth.signInWithEmailAndPassword(email.value!!, password.value!!)
                .addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            if (it.result?.user?.isEmailVerified == true) {
                                //
                            } else {
                                _apiRequest.value = ApiRequestStatus.Error(applicationContext.getString(R.string.verify_email_message))
                            }
                        }
                        else -> {
                            //
                        }
                    }
                }
    }
}
```
### Using [Single Source Of Truth](https://developer.android.com/jetpack/guide#addendum) for Addendum exposing network status :

this is recommended for best practice and clean architecture for network call states we are using 
```koltin
sealed class ApiRequestStatus<T>(
        val data: T? = null,
        val apiStatus: Status,
        val message: String? = null
) {
    enum class Status { LOADING, SUCCESS, ERROR, FAILED, EMPTY }
    class Success<T>(data: T? = null) : ApiRequestStatus<T>(data, Status.SUCCESS)
    class Loading<T>(data: T? = null) : ApiRequestStatus<T>(data, Status.LOADING)
    class Error<T>(message: String?) : ApiRequestStatus<T>(apiStatus = Status.ERROR, message = message)
    class Failed<T>(message: String? = null) :
            ApiRequestStatus<T>(apiStatus = Status.FAILED, message = message)
}
```
so when ever you make a network call we will use live data variable holding instance of **ApiRequestStatus** and bind the observation with the views.

#### for example :
this is how we use live data inside the view model using the internal external pattern to prevent set value out side the scope of the view model.
```koltin
private val _apiRequest = MutableLiveData<ApiRequestStatus<String>>()
val apiRequest: LiveData<ApiRequestStatus<String>> get() = _apiRequest
```
then we start our fun and set the action state as below :
```koltin
    fun loginWithEmailAndPassword() {
        _apiRequest.value = ApiRequestStatus.Loading()
        auth.signInWithEmailAndPassword(email.value!!, password.value!!)
                .addOnCompleteListener {
                    when {
                        it.isSuccessful -> {
                            val username = it.result?.user?.displayName
                            if (it.result?.user?.isEmailVerified == true) {
                                _apiRequest.value = ApiRequestStatus.Success(username)
                            } else {
                                it.result?.user?.sendEmailVerification()
                                _apiRequest.value = ApiRequestStatus.Error(applicationContext.getString(R.string.verify_email_message))
                            }
                        }
                        else -> {
                            _apiRequest.value = ApiRequestStatus.Error(it.exception?.message)
                        }
                    }
                }
    }
```
As we can see her we used the states [ Loading, Success, Error ] so it's now easier and better to make our UI changes binding the value of our instance of apiRequest.

## Data Binding :
The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.

The expression language allows you to write expressions that connect variables to the views in the layout. The Data Binding Library automatically generates the classes required to bind the views in the layout with your data objects. The library provides features such as imports, variables, and includes that you can use in your layouts.

example for using it :
```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto">
    <data>
        <variable
            name="viewmodel"
            type="com.myapp.data.ViewModel" />
    </data>
    <ConstraintLayout... /> <!-- UI layout's root element -->
</layout>
```

### Binding Adapters
For every layout expression, there is a binding adapter that makes the framework calls required to set the corresponding properties or listeners. For example, the binding adapter can take care of calling the setText() method to set the text property or call the setOnClickListener() method to add a listener to the click event.

here's how we make our fun :
```koltin
@BindingAdapter("imgSrc")
fun bindImages(imageView: ImageView, source: Any?) {
    Glide.with(imageView).load(source).into(imageView)
}
```
and here's how we call it in the view :
```xml
<layout>
    <data>
    <variable
         name="boardingItem"
         type="BoardingItem" />
    </data>
        <ImageView>
            <!--your xml style code here-->
            app:imgSrc="@{boardingItem.boardingImageResource}"/>
        </ImageView>
</layout>  
```

### Using Single souce of truth with view binding 
we are going to bind our request response always with one included view in the parent view as below :
```xml
<layout xmlns:bind="http://schemas.android.com/apk/res-auto">
    <data>
        <import type="com.hadi.torrentmovies.data.wrapper.ApiRequestStatus.Status" />
        <variable
            name="viewModel"
            type="AuthViewModel" />
    </data>
    <include
            layout="@layout/layout_api_status"
            bind:apiRequestStatus="@{viewModel.apiRequest.apiStatus}"
            bind:message="@{viewModel.apiRequest.message}"
            bind:retryClickListener="@{viewModel.retry}"/>
</layout>
```
Now it's easier to observe the network changes and states, and even give the user the chance to retry the failed requests with this simple lamda class :
```kotlin
class RetryError(private val retry: () -> Unit) {
    // pass your action here to retry later
    fun retryAgain() = retry()
}
```

### Android Hilt :
Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.\
Hilt provides a standard way to use DI in your application by providing containers for every Android class in your project and managing their lifecycles automatically, [See More](https://developer.android.com/training/dependency-injection/hilt-android).
#### example for creating retrofit module :
- Start with declaring your application class :
```kotlin
@HiltAndroidApp
class MyApplication : Application() 
```
- Then tell hilt how to instantiate your object :
```kotlin
@InstallIn(ApplicationComponent::class)
@Module
object RetrofitModule {
    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(Gson()))

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit.Builder): ApiService =
        retrofit.build().create(ApiService::class.java)
}
```
- And use it in constructor injection :
```kotlin
@Singleton
class AccountRepository @Inject constructor(
    private val networkManager: NetworkManager,
    private val service: ApiService
) 
```
## Git for **Version Control System** :
Please feel free to know more about using [Git](https://git-scm.com/docs/git)\
Just one important rule before committing any changes and do the push, please open your terminal if you're using windows and make the following commands :
- gradlew clean && gradlew CleanBuildCache
- Or open from your right side menu in android studio -> Gradle -> module name (app) -> tasks -> build -> clean and then cleanBuildCache.\
This will make sure to commit and push your project clean without boilerplate code or files.
# Road Map :

In the future we are going to use :
- **[Paging](https://developer.android.com/topic/libraries/architecture/paging)** for pagination inside the application.
- **[Room](https://developer.android.com/topic/libraries/architecture/room)** for offline caching.